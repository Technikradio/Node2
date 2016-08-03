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
package org.technikradio.node.engine.event;

import org.technikradio.node.engine.action.Main;
import org.technikradio.node.engine.plugin.ui.DisplayFactory;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * This EventHandler is used to process the events on the UI thread.
 * 
 * @author doralitze
 *
 */
public abstract class UIEventHandler implements EventHandler {

	/**
	 * Implement this method in order to process something inside the UI thread.
	 * 
	 * @param e
	 *            The event that is given by the EventHandler.
	 */
	public abstract void execute(Event e);

	private boolean sync = false;

	/**
	 * Use this method to check if the Event should be processed asynchronous
	 * inside the UI thread.
	 * 
	 * @return the sync flag
	 */
	public boolean isSync() {
		return sync;
	}

	/**
	 * Use this method to enable or disable synchronous UI execution.
	 * 
	 * @param sync
	 *            the sync flag to set
	 */
	public void setSync(boolean sync) {
		this.sync = sync;
	}

	@Override
	public void handleEvent(Event e) {
		if(Main.DEBUG_MODE)
			Console.log(LogType.Information, this, "Executing on UI thread. (sync=" + isSync() + ")");
		if (isSync())
			DisplayFactory.getDisplay().syncExec(new Runnable() {

				@Override
				public void run() {
					execute(e);
				}

			});
		else
			DisplayFactory.getDisplay().asyncExec(new Runnable() {

				@Override
				public void run() {
					execute(e);
				}

			});
	}

}
