package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute_Yamazaki;

public class ClassPartAttributesArtifact_Yamazaki extends NodePartArtifact {
	private final Map<GfxObject, UMLClassAttribute_Yamazaki>	attributeGfxObjects;
	private GfxObject								attributeRect;
	private final List<UMLClassAttribute_Yamazaki>			attributes;
	private GfxObject								lastGfxObject;


	public ClassPartAttributesArtifact_Yamazaki()
	{
		super();
		this.attributes = new ArrayList();
		this.attributeGfxObjects = new LinkedHashMap<GfxObject, UMLClassAttribute_Yamazaki>();
		this.height = 0;
		this.width = 0;
	}

	@Override
	public void edit() {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	void computeBounds() {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	void setNodeWidth(int width) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public void edit(GfxObject editedGfxObject) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public int getHeight() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}
	@Override
	public MenuBarAndTitle getRightMenu() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	@Override
	public int getWidth() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}
	@Override
	public String toURL() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	@Override
	protected void buildGfxObject() {
		// TODO 自動生成されたメソッド・スタブ

	}

	public List<UMLClassAttribute_Yamazaki> getList() {
		// TODO 自動生成されたメソッド・スタブ
		return attributes;
	}

	public void add(UMLClassAttribute_Yamazaki attribute) {
		// TODO 自動生成されたメソッド・スタブ
		attributes.add(attribute);
	}
}
