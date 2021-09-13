/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 *
 * Copyright © 2009 Objet Direct Contact: gwtuml@googlegroups.com
 *
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.umlcomponents;

import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;

/**
 * This abstract class represent any uml component
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public abstract class UMLComponent {
	// Nothing here yet

	// add Yamazaki
	protected void setStroke_BLACK(GfxObject element)
	{
		GfxManager.getPlatform().setFont(element, OptionsManager.getSmallFont());
		GfxManager.getPlatform().setStroke(element, ThemeManager.getTheme().getClassForegroundColor(), 0); //ThemeManager.getTheme().getClassBackgroundColor()
		GfxManager.getPlatform().setFillColor(element, ThemeManager.getTheme().getClassForegroundColor());//attributeText, ThemeManager.getTheme().getClassForegroundColor()
	}

	protected void setStroke_RED(GfxObject element)
	{
		GfxManager.getPlatform().setFont(element, OptionsManager.getSmallFont());
		GfxManager.getPlatform().setStroke(element, GfxColor.RED, 1); //ThemeManager.getTheme().getClassBackgroundColor()

		GfxManager.getPlatform().setFillColor(element, ThemeManager.getTheme().getClassForegroundColor());//attributeText, ThemeManager.getTheme().getClassForegroundColor()
	}


}
