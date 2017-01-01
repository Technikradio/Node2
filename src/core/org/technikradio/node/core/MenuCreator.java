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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.technikradio.node.engine.action.Application;
import org.technikradio.node.engine.event.Event;
import org.technikradio.node.engine.event.EventHandler;
import org.technikradio.node.engine.event.EventRegistry;
import org.technikradio.node.engine.event.EventResponder;
import org.technikradio.node.engine.plugin.ui.DisplayFactory;
import org.technikradio.node.engine.plugin.ui.Window;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * The purpose of this class is to create the most basic file entries for opening work windows.
 * @author doralitze
 */
public class MenuCreator {

	public static void addEvents(){
		EventRegistry.addEventHandler(Identifiers.WORK_WINDOW_CREATING_EVENT, new EventHandler(){

			@Override
			public void handleEvent(Event e) {
				DisplayFactory.getDisplay().syncExec(new Runnable() {
					public void run() {
						Console.log(LogType.StdOut, this, "Creating menu entries.");
						Window w = (Window) e.getEventHint();
						Menu menuBar = new Menu(w.getShell(), SWT.BAR);
						MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
				        cascadeFileMenu.setText("&File");
				        
				        //Add file menu entries
				        Menu fileMenu = new Menu(w.getShell(), SWT.DROP_DOWN);
				        cascadeFileMenu.setMenu(fileMenu);
				        
				        MenuItem openFileItem = new MenuItem(fileMenu, SWT.PUSH);
				        openFileItem.setText("&Open");
				        
				        
				        openFileItem.addListener(SWT.Selection, event-> {
				        	DisplayFactory.getDisplay().syncExec(new Runnable() {
								public void run() {
									WorksheetBrowser wsb = new WorksheetBrowser();
									wsb.setFirst(false);
									Console.log(LogType.Information, this, "Opened worksheetbrowser.");
								}
							});
				        });
				        
				        MenuItem exitFileItem = new MenuItem(fileMenu, SWT.PUSH);
				        exitFileItem.setText("&Exit");
				        
				        
				        exitFileItem.addListener(SWT.Selection, event-> {
				        	DisplayFactory.getDisplay().syncExec(new Runnable() {
								public void run() {
									Application.close();
								}
							});
				        });
				        
				        w.getShell().setMenuBar(menuBar);
				        
				        EventResponder<Composite> er = new EventResponder<Composite>();
						Event l = new Event(Identifiers.WORK_WINDOW_MENU_CREATED, null, er);
						l.setEventHint(w);
						EventRegistry.raiseEvent(l, true);
						Console.log(LogType.StdOut, this, "Created menu entries.");

					}
				});
		}});
	}
}
