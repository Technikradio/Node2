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

import java.io.PrintStream;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.technikradio.node.engine.plugin.Command;
import org.technikradio.node.engine.plugin.CommandNotFoundException;
import org.technikradio.node.engine.plugin.CommandRegistry;

/**
 * This is the test case for the command registry.
 * @author doralitze
 */
public class CommandRegistryTest {

	/**
	 * @throws java.lang.Exception In case of an exception
	 */
	@Before
	public void setUp() throws Exception {
		CommandRegistry.addCommand("testcommandB", new Command(){

			@Override
			public boolean execute(String[] args, PrintStream outputStream) {
				return true;
			}});
		CommandRegistry.addCommand("testcommandC", new Command(){

			@Override
			public boolean execute(String[] args, PrintStream outputStream) {
				return false;
			}});
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.CommandRegistry#addCommand(java.lang.String, org.technikradio.node.engine.plugin.Command)}.
	 */
	@Test(expected=NullPointerException.class)
	public final void testAddCommand1() {
		CommandRegistry.addCommand(null, new Command(){

			@Override
			public boolean execute(String[] args, PrintStream outputStream) {
				return false;
			}});
	}
	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.CommandRegistry#addCommand(java.lang.String, org.technikradio.node.engine.plugin.Command)}.
	 */
	@Test(expected=NullPointerException.class)
	public final void testAddCommand2() {
		CommandRegistry.addCommand("", new Command(){

			@Override
			public boolean execute(String[] args, PrintStream outputStream) {
				return false;
			}});
	}
	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.CommandRegistry#addCommand(java.lang.String, org.technikradio.node.engine.plugin.Command)}.
	 */
	@Test(expected=NullPointerException.class)
	public final void testAddCommand3() {
		CommandRegistry.addCommand("testCommandA", null);
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.CommandRegistry#isCommandRegistered(java.lang.String)}.
	 */
	@Test
	public final void testIsCommandRegistered() {
		assertFalse(CommandRegistry.isCommandRegistered("testCommandA"));
		assertTrue(CommandRegistry.isCommandRegistered("testcommandB"));
		assertTrue(CommandRegistry.isCommandRegistered("testcommandC"));
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.CommandRegistry#getAllRegisteredCommands()}.
	 */
	@Test
	public final void testGetAllRegisteredCommands() {
		String[] c = CommandRegistry.getAllRegisteredCommands();
		System.out.println(Arrays.toString(c));
		String[] expected = {"help", "testcommandB", "testcommandC"};
		for(int i = 0; i < expected.length; i++)
			assertTrue("should contain " + expected[i], contains(c, expected[i]));
		assertFalse("Shouldn't contain testcommandA", contains(c, "testcommandA"));
		
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.CommandRegistry#addCommandHelp(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test(expected=RuntimeException.class)
	public final void testAddCommandHelp() {
		CommandRegistry.addCommandHelp("testCommandA", "blah", "more blah");
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.CommandRegistry#call(java.lang.String, java.lang.String[], java.io.PrintStream)}.
	 * @throws CommandNotFoundException If the test failed
	 */
	@Test
	public final void testCall1() throws CommandNotFoundException {
		assertTrue(CommandRegistry.call("testcommandB"));
		assertFalse(CommandRegistry.call("testcommandC"));
	}
	
	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.CommandRegistry#call(java.lang.String, java.lang.String[], java.io.PrintStream)}.
	 * @throws CommandNotFoundException If the test failed.
	 */
	@Test(expected=CommandNotFoundException.class)
	public final void testCall2() throws CommandNotFoundException {
		CommandRegistry.call("testcommandA");
	}
	
	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.CommandRegistry#setDefaultPrintStream(PrintStream)}.
	 */
	@Test(expected=NullPointerException.class)
	public final void testSetDefaultPrintStream(){
		CommandRegistry.setDefaultPrintStream(null);
	}
	
	private final boolean contains(String[] arr, String targetValue) {
		for(String s: arr){
			if(s.equals(targetValue))
				return true;
		}
		return false;
	}

}
