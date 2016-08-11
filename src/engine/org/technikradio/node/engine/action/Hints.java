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

import java.util.ArrayList;

/**
 * This class contains startup hints.
 * @author doralitze
 *
 */
public class Hints {

	private static ArrayList<String> updatedPlugins;
	
	/**
	 * This method initializes all hint fields.
	 */
	static {
		updatedPlugins = new ArrayList<String>();
		
	}
	
	/**
	 * This method is used to add plug-ins that should update their content. 
	 * @param identifier The identifier of the plug-in that was updated.
	 */
	protected static void addPluginUpdateHine(String identifier){
		updatedPlugins.add(identifier);
	}
	
	/**
	 * Use this method to check if a certain plug-in was updated doing the start of Node.
	 * @param identifier The identifier of the plug-in to check for.
	 * @return True if the plug-in was updated doing the start of Node or otherwise false.
	 */
	public static boolean wasUpdated(String identifier){
		return updatedPlugins.contains(identifier);
	}

}
