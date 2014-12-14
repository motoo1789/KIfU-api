/*
 * Copyright (c) 2008-2009 GWT Mosaic Georgios J. Georgopoulos.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package com.objetdirect.gwt.umlapi.client.contrib;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbstractDecoratedPopupPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 *
 * @author georgopoulos.georgios(at)gmail.com
 *
 */
public class PopupMenu extends AbstractDecoratedPopupPanel {

	private final MenuBar	menu;

	/**
	 * Constructor of PopupMenu
	 *
	 */
	public PopupMenu() {
		super(true, false, "menuPopup", AnimationType.ONE_WAY_CORNER);
		this.menu = new MenuBar(true) {
			private boolean	canClose	= true;

			/*
			 * (non-Javadoc)
			 *
			 * @see com.google.gwt.user.client.ui.MenuBar#onBrowserEvent(com.google.gwt.user.client.Event)
			 */
			@Override
			public void onBrowserEvent(final Event event) {
				switch (DOM.eventGetType(event)) {
					case Event.ONMOUSEOVER: {
						this.canClose = false;
						break;
					}

					case Event.ONMOUSEOUT: {
						this.canClose = true;
						break;
					}
				}
				super.onBrowserEvent(event);
			}

			@SuppressWarnings("deprecation")
			@Override
			public void onPopupClosed(final PopupPanel sender, final boolean autoClosed) {
				super.onPopupClosed(sender, autoClosed);

				// If the menu popup was not auto-closed, close popup menu..
				//if (!autoClosed) {
					if (this.canClose) {
						PopupMenu.this.hide();
					}
					//if (!this.canClose) {
					//	PopupMenu.this.hide();
					//}
				//}
			}
		};
		this.menu.setAutoOpen(true);
		this.add(this.menu);
		this.setAnimationEnabled(true);
		this.sinkEvents(Event.ONCLICK);
		this.setStyleName("gwt-MenuBarPopup");
		// Issue 5 fix (ggeorg)
		DOM.setIntStyleAttribute(this.getElement(), "zIndex", Integer.MAX_VALUE);
	}

	/**
	 * Adds a menu item to the menu.
	 *
	 * @param item
	 *            the item to be added
	 * @return the {@link MenuItem} object
	 */
	public MenuItem addItem(final MenuItem item) {
		return this.menu.addItem(item);
	}

	/**
	 * Adds a menu item to the bar, that will fire the given command when it is selected.
	 *
	 * @param text
	 *            the item's text
	 * @param asHTML
	 *            <code>true</code> to treat the specified text as html
	 * @param cmd
	 *            the command to be fired
	 * @return the {@link MenuItem} object created
	 */
	public MenuItem addItem(final String text, final boolean asHTML, final Command cmd) {
		return this.menu.addItem(text, asHTML, cmd);
	}

	/**
	 * Adds a menu item to the bar, that will open the specified menu when it is selected.
	 *
	 * @param text
	 *            the item's text
	 * @param asHTML
	 *            <code>true</code> to treat the specified text as html
	 * @param popup
	 *            the menu to be cascaded from it
	 * @return the {@link MenuItem} object created
	 */
	public MenuItem addItem(final String text, final boolean asHTML, final MenuBar popup) {
		return this.menu.addItem(text, asHTML, popup);
	}

	/**
	 * Adds a menu item to the bar, that will fire the given command when it is selected.
	 *
	 * @param text
	 *            the item's text
	 * @param cmd
	 *            the command to be fired
	 * @return the {@link MenuItem} object created
	 */
	public MenuItem addItem(final String text, final Command cmd) {
		return this.menu.addItem(text, cmd);
	}

	/**
	 * Adds a menu item to the bar, that will open the specified menu when it is selected.
	 *
	 * @param text
	 *            the item's text
	 * @param popup
	 *            the menu to be cascaded from it
	 * @return the {@link MenuItem} object created
	 */
	public MenuItem addItem(final String text, final MenuBar popup) {
		return this.menu.addItem(text, popup);
	}

	/**
	 * Adds a thin line to the {@link MenuBar} to separate sections of {@link MenuItem}s.
	 *
	 * @return the {@link MenuItemSeparator} object created
	 */
	public MenuItemSeparator addSeparator() {
		return this.menu.addSeparator();
	}

	/**
	 * Adds a thin line to the {@link MenuBar} to separate sections of {@link MenuItem}s.
	 *
	 * @param separator
	 *            the {@link MenuItemSeparator} to be added
	 * @return the {@link MenuItemSeparator} object
	 */
	public MenuItemSeparator addSeparator(final MenuItemSeparator separator) {
		return this.menu.addSeparator(separator);
	}

	/**
	 * Removes all menu items from this menu bar.
	 */
	public void clearItems() {
		this.menu.clearItems();
	}

	/**
	 * Gets whether this menu bar's child menus will open when the mouse is moved over it.
	 *
	 * @return <code>true</code> if child menus will auto-open
	 */
	public boolean getAutoOpen() {
		return this.menu.getAutoOpen();
	}

	@Override
	public void onBrowserEvent(final Event event) {
		super.onBrowserEvent(event);
		switch (DOM.eventGetType(event)) {
			case Event.ONCLICK:
				this.hide();
				break;
		}
	}

	/**
	 * Removes the specified menu item from the bar.
	 *
	 * @param item
	 *            the item to be removed
	 */
	public void removeItem(final MenuItem item) {
		this.menu.removeItem(item);
	}

	/**
	 * Removes the specified {@link MenuItemSeparator} from the bar.
	 *
	 * @param separator
	 *            the separator to be removed
	 */
	public void removeSeparator(final MenuItemSeparator separator) {
		this.menu.removeSeparator(separator);
	}

	@Override
	public void setAnimationEnabled(final boolean b) {
		super.setAnimationEnabled(true);
		this.menu.setAnimationEnabled(true);
	}

	/**
	 * Sets whether this menu bar's child menus will open when the mouse is moved over it.
	 *
	 * @param autoOpen
	 *            <code>true</code> to cause child menus to auto-open
	 */
	public void setAutoOpen(final boolean autoOpen) {
		this.menu.setAutoOpen(autoOpen);
	}

}
