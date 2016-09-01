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
package org.technikradio.node.tests.engine;

import static org.junit.Assert.*;

import java.util.Currency;
import java.util.Iterator;
import java.util.Locale;

import org.eclipse.swt.widgets.Composite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.technikradio.node.engine.plugin.DataObject;
import org.technikradio.node.engine.plugin.WorkFile;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * This is the test case for the WorkFile class.
 * @author doralitze
 *
 */
public final class WorkFileTest {
	
	private WorkFile w;
	DataObjectTestClass tc1;
	DataObjectTestClass tc2;
	DataObjectTestClass tc3;
	
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
		w = new WorkFile(Currency.getInstance(Locale.getDefault()));
		tc1 = new DataObjectTestClass("a", "1");
		tc2 = new DataObjectTestClass("b", "2");
		tc3 = new DataObjectTestClass("c", "3");
		w.addChild(tc1);
		w.addChild(tc2);
		w.addChild(tc3);
	}

	/**
	 * @throws java.lang.Exception In case of an exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.WorkFile#WorkFile(java.util.Currency)}.
	 */
	@Test
	public final void testWorkFile() {
		Currency c = Currency.getInstance(Locale.getDefault());
		WorkFile ww = new WorkFile(c);
		assertEquals(c, ww.getCurrency());
		assertNotNull(ww.getCurrency());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.WorkFile#getChildObjects()}.
	 */
	@Test
	public final void testGetChildObjects() {
		Iterator<DataObject> i = w.getChildObjects();
		DataObject o1 = i.next();
		DataObject o2 = i.next();
		if(!o1.equals(tc1) && !o1.equals(tc2) && !o1.equals(tc3)){
			Console.log(LogType.Error, this, "Got: " + o1.getIdentifier());
			fail("Fist test object didn't match. (" + o1.toString() + ")");
		}
		if(!o2.equals(tc1) && !o2.equals(tc2) && !o2.equals(tc3)){

			Console.log(LogType.Error, this, "Got: " + o1.getIdentifier());
			fail("Second test object didn't match. (" + o2.toString() + ")");
		}
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.WorkFile#getCurrency()}.
	 */
	@Test
	public final void testCurrency() {
		Currency c = Currency.getInstance("EUR");
		w.setCurrency(c);
		assertEquals(c, w.getCurrency());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.WorkFile#removeChild(org.technikradio.node.engine.plugin.DataObject)}.
	 */
	@Test
	public final void testRemoveAndFindChild() {
		assertEquals("The workfile should contain the tc3 object at the beginning.", tc3, w.getChild("c"));
		assertNull("It shouldn't be possible to retrive objects by their title.", w.getChild("3"));
		assertNull("The tc4 object shouldn't be avaiable yet.", w.getChild("d"));
		assertEquals("It should be possible to retrieve the tc3 object by its identifier.", tc3, w.getChild("c"));
		DataObjectTestClass tc4 = new DataObjectTestClass("d", "4");
		assertFalse("It shouldn't be possible to delete the tc3 object because it's not added yet.", w.removeChild(tc4));
		w.addChild(tc4);
		assertNotNull("The tc4 object should be accessable now.", w.getChild("d"));
		assertTrue("It should be possible to delete the object by now.", w.removeChild(tc4));
		assertNull("The tc4 object shouldn't be avaiable anymore.", w.getChild("d"));
	}

}
