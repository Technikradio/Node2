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
package org.technikradio.node.engine.plugin;

import java.net.URI;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;

/**
 * This class represents a work sheet containing all sub objects.
 * 
 * @author doralitze
 *
 */
public class WorkFile {

	/**
	 * This field contains the global currency for the work file. The currency
	 * property is global due the the issue of currency conversion.
	 */
	private Currency currency;
	private ArrayList<DataObject> childs;
	private URI location;

	/**
	 * This constructor instantiates a new work sheet. If you're not sure what
	 * currency to use, get the local default currency by calling
	 * <code>Currency.getInstance(Locale.getDefault())</code>
	 * 
	 * @param c
	 *            The currency to use.
	 */
	public WorkFile(Currency c) {
		super();
		setCurrency(c);
		childs = new ArrayList<DataObject>();
	}

	/**
	 * This method returns all underlying DataObjects.
	 * 
	 * @return the objects
	 */
	public Iterator<DataObject> getChildObjects() {
		return childs.iterator();
	}

	/**
	 * This getter returns the global currency for the work file. The currency
	 * property is global due the the issue of currency conversion. This may
	 * change in the future but for the moment it isn't required to support
	 * multiple currencies inside a work sheet.
	 * 
	 * @return the currency
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * This sets the currency to a new value.
	 * 
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	/**
	 * Use this method to add child objects to this data source.
	 * 
	 * @param o
	 *            The child to add.
	 */
	public void addChild(DataObject o) {
		childs.add(o);
		o.onAddToWorkSheet(this);
	}

	/**
	 * This method is used to remove children from the work sheet.
	 * 
	 * @param o
	 *            The child to remove
	 * @return true if it successfully removed the child otr otherwise false.
	 */
	public boolean removeChild(DataObject o) {
		if (!childs.contains(o))
			return false;
		childs.remove(o);
		return true;
	}

	/**
	 * This method returns the DataObject matching the identifier.
	 * 
	 * @param identifier
	 *            The identifier to look for.
	 * @return The corresponding DataObject or <code>null</code> if it can't
	 *         find one.
	 */
	public DataObject getChild(String identifier) {
		for (DataObject o : childs) {
			if (o.getIdentifier().equals(identifier))
				return o;
		}
		return null;
	}

	/**
	 * Use this method to check where the work file needs to be saved.
	 * @return the location of the work file
	 */
	public URI getLocation() {
		return location;
	}

	/**
	 * Use this method to change where the work file should be saved.
	 * @param location the location of the work file to set
	 */
	public void setLocation(URI location) {
		this.location = location;
	}

	/**
	 * Use this method in order to get the top level data objects.
	 * @return An array containing the top level data objects.
	 */
	public DataObject[] getChildArray() {
		return childs.toArray(new DataObject[childs.size()]);
	}

	/**
	 * Use this method in order to retrieve the number of top level data objects.
	 * @return The number of data objects.
	 */
	public int getChildCount() {
		return childs.size();
	}
}
