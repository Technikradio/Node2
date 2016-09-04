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

import java.net.URI;

/**
 * This class represents a data source where the plug-ins load and save all of
 * their data. The class determinates where and how the data is stored so the
 * application will only see this data adapter and doesn't need to care about
 * the files.
 * 
 * NOTE: Please override the Object.toString method in order to have a
 * displayable name.
 * 
 * @author doralitze
 * 
 */
public abstract class DataSource {

	private String identifier = "";
	private String name = "";
	private String description = "";
	private URI lastLoadedWorkFile = null;

	/**
	 * This is the most basic constructor. It only sets the identifier. The
	 * identifier should be set at all time.
	 * 
	 * @param identifier
	 *            The identifier used to identify the data source
	 */
	public DataSource(String identifier) {
		super();
		this.identifier = identifier;
		this.name = identifier;
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * This method will get called if the user clicks on save or any other thing
	 * invokes a save event. Note that the engine makes no difference between
	 * 'save' and 'save as'. It is up to the plugin to determinate what to do.
	 * 
	 * @param uri
	 *            to save to.
	 * @param file
	 *            The work file to save.
	 * @return true if the save was successful or false otherwise
	 */
	public abstract boolean save(URI uri, WorkFile file);

	/**
	 * This method gets called when the plugin should load the data. The URI can
	 * be taken from the load dialog or from recent locations.
	 * 
	 * @param uri
	 *            to load from
	 * @return The loaded file or if it failed null
	 */
	public abstract WorkFile load(URI uri);

	/**
	 * This method gets called when the user clicks on the 'open database'
	 * button.<br/>
	 * Note that a class implementing this method should call the
	 * {@link org.technikradio.node.plugin.DataSource#setLastLoadedWorkFile(URI) }
	 * method before it is about to load the data due to classes like the
	 * {@link org.technikradio.core.WorksheetBrowser} class relying on it.
	 * 
	 * @return the URI that the user selected.
	 * @see org.technikradio.node.event.BasicEvents#WORK_FILE_LOADED To get
	 *      further understanding about the event topic.
	 */
	public abstract URI showResourceOpenDialog();

	/**
	 * This method gets called when a specific data object whant's to be stored.
	 * NOTE that this method is desired to store only the requested data object
	 * and not the entire work file.
	 * 
	 * @param o
	 *            The object that want's to be stored.
	 * @param f The corresponding work file of the object.
	 * @return true if the save was successful otherwise false
	 */
	public abstract boolean saveDataObject(DataObject w, WorkFile f);

	/**
	 * Use this method to tell node if this data source is a local ore a remote
	 * one.
	 * 
	 * @return true if the data source is on a remote host or otherwise false.
	 */
	public abstract boolean isRemoteDataSource();

	/**
	 * Use this method in order to open a dialog capable of creating a new work
	 * file.
	 */
	public abstract void showNewWorkFileDialog();

	/**
	 * This method saves the entire work file.
	 * @param f The work file to be saved.
	 */
	public abstract void saveWorkFile(WorkFile f);
	
	/**
	 * Use this method to get the human readable name of this data source.
	 * 
	 * @return the localized name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Use this method to set the displayed name of this data source.
	 * 
	 * @param name
	 *            the localized name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Use this method in order to retrieve the URI of the last loaded work
	 * file.
	 * 
	 * @return the last loaded work file location
	 */
	public URI getLastLoadedWorkFile() {
		return lastLoadedWorkFile;
	}

	/**
	 * Use this method in order to set the location of the last loaded work file
	 * or invoke it if a newly created work file is saved for the first time.
	 * NOTE that this method also raises the corresponding events.
	 * 
	 * @param lastLoadedWorkFile
	 *            the location of the last loaded work file to set
	 */
	public void setLastLoadedWorkFile(URI lastLoadedWorkFile) {
		this.lastLoadedWorkFile = lastLoadedWorkFile;
	}

	/**
	 * Use this method to get the human readable description of this data source.
	 * @return the description of this data source
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Use this method in order to set a human readable description of this data source.
	 * @param description the description value to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
