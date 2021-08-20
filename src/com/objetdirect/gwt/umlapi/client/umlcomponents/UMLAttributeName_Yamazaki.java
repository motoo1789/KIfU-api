package com.objetdirect.gwt.umlapi.client.umlcomponents;

public class UMLAttributeName_Yamazaki {

	private String name = "";

	public UMLAttributeName_Yamazaki()
	{

	}

	public UMLAttributeName_Yamazaki(String name)
	{
		this.name = name;
	}

	public String getName() {
		return this.name;
	}



	/**
	 * Setter for the name
	 *
	 * @param name
	 *            to be set
	 *
	 */
	public void setName(final String name) {
		this.name = name;
	}



	/**
	 * Format a string from attribute name and type
	 *
	 * @return the UML formatted string for attribute name and type
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
//		final StringBuilder f = new StringBuilder();
//		f.append(this.visibility);
//		f.append(this.name);
//		if ((this.type != null) && !this.type.equals("")) {
//			f.append(" : ");
//			f.append(this.type);
//		}
//		return f.toString();
		return "";
	}
}
