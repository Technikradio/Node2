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
package org.technikradio.node.engine.action;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.technikradio.node.engine.event.BasicEvents;
import org.technikradio.node.engine.event.Event;
import org.technikradio.node.engine.event.EventRegistry;
import org.technikradio.node.engine.event.EventResponder;
import org.technikradio.node.engine.plugin.Manifest;
import org.technikradio.node.engine.plugin.PluginLoader;
import org.technikradio.node.engine.plugin.PluginRegistry;
import org.technikradio.node.engine.plugin.UnsolvedDependencyException;
import org.technikradio.node.engine.plugin.ui.DisplayFactory;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * This class handles the application interaction.
 * @author doralitze
 */
public class Application {
	
	/**
	 * This path points to the directory where node stores local user dependent stuff like settings.
	 */
	public static final String LOCAL_APPDATA_FOLDER;
	/**
	 * This field contains the current operation system node is running on.
	 */
	public static final String CURRENT_OPERATING_SYSTEM;
	/**
	 * This variable indicates the availability of the APPDATA folder.
	 */
	public static final boolean LOCAL_APPDATA_FOLDER_AVAIABLE;
	/**
	 * This field contains the path to the plug-in folder.
	 */
	public static final String PLUGIN_FOLDER;
	/**
	 * This field contains the path to the translation folder.
	 */
	public static final String LANG_FOLDER;
	/**
	 * This field contains the path to the resources folder.
	 */
	public static final String RESOURCES_FOLDER;
	
	
	static {
		CURRENT_OPERATING_SYSTEM  = (System.getProperty("os.name")).toUpperCase();
		{
			String currentBaseDir = "";
			String dirPrefix = "";
			if (CURRENT_OPERATING_SYSTEM.contains("WIN")){
				currentBaseDir = System.getenv("APPDATA");
			} else {
				currentBaseDir = System.getProperty("user.home");
				if(CURRENT_OPERATING_SYSTEM.contains("MAC")){
					currentBaseDir += "/Library/Application Support";
				} else {
					dirPrefix = ".";
				}
			}
			LOCAL_APPDATA_FOLDER = currentBaseDir + File.separator + dirPrefix + "Node";
		}
		{
			boolean pathexists = false;
			if(!(new File(LOCAL_APPDATA_FOLDER).exists())){
				try {
					Files.createDirectory(Paths.get(LOCAL_APPDATA_FOLDER));
					pathexists = true;
				} catch (IOException e) {
					Console.log(LogType.Error, "Application", "Can't create APPDATA folder:");
					e.printStackTrace();
				}
			} else {
				pathexists = true;
			}
			LOCAL_APPDATA_FOLDER_AVAIABLE = pathexists;
		}
		PLUGIN_FOLDER = Main.getAppHome() + File.separator + "plugins";
		LANG_FOLDER = Main.getAppHome() + File.separator + "lang";
		RESOURCES_FOLDER = Main.getAppHome() + File.separator + "rsc";
	}

	/**
	 * This method gets called to initialize stuff like the look and feel
	 */
	protected static void setupApp(){
		DisplayFactory.init();
		Manifest[] ms = null;
		try {
			ms = PluginLoader.calculateDependencys(PLUGIN_FOLDER);
		} catch (IOException | UnsolvedDependencyException e) {
			Console.log(LogType.Error, "Application", "Failed to retrieve information about plugins.");
			e.printStackTrace();
			crash("Failed to retrieve information about plugins.", CrashCodes.ERROR_DURING_INITIALIZATION);
			return;
		}
		for(int i = 0; i < ms.length; i++){
			try {
				Console.log(LogType.StdOut, "Application", "Loading plugin '" + ms[i].getName() + "'.");
				PluginLoader.loadPlugin(ms[i]);
			} catch (Exception e) {
				Console.log(LogType.Warning, "Application", "Failed to load plugin '" + ms[i].getName() + "'.");
				e.printStackTrace();
			}
		}
		if(PluginRegistry.getNumberOfLoadedPlugins() == 0){
			Thread t = new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {}
					Console.log(LogType.StdOut, "Application",
							"No plugins loaded. Exiting Node. Please install some plugins.");
					close();
				}
			});
			t.setName("ExitThread");
			t.start();
		}
	}
	
	/**
	 * Use this method to invoke the crash routines. This method
	 * uses 1 as the default error code.
	 * @param reason The reason why the application crashed
	 */
	public static void crash(Object reason){
		crash(reason, CrashCodes.UNKNOWN_REASON);
	}
	
	/**
	 * Use this method to invoke the crash routines.
	 * @param reason The reason why the application crashed
	 * @param code The error code to use
	 */
	public static void crash(Object reason, int code){
		Console.log(LogType.Error, "Application", "The application is about to crash");
		Event e = new Event(BasicEvents.APPLICATION_CRASHED_EVENT, null, new EventResponder<String>());
		EventRegistry.raiseEvent(e, true);
		System.err.println(reason.toString());
		System.exit(code);
	}
	
	/**
	 * Use this method to safely close the application.
	 */
	public static void close(){
		Event e = new Event(BasicEvents.APPLICATION_CLOSING_EVENT, null, new EventResponder<String>());
		EventRegistry.registerWaitSync(e);
		EventRegistry.raiseEvent(e, false);
		EventRegistry.waitForProcessedEvent(e);
		Console.log(LogType.StdOut, "Application", "The Application will now exit.");
		System.exit(0);
	}
}
