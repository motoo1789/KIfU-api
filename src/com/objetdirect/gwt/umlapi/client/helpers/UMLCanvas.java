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
package com.objetdirect.gwt.umlapi.client.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.InstantiationRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LifeLineArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact.LinkAdornment;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact.LinkStyle;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkClassRelationArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkNoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.MessageLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.editors.FieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Direction;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.engine.Scheduler;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObjectListener;
import com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.CursorIconManager.PointerStyle;
import com.objetdirect.gwt.umlapi.client.mylogger.MyLoggerExecute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLifeLine;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObjectAttribute;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class UMLCanvas extends AbsolutePanel {

	private enum DragAndDropState {
		TAKING, DRAGGING, NONE, PREPARING_SELECT_BOX, SELECT_BOX;
	}

	private static long										classCount						= 1;
	private static long										objectCount						= 1;
	private static long										lifeLineCount					= 1;
	private String											copyBuffer 						= "";
	private long											noteCount;
	private LinkKind										activeLinking;
	private Point											selectBoxStartPoint;
	private GfxObject										selectBox;
	private final Label										helpText						= new Label("");
	private final Widget									drawingCanvas;																	// Drawing canvas
	private DragAndDropState								dragAndDropState				= DragAndDropState.NONE;
	private boolean 										wasACopy;
	private Point											currentMousePosition			= new Point(-200, -200);						// In case of the

	private int												height									=GfxPlatform.DEFAULT_CANVAS_HEIGHT;
	private int												width									=GfxPlatform.DEFAULT_CANVAS_WIDTH;
	// mouse hasn't been
	// moved before
	// adding an
	// artifact
	private final Point										canvasOffset					= Point.getOrigin();
	private Point											duringDragOffset				= Point.getOrigin();
	private HashMap<UMLArtifact, ArrayList<Point>>			previouslySelectedArtifacts;

	private final UMLDiagram								uMLDiagram;
	private boolean											isMouseEnabled					= true;
	private final GfxObjectListener							gfxObjectListener				= new GfxObjectListener() {

		@Override
		public void mouseDoubleClicked(final GfxObject graphicObject,
				final Event event) {
			if (UMLCanvas.this.isMouseEnabled) {
				Mouse.doubleClick(graphicObject, new Point(event
						.getClientX(), event.getClientY()), event
						.getButton(), event.getCtrlKey(), event
						.getAltKey(), event.getShiftKey(), event
						.getMetaKey());
			}
		}

		@Override
		public void mouseMoved(final Event event) {
			if (UMLCanvas.this.isMouseEnabled) {
				Mouse.move(new Point(event.getClientX(), event
						.getClientY()), event.getButton(), event
						.getCtrlKey(), event.getAltKey(), event
						.getShiftKey(), event.getMetaKey());
			}
		}

		@Override
		public void mousePressed(final GfxObject graphicObject,
				final Event event) {
			if (UMLCanvas.this.isMouseEnabled) {
				Mouse.press(graphicObject, new Point(
						event.getClientX(), event.getClientY()), event
						.getButton(), event.getCtrlKey(), event
						.getAltKey(), event.getShiftKey(), event
						.getMetaKey());
			}
		}

		@Override
		public void mouseReleased(final GfxObject graphicObject,
				final Event event) {
			if (UMLCanvas.this.isMouseEnabled) {
				Mouse.release(graphicObject, new Point(event
						.getClientX(), event.getClientY()), event
						.getButton(), event.getCtrlKey(), event
						.getAltKey(), event.getShiftKey(), event
						.getMetaKey());
			}
		}

	};

	private boolean											mouseIsPressed					= false;										// Manage mouse
	// state when
	// releasing outside
	// the listener

	private final GfxObject									movingLines						= GfxManager.getPlatform().buildVirtualGroup();

	private final GfxObject									movingOutlineDependencies		= GfxManager.getPlatform().buildVirtualGroup();

	private final GfxObject									outlines						= GfxManager.getPlatform().buildVirtualGroup();

	private final GfxObject									allObjects						= GfxManager.getPlatform().buildVirtualGroup();

	private final Map<GfxObject, UMLArtifact>					objects							= new HashMap<GfxObject, UMLArtifact>();	// Map of
	// UMLArtifact with
	// corresponding
	// Graphical objects
	// (group)

	private final Set<UMLArtifact>							objectsToBeAddedWhenAttached	= new LinkedHashSet<UMLArtifact>();

	private final HashMap<UMLArtifact, ArrayList<Point>>	selectedArtifacts				= new HashMap<UMLArtifact, ArrayList<Point>>(); // Represent the
	// selected
	// UMLArtifacts and
	// his moving
	// dependencies
	// points

	private final ArrayList<GWTUMLEventListener>			uMLEventListenerList			= new ArrayList<GWTUMLEventListener>();
	private Point											dragOffset;
	private Point											totalDragShift					= Point.getOrigin();
	private GfxObject										arrowsVirtualGroup;
	private Point	copyMousePosition;

	/**
	 * Constructor of an {@link UMLCanvas} with default size
	 *
	 * @param uMLDiagram
	 *            The {@link UMLDiagram} this {@link UMLCanvas} is drawing
	 *
	 */
	public UMLCanvas(final UMLDiagram uMLDiagram) {
		super();
		Log.trace("Making Canvas");
		this.drawingCanvas = GfxManager.getPlatform().makeCanvas();
		this.setPixelSize(GfxPlatform.DEFAULT_CANVAS_WIDTH, GfxPlatform.DEFAULT_CANVAS_HEIGHT);
		this.height = GfxPlatform.DEFAULT_CANVAS_HEIGHT;
		this.width  = GfxPlatform.DEFAULT_CANVAS_WIDTH;
		this.initCanvas();
		this.uMLDiagram = uMLDiagram;
	}

	/**
	 * Constructor of an {@link UMLCanvas} with the specified size
	 *
	 * @param uMLDiagram
	 *            The {@link UMLDiagram} this {@link UMLCanvas} is drawing
	 * @param width
	 *            The uml canvas width
	 * @param height
	 *            The uml canvas height
	 */
	public UMLCanvas(final UMLDiagram uMLDiagram, final int width, final int height) {
		super();
		Log.trace("Making " + width + " x " + height + " Canvas");
		this.drawingCanvas = GfxManager.getPlatform().makeCanvas(width, height, ThemeManager.getTheme().getCanvasColor());
		this.drawingCanvas.getElement().setAttribute("oncontextmenu", "return false");

		//TODO 20140714
		this.addUMLEventListener(new GWTUMLEventListenerForLog());

		this.setPixelSize(width, height);
		this.height = height;
		this.width = width;
		this.initCanvas();
		this.uMLDiagram = uMLDiagram;

		//TODO 20150531コメントアウト
//		Window.addWindowClosingHandler(new Window.ClosingHandler() {
//		    public void onWindowClosing(Window.ClosingEvent closingEvent) {
//		    	MyLoggerExecute.registEditEvent(-1,null, "CanvasClose",
//						null, -1, null, -1, -1,
//						null, null, null, null, UMLArtifact.getIdCount());
////				int preEventId, String editEvent, String eventType,
////				String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
////				String targetPart, String beforeEdit, String afterEdit, String canvasUrl
//		    }
//		});
	}

	/**
	 * Add an {@link UMLArtifact} to this canvas
	 *
	 * @param artifact
	 *            The {@link UMLArtifact} to add
	 */
	public void add(final UMLArtifact artifact) {
		if (artifact == null) {
			Log.info("Adding null element to canvas");
			return;
		}
		if (this.isAttached()) {
			artifact.setCanvas(this);
			final long t = System.currentTimeMillis();
			GfxManager.getPlatform().addToVirtualGroup(this.allObjects, artifact.initializeGfxObject());

			GfxManager.getPlatform().translate(artifact.getGfxObject(), artifact.getLocation());
			this.objects.put(artifact.getGfxObject(), artifact);
			Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to add " + artifact);
			System.out.println("add artifact into canvas");

		} else {
			Log.debug("Canvas not attached, queuing " + artifact);
			this.objectsToBeAddedWhenAttached.add(artifact);
		}
	}

	/**
	 * Add a new class with default values to this canvas to an the current mouse position
	 */
	public void addNewClass() {
		this.addNewClass(this.currentMousePosition);

	}

	/**
	 * Add a new lifeLine with default values to this canvas to the current mouse position
	 */
	public void addNewLifeLine() {
		this.addNewLifeLine(this.currentMousePosition);

	}

	/**
	 * Add a new object with default values to this canvas to the current mouse position
	 */
	public void addNewObject() {
		this.addNewObject(this.currentMousePosition);

	}

	/**
	 * Add an {@link GWTUMLEventListener} to this canvas
	 *
	 * @param uMLEventListener
	 *            The {@link GWTUMLEventListener} to add to this canvas
	 */
	public void addUMLEventListener(final GWTUMLEventListener uMLEventListener) {
		this.uMLEventListenerList.add(uMLEventListener);
	}

	/**
	 * Remove the direction arrows from the canvas
	 *
	 */
	public void clearArrows() {
		GfxManager.getPlatform().clearVirtualGroup(this.arrowsVirtualGroup);

	}

	/**
	 * Deselect an artifact and put it in the artifact list
	 *
	 * @param toDeselect
	 *            The artifact to be deselected
	 */
	public void deselectArtifact(final UMLArtifact toDeselect) {
		this.selectedArtifacts.remove(toDeselect);

		toDeselect.unselect();
		//MyLoggerExecute.registEditEvent("Deselect"+this.toUrl());
	}

	/**
	 * Create a diagram from the URL {@link String} previously generated by {@link UMLCanvas#toUrl()}
	 *
	 * @param url
	 *            The {@link String} previously generated by {@link UMLCanvas#toUrl()}
	 *
	 * @param isForPasting True if the parsing is for copy pasting
	 *
	 */
	public void fromURL(final String url, final boolean isForPasting) {
		//try {
		if (!url.equals("AA==")) {
			String diagram = isForPasting ? url : GWTUMLDrawerHelper.decodeBase64(url);
			Point pasteShift = isForPasting ? Point.substract(Point.substract(this.currentMousePosition, this.copyMousePosition), this.canvasOffset) : Point.getOrigin();

			diagram = diagram.substring(0, diagram.lastIndexOf(";"));
			final String[] diagramArtifacts = diagram.split(";");

			for (final String artifactWithParameters : diagramArtifacts) {
				if (!artifactWithParameters.equals("")) {
					final String[] artifactAndParameters = artifactWithParameters.split("\\$");
					if (artifactAndParameters.length > 1) {
						final String[] artifactAndId = artifactAndParameters[0].split("]");
						final String[] parameters = artifactAndParameters[1].split("!", -1);
						final String artifact = artifactAndId[1];
						int id = 0;
						System.out.println("fromURL : "+artifactAndParameters[1]);
						try {
							id = Integer.parseInt(artifactAndId[0].replaceAll("[<>]", ""));
						} catch (final Exception ex) {
							Log.error("Parsing url, artifact id is NaN : " + artifactWithParameters + " : " + ex);
						}
						UMLArtifact newArtifact = null;
						if (artifact.equals("Class")) {
							newArtifact = new ClassArtifact((isForPasting && wasACopy ? "CopyOf" : "") +UMLClass.parseNameOrStereotype(parameters[1]), UMLClass.parseNameOrStereotype(parameters[2]));
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));
							if (parameters[3].length() > 1) {
								final String[] classAttributes = parameters[3].substring(0, parameters[3].lastIndexOf("%")).split("%");
								for (final String attribute : classAttributes) {
									((ClassArtifact) newArtifact).addAttribute(UMLClassAttribute.parseAttribute(attribute));
								}
							}
							if (parameters[4].length() > 1) {
								final String[] classMethods = parameters[4].substring(0, parameters[4].lastIndexOf("%")).split("%");
								for (final String method : classMethods) {
									((ClassArtifact) newArtifact).addMethod(UMLClassMethod.parseMethod(method));
								}
							}

						} else if (artifact.equals("Object")) {
							newArtifact = new ObjectArtifact(UMLObject.parseName(parameters[1]).get(0), (isForPasting && wasACopy ? "CopyOf" : "") + UMLObject.parseName(parameters[1]).get(1),
									UMLObject.parseStereotype(parameters[2]));
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));
							if (parameters[3].length() > 1) {
								final String[] objectAttributes = parameters[3].substring(0, parameters[3].lastIndexOf("%")).split("%");
								for (final String attribute : objectAttributes) {
									((ObjectArtifact) newArtifact).addAttribute(UMLObjectAttribute.parseAttribute(attribute));
								}
							}

						} else if (artifact.equals("LifeLine")) {
							newArtifact = new LifeLineArtifact((isForPasting && wasACopy ? "CopyOf" : "") + UMLLifeLine.parseName(parameters[1]).get(1), UMLLifeLine.parseName(parameters[1]).get(0));
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));

						} else if (artifact.equals("Note")) {
							newArtifact = new NoteArtifact(parameters[1]);
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));

						} else if (artifact.equals("LinkNote")) {
							Integer noteId = 0;
							Integer targetId = 0;
							try {
								noteId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								targetId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Log.error("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new LinkNoteArtifact((NoteArtifact) UMLArtifact.getArtifactById(noteId), UMLArtifact.getArtifactById(targetId));

						} else if (artifact.equals("LinkClassRelation")) {
							Integer classId = 0;
							Integer relationId = 0;
							try {
								classId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								relationId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Log.error("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new LinkClassRelationArtifact((ClassArtifact) UMLArtifact.getArtifactById(classId),
									(ClassRelationLinkArtifact) UMLArtifact.getArtifactById(relationId));

						} else if (artifact.equals("ClassRelationLink")) {
							Integer classLeftId = 0;
							Integer classRigthId = 0;
							try {
								classLeftId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								classRigthId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Log.error("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new ClassRelationLinkArtifact((ClassArtifact) UMLArtifact.getArtifactById(classLeftId),
									(ClassArtifact) UMLArtifact.getArtifactById(classRigthId), LinkKind.getRelationKindFromName(parameters[2]));
							((ClassRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((ClassRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((ClassRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((ClassRelationLinkArtifact) newArtifact).setLeftCardinality(parameters[6]);
							((ClassRelationLinkArtifact) newArtifact).setLeftConstraint(parameters[7]);
							((ClassRelationLinkArtifact) newArtifact).setLeftRole(parameters[8]);
							((ClassRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[9]));
							((ClassRelationLinkArtifact) newArtifact).setRightCardinality(parameters[10]);
							((ClassRelationLinkArtifact) newArtifact).setRightConstraint(parameters[11]);
							((ClassRelationLinkArtifact) newArtifact).setRightRole(parameters[12]);

						} else if (artifact.equals("ObjectRelationLink")) {
							Integer objectLeftId = 0;
							Integer objectRigthId = 0;
							try {
								objectLeftId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								objectRigthId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Log.error("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new ObjectRelationLinkArtifact((ObjectArtifact) UMLArtifact.getArtifactById(objectLeftId),
									(ObjectArtifact) UMLArtifact.getArtifactById(objectRigthId), LinkKind.getRelationKindFromName(parameters[2]));
							((ObjectRelationLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((ObjectRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((ObjectRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((ObjectRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[6]));

						} else if (artifact.equals("MessageLink")) {
							Integer lifeLineLeftId = 0;
							Integer lifeLineRigthId = 0;
							try {
								lifeLineLeftId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								lifeLineRigthId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Log.error("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new MessageLinkArtifact((LifeLineArtifact) UMLArtifact.getArtifactById(lifeLineLeftId),
									(LifeLineArtifact) UMLArtifact.getArtifactById(lifeLineRigthId), LinkKind.getRelationKindFromName(parameters[2]));
							((MessageLinkArtifact) newArtifact).setName((isForPasting && wasACopy ? "CopyOf" : "") + parameters[3]);
							((MessageLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((MessageLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((MessageLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[6]));

						} else if (artifact.equals("InstantiationRelationLink")) {
							Integer classId = 0;
							Integer objectId = 0;
							try {
								classId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								objectId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Log.error("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new InstantiationRelationLinkArtifact((ClassArtifact) UMLArtifact.getArtifactById(classId),
									(ObjectArtifact) UMLArtifact.getArtifactById(objectId), LinkKind.INSTANTIATION);
						}
						if (newArtifact != null) {
							newArtifact.setId(id);
							this.add(newArtifact);
						}
						if(isForPasting) {
							selectArtifact(newArtifact);

						}
					}
				}
			}
		}
		//} catch (final Exception ex) {
		//	Log.error("There was a problem reading diagram in url : ");
		//}
	}

	/**
	 * Return the first artifact found at the specified location
	 *
	 * @param location
	 *            The location to look for an artifact
	 *
	 * @return The first artifact found at the specified location or null if there is no artifact there
	 */
	public GfxObject getArtifactAt(final Point location) {
		for (final UMLArtifact artifact : this.objects.values()) {
			if (this.isIn(artifact.getLocation(), Point.add(artifact.getLocation(), new Point(artifact.getWidth(), artifact.getHeight())), location, location)) {
				Log.info("Artifact : " + artifact + " found");
				return artifact.getGfxObject();
			}
		}
		Log.info("No Artifact Found !");
		return null;
	}

	/**
	 * Getter for the canvasOffset
	 *
	 * @return the canvasOffset
	 */
	public Point getCanvasOffset() {
		return this.canvasOffset;
	}

	/**
	 * Getter for the graphic canvas
	 *
	 * @return The graphic canvas
	 */
	public Widget getDrawingCanvas() {
		return this.drawingCanvas;
	}

	/**
	 * Getter for the uMLDiagram
	 *
	 * @return the uMLDiagram
	 */
	public UMLDiagram getUMLDiagram() {
		return this.uMLDiagram;
	}

	/**
	 * Draw the directions arrow on the canvas
	 *
	 */
	public void makeArrows() {
		final int arrowSize = 6;
		int width = this.getOffsetWidth();
		int height = this.getOffsetHeight();
		this.arrowsVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		GfxManager.getPlatform().addToCanvas(this.drawingCanvas, this.arrowsVirtualGroup, Point.getOrigin());
		final ArrayList<GfxObject> arrowList = new ArrayList<GfxObject>();
		for (float f = 0; f < 360; f += 45) {
			final GfxObject arrow = GfxManager.getPlatform().buildPath();
			arrowList.add(arrow);
			GfxManager.getPlatform().moveTo(arrow, Point.getOrigin());
			GfxManager.getPlatform().lineTo(arrow, new Point(arrowSize, 0));
			GfxManager.getPlatform().lineTo(arrow, new Point(2 * arrowSize, arrowSize));
			GfxManager.getPlatform().lineTo(arrow, new Point(arrowSize, 2 * arrowSize));
			GfxManager.getPlatform().lineTo(arrow, new Point(0, 2 * arrowSize));
			GfxManager.getPlatform().lineTo(arrow, new Point(arrowSize, arrowSize));
			GfxManager.getPlatform().lineTo(arrow, Point.getOrigin());
			GfxManager.getPlatform().setFillColor(arrow, ThemeManager.getTheme().getDefaultForegroundColor());
			GfxManager.getPlatform().setStroke(arrow, ThemeManager.getTheme().getDefaultForegroundColor(), 1);
			GfxManager.getPlatform().rotate(arrow, f, new Point(arrowSize, arrowSize));
			GfxManager.getPlatform().addToVirtualGroup(this.arrowsVirtualGroup, arrow);
		}

		GfxManager.getPlatform().translate(arrowList.get(0), new Point(width - 2 * arrowSize - 2, height / 2 - arrowSize - 2)); // right
		GfxManager.getPlatform().translate(arrowList.get(1), new Point(width - 2 * arrowSize - 2, height - 2 * arrowSize - 2)); // bottom right
		GfxManager.getPlatform().translate(arrowList.get(2), new Point(width / 2 - arrowSize - 2, height - 2 * arrowSize - 2)); // bottom
		GfxManager.getPlatform().translate(arrowList.get(3), new Point(2, height - 2 * arrowSize - 2)); // bottom left
		GfxManager.getPlatform().translate(arrowList.get(4), new Point(2, height / 2 - arrowSize - 2)); // left
		GfxManager.getPlatform().translate(arrowList.get(5), new Point(2, 2)); // up left
		GfxManager.getPlatform().translate(arrowList.get(6), new Point(width / 2 - arrowSize - 2, 2)); // up
		GfxManager.getPlatform().translate(arrowList.get(7), new Point(width - 2 * arrowSize - 2, 2)); // up right
	}

	/**
	 * Move all artifact in the specified {@link Direction}
	 *
	 * @param direction
	 *            The {@link Direction} to move the artifact by
	 * @param isRecursive
	 *            True if the method call is recursive
	 */
	public void moveAll(final Direction direction, final boolean isRecursive) {
		new Scheduler.Task("MovingAllArtifacts") {
			@Override
			public void process() {
				final Point translation = new Point(-direction.getXShift(), -direction.getYShift());
				GfxManager.getPlatform().translate(UMLCanvas.this.allObjects, translation);
				UMLCanvas.this.canvasOffset.translate(translation);
				UMLCanvas.this.duringDragOffset.translate(translation);
				UMLCanvas.this.mouseMoved(UMLCanvas.this.currentMousePosition, false, false);
				GfxManager.getPlatform().translate(UMLCanvas.this.movingLines, translation);
				GfxManager.getPlatform().translate(UMLCanvas.this.movingOutlineDependencies, translation);
				if (FieldEditor.getEditField() != null) {
					UMLCanvas.this.setWidgetPosition(FieldEditor.getEditField(), (int) (FieldEditor.getEditField().getAbsoluteLeft() - direction.getXShift()),
							(int) (FieldEditor.getEditField().getAbsoluteTop() - direction.getYShift()));
				}
			}
		};

		if (isRecursive) {
			new Scheduler.Task("MovingAllArtifactsRecursive", 5) {
				@Override
				public void process() {
					UMLCanvas.this.moveAll(direction, true);
				}
			};
		}
	}

	/**
	 * Remove the specified artifact from the canvas
	 *
	 * @param umlArtifact
	 */
	//Removeが発生すると最初に呼ばれる。このメソッドの最後でRemoveArtifactsのログを取る
	//removeRecursiveから呼ばれる。
		public void remove(final UMLArtifact umlArtifact) {
			if (this.fireDeleteArtifactEvent(umlArtifact)) {
				this.removeRecursive(umlArtifact);
				if (umlArtifact.isALink()) {
					((LinkArtifact) umlArtifact).removeCreatedDependency();
				}
			}
		}
	private void removeRecursive(final UMLArtifact element) {
		GfxManager.getPlatform().removeFromVirtualGroup(this.allObjects, element.getGfxObject(), false);
		this.objects.remove(element.getGfxObject());
		UMLArtifact.removeArtifactById(element.getId());
		element.setCanvas(null);
		this.selectedArtifacts.remove(element);


		if (element.isALink()) {
			UMLArtifact right = ((LinkArtifact) element).getRightUMLArtifact();
			UMLArtifact left = ((LinkArtifact) element).getLeftUMLArtifact();
			MyLoggerExecute.registEditEvent(-1,element.toString(), "Remove",
					element.getClass().getName(), element.getId(), null, right.getId(), left.getId(),
					null, null, null, null, UMLArtifact.getIdCount());
//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl
		}else {
			MyLoggerExecute.registEditEvent(-1,element.toString(), "Remove",
					element.getClass().getName(), element.getId(), null, -1, -1,
					null, null, null, null, UMLArtifact.getIdCount());
//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl
		}

		for (final Entry<LinkArtifact, UMLArtifact> entry : element.getDependentUMLArtifacts().entrySet()) {
			if (entry.getValue().isALink() && (entry.getKey().getClass() != LinkNoteArtifact.class)) {
				this.remove(entry.getValue());
			}
			entry.getValue().removeDependency(entry.getKey());
			this.removeRecursive(entry.getKey());
		}

	}

	//For Viewer clearCanvas
	public void removeSilent(final UMLArtifact umlArtifact) {
		if (this.fireDeleteArtifactEvent(umlArtifact)) {
			this.removeRecursive(umlArtifact);
			if (umlArtifact.isALink()) {
				((LinkArtifact) umlArtifact).removeCreatedDependency();
			}
		}
		//TODO takafumi


	}

	/**
	 *Remove the specified {@link GWTUMLEventListener} from this canvas
	 *
	 * @param uMLEventListener
	 *            The {@link GWTUMLEventListener} to remove from this canvas
	 */
	public void removeUMLEventListener(final GWTUMLEventListener uMLEventListener) {
		this.uMLEventListenerList.remove(uMLEventListener);
	}

	/**
	 * Select an artifact and put it in the artifact list
	 *
	 * @param toSelect
	 *            The artifact to be selected
	 */
	public void selectArtifact(final UMLArtifact toSelect) {
		this.selectedArtifacts.put(toSelect, new ArrayList<Point>());
		System.out.println("select:"+toSelect.toURL());
		toSelect.select(true);

	}

	/**
	 * Set the mouse state
	 *
	 * @param isMouseEnabled
	 *            True to enable Mouse <br />
	 *            False to disable Mouse
	 */
	public void setMouseEnabled(final boolean isMouseEnabled) {
		this.isMouseEnabled = isMouseEnabled;
	}

	/**
	 * This method calls {@link UMLArtifact#toURL()} on all artifacts of this canvas and concatenate it in a String separated by a semicolon
	 *
	 * @return The concatenated String from all {@link UMLArtifact#toURL()}
	 */
	public String toUrl() {
		final StringBuilder url = new StringBuilder();
		for (final Entry<Integer, UMLArtifact> uMLArtifactEntry : UMLArtifact.getArtifactList().entrySet()) {
			final String artifactString = uMLArtifactEntry.getValue().toURL();
			if ((artifactString != null) && !artifactString.equals("")) {
				url.append("<");
				url.append(uMLArtifactEntry.getKey());
				url.append(">]");
				url.append(artifactString);
				url.append(";");
			}
		}

		return GWTUMLDrawerHelper.encodeBase64(url.toString());
	}

	/**
	 * Add a new class with default values to this canvas at the specified location
	 *
	 * @param location
	 *            The initial class location
	 *
	 */
	void addNewClass(final Point location) {
		if (this.dragAndDropState != DragAndDropState.NONE) {
			return;
		}
		final ClassArtifact newClass = new ClassArtifact("Class" + ++UMLCanvas.classCount);
		if (this.fireNewArtifactEvent(newClass)) {
			this.helpText.setText("Adding a new class");
			this.add(newClass);
			newClass.moveTo(Point.substract(location, this.canvasOffset));
			for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
				selectedArtifact.unselect();
			}
			this.selectedArtifacts.clear();
			this.doSelection(newClass.getGfxObject(), false, false);
			this.selectedArtifacts.put(newClass, new ArrayList<Point>());
			this.dragOffset = location;
			CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			this.dragAndDropState = DragAndDropState.TAKING;
			this.mouseIsPressed = true;

			this.setWidgetPosition(this.helpText, location.getX() + 5, location.getY() - this.helpText.getOffsetHeight() - 5);
			//TODO
			//addNewClass
			MyLoggerExecute.registEditEvent(-1, newClass.toString(), "Create",
					newClass.getClass().getName(), newClass.getId(), null, -1, -1,
					null, null, newClass.getLocation().getX()+","+newClass.getLocation().getY(), null, UMLArtifact.getIdCount());

//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl
		}

		System.out.println("addNewClass");

	}

	/**
	 * Add a new life life with default values to this canvas at the specified location
	 *
	 * @param location
	 *            The initial life line location
	 *
	 */
	void addNewLifeLine(final Point location) {
		if (this.dragAndDropState != DragAndDropState.NONE) {
			return;
		}
		final LifeLineArtifact newLifeLine = new LifeLineArtifact("LifeLine" + ++UMLCanvas.lifeLineCount, "ll" + UMLCanvas.lifeLineCount);
		if (this.fireNewArtifactEvent(newLifeLine)) {
			this.helpText.setText("Adding a new life line");
			this.add(newLifeLine);
			newLifeLine.moveTo(Point.substract(location, this.canvasOffset));
			for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
				selectedArtifact.unselect();
			}
			this.selectedArtifacts.clear();
			this.doSelection(newLifeLine.getGfxObject(), false, false);
			this.selectedArtifacts.put(newLifeLine, new ArrayList<Point>());
			this.dragOffset = location;
			CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			this.dragAndDropState = DragAndDropState.TAKING;
			this.mouseIsPressed = true;

			this.setWidgetPosition(this.helpText, location.getX() + 5, location.getY() - this.helpText.getOffsetHeight() - 5);
		}
	}

	void addNewLink(final UMLArtifact newSelected) {
		int linkOkCount = 0;
		for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
			final LinkArtifact newLink = LinkArtifact.makeLinkBetween(selectedArtifact, newSelected, this.activeLinking);

			if (newLink != null) {
				linkOkCount++;
			}
			this.add(newLink);

			//TODO takafumi
			System.out.println("addNewLink:"+newLink.toURL());
			MyLoggerExecute.registEditEvent(-1, newLink.toString(), "Create",
					newLink.getClass().getName(), newLink.getId(), null, selectedArtifact.getId(), newSelected.getId(),
					null, null, newLink.getLocation().getX()+","+newLink.getLocation().getY(), this.toUrl(), UMLArtifact.getIdCount());

//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl
		}
		if (linkOkCount != 0) {
			this.linkingModeOff();
		}
		//System.out.println("addNewLink");
	}

	void addNewNote() {
		this.addNewNote(this.currentMousePosition);

	}

	void addNewNote(final Point location) {
		if (this.dragAndDropState != DragAndDropState.NONE) {
			return;
		}
		final NoteArtifact newNote = new NoteArtifact("Note " + ++this.noteCount);
		if (this.fireNewArtifactEvent(newNote)) {
			this.helpText.setText("Adding a new note");
			this.add(newNote);
			newNote.moveTo(Point.substract(location, this.canvasOffset));
			for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
				selectedArtifact.unselect();
			}
			this.selectedArtifacts.clear();
			this.doSelection(newNote.getGfxObject(), false, false);
			this.selectedArtifacts.put(newNote, new ArrayList<Point>());
			this.dragOffset = location;
			this.dragAndDropState = DragAndDropState.TAKING;
			this.mouseIsPressed = true;
			CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			this.setWidgetPosition(this.helpText, location.getX() + 5, location.getY() - this.helpText.getOffsetHeight() - 5);

			MyLoggerExecute.registEditEvent(-1, newNote.toString(), "Create",
					newNote.getClass().getName(), newNote.getId(), null, -1, -1,
					null, null, newNote.getLocation().getX()+","+newNote.getLocation().getY(), null, UMLArtifact.getIdCount());

//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl
		}
		//System.out.println("addNewNote");
	}

	/**
	 * Add a new object with default values to this canvas at the specified location
	 *
	 * @param location
	 *            The initial object location
	 *
	 */
	void addNewObject(final Point location) {
		if (this.dragAndDropState != DragAndDropState.NONE) {
			return;
		}
		final ObjectArtifact newObject = new ObjectArtifact("obj" + ++UMLCanvas.objectCount, "Object" + UMLCanvas.objectCount);
		if (this.fireNewArtifactEvent(newObject)) {
			this.helpText.setText("Adding a new object");
			this.add(newObject);
			newObject.moveTo(Point.substract(location, this.canvasOffset));
			for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
				selectedArtifact.unselect();
			}
			this.selectedArtifacts.clear();
			this.doSelection(newObject.getGfxObject(), false, false);
			this.selectedArtifacts.put(newObject, new ArrayList<Point>());
			this.dragOffset = location;
			CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			this.dragAndDropState = DragAndDropState.TAKING;
			this.mouseIsPressed = true;

			this.setWidgetPosition(this.helpText, location.getX() + 5, location.getY() - this.helpText.getOffsetHeight() - 5);
		}
		//System.out.println("addNewObject");
	}
	void cut() {
		this.copy();
		this.wasACopy = false;
		this.removeSelected();

		System.out.println("Cut");
	}
	void copy() {
		if(this.selectedArtifacts.isEmpty()) return;
		this.wasACopy = true;
		final StringBuilder url = new StringBuilder();
		Point lower = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
		Point higher = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
		StringBuilder artifacts = new StringBuilder();//

		for (final Entry<Integer, UMLArtifact> uMLArtifactEntry : UMLArtifact.getArtifactList().entrySet()) {
			if(this.selectedArtifacts.containsKey(uMLArtifactEntry.getValue())) {
				final String artifactString = uMLArtifactEntry.getValue().toURL();
				if ((artifactString != null) && !artifactString.equals("")) {
					if(!uMLArtifactEntry.getValue().isALink()
							|| (this.selectedArtifacts.containsKey(((LinkArtifact) uMLArtifactEntry.getValue()).getLeftUMLArtifact())
									&& this.selectedArtifacts.containsKey(((LinkArtifact) uMLArtifactEntry.getValue()).getRightUMLArtifact()))) {
						url.append("<");
						url.append(uMLArtifactEntry.getKey());
						url.append(">]");
						url.append(artifactString);
						url.append(";");

						artifacts.append("<");
						artifacts.append(uMLArtifactEntry.getKey());
						artifacts.append(">]");
						artifacts.append(artifactString);
						artifacts.append(";");

						if(!uMLArtifactEntry.getValue().isALink()) {
							lower = Point.min(lower, uMLArtifactEntry.getValue().getCenter());
							higher = Point.max(higher, uMLArtifactEntry.getValue().getCenter());
						}
					}
				}
			}
		}
		this.copyBuffer = url.toString();
		this.copyMousePosition = Point.getMiddleOf(lower, higher);


		System.out.println("Copy");
		MyLoggerExecute.registEditEvent(-1, artifacts.toString(), "Copy",
				artifacts.toString(), -1, null, -1, -1,
				null, null, null, null, UMLArtifact.getIdCount());

//		int preEventId, String editEvent, String eventType,
//		String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//		String targetPart, String beforeEdit, String afterEdit, String canvasUrl
		//TODO
		//MyLoggerExecute.registEditEvent("Copy:"+selectedArtifact.toURL(),this.toUrl());
	}
	void paste() {
		if(!this.copyBuffer.equals("") && this.dragAndDropState == DragAndDropState.NONE) {
			// Getting ids :
			deselectAllArtifacts();
			LinkedList<Integer> oldIds = new LinkedList<Integer>();
			String[] slices = this.copyBuffer.split(">");
			for (String stringId : slices) {
				if(stringId.contains("<")) {
					oldIds.add(Integer.parseInt(stringId.split("<")[1]));
				}
			}
			Collections.sort(oldIds);
			// Creating new Ids
			for (Integer oldId : oldIds) {
				this.copyBuffer = this.copyBuffer.replaceAll("<" + oldId + ">", "<" + (UMLArtifact.getIdCount() + oldIds.indexOf(oldId) + 1) + ">");
			}
			UMLArtifact.setIdCount(UMLArtifact.getIdCount() + oldIds.size() + 1);
			fromURL(this.copyBuffer, true);

			MyLoggerExecute.registEditEvent(-1, this.copyBuffer, "Paste",
					this.copyBuffer, -1, null, -1, -1,
					null, null, null, null, UMLArtifact.getIdCount());

//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl
			this.dragOffset = this.currentMousePosition;

			CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			this.dragAndDropState = DragAndDropState.TAKING;
			this.mouseIsPressed = true;
		}

		System.out.println("Paste");
	}
	boolean fireDeleteArtifactEvent(final UMLArtifact umlArtifact) {
		boolean isThisOk = true;
		for (final GWTUMLEventListener listener : this.uMLEventListenerList) {
			// If one is not ok then it's not ok !
			isThisOk = listener.onDeleteUMLArtifact(umlArtifact) && isThisOk;
		}
		//System.out.println("Delete");
		Log.trace("Delete artifact event fired. Status : " + isThisOk);
		return isThisOk;
	}

	boolean fireEditArtifactEvent(final UMLArtifact umlArtifact) {
		boolean isThisOk = true;
		for (final GWTUMLEventListener listener : this.uMLEventListenerList) {
			// If one is not ok then it's not ok !
			isThisOk = listener.onEditUMLArtifact(umlArtifact) && isThisOk;
		}
		Log.trace("New Artifact event fired. Status : " + isThisOk);
		return isThisOk;
	}

	boolean fireLinkKindChange(final LinkArtifact newLink, final LinkKind oldKind, final LinkKind newKind) {
		boolean isThisOk = true;
		for (final GWTUMLEventListener listener : this.uMLEventListenerList) {
			isThisOk = listener.onLinkKindChange(newLink, oldKind, newKind) && isThisOk;
		}
		Log.trace("Link kind chage event fired. Status : " + isThisOk);
		return isThisOk;
	}

	boolean fireNewArtifactEvent(final UMLArtifact umlArtifact) {
		boolean isThisOk = true;
		for (final GWTUMLEventListener listener : this.uMLEventListenerList) {
			// If one is not ok then it's not ok !
			isThisOk = listener.onNewUMLArtifact(umlArtifact) && isThisOk;
		}
		Log.trace("New Artifact event fired. Status : " + isThisOk);
		return isThisOk;
	}

	void mouseDoubleClicked(final GfxObject gfxObject, final Point location) {
		this.editItem(gfxObject);
	}

	void mouseLeftPressed(final GfxObject gfxObject, final Point location, final boolean isCtrlDown, final boolean isShiftDown) {
		if (this.mouseIsPressed) {
			return;
		}
		this.duringDragOffset = Point.getOrigin();
		final Point realPoint = this.convertToRealPoint(location);
		this.mouseIsPressed = true;
		if (this.dragAndDropState == DragAndDropState.DRAGGING) {
			return;
		}
		if (this.activeLinking == null) {
			if (gfxObject != null) {
				this.dragAndDropState = DragAndDropState.TAKING;
				this.dragOffset = realPoint.clonePoint();
				CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			} else {
				this.selectBoxStartPoint = realPoint.clonePoint();
				this.dragAndDropState = DragAndDropState.PREPARING_SELECT_BOX;
			}
		}
		this.doSelection(gfxObject, isCtrlDown, isShiftDown);
	}

	@SuppressWarnings("fallthrough")
	void mouseMoved(final Point location, final boolean isCtrlDown, final boolean isShiftDown) {
		final Point realPoint = this.convertToRealPoint(location);
		if (!this.helpText.getText().equals("")) {
			this.setWidgetPosition(this.helpText, realPoint.getX() + 5, realPoint.getY() - this.helpText.getOffsetHeight() - 5);
		}
		this.currentMousePosition = realPoint;
		switch (this.dragAndDropState) {
			case TAKING:
				this.take();
				UMLCanvas.this.dragAndDropState = DragAndDropState.DRAGGING;
			case DRAGGING:
				this.drag(realPoint);
				break;
			case PREPARING_SELECT_BOX:
				this.dragAndDropState = DragAndDropState.SELECT_BOX;
				this.previouslySelectedArtifacts = new HashMap<UMLArtifact, ArrayList<Point>>(this.selectedArtifacts);
			case SELECT_BOX:
				this.boxSelector(Point.add(this.selectBoxStartPoint, this.duringDragOffset), realPoint, isCtrlDown, isShiftDown);
		}

		if ((this.activeLinking != null) && !this.selectedArtifacts.isEmpty()) {
			this.animateLinking(realPoint);

		}
	}

	@SuppressWarnings("fallthrough")
	void mouseReleased(final GfxObject gfxObject, final Point location, final boolean isCtrlDown, final boolean isShiftDown) {
		if (!this.mouseIsPressed) {
			return;
		}
		final Point realPoint = this.convertToRealPoint(location);
		this.mouseIsPressed = false;

		if (this.dragAndDropState == DragAndDropState.TAKING) {
			this.unselectOnRelease(gfxObject, isCtrlDown, isShiftDown);
		}
		switch (this.dragAndDropState) {
			case SELECT_BOX:
				if (this.selectBox != null) {
					GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas, this.selectBox);
				}
				this.dragAndDropState = DragAndDropState.NONE;
				break;
			case DRAGGING:
				this.drop(realPoint);
			case TAKING:
				CursorIconManager.setCursorIcon(PointerStyle.AUTO);
				this.helpText.setText("");
			default:
				this.dragAndDropState = DragAndDropState.NONE;
		}
	}

	void mouseRightPressed(final GfxObject gfxObject, final Point location) {
		final Point realPoint = this.convertToRealPoint(location);
		this.dropRightMenu(gfxObject, realPoint);
	}

	void moveSelected(final Direction direction) {
		if (!this.selectedArtifacts.isEmpty()) {
			Point lower = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
			Point higher = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
			for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
				if(!selectedArtifact.isALink()) {
					lower = Point.min(lower, Point.add(selectedArtifact.getLocation(), new Point(direction.getXShift(), direction.getYShift())));
					higher = Point.max(higher, Point.add(Point.add(selectedArtifact.getLocation(), new Point(selectedArtifact.getWidth(), selectedArtifact.getHeight())), new Point(direction.getXShift(), direction.getYShift())));
				}
				if (selectedArtifact.isDraggable()) {
					new Scheduler.Task("MovingSelected") {
						@Override
						public void process() {
							selectedArtifact.moveTo(new Point(selectedArtifact.getLocation().getX() + direction.getXShift(),
									selectedArtifact.getLocation().getY() + direction.getYShift()));
							selectedArtifact.rebuildGfxObject();
						}
					};
				}
			}
			boolean mustShiftCanvas = false;
			switch(direction) {
				case UP:
					mustShiftCanvas = lower.getY() < -this.canvasOffset.getY();
					break;
				case DOWN:
					mustShiftCanvas = higher.getY() > -this.canvasOffset.getY() + this.getOffsetHeight();
					break;
				case LEFT:
					mustShiftCanvas = lower.getX() < -this.canvasOffset.getX();
					break;
				case RIGHT:
					mustShiftCanvas = higher.getX() > -this.canvasOffset.getX() + this.getOffsetWidth();
					break;
			}
			if(mustShiftCanvas) {
				moveAll(direction, false);
			}
		}
		System.out.println(this.toUrl());
		System.out.println("");
	}

	void rebuildAllLinks() {
		for (final UMLArtifact artifact : this.objects.values()) {
				artifact.rebuildGfxObject();
		}
	}

	void removeSelected() {
		if (!this.selectedArtifacts.isEmpty()) {
			final HashMap<UMLArtifact, ArrayList<Point>> selectedBeforeRemovalArtifacts = new HashMap<UMLArtifact, ArrayList<Point>>(this.selectedArtifacts);
			for (final UMLArtifact selectedArtifact : selectedBeforeRemovalArtifacts.keySet()) {
				this.remove(selectedArtifact);
			}
			//TODO takafumi
			//RemoveArtifactsのログをとる。
			MyLoggerExecute.registEditEvent(-1,"RemoveArtifacts", "RemoveArtifacts",
					null, -1, null, -1, -1,
					null, null, null, this.toUrl(), UMLArtifact.getIdCount());
//			int preEventId, String editEvent, String eventType,
//			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//			String targetPart, String beforeEdit, String afterEdit, String canvasUrl

		}
	}

	void selectAll() {
		for (final UMLArtifact artifact : this.objects.values()) {
			this.selectArtifact(artifact);
		}
	}

	//public for drawer base
	public void toLinkMode(final LinkKind linkType) {
		this.activeLinking = linkType;
		final int selectedToLink = this.selectedArtifacts.keySet().size();

		this.helpText.setText("Adding " + (selectedToLink == 0 ? "a" : selectedToLink) + " new " + this.activeLinking.getName());

		CursorIconManager.setCursorIcon(PointerStyle.CROSSHAIR);
		//TODO menu visible=false

	}

	@Override
	protected void onAttach() {
		super.onAttach();
		Log.trace("Attaching");
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		Log.trace("Loading");
		for (final UMLArtifact elementNotAdded : this.objectsToBeAddedWhenAttached) {
			Log.debug("Adding queued " + elementNotAdded);
			elementNotAdded.setCanvas(this);
			final long t = System.currentTimeMillis();
			GfxManager.getPlatform().addToVirtualGroup(this.allObjects, elementNotAdded.initializeGfxObject());
			GfxManager.getPlatform().translate(elementNotAdded.getGfxObject(), elementNotAdded.getLocation());
			this.objects.put(elementNotAdded.getGfxObject(), elementNotAdded);
			Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to add queued " + elementNotAdded);
		}
		this.objectsToBeAddedWhenAttached.clear();
		this.makeArrows();
		MyLoggerExecute.registEditEvent(-1, null, "CanvasLoad",
				null, -1, null, -1, -1,
				null, null, null, null, UMLArtifact.getIdCount());

//		int preEventId, String editEvent, String eventType,
//		String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//		String targetPart, String beforeEdit, String afterEdit, String canvasUrl

	}

	private void animateLinking(final Point location) {
		if (QualityLevel.IsAlmost(QualityLevel.HIGH)) {
			GfxManager.getPlatform().clearVirtualGroup(this.movingLines);
			for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
				final GfxObject movingLine = GfxManager.getPlatform().buildLine(selectedArtifact.getCenter(), Point.substract(location, this.canvasOffset));
				GfxManager.getPlatform().addToVirtualGroup(this.movingLines, movingLine);
				GfxManager.getPlatform().setStrokeStyle(movingLine, GfxStyle.LONGDASHDOTDOT);
			}
			GfxManager.getPlatform().setStroke(this.movingLines, ThemeManager.getTheme().getDefaultHighlightedForegroundColor(), 1);
			GfxManager.getPlatform().moveToBack(this.movingLines);
		}
	}

	private void boxSelect(final UMLArtifact artifact, final boolean isSelecting) {
		if (isSelecting) {
			this.selectedArtifacts.put(artifact, new ArrayList<Point>());
			artifact.select(false);
		} else {
			this.selectedArtifacts.remove(artifact);
			artifact.unselect();
		}
	}

	private void boxSelector(final Point startPoint, final Point location, final boolean isCtrlDown, final boolean isShiftDown) {
		if (this.selectBox != null) {
			GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas, this.selectBox);
		}

		this.selectBox = GfxManager.getPlatform().buildPath();
		GfxManager.getPlatform().moveTo(this.selectBox, startPoint);
		GfxManager.getPlatform().lineTo(this.selectBox, new Point(location.getX(), startPoint.getY()));
		GfxManager.getPlatform().lineTo(this.selectBox, location);
		GfxManager.getPlatform().lineTo(this.selectBox, new Point(startPoint.getX(), location.getY()));
		GfxManager.getPlatform().lineTo(this.selectBox, startPoint);
		GfxManager.getPlatform().addToCanvas(this.drawingCanvas, this.selectBox, Point.getOrigin());
		GfxManager.getPlatform().setStroke(this.selectBox, ThemeManager.getTheme().getSelectBoxForegroundColor(), 2);
		GfxManager.getPlatform().setFillColor(this.selectBox, ThemeManager.getTheme().getSelectBoxBackgroundColor());
		GfxManager.getPlatform().setOpacity(this.selectBox, ThemeManager.getTheme().getSelectBoxBackgroundColor().getAlpha(), true);
		final Point min = Point.substract(Point.min(startPoint, location), this.canvasOffset);
		final Point max = Point.substract(Point.max(startPoint, location), this.canvasOffset);

		for (final UMLArtifact artifact : this.objects.values()) {
			if (artifact.isDraggable()) {
				if (this.isIn(artifact.getLocation(), Point.add(artifact.getLocation(), new Point(artifact.getWidth(), artifact.getHeight())), min, max)) {
					this.boxSelect(artifact, !(this.previouslySelectedArtifacts.containsKey(artifact) && isCtrlDown));
				} else {
					this.boxSelect(artifact, (isShiftDown || isCtrlDown) && this.previouslySelectedArtifacts.containsKey(artifact));
				}
			}
		}
	}

	private Point convertToRealPoint(final Point location) {
		return Point.add(location, new Point(RootPanel.getBodyElement().getScrollLeft() - this.getAbsoluteLeft(), RootPanel.getBodyElement().getScrollTop()
				- this.getAbsoluteTop()));
	}

	private void deselectAllArtifacts() {

		for (final UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
			selectedArtifact.unselect();
		}
		this.selectedArtifacts.clear();
	}

	private void doSelection(final GfxObject gfxObject, final boolean isCtrlKeyDown, final boolean isShiftKeyDown) {
		final UMLArtifact newSelected = this.getUMLArtifact(gfxObject);
		Log.trace("Selecting : " + newSelected + " (" + gfxObject + ")");
		// New selection is null -> deselecting all selected artifacts and disabling linking mode
		if (newSelected == null) {
			this.linkingModeOff();
			this.deselectAllArtifacts();
		} else { // New selection is not null
			if (this.selectedArtifacts.containsKey(newSelected)) { // New selection is already selected -> deselecting it if ctrl is down
				if (this.activeLinking != null) {// if linking mode on
					this.addNewLink(newSelected);
				}
				if (this.selectedArtifacts.size() != 1) { // New selection isn't the only one selected
					if (isCtrlKeyDown) { // If ctrl down deselecting only this one
						this.deselectArtifact(newSelected);
					}
				}
			} else { // New selection is not selected
				if (this.selectedArtifacts.isEmpty()) { // If nothing is selected -> selecting
					this.selectArtifact(newSelected);
				} else { // Other artifacts are selected
					if (this.activeLinking != null) {// if linking mode on
						this.addNewLink(newSelected);
					}
					if (!isCtrlKeyDown && !isShiftKeyDown) { // If selection is not greedy ->deselect all
						this.deselectAllArtifacts();
					}
					this.selectArtifact(newSelected);
				}
			}
		}
	}

	private void drag(final Point location) {
		final Point shift = Point.substract(location, this.dragOffset);
		this.totalDragShift.translate(shift);

		if (QualityLevel.IsAlmost(QualityLevel.HIGH)) {
			GfxManager.getPlatform().clearVirtualGroup(this.movingOutlineDependencies);
		}
		for (final Entry<UMLArtifact, ArrayList<Point>> selectedArtifactEntry : this.selectedArtifacts.entrySet()) {
			final UMLArtifact selectedArtifact = selectedArtifactEntry.getKey();
			if (selectedArtifact.isDraggable()) {
				final Point outlineOfSelectedCenter = Point.substract(Point.add(selectedArtifact.getCenter(), this.totalDragShift), this.duringDragOffset);
				if (QualityLevel.IsAlmost(QualityLevel.HIGH)) {
					for (final Point dependantArtifactCenter : selectedArtifactEntry.getValue()) {
						final GfxObject outlineDependency = GfxManager.getPlatform().buildLine(outlineOfSelectedCenter, dependantArtifactCenter);
						GfxManager.getPlatform().addToVirtualGroup(this.movingOutlineDependencies, outlineDependency);
						GfxManager.getPlatform().setStrokeStyle(outlineDependency, GfxStyle.DASH);
					}
					GfxManager.getPlatform().setStroke(this.movingOutlineDependencies, ThemeManager.getTheme().getDefaultHighlightedForegroundColor(), 1);
					GfxManager.getPlatform().moveToBack(this.movingOutlineDependencies);
				}
				GfxManager.getPlatform().setStroke(this.outlines, ThemeManager.getTheme().getDefaultHighlightedForegroundColor(), 1);
			}
		}
		GfxManager.getPlatform().translate(this.outlines, shift);
		this.dragOffset = location.clonePoint();
	}

	private void drop(final Point location) {
		boolean placeFlag =false;//20141203
		for (final UMLArtifact selectedArtifact : UMLCanvas.this.selectedArtifacts.keySet()) {
			if (selectedArtifact.isDraggable()) {
				placeFlag=true;
				Point oldPoint = selectedArtifact.getLocation();
				selectedArtifact.moveTo(Point.substract(Point.add(selectedArtifact.getLocation(), this.totalDragShift), this.duringDragOffset));
				selectedArtifact.rebuildGfxObject();
				//TODO dropEvent
				//writeLog(selectedArtifact);
				//MyLogger.operationLog("DropArtifact"+":"+selectedArtifact.toURL());
				System.out.println("DropArtifact"+":"+selectedArtifact.toString());
				MyLoggerExecute.registEditEvent(-1, selectedArtifact.toString(), "Place",
						selectedArtifact.getClass().getName(), selectedArtifact.getId(), null, -1, -1,
						null, oldPoint.getX()+","+oldPoint.getY(), selectedArtifact.getLocation().getX()+","+selectedArtifact.getLocation().getY(), null, UMLArtifact.getIdCount());

//				int preEventId, String editEvent, String eventType,
//				String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//				String targetPart, String beforeEdit, String afterEdit, String canvasUrl
			}
		}
		this.totalDragShift = Point.getOrigin();
		this.duringDragOffset = Point.getOrigin();
		GfxManager.getPlatform().clearVirtualGroup(this.outlines);
		GfxManager.getPlatform().clearVirtualGroup(this.movingOutlineDependencies);

		if(placeFlag){
			MyLoggerExecute.registEditEvent(-1, "PlaceArtifacts", "Place",
					null, -1, null, -1, -1,
					null, null, null, this.toUrl(), UMLArtifact.getIdCount());
		}
		//TODO takafumi
		System.out.println(this.toString());

		//MyLoggerExecute.registEditEvent("DropArtifacts",this.toUrl());

//		int preEventId, String editEvent, String eventType,
//		String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//		String targetPart, String beforeEdit, String afterEdit, String canvasUrl

	}

	private void dropRightMenu(final GfxObject gfxObject, final Point location) {
		this.doSelection(gfxObject, false, false);
		final UMLArtifact elem = this.getUMLArtifact(gfxObject);
		ContextMenu contextMenu;
		if (elem != null) {
			contextMenu = new ContextMenu(location, this, elem.getRightMenu());
		} else {
			contextMenu = new ContextMenu(location, this);
		}
		contextMenu.show();

	}

	private void editItem(final GfxObject gfxObject) {
		Log.trace("Edit request on " + gfxObject);
		final UMLArtifact uMLArtifact = this.getUMLArtifact(gfxObject);
		if (uMLArtifact != null) {
			Log.trace("Edit started on " + uMLArtifact);
			if (this.fireEditArtifactEvent(uMLArtifact)) {
				//TODO takafumi

				//MyLoggerExecute.registEditEvent("EditStart:"+uMLArtifact.toURL(), this.toUrl());
//				System.out.println("EditStart:"+uMLArtifact.toURL());
				uMLArtifact.edit(gfxObject);
			}
		}
	}

	private UMLArtifact getUMLArtifact(final GfxObject gfxObject) {
		if (gfxObject == null) {
			Log.trace("No Object");
			return null;
		}
		GfxObject currentGfxObject = gfxObject;
		GfxObject gfxOParentGroup = GfxManager.getPlatform().getGroup(currentGfxObject);
		while ((gfxOParentGroup != null) && !gfxOParentGroup.equals(this.allObjects)) {
			currentGfxObject = gfxOParentGroup;
			gfxOParentGroup = GfxManager.getPlatform().getGroup(currentGfxObject);
		}
		final UMLArtifact UMLArtifact = this.objects.get(currentGfxObject);
		if (UMLArtifact == null) {
			Log.trace("Artifact not found");
		}
		return UMLArtifact;
	}

	private void initCanvas() {
		Log.trace("Adding Canvas");
		this.add(this.drawingCanvas, 0, 0);
		this.helpText.setStylePrimaryName("contextual-help");
		this.add(this.helpText, 0, 0);
		Log.trace("Adding object listener");
		GfxManager.getPlatform().addObjectListenerToCanvas(this.drawingCanvas, this.gfxObjectListener);
		this.noteCount = 0;
		GfxManager.getPlatform().addToCanvas(this.drawingCanvas, this.allObjects, Point.getOrigin());
		GfxManager.getPlatform().addToCanvas(this.drawingCanvas, this.movingLines, Point.getOrigin());
		GfxManager.getPlatform().addToCanvas(this.drawingCanvas, this.outlines, Point.getOrigin());
		GfxManager.getPlatform().addToCanvas(this.drawingCanvas, this.movingOutlineDependencies, Point.getOrigin());
		Log.trace("Init canvas done");
	}

	private boolean isIn(final Point artifactMin, final Point artifactMax, final Point selectMin, final Point selectMax) {
		return (selectMax.isSuperiorTo(artifactMin) && artifactMax.isSuperiorTo(selectMin));
	}

	private void linkingModeOff() {
		this.activeLinking = null;
		GfxManager.getPlatform().clearVirtualGroup(this.movingLines);
		CursorIconManager.setCursorIcon(PointerStyle.AUTO);
		this.helpText.setText("");
	}



	private void take() {
		GfxManager.getPlatform().translate(this.outlines, Point.substract(this.canvasOffset, GfxManager.getPlatform().getLocationFor(this.outlines)));
		final HashMap<UMLArtifact, UMLArtifact> alreadyAdded = new HashMap<UMLArtifact, UMLArtifact>();
		this.duringDragOffset = Point.getOrigin();
		for (final Entry<UMLArtifact, ArrayList<Point>> selectedArtifactEntry : this.selectedArtifacts.entrySet()) {
			final UMLArtifact selectedArtifact = selectedArtifactEntry.getKey();
			Scheduler.cancel("RebuildingDependencyFor" + selectedArtifact);
			if (selectedArtifact.isDraggable()) {
				final GfxObject outline = selectedArtifact.getOutline();
				GfxManager.getPlatform().addToVirtualGroup(this.outlines, outline);
				GfxManager.getPlatform().translate(outline, selectedArtifact.getLocation());
				selectedArtifact.destroyGfxObjectWhithDependencies();
				Log.trace("Adding outline for " + selectedArtifact);
				// Drawing lines only translating during with drag
				if (QualityLevel.IsAlmost(QualityLevel.HIGH)) {
					selectedArtifactEntry.getValue().clear();

					for (final UMLArtifact dependantArtifact : selectedArtifact.getDependentUMLArtifacts().values()) {
						if (this.selectedArtifacts.containsKey(dependantArtifact)) {
							if ((alreadyAdded.get(selectedArtifact) == null) || !alreadyAdded.get(selectedArtifact).equals(dependantArtifact)) {
								final GfxObject outlineDependency = GfxManager.getPlatform().buildLine(selectedArtifact.getCenter(),
										dependantArtifact.getCenter());
								GfxManager.getPlatform().addToVirtualGroup(this.outlines, outlineDependency);
								GfxManager.getPlatform().setStrokeStyle(outlineDependency, GfxStyle.DASH);
								GfxManager.getPlatform().moveToBack(outlineDependency);
								alreadyAdded.put(dependantArtifact, selectedArtifact);
							}
						} else {
							selectedArtifactEntry.getValue().add(dependantArtifact.getCenter());
						}
					}
				}
			}
		}
	}

	private void unselectOnRelease(final GfxObject gfxObject, final boolean isCtrlKeyDown, final boolean isShiftKeyDown) {

		final UMLArtifact newSelected = this.getUMLArtifact(gfxObject);
		if (newSelected != null) {
			if (this.selectedArtifacts.containsKey(newSelected)) {
				if (this.selectedArtifacts.size() != 1) {
					if (!isShiftKeyDown && !isCtrlKeyDown) { // Doing what have not been done on mouse press to allow multiple dragging
						this.deselectAllArtifacts();
						this.selectArtifact(newSelected);
					}
				}
			}
		}
	}

	//TODO clearCanvas
	public void clearCanvas() {
		this.selectAll();
		if (!this.selectedArtifacts.isEmpty()) {
			final HashMap<UMLArtifact, ArrayList<Point>> selectedBeforeRemovalArtifacts = new HashMap<UMLArtifact, ArrayList<Point>>(this.selectedArtifacts);
			for (final UMLArtifact selectedArtifact : selectedBeforeRemovalArtifacts.keySet()) {
				this.removeSilent(selectedArtifact);
			}
		}
	}

	/**
	 * @return height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height セットする height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width セットする width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	public void setSize(int height, int width){
		this.height = height;
		this.width = width;
	}
}
