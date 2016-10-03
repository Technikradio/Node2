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
package org.technikradio.node.engine.plugin;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.technikradio.node.engine.Permission;

/**
 * This class is a sub class of the data object class and designed to be represented inside a table containing all the positions.
 * @author doralitze
 *
 */
public class TableObject extends DataObject implements Calculatable {
	
	private final ArrayList<Row> rows;
	private Table table;

	//#REGION constructors
	
	/**
	 * This is the most basic constructor. It only sets the identifier of this
	 * DataObject. The identifier should be set at all time. Note that you're
	 * highly recommended to set the parent after constructing this object. If
	 * this is a top level DataObject you can leave it to null.
	 * 
	 * @param identifier
	 *            The identifier used to identify the data object
	 */
	public TableObject(String identifier) {
		super(identifier);
		rows = new ArrayList<Row>();
	}

	/**
	 * This is the most basic constructor. It only sets the identifier and the
	 * parent of this DataObject. The identifier should be set at all time.
	 * 
	 * @param parent
	 *            The parent of this DataObject
	 * @param identifier
	 *            The identifier used to identify the data object
	 */
	public TableObject(DataObject parent, String identifier) {
		super(parent, identifier);
		rows = new ArrayList<Row>();
	}

	/**
	 * This constructor sets beside the identifier and parent also the title of
	 * the data object.
	 * 
	 * @param parent
	 *            The parent of this DataObject
	 * @param identifier
	 *            The identifier used to identify the data object
	 * @param title
	 *            The title displayed inside the tree of data objects
	 */
	public TableObject(DataObject parent, String identifier, String title) {
		super(parent, identifier, title);
		rows = new ArrayList<Row>();
	}

	/**
	 * This constructor sets beside the identifier and parent also the title of
	 * the data object. Note that you're highly recommended to set the parent
	 * after constructing this object. If this is a top level DataObject you can
	 * leave it to null.
	 * 
	 * @param identifier
	 *            The identifier used to identify the data object
	 * @param title
	 *            The title displayed inside the tree of data objects
	 */
	public TableObject(String identifier, String title) {
		super(identifier, title);
		rows = new ArrayList<Row>();
	}

	/**
	 * This constructor creates a complete instance of a DataOject.
	 * 
	 * @param parent
	 *            The parent of this DataObject
	 * @param identifier
	 *            The identifier used to identify the data object
	 * @param title
	 *            The title displayed inside the tree of data objects
	 * @param p
	 *            The permissions of this DataObject (leave them to null in
	 *            order to use the same permissions as the parent object)
	 * @param icon
	 *            The icon of this DataObject to display inside the UI
	 */
	public TableObject(DataObject parent, String identifier, String title, Permission p, BufferedImage icon) {
		super(parent, identifier, title, p, icon);
		rows = new ArrayList<Row>();
	}
	
	//#ENDREGION

	/* (non-Javadoc)
	 * @see org.technikradio.node.engine.plugin.Calculatable#getSum()
	 */
	@Override
	public long getSum() {
		long sum = 0;
		for(Row w : rows){
			sum += w.getValue();
		}
		return sum;
	}

	/* (non-Javadoc)
	 * @see org.technikradio.node.engine.plugin.DataObject#onOpen()
	 */
	@Override
	public void onOpen(Composite parent) {
		table = new Table(parent, SWT.VIRTUAL | SWT.BORDER);
	}

	/* (non-Javadoc)
	 * @see org.technikradio.node.engine.plugin.DataObject#onClose()
	 */
	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		//Check object saved? here
	}

	/* (non-Javadoc)
	 * @see org.technikradio.node.engine.plugin.DataObject#onAddToWorkSheet(org.technikradio.node.engine.plugin.WorkFile)
	 */
	@Override
	public void onAddToWorkSheet(WorkFile wf) {

	}
	
	/**
	 * Use this method in order to get a range of row elements to display.
	 * @param start The first row to include.
	 * @param end The first row to not include anymore.
	 * @return The generated array.
	 */
	public Row[] getRows(int start, int end){
		int amount = end - start;
		if(amount < 1)
			throw new RuntimeException("There are no arrays possible that contain less than 0 elements.");
		Row rs[] = new Row[amount];
		for(int i = start; i < end; i++)
			rs[i - start] = rows.get(i);
		return rs;
	}
	
	/**
	 * Use this method in order to add a row to the document.
	 * @param r The row to add.
	 */
	public void addRow(Row r){
		rows.add(r);
	}
	
	/**
	 * Use this method in order to remove a row.
	 * @param r The row to remove.
	 */
	public void removeRow(Row r){
		rows.remove(r);
	}
	
	/**
	 * Use this method in order to get the amount of stored positions inside this document.
	 * @return The amount of stored rows.
	 */
	public int getLength(){
		return rows.size();
	}

}
