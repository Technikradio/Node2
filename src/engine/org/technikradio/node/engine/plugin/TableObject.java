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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.technikradio.node.engine.Permission;
import org.technikradio.node.engine.plugin.settings.Settings;
import org.technikradio.node.engine.resources.Localisation;

/**
 * This class is a sub class of the data object class and designed to be
 * represented inside a table containing all the positions.
 * 
 * @author doralitze
 *
 */
public class TableObject extends DataObject implements Calculatable {

	private static boolean pre = Boolean.parseBoolean(Settings.get("org.technikradio.node.core.putCurrencyBeforeNumber", "true"));
	
	private String unit = "$";
	private final ArrayList<Row> rows;
	private Table table;

	// #REGION constructors

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

	// #ENDREGION

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.technikradio.node.engine.plugin.Calculatable#getSum()
	 */
	@Override
	public long getSum() {
		Row r = rows.get(rows.size() - 1);
		return r.getSum();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.technikradio.node.engine.plugin.DataObject#onOpen()
	 */
	@Override
	public void onOpen(Composite parent) {
		table = new Table(parent, SWT.VIRTUAL | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		TableColumn idcolumn = new TableColumn(table, SWT.NONE);
		idcolumn.setText(Localisation.getString("org.technikradio.node.core.IDColumnText", "ID"));
		idcolumn.setToolTipText(Localisation.getString("org.technikradio.node.core.IDColumnTTText",
				"This field contains the ids of the positions."));
		idcolumn.setWidth(50);
		TableColumn dateColumn = new TableColumn(table, SWT.NONE);
		dateColumn.setText(Localisation.getString("org.technikradio.node.core.DateColumnText", "Date"));
		dateColumn.setToolTipText(Localisation.getString("org.technikradio.node.core.DateColumnTTText",
				"This field contains the dates of the positions."));
		dateColumn.setWidth(100);
		TableColumn descColumn = new TableColumn(table, SWT.NONE);
		descColumn.setText(Localisation.getString("org.technikradio.node.core.DESColumnText", "Description"));
		descColumn.setToolTipText(Localisation.getString("org.technikradio.node.core.DESColumnTTText",
				"This field contains the descriptions of the positions."));
		descColumn.setWidth(200);
		TableColumn ccColumn = new TableColumn(table, SWT.NONE);
		ccColumn.setText(Localisation.getString("org.technikradio.node.core.CostCentreColumnText", "Cost Centre"));
		ccColumn.setToolTipText(Localisation.getString("org.technikradio.node.core.CostCentreColumnTTText",
				"This field contains the cost centres of the positions."));
		ccColumn.setWidth(70);
		TableColumn valueColumn = new TableColumn(table, SWT.NONE);
		valueColumn.setText(Localisation.getString("org.technikradio.node.core.ValueColumnText", "Value"));
		valueColumn.setToolTipText(Localisation.getString("org.technikradio.node.core.ValueColumnTTText",
				"This field contains the values of the positions"));
		valueColumn.setWidth(100);
		TableColumn sumColumn = new TableColumn(table, SWT.NONE);
		sumColumn.setText(Localisation.getString("org.technikradio.node.core.SumColumnText", "Sum"));
		sumColumn.setToolTipText(Localisation.getString("org.technikradio.node.core.SumColumnTTText",
				"This field contains the sum until the given position"));
		sumColumn.setWidth(100);
		table.addListener(SWT.SetData, new Listener() {
			public void handleEvent(Event event) {
				TableItem item = (TableItem) event.item;
				int index = event.index;
				Row r = rows.get(index);
				item.setText(0, Long.toString(r.getId()));
				item.setText(1, r.getDate());
				item.setText(2, r.getDescription());
				item.setText(3, r.getCostCenter());
				item.setText(4, Row.getDString(r.getValue(), pre, unit));
				item.setText(5, Row.getDString(r.getSum(), pre, unit));
			}
		});
		table.setItemCount(rows.size());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.technikradio.node.engine.plugin.DataObject#onClose()
	 */
	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		// Check object saved? here
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.technikradio.node.engine.plugin.DataObject#onAddToWorkSheet(org.
	 * technikradio.node.engine.plugin.WorkFile)
	 */
	@Override
	public void onAddToWorkSheet(WorkFile wf) {
		unit = wf.getCurrency().getSymbol();
	}

	/**
	 * Use this method in order to get a range of row elements to display.
	 * 
	 * @param start
	 *            The first row to include.
	 * @param end
	 *            The first row to not include anymore.
	 * @return The generated array.
	 */
	public Row[] getRows(int start, int end) {
		int amount = end - start;
		if (amount < 1)
			throw new RuntimeException("There are no arrays possible that contain less than 0 elements.");
		Row rs[] = new Row[amount];
		for (int i = start; i < end; i++)
			rs[i - start] = rows.get(i);
		return rs;
	}

	/**
	 * Use this method in order to add a row to the document.
	 * 
	 * @param r
	 *            The row to add.
	 */
	public synchronized void addRow(Row r) {
		long lastValue = 0;
		if (rows.size() > 0)
			lastValue = rows.get(rows.size() - 1).getSum();
		r.setSum(lastValue + r.getValue());
		rows.add(r);
	}

	/**
	 * Use this method in order to remove a row.
	 * 
	 * @param r
	 *            The row to remove.
	 */
	public void removeRow(Row r) {
		rows.remove(r);
	}

	/**
	 * Use this method in order to get the amount of stored positions inside
	 * this document.
	 * 
	 * @return The amount of stored rows.
	 */
	public int getLength() {
		return rows.size();
	}

	/**
	 * Use this method in order to recalculate the sum cache. NOTE this
	 * operation is quite expensive. DO NOT PERFORM THIS ON THE UI THREAD!
	 */
	public void recalculateCache() {
		Row lr = null;
		{
			Row r = rows.get(0);
			r.setSum(r.getValue());
			lr = r;
		}
		
		for(int i = 1; i < rows.size(); i++){
			Row r = rows.get(i);
			r.setSum(lr.getSum() + r.getValue());
			lr = r;
		}
	}

}
