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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.technikradio.node.engine.action.Application;
import org.technikradio.node.engine.plugin.CommandRegistry;
import org.technikradio.node.engine.plugin.settings.Settings;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * @author doralitze test case for the settings registry.
 */
public class SettingsRegistryTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Settings.put("org.technikradio.node.tests.samplesettingsteststringA", "testA");
	}

	/**
	 * Test method for
	 * {@link org.technikradio.node.engine.plugin.settings.Settings#save()}.
	 */
	@Test
	public final void testSave() {
		Settings.put("org.technikradio.node.tests.samplesettingsteststringB", "testB\n:");
		Settings.save();
		BufferedReader br = null;
		boolean found = false;
		try {
			if (Application.LOCAL_APPDATA_FOLDER_AVAIABLE) {
				br = new BufferedReader(
						new FileReader(new File(Application.LOCAL_APPDATA_FOLDER + File.separator + "settings.mml")));
				String line;
				while ((line = br.readLine()) != null) {
					if (line.replace("\n", "")
							.equals("org.technikradio.node.tests.samplesettingsteststringB:testB${13}${58}"))
						found = true;
				}
			} else {
				Console.log(LogType.Warning, "SettingsTest",
						"Cannot load settings file. Falling back to empty dictionary");
			}
		} catch (IOException e) {
			Console.log(LogType.Error, "SettingsTest", "Cannot load settings file. Falling back to empty dictionary");
			e.printStackTrace();
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					Console.log(LogType.Error, "SettingsTest", "An error occured while closing the buffered reader:");
					e.printStackTrace();
				}
		}
		if (!found)
			fail("Didn't found the required setting inside the settings file.");
	}

	/**
	 * Test method for
	 * {@link org.technikradio.node.engine.plugin.settings.Settings#get(java.lang.String)}.
	 */
	@Test
	public final void testGetString() {
		assertEquals("testA", Settings.get("org.technikradio.node.tests.samplesettingsteststringA"));
		assertEquals("", Settings.get("org.technikradio.node.tests.samplesettingsteststringM"));
	}

	/**
	 * Test method for
	 * {@link org.technikradio.node.engine.plugin.settings.Settings#get(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testGetStringString() {
		assertEquals("testA", Settings.get("org.technikradio.node.tests.samplesettingsteststringA", "E"));
		assertEquals("E", Settings.get("org.technikradio.node.tests.samplesettingsteststringM", "E"));
	}

	/**
	 * Due to the fact that the put feature must be working in order to execute
	 * the other tests we're only testing the crashing. Test method for
	 * {@link org.technikradio.node.engine.plugin.settings.Settings#put(java.lang.String, java.lang.String)}.
	 */
	@Test(expected = NullPointerException.class)
	public final void testPutNull() {
		Settings.put(null, "testM");
	}

	/**
	 * Due to the fact that the put feature must be working in order to execute
	 * the other tests we're only testing the crashing. Test method for
	 * {@link org.technikradio.node.engine.plugin.settings.Settings#put(java.lang.String, java.lang.String)}.
	 */
	@Test(expected = NullPointerException.class)
	public final void testPutEmpty() {
		Settings.put("", "testM");
	}

	/**
	 * Test method for
	 * {@link org.technikradio.node.engine.plugin.settings.Settings#get(java.lang.String)}.
	 */
	@Test(expected = NullPointerException.class)
	public final void testGetStringNull() {
		Settings.get(null);
	}

	/**
	 * Test method for
	 * {@link org.technikradio.node.engine.plugin.settings.Settings#get(java.lang.String)}.
	 */
	@Test(expected = NullPointerException.class)
	public final void testGetStringEmpty() {
		Settings.get("");
	}
	
	/**
	 * Test method for the initializer
	 */
	@Test
	public final void testCommandAdder() {
		assertTrue(CommandRegistry.isCommandRegistered("list-all-settings"));
		assertTrue(CommandRegistry.isCommandRegistered("set-property"));
	}

}
