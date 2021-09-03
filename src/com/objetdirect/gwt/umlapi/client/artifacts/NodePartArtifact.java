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
package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;

/**
 * This abstract class represent a part of a {@link NodeArtifact}
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public abstract class NodePartArtifact extends BoxArtifact {

	protected NodeArtifact	nodeArtifact;
	protected int			nodeWidth;
	protected int			height;
	protected GfxObject		textVirtualGroup;
	protected int			width;

	/**
	 * Constructor of NodePartArtifact
	 *
	 */
	public NodePartArtifact() {
		super(false);
	}

	@Override
	public void buildGfxObjectWithAnimation() {
		this.buildGfxObject();
	}

	/**
	 * Like {@link UMLArtifact#edit(GfxObject)} this method requests an edition but for a new object
	 */
	public abstract void edit();

	/**
	 * Getter for the nodeArtifact
	 *
	 * @return the nodeArtifact
	 */
	public NodeArtifact getNodeArtifact() {
		return this.nodeArtifact;
	}

	@Override
	public boolean isDraggable() {
		return false;
	}

	/**
	 * This method creates the graphical object for the text to determine the size of this part
	 */
	abstract void computeBounds();

	/**
	 * Setter for the nodeArtifact
	 *
	 * @param nodeArtifact
	 *            the nodeArtifact to set
	 */
	void setNodeArtifact(final NodeArtifact nodeArtifact) {
		this.nodeArtifact = nodeArtifact;
	}

	abstract void setNodeWidth(int width);

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
	//
}
