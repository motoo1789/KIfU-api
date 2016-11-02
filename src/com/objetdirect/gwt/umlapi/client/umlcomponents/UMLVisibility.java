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

/**
 * This enumeration lists all the visibility defined in uml
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public enum UMLVisibility {
	/**
	 * Package visibility (~)
	 */
	PACKAGE('~'),
	/**
	 * Private visibility (-)
	 */
	PRIVATE('-'),
	/**
	 * Protected visibility (#)
	 */
	PROTECTED('#'),
	/**
	 * Public visibility (+)
	 */
	PUBLIC('+'),
	/**
	 * Public visibility (NONE)
	 */
	NONE(' '); //space.
	/**
	 * This function convert a visibility char (+, -, #, ~) to a Visibility
	 *
	 * @param token
	 * @return the Visibility, if the char is any other character returns the PACKAGE visibility
	 */
	public static UMLVisibility getVisibilityFromToken(final char token) {
		switch (token) {
			case '+':
				return PUBLIC;
			case '#':
				return PROTECTED;
			case '-':
				return PRIVATE;
			case '~':
			default:
				return PACKAGE;
		}
	}

	private char	token;

	/**
	 * @param token
	 */
	private UMLVisibility(final char token) {
		this.token = token;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return ("" + this.token).trim();
	}

}
