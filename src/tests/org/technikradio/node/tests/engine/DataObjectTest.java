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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.util.Set;

import org.eclipse.swt.widgets.Composite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.technikradio.node.engine.plugin.DataNotYetLoadedException;
import org.technikradio.node.engine.plugin.DataObject;
import org.technikradio.node.engine.plugin.WorkFile;

/**
 * @author doralitze
 * Test case for the data object class
 */
public class DataObjectTest {
	
	private DataObjectTestClass cl;
	
	private class DataObjectTestClass extends DataObject{

		public DataObjectTestClass(String identifier, String title) {
			super(identifier, title);
		}

		@Override
		public Composite onOpen() {
			return null;
		}

		@Override
		public void onClose() {
		}

		@Override
		public void onAddToWorkSheet(WorkFile wf) {
			//Do nothing here
		}
		
	}

	/**
	 * @throws java.lang.Exception In case of an exception
	 */
	@Before
	public void setUp() throws Exception {
		cl = new DataObjectTestClass("bla", null);
	}

	/**
	 * @throws java.lang.Exception In case of an exception
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
	
	/**
	 * Test method for the meta data functionality of the data object class.
	 */
	@Test
	public final void testMetadata(){
		assertFalse("Trying to remove an non existant entry shouldn't be possible.", cl.removeMetadataEntry("k1"));
		assertNull("There shouldn't be an k1 entry registered", cl.setMetadata("k1", "t1"));
		assertEquals("The method should return the correct value", cl.getMetadataValue("k1"), "t1");
		assertTrue("There shouldn't be a problem removing k1", cl.removeMetadataEntry("k1"));
		assertNull("There shouldn't be an k3 entry registered when querring", cl.getMetadataValue("k1"));
		assertNull("There shouldn't be an k1 entry registered anymore", cl.setMetadata("k1", "t1"));
		assertNull("There shouldn't be an k2 entry registered", cl.setMetadata("k2", "t2"));
		assertEquals("Registering a new k2 entry should return the old one.", cl.setMetadata("k2", "t3"), "t2");
		assertNull("There shouldn't be an k3 entry registered", cl.setMetadata("k3", "t2"));
		Set<String> set = cl.getAllKeys();
		int t1 = 0, t2 = 0, t3 = 0;
		for(String s : set){
			if(s.equals("k1")){
				t1++;
				assertEquals(cl.getMetadataValue(s), "t1");
			} else if(s.equals("k2")){
				t2++;
				assertEquals(cl.getMetadataValue(s), "t3");
			} else if(s.equals("k3")){
				t3++;
				assertEquals(cl.getMetadataValue(s), "t2");
			} else {
				fail("There was meta data that doesn't belong to the object: " + s);
			}
		}
		assertEquals("There wasn't the correct amount of t1 inside the meta data: " + t1, t1, 1);
		assertEquals("There wasn't the correct amount of t2 inside the meta data: " + t2, t2, 1);
		assertEquals("There wasn't the correct amount of t3 inside the meta data: " + t3, t3, 1);
	}

}
