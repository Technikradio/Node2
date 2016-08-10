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
package org.technikradio.node.engine.plugin.ui;

import org.eclipse.swt.widgets.Display;
import org.technikradio.node.engine.action.Application;
import org.technikradio.node.engine.action.CrashCodes;
import org.technikradio.node.engine.event.BasicEvents;
import org.technikradio.node.engine.event.Event;
import org.technikradio.node.engine.event.EventHandler;
import org.technikradio.node.engine.event.EventRegistry;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * This class is used to create an SWT display. It also handles the SWT event
 * loop.
 * 
 * @author doralitze
 *
 */
public class DisplayFactory {
	private static boolean initialized = false;
	private static Display d;
	private static Thread eventLoopThread;

	/**
	 * This class is designed to handle the SWT event loop.
	 * 
	 * @author doralitze
	 *
	 */
	private static class EventLoopHandler implements Runnable {

		private boolean apprunning = true;

		/**
		 * This constructor creates a new instance.
		 */
		public EventLoopHandler() {
			EventRegistry.addEventHandler(BasicEvents.APPLICATION_CLOSING_EVENT, new EventHandler() {

				/**
				 * This implements the abstract method of an event handler.
				 */
				@Override
				public void handleEvent(Event e) {
					apprunning = false;
				}
			});
			EventRegistry.addEventHandler(BasicEvents.APPLICATION_CRASHED_EVENT, new EventHandler() {

				/**
				 * This implements the abstract method of an event handler.
				 */
				@Override
				public void handleEvent(Event e) {
					apprunning = false;
				}
			});
		}

		/**
		 * This method is used to implement the runnable interface. Its point is
		 * to handle the events.
		 */
		@Override
		public void run() {
			Console.log(LogType.StdOut, this, "Starting SWT event loop.");
			
			try {
				while (apprunning) {
					System.out.println("p");
					new Runnable(){
						@Override
						public void run() {
							int i = 0;
							while(!d.readAndDispatch()){i++;System.out.println(i);}
							d.sleep();
						}}.run();
					}
			} catch (Exception e) {
				Console.log(LogType.Error, this, "The UI thread crashed.");
				e.printStackTrace();
				Application.crash(e, CrashCodes.UI_CRASH);
			} finally {
				Console.log(LogType.StdOut, this, "disassembling swt display adapter...");
				d.syncExec(new Runnable(){

					@Override
					public void run() {
						d.dispose();
					}});
			}
		}

		/**
		 * We're overwriting the toString method in order to support proper
		 * logging.
		 */
		@Override
		public String toString() {
			return "DisplayFactory.EventLoopHandler";
		}

	}

	/**
	 * Simply to all the behind the scenes stuff...
	 * Do not invoke this method outside the main thread. The engine handles this stuff.
	 */
	public static final void init() {
		if(initialized)
			throw new RuntimeException("DisplayFactory already initialized by main thread.");
		d = new Display();
		d.readAndDispatch();
		d.wake();
		eventLoopThread = new Thread(new EventLoopHandler());
		eventLoopThread.setPriority(8);
		eventLoopThread.setName("SWT-EVENT-LOOP-THREAD");
		eventLoopThread.start();
		//EventLoopHandler d = new EventLoopHandler();
		//d.run();
		initialized = true;
	}

	/**
	 * This method can be used to check if the UI system has already been initialized.
	 * @return The initialized flag. 
	 */
	public static final boolean isInitialized() {
		return initialized;
	}

	/**
	 * This method is used to get the shared display adapter to create UI
	 * elements.
	 * 
	 * @return The common display adapter.
	 */
	public static Display getDisplay() {
		return d;
	}
}
