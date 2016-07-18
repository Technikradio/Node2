using System;
using Gtk;

namespace NodeLauncher
{
	class MainClass
	{
		public static void Main (string [] args)
		{
			String pathtoSource = System.Reflection.Assembly.GetEntryAssembly ().Location;
			Application.Init ();
			UpdatesWindow win = new UpdatesWindow ();
			win.Show ();
			Application.Run ();
		}
	}
}
