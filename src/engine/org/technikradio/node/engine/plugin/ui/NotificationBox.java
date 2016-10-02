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
package org.technikradio.node.engine.plugin.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.technikradio.node.engine.plugin.settings.Settings;
import org.technikradio.node.engine.resources.Localisation;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * This class contains some static methods in order to display message boxes.
 * The difference between these methods and the MessageBox class provided by SWT
 * is that these methods block. Do not use these methods on the UI Thread!
 * 
 * @author doralitze
 *
 */
public final class NotificationBox {

	/**
	 * This class provides a blocking message dialog
	 * 
	 * @author doralitze
	 *
	 */
	private static class Box {

		private boolean done = false;
		private Shell s;
		private Label l;
		private Button doNotShowAgain;
		private Button dismissbutton;

		/**
		 * This constructor initializes the shell.
		 * 
		 * @param style
		 *            the SWT compatible shell style
		 */
		protected Box(int style) {
			// s = new MessageBox(DisplayFactory.getDisplay().getActiveShell(),
			// style);
			s = new Shell(style);
			l = new Label(s, SWT.LEFT);
			doNotShowAgain = new Button(s, SWT.CHECK);
			doNotShowAgain.setText(
					Localisation.getString("NotificationBox.donotshowbutton", "Do not show this message again"));
			dismissbutton = new Button(s, SWT.PUSH);
			dismissbutton.setText(Localisation.getString("NotificationBox.dismissbutton", "OK"));
			dismissbutton.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event arg0) {
					done = true;
					s.dispose();
				}

			});
			FillLayout fl = new FillLayout();
			fl.type = SWT.VERTICAL;
			s.setLayout(fl);
			s.setSize(380, 200);
		}

		/**
		 * Use this method in order to set the title of the shell.
		 * 
		 * @param text
		 *            The text to set as the title.
		 */
		protected void setText(String text) {
			s.setText(text);
		}

		/**
		 * Use this method in order to set the message inside the shell.
		 * 
		 * @param text
		 *            The text to set as the message.
		 */
		protected void setMessage(String text) {
			l.setText(text);
		}

		/**
		 * Use this method in order to display the shell and wait until it has
		 * been closed.
		 */
		protected void lock() {
			s.open();
			while (!done) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					Console.log(LogType.Warning, this, "Locking of Thread got interrupted:");
					e.printStackTrace();
				}
			}
		}

		/**
		 * Use this method in order to check if the user clicked on the "do not
		 * annoy me again" check box.
		 * 
		 * @return True if the user did so or otherwise false.
		 */
		protected boolean doNotShowAgainMarked() {
			return doNotShowAgain.getSelection();
		}

		@Override
		public String toString() {
			return "MessageBox";
		}

	}

	/**
	 * This method opens an message dialog without an option to enable a "do not
	 * notify me again" check box.
	 * 
	 * @param message
	 *            The message to show.
	 */
	public static void notify(String message) {
		notify(message, "");
	}

	/**
	 * This method opens an message dialog without an option to enable a "do not
	 * notify me again" check box.
	 * 
	 * @param message
	 *            The message to show.
	 * @param title
	 *            The title of the message box.
	 */
	public static void notify(String message, String title) {
		notify(message, title, SWT.OK | SWT.ICON_INFORMATION);
	}

	/**
	 * This method opens an message dialog without an option to enable a "do not
	 * notify me again" check box.
	 * 
	 * @param message
	 *            The message to show.
	 * @param title
	 *            The title of the message box.
	 * @param style
	 *            The SWT compatible style of the message box.
	 */
	public static void notify(String message, String title, int style) {
		MessageBox b = new MessageBox(DisplayFactory.getDisplay().getActiveShell(), style);
		b.setMessage(message);
		b.setText(title);
		b.open();
	}

	/**
	 * Use this method in order to display a message box containing a check box
	 * enabling the user to prevent such a message to be displayed in the
	 * future. This method does all the required checking for you based on the
	 * assumption that you will provide a valid settings key. The dialog will be
	 * display in the middle of the screen and will contain an empty title.
	 * 
	 * @param message
	 *            The message to display.
	 * @param showAgainKey
	 *            The settings key associated with the information to ignore
	 *            these messages in the future.
	 */
	public static void show(String message, String showAgainKey) {
		show(message, "", showAgainKey);
	}

	/**
	 * Use this method in order to display a message box containing a check box
	 * enabling the user to prevent such a message to be displayed in the
	 * future. This method does all the required checking for you based on the
	 * assumption that you will provide a valid settings key. The dialog will be
	 * display in the middle of the screen.
	 * 
	 * @param message
	 *            The message to display.
	 * @param title
	 *            The title of the dialog to display.
	 * @param showAgainKey
	 *            The settings key associated with the information to ignore
	 *            these messages in the future.
	 */
	public static void show(String message, String title, String showAgainKey) {
		show(message, title, showAgainKey, SWT.CENTER);
	}

	/**
	 * Use this method in order to display a message box containing a check box
	 * enabling the user to prevent such a message to be displayed in the
	 * future. This method does all the required checking for you based on the
	 * assumption that you will provide a valid settings key.
	 * 
	 * @param message
	 *            The message to display.
	 * @param title
	 *            The title of the dialog to display.
	 * @param showAgainKey
	 *            The settings key associated with the information to ignore
	 *            these messages in the future.
	 * @param messageBoxStyle
	 *            An SWT compatible style flag.
	 */
	public static void show(String message, String title, String showAgainKey, int messageBoxStyle) {
		if (Boolean.parseBoolean(Settings.get(showAgainKey, "false"))) {
			if (!Settings.avaiable(showAgainKey)) {
				Settings.put(showAgainKey, "false");
				Settings.save();
			}
			return;
		}
		int oldPrio = Thread.currentThread().getPriority();
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		Box b = new Box(messageBoxStyle);
		b.setText(title);
		b.setMessage(message);
		b.lock();
		if (b.doNotShowAgainMarked()) {
			Settings.put(showAgainKey, "true");
			Settings.save();
		}
		Thread.currentThread().setPriority(oldPrio);
	}
}
