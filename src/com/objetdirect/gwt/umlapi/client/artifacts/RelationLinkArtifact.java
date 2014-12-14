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
package com.objetdirect.gwt.umlapi.client.artifacts;

import static com.objetdirect.gwt.umlapi.client.helpers.TextResource.*;

import java.util.ArrayList;
import java.util.HashMap;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLComponent;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLRelation;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public abstract class RelationLinkArtifact extends LinkArtifact {

	/**
	 * This enumeration list all text part of a RelationLinkArtifact
	 *
	 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
	 *
	 */
	public enum RelationLinkArtifactPart {
		/**
		 * Left end cardinality
		 */
		LEFT_CARDINALITY("Cardinality",CARDINALITY.getMessage(), true) {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com.objetdirect.gwt.umlapi.client.umlcomponents
			 * .Relation)
			 */
			@Override
			public String getText(final UMLRelation relation) {
				return relation.getLeftCardinality();
			}

			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com.objetdirect.gwt.umlapi.client.umlcomponents
			 * .Relation, java.lang.String)
			 */
			@Override
			public void setText(final UMLRelation relation, final String text) {
				//TODO takafumi Relation Name
				String leftCardinality = relation.getLeftCardinality();

				relation.setLeftCardinality(text);
				//MyLoggerExecute.registEditEvent("RelationLeftCardinality:"+relation.getName()+":"+leftCardinality+":"+text, canvas.toUrl());
			}
		},
		/**
		 * Left end constraint
		 */
		LEFT_CONSTRAINT("Constraint",CONSTRAINT.getMessage(), true) {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com.objetdirect.gwt.umlapi.client.umlcomponents
			 * .Relation)
			 */
			@Override
			public String getText(final UMLRelation relation) {
				return relation.getLeftConstraint();
			}

			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com.objetdirect.gwt.umlapi.client.umlcomponents
			 * .Relation, java.lang.String)
			 */
			@Override
			public void setText(final UMLRelation relation, final String text) {

				relation.setLeftConstraint(text);
			}
		},
		/**
		 * Left end role
		 */
		LEFT_ROLE("Role",ROLE.getMessage(), true) {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com.objetdirect.gwt.umlapi.client.umlcomponents
			 * .Relation)
			 */
			@Override
			public String getText(final UMLRelation relation) {
				return relation.getLeftRole();
			}

			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com.objetdirect.gwt.umlapi.client.umlcomponents
			 * .Relation, java.lang.String)
			 */
			@Override
			public void setText(final UMLRelation relation, final String text) {
				relation.setLeftRole(text);
			}
		},
		/**
		 * The relation name
		 */
		NAME("Name",NAME_TR.getMessage(), false) {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com.objetdirect.gwt.umlapi.client.umlcomponents
			 * .Relation)
			 */
			@Override
			public String getText(final UMLRelation relation) {
				return relation.getName();
			}

			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com.objetdirect.gwt.umlapi.client.umlcomponents
			 * .Relation, java.lang.String)
			 */
			@Override
			public void setText(final UMLRelation relation, final String text) {
				//TODO takafumi Relation Name
				String name = relation.getName();
			//	System.out.println("Relation:"+relation.getName()+"_RightCardinarity:"+relation.getName()+"==>"+text);
				relation.setName(text);
				//MyLoggerExecute.registEditEvent("RelationName:"+relation.getName()+":"+name+":"+text, canvas.toUrl());
			}
		},

		/**
		 * Right end cardinality
		 */
		RIGHT_CARDINALITY("Cardinality",CARDINALITY.getMessage(), false) {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com.objetdirect.gwt.umlapi.client.umlcomponents
			 * .Relation)
			 */
			@Override
			public String getText(final UMLRelation relation) {
				return relation.getRightCardinality();
			}

			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com.objetdirect.gwt.umlapi.client.umlcomponents
			 * .Relation, java.lang.String)
			 */
			@Override
			public void setText(final UMLRelation relation, final String text) {
				//TODO takafumi Relation Name
				String rightCardinality = relation.getRightCardinality();
				//System.out.println("Relation:"+relation.getName()+":"+relation.getRightCardinality()+"==>"+text);
				relation.setRightCardinality(text);
				//MyLoggerExecute.registEditEvent("RelationRightCardinality:"+relation.getName()+":"+rightCardinality+":"+text, canvas.toUrl());
			}
		},
		/**
		 * Right end constraint
		 */
		RIGHT_CONSTRAINT("Constraint",CONSTRAINT.getMessage(), false) {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com.objetdirect.gwt.umlapi.client.umlcomponents
			 * .Relation)
			 */
			@Override
			public String getText(final UMLRelation relation) {
				return relation.getRightConstraint();
			}

			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com.objetdirect.gwt.umlapi.client.umlcomponents
			 * .Relation, java.lang.String)
			 */
			@Override
			public void setText(final UMLRelation relation, final String text) {
				relation.setRightConstraint(text);
			}
		},
		/**
		 * Right end role
		 */
		RIGHT_ROLE("Role",ROLE.getMessage(), false) {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com.objetdirect.gwt.umlapi.client.umlcomponents
			 * .Relation)
			 */
			@Override
			public String getText(final UMLRelation relation) {
				return relation.getRightRole();
			}

			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com.objetdirect.gwt.umlapi.client.umlcomponents
			 * .Relation, java.lang.String)
			 */
			@Override
			public void setText(final UMLRelation relation, final String text) {
				relation.setRightRole(text);
			}
		};

		private static HashMap<GfxObject, RelationLinkArtifactPart>	textGfxObject	= new HashMap<GfxObject, RelationLinkArtifactPart>();

		/**
		 * Getter for the part represented by the graphical object
		 *
		 * @param gfxObjectText
		 *            The graphical object to retrieve the relationLinkArtifactPart
		 * @return The RelationLinkArtifactPart represented by the graphical object
		 */
		public static RelationLinkArtifactPart getPartForGfxObject(final GfxObject gfxObjectText) {
			return RelationLinkArtifactPart.textGfxObject.get(gfxObjectText);
		}

		/**
		 * Setter of the relation between a RelationLinkArtifactPart and his graphical object
		 *
		 * @param gfxObjectText
		 *            The graphical object representing the relationLinkArtifactPart
		 * @param relationLinkArtifactPart
		 *            the relationLinkArtifactPart represented by gfxObjecttext
		 */
		public static void setGfxObjectTextForPart(final GfxObject gfxObjectText, final RelationLinkArtifactPart relationLinkArtifactPart) {
			RelationLinkArtifactPart.textGfxObject.put(gfxObjectText, relationLinkArtifactPart);
		}

		private boolean	isLeft;

		private String	name;

		private String nameInMenu;

		private RelationLinkArtifactPart(final String name, final boolean isLeft) {
			this.name = name;
			this.isLeft = isLeft;
		}
		private RelationLinkArtifactPart(final String name, final String nameInMenu, final boolean isLeft) {
			this.name = name;
			this.setNameInMenu(nameInMenu);
			this.isLeft = isLeft;
		}

		/**
		 * Getter for the text contained by the graphical object for a part
		 *
		 * @param relation
		 *            The relation {@link UMLComponent} this enumeration is about
		 * @return the text corresponding to this part
		 */
		public abstract String getText(UMLRelation relation);

		/**
		 * Determine if this part is "Left"
		 *
		 * @return <ul>
		 *         <li><b>True</b> if it is actually "Left"</li>
		 *         <li><b>False</b> otherwise</li>
		 *         </ul>
		 */
		public boolean isLeft() {
			return this.isLeft;
		}

		/**
		 * Setter to affect a text to a part of the relation {@link UMLComponent}
		 *
		 * @param relation
		 *            The relation {@link UMLComponent} this enumeration is about
		 * @param text
		 *            The text corresponding to this part
		 */
		public abstract void setText(UMLRelation relation, String text);

		/*
		 * (non-Javadoc)
		 *
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return this.name;
		}

		public String getNameInMenu() {
			return nameInMenu;
		}

		public void setNameInMenu(String nameInMenu) {
			this.nameInMenu = nameInMenu;
		}
	}

	protected UMLRelation		relation;
	protected Point				leftDirectionPoint	= Point.getOrigin();
	protected Point				rightDirectionPoint	= Point.getOrigin();
	protected Point				nameAnchorPoint		= Point.getOrigin();
	private final NodeArtifact	rightNodeArtifact;
	private final NodeArtifact	leftNodeArtifact;

	/**
	 * Constructor of RelationLinkArtifact
	 *
	 * @param nodeArtifact1
	 * @param nodeArtifact2
	 * @param relationKind
	 */
	public RelationLinkArtifact(final NodeArtifact nodeArtifact1, final NodeArtifact nodeArtifact2, final LinkKind relationKind) {
		super(nodeArtifact1, nodeArtifact2);
		this.leftNodeArtifact = nodeArtifact1;
		this.rightNodeArtifact = nodeArtifact2;
		if ((relationKind == LinkKind.NOTE) || (relationKind == LinkKind.CLASSRELATION)) {
			Log.error("Making a relation artifact for : " + relationKind.getName());
		}
		this.relation = new UMLRelation(relationKind);
	}

	/**
	 * Setter of a part text
	 *
	 * @param part
	 *            The {@link RelationLinkArtifactPart} in which the text is to be updated
	 * @param newContent
	 *            The new text to set for this part
	 */
	public void setPartContent(final RelationLinkArtifactPart part, final String newContent) {
		part.setText(this.relation, newContent);
	}

	public String getPartContent(final RelationLinkArtifactPart part) {
		return part.getText(this.relation);
	}

	protected GfxObject getLine() {

		if (this.isSelfLink) {
			return this.getSelfLine();
		}
		return this.getPeerLine();

	}

	private GfxObject getPeerAngularLine() {

		this.leftPoint = Point.add(this.leftNodeArtifact.getCenter(), new Point(
				Math.abs(this.leftDirection.getYDirection())
						* (-this.leftNodeArtifact.getWidth() / 2 + ((this.leftNodeArtifact.getWidth() / (this.leftNodeArtifact
								.getDependenciesCount(this.leftDirection) + 1)))
								* (this.leftNodeArtifact.getDependencyIndexOf(this, this.leftDirection) + 1)), Math.abs(this.leftDirection.getXDirection())
						* (-this.leftNodeArtifact.getHeight() / 2 + ((this.leftNodeArtifact.getHeight() / (this.leftNodeArtifact
								.getDependenciesCount(this.leftDirection) + 1)))
								* (this.leftNodeArtifact.getDependencyIndexOf(this, this.leftDirection) + 1))));
		this.rightPoint = Point.add(this.rightNodeArtifact.getCenter(), new Point(Math.abs(this.rightDirection.getYDirection())
				* (-this.rightNodeArtifact.getWidth() / 2 + ((this.rightNodeArtifact.getWidth() / (this.rightNodeArtifact
						.getDependenciesCount(this.rightDirection) + 1)))
						* (this.rightNodeArtifact.getDependencyIndexOf(this, this.rightDirection) + 1)), Math.abs(this.rightDirection.getXDirection())
				* (-this.rightNodeArtifact.getHeight() / 2 + ((this.rightNodeArtifact.getHeight() / (this.rightNodeArtifact
						.getDependenciesCount(this.rightDirection) + 1)))
						* (this.rightNodeArtifact.getDependencyIndexOf(this, this.rightDirection) + 1))));

		this.leftPoint.translate(this.leftDirection.getXDirection() * this.leftNodeArtifact.getWidth() / 2, this.leftDirection.getYDirection()
				* this.leftNodeArtifact.getHeight() / 2);
		this.rightPoint.translate(this.rightDirection.getXDirection() * this.rightNodeArtifact.getWidth() / 2, this.rightDirection.getYDirection()
				* this.rightNodeArtifact.getHeight() / 2);

		final Point intermediate = Point.abs(Point.substract(this.rightPoint, this.leftDirection.isOppositeOf(this.rightDirection) ? Point.getMiddleOf(
				this.leftPoint, this.rightPoint) : this.leftPoint));
		final GfxObject line = GfxManager.getPlatform().buildPath();

		this.leftDirectionPoint = Point.add(this.leftPoint, new Point(this.leftDirection.getXDirection() * intermediate.getX(), this.leftDirection
				.getYDirection()
				* intermediate.getY()));
		this.rightDirectionPoint = Point.add(this.rightPoint, new Point(this.rightDirection.getXDirection() * intermediate.getX(), this.rightDirection
				.getYDirection()
				* intermediate.getY()));

		GfxManager.getPlatform().moveTo(line, this.leftPoint);
		GfxManager.getPlatform().lineTo(line, this.leftDirectionPoint);
		GfxManager.getPlatform().lineTo(line, this.rightDirectionPoint);
		GfxManager.getPlatform().lineTo(line, this.rightPoint);
		GfxManager.getPlatform().setOpacity(line, 0, true);

		return line;
	}

	private GfxObject getPeerLine() {
		final ArrayList<Point> points = GeometryManager.getPlatform().getLineBetween(this.leftNodeArtifact, this.rightNodeArtifact);
		this.leftPoint = points.get(0);
		this.rightPoint = points.get(1);
		this.computeDirectionsType();
		return OptionsManager.get("AngularLinks") == 1 ? this.getPeerAngularLine() : this.getPeerRightLine();
	}

	private GfxObject getPeerRightLine() {
		GfxObject line;
		this.nameAnchorPoint = Point.getMiddleOf(this.leftPoint, this.rightPoint);
		if (this.order == 0) {
			line = GfxManager.getPlatform().buildLine(this.leftPoint, this.rightPoint);
			this.leftDirectionPoint = this.rightPoint;
			this.rightDirectionPoint = this.leftPoint;
		} else {
			final Point curveControl = GeometryManager.getPlatform().getShiftedCenter(this.leftPoint, this.rightPoint,
					50 * ((this.order + 1) / 2) * ((this.order % 2) == 0 ? -1 : 1));
			this.leftDirectionPoint = curveControl;
			this.rightDirectionPoint = curveControl;
			line = GfxManager.getPlatform().buildPath();
			GfxManager.getPlatform().moveTo(line, this.leftPoint);
			GfxManager.getPlatform().curveTo(line, this.rightPoint, curveControl);
			GfxManager.getPlatform().setOpacity(line, 0, true);
			this.nameAnchorPoint = Point.getMiddleOf(curveControl, this.nameAnchorPoint);
		}

		return line;
	}

	private GfxObject getSelfAngularLine() {
		this.leftPoint = this.leftNodeArtifact.getCenter().translate(0, -this.leftNodeArtifact.getHeight() / 2);
		this.rightPoint = this.leftNodeArtifact.getCenter().translate(this.leftNodeArtifact.getWidth() / 2, 0);
		this.computeDirectionsType();
		final GfxObject line = GfxManager.getPlatform().buildPath();
		final Point rightShiftedPoint = Point.add(this.rightPoint, new Point((this.order + 1) * OptionsManager.get("ReflexivePathXGap"), 0));
		final Point leftShiftedPoint = Point.add(this.leftPoint, new Point(0, -(this.order + 1) * OptionsManager.get("ReflexivePathYGap")));
		this.leftDirectionPoint = leftShiftedPoint;
		this.rightDirectionPoint = rightShiftedPoint;
		GfxManager.getPlatform().moveTo(line, this.leftPoint);
		GfxManager.getPlatform().lineTo(line, leftShiftedPoint);
		GfxManager.getPlatform().lineTo(line, new Point(rightShiftedPoint.getX(), leftShiftedPoint.getY()));
		GfxManager.getPlatform().lineTo(line, rightShiftedPoint);
		GfxManager.getPlatform().lineTo(line, this.rightPoint);
		GfxManager.getPlatform().setOpacity(line, 0, true);
		this.nameAnchorPoint = new Point((this.leftPoint.getX() + this.rightPoint.getX() + (this.order + 1) * OptionsManager.get("ReflexivePathXGap")) / 2,
				this.leftPoint.getY() - (this.order + 1) * OptionsManager.get("ReflexivePathYGap"));
		return line;
	}

	private GfxObject getSelfLine() {
		return OptionsManager.get("AngularLinks") == 1 ? this.getSelfAngularLine() : this.getSelfRightLine();
	}

	private GfxObject getSelfRightLine() {
		final int radius = (this.order + 1) * OptionsManager.get("ReflexivePathXGap");
		this.leftPoint = this.leftNodeArtifact.getLocation().clonePoint().translate(this.leftNodeArtifact.getWidth() - radius, 0);
		this.rightPoint = this.leftNodeArtifact.getLocation().clonePoint().translate(this.leftNodeArtifact.getWidth(), radius);
		this.computeDirectionsType();
		final Point edge = new Point(this.rightPoint.getX(), this.leftPoint.getY());
		final GfxObject line = GfxManager.getPlatform().buildCircle((this.order + 1) * OptionsManager.get("ReflexivePathXGap"));
		this.leftDirectionPoint = Point.add(this.leftPoint, new Point(0, -OptionsManager.get("ReflexivePathXGap")));
		this.rightDirectionPoint = Point.add(this.rightPoint, new Point(OptionsManager.get("ReflexivePathXGap"), 0));
		this.nameAnchorPoint = Point.add(edge, new Point(0, -(this.order + 1) * OptionsManager.get("ReflexivePathXGap")));
		GfxManager.getPlatform().translate(line, edge);
		GfxManager.getPlatform().setOpacity(line, 0, true);
		return line;
	}

}
