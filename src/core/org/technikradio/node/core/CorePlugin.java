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
package org.technikradio.node.core;

import org.technikradio.node.engine.CurrencyCode;
import org.technikradio.node.engine.RuntimeRelevant;
import org.technikradio.node.engine.event.BasicEvents;
import org.technikradio.node.engine.event.Event;
import org.technikradio.node.engine.event.EventHandler;
import org.technikradio.node.engine.event.EventRegistry;
import org.technikradio.node.engine.plugin.Plugin;
import org.technikradio.node.engine.plugin.ui.DisplayFactory;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * This plug-in will provide the basic functionality of node.
 * 
 * @author doralitze
 * 
 */
@RuntimeRelevant
public class CorePlugin extends Plugin {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.technikradio.node.engine.plugin.Plugin#load()
	 */
	@Override
	public void load() {
		{
			EventHandler eh = new EventHandler() {

				public void handleEvent(Event e) {
					DisplayFactory.getDisplay().syncExec(new Runnable() {
						public void run() {
							WorksheetBrowser wsb = new WorksheetBrowser();
							wsb.setFirst(true);
							Console.log(LogType.Information, this, "Opened worksheetbrowser.");
						}
					});
				}

			};
			if (!EventRegistry.addEventHandler(BasicEvents.APPLICATION_LOADED_EVENT, eh)) {
				Console.log(LogType.Information, this,
						"There are other plugin listening on the app start, registered before the core plugin.");
			}
			MenuCreator.addEvents();
		}
		CurrencyBrowser.setDefaultCurrency(CurrencyCode.EUR);
		Console.log(LogType.StdOut, this, "Successfully loaded core plug-in.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.technikradio.node.engine.plugin.Plugin#unload()
	 */
	@Override
	public void unload() {
		
	}

}
