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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * This class handles the application interaction.
 * @author doralitze
 */
public class Application {
	
	/**
	 * This path points to the directory where node stores local user dependent stuff like settings.
	 */
	public static final String LOCAL_APPDATA_FOLDER;
	public static final String CURRENT_OPERATING_SYSTEM;
	/**
	 * This variable indicates the availability of the APPDATA folder.
	 */
	public static final boolean LOCAL_APPDATA_FOLDER_AVAIABLE;
	
	static {
		CURRENT_OPERATING_SYSTEM  = (System.getProperty("os.name")).toUpperCase();
		{
			String currentBaseDir = "";
			String dirPrefix = "";
			if (CURRENT_OPERATING_SYSTEM.contains("WIN")){
				currentBaseDir = System.getenv("APPDATA");
			} else {
				currentBaseDir = System.getProperty("user.home");
				if(CURRENT_OPERATING_SYSTEM.contains("MAC")){
					currentBaseDir += "/Library/Application Support";
				} else {
					dirPrefix = ".";
				}
			}
			LOCAL_APPDATA_FOLDER = currentBaseDir + File.separator + dirPrefix + "Node";
		}
		{
			boolean pathexists = false;
			if(!(new File(LOCAL_APPDATA_FOLDER).exists())){
				try {
					Files.createDirectory(Paths.get(LOCAL_APPDATA_FOLDER));
					pathexists = true;
				} catch (IOException e) {
					Console.log(LogType.Error, "Application", "Can't create APPDATA folder:");
					e.printStackTrace();
				}
			} else {
				pathexists = true;
			}
			LOCAL_APPDATA_FOLDER_AVAIABLE = pathexists;
		}
	}

	/**
	 * This method gets called to initialize stuff like the look and feel
	 */
	protected static void setupUIBehaviour(){
		
	}
	
	/**
	 * Use this method to invoke the crash routines. This method
	 * uses 1 as the default error code.
	 * @param reason The reason why the application crashed
	 */
	public static void crash(Object reason){
		crash(reason, 1);
	}
	
	/**
	 * Use this method to invoke the crash routines.
	 * @param reason The reason why the application crashed
	 * @param code The error code to use
	 */
	public static void crash(Object reason, int code){
		Console.log(LogType.Error, "Application", "The application is about to crash");
		//TODO implement the rest of the method
		System.err.println(reason.toString());
		System.exit(code);
	}
}
