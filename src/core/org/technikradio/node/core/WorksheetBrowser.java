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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.technikradio.node.engine.action.Application;
import org.technikradio.node.engine.plugin.DataSource;
import org.technikradio.node.engine.plugin.PluginRegistry;
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
	private Composite localComposite;
	private Composite remoteComposite;
	private DataSource currentSelectedDS = null;
	private boolean first = false;
	
	public WorksheetBrowser(){
		w = new Window("Worksheetbrowser");
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
			
			Label l1 = new Label(w.getContainer(WindowOrientation.BOTTOM), SWT.BORDER);
			l1.setText("Bottom-Info");
			
		}
		{
			Button newButton = new Button(w.getContainer(WindowOrientation.TOP), SWT.PUSH);
			newButton.setText("New");
			newButton.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event arg0) {
					if(currentSelectedDS != null)
						currentSelectedDS.showResourceOpenDialog();
				}});
		}
		{
			Button openButton = new Button(w.getContainer(WindowOrientation.TOP), SWT.PUSH);
			openButton.setText("Open");
		}
		{
			ExpandBar dataSourceBar = new ExpandBar(w.getContainer(WindowOrientation.LEFT_TRAY), SWT.V_SCROLL);
			localComposite = new Composite (dataSourceBar, SWT.NONE);
			GridLayout layout = new GridLayout ();
			layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
			layout.verticalSpacing = 10;
			localComposite.setLayout(layout);
			ExpandItem localExpandItem = new ExpandItem (dataSourceBar, SWT.NONE, 0);
			localExpandItem.setText("Local data sources");
			localExpandItem.setHeight(localComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			localExpandItem.setControl(localComposite);
			localExpandItem.setExpanded(true);
			//localExpandItem.setImage(image);
			
			remoteComposite = new Composite (dataSourceBar, SWT.NONE);
			layout = new GridLayout (2, false);
			layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
			layout.verticalSpacing = 10;
			remoteComposite.setLayout(layout);
			ExpandItem remoteExpandItem = new ExpandItem (dataSourceBar, SWT.NONE, 1);
			remoteExpandItem.setText("Remote data sources");
			remoteExpandItem.setHeight(localComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			remoteExpandItem.setControl(remoteComposite);
			//localExpandItem.setImage(image);
			
			dataSourceBar.setSpacing(8);
		}
		w.open();
		w.setSize(750, 400);
		fillDSS();
	}

	private void fillDSS() {
		int numLoc = 0;
		int numRem = 0;
		boolean first = true;
		for(DataSource ds : PluginRegistry.getAllRegisteredDataSources()){
			if(first && !ds.isRemoteDataSource()){
				first = false;
				currentSelectedDS = ds;
			}
			Console.log(LogType.Information, this, "Adding data source: " + ds.getIdentifier() + "; " + ds.getName());
			if(ds.isRemoteDataSource()){
				numRem++;
				Composite c = new Composite(remoteComposite, SWT.NONE);
				GridLayout layout = new GridLayout (2, false);
				layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
				layout.verticalSpacing = 10;
				c.setLayout(new FillLayout());
				c.setBackground(Colors.BLACK);
				if(numRem % 2 == 0){
					c.setBackground(Colors.GRAY_DESIGN.getTextColor());
				}
				Label l = new Label(c, SWT.BORDER);
				l.setText(ds.getName());
				Label l1 = new Label(w.getContainer(WindowOrientation.BOTTOM), SWT.BORDER);
				l1.setText(ds.getName());
				c.addListener(SWT.MouseDown, new Listener(){

					@Override
					public void handleEvent(Event arg0) {
						final DataSource d = ds;
						Console.log(LogType.StdOut, this, "Clicked. " + d.getIdentifier());
						currentSelectedDS = d;
					}
					
				});
			} else {
				numLoc++;
				Composite c = new Composite(localComposite, SWT.NONE);
				GridLayout layout = new GridLayout (2, false);
				layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
				layout.verticalSpacing = 10;
				c.setLayout(new FillLayout());
				c.setBackground(Colors.BLACK);
				if(numLoc % 2 == 0){
					c.setBackground(Colors.GRAY_DESIGN.getTextColor());
				}
				Label l = new Label(c, SWT.BORDER);
				l.setText(ds.getName());
				c.addListener(SWT.MouseDown, new Listener(){

					@Override
					public void handleEvent(Event arg0) {
						final DataSource d = ds;
						Console.log(LogType.StdOut, this, "Clicked. " + d.getIdentifier());
						currentSelectedDS = d;
					}
					
				});
			}
		}
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
