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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Sash;

/**
 * This Container is designed to provide a split view for components. Use the
 * SWT style hints for the underlying composites and use the boolean variable
 * provided by the constructor in order to specify the layout of the container.
 * <br/>
 * This composite uses a sash to split the two underlying composites.
 * 
 * @author doralitze
 *
 */
public class SplitContainer extends Composite {

	private Composite first, second;
	private Sash sash;

	/**
	 * This constructor generates a new instance of this container. This
	 * constructor creates a vertical orientation by default.
	 * 
	 * @param parent
	 *            This argument is required by all SWT widgets and defines the
	 *            parent of this composite.
	 * @param style
	 *            This argument defines the style of the underlying composites.
	 * @param firstContainer
	 *            The class of the first container inside this split view.
	 * @param secondContainer
	 *            The class of the second container inside this split view.
	 * @throws SecurityException
	 *             This exception gets thrown if one or two of the desired
	 *             containers isn't accessible.
	 * @throws NoSuchMethodException
	 *             This exception gets thrown if one of the given container
	 *             classes doesn't have a
	 *             <code>public Composite(Composite, int){ ... }</code>
	 *             constructor.
	 * @throws InvocationTargetException
	 *             This exception gets thrown if the desired constructor isn't
	 *             capable of handling the object creation.
	 * @throws IllegalArgumentException
	 *             This exception gets thrown if one of the given container
	 *             classes doesn't have a
	 *             <code>public Composite(Composite, int){ ... }</code>
	 *             constructor.
	 * @throws IllegalAccessException
	 *             This exception gets thrown if the desired constructor isn't
	 *             capable of handling the object creation.
	 * @throws InstantiationException
	 *             This exception gets thrown if the desired constructor isn't
	 *             capable of handling the object creation.
	 */
	public SplitContainer(Composite parent, int style, Class<Composite> firstContainer,
			Class<Composite> secondContainer) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this(parent, style, firstContainer, secondContainer, false);
	}

	/**
	 * This constructor generates a new instance of this container.
	 * 
	 * @param parent
	 *            This argument is required by all SWT widgets and defines the
	 *            parent of this composite.
	 * @param style
	 *            This argument defines the style of the underlying composites.
	 * @param firstContainer
	 *            The class of the first container inside this split view.
	 * @param secondContainer
	 *            The class of the second container inside this split view.
	 * @param horizontal
	 *            A boolean value defining the orientation of this container.
	 *            Use true here if you want the container to be orientated
	 *            horizontally.
	 * @throws SecurityException
	 *             This exception gets thrown if one or two of the desired
	 *             containers isn't accessible.
	 * @throws NoSuchMethodException
	 *             This exception gets thrown if one of the given container
	 *             classes doesn't have a
	 *             <code>public Composite(Composite, int){ ... }</code>
	 *             constructor.
	 * @throws InvocationTargetException
	 *             This exception gets thrown if the desired constructor isn't
	 *             capable of handling the object creation.
	 * @throws IllegalArgumentException
	 *             This exception gets thrown if one of the given container
	 *             classes doesn't have a
	 *             <code>public Composite(Composite, int){ ... }</code>
	 *             constructor.
	 * @throws IllegalAccessException
	 *             This exception gets thrown if the desired constructor isn't
	 *             capable of handling the object creation.
	 * @throws InstantiationException
	 *             This exception gets thrown if the desired constructor isn't
	 *             capable of handling the object creation.
	 */
	public SplitContainer(Composite parent, int style, Class<Composite> firstContainer,
			Class<Composite> secondContainer, boolean horizontal) throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(parent, style);
		@SuppressWarnings("rawtypes")
		Class[] constructorDefinition = { Composite.class, Integer.TYPE };
		{
			Constructor<Composite> cs = firstContainer.getDeclaredConstructor(constructorDefinition);
			first = cs.newInstance(this, style);
		}
		if(!horizontal)
			sash = new Sash(this, SWT.VERTICAL);
		else
			sash = new Sash(this, SWT.HORIZONTAL);
		{
			Constructor<Composite> cs = secondContainer.getDeclaredConstructor(constructorDefinition);
			second = cs.newInstance(this, style);
		}
		
	}

}
