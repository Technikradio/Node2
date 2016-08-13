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

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;

/**
 * This class represents a window. Plug-ins can add their content to it.
 * 
 * @author doralitze
 *
 */
public class Window {

	private Shell shell;
	private CoolBar toolC;
	private Composite bottomC;
	private Composite topC;
	private ScrolledComposite centerC;
	private ScrolledComposite leftC;
	private ScrolledComposite rightC;
	private Sash[] sashes;

	/**
	 * This constructor initializes a new instance.
	 * @param title The title to use for the new window created.
	 */
	public Window(String title) {
		super();
		shell = new Shell(DisplayFactory.getDisplay());
		shell.setText(title);
		shell.setSize(250, 250);
		
		getShell().addListener(SWT.Close, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				internalClose();
			}});
		
		Rectangle bds = shell.getDisplay().getBounds();

		Point p = shell.getSize();

		int nLeft = (bds.width - p.x) / 2;
		int nTop = (bds.height - p.y) / 2;

		shell.setBounds(nLeft, nTop, p.x, p.y);
		
		final FormLayout form = new FormLayout ();
		shell.setLayout (form);
		
		
		toolC = new CoolBar(shell, SWT.HORIZONTAL);
		FormData toolData = new FormData();
		toolData.top = new FormAttachment(0,0);
		toolData.left = new FormAttachment(0,0);
		toolData.right = new FormAttachment(0,0);
		toolC.setLayoutData(toolData);
		
		leftC = new ScrolledComposite(shell, SWT.V_SCROLL);
		topC = new Composite(shell, SWT.None);
		centerC = new ScrolledComposite(shell, SWT.V_SCROLL + SWT.H_SCROLL);
		rightC = new ScrolledComposite(shell, SWT.V_SCROLL);
		
		bottomC = new Composite(shell, SWT.None);
		FormData bottomData = new FormData();
		bottomData.bottom = new FormAttachment(0,0);
		bottomData.left = new FormAttachment(0,0);
		bottomData.right = new FormAttachment(0,0);
		toolC.setLayoutData(bottomData);
		
		sashes = new Sash[3];
		sashes[0] = new Sash(shell, SWT.VERTICAL);
		sashes[1] = new Sash(shell, SWT.VERTICAL);
		sashes[2] = new Sash(shell, SWT.HORIZONTAL);
		
		FormData leftData = new FormData();
		leftData.top = new FormAttachment(toolC, 1);
		leftData.left = new FormAttachment(0,0);
		leftData.right = new FormAttachment(sashes[0], 0);
		leftData.bottom = new FormAttachment(sashes[2], 2);
		leftC.setLayoutData(leftData);
		
		FormData sash0Data = new FormData();
		sash0Data.top = new FormAttachment(toolC, 0);
		sash0Data.left = new FormAttachment(leftC, 0);
		sash0Data.bottom = new FormAttachment(sashes[2], 0);
		sashes[0].setLayoutData(sash0Data);
		
		FormData topData = new FormData();
		topData.top = new FormAttachment(toolC, 0);
		topData.left = new FormAttachment(sashes[0], 0);
		topData.right = new FormAttachment(sashes[1], 0);
		topData.bottom = new FormAttachment(centerC, 0);
		topC.setLayoutData(topData);
		
		FormData sash1Data = new FormData();
		sash1Data.top = new FormAttachment(toolC, 0);
		sash1Data.right = new FormAttachment(rightC, 0);
		sash1Data.bottom = new FormAttachment(sashes[2], 0);
		sashes[1].setLayoutData(sash1Data);
		
		FormData centerData = new FormData();
		centerData.bottom = new FormAttachment(sashes[2], 0);
		centerData.left = new FormAttachment(sashes[0], 0);
		centerData.right = new FormAttachment(sashes[1], 0);
		centerData.top = new FormAttachment(topC, 0);
		centerC.setLayoutData(centerData);
		
		FormData rightData = new FormData();
		rightData.top = new FormAttachment(toolC, 1);
		rightData.right = new FormAttachment(0,0);
		rightData.left = new FormAttachment(sashes[1], 0);
		rightData.bottom = new FormAttachment(sashes[2], 2);
		rightC.setLayoutData(rightData);
	}

	/**
	 * This method is used to set the dimensions of the window.
	 * 
	 * @param width
	 *            The width to set.
	 * @param height
	 *            The height to set
	 */
	public void setSize(int width, int height) {
		shell.setSize(width, height);
	}
	
	/**
	 * Use this method in order to manipulate the underlying shell.
	 * Use the methods of the window class in order to add UI elements.
	 * @return The shell of this window.
	 */
	public Shell getShell(){
		return shell;
	}
	
	/**
	 * Use this method to open the window.
	 */
	public void open(){
		shell.open();
	}
	
	/**
	 * Position the window inside the middle of the monitor.
	 */
	public void center(){
		Monitor primary = DisplayFactory.getDisplay().getPrimaryMonitor();
	    Rectangle monitorBounds = primary.getBounds();
	    Rectangle windowBounds = shell.getBounds();
	    int posX = monitorBounds.x + (monitorBounds.width - windowBounds.width) / 2;
	    int posY = monitorBounds.y + (monitorBounds.height - windowBounds.height) / 2;
	    shell.setLocation(posX, posY);
	}
	
	/**
	 * This method closes the window.
	 */
	public void close(){
		internalClose();
		shell.dispose();
	}

	/**
	 * This method invokes all things that need to be done in order to gentelly close this window.
	 */
	private void internalClose() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Use this method to access the different containers of this window.
	 * 
	 * NOTE: It's highly recommended to pack every component that should go inside the right tray inside a ExpandBar
	 * @param position The container to request.
	 * @return The requested container.
	 */
	public Composite getContainer(WindowOrientation position){
		switch(position){
		case BOTTOM:
			return bottomC;
		case CENTER:
			return centerC;
		case LEFT_TRAY:
			return leftC;
		case RIGHT_TRAY:
			return rightC;
		case TOOLBAR:
			return toolC;
		case TOP:
			return topC;
		default:
			return null;
		
		}
	}
	

}
