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

import org.technikradio.node.engine.resources.Localisation;

/**
 * This class represents a table entry.
 * 
 * @author doralitze
 *
 */
public class Row {
	
	private static final String comma = Localisation.getString("org.technikradio.engine.plugin.row.comma", ".");

	private long value;
	private long id;
	private long sum;
	private String description;
	private String costCenter;
	private String date;
	

	/**
	 * This constructor creates a new instance of the row element class.
	 * 
	 * @param value
	 *            The value that this class will store. Note that the work
	 *            sheets currency will be used.
	 * @param id
	 *            The ID that will be assigned to this row.
	 * @param description
	 *            The description of this row element.
	 * @param costCenter
	 *            The cost center of this row element
	 * @param date
	 *            The date of this position.
	 */
	public Row(long value, long id, String description, String costCenter, String date) {
		super();
		this.value = value;
		this.id = id;
		this.description = description;
		this.costCenter = costCenter;
		this.date = date;
	}

	/**
	 * Use this method in order to retrieve the value of this position. NOTE
	 * that the the work sheets unit will be used and that $1.05 will be stored
	 * as 102 due to a missing long float data type.
	 * 
	 * @return the value of this position
	 */
	public long getValue() {
		return value;
	}

	/**
	 * Use this method in order to retrieve the assigned ID of this position.
	 * 
	 * @return the id of this position.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Use this method in order to retrieve the description of this position.
	 * 
	 * @return the description of this position
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Use this method in order to retrieve the cost center of this position
	 * 
	 * @return the cost center of this position
	 */
	public String getCostCenter() {
		return costCenter;
	}

	/**
	 * Use this method in order to retrieve the assigned date of this position.
	 * 
	 * @return the date of this position.
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Use this method in order to set the value of this position.
	 * 
	 * @param value
	 *            the value of this position to set (simply ignore the . from
	 *            the value)
	 */
	public void setValue(long value) {
		this.value = value;
	}

	/**
	 * Use this method in order to assign an ID to this Row.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Use this method in order to set the description of this position.
	 * 
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Use this method in order to set the cost center of this position.
	 * 
	 * @param costCenter
	 *            the cost center to set
	 */
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	/**
	 * Use this method in order to set a date for this position.
	 * 
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * Use this function in order to retrieve an exact copy of this row.
	 * @return The new copy of this row.
	 */
	public Row copy(){
		return new Row(this.value, this.id, this.description, this.costCenter, this.date);
	}

	/**
	 * Use this method in order to get the sum data of this row. Note that the sum data is computed at runtime.
	 * @return the sum of this row object
	 */
	protected long getSum() {
		return sum;
	}

	/**
	 * Use this method in order to set the sum of this row object.
	 * @param sum the sum to set
	 */
	protected void setSum(long sum) {
		this.sum = sum;
	}

	/**
	 * This method formats the number correctly in order to be human readable.
	 * @param value The number to format.
	 * @return The generated string.
	 */
	public static String getDString(long value) {
		//TODO fix
		boolean neg = false;
		long v;
		if(value < 0){
			neg = true;
			v = value * (-1);
		} else {
			v = value;
		}
		long pre = v / 100;
		long post = v - pre;
		StringBuilder sb = new StringBuilder();
		if(neg)
			sb.append("-");
		sb.append(Long.toString(pre));
		sb.append(comma);
		if(post < 10)
			sb.append("0");
		sb.append(Long.toString(post));
		return sb.toString();
	}

}
