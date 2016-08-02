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
package org.technikradio.node.engine.plugin;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.technikradio.node.engine.action.Main;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * This class is used to load a plug-in based on the given manifest file.
 * 
 * @author doralitze
 *
 */
public class PluginLoader {

	/**
	 * This method is used to load a plug-in. Note that it doesn't automatically
	 * handles the dependency loading. Use
	 * {@link org.technikradio.node.engine.plugin.PluginLoader#calculateDependencys(String)}
	 * first.
	 * 
	 * @param manifest
	 *            The manifest required to load the plug-in.
	 * @return true if the plug-in is compatible and loaded or false if the
	 *         plug-in is incompatible.
	 * @throws IOException
	 *             This exception gets thrown when the method is unable of
	 *             managing the IO required to load the plug-in.
	 */
	public static final boolean loadPlugin(Manifest manifest) throws IOException {
		PluginLoader.class.getClassLoader();
		try {
			URLClassLoader loader = URLClassLoader.newInstance(doBatchTest(manifest.getPathToPlugin()),
					ClassLoader.getSystemClassLoader());
			@SuppressWarnings("rawtypes")
			Class pclass = loader.loadClass(manifest.getMainClass());
			Object pseudoPlugin = pclass.newInstance();
			if (!(pseudoPlugin instanceof Plugin)) {
				Console.log(LogType.Error, "PluginLoader.loadPlugin", manifest.getPathToPlugin().toString()
						+ ": referenced main class is not an instance of Plugin.class.");
				return false;
			} else {
				Plugin p = (Plugin) pseudoPlugin;
				p.setMainfest(manifest);
				PluginRegistry.registerPlugin(p);
			}

		} catch (ClassNotFoundException e) {
			Console.log(LogType.Error, "PluginLoader.loadPlugin",
					manifest.getPathToPlugin().toString() + ": Unable to load plug-in.");
			e.printStackTrace();
			return false;
		} catch (InstantiationException e) {
			Console.log(LogType.Error, "PluginLoader.loadPlugin",
					manifest.getPathToPlugin().toString() + ": Unable to instanciate plug-in.");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Console.log(LogType.Error, "PluginLoader.loadPlugin",
					manifest.getPathToPlugin().toString() + ": Forbidden to load plug-in.");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method is used to load a manifest file of a plug-in.
	 * 
	 * @param path
	 *            The path pointing to the top level folder of the plug-in. This
	 *            folder has to contain the manifest file.
	 * @throws IOException
	 *             It throws an IOException if it fails to handle the IO.
	 * @return The loaded manifest file.
	 */
	public static final Manifest loadManifest(File path) throws IOException {
		Manifest m = null;
		File f = null;
		try {
			f = new File(path.getAbsolutePath() + File.separator + "manifest.json");
			JSONObject j = new JSONObject(new String(Files.readAllBytes(f.toPath())));
			m = new Manifest();
			m.setPathToPlugin(path);
			try {
				m.setMainClass(j.getString("mainClass"));
			} catch (JSONException e) {
				Console.log(LogType.Error, "PluginLoader.loadManifest",
						f.toString() + " doesn't specify a main class.");
				e.printStackTrace();
				return null;
			}
			try {
				m.setVersion(j.getInt("version"));
			} catch (JSONException e) {
			}
			try {
				m.setCompatibleVersion(j.getInt("compatibleVersion"));
			} catch (JSONException e1) {
				m.setCompatibleVersion(0);
			}
			try {
				m.setDescription(j.getString("description"));
			} catch (JSONException e1) {
				m.setDescription("This plug-in has no description.");
			}
			try {
				m.setIdentifier(j.getString("identifier"));
			} catch (JSONException e) {
				Console.log(LogType.Error, "PluginLoader.loadManifest",
						f.toString() + " doesn't specify an identifier.");
				e.printStackTrace();
				return null;
			}
			try {
				m.setLicense(j.getString("license"));
			} catch (JSONException e) {
				m.setLicense("");
			}
			try {
				m.setLongInfoText(j.getString("longInfoText"));
			} catch (JSONException e) {
				m.setLongInfoText("<h1>This plug-in has no description.</h1>");
			}
			try {
				m.setMaintainer(j.getString("maintainer"));
			} catch (JSONException e) {
				m.setMaintainer("");
			}
			try {
				m.setName(j.getString("name"));
			} catch (JSONException e) {
				m.setName("no name");
			}
			try {
				m.setUpdateSite(j.getString("updateSide"));
			} catch (JSONException e) {
			}
			try {
				m.setWebsite(j.getString("website"));
			} catch (JSONException e) {
			}
			try {
				{
					JSONArray a = j.getJSONArray("dependancys");
					ArrayList<String> l = new ArrayList<String>();
					for (int i = 0; i < a.length(); i++) {
						try {
							l.add(a.getString(i));
						} catch (Exception e) {
							Console.log(LogType.Warning, "PluginLoader.loadManifest",
									f.toString() + " [" + i + "]: unable to load dependency.");
						}
					}
					m.setDependancys(l);
				}
			} catch (Exception e) {
				m.setDependancys(new ArrayList<String>());
			}
			try {
				{
					JSONArray a = j.getJSONArray("incompatiblePlugins");
					ArrayList<String> l = new ArrayList<String>();
					for (int i = 0; i < a.length(); i++) {
						try {
							l.add(a.getString(i));
						} catch (Exception e) {
							Console.log(LogType.Warning, "PluginLoader.loadManifest",
									f.toString() + " [" + i + "]: unable to load incompatible plug-in.");
						}
					}
					m.setIncompatiblePlugins(l);
				}
			} catch (Exception e) {
				m.setIncompatiblePlugins(new ArrayList<String>());
			}
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			Console.log(LogType.Error, "PluginLoader.loadManifest", "An error occured: ");
			e.printStackTrace();
		}
		return m;
	}

	/**
	 * This method calculates the order in which the plug-ins need to be loaded.
	 * The only thing required is to load all the returned plug-ins in the order
	 * given by the array.
	 * 
	 * @param topLevelFolder
	 *            The top level folder where all plug-ins used to calculate the
	 *            tree are located.
	 * @throws IOException
	 *             This method throws an IOException if it isn't able of
	 *             handling the required IO.
	 * @throws UnsolvedDependencyException
	 *             This method throws an UnsolvedDependencyException if it can't
	 *             calculate the order in which the plug-ins need to be loaded.
	 * @see org.technikradio.node.engine.plugin.UnsolvedDependencyException
	 *      UnsolvedDependencyException (for further information)
	 * @return An array of the plug-ins to load in the correct order.
	 */
	public static final Manifest[] calculateDependencys(String topLevelFolder)
			throws IOException, UnsolvedDependencyException {
		ArrayList<Manifest> mfFiles = new ArrayList<Manifest>();
		// load all manifests inside the folder
		{

			File dir = new File(topLevelFolder);
			String[] content = dir.list();
			for (String child : content) {
				File f = new File(topLevelFolder + File.separator + child);
				if (f.isDirectory()) {
					try {
						mfFiles.add(loadManifest(f));
					} catch (Exception e) {
						Console.log(LogType.Warning, "PluginLoader.calculateDependencies", "Couldn't load plug-in: ");
						e.printStackTrace();
					}
				}
			}
		}
		// Sort the order
		ArrayList<Manifest> a = new ArrayList<Manifest>();
		solve(mfFiles, a);
		return a.toArray(new Manifest[a.size()]);
	}

	/**
	 * This method is used to sort the order in which the plug-ins have to be
	 * loaded.
	 * 
	 * @param toSolve
	 *            An ArrayList containing the manifests that need to be sorted.
	 * @param solved
	 *            An empty ArrayList in which the plug-ins will be sorted.
	 * @throws UnsolvedDependencyException
	 *             This exception gets thrown when the plug-ins are unsortable.
	 */
	public static final void solve(ArrayList<Manifest> toSolve, ArrayList<Manifest> solved)
			throws UnsolvedDependencyException {
		while (toSolve.size() > 0) {
			int moved = 0;
			ArrayList<Manifest> toRemove = new ArrayList<Manifest>();
			for (Manifest m : toSolve)
				if (depsSolved(m, solved)) {
					solved.add(m);
					toRemove.add(m);
					moved++;
				}
			for (Manifest m : toRemove)
				toSolve.remove(m);
			if (moved == 0 && toSolve.size() > 0)
				throw new UnsolvedDependencyException(
						UnsolvedDependencyException.DEPENDENCY_LOOP + ": \n" + getListString(toSolve));
		}
	}

	/**
	 * This method constructs a string from an ArrayList containing manifests.
	 * 
	 * @param list
	 *            The list containing the manifests.
	 * @return The generated string.
	 */
	private static final String getListString(ArrayList<Manifest> list) {
		StringBuilder sb = new StringBuilder();
		sb.append("The following plug-ins have unresolved dependencies:");
		for (Manifest m : list) {
			sb.append("\n");
			sb.append(m.getIdentifier());
		}
		return sb.toString();
	}

	/**
	 * This method checks the dependencies of a given manifest.
	 * 
	 * @param m
	 *            The manifest to check.
	 * @param solved
	 *            The ArrayList containing the solved dependencies.
	 * @return True if all dependencies are solved or otherwise false.
	 */
	private static final boolean depsSolved(Manifest m, ArrayList<Manifest> solved) {
		if (m.getDependencies().size() == 0)
			return true;
		for (String d : m.getDependencies()) {
			boolean found = false;
			for (Manifest dependency : solved)
				if (dependency.getIdentifier().equals(d)) {
					found = true;
					break;
				}
			if (!found)
				return false;
		}
		return true;
	}

	/**
	 * This method is used to get all jar files recursively inside a folder.
	 * 
	 * @param pathToInspect
	 *            The path that should be searched.
	 * @return The URL's pointing to the jar files inside that folder and its
	 *         sub folders.
	 */
	private static URL[] doBatchTest(File pathToInspect) {
		ArrayList<URL> a = new ArrayList<URL>();
		{
			String[] content = pathToInspect.list();
			for (String child : content) {
				File f = new File(pathToInspect.getAbsolutePath() + File.separator + child);
				if (f.isDirectory()) {
					try {
						URL[] m = doBatchTest(f);
						for (URL u : m) {
							if(u != null)
								a.add(u);
						}
					} catch (Exception e) {
						Console.log(LogType.Warning, "PluginLoader.doBatchTest", "Couldn't load jar: ");
						e.printStackTrace();
					}
				} else if(f.isFile()){
					if(getFileExtension(f.getAbsolutePath()).equals(".jar")){
						try {
							a.add(f.toURI().toURL());
							if(Main.DEBUG_MODE)
								try {
									Console.log(LogType.Information, "PluginLoader.doBatchTest", "Adding " + f.getCanonicalPath() + " to list of jar files.");
								} catch (IOException e) {
									Console.log(LogType.Warning, "PluginLoader.doBatchTest", "Failed to print debug message:");
									e.printStackTrace();
								}
						} catch (MalformedURLException e) {
							Console.log(LogType.Warning, "PluginLoader.doBatchTest", "Couldn't load jar: ");
							e.printStackTrace();
						}
					}
				}
			}
		}
		return a.toArray(new URL[a.size()]);
	}

	private static Object getFileExtension(String f) {
		String e = "";

		int i = f.lastIndexOf('.');
		if (i > 0) {
		    e = f.substring(i+1);
		}
		return e;
	}
}
