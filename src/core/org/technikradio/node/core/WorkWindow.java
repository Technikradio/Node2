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
package org.technikradio.node.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.technikradio.node.engine.event.Event;
import org.technikradio.node.engine.event.EventRegistry;
import org.technikradio.node.engine.event.EventResponder;
import org.technikradio.node.engine.plugin.WorkFile;
import org.technikradio.node.engine.plugin.ui.Window;
import org.technikradio.node.engine.plugin.ui.WindowOrientation;

/**
 * This class handles a window desired to be the one that contains the working
 * elements.
 * 
 * @author doralitze
 *
 */
public class WorkWindow {

	private final Window w;
	private final Tree tree;
	private WorkFile file;

	/**
	 * Use this constructor in order to construct a new instance of this class.
	 */
	public WorkWindow() {
		w = new Window();
		tree = new Tree(w.getContainer(WindowOrientation.LEFT_TRAY), SWT.VIRTUAL | SWT.BORDER);
		tree.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		{
			EventResponder<Composite> er = new EventResponder<Composite>();
			Event e = new Event(Identifiers.WORK_WINDOW_CREATING_EVENT, null, er);
			EventRegistry.raiseEvent(e, true);
		}
	}

	/**
	 * Use this method in order to retrive the current work file.
	 * 
	 * @return the current work file
	 */
	public WorkFile getWorkFile() {
		return file;
	}

	/**
	 * Use this method in order to set the current work file.
	 * 
	 * @param file
	 *            the file to set
	 */
	public void setWorkFile(WorkFile file) {
		this.file = file;
	}
}
