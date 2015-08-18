package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.HashMap;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.editors.RelationFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLRelation;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class AssetAndMisUseRelationLinkArtifact extends RelationLinkArtifact {
	/**
	 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
	 */
	protected enum Anchor {
		BOTTOM, LEFT, RIGHT, TOP, UNKNOWN;
	}

	protected GfxObject											arrowVirtualGroup;
	protected AssetArtifact										assetArtifact;
	protected GfxObject											line;
	protected MisUseCaseArtifact									misusecaseArtifact;
	protected GfxObject											textVirtualGroup;
	private final HashMap<RelationLinkArtifactPart, GfxObject>	gfxObjectPart	= new HashMap<RelationLinkArtifactPart, GfxObject>();

	/**
	 * Constructor of {@link ObjectRelationLinkArtifact}
	 *
	 * @param left
	 *            The left {@link ObjectArtifact} of the relation
	 * @param right
	 *            The right {@link ObjectArtifact} of the relation
	 * @param relationKind
	 *            The kind of relation this link is.
	 */
	public AssetAndMisUseRelationLinkArtifact(final AssetArtifact left, final MisUseCaseArtifact right, final LinkKind relationKind) {
		super(left, right, relationKind);
		if (relationKind != LinkKind.INSTANTIATION) {
			Log.error("Making a instantiation relation artifact for : " + relationKind.getName());
		}
		this.relation = new UMLRelation(relationKind);
		this.assetArtifact = left;
		left.addDependency(this, right);
		this.misusecaseArtifact = right;
		right.addDependency(this, left);
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		final RelationLinkArtifactPart editPart = RelationLinkArtifactPart.getPartForGfxObject(editedGfxObject);
		if (editPart == null) {
			this.edit(RelationLinkArtifactPart.NAME);
		} else {
			final RelationFieldEditor editor = new RelationFieldEditor(this.canvas, this, editPart);
			editor.startEdition(editPart.getText(this.relation), GfxManager.getPlatform().getLocationFor(editedGfxObject).getX(), GfxManager.getPlatform()
					.getLocationFor(editedGfxObject).getY(), GfxManager.getPlatform().getTextWidthFor(editedGfxObject)
					+ OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding"), false, true);
		}
	}

	public void edit(final RelationLinkArtifactPart part) {
		String defaultText;
		switch (part) {
			case NAME:
				final String name = part.getText(this.relation);
				if ((name == null) || name.equals("")) {
					defaultText = this.assetArtifact.getName() + "-" + this.misusecaseArtifact.getName();
				} else {
					defaultText = name;
				}
				break;
			case LEFT_CARDINALITY:
			case RIGHT_CARDINALITY:
				defaultText = "0..*";
				break;
			case LEFT_CONSTRAINT:
			case RIGHT_CONSTRAINT:
				defaultText = "{union}";
				break;
			case LEFT_ROLE:
			case RIGHT_ROLE:
				defaultText = "role";
				break;
			default:
				defaultText = "?";

		}
		part.setText( this.relation, defaultText);
		this.rebuildGfxObject();
		this.edit(this.gfxObjectPart.get(part));
	}

	/**
	 * Getter for the left {@link ClassArtifact} of this relation
	 *
	 * @return the left {@link ClassArtifact} of this relation
	 */
	public AssetArtifact getUseCaseArtifact() {
		return this.assetArtifact;
	}

	/**
	 * Getter for the right {@link ObjectArtifact} of this relation
	 *
	 * @return the right {@link ObjectArtifact} of this relation
	 */
	public MisUseCaseArtifact getMisUseCaseArtifact() {
		return this.misusecaseArtifact;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.links.RelationshipLinkArtifact #getRightMenu()
	 */
	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		rightMenu.setName(this.relation.getLinkKind().getNameInMenu() + " " + this.assetArtifact.getName() + " "
				+ this.relation.getLeftAdornment().getShape().getIdiom() + "-" + this.relation.getRightAdornment().getShape().getIdiom(true) + " "
				+ this.misusecaseArtifact.getName());
		return rightMenu;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact#removeCreatedDependency()
	 */
	@Override
	public void removeCreatedDependency() {
		this.assetArtifact.removeDependency(this);
		this.misusecaseArtifact.removeDependency(this);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		return "UseAndMisUseRelationLink$<" + this.assetArtifact.getId() + ">!<" + this.misusecaseArtifact.getId() + ">";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#unselect()
	 */
	@Override
	public void unselect() {
		super.unselect();
		GfxManager.getPlatform().setStroke(this.line, ThemeManager.getTheme().getInstantiationForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(this.arrowVirtualGroup, ThemeManager.getTheme().getInstantiationForegroundColor(), 1);
	}

	@Override
	protected void buildGfxObject() {
		Point curveControl = Point.getOrigin();

		this.gfxObjectPart.clear();
		ArrayList<Point> linePoints = new ArrayList<Point>();
		final boolean isComputationNeededOnLeft = this.relation.getLeftAdornment() != LinkAdornment.NONE;
		final boolean isComputationNeededOnRight = this.relation.getRightAdornment() != LinkAdornment.NONE;

		if (isComputationNeededOnLeft && isComputationNeededOnRight) {
			linePoints = GeometryManager.getPlatform().getLineBetween(this.assetArtifact, this.misusecaseArtifact);
			this.leftPoint = linePoints.get(0);
			this.rightPoint = linePoints.get(1);
		} else if (isComputationNeededOnLeft) {
			this.rightPoint = this.misusecaseArtifact.getCenter();
			this.leftPoint = GeometryManager.getPlatform().getPointForLine(this.assetArtifact, this.rightPoint);
		} else if (isComputationNeededOnRight) {
			this.leftPoint = this.assetArtifact.getCenter();
			this.rightPoint = GeometryManager.getPlatform().getPointForLine(this.assetArtifact, this.leftPoint);
		} else {
			this.leftPoint = this.assetArtifact.getCenter();
			this.rightPoint = this.misusecaseArtifact.getCenter();
		}
		if (this.order == 0) {
			this.line = GfxManager.getPlatform().buildLine(this.leftPoint, this.rightPoint);
		} else {
			int factor = 50 * ((this.order + 1) / 2);
			factor *= (this.order % 2) == 0 ? -1 : 1;
			curveControl = GeometryManager.getPlatform().getShiftedCenter(this.leftPoint, this.rightPoint, factor);
			this.line = GfxManager.getPlatform().buildPath();
			GfxManager.getPlatform().moveTo(this.line, this.leftPoint);
			GfxManager.getPlatform().curveTo(this.line, this.rightPoint, curveControl);
			GfxManager.getPlatform().setOpacity(this.line, 0, true);
		}

		GfxManager.getPlatform().setStroke(this.line, ThemeManager.getTheme().getInstantiationForegroundColor(), 1);
		GfxManager.getPlatform().setStrokeStyle(this.line, this.relation.getLinkStyle().getGfxStyle());
		GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.line);

		// Making arrows group :
		this.arrowVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.arrowVirtualGroup);
		if (isComputationNeededOnLeft) {
			GfxManager.getPlatform().addToVirtualGroup(
					this.arrowVirtualGroup,
					this.order == 0 ? GeometryManager.getPlatform().buildAdornment(this.leftPoint, this.rightPoint, this.relation.getLeftAdornment())
							: GeometryManager.getPlatform().buildAdornment(this.leftPoint, curveControl, this.relation.getLeftAdornment()));
		}
		if (isComputationNeededOnRight) {
			GfxManager.getPlatform().addToVirtualGroup(
					this.arrowVirtualGroup,
					this.order == 0 ? GeometryManager.getPlatform().buildAdornment(this.rightPoint, this.leftPoint, this.relation.getRightAdornment())
							: GeometryManager.getPlatform().buildAdornment(this.rightPoint, curveControl, this.relation.getRightAdornment()));
		}

		// Making the text group :
		this.textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.textVirtualGroup);
		Log.trace("Creating name");
		Point linkMiddle = Point.getMiddleOf(this.leftPoint, this.rightPoint);
		if (this.order != 0) {
			linkMiddle = Point.getMiddleOf(curveControl, linkMiddle);
		}
		final GfxObject nameGfxObject = GfxManager.getPlatform().buildText(this.relation.getName(), linkMiddle);
		//final GfxObject nameGfxObject = GfxManager.getPlatform().buildText("«InstanceOf»", linkMiddle);
		GfxManager.getPlatform().setFont(nameGfxObject, OptionsManager.getSmallFont());
		GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup, nameGfxObject);
		GfxManager.getPlatform().setStroke(nameGfxObject, ThemeManager.getTheme().getInstantiationBackgroundColor(), 0);
		GfxManager.getPlatform().setFillColor(nameGfxObject, ThemeManager.getTheme().getInstantiationForegroundColor());
		GfxManager.getPlatform().translate(nameGfxObject, new Point(-GfxManager.getPlatform().getTextWidthFor(nameGfxObject) / 2, 0));
		RelationLinkArtifactPart.setGfxObjectTextForPart(nameGfxObject, RelationLinkArtifactPart.NAME);
		this.gfxObjectPart.put(RelationLinkArtifactPart.NAME, nameGfxObject);

		GfxManager.getPlatform().moveToBack(this.gfxObject);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#select()
	 */
	@Override
	protected void select() {
		super.select();
		GfxManager.getPlatform().setStroke(this.line, ThemeManager.getTheme().getInstantiationHighlightedForegroundColor(), 2);
		GfxManager.getPlatform().setStroke(this.arrowVirtualGroup, ThemeManager.getTheme().getInstantiationHighlightedForegroundColor(), 2);
	}
}

