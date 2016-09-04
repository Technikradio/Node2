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
package org.technikradio.node.snfsource;

import java.net.URI;

import org.technikradio.node.engine.plugin.DataObject;
import org.technikradio.node.engine.plugin.DataSource;
import org.technikradio.node.engine.plugin.WorkFile;
import org.technikradio.node.engine.resources.Localisation;

/**
 * This data source adds support for the old SNF data format.
 * @author doralitze
 *
 */
public class SNFDataSource extends DataSource {

	public SNFDataSource() {
		super("org.technikradio.node.snfsource.SNFDataSource");
		this.setName(Localisation.getString("org.technikradio.node.snfsource.SNFDataSource.name", "SNF File"));
		this.setDescription(Localisation.getString("org.technikradio.node.snfsource.SNFDataSource.description", "This module enables you to save and load data using the old SNF format."));
	}

	/* (non-Javadoc)
	 * @see org.technikradio.node.engine.plugin.DataSource#save(java.net.URI, org.technikradio.node.engine.plugin.WorkFile)
	 */
	@Override
	public boolean save(URI uri, WorkFile file) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.technikradio.node.engine.plugin.DataSource#load(java.net.URI)
	 */
	@Override
	public WorkFile load(URI uri) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.technikradio.node.engine.plugin.DataSource#showResourceOpenDialog()
	 */
	@Override
	public URI showResourceOpenDialog() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.technikradio.node.engine.plugin.DataSource#saveDataObject(org.technikradio.node.engine.plugin.DataObject)
	 */
	@Override
	public boolean saveDataObject(DataObject o, WorkFile f) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.technikradio.node.engine.plugin.DataSource#isRemoteDataSource()
	 */
	@Override
	public boolean isRemoteDataSource() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.technikradio.node.engine.plugin.DataSource#showNewWorkFileDialog()
	 */
	@Override
	public void showNewWorkFileDialog() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveWorkFile(WorkFile f) {
		// TODO Auto-generated method stub
		
	}

}
