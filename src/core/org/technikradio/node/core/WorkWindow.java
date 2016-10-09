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
package org.technikradio.node.core;

import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.technikradio.node.engine.event.Event;
import org.technikradio.node.engine.event.EventRegistry;
import org.technikradio.node.engine.event.EventResponder;
import org.technikradio.node.engine.plugin.DataObject;
import org.technikradio.node.engine.plugin.Foldable;
import org.technikradio.node.engine.plugin.WorkFile;
import org.technikradio.node.engine.plugin.ui.Colors;
import org.technikradio.node.engine.plugin.ui.Window;
import org.technikradio.node.engine.plugin.ui.WindowOrientation;

/**
 * This class handles a window desired to be the one that contains the working
 * elements.
 * 
 * @author doralitze
 *
 */
public class WorkWindow {

	private final Window w;
	private final Tree tree;
	private final CTabFolder tabs;
	private WorkFile file;

	/**
	 * Use this constructor in order to construct a new instance of this class.
	 */
	public WorkWindow() {
		w = new Window();
		tree = new Tree(w.getContainer(WindowOrientation.LEFT_TRAY), SWT.VIRTUAL | SWT.BORDER);
		tree.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		tabs = new CTabFolder(w.getContainer(WindowOrientation.CENTER), SWT.BORDER);
		
		{
			EventResponder<Composite> er = new EventResponder<Composite>();
			Event e = new Event(Identifiers.WORK_WINDOW_CREATING_EVENT, null, er);
			EventRegistry.raiseEvent(e, true);
		}
		w.setSize(800, 600);
	}
	
	/**
	 * Use this method in order to open a data object.
	 * @param o The data object to open
	 */
	public void openObject(DataObject o){
		if(o == null)
			return;
		Composite composite = new Composite(tabs, SWT.NONE);
        composite.setLayout(new FillLayout());
        composite.setBackground(Colors.BLACK);
        CTabItem item = new CTabItem(tabs, SWT.NONE);
        item.setControl(composite);
        item.setText(o.getTitle());
        if(o.getIcon() != null)
        	item.setImage(new Image(w.getShell().getDisplay(), convertToSWT(o.getIcon())));
        o.onOpen(composite);
        tabs.setSelection(item);
	}

	/**
	 * Use this method in order to retrive the current work file.
	 * 
	 * @return the current work file
	 */
	public WorkFile getWorkFile() {
		return file;
	}

	/**
	 * Use this method in order to set the current work file.
	 * 
	 * @param file
	 *            the file to set
	 */
	public void setWorkFile(WorkFile file) {
		this.file = file;
		DataObject[] objs = file.getChildArray();
		tree.setData(objs);
		tree.addListener(SWT.SetData, new Listener() {
				    public void handleEvent(org.eclipse.swt.widgets.Event event) {
				       TreeItem item = (TreeItem)event.item;
				       TreeItem parentItem = item.getParentItem();
				       DataObject d = null;
				       if (parentItem == null) {
				          /* root-level item */
				          DataObject [] objs = (DataObject [])tree.getData();
				          d = objs [event.index];
				          item.setText(d.getTitle());
				       } else {
				    	   DataObject [] objs = (DataObject [])parentItem.getData();
				          d = objs [event.index];
				          item.setText(d.getTitle());
				       }
				       if (d instanceof Foldable) {
				    	   Foldable f = (Foldable) d;
				    	   DataObject [] objs = getMembersArray(f);
				          if (objs != null) {
				             item.setData(objs);
				             item.setItemCount(objs.length);
				          }
				       }
				    }

					private DataObject[] getMembersArray(Foldable f) {
						ArrayList<DataObject> m = new ArrayList<DataObject>();
						Iterator<DataObject> i = f.getMembers();
						DataObject d = i.next();
						while(i != null){
							m.add(d);
							d = i.next();
						}
						return m.toArray(new DataObject[m.size()]);
					}
				});
		tree.setItemCount(file.getChildCount());
	}
	
	/**
	 * snippet 156: convert between SWT Image and AWT BufferedImage.
	 * <p>
	 * For a list of all SWT example snippets see
	 * http://www.eclipse.org/swt/snippets/
	 */
	public static ImageData convertToSWT(BufferedImage bufferedImage) {
	    if (bufferedImage.getColorModel() instanceof DirectColorModel) {
	        DirectColorModel colorModel = (DirectColorModel)bufferedImage.getColorModel();
	        PaletteData palette = new PaletteData(
	                colorModel.getRedMask(),
	                colorModel.getGreenMask(),
	                colorModel.getBlueMask());
	        ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(),
	                colorModel.getPixelSize(), palette);
	        for (int y = 0; y < data.height; y++) {
	            for (int x = 0; x < data.width; x++) {
	                int rgb = bufferedImage.getRGB(x, y);
	                int pixel = palette.getPixel(new RGB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF));
	                data.setPixel(x, y, pixel);
	                if (colorModel.hasAlpha()) {
	                    data.setAlpha(x, y, (rgb >> 24) & 0xFF);
	                }
	            }
	        }
	        return data;
	    }
	    else if (bufferedImage.getColorModel() instanceof IndexColorModel) {
	        IndexColorModel colorModel = (IndexColorModel)bufferedImage.getColorModel();
	        int size = colorModel.getMapSize();
	        byte[] reds = new byte[size];
	        byte[] greens = new byte[size];
	        byte[] blues = new byte[size];
	        colorModel.getReds(reds);
	        colorModel.getGreens(greens);
	        colorModel.getBlues(blues);
	        RGB[] rgbs = new RGB[size];
	        for (int i = 0; i < rgbs.length; i++) {
	            rgbs[i] = new RGB(reds[i] & 0xFF, greens[i] & 0xFF, blues[i] & 0xFF);
	        }
	        PaletteData palette = new PaletteData(rgbs);
	        ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(),
	                colorModel.getPixelSize(), palette);
	        data.transparentPixel = colorModel.getTransparentPixel();
	        WritableRaster raster = bufferedImage.getRaster();
	        int[] pixelArray = new int[1];
	        for (int y = 0; y < data.height; y++) {
	            for (int x = 0; x < data.width; x++) {
	                raster.getPixel(x, y, pixelArray);
	                data.setPixel(x, y, pixelArray[0]);
	            }
	        }
	        return data;
	    }
	    else if (bufferedImage.getColorModel() instanceof ComponentColorModel) {
	        ComponentColorModel colorModel = (ComponentColorModel)bufferedImage.getColorModel();
	        //ASSUMES: 3 BYTE BGR IMAGE TYPE
	        PaletteData palette = new PaletteData(0x0000FF, 0x00FF00,0xFF0000);
	        ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(),
	                colorModel.getPixelSize(), palette);
	        //This is valid because we are using a 3-byte Data model with no transparent pixels
	        data.transparentPixel = -1;
	        WritableRaster raster = bufferedImage.getRaster();
	        int[] pixelArray = new int[3];
	        for (int y = 0; y < data.height; y++) {
	            for (int x = 0; x < data.width; x++) {
	                raster.getPixel(x, y, pixelArray);
	                int pixel = palette.getPixel(new RGB(pixelArray[0], pixelArray[1], pixelArray[2]));
	                data.setPixel(x, y, pixel);
	            }
	        }
	        return data;
	    }
	    return null;
	}

	public void open() {
		w.open();
	}
}
