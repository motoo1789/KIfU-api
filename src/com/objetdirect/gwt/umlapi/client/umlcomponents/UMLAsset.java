package com.objetdirect.gwt.umlapi.client.umlcomponents;

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
	private String stereotype;

	public UMLAsset(final String assetName){
		super();
		this.name = assetName;
	}

	public UMLAsset(String assetName, String stereotype) {
		super();
		this.name = assetName;
		this.stereotype = stereotype;
	}

	public final String getStereoTypes(){
		return this.stereotype;
	}

	public final String getName(){
		return this.name;
	}

	public final void setStereotypes(final String stereotype){
		this.stereotype = stereotype;
	}

	public final void setName(final String name){
		this.name = name;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name+"!"+this.stereotype;
	}

	@Override
	public void setType(String type) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void setVisibility(UMLVisibility visibility) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean hasGfxObjectKey(String haskey) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}



}