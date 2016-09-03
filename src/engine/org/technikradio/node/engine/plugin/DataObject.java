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
import org.technikradio.node.engine.Permission;

/**
 * This class represents an abstract data object
 * @author doralitze
 */
public abstract class DataObject {

	private String identifier;
	private String title;
	private BufferedImage icon;
	private DataObject parent;
	private Permission permissions;
	
	/**
	 * This is the most basic constructor. It only sets the identifier of this DataObject.
	 * The identifier should be set at all time. Note that you're highly recommended to set
	 * the parent after constructing this object. If this is a top level DataObject you can leave it to null.
	 * @param identifier The identifier used to identify the data object
	 */
	public DataObject(String identifier){
		this(null, identifier, "");
	}
	
	/**
	 * This is the most basic constructor. It only sets the identifier and the parent of this DataObject.
	 * The identifier should be set at all time.
	 * @param parent The parent of this DataObject
	 * @param identifier The identifier used to identify the data object
	 */
	public DataObject(DataObject parent, String identifier){
		this(parent, identifier, "");
	}
	
	/**
	 * This constructor sets beside the identifier and parent also the title of the data object.
	 * @param parent The parent of this DataObject
	 * @param identifier The identifier used to identify the data object
	 * @param title The title displayed inside the tree of data objects
	 */
	public DataObject(DataObject parent, String identifier, String title){
		this(parent, identifier, title, null, null);
	}
	
	/**
	 * This constructor sets beside the identifier and parent also the title of the data object.
	 * Note that you're highly recommended to set
	 * the parent after constructing this object. If this is a top level DataObject you can leave it to null.
	 * @param identifier The identifier used to identify the data object
	 * @param title The title displayed inside the tree of data objects
	 */
	public DataObject(String identifier, String title){
		this(null, identifier, title, null, null);
	}
	
	/**
	 * This constructor creates a complete instance of a DataOject.
	 * @param parent The parent of this DataObject
	 * @param identifier The identifier used to identify the data object
	 * @param title The title displayed inside the tree of data objects
	 * @param p The permissions of this DataObject (leave them to null in order to use the same permissions as the parent object)
	 * @param icon The icon of this DataObject to display inside the UI
	 */
	public DataObject(DataObject parent, String identifier, String title, Permission p, BufferedImage icon){
		super();
		this.identifier = identifier;
		this.setTitle(title);
		setIcon(icon);
		setParent(parent);
		setPermissions(p);
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
	 * @return an SWT composite object to display the content of the data object
	 */
	public abstract Composite onOpen();
	
	/**
	 * This method gets called when the user closes the content of the
	 * DataObject.
	 */
	public abstract void onClose();
	
	/**
	 * This method gets called when the DataObject gets added to a work file.
	 * @param wf The WorkFile that added the DataObject.
	 */
	public abstract void onAddToWorkSheet(WorkFile wf);

	/**
	 * This method is used to get the parent of this DataObject.
	 * If it returns null it means that it is located at the top of the tree (e.g. that its parent is the work sheet).
	 * @return the parent
	 */
	public DataObject getParent() {
		return parent;
	}

	/**
	 * Use this method to set a different parent for this DataObject.
	 * @param parent the parent to set
	 */
	public void setParent(DataObject parent) {
		this.parent = parent;
	}

	/**
	 * Use this method to get the permissions of this DataObject.
	 * If they're set to null it will return the ones of its parent.
	 * NOTE: This method can still return null if there are no permissions set at all.
	 * @return the permissions applying to this DataObject
	 */
	public Permission getPermissions() {
		if(permissions != null)
			return permissions;
		if(parent != null)
			return parent.getPermissions();
		//Should we query the work sheet?
		return null;
	}

	/**
	 * Use this method to set separate permissions for this DataObject.
	 * @param permissions the permissions to set
	 */
	public void setPermissions(Permission permissions) {
		this.permissions = permissions;
	}
	
	@Override
	public boolean equals(Object o){
		if(o == null)
			return false;
		if(!(o instanceof DataObject))
			return false;
		DataObject d = (DataObject) o;
		if(!d.getIdentifier().equals(this.getIdentifier()))
			return false;
		if(!d.getTitle().equals(this.getTitle()))
			return false;
		return true;
	}
	
}
