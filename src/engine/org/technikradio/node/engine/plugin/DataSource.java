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
import java.util.Iterator;

/**
 * @author doralitze
 * This class represents a data source where the plugins load and save all of their data.
 * The class determinates where and how the data is stored so the application will only
 * see this data adapter and doesn't need to care about the files.
 */
public abstract class DataSource {

	private String identifier = "";
	
	/**
	 * This is the most basic constructor. It only sets the identifier.
	 * The identifier should be set at all time.
	 * @param identifier
	 */
	public DataSource(String identifier){
		super();
		this.identifier = identifier;
	}
	
	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * This method should return all underlying DataObjects.
	 * @return the objects
	 */
	public abstract Iterator<DataObject> getChildObjects();
	
	/**
	 * This method will get called if the user clicks on save
	 * or any other thing invokes a save event. Note that the
	 * engine makes no difference between 'save' and 'save as'.
	 * It is up to the plugin to determinate what to do.
	 * @param uri to save to.
	 * @return true if the save was successful or false otherwise
	 */
	public abstract boolean save(URI uri);
	
	/**
	 * This method gets called when the plugin should load the data.
	 * The URI can be taken from the load dialog or from recent locations.
	 * @param uri to load from
	 * @return true if it successfully opened the resource or otherwise false.
	 */
	public abstract boolean load(URI uri);
	
	/**
	 * This method gets called when the user clicks on the 'open database' button.
	 * @return the URI that the user selected.
	 */
	public abstract URI showResourceOpenDialog();
	
	/**
	 * This method gets called when a specific data object whant's to be stored.
	 * @param o The object that want's to be stored.
	 * @return true if the save was successful otherwise false
	 */
	public abstract boolean saveDataObject(DataObject o);
}
