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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author doralitze
 * This class represents a kind of folder displayed inside the settings the of the settings dialog.
 */
public class SettingsTree extends SettingsObject implements Browsable, Updater{

	private ArrayList<SettingsObject> childreen;
	private UpdateListener ul;
	
	/**
	 * This constructor initializes an empty tree.
	 * @param identifier to use
	 */
	public SettingsTree(String identifier) {
		this(identifier, null);
	}
	
	/**
	 * This constructor initializes a new tree containing the SettingObject's provided by objects
	 * @param identifier to use
	 * @param objects to contain
	 */
	public SettingsTree(String identifier, SettingsObject[] objects) {
		super(identifier);
		childreen = new ArrayList<SettingsObject>();
		for(SettingsObject o : objects){
			childreen.add(o);
		}
	}

	@Override
	public Iterator<SettingsObject> getAllObjects() {
		return childreen.listIterator();
	}
	
	/**
	 * This adds an object to the current list
	 * @param o the object to add
	 */
	public void addObject(SettingsObject o){
		childreen.add(o);
		if(ul != null)
			ul.updateUI(true);
	}

	@Override
	public void registerUpdater(UpdateListener listener) {
		ul = listener;
	}
	
	/**
	 * This returns all child objects as an array
	 * @return the created array
	 */
	public SettingsObject[] getAllObjectsAsArray(){
		return (SettingsObject[]) childreen.toArray();
	}

}
