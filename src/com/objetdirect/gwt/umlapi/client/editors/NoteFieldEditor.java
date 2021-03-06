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


import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.mylogger.MyLoggerExecute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLNote;

/**
 * This field editor is a specialized editor for {@link UMLNote} editing
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class NoteFieldEditor extends FieldEditor {
	/**
	 * Constructor of the {@link NoteFieldEditor}
	 *
	 * @param canvas
	 *            The canvas on which is the artifact
	 * @param artifact
	 *            The artifact being edited
	 */
	public NoteFieldEditor(final UMLCanvas canvas, final NoteArtifact artifact) {
		super(canvas, artifact);
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
		if (newContent.trim().equals("")) {
			this.canvas.remove(this.artifact);
			return false;
		}

		//TODO takafumi Note Changeed Log
		NoteArtifact target = ((NoteArtifact) this.artifact);
		String oldContent = target.getContent();

		((NoteArtifact) this.artifact).setContent(newContent);

		this.artifact.rebuildGfxObject();
//		MyLoggerExecute.registEditEvent("Note:"+this.artifact.getId()+":"+oldContent+":"+newContent, canvas.toUrl());

		MyLoggerExecute.registEditEvent(-1, "Note", "Edit",
				target.getClass().getName(), target.getId(), null, -1, -1,
				null, oldContent, newContent, this.canvas.toUrl(), UMLArtifact.getIdCount());

//		int preEventId, String editEvent, String eventType,
//		String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//		String targetPart, String beforeEdit, String afterEdit, String canvasUrl

		System.out.println("EditNote:"+oldContent+":"+newContent);


		return false;
	}
}
