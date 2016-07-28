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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.technikradio.node.engine.plugin.Manifest;

/**
 * @author doralitze
 * This is the test class for {@link org.technikradio.node.engine.plugin.Manifest}
 */
public class ManifestTest {
	
	private Manifest m;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		m = new Manifest();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.Manifest#getVersion()}.
	 */
	@Test
	public final void testGetVersion() {
		assertEquals(-1, m.getVersion());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.Manifest#getCompatibleVersion()}.
	 */
	@Test
	public final void testGetCompatibleVersion() {
		assertEquals(-1, m.getCompatibleVersion());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.Manifest#getName()}.
	 */
	@Test
	public final void testGetName() {
		assertEquals("", m.getName());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.Manifest#getMaintainer()}.
	 */
	@Test
	public final void testGetMaintainer() {
		assertEquals("", m.getMaintainer());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.Manifest#getUpdateSite()}.
	 */
	@Test
	public final void testGetUpdateSite() {
		assertEquals("", m.getUpdateSite());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.Manifest#getLicense()}.
	 */
	@Test
	public final void testGetLicense() {
		assertEquals("none", m.getLicense());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.Manifest#getWebsite()}.
	 */
	@Test
	public final void testGetWebsite() {
		assertEquals("", m.getWebsite());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.Manifest#getDescription()}.
	 */
	@Test
	public final void testGetDescription() {
		assertEquals("", m.getDescription());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.Manifest#getLongInfoText()}.
	 */
	@Test
	public final void testGetLongInfoText() {
		assertEquals("", m.getLongInfoText());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.Manifest#getDependancys()}.
	 */
	@Test
	public final void testGetDependancys() {
		assertEquals(0, m.getDependencies().size());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.Manifest#getIdentifier()}.
	 */
	@Test
	public final void testGetIdentifier() {
		assertEquals("", m.getIdentifier());
	}

}
