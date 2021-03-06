/*
 * Copyright 2008 Georgios J. Georgopoulos.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the
 * License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package com.google.gwt.user.client.ui;

/**
 * @author georgopoulos.georgios(at)gmail.com
 */
public abstract class AbstractDecoratorPanel extends DecoratorPanel {

	/**
	 * Creates a new panel using the specified style names to apply to each row. Each row will contain three cells (Left, Center, and Right). The Center cell in
	 * the containerIndex row will contain the {@link Widget}.
	 * 
	 * @param rowStyles
	 *            an array of style names to apply to each row
	 * @param containerIndex
	 *            the index of the container row
	 */
	protected AbstractDecoratorPanel(final String[] rowStyles, final int containerIndex) {
		super(rowStyles, containerIndex);
	}
}
