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

import java.net.URI;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.technikradio.node.engine.action.Application;
import org.technikradio.node.engine.action.Main;
import org.technikradio.node.engine.event.BasicEvents;
import org.technikradio.node.engine.event.EventHandler;
import org.technikradio.node.engine.event.EventRegistry;
import org.technikradio.node.engine.plugin.DataSource;
import org.technikradio.node.engine.plugin.PluginRegistry;
import org.technikradio.node.engine.plugin.WorkFile;
import org.technikradio.node.engine.plugin.ui.NotificationBox;
import org.technikradio.node.engine.plugin.ui.Window;
import org.technikradio.node.engine.plugin.ui.WindowOrientation;
import org.technikradio.node.engine.resources.Localisation;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * This class is used to display a work sheet browser. It stores its recent
 * location inside APPDATA.
 * 
 * @author doralitze
 *
 */
public final class WorksheetBrowser {

	private final Window w;
	private ExpandBar dataSourceBar;
	private ExpandItem localExpandItem;
	private ExpandItem remoteExpandItem;
	private List localList;
	private List remoteList;
	private Label infoLabel;
	private Label descriptionLabel;
	private ArrayList<DataSource> ldsa = new ArrayList<DataSource>();
	private ArrayList<DataSource> rdsa = new ArrayList<DataSource>();
	private DataSource currentSelectedDS = null;
	private boolean first = false;
	private EventHandler openedHandler;

	public WorksheetBrowser() {
		w = new Window("Worksheetbrowser");
		w.getShell().addListener(SWT.Close, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				internalClose();
				if (!isFirst())
					return;
				Console.log(LogType.StdOut, this, "Aborted worksheet selection. Closing app.");
				Application.close();
			}
		});
		w.center();
		{

			infoLabel = new Label(w.getContainer(WindowOrientation.BOTTOM), SWT.BORDER);
			infoLabel.setText("Bottom-Info");
			descriptionLabel = new Label(w.getContainer(WindowOrientation.RIGHT_TRAY), SWT.WRAP);
		}
		{
			Button newButton = new Button(w.getContainer(WindowOrientation.TOP), SWT.PUSH);
			newButton.setText("New");
			newButton.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event arg0) {
					if (getCurrentSelectedDS() != null) {
						Console.log(LogType.StdOut, "org.technikradio.node.core.WorksheetBrowser.newButton",
								"Clicked on NewButton. DS: " + getCurrentSelectedDS().getIdentifier());
						getCurrentSelectedDS().showNewWorkFileDialog();
					} else {
						Console.log(LogType.StdOut, "org.technikradio.node.core.WorksheetBrowser.newButton",
								"Clicked on NewButton. No data source.");
					}
				}
			});
		}
		{
			Button openButton = new Button(w.getContainer(WindowOrientation.TOP), SWT.PUSH);
			openButton.setText("Open");
			openButton.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event arg0) {
					try {
						if (getCurrentSelectedDS() != null) {
							Console.log(LogType.StdOut, "org.technikradio.node.core.WorksheetBrowser.openButton",
									"Clicked on OpenButton. DS: " + getCurrentSelectedDS().getIdentifier());
							URI uri = getCurrentSelectedDS().showResourceOpenDialog(w);
							if (uri != null) {
								WorkFile wf = getCurrentSelectedDS().load(uri);
								// TODO implement window loading when branch got
								// merged
							}

						} else {
							Console.log(LogType.StdOut, "org.technikradio.node.core.WorksheetBrowser.openButton",
									"Clicked on OpenButton. No data source.");
						}
					} catch (Exception e) {
						Console.log(LogType.Warning, this,
								"While trying to load a work file the desired data source crashed:");
						e.printStackTrace();
						NotificationBox.notify(
								Localisation.getString("org.technikradio.node.core.WorksheetBrowser.opencrash",
										"While trying to load a work file the desired data source crashed:") + "\n"
										+ e.getLocalizedMessage()
										+ Localisation
												.getString("org.technikradio.node.core.WorksheetBrowser.opencrash2",
														"\n See the console output fur further information"),
								Localisation.getString("org.technikradio.node.core.WorksheetBrowser.opencrashtitle",
										"An error occured"),
								SWT.ICON_ERROR | SWT.OK);
					}
				}
			});
		}
		{
			dataSourceBar = new ExpandBar(w.getContainer(WindowOrientation.LEFT_TRAY), SWT.V_SCROLL);
			localList = new List(dataSourceBar, SWT.MULTI);
			localExpandItem = new ExpandItem(dataSourceBar, SWT.NONE, 0);
			localExpandItem.setText("Local data sources");
			localExpandItem.setHeight(localList.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			localExpandItem.setControl(localList);
			localExpandItem.setExpanded(true);
			// localExpandItem.setImage(image);

			remoteList = new List(dataSourceBar, SWT.MULTI);
			remoteExpandItem = new ExpandItem(dataSourceBar, SWT.NONE, 1);
			remoteExpandItem.setText("Remote data sources");
			remoteExpandItem.setHeight(localList.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			remoteExpandItem.setControl(remoteList);
			// localExpandItem.setImage(image);

			dataSourceBar.setSpacing(8);
		}
		fillDSS();
		w.open();
		w.setSize(750, 400);
		openedHandler = new EventHandler() {

			@Override
			public void handleEvent(org.technikradio.node.engine.event.Event e) {
				close();
			}
		};
		EventRegistry.addEventHandler(BasicEvents.WORK_FILE_LOADED, openedHandler);
	}

	private void fillDSS() {
		boolean first = true;

		remoteList.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				findItem();
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				findItem();
			}
		});
		localList.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				findItem();
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				findItem();
			}
		});

		for (DataSource ds : PluginRegistry.getAllRegisteredDataSources()) {
			if (Main.isDEBUG_MODE())
				Console.log(LogType.Information, this,
						"Adding data source: " + ds.getIdentifier() + "; " + ds.getName());

			if (ds.isRemoteDataSource()) {
				remoteList.add(ds.getName());
				rdsa.add(ds);
			} else {
				localList.add(ds.getName());
				ldsa.add(ds);
			}

			if (first && !ds.isRemoteDataSource()) {
				first = false;
				setCurrentSelectedDS(ds);
				localList.setSelection(0);
			}
		}
		remoteExpandItem.setHeight(remoteList.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		localExpandItem.setHeight(localList.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
	}

	private void findItem() {
		for (int i : localList.getSelectionIndices()) {
			if (ldsa.get(i) != getCurrentSelectedDS()) {
				setCurrentSelectedDS(ldsa.get(i));
				localList.deselectAll();
				remoteList.deselectAll();
				localList.select(i);
			}
		}
		for (int i : remoteList.getSelectionIndices()) {
			if (rdsa.get(i) != getCurrentSelectedDS()) {
				setCurrentSelectedDS(rdsa.get(i));
				localList.deselectAll();
				remoteList.deselectAll();
				remoteList.select(i);
			}
		}
	}

	/**
	 * Use this method in order to check if this dialog is displayed at the
	 * launch of node.
	 * 
	 * @return the first flag
	 */
	public boolean isFirst() {
		return first;
	}

	/**
	 * Set the first flag to true if this instance is displayed at the start of
	 * node.
	 * 
	 * @param first
	 *            the first flag to set
	 */
	public void setFirst(boolean first) {
		this.first = first;
	}

	/**
	 * Use this method in order to get the current selected data source.
	 * 
	 * @return the current selected data source
	 */
	public DataSource getCurrentSelectedDS() {
		return currentSelectedDS;
	}

	/**
	 * Use this method to set the current selected data source.
	 * 
	 * @param ds
	 *            the current selected data source to set
	 */
	private void setCurrentSelectedDS(DataSource ds) {
		this.currentSelectedDS = ds;
		infoLabel.setText(getCurrentSelectedDS().getName());
		descriptionLabel.setText(getCurrentSelectedDS().getDescription());
	}

	private void internalClose() {
		Console.log(LogType.StdOut, this, "Removing event handler from list");
		EventRegistry.removeEventHandler(BasicEvents.WORK_FILE_LOADED, openedHandler);
	}

	/**
	 * Use this method in order to close the worksheet browser.
	 */
	public void close() {
		w.close();
	}
}
