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

import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * This class starts all the loading of plugins.
 * @author doralitze
 */
public class Main {
	
	private static String APP_HOME;
	/**
	 * This method returns the location of the installation
	 * @return The path
	 */
	protected static String getAppHome(){
		return APP_HOME;
	}
	/**
	 * This method is the main entry point for node.
	 * @param args The arguments provided by the VM.
	 */
	public static void main(String[] args) {
		if(args.length < 1){
			Application.crash("Launched without launch variables", 1);
		}
		APP_HOME = args[0];
		Console.log(LogType.StdOut, "UpstartAgent", "Starting node...");
		try {
			Application.setupApp();
			Console.log(LogType.StdOut, "UpstartAgent", "Successfully started node...");
		} catch (Exception e) {
			Console.log(LogType.Error, "UpstartAgent", "Error during initialzation.");
			e.printStackTrace();
			Application.crash(e, CrashCodes.ERROR_DURING_INITIALIZATION);
		}
	}

}
