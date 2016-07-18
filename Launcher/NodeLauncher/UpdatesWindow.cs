using System;
using Gtk;

public partial class UpdatesWindow : Gtk.Window
{
	public UpdatesWindow () : base (Gtk.WindowType.Toplevel)
	{
		Build ();
	}

	protected void OnDeleteEvent (object sender, DeleteEventArgs a)
	{
		Application.Quit ();
		a.RetVal = true;
	}
}
