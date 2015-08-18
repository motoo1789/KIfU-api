package com.objetdirect.gwt.umlapi.client.umlcomponents;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.GWTUMLAPIException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.LexicalFlag;

public class UMLAsset extends UMLNode{

	public static String parseNameOrStereotype(final String stringToParse){
		if(stringToParse.equals("")){
			return "";
		}
		final LexicalAnalyzer lex = new LexicalAnalyzer(stringToParse);
		try{
			final LexicalAnalyzer.Token tk = lex.getToken();
			if ((tk == null) || (tk.getType() != LexicalFlag.IDENTIFIER)) {
				throw new GWTUMLAPIException("Invalid class name/stereotype : " + stringToParse + " doesn't repect uml conventions");
		}
			return tk.getContent();
	}catch(final GWTUMLAPIException e){
		Log.error(e.getMessage());
	}
		return "";
	}

	private String name;
	private ArrayList<UMLAssetType> stereotypes;

	public UMLAsset(final String name){
		super();
		this.name = name;
	}

	public final ArrayList<UMLAssetType> getStereoTypes(){
		return this.stereotypes;
	}

	public final String getName(){
		return this.name;
	}

	public final void setStereotypes(final ArrayList<UMLAssetType> stereotypes){
		this.stereotypes = stereotypes;
	}

	public final void setName(final String name){
		this.name = name;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name;
	}



}