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

import org.eclipse.swt.widgets.Composite;

/**
 * This class represents an abstract data object
 * @author doralitze
 */
public abstract class DataObject {

	private String identifier;
	private String title;
	private BufferedImage icon;
	
	/**
	 * This is the most basic constructor. It only sets the identifier.
	 * The identifier should be set at all time.
	 * @param identifier The identifier used to identify the data object
	 */
	public DataObject(String identifier){
		this(identifier, "");
	}
	
	/**
	 * This constructor sets beside the identifier also the title of the data object.
	 * @param identifier The identifier used to identify the data object
	 * @param title The title displayed inside the tree of data objects
	 */
	public DataObject(String identifier, String title){
		super();
		this.identifier = identifier;
		this.setTitle(title);
		setIcon(null);
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the icon displayed inside the data object tree
	 */
	public BufferedImage getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(BufferedImage icon) {
		this.icon = icon;
	}
	
	public boolean save() throws DataNotYetLoadedException{
		DataSource ds = PluginRegistry.getCurrentActiveDataSource();
		if(ds == null)
			throw new DataNotYetLoadedException("Try to save into unaviable data yourself the next time!");
		return ds.saveDataObject(this);
	}
	
	/**
	 * This method gets called when the user opens the data object.
	 * @return an swt composite object to display the content of the data object
	 */
	public abstract Composite onOpen();
	
	/**
	 * This method gets called when the user closes the content of the
	 * DataObject.
	 */
	public abstract void onClose();

}
