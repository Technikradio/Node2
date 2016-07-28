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
 * This class represents a plugin manifest. It contains all required metadata of
 * the plugin.
 * 
 * @author doralitze
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
	private ArrayList<String> incompatiblePlugins;
	private String identifier;
	private String mainClass;

	/**
	 * @param version
	 *            the version to set
	 */
	protected void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @param compatibleVersion
	 *            the compatibleVersion to set
	 */
	protected void setCompatibleVersion(int compatibleVersion) {
		this.compatibleVersion = compatibleVersion;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * @param maintainer
	 *            the maintainer to set
	 */
	protected void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
	}

	/**
	 * @param updateSite
	 *            the updateSite to set
	 */
	protected void setUpdateSite(String updateSite) {
		this.updateSite = updateSite;
	}

	/**
	 * @param license
	 *            the license to set
	 */
	protected void setLicense(String license) {
		this.license = license;
	}

	/**
	 * @param website
	 *            the website to set
	 */
	protected void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	protected void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param longInfoText
	 *            the longInfoText to set
	 */
	protected void setLongInfoText(String longInfoText) {
		this.longInfoText = longInfoText;
	}

	/**
	 * @param dependancys
	 *            the dependancys to set
	 */
	protected void setDependancys(ArrayList<String> dependancys) {
		this.dependancys = dependancys;
	}

	/**
	 * @param identifier
	 *            the identifier to set
	 */
	protected void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * This method is used to set the list of incompatible plug-ins.
	 * 
	 * @param incompatiblePlugins
	 *            The list of incompatible plug-ins.
	 */
	protected void setIncompatiblePlugins(ArrayList<String> incompatiblePlugins) {
		this.incompatiblePlugins = incompatiblePlugins;
	}

	/**
	 * @return the mainClass
	 */
	protected String getMainClass() {
		return mainClass;
	}

	/**
	 * @param mainClass
	 *            the mainClass to set
	 */
	protected void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}

	/**
	 * Use this method to get the version of the plug-in.
	 * 
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * Use this method to get the version of node this plug-in is build for.
	 * 
	 * @return the compatibleVersion
	 */
	public int getCompatibleVersion() {
		return compatibleVersion;
	}

	/**
	 * Use this method to get the name of the plug-in to display.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Use this method to get the maintainer of the plug-in.
	 * 
	 * @return the maintainer
	 */
	public String getMaintainer() {
		return maintainer;
	}

	/**
	 * Use this method to get the update site. This should be a link pointing to
	 * an MML file with pairs of the compatible node version and the latest
	 * version of the plug-in followed by a link to the plug-in file. The
	 * version and the link should be separated by a ':'.
	 * 
	 * @return the updateSite
	 */
	public String getUpdateSite() {
		return updateSite;
	}

	/**
	 * Use this method to get the license of the plug-in. Meant is the
	 * identifier (like LGPLv3 for example). Use the info text to display the
	 * full text of the license for example.
	 * 
	 * @return the license
	 */
	public String getLicense() {
		return license;
	}

	/**
	 * Use this method to get the web site of the plug-in. The web site meant is
	 * the one providing information about the plug-in and support.
	 * 
	 * @return the web site
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * This method is used to get the description of the plug-in. You shouldn't
	 * use HTML for this due to the fact that it will be displayed inside a text
	 * field later on. You also should make sure that the text isn't longer than
	 * 300 characters.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * This method is used to get the long informational text of this plug-in.
	 * The text should be correct plain HTML due to the fact that is very likely
	 * that it will be displayed inside a web browser widget.
	 * 
	 * @return the longInfoText
	 */
	public String getLongInfoText() {
		return longInfoText;
	}

	/**
	 * This method is used to return the dependencies of the plug-in. It's a
	 * list of the identifiers of the dependent plug-ins.
	 * 
	 * @return the dependencies
	 */
	public ArrayList<String> getDependencies() {
		return dependancys;
	}

	/**
	 * This method is used to get the main identifier of the corresponding
	 * plug-in.
	 * 
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * This method is used to get the list of incompatible plug-ins.
	 * 
	 * @return The list of incompatible plug-ins.
	 */
	public ArrayList<String> getIncompatiblePlugins() {
		return this.incompatiblePlugins;
	}

	/**
	 * This constructor is used to generate a new instance on an manifest.
	 */
	public Manifest() {
		super();
		version = -1;
		compatibleVersion = -1;
		name = "";
		maintainer = "";
		updateSite = "";
		license = "none";
		website = "";
		description = "";
		longInfoText = "";
		dependancys = new ArrayList<String>();
		identifier = "";
	}

	/**
	 * This constructor is used to generate a virtual manifest. This is mainly
	 * used for the unit tests.
	 * 
	 * @param identifier
	 *            The identifier to use for an virtual manifest.
	 */
	public Manifest(String identifier) {
		this();
		this.setIdentifier(identifier);
	}

}
