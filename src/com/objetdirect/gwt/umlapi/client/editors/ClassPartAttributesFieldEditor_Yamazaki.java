package com.objetdirect.gwt.umlapi.client.editors;

import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifactUMLDS_Yamazaki;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartAttributesArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartAttributesNameArtifact_Yamazaki;
import com.objetdirect.gwt.umlapi.client.artifacts.NodePartArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectPartAttributesArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.mylogger.MyLoggerExecute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLAttributeName_Yamazaki;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;

public class ClassPartAttributesFieldEditor_Yamazaki extends FieldEditor{

	UMLAttributeName_Yamazaki attributeToChange_yamazaki;

	public ClassPartAttributesFieldEditor_Yamazaki(final UMLCanvas canvas, final ClassPartAttributesNameArtifact_Yamazaki classPartAttributesArtifact_Yamazaki,
			final UMLAttributeName_Yamazaki attributeToChange)
	{
		super(canvas,classPartAttributesArtifact_Yamazaki);
		this.attributeToChange_yamazaki = attributeToChange;
	}

	public ClassPartAttributesFieldEditor_Yamazaki(final UMLCanvas canvas, final ObjectPartAttributesArtifact objectPartAttributesArtifact,
			final UMLAttributeName_Yamazaki attributeToChange) {
		super(canvas, objectPartAttributesArtifact);
		this.attributeToChange_yamazaki = attributeToChange;
	}

	protected void next() {
		// TODO 自動生成されたメソッド・スタブ
		((NodePartArtifact) this.artifact).edit();
	}


	protected boolean updateUMLArtifact(String newContent) {
		// TODO 自動生成されたメソッド・スタブ

		String oldContent = this.attributeToChange_yamazaki.toString();
		ClassPartAttributesArtifact cpm = (ClassPartAttributesArtifact) this.artifact;
		ClassArtifactUMLDS_Yamazaki classArtifact = (ClassArtifactUMLDS_Yamazaki)cpm.getNodeArtifact();

		if (newContent.trim().equals("")) {
			//値が何も入力されなかった時の巻き戻しの処理を考えないとかもしれない
			//((ClassPartAttributesArtifact) this.artifact).remove(this.attributeToChange_yamazaki);
			((ClassPartAttributesArtifact) this.artifact).getNodeArtifact().rebuildGfxObject();
			return false;
		}

		final UMLClassAttribute newAttribute = UMLClassAttribute.parseAttribute(newContent);
		if ((newAttribute.getName() + newAttribute.getType()).equals("")) {
			//((ClassPartAttributesArtifact) this.artifact).remove(this.attributeToChange_yamazaki);
			((ClassPartAttributesArtifact) this.artifact).getNodeArtifact().rebuildGfxObject();
			return false;
		}

		this.attributeToChange_yamazaki.setName(newAttribute.getName());

		((ClassPartAttributesArtifact) this.artifact).getNodeArtifact().rebuildGfxObject();
		if(oldContent.equals(newAttribute.toString())){
			//Nothing to do
		}
		else if(oldContent.trim().equals("")){
		MyLoggerExecute.registEditEvent(-1, "Attribute", "Create",
				newAttribute.getClass().getName(), classArtifact.getId(), null, -1, -1,
				null, oldContent, newContent, this.canvas.toUrl(), UMLArtifact.getIdCount());
		}else {
			MyLoggerExecute.registEditEvent(-1, "Attribute", "Edit",
					newAttribute.getClass().getName(), classArtifact.getId(), null, -1, -1,
					null, oldContent, newContent, this.canvas.toUrl(), UMLArtifact.getIdCount());
		}

		return true;
	}

}
