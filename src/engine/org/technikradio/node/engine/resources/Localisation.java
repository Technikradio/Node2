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
package org.technikradio.node.engine.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;

import org.technikradio.node.engine.action.Application;
import org.technikradio.node.engine.plugin.settings.Settings;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * This class is used to translate everything.
 * 
 * @author doralitze
 */
public class Localisation {

	private static final Hashtable<String, String> main;
	private static final Hashtable<String, String> fallback;

	static {
		main = new Hashtable<String, String>();
		fallback = new Hashtable<String, String>();
		if (Settings.get("org.technikradio.node.engine.resources.defaultLanguageFile").equals("")) {
			Settings.put("org.technikradio.node.engine.resources.defaultLanguageFile",
					System.getProperty("user.language") + "_" + System.getProperty("user.country") + ".mml");
		}
		if (Settings.get("org.technikradio.node.engine.resources.fallbackLanguageFile").equals("")) {
			Settings.put("org.technikradio.node.engine.resources.fallbackLanguageFile", "en_US.mml");
		}
		String mainFile = Application.LANG_FOLDER + File.separator
				+ Settings.get("org.technikradio.node.engine.resources.defaultLanguageFile", "en_US.mml");
		String fallbackFile = Application.LANG_FOLDER + File.separator
				+ Settings.get("org.technikradio.node.engine.resources.fallbackLanguageFile", "en_US.mml");
		BufferedReader br = null;
		// load main file
		try {
			if (Files.exists(Paths.get(mainFile))) {
				br = new BufferedReader(new FileReader(new File(mainFile)));
				String line;
				while ((line = br.readLine()) != null) {
					String[] v = line.split(":");
					String key = v[0];
					String value = v[1];
					main.put(key.replace("${58}", ":").replace("${13}", "\n"),
							value.replace("${58}", ":").replace("${13}", "\n"));
				}
			} else {
				Console.log(LogType.Warning, "Localisation",
						"Cannot load settings file. APPDATA folder not aviable. Falling back to empty dictionary");
			}
		} catch (IOException e) {
			Console.log(LogType.Error, "Localisation", "Cannot load settings file. Falling back to empty dictionary:");
			if (!(e instanceof FileNotFoundException))
				e.printStackTrace();
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					Console.log(LogType.Error, "Localisation", "An error occured while closing the buffered reader:");
					if (!(e instanceof FileNotFoundException))
						e.printStackTrace();
				}
		}
		// load fallback file
		try {
			if (Files.exists(Paths.get(fallbackFile))) {
				br = new BufferedReader(new FileReader(new File(fallbackFile)));
				String line;
				while ((line = br.readLine()) != null) {
					String[] v = line.split(":");
					String key = v[0];
					String value = v[1];
					fallback.put(key.replace("${58}", ":").replace("${13}", "\n"),
							value.replace("${58}", ":").replace("${13}", "\n"));
				}
			} else {
				Console.log(LogType.Warning, "Localisation",
						"Cannot load settings file. APPDATA folder not aviable. Falling back to empty dictionary");
			}
		} catch (IOException e) {
			Console.log(LogType.Error, "Localisation", "Cannot load settings file. Falling back to empty dictionary:");
			if (!(e instanceof FileNotFoundException))
				e.printStackTrace();
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					Console.log(LogType.Error, "Localisation", "An error occured while closing the buffered reader:");
					if (!(e instanceof FileNotFoundException))
						e.printStackTrace();
				}
		}
	}

	/**
	 * This method will search the primary translation file for the desired key
	 * and will return the value if it found one. If it doesn't find the desired
	 * value in the first place it will search the fall back file. If it can't
	 * find the value there either it will return the key wrapped in percentage
	 * symbols.
	 * 
	 * @param key
	 *            The key / identifier to search for.
	 * @return Either the optimal translation or the fall back translation or
	 *         %key%.
	 */
	public static String getString(String key) {
		return getString(key, "%" + key + "%");
	}

	/**
	 * This method will search the primary translation file for the desired key
	 * and will return the value if it found one. If it doesn't find the desired
	 * value in the first place it will search the fall back file. If it can't
	 * find the value there either it will return the escape string.
	 * 
	 * @param key
	 *            The key / identifier to search for.
	 * @param escape
	 *            The escape string to return if it can't find the desired
	 *            string.
	 * @return Either the optimal translation or the fall back translation or
	 *         the escape string.
	 */
	public static String getString(String key, String escape) {
		if (main.containsKey(key))
			return main.get(key);
		if (fallback.containsKey(key))
			return fallback.get(key);
		Console.log(LogType.Warning, "Localisation", "Couldn't find entry: " + key);
		return escape;
	}

}
