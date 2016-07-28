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
import java.util.ArrayList;

/**
 * This class is used to load a plugin based on the given manifest file.
 * 
 * @author doralitze
 *
 */
public class PluginLoader {

	/**
	 * This method is used to load a plugin. Note that it doesn't automatically
	 * handles the dependency loading. Use
	 * {@link org.technikradio.node.engine.plugin.PluginLoader#calculateDependencys(String)}
	 * first.
	 * 
	 * @param manifest
	 *            The manifest required to load the plugin.
	 * @return true if the plugin is compatible and loaded or false if the
	 *         plugin is incompatible.
	 * @throws IOException
	 *             This exception gets thrown when the method is unable of
	 *             managing the IO required to load the plug-in.
	 */
	public static final boolean loadPlugin(Manifest manifest) throws IOException {
		// TODO implement
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
		// TODO implement
		return null;
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
	 * @see {@link org.technikradio.node.engine.plugin.UnsolvedDependencyException}
	 *      for further information
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
					mfFiles.add(loadManifest(f));
				}
			}
		}
		// Sort the order
		ArrayList<Manifest> a = new ArrayList<Manifest>();
		solve(mfFiles, a);
		return a.toArray(new Manifest[a.size()]);
	}

	public static final void solve(ArrayList<Manifest> toSolve, ArrayList<Manifest> solved)
			throws UnsolvedDependencyException {
		while (toSolve.size() > 0) {
			int moved = 0;
			for (Manifest m : toSolve)
				if (depsSolved(m, solved)) {
					solved.add(m);
					toSolve.remove(m);
					moved++;
				}
			if (moved == 0)
				throw new UnsolvedDependencyException(UnsolvedDependencyException.DEPENDENCY_LOOP);
		}
	}

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
}
