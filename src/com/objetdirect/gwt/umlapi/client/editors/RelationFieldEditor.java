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


import com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.mylogger.MyLoggerExecute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLRelation;

/**
 * This field editor is a specialized editor for {@link UMLRelation} editing
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class RelationFieldEditor extends FieldEditor {

	RelationLinkArtifactPart	relationshipPart;

	/**
	 * Constructor of the {@link RelationFieldEditor}
	 *
	 * @param canvas
	 *            The canvas on which is the artifact
	 * @param artifact
	 *            The artifact being edited
	 * @param relationshipPart
	 *            The {@link RelationLinkArtifactPart} of {@link RelationLinkArtifact} on which edition has been requested
	 */
	public RelationFieldEditor(final UMLCanvas canvas, final RelationLinkArtifact artifact, final RelationLinkArtifactPart relationshipPart) {
		super(canvas, artifact);
		this.relationshipPart = relationshipPart;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.objetdirect.gwt.umlapi.client.editors.FieldEditor#next()
	 */
	@Override
	protected void next() {
		// No next part to edit
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.objetdirect.gwt.umlapi.client.editors.FieldEditor#updateUMLArtifact(java.lang.String)
	 */
	@Override
	protected boolean updateUMLArtifact(final String newContent) {

		//TODO takafumi RelationField Changeed Log [Making]
		RelationLinkArtifact rla = (RelationLinkArtifact) this.artifact;
		String part = this.relationshipPart.name();
		String oldContent = rla.getPartContent(this.relationshipPart);

		System.out.println("Relationship:"+rla.toURL()+":"+rla.toString()+" ==> "+newContent);
		((RelationLinkArtifact) this.artifact).setPartContent(this.relationshipPart, newContent);
		//MyLoggerExecute.registEditEvent("Relationship:"+rla.getId()+":"+rla.toString()+":"+rla.toString()+":"+newContent, canvas.toUrl());

		MyLoggerExecute.registEditEvent(-1, "Relation", "Edit",
				rla.getClass().getName(), rla.getId(), null, rla.getLeftUMLArtifact().getId(), rla.getRightUMLArtifact().getId(),
				part, oldContent, newContent, this.canvas.toUrl());

//		int preEventId, String editEvent, String eventType,
//		String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//		String targetPart, String beforeEdit, String afterEdit, String canvasUrl

		this.artifact.rebuildGfxObject();
		return false;
	}

}
