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

/**
 * @author doralitze
 * This class represents an abstract plugin
 */
public abstract class Plugin {
	
	private Manifest mainfest;
	protected boolean loaded = false;

	/**
	 * This constructor initializes a new plugin instance.
	 * @param m the manifest of the plugin to use
	 */
	protected Plugin(Manifest m) {
		super();
		this.setMainfest(m);
	}
	
	/**
	 * This method sets the loaded flag.
	 * This method gets called after the load() function
	 * returned.
	 */
	protected void setLoadedFlag(){
		loaded = true;
	}
	
	/**
	 * This method indicates if the plugin did successfully loaded or not.
	 * @return the loaded flag
	 */
	protected boolean isPluginLoaded(){
		return loaded;
	}

	/**
	 * @return the mainfest
	 */
	public Manifest getMainfest() {
		return mainfest;
	}

	/**
	 * @param mainfest the mainfest of the plugin to set
	 */
	protected void setMainfest(Manifest mainfest) {
		this.mainfest = mainfest;
	}
	
	/**
	 * This method gets called when the plugin should initialize itself
	 */
	public abstract void load();
	
	/**
	 * This method gets called before the application exits. Use this method to save all required things.
	 */
	public abstract void unload();

}
