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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.technikradio.node.engine.action.Application;
import org.technikradio.node.engine.plugin.settings.Settings;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * This class is used to translate everything. It will use the files located in
 * the LANG folder and load up files containing the local using the MML format.
 * If it can't find the perfect file it will try to load the
 * '<code>en_US.mml</code>' file. It assumes lower case file endings by default
 * and expects files with the following naming format:
 * <code>&lt;language (lower case)&gt;_&lt;country (upper case).mml</code>.
 * 
 * An MML file contains one entry per line in the following format:
 * <code>&lt;key&gt;:&lt;value&gt;</code>. In order to use the new line
 * character escape it with <code>${13}</code>. A <code>:</code> can also be
 * escaped by the usage of <code>${58}</code> as well as the space symbol can
 * be escaped by the usage of <code>${Space}</code>. All escaping methods can be
 * used everywhere inside the string containments.
 * 
 * It is possible to use the content of an .string file within a value. To do so
 * use the following syntax:
 * <code>&lt;key&gt;: !!file&lt;path to file&gt;!!</code> the escaping methods
 * can be used to describe the file path as well. The loading of the file is
 * relative to the parent file. The content of a .string file will be loaded as
 * on unit (the entire file will be handled as on string). Escaping rules don't
 * apply to such files. This class will load .string files using the UTF-8
 * encoding but using a different file ending than .string will make this class
 * treating the content as ASCII code.
 * 
 * Other MML files can be imported by writing a line of the following syntax:
 * <code>!!import <path to file> !!</code> with all escaping rules applying.
 * This class will load the file then and returns after finishing that operation
 * with the loading of this file. Be aware of the fact that this can result in a
 * large number of open files.
 * 
 * Finally the MML file format supports comments. Comments are simply lines of
 * text that start with a <code>#</code> symbol. All text in this line will then
 * be ignored. NOTE that as soon the is a different character in front of the
 * <code>#</code> it won't be treated as a comment.
 * 
 * @author doralitze
 */
public class Localisation {

	private static final HashMap<String, String> main;
	private static final HashMap<String, String> fallback;

	static {
		main = new HashMap<String, String>();
		fallback = new HashMap<String, String>();
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

		// load main file
		loadFile(mainFile, main);
		// load fallback file
		loadFile(fallbackFile, fallback);
	}

	/**
	 * This method gets used by the initializer of this class. it loads an .mml
	 * file.
	 * 
	 * @param file
	 *            The file to load.
	 * @param database
	 *            The database to store the files content in.
	 */
	protected static void loadFile(String file, HashMap<String, String> database) {
		BufferedReader br = null;
		try {
			if (Files.exists(Paths.get(file))) {
				File f = new File(file);
				File ff = f.getParentFile();
				br = new BufferedReader(new FileReader(f));
				String line;
				while ((line = br.readLine()) != null) {
					if (line.startsWith("#")) {
						// Do nothing here it's a comment
					} else if (line.startsWith("!!import") && line.endsWith("!!")) {
						if (ff.exists() && ff.isDirectory())
							loadFile(ff.getAbsolutePath() + File.separator
									+ line.replaceAll("!!import", "").replaceAll("!!", "").replace(" ", "")
											.replace("${58}", ":").replace("${13}", "\n").replace("${Space}", " ")
											.replace("/", File.separator),
									database);
						else {
							Console.log(LogType.Warning, "Localisation",
									ff.getAbsolutePath() + " doesn't seam to be a directory.");
						}
					} else {
						String[] v = line.split(":");
						String key = v[0];
						String value = v[1];
						if (value.startsWith("!!file") && value.endsWith("!!")) {
							value = loadDotStringFile(ff.getAbsolutePath() + File.separator
									+ value.replaceAll("!!file", "").replaceAll("!!", "").replace(" ", "")
											.replace("${58}", ":").replace("${13}", "\n").replace("${Space}", " ")
											.replace("/", File.separator));
						}
						database.put(key.replace("${58}", ":").replace("${13}", "\n").replace("${Space}", " "),
								value.replace("${58}", ":").replace("${13}", "\n").replace("${Space}", " "));
					}
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
	 * This method is used to load up .string files. If the desired files end
	 * with .string this method will pare the content as UTF-8 and otherwise as
	 * ASCII.
	 * 
	 * @param filepath
	 *            The path to the desired file.
	 * @return The content of the file
	 * @throws IOException
	 *             This exception gets thrown if something goes wrong.
	 */
	protected static String loadDotStringFile(String filepath) throws IOException {
		File file = new File(filepath);
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();
		String str = null;
		if (file.getAbsolutePath().toLowerCase().endsWith(".string"))
			str = new String(data, "UTF-8");
		else
			str = new String(data, "ASCII");
		return str;
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
