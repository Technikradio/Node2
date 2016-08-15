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
import org.eclipse.swt.layout.RowLayout;
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
	
	private static final int limit = 20;

	private Shell shell;
	private CoolBar toolC;
	private Composite bottomC;
	private Composite topC;
	private ScrolledComposite centerC;
	private ScrolledComposite leftC;
	private ScrolledComposite rightC;
	private Sash[] sashes;
	
	private FormData fmMiddleData;
	private FormData fmBottomData;
	private FormData fmToolBarData;

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
		{
			fmToolBarData = new FormData();
			fmToolBarData.top = new FormAttachment(0,0);
			fmToolBarData.left = new FormAttachment(0,0);
			fmToolBarData.right = new FormAttachment(0,0);
			toolC.setLayoutData(fmToolBarData);
		}
		sashes = new Sash[3];
		Composite middleCom = new Composite(shell, SWT.None);
		fmToolBarData.bottom = new FormAttachment(middleCom, 0);
		{
			
			middleCom.setLayout(new FormLayout());
			leftC = new ScrolledComposite(middleCom, SWT.V_SCROLL);
			sashes[0] = new Sash(middleCom, SWT.VERTICAL);
			Composite mmc = new Composite(middleCom, SWT.None);
			
			{
				FormData fm = new FormData();
				fm.top = new FormAttachment(0, 0);
				fm.left = new FormAttachment(0, 0);
				fm.right = new FormAttachment(sashes[0], 0);
				fm.bottom = new FormAttachment(0, 0);
				leftC.setLayoutData(fm);
			}
			
			{
				FormData fm = new FormData();
				fm.top = new FormAttachment(0, 0);
				fm.left = new FormAttachment(leftC, 0);
				fm.right = new FormAttachment(mmc, 0);
				fm.bottom = new FormAttachment(0, 0);
				sashes[0].setLayoutData(fm);
				sashes[0].addListener (SWT.Selection, new Listener() {
					
					@Override
					public void handleEvent(Event e) {
						Rectangle sashRect = sashes[0].getBounds ();
						Rectangle shellRect = shell.getClientArea ();
						int right = shellRect.width - sashRect.width - limit;
						e.x = Math.max (Math.min (e.x, right), limit);
						if (e.x != sashRect.x)  {
							fm.left = new FormAttachment (0, e.x);
							shell.layout ();
						}
					}
				});
			}
			
			{
				RowLayout rl = new RowLayout();
				rl.type = SWT.VERTICAL;
				mmc.setLayout(rl);
				topC = new Composite(mmc, SWT.None);
				centerC = new ScrolledComposite(mmc, SWT.V_SCROLL + SWT.H_SCROLL);
			}
			
			sashes[1] = new Sash(middleCom, SWT.VERTICAL);
			rightC = new ScrolledComposite(middleCom, SWT.V_SCROLL);
			
			{
				FormData fm = new FormData();
				fm.top = new FormAttachment(0, 0);
				fm.left = new FormAttachment(sashes[0], 0);
				fm.right = new FormAttachment(sashes[1], 0);
				fm.bottom = new FormAttachment(0, 0);
				mmc.setLayoutData(fm);
			}
			
			{
				FormData fm = new FormData();
				fm.top = new FormAttachment(0, 0);
				fm.left = new FormAttachment(mmc, 0);
				fm.right = new FormAttachment(rightC, 0);
				fm.bottom = new FormAttachment(0, 0);
				sashes[1].setLayoutData(fm);
			}
			
			{
				FormData fm = new FormData();
				fm.top = new FormAttachment(0, 0);
				fm.left = new FormAttachment(sashes[1], 0);
				fm.right = new FormAttachment(0, 0);
				fm.bottom = new FormAttachment(0, 0);
				rightC.setLayoutData(fm);
			}
			
			{
				fmMiddleData = new FormData();
				fmMiddleData.top = new FormAttachment(toolC);
				fmMiddleData.right = new FormAttachment(0,0);
				fmMiddleData.left = new FormAttachment(0,0);
				middleCom.setLayoutData(fmMiddleData);
			}
			
		}
		sashes[2] = new Sash(shell, SWT.HORIZONTAL);
		bottomC = new Composite(shell, SWT.None);
		fmMiddleData.bottom = new FormAttachment(sashes[2],0);
		{
			fmBottomData = new FormData();
			fmBottomData.top = new FormAttachment(sashes[2],0);
			fmBottomData.left = new FormAttachment(0,0);
			fmBottomData.right = new FormAttachment(0,0);
			fmBottomData.bottom = new FormAttachment(0,0);
			bottomC.setLayoutData(fmBottomData);
		}
		shell.pack();
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
		fmToolBarData.width = width;
		fmToolBarData.height = 25;
		fmMiddleData.height = (int) ((height / 100) * 75);
		fmMiddleData.width = width;
		fmBottomData.width = width;
		fmBottomData.height = (int) ((height / 100) * 25) - 25;
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
