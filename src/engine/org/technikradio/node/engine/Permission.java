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
package org.technikradio.node.engine;

/**
 * This class is used to store permissions. This is used for a potential client
 * side in order to prevent the user from doing stuff that he isn't allowed to
 * do anyway.
 * 
 * @author doralitze
 *
 */
public final class Permission {

	/**
	 * This permission is a permission setting allowing you everything.
	 * NOTE that you can't modify the flags of this constant.
	 */
	public static final Permission MASTER_PERMISSION;

	private boolean readAllowed = false;
	private boolean appendAllowed = false;
	private boolean modifyAllowed = false;
	private boolean deleteAllowed = false;
	private boolean readMetaDataAllowed = false;
	private boolean editMetaDataAllowed = false;
	private boolean editPermissionsAllowed = false;

	static {
		Permission p = new Permission();
		p.readAllowed = true;
		p.appendAllowed = true;
		p.setModifyAllowed(true);
		p.deleteAllowed = true;
		p.readMetaDataAllowed = true;
		p.editMetaDataAllowed = true;
		p.editPermissionsAllowed = true;
		MASTER_PERMISSION = p;
	}

	/**
	 * This method is used to check if reading the data is allowed.
	 * 
	 * @return true if reading the object is allowed or otherwise false.
	 */
	public boolean isReadAllowed() {
		return readAllowed;
	}

	/**
	 * This method is used to check the read allowed flag. If it's set to true
	 * reading the data is allowed.
	 * 
	 * @param readAllowed
	 *            the readAllowed value to set.
	 */
	public void setReadAllowed(boolean readAllowed) {
		if(this == Permission.MASTER_PERMISSION)
			throw new RuntimeException("You can't modify the master permission.");
		this.readAllowed = readAllowed;
	}

	/**
	 * This method is used to check if the user is allowed to append data.
	 * 
	 * @return the appendAllowed value to set.
	 */
	public boolean isAppendAllowed() {
		return appendAllowed;
	}

	/**
	 * This method is used to set the append allowed flag.
	 * 
	 * @param appendAllowed
	 *            the append allowed value to set
	 */
	public void setAppendAllowed(boolean appendAllowed) {
		if(this == Permission.MASTER_PERMISSION)
			throw new RuntimeException("You can't modify the master permission.");
		this.appendAllowed = appendAllowed;
	}

	/**
	 * Use this method to check if the user is allowed to delete data from the
	 * object.
	 * 
	 * @return true if the user is allowed to delete data or otherwise false.
	 */
	public boolean isDeleteAllowed() {
		return deleteAllowed;
	}

	/**
	 * Use this method to set the value of the delete allowed flag.
	 * 
	 * @param deleteAllowed
	 *            the deleteAllowed to set
	 */
	public void setDeleteAllowed(boolean deleteAllowed) {
		if(this == Permission.MASTER_PERMISSION)
			throw new RuntimeException("You can't modify the master permission.");
		this.deleteAllowed = deleteAllowed;
	}

	/**
	 * Use this method to check if the user is allowed to read meta data.
	 * 
	 * @return True if the user is allowed to do so or otherwise false
	 */
	public boolean isReadMetaDataAllowed() {
		return readMetaDataAllowed;
	}

	/**
	 * Use this method to set the value of the read meta data flag.
	 * 
	 * @param readMetaDataAllowed
	 *            set this to true in order to allow the user to read the meta
	 *            data.
	 */
	public void setReadMetaDataAllowed(boolean readMetaDataAllowed) {
		if(this == Permission.MASTER_PERMISSION)
			throw new RuntimeException("You can't modify the master permission.");
		this.readMetaDataAllowed = readMetaDataAllowed;
	}

	/**
	 * Use this method to check if the user is allowed to edit the meta data.
	 * 
	 * @return True if the user is allowed to edit the meta data.
	 */
	public boolean isEditMetaDataAllowed() {
		return editMetaDataAllowed;
	}

	/**
	 * Use this method to set the value of the edit meta data flag.
	 * 
	 * @param editMetaDataAllowed
	 *            Use either true or false here depending on the situation. True
	 *            means that the user is allowed to edit the meta data and false
	 *            means the opposite.
	 */
	public void setEditMetaDataAllowed(boolean editMetaDataAllowed) {
		if(this == Permission.MASTER_PERMISSION)
			throw new RuntimeException("You can't modify the master permission.");
		this.editMetaDataAllowed = editMetaDataAllowed;
	}

	/**
	 * Use this method to check if the user is allowed to edit the permissions
	 * of the object.
	 * 
	 * @return True if the user is allowed to do so or otherwise false.
	 */
	public boolean isEditPermissionsAllowed() {
		return editPermissionsAllowed;
	}

	/**
	 * Use this method to set the value of the edit permissions allowed flag.
	 * 
	 * @param editPermissionsAllowed
	 *            Use either true or false here depending on the situation. True
	 *            means that the user is allowed to edit the permissions and
	 *            false means the opposite.
	 */
	public void setEditPermissionsAllowed(boolean editPermissionsAllowed) {
		if(this == Permission.MASTER_PERMISSION)
			throw new RuntimeException("You can't modify the master permission.");
		this.editPermissionsAllowed = editPermissionsAllowed;
	}

	/**
	 * Use this method to check if the user is allowed to modify existing data.
	 * 
	 * @return True if the user is allowed to do so or otherwise false.
	 */
	public boolean isModifyAllowed() {
		return modifyAllowed;
	}

	/**
	 * Use this method to set the value of the modify data allowed flag.
	 * 
	 * @param modifyAllowed
	 *            Use either true or false here depending on the situation. True
	 *            means that the user is allowed to edit the data and false
	 *            means the opposite.
	 */
	public void setModifyAllowed(boolean modifyAllowed) {
		if(this == Permission.MASTER_PERMISSION)
			throw new RuntimeException("You can't modify the master permission.");
		this.modifyAllowed = modifyAllowed;
	}
	
	/**
	 * This overrides <code>Object.toString()</code>.
	 * This method returns a valid JSON representation of the values of this object.
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		sb.append("\t\"read\" : " + Boolean.toString(isReadAllowed()) + ",\n");
		sb.append("\t\"append\" : " + Boolean.toString(isAppendAllowed()) + ",\n");
		sb.append("\t\"modify\" : " + Boolean.toString(isModifyAllowed()) + ",\n");
		sb.append("\t\"delete\" : " + Boolean.toString(isDeleteAllowed()) + ",\n");
		sb.append("\t\"read_metadata\" : " + Boolean.toString(isReadMetaDataAllowed()) + ",\n");
		sb.append("\t\"edit_metadata\" : " + Boolean.toString(isEditMetaDataAllowed()) + ",\n");
		sb.append("\t\"edit_permissions\" : " + Boolean.toString(isEditPermissionsAllowed()) + ",\n");
		sb.append("}");
		return sb.toString();
	}

}
