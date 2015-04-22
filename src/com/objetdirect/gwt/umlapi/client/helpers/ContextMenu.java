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
package com.objetdirect.gwt.umlapi.client.helpers;

import static com.objetdirect.gwt.umlapi.client.helpers.TextResource.*;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.objetdirect.gwt.umlapi.client.contrib.PopupMenu;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram.Type;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind;


/**
 * This class manages the right click context drop menu
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ContextMenu {

	private final Command			addNewClass		= new Command() {
														public void execute() {
															ContextMenu.this.canvas.addNewClass(ContextMenu.this.location);
														}
													};
	private final Command			addNewObject	= new Command() {
														public void execute() {
															ContextMenu.this.canvas.addNewObject(ContextMenu.this.location);
														}
													};
	private final Command			addNewLifeLine	= new Command() {
														public void execute() {
															ContextMenu.this.canvas.addNewLifeLine(ContextMenu.this.location);
														}
													};
	private final Command			addNewNote		= new Command() {
														public void execute() {
															ContextMenu.this.canvas.addNewNote(ContextMenu.this.location);
														}
													};
	private final Command			bringHelp		= new Command() {
														public void execute() {
															HelpManager.bringHelpPopup();
														}
													};

	private final Command			clearDiagram	= new Command() {
														public void execute() {
															ContextMenu.this.canvas.selectAll();
															ContextMenu.this.canvas.removeSelected();
														}
													};
	private final Command			changeLinkStyle	= new Command() {
														public void execute() {
															OptionsManager.set("AngularLinks", 1 - OptionsManager.get("AngularLinks"));
															ContextMenu.this.canvas.rebuildAllLinks();
														}
													};

	private final UMLCanvas			canvas;
	private PopupMenu				contextMenu;

	private final Command			remove			= new Command() {
														public void execute() {
															ContextMenu.this.canvas.removeSelected();
														}
													};

	private final MenuBarAndTitle	specificRightMenu;

	private final Point				location;
	private final Command	 			cut			= new Command() {
														public void execute() {
															ContextMenu.this.canvas.cut();
														}
													};
	private final Command	 			copy			= new Command() {
														public void execute() {
															ContextMenu.this.canvas.copy();
														}
													};
	private final Command	 			paste			= new Command() {
														public void execute() {
															ContextMenu.this.canvas.paste();
														}
													};

	/**
	 * Constructor of ContextMenu without a specific context menu part
	 *
	 * @param location
	 *            The {@link Point} location where to display it (generally the mouse coordinates)
	 * @param canvas
	 *            The canvas where the actions must be called
	 */
	public ContextMenu(final Point location, final UMLCanvas canvas) {
		this(location, canvas, null);
	}

	/**
	 * Constructor of ContextMenu with a specific context menu part
	 *
	 * @param location
	 *            The {@link Point} location where to display it (generally the mouse coordinates)
	 * @param canvas
	 *            The canvas where the actions must be called
	 * @param specificRightMenu
	 *            The right menu specific to an artifact to add in this menu
	 */
	public ContextMenu(final Point location, final UMLCanvas canvas, final MenuBarAndTitle specificRightMenu) {
		super();
		this.location = location;
		this.canvas = canvas;
		this.specificRightMenu = specificRightMenu;
		this.makeMenu();
	}

	/**
	 * This method show the constructed context menu
	 *
	 */
	public void show() {
		this.contextMenu.setPopupPositionAndShow(new PositionCallback() {
			public void setPosition(final int offsetWidth, final int offsetHeight) {
				ContextMenu.this.contextMenu.setPopupPosition(ContextMenu.this.location.getX(), ContextMenu.this.location.getY());
			}
		});
	}

	private Command addRelation(final LinkKind relation) {
		return new Command() {
			public void execute() {
				ContextMenu.this.canvas.toLinkMode(relation);
			}
		};
	}

	private void makeMenu() {
		this.contextMenu = new PopupMenu();
		this.contextMenu.setAutoOpen(true);
		this.contextMenu.setAutoHideEnabled(true);
		if (this.specificRightMenu != null) {
			final MenuBar specificSubMenu = this.specificRightMenu.getSubMenu();

			specificSubMenu.addItem(DELETE.getMessage(), this.remove);
			this.contextMenu.addItem(this.specificRightMenu.getName(), specificSubMenu);
			this.contextMenu.addSeparator();
		}
		if (this.canvas.getUMLDiagram().getType().isClassType()) {
			this.contextMenu.addItem(ADD_NEW_CLASS.getMessage(), this.addNewClass);
		}
		if (this.canvas.getUMLDiagram().getType().isObjectType()) {
			this.contextMenu.addItem(ADD_NEW_OBJECT.getMessage(), this.addNewObject);
		}
		if (this.canvas.getUMLDiagram().getType() == Type.SEQUENCE) {
			this.contextMenu.addItem(ADD_NEW_LIFELINE.getMessage(), this.addNewLifeLine);
		}
		this.contextMenu.addItem(ADD_NEW_NOTE.getMessage(), this.addNewNote);
		final MenuBar linkSubMenu = new MenuBar(true);
		for (final LinkKind relationKind : LinkKind.values()) {
			if (relationKind.isForDiagram(Session.getActiveCanvas().getUMLDiagram().getType())) {
				linkSubMenu.addItem(relationKind.getNameInMenu(), this.addRelation(relationKind));
			}
		}
		this.contextMenu.addItem(ADD_RELATION.getMessage(), linkSubMenu);
		this.contextMenu.addSeparator();

//		this.contextMenu.addItem(CUT.getMessage(),  this.cut);
//		this.contextMenu.addItem(COPY.getMessage(),  this.copy);
//		this.contextMenu.addItem(PASTE.getMessage(), this.paste);

		this.contextMenu.addSeparator();
		if (this.canvas.getUMLDiagram().getType().isClassOrObjectType()) {
			this.contextMenu.addItem(SWITCH_LINK_STYLE.getMessage(), this.changeLinkStyle);
		}

		//this.contextMenu.addItem(CLEAR_DIAGRAM.getMessage(), this.clearDiagram);
		this.contextMenu.addItem(REMOVE.getMessage(), this.remove);
		this.contextMenu.addItem(HOTKEYS.getMessage(), this.bringHelp);
	}
}