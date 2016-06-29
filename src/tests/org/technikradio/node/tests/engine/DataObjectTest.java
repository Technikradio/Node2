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

import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.technikradio.node.engine.plugin.DataNotYetLoadedException;
import org.technikradio.node.engine.plugin.DataObject;

/**
 * @author doralitze
 * Test case for the data object class
 */
public class DataObjectTest {
	
	private TestClass cl;
	
	private class TestClass extends DataObject{

		public TestClass(String identifier, String title) {
			super(identifier, title);
		}

		@Override
		public JPanel onOpen() {
			return null;
		}

		@Override
		public void onClose() {
		}
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		cl = new TestClass("bla", null);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.DataObject#getIdentifier()}.
	 */
	@Test
	public final void testGetIdentifier() {
		assertEquals("bla", cl.getIdentifier());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.DataObject#getTitle()}
	 * and {@link org.technikradio.node.engine.plugin.DataObject#setTitle(java.lang.String)}.
	 */
	@Test
	public final void testGetAndSetTitle() {
		assertEquals(null, cl.getTitle());
		cl.setTitle("more blah");
		assertEquals("more blah", cl.getTitle());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.DataObject#getIcon()}
	 * and {@link org.technikradio.node.engine.plugin.DataObject#setIcon(java.awt.image.BufferedImage)}.
	 */
	@Test
	public final void testGetAndSetIcon() {
		assertEquals(null, cl.getIcon());
		BufferedImage b = new BufferedImage(20, 20, BufferedImage.TYPE_3BYTE_BGR);
		cl.setIcon(b);
		assertEquals(b, cl.getIcon());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.DataObject#save()}.
	 */
	@Test(expected=DataNotYetLoadedException.class)
	public final void testSave() {
		cl.save();
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.DataObject#onOpen()}.
	 */
	@Test
	public final void testOnOpen() {
		assertNull(cl.onOpen());
	}

}
