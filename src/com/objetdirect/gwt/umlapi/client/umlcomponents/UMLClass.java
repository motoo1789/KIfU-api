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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Window;
import com.objetdirect.gwt.umlapi.client.GWTUMLAPIException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.LexicalFlag;
import com.objetdirect.gwt.umlapi.client.artifacts.ISetStrokeRED;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;

/**
 * This class represents a class uml component
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class UMLClass extends UMLNode implements IGetDiffString {
	/**
	 * Parse a name or a stereotype from a {@link String}
	 *
	 * @param stringToParse
	 *            The string containing a name or a stereotype
	 *
	 * @return The new parsed name or stereotype or an empty one if there was a problem
	 */
	public static String parseNameOrStereotype(final String stringToParse) {
		if (stringToParse.equals("")) {
			return "";
		}
		final LexicalAnalyzer lex = new LexicalAnalyzer(stringToParse);
		try {
			final LexicalAnalyzer.Token tk = lex.getToken();
			if ((tk == null) || (tk.getType() != LexicalFlag.IDENTIFIER)) {
				throw new GWTUMLAPIException("Invalid class name/stereotype : " + stringToParse + " doesn't repect uml conventions");
			}
			return tk.getContent();
		} catch (final GWTUMLAPIException e) {
			Log.error(e.getMessage());
		}
		return "";
	}

	private String							name;
	private String							stereotype;
	private ArrayList<UMLClassAttribute>	attributes;
	private ArrayList<UMLClassMethod>		methods;

	// add Yamazaki
	private Map<String,GfxObject> classGfxObject = new HashMap<String,GfxObject>();

	/**
	 * Constructor of {@link UMLClass}
	 *
	 * @param name
	 */
	public UMLClass(final String name) {
		super();
		this.name = name;
	}

	/**
	 * Getter for the attributes
	 *
	 * @return the attributes
	 */
	public final ArrayList<UMLClassAttribute> getAttributes() {
		return this.attributes;
	}

	/**
	 * Getter for the methods
	 *
	 * @return the methods
	 */
	public final ArrayList<UMLClassMethod> getMethods() {
		return this.methods;
	}

	/**
	 * Getter for the name
	 *
	 * @return the name
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * Getter for the stereotype
	 *
	 * @return the stereotype
	 */
	public final String getStereotype() {
		return this.stereotype;
	}

	/**
	 * Setter for the attributes
	 *
	 * @param attributes
	 *            the attributes to set
	 */
	public final void setAttributes(final ArrayList<UMLClassAttribute> attributes) {
		this.attributes = attributes;
	}

	/**
	 * Setter for the methods
	 *
	 * @param methods
	 *            the methods to set
	 */
	public final void setMethods(final ArrayList<UMLClassMethod> methods) {
		this.methods = methods;
	}

	/**
	 * Setter for the name
	 *
	 * @param name
	 *            the name to set
	 */
	@Override
	public final void setName(final String name) {
		this.name = name;
	}

	/**
	 * Setter for the stereotype
	 *
	 * @param stereotype
	 *            the stereotype to set
	 */
	public final void setStereotype(final String stereotype) {
		this.stereotype = stereotype;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public String getDiffVisibilityKey() {
		// TODO 自動生成されたメソッド・スタブ
		return "KIfUのクラス図に修飾子はない";
	}

	@Override
	public String getDiffNameKey() {
		// TODO 自動生成されたメソッド・スタブ
		return this.name;
	}

	@Override
	public String getDiffTypeKey() {
		// TODO 自動生成されたメソッド・スタブ
		String key = this.name + ";type";
		return key.replaceAll(" ","");
	}


	@Override
	public void setVisibilityGfxObject(GfxObject visigility) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void setNameGfxObject(GfxObject name) {
		// TODO 自動生成されたメソッド・スタブ
		classGfxObject.put(getDiffNameKey(), name);
	}

	@Override
	public void setTypeGfxObject(GfxObject type) {
		// TODO 自動生成されたメソッド・スタブ
		classGfxObject.put(getDiffTypeKey(), type);
	}

	@Override
	public void setStrokeBLACK(String key) {
		// TODO 自動生成されたメソッド・スタブ
		if(this.classGfxObject.containsKey(key))
			super.setStroke_BLACK(this.classGfxObject.get(key));
	}

	@Override
	public void setStrokeRED(String key) {
		// TODO 自動生成されたメソッド・スタブ
		if(this.classGfxObject.containsKey(key))
			super.setStroke_RED(this.classGfxObject.get(key));
	}

	@Override
	public void setType(String type) {
		// TODO 自動生成されたメソッド・スタブ
		this.stereotype = type;
	}

	@Override
	public void setVisibility(UMLVisibility visibility) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean hasGfxObjectKey(String haskey) {
		// TODO 自動生成されたメソッド・スタブ
		return classGfxObject.containsKey(haskey) ? true : false;
	}

	public GfxObject getGfxObject(String key) {
		// TODO 自動生成されたメソッド・スタブ
		return classGfxObject.get(key);
	}


}
