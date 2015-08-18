package com.objetdirect.gwt.umlapi.client.umlcomponents;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.GWTUMLAPIException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.LexicalFlag;

public class UMLSecurityUseCase extends UMLNode {
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

	/**
	 * Constructor of {@link UMLClass}
	 *
	 * @param name
	 */
	public UMLSecurityUseCase(final String name) {
		super();
		this.name = name;
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
	 * Setter for the name
	 *
	 * @param name
	 *            the name to set
	 */
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
}
