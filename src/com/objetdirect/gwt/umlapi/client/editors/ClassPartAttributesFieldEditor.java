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
package com.objetdirect.gwt.umlapi.client.editors;


import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartAttributesArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NodePartArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectPartAttributesArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.mylogger.MyLoggerExecute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ClassPartAttributesFieldEditor extends FieldEditor {

	UMLClassAttribute	attributeToChange;

	/**
	 * Constructor of ClassPartAttributesEditor
	 *
	 * @param canvas
	 * @param classPartAttributesArtifact
	 * @param attributeToChange
	 */
	public ClassPartAttributesFieldEditor(final UMLCanvas canvas, final ClassPartAttributesArtifact classPartAttributesArtifact,
			final UMLClassAttribute attributeToChange) {
		super(canvas, classPartAttributesArtifact);
		this.attributeToChange = attributeToChange;
	}

	/**
	 * Constructor of ClassPartAttributesEditor
	 *
	 * @param canvas
	 * @param objectPartAttributesArtifact
	 * @param attributeToChange
	 */
	public ClassPartAttributesFieldEditor(final UMLCanvas canvas, final ObjectPartAttributesArtifact objectPartAttributesArtifact,
			final UMLClassAttribute attributeToChange) {
		super(canvas, objectPartAttributesArtifact);
		this.attributeToChange = attributeToChange;
	}

	@Override
	protected void next() {
		((NodePartArtifact) this.artifact).edit();
	}

	@Override
	protected boolean updateUMLArtifact(final String newContent) {
		String oldContent = this.attributeToChange.toString();
		ClassPartAttributesArtifact cpm = (ClassPartAttributesArtifact) this.artifact;
		ClassArtifact classArtifact = (ClassArtifact)cpm.getNodeArtifact();

		UMLClassAttribute uca = new UMLClassAttribute(null, "", "");

		if (newContent.trim().equals("")) {
			((ClassPartAttributesArtifact) this.artifact).remove(this.attributeToChange);
			((ClassPartAttributesArtifact) this.artifact).getNodeArtifact().rebuildGfxObject();
			return false;
		}

		final UMLClassAttribute newAttribute = UMLClassAttribute.parseAttribute(newContent);
		if ((newAttribute.getName() + newAttribute.getType()).equals("")) {
			((ClassPartAttributesArtifact) this.artifact).remove(this.attributeToChange);
			((ClassPartAttributesArtifact) this.artifact).getNodeArtifact().rebuildGfxObject();
			return false;
		}
		this.attributeToChange.setVisibility(newAttribute.getVisibility());
		this.attributeToChange.setName(newAttribute.getName());
		this.attributeToChange.setType(newAttribute.getType());

		if(oldContent.equals(newAttribute.toString())){
			//Nothing to do
		}
		else if(oldContent.replaceAll("\\s", "").equals("-attribute:String")){
		MyLoggerExecute.registEditEvent(-1, "Attribute", "Create",
				newAttribute.getClass().getName(), classArtifact.getId(), null, -1, -1,
				null, oldContent, newContent, this.canvas.toUrl(), UMLArtifact.getIdCount());
		}else {
			MyLoggerExecute.registEditEvent(-1, "Attribute", "Edit",
					newAttribute.getClass().getName(), classArtifact.getId(), null, -1, -1,
					null, oldContent, newContent, this.canvas.toUrl(), UMLArtifact.getIdCount());
		}
//		int preEventId, String editEvent, String eventType,
//		String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//		String targetPart, String beforeEdit, String afterEdit, String canvasUrl

		//TODO takafumi Class Attributes Changeed Log


		((ClassPartAttributesArtifact) this.artifact).getNodeArtifact().rebuildGfxObject();
		return true;
	}
}
