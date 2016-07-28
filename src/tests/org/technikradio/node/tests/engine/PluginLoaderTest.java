/**
 * 
 */
package org.technikradio.node.tests.engine;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.technikradio.node.engine.plugin.Manifest;
import org.technikradio.node.engine.plugin.PluginLoader;
import org.technikradio.node.engine.plugin.UnsolvedDependencyException;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * @author leon
 *
 */
public class PluginLoaderTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.PluginLoader#solve(java.util.ArrayList, java.util.ArrayList)}.
	 * @throws UnsolvedDependencyException 
	 */
	@Test
	public final void testSolve() throws UnsolvedDependencyException {
		ArrayList<Manifest> toSolve = new ArrayList<Manifest>();
		ArrayList<Manifest> solved = new ArrayList<Manifest>();
		{
			Manifest m = new Manifest("a");
			m.getDependencies().add("c");
			m.getDependencies().add("b");
			toSolve.add(m);
		}
		toSolve.add(new Manifest("c"));
		{
			Manifest m = new Manifest("b");
			m.getDependencies().add("c");
			toSolve.add(m);
		}
		ArrayList<String> s = new ArrayList<String>();
		try {
			PluginLoader.solve(toSolve, solved);
		} catch (UnsolvedDependencyException e) {
			Console.log(LogType.Error, this, "This exception shouldn't happen:");
			e.printStackTrace();
			throw e;
		}
		for(Manifest m : solved){
			s.add(m.getIdentifier());
		}
		String[] a = {"c", "b", "a"};
		assertArrayEquals(a, s.toArray(new String[s.size()]));
	}
	
	@Test(expected=UnsolvedDependencyException.class)
	public final void testLoopException() throws UnsolvedDependencyException{
		ArrayList<Manifest> toSolve = new ArrayList<Manifest>();
		ArrayList<Manifest> solved = new ArrayList<Manifest>();
		{
			Manifest m = new Manifest("a");
			m.getDependencies().add("c");
			m.getDependencies().add("b");
			toSolve.add(m);
		}
		toSolve.add(new Manifest("c"));
		{
			Manifest m = new Manifest("b");
			m.getDependencies().add("c");
			m.getDependencies().add("a");
			toSolve.add(m);
		}
		PluginLoader.solve(toSolve, solved);
	}
	
	@Test(expected=UnsolvedDependencyException.class)
	public final void testUnsatisfiedException() throws UnsolvedDependencyException{
		ArrayList<Manifest> toSolve = new ArrayList<Manifest>();
		ArrayList<Manifest> solved = new ArrayList<Manifest>();
		{
			Manifest m = new Manifest("a");
			m.getDependencies().add("b");
			toSolve.add(m);
		}
		PluginLoader.solve(toSolve, solved);
	}

}
