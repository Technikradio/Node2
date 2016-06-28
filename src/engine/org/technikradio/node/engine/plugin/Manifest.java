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

import java.util.ArrayList;

/**
 * @author doralitze
 * This class represents a plugin manifest. It contains all required metadata of the plugin.
 */
public final class Manifest {

	private int version;
	private int compatibleVersion;
	private String name;
	private String maintainer;
	private String updateSite;
	private String license;
	private String website;
	private String description;
	private String longInfoText;
	private ArrayList<String> dependancys;
	private String identifier;
	
	/**
	 * @param version the version to set
	 */
	protected void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @param compatibleVersion the compatibleVersion to set
	 */
	protected void setCompatibleVersion(int compatibleVersion) {
		this.compatibleVersion = compatibleVersion;
	}

	/**
	 * @param name the name to set
	 */
	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * @param maintainer the maintainer to set
	 */
	protected void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
	}

	/**
	 * @param updateSite the updateSite to set
	 */
	protected void setUpdateSite(String updateSite) {
		this.updateSite = updateSite;
	}

	/**
	 * @param license the license to set
	 */
	protected void setLicense(String license) {
		this.license = license;
	}

	/**
	 * @param website the website to set
	 */
	protected void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * @param description the description to set
	 */
	protected void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param longInfoText the longInfoText to set
	 */
	protected void setLongInfoText(String longInfoText) {
		this.longInfoText = longInfoText;
	}

	/**
	 * @param dependancys the dependancys to set
	 */
	protected void setDependancys(ArrayList<String> dependancys) {
		this.dependancys = dependancys;
	}

	/**
	 * @param identifier the identifier to set
	 */
	protected void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @return the compatibleVersion
	 */
	public int getCompatibleVersion() {
		return compatibleVersion;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the maintainer
	 */
	public String getMaintainer() {
		return maintainer;
	}

	/**
	 * @return the updateSite
	 */
	public String getUpdateSite() {
		return updateSite;
	}

	/**
	 * @return the license
	 */
	public String getLicense() {
		return license;
	}

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the longInfoText
	 */
	public String getLongInfoText() {
		return longInfoText;
	}

	/**
	 * @return the dependancys
	 */
	public ArrayList<String> getDependancys() {
		return dependancys;
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	public Manifest() {
		super();
	}

}
