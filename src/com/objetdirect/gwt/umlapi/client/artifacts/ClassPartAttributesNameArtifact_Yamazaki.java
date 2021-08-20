package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.editors.ClassPartAttributesFieldEditor;
import com.objetdirect.gwt.umlapi.client.editors.ClassPartAttributesFieldEditor_Yamazaki;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.GWTUMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.mylogger.MyLoggerExecute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLAttributeName_Yamazaki;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;

public class ClassPartAttributesNameArtifact_Yamazaki extends NodePartArtifact  {

	private final Map<GfxObject,UMLAttributeName_Yamazaki>	attributeNameGfxObjects;
	private GfxObject								attributeNameRect;
	private UMLAttributeName_Yamazaki			attributesName;
	private GfxObject								lastGfxObject;

	public ClassPartAttributesNameArtifact_Yamazaki()
	{
		super();
		this.attributesName = null;
		this.attributeNameGfxObjects = new LinkedHashMap<GfxObject, UMLAttributeName_Yamazaki>();
		this.height = 0;
		this.width = 0;
	}

	@Override
	protected void buildGfxObject() {
		// TODO 自動生成されたメソッド・スタブ
		if (this.textVirtualGroup == null) {
			this.computeBounds();
		}
		this.attributeNameRect = GfxManager.getPlatform().buildRect(this.nodeWidth, this.height);
		GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.attributeNameRect);
		GfxManager.getPlatform().setFillColor(this.attributeNameRect, GfxColor.AQUA);
		GfxManager.getPlatform().setStroke(this.attributeNameRect, ThemeManager.getTheme().getClassForegroundColor(), 1);
		GfxManager.getPlatform().translate(this.textVirtualGroup,
				new Point(OptionsManager.get("RectangleLeftPadding"), OptionsManager.get("RectangleTopPadding")));
		GfxManager.getPlatform().moveToFront(this.textVirtualGroup);
	}

	@Override
	void computeBounds() {
		// TODO 自動生成されたメソッド・スタブ
		this.attributeNameGfxObjects.clear();
		this.height = 0;
		this.width = 0;
		this.textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.textVirtualGroup);


		final GfxObject attributeText = GfxManager.getPlatform().buildText(attributesName.toString(),
				new Point(OptionsManager.get("TextLeftPadding"), OptionsManager.get("TextTopPadding") + this.height));
		GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup, attributeText);
		GfxManager.getPlatform().setFont(attributeText, OptionsManager.getSmallFont());
		GfxManager.getPlatform().setStroke(attributeText, ThemeManager.getTheme().getClassBackgroundColor(), 0);
		GfxManager.getPlatform().setFillColor(attributeText, GfxColor.AQUA);
		int thisAttributeWidth = GfxManager.getPlatform().getTextWidthFor(attributeText);
		int thisAttributeHeight = GfxManager.getPlatform().getTextHeightFor(attributeText);
		thisAttributeWidth += OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding");
		thisAttributeHeight += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
		this.width = thisAttributeWidth > this.width ? thisAttributeWidth : this.width;
		this.height += thisAttributeHeight;
		this.attributeNameGfxObjects.put(attributeText, attributesName);
		this.lastGfxObject = attributeText;

		this.width += OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding");
		this.height += OptionsManager.get("RectangleTopPadding") + OptionsManager.get("RectangleBottomPadding");

		Log.trace("WxH for " + GWTUMLDrawerHelper.getShortName(this) + "is now " + this.width + "x" + this.height);
	}

	@Override
	public void edit() {
		// TODO 自動生成されたメソッド・スタブ
		attributesName = new UMLAttributeName_Yamazaki("Sample");
		this.nodeArtifact.rebuildGfxObject();
		this.attributeNameGfxObjects.put(this.lastGfxObject, attributesName);
		this.edit(lastGfxObject);
	}

	@Override
	public void edit(GfxObject editedGfxObject) {
		// TODO 自動生成されたメソッド・スタブ
		final UMLAttributeName_Yamazaki attributeToChange = this.attributeNameGfxObjects.get(editedGfxObject);
		if (attributeToChange == null) {
			this.edit();
		} else {
			final ClassPartAttributesFieldEditor_Yamazaki editor = new ClassPartAttributesFieldEditor_Yamazaki(this.canvas, this, attributeToChange);
			editor.startEdition(attributeToChange.toString(), (this.nodeArtifact.getLocation().getX() + OptionsManager.get("TextLeftPadding") + OptionsManager
					.get("RectangleLeftPadding")), (this.nodeArtifact.getLocation().getY() + ((ClassArtifact) this.nodeArtifact).className.getHeight()
					+ GfxManager.getPlatform().getLocationFor(editedGfxObject).getY() + OptionsManager.get("RectangleTopPadding")), this.nodeWidth
					- OptionsManager.get("TextRightPadding") - OptionsManager.get("TextLeftPadding") - OptionsManager.get("RectangleRightPadding")
					- OptionsManager.get("RectangleLeftPadding"), false, true);
		}
	}

	/**
	 * Remove an attribute to the current attribute list. The graphical object must be rebuilt to reflect the changes
	 *
	 * @param attribute
	 *            The attribute to be removed
	 */
	public void remove(final UMLAttributeName_Yamazaki attribute) {
		// Listを持ってるわけじゃないからどうしようか
		attributesName = new UMLAttributeName_Yamazaki();
		//this.attributes.remove(attribute);
		ClassArtifactUMLDS_Yamazaki classArtifact = (ClassArtifactUMLDS_Yamazaki) (this.getNodeArtifact() );

		if(!attribute.toString().equals("")){ //初期値の空白でなければRemoveイベントを記録

			MyLoggerExecute.registEditEvent(-1, "Attribute", "Remove",
					attribute.getClass().getName(), classArtifact.getId(), null, -1, -1,
					null, attribute.toString(), "", null, UMLArtifact.getIdCount());

			MyLoggerExecute.registEditEvent(-1, "Attribute", "RemoveArtifacts",
					attribute.getClass().getName(), classArtifact.getId(), null, -1, -1,
					null, attribute.toString(), "", this.canvas.toUrl(), UMLArtifact.getIdCount());
		}

	}


	@Override
	void setNodeWidth(int width) {
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


}
