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

import java.awt.SplashScreen;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Display;
import org.technikradio.node.engine.event.BasicEvents;
import org.technikradio.node.engine.event.Event;
import org.technikradio.node.engine.event.EventHandler;
import org.technikradio.node.engine.event.EventRegistry;
import org.technikradio.node.engine.event.EventResponder;
import org.technikradio.node.engine.plugin.Command;
import org.technikradio.node.engine.plugin.CommandNotFoundException;
import org.technikradio.node.engine.plugin.CommandRegistry;
import org.technikradio.node.engine.plugin.ui.DisplayFactory;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * This class starts all the loading of plug-ins.
 * @author doralitze
 */
public class Main {
	
	protected static boolean DEBUG_MODE;
	private static String APP_HOME = ".";
	private static boolean appRunning = true;
	private static Display d;
	private static Thread commandThread;
	/**
	 * This method returns the location of the installation
	 * @return The path
	 */
	protected static String getAppHome(){
		if(APP_HOME == null)
			Console.log(LogType.Error, "UpstartAgent", "Apphome wasn't specified.");
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
		
		DEBUG_MODE = Application.isDevelopmentVersion();
		Console.log(LogType.StdOut, "UpstartAgent", "Launched with the following args:");
		for(int i = 0; i < args.length; i++)
			System.out.println("\t[" + i + "] " + args[i]);
		Console.log(LogType.StdOut, "UpstartAgent", "Starting node...");
		processFurtherCommands(args);
		try {
			Application.setupApp();
			Console.log(LogType.StdOut, "UpstartAgent", "Successfully started node...");
			EventRegistry.raiseEvent(new Event(BasicEvents.APPLICATION_LOADED_EVENT, null, new EventResponder<Object>()));
		} catch (Exception e) {
			Console.log(LogType.Error, "UpstartAgent", "Error during initialzation.");
			e.printStackTrace();
			Application.crash(e, CrashCodes.ERROR_DURING_INITIALIZATION);
		}
		{
			EventHandler eh = new EventHandler(){

				@Override
				public void handleEvent(Event e) {
					appRunning = false;
				}};
			EventRegistry.addEventHandler(BasicEvents.APPLICATION_CLOSING_EVENT, eh);
			EventRegistry.addEventHandler(BasicEvents.APPLICATION_CRASHED_EVENT, eh);
			commandThread = new Thread(new Runnable(){

				@Override
				public void run() {
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					CommandRegistry.addCommand("exit", new Command(){

						@Override
						public boolean execute(String[] args, PrintStream outputStream) {
							Event ev = new Event(BasicEvents.APPLICATION_CLOSING_EVENT, null, new EventResponder<Boolean>());
							EventRegistry.raiseEvent(ev, true);
							return false;
						}});
					CommandRegistry.addCommandHelp("exit", "Use this command to exit Node.", "This command shuts Node down.");
					while(appRunning){
						try {
							String[] cmp = br.readLine().split(" ");
							String command = cmp[0];
							String[] args = new String[cmp.length - 1];
							for(int i = 1; i < cmp.length; i++)
								args[i - 1] = cmp[i];
							CommandRegistry.call(command, args);
						} catch (IOException e) {
							Console.log(LogType.Warning, this, "Reached end of input stream.");
							e.printStackTrace();
							return;
						} catch (CommandNotFoundException e) {
							Console.log(LogType.Error, this, "The execution of the desired command failed:");
							e.printStackTrace();
						}
					}
				}
				@Override
				public String toString(){
					return commandThread.getName();
					}
				});
			commandThread.setDaemon(true);
			commandThread.setPriority(5);
			commandThread.setName("STDIN-COMMAND-THREAD");
		}
		commandThread.start();
		if(SplashScreen.getSplashScreen() != null){
			SplashScreen.getSplashScreen().close();
		} else {
			Console.log(LogType.Warning, "UpsartAgent", "There wasn't a splash screen provided by the start script.");
		}
		d = DisplayFactory.getDisplay();
		while(appRunning){
			try {
				care();
			} catch (Exception e) {
				Console.log(LogType.Warning, "UIThread", "There was an exception thrown inside the UI thread: " + e.getLocalizedMessage());
			}
		}
	}
	
	/**
	 * This method does all the UI sanity.
	 * @throws SWTException if the UI has a problem.
	 */
	private static void care() throws SWTException{
		if(d.isDisposed()){
			appRunning = false;
			Event e = new Event(BasicEvents.APPLICATION_CLOSING_EVENT, null, new EventResponder<Object>());
			EventRegistry.raiseEvent(e);
			return;
		}
		while(!d.readAndDispatch()){}
		d.sleep();
	}
	
	/**
	 * This method is used to process further commands.
	 */
	private static void processFurtherCommands(String[] args){
		for(int i = 1; i < args.length; i++){
			if(args[i].startsWith("-UP")){
				Hints.addPluginUpdateHine(args[i].substring(3, args[i].length()));
			}
		}
	}
	
	/**
	 * Use this method to detect if the main/UI thread is still running.
	 * @return True if the UI thread still runns or otherwise false.
	 */
	public static boolean isAppRunning(){
		return appRunning;
	}
	
	/**
	 * Use this method to check if the application is running in debug mode.
	 * @return the DEBUG_MODE flag.
	 */
	public static boolean isDEBUG_MODE() {
		return DEBUG_MODE;
	}

}
