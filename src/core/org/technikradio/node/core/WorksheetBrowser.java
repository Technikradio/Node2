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
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.technikradio.node.engine.action.Application;
import org.technikradio.node.engine.plugin.ui.Colors;
import org.technikradio.node.engine.plugin.ui.Window;
import org.technikradio.node.engine.plugin.ui.WindowOrientation;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * This class is used to display a work sheet browser.
 * It stores its recent location inside APPDATA.
 * @author doralitze
 *
 */
public final class WorksheetBrowser {

	private final Window w;
	private boolean first = false;
	
	public WorksheetBrowser(){
		w = new Window("Worksheetbrowser");
		w.setSize(500, 300);
		w.getShell().addListener(SWT.Close, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				if(!isFirst())
					return;
				Console.log(LogType.StdOut, this, "Aborted worksheet selection. Closing app.");
				Application.close();
			}});
		w.center();
		{
			w.getContainer(WindowOrientation.BOTTOM).setBackground(Colors.RED);
			w.getContainer(WindowOrientation.CENTER).setBackground(Colors.BLUE);
			w.getContainer(WindowOrientation.LEFT_TRAY).setBackground(Colors.BLACK);
			w.getContainer(WindowOrientation.RIGHT_TRAY).setBackground(Colors.GREEN);
			w.getContainer(WindowOrientation.TOP).setBackground(Colors.YELLOW);
			Label l1 = new Label(w.getContainer(WindowOrientation.BOTTOM), SWT.None);
			l1.setText("Bottom");
		}
		{
			Label l1 = new Label(w.getContainer(WindowOrientation.CENTER), SWT.None);
			l1.setText("center");
		}
		{
			Label l1 = new Label(w.getContainer(WindowOrientation.LEFT_TRAY), SWT.None);
			l1.setText("left");
		}
		{
			Label l1 = new Label(w.getContainer(WindowOrientation.RIGHT_TRAY), SWT.None);
			l1.setText("right");
		}
		{
			Label l1 = new Label(w.getContainer(WindowOrientation.TOP), SWT.None);
			l1.setText("top");
			System.out.println("p");
		}
		w.open();
		printBounds(w.getContainer(WindowOrientation.CENTER).getBounds());
		printBounds(w.getContainer(WindowOrientation.TOOLBAR).getBounds());
		
	}

	private void printBounds(Rectangle bounds) {
		Console.log(LogType.Information, this, "Bounds { X=" + bounds.x + " Y=" + bounds.y + " width=" + bounds.width + " height=" + bounds.height + " }" );
	}

	/**
	 * Use this method in order to check if this dialog is displayed at the launch of node.
	 * @return the first flag
	 */
	public boolean isFirst() {
		return first;
	}

	/**
	 * Set the first flag to true if this instance is displayed at the start of node.
	 * @param first the first flag to set
	 */
	public void setFirst(boolean first) {
		this.first = first;
	}
}
