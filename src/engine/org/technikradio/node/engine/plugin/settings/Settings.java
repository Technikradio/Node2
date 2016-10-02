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
package org.technikradio.node.engine.plugin.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Hashtable;

import org.technikradio.node.engine.action.Application;
import org.technikradio.node.engine.plugin.Command;
import org.technikradio.node.engine.plugin.CommandRegistry;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * This class is used to load and save settings. The settings files are stored
 * inside the local users's application data folder. This is for example
 * %APPDATA% under windows. Note that the characters ':' and CR are replaced
 * with ${58} and ${13} and vice versa. if your setting contains these escape
 * strings you have to deal yourself with it. Please also consider that all
 * settings are put into one file so it would be wise to use identifiers as the
 * keys.
 * 
 * @author doralitze
 */
public class Settings {

	private static final Hashtable<String, String> pairs;

	static {
		pairs = new Hashtable<String, String>();
		BufferedReader br = null;
		try {
			if (Application.LOCAL_APPDATA_FOLDER_AVAIABLE) {
				br = new BufferedReader(
						new FileReader(new File(Application.LOCAL_APPDATA_FOLDER + File.separator + "settings.mml")));
				String line;
				while ((line = br.readLine()) != null) {
					String[] v = line.split(":");
					String key = v[0];
					String value = v[1];
					pairs.put(key.replace("${58}", ":").replace("${13}", "\n"),
							value.replace("${58}", ":").replace("${13}", "\n"));
				}
			} else {
				Console.log(LogType.Warning, "Settings",
						"Cannot load settings file. APPDATA folder not aviable. Falling back to empty dictionary");
			}
		} catch (IOException e) {
			Console.log(LogType.Error, "Settings", "Cannot load settings file. Falling back to empty dictionary:");
			if (!(e instanceof FileNotFoundException))
				e.printStackTrace();
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					Console.log(LogType.Error, "Settings", "An error occured while closing the buffered reader:");
					if (!(e instanceof FileNotFoundException))
						e.printStackTrace();
				}
		}
		CommandRegistry.addCommand("list-all-settings", new Command() {

			@Override
			public boolean execute(String[] args, PrintStream outputStream) {
				for (String key : pairs.keySet()) {
					outputStream.print(key);
					outputStream.print("=");
					outputStream.println(pairs.get(key));
				}
				return true;
			}
		});
		CommandRegistry.addCommandHelp("list-all-settings", "Use this command to display all settings",
				"list-all-settings\n\tUse this command to review all aviable settings.\n");
		CommandRegistry.addCommand("set-property", new Command() {

			@Override
			public boolean execute(String[] args, PrintStream outputStream) {
				if (args.length < 2) {
					outputStream.println("Please consider the help by typing 'help set-property'");
					return false;
				}
				pairs.put(args[0], args[1]);
				return true;
			}
		});
		CommandRegistry.addCommandHelp("set-property", "Use this command to modify the settings properties",
				"set-property <property> <value>\n\tSets the setting 'property' to 'value'.");
	}

	/**
	 * This method is used to save the settings to the hard drive.
	 * 
	 * @return true if it successfully saved the settings or otherwise false.
	 */
	public static boolean save() {
		if (!Application.LOCAL_APPDATA_FOLDER_AVAIABLE)
			return false;
		FileWriter savefile = null;
		try {
			savefile = new FileWriter(Application.LOCAL_APPDATA_FOLDER + File.separator + "settings.mml");
			for (String key : pairs.keySet()) {
				String value = pairs.get(key);
				savefile.write(key.replace(":", "${58}").replace("\n", "${13}"));
				savefile.write(":");
				savefile.write(value.replace(":", "${58}").replace("\n", "${13}"));
				savefile.write("\n");
			}
			savefile.flush();
		} catch (IOException e) {
			Console.log(LogType.Error, "Settings", "An error occured while saving the settings file:");
			e.printStackTrace();
			return false;
		} finally {
			try {
				savefile.close();
			} catch (IOException e) {
				Console.log(LogType.Error, "Settings", "An error occured while closing the settings file:");
				e.printStackTrace();
			}
		}

		return true;
	}

	/**
	 * This method is used to get stored setting entries. This method uses an
	 * empty string as the default value if no entry was found.
	 * 
	 * @param identifier
	 *            The identifier used to identify the requested entry.
	 * @throws NullPointerException
	 *             if the given identifier is empty or null.
	 * @return The requested setting or the an empty string depending on the
	 *         situation.
	 */
	public static String get(String identifier) {
		return get(identifier, "");
	}

	/**
	 * This method is used to get stored setting entries.
	 * 
	 * @param identifier
	 *            The identifier used to identify the requested entry.
	 * @param defaultValue
	 *            The default value to use if the setting wasn't found.
	 * @throws NullPointerException
	 *             if the given identifier is empty or null.
	 * @return The requested setting or the given default value depending on the
	 *         situation.
	 */
	public static String get(String identifier, String defaultValue) {
		if (identifier == null || identifier.equals(""))
			throw new NullPointerException("The identifier is not allowed to be empty.");
		if (pairs.containsKey(identifier))
			return pairs.get(identifier);
		else
			return defaultValue;
	}

	/**
	 * This method is used to store entries inside the settings.
	 * 
	 * @param identifier
	 *            The identifier used to mark the value.
	 * @param value
	 *            The value to store.
	 * @throws NullPointerException
	 *             if the given identifier is empty or null.
	 * @return Returns the value that was overwritten or null if there wasn't
	 *         any value overwritten.
	 */
	public static String put(String identifier, String value) {
		if (identifier == null || identifier.equals(""))
			throw new NullPointerException("The identifier is not allowed to be empty.");
		return pairs.put(identifier, value);
	}

	/**
	 * Use this method in order to check if a certain key already exists in the
	 * database.
	 * 
	 * @param key
	 *            The key to check for.
	 * @return True if the key is already stored or otherwise false.
	 */
	public static boolean avaiable(String key) {
		return pairs.containsKey(key);
	}

}
