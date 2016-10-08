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

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.technikradio.node.engine.action.Application;
import org.technikradio.node.engine.plugin.PluginRegistry;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * This class represents a window. Plug-ins can add their content to it.
 *
 * @author doralitze
 *
 */
public class Window {
	
	private static ArrayList<Window> openWindows = new ArrayList<Window>();

	private Shell shell;
	private CoolBar toolC;
	private Composite bottomC;
	private Composite topC;
	private Composite centerC;
	private Composite leftC;
	private Composite rightC;
	private ColorPalette theme;

	/**
	 * This constructor creates a new window with an empty title.
	 */
	public Window() {
		this("");
	}

	/**
	 * This constructor initializes a new instance.
	 *
	 * @param title
	 *            The title to use for the new window created.
	 */
	public Window(String title) {
		super();
		shell = new Shell(DisplayFactory.getDisplay());
		shell.setText(title);
		shell.setSize(250, 250);
		shell.setLayout(new FillLayout());
		SashForm mc = new SashForm(shell, SWT.VERTICAL);

		getShell().addListener(SWT.Close, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				internalClose();
			}
		});

		FillLayout topLayout = new FillLayout();
		topLayout.type = SWT.VERTICAL;
		mc.setLayout(topLayout);
		mc.setBackground(Colors.GRAY_DESIGN.getTextColor());
		toolC = new CoolBar(mc, SWT.HORIZONTAL);
		SashForm middleC = new SashForm(mc, SWT.HORIZONTAL);
		middleC.setBackground(Colors.GRAY_DESIGN.getTextColor());
		// leftC = new ScrolledComposite(middleC, SWT.VERTICAL);
		leftC = new Composite(middleC, SWT.NONE);
		{
			SashForm cc = new SashForm(middleC, SWT.VERTICAL);
			cc.setBackground(Colors.GRAY_DESIGN.getTextColor());
			topC = new Composite(cc, SWT.NONE);
			centerC = new Composite(cc, SWT.NONE);
			int[] w = { 35, 65 };
			cc.setWeights(w);
		}
		rightC = new Composite(middleC, SWT.NONE);
		{
			int[] mmr = { 25, 60, 15 };
			middleC.setWeights(mmr);
		}
		bottomC = new Composite(mc, SWT.NONE);

		{
			RowLayout rl = new RowLayout();
			bottomC.setLayout(rl);
		}
		{
			RowLayout rl = new RowLayout();
			topC.setLayout(rl);
		}
		{
			RowLayout rl = new RowLayout();
			bottomC.setLayout(rl);
		}
		{
			FillLayout fl = new FillLayout();
			fl.type = SWT.VERTICAL;
			centerC.setLayout(fl);
		}
		{
			FillLayout fl = new FillLayout();
			fl.type = SWT.VERTICAL;
			leftC.setLayout(fl);
		}
		{
			FillLayout fl = new FillLayout();
			fl.type = SWT.VERTICAL;
			rightC.setLayout(fl);
		}
		{
			int[] w = { 5, 80, 15 };
			mc.setWeights(w);
		}
		shell.addListener(SWT.SELECTED, new Listener() {
			//TODO fix
			@Override
			public void handleEvent(Event arg0) {
				bringToForeground();
			}
		});
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
		shell.setSize(height, width);
	}

	/**
	 * Use this method in order to manipulate the underlying shell. Use the
	 * methods of the window class in order to add UI elements.
	 *
	 * @return The shell of this window.
	 */
	public Shell getShell() {
		return shell;
	}

	/**
	 * Use this method to open the window.
	 */
	public void open() {
		shell.pack();
		shell.open();
		openWindows.add(this);
	}

	/**
	 * Position the window inside the middle of the monitor.
	 */
	public void center() {
		Monitor primary = DisplayFactory.getDisplay().getPrimaryMonitor();
		Rectangle monitorBounds = primary.getBounds();
		Rectangle windowBounds = shell.getBounds();
		int posX = monitorBounds.x + (monitorBounds.width - windowBounds.width) / 2;
		int posY = monitorBounds.y + (monitorBounds.height - windowBounds.height) / 2;
		shell.setLocation(posX, posY);
	}

	/**
	 * This method closes the window. Note that the color theme won't be
	 * disposed by this method.
	 */
	public void close() {
		internalClose();
		shell.dispose();
		openWindows.remove(this);
	}

	/**
	 * This method invokes all things that need to be done in order to genteelly
	 * close this window.
	 */
	private void internalClose() {
	}

	/**
	 * Use this method to access the different containers of this window.
	 *
	 * NOTE: It's highly recommended to pack every component that should go
	 * inside the right tray inside a ExpandBar
	 *
	 * @param position
	 *            The container to request.
	 * @return The requested container.
	 */
	public Composite getContainer(WindowOrientation position) {
		switch (position) {
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

	/**
	 * Use this method in order to set the current active theme to a different
	 * one. NOTE: that the closing method of this class won't dispose the
	 * ColorPalette object.
	 *
	 * @param p
	 *            The color theme to set.
	 */
	public void setColorTheme(ColorPalette p) {
		if (p == null)
			return;
		this.theme = p;
		toolC.setBackground(p.getAccentColor());
		bottomC.setBackground(p.getAccentColor());
		topC.setBackground(p.getMainBackground());
		centerC.setBackground(p.getMainBackground());
		leftC.setBackground(p.getSecondaryBackground());
		rightC.setBackground(p.getSecondaryBackground());
	}

	/**
	 * Use this method to get the current active color theme.
	 *
	 * @return The currently applied color theme.
	 */
	public ColorPalette getColorTheme() {
		return this.theme;
	}

	/**
	 * This method sets the title of the window.
	 *
	 * @param newTitle
	 *            The new title to set
	 * @return The old title of this window.
	 */
	public String setTitle(String newTitle) {
		String st = shell.getText();
		shell.setText(newTitle);
		return st;
	}

	/**
	 * Override this method when your implementation of an window needs to do
	 * stuff when the window was brought to the front. Don not call this method on your own!
	 */
	public void bringToForeground() {
		if(Application.isDevelopmentVersion())
			Console.log(LogType.Information, this, "Window was brought to the front.");
		PluginRegistry.setCurrentOpenWindow(this);
	}
	
	/**
	 * Use this method in order to retrieve an array of all open windows.
	 * @return A list containing all open windows.
	 */
	public static Window[] getOpenWindows(){
		return openWindows.toArray(new Window[openWindows.size()]);
	}
}
