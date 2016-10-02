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
package org.technikradio.node.tests.engine;

import static org.junit.Assert.*;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.technikradio.node.engine.plugin.DataObject;
import org.technikradio.node.engine.plugin.DataSource;
import org.technikradio.node.engine.plugin.PluginRegistry;
import org.technikradio.node.engine.plugin.WorkFile;
import org.technikradio.node.engine.plugin.ui.Window;

/**
 * Test case for testable parts of the plug-in registry
 * @author doralitze
 *
 */
public class PluginRegistryTest {

	/**
	 * @throws java.lang.Exception If the construction fails.
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void testDataSources() {
		DataSource ds = new DataSource("test.datasource.testidentifier"){

			@Override
			public boolean save(URI uri, WorkFile file) {
				return false;
			}

			@Override
			public WorkFile load(URI uri) {
				return null;
			}

			@Override
			public URI showResourceOpenDialog(Window w) {
				return null;
			}

			@Override
			public boolean saveDataObject(DataObject o, WorkFile f) {
				return false;
			}

			@Override
			public boolean isRemoteDataSource() {
				return false;
			}

			@Override
			public void showNewWorkFileDialog() {
				
			}

			@Override
			public void saveWorkFile(WorkFile f) {
				
			}};
		assertEquals("The array of all registered data sources should be empty at the beginning.", 0, PluginRegistry.getAllRegisteredDataSources().length);
		assertTrue("First it should be possible to register the given DataSource.", PluginRegistry.addDataSource(ds));
		assertFalse("Now it should be impossible to register the given DataSource.", PluginRegistry.addDataSource(ds));
		DataSource[] dss = {ds};
		assertArrayEquals("It should return an array containing only theone registered data source.", dss, PluginRegistry.getAllRegisteredDataSources());
	}

}
