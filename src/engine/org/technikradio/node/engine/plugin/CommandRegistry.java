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
package org.technikradio.node.engine.plugin;

import java.io.PrintStream;
import java.util.Hashtable;

import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * This class is designed to register and call commands.
 * @author doralitze
 */
public class CommandRegistry {

	private static Hashtable<String, Command> commands;
	private static Hashtable<String, String> shortHelp;
	private static Hashtable<String, String> longHelp;
	private static PrintStream defaultPrintStream;

	static {
		commands = new Hashtable<String, Command>();
		shortHelp = new Hashtable<String, String>();
		longHelp = new Hashtable<String, String>();
		addCommand("help", new Command() {

			private void displayHelpPage(int number, PrintStream ps) {
				ps.println("Displaying helppage " + Integer.toString(number)
						+ ". Type 'help <page>' to see a different one.");
				String[] names = getAllRegisteredCommands();
				if (number * 20 > names.length || number < 0) {
					ps.println("Unfortunatly there is no help page '" + Integer.toString(number)
							+ "'. Maybe try a different one?");
					return;
				}
				for (int i = number * 20; i < names.length && i < (number + 1) * 20; i++) {
					try {
						ps.print(names[i]);
						ps.print(": ");
						if (shortHelp.containsKey(names[i])) {
							ps.println(shortHelp.get(names[i]));
						} else {
							ps.println("No further help avaiable.");
						}
					} catch (NullPointerException e) {
						Console.log(LogType.Warning, this, "An error occured while displaying the help page.");
					}
				}
				ps.println("End of help page " + Integer.toString(number) + " out of "
						+ Integer.toString(Math.floorDiv(names.length, 20)) + " pages.");
			}

			@Override
			public boolean execute(String[] args, PrintStream outputStream) {
				if (args.length < 1) {
					displayHelpPage(0, outputStream);
				} else {
					if (commands.containsKey(args[0])) {
						if (longHelp.containsKey(args[0])) {
							outputStream.println(longHelp.get(args[0]));
						} else {
							outputStream.println(
									"Unfortunaltely the requested command doesn't provide any further documanetation.");
						}
					} else
						try {
							displayHelpPage(Integer.parseInt(args[0]), outputStream);
						} catch (NumberFormatException e) {
							outputStream.println("Unable of finding a command called '" + args[0] + "'.");
						}
				}
				return true;
			}
		});
		addCommandHelp("help", "display this help.",
				"help <help page>\nhelp <command>\n\n\tUse help to get a list of avaible commands or specific help gor a certain command.");
		setDefaultPrintStream(System.out);
	}

	/**
	 * This method is used to register new commands. Note that the name must be
	 * unique.
	 * 
	 * @param name
	 *            The name of the command to be registered. Note: this is the
	 *            name used to call the command.
	 * @param c
	 *            The command to register
	 * @return true if it successfully registered the command or otherwise
	 *         false.
	 */
	public static boolean addCommand(String name, Command c) {
		if (name == null)
			throw new NullPointerException("The name of the command must be provided.");
		if (name.equals(""))
			throw new NullPointerException("The name of the command is not allowed to be null.");
		if (c == null)
			throw new NullPointerException("The command is not allowed to be null.");
		if (commands.containsKey(name))
			return false;
		commands.put(name, c);
		return true;
	}

	/**
	 * This method is used to check if a certain command is already registered.
	 * 
	 * @param name
	 *            The name to check for.
	 * @return true if the command is already registered or otherwise false.
	 */
	public static boolean isCommandRegistered(String name) {
		return commands.containsKey(name);
	}

	/**
	 * This method is used to collect all command names.
	 * 
	 * @return An array containing all names of the registered commands.
	 */
	public static String[] getAllRegisteredCommands() {
		return commands.keySet().toArray(new String[commands.size()]);
	}

	/**
	 * This method is used to provide help for a registered command. Note that
	 * the command must first be registered before there can be documentation
	 * registered for it.
	 * 
	 * @param commandName
	 *            The name of the command to provide documentation for.
	 * @param description
	 *            The description of the command
	 * @param longHelp
	 *            A longer help text displayed by calling the command
	 *            <code>help $command$</code>
	 */
	public static void addCommandHelp(String commandName, String description, String longHelp) {
		if (!commands.containsKey(commandName))
			throw new RuntimeException("The command '" + commandName + "' wasn't found.");
		shortHelp.put(commandName, description);
		CommandRegistry.longHelp.put(commandName, longHelp);
	}

	/**
	 * This method is used to set the default print stream of the command
	 * registry.
	 * 
	 * @param ps
	 *            The PrintStream to use as the new default one.
	 */
	public static void setDefaultPrintStream(PrintStream ps) {
		if (ps == null)
			throw new NullPointerException("The PrintStream is not allowed to be null.");
		defaultPrintStream = ps;
	}

	/**
	 * This method is used to call a command and uses the default output stream
	 * and without arguments.
	 * 
	 * @param name
	 *            The name of the command to call.
	 * @return true if the command successfully executed or otherwise false.
	 * @throws CommandNotFoundException
	 *             This exception gets thrown if the desired command doesn't
	 *             exists.
	 */
	public static boolean call(String name) throws CommandNotFoundException {
		return call(name, null, defaultPrintStream);
	}

	/**
	 * This method is used to call a command and uses the default output stream.
	 * 
	 * @param name
	 *            The name of the command to call.
	 * @param args
	 *            The arguments to pass to the command
	 * @return true if the command successfully executed or otherwise false.
	 * @throws CommandNotFoundException
	 *             This exception gets thrown if the desired command doesn't
	 *             exists.
	 */
	public static boolean call(String name, String[] args) throws CommandNotFoundException {
		return call(name, args, defaultPrintStream);
	}

	/**
	 * This method is used to call a command.
	 * 
	 * @param name
	 *            The name of the command to call.
	 * @param args
	 *            The arguments to pass to the command
	 * @param output
	 *            The output stream to output to.
	 * @return true if the command successfully executed or otherwise false.
	 * @throws CommandNotFoundException
	 *             This exception gets thrown if the desired command doesn't
	 *             exists.
	 */
	public static boolean call(String name, String[] args, PrintStream output) throws CommandNotFoundException {
		if (!commands.containsKey(name))
			throw new CommandNotFoundException("The command '" + name + "' wasn't found.",
					new NullPointerException("Unknown command."));
		Command c = commands.get(name);
		return c.execute(args, output);
	}

}
