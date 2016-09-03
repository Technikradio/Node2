/*
Copyright (c) 2016, Technikradio
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

* Neither the name of Node2 nor the names of its
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * 
 */
package org.technikradio.node.engine.plugin;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import org.technikradio.node.engine.RuntimeRelevant;
import org.technikradio.node.engine.action.Hints;
import org.technikradio.node.engine.event.BasicEvents;
import org.technikradio.node.engine.event.Event;
import org.technikradio.node.engine.event.EventHandler;
import org.technikradio.node.engine.event.EventRegistry;
import org.technikradio.node.engine.plugin.settings.SettingsObject;
import org.technikradio.node.engine.plugin.ui.Window;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * This class is used to register all required components that enable the
 * plug-ins to work.
 * 
 * @author doralitze
 */
public final class PluginRegistry {

	private static Hashtable<String, Plugin> plugins;
	private static ArrayList<SettingsObject> settingsTabs;
	private static DataSource currentActiveDataSource;
	private static Window currentOpenWindow;
	private static WorkFile currentActiveWorkFile;
	private static ArrayList<DataSource> dataSources;

	static {
		plugins = new Hashtable<String, Plugin>();
		settingsTabs = new ArrayList<SettingsObject>();
		currentActiveDataSource = null;
		dataSources = new ArrayList<DataSource>();
		EventRegistry.addEventHandler(BasicEvents.APPLICATION_CLOSING_EVENT, new EventHandler() {

			@Override
			public void handleEvent(Event e) {
				Enumeration<Plugin> ee = plugins.elements();
				while (ee.hasMoreElements()) {
					Plugin p = ee.nextElement();
					p.unload();
				}
			}
		});
	}

	/**
	 * This method returns an enumeration of all registered plug-ins
	 * 
	 * @return the enumeration
	 */
	protected static Enumeration<Plugin> getAllPlugins() {
		return plugins.elements();
	}

	/**
	 * This method registers a plug-in loaded by the PluginLoader class.
	 * 
	 * @param plugin
	 *            The plug-in to register.
	 * @return true if the plug-in successfully loaded or false otherwise
	 */
	protected synchronized static boolean registerPlugin(Plugin plugin) {
		try {
			boolean keep = true;
			if (plugin.getClass().isAnnotationPresent(RuntimeRelevant.class)) {
				RuntimeRelevant ann = plugin.getClass().getAnnotation(RuntimeRelevant.class);
				keep = ann.required();
			} else {
				Console.log(LogType.Warning, "PluginRegistry", "The plugin '" + plugin.getMainfest().getName() + "' doesn't specify its runtime requirement.");
			}
			if (keep)
				plugins.put(plugin.getMainfest().getIdentifier(), plugin);
			if (Hints.wasUpdated(plugin.getMainfest().getIdentifier()))
				plugin.update();
			plugin.load();
			plugin.setLoadedFlag();
			return true;
		} catch (Exception e) {
			Console.log(LogType.Warning, "PluginRegistry", "Error on invoking plugins init methods.");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method searches the known plug-ins for the desired one. It will
	 * return <code>null</code> if it can't find one.
	 * 
	 * @param identifier
	 *            to look for
	 * @return The requested plug-in or <code>null</code> otherwise
	 */
	public static Plugin findPlugin(String identifier) {
		return plugins.get(identifier);
	}

	/**
	 * This method is used to register settings tabs used inside the settings
	 * frame. It will drop the request if the object is already registered.
	 * 
	 * @param tab
	 *            to use
	 */
	public synchronized static void registerSettingsTab(SettingsObject tab) {
		if (!settingsTabs.contains(tab))
			settingsTabs.add(tab);
	}

	/**
	 * @return the currentActiveDataSource
	 */
	public static DataSource getCurrentActiveDataSource() {
		return currentActiveDataSource;
	}

	/**
	 * This method looks for the requested identifier and returns true if the
	 * plug-in is successfully registered.
	 * 
	 * @param identifier
	 *            to look for
	 * @return true if it can find the plug-in otherwise false
	 */
	public static boolean isPluginPresent(String identifier) {
		return plugins.containsKey(identifier) && (plugins.get(identifier) != null);
	}

	/**
	 * This method checks if the desired plug-in already finished initializing
	 * itself.
	 * 
	 * @param identifier
	 *            to look for
	 * @return true if the plug-in finished loading otherwise false
	 */
	public static boolean isPluginLoaded(String identifier) {
		if (!plugins.contains(identifier))
			return false;
		Plugin p = plugins.get(identifier);
		return p.isPluginLoaded();
	}

	/**
	 * This method is used to get the current open window.
	 * 
	 * @return the current open window
	 */
	public static Window getCurrentOpenWindow() {
		return currentOpenWindow;
	}

	/**
	 * This is the setter for the current open window. Be very careful using
	 * this setter!
	 * 
	 * @param currentOpenWindow
	 *            the window to set
	 */
	public synchronized static void setCurrentOpenWindow(Window currentOpenWindow) {
		PluginRegistry.currentOpenWindow = currentOpenWindow;
	}

	/**
	 * Use this method to get the number of loaded plug-ins.
	 * 
	 * @return The number of registered plug-ins.
	 */
	public static int getNumberOfLoadedPlugins() {
		return plugins.size();
	}

	/**
	 * Use this method to add a possible DataSource.
	 * 
	 * @param ds
	 *            The DataSource to register.
	 * @return False if the given DataSource was already added or otherwise
	 *         true.
	 */
	public synchronized static boolean addDataSource(DataSource ds) {
		if (dataSources.contains(ds))
			return false;
		dataSources.add(ds);
		return true;
	}

	/**
	 * Use this method to get all registered DataSources.
	 * 
	 * @return An array containing all registered DataSource's.
	 */
	public synchronized static DataSource[] getAllRegisteredDataSources() {
		return dataSources.toArray(new DataSource[dataSources.size()]);
	}

	/**
	 * Use this method in order to get the current active work file.
	 * @return the current active work file
	 */
	public static WorkFile getCurrentActiveWorkFile() {
		return currentActiveWorkFile;
	}

	/**
	 * Use this method in order to set the current active work file.
	 * @param currentActiveWorkFile the work file to make the current active one
	 */
	public synchronized static void setCurrentActiveWorkFile(WorkFile currentActiveWorkFile) {
		PluginRegistry.currentActiveWorkFile = currentActiveWorkFile;
	}

}
