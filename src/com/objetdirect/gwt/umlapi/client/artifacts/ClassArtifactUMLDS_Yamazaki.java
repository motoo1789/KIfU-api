package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.List;

import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute_Yamazaki;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;

public class ClassArtifactUMLDS_Yamazaki extends NodeArtifact {

	ClassPartNameArtifact className;
	ClassPartAttributesArtifact_Yamazaki classAttributes;

	// メソッドも作る けど仮で既存のやつを入れておく
	ClassPartMethodsArtifact classMethods;

	public ClassArtifactUMLDS_Yamazaki()
	{
		this("Class");
	}

	public ClassArtifactUMLDS_Yamazaki(final String className)
	{
		this(className,"");
	}

	public ClassArtifactUMLDS_Yamazaki(final String className, final String stereoType)
	{
		super();
		this.className = new ClassPartNameArtifact(className,stereoType);
		this.classAttributes = new ClassPartAttributesArtifact_Yamazaki();
		this.classMethods = new ClassPartMethodsArtifact();
		this.nodeParts.add(this.className);
		this.nodeParts.add(this.classAttributes);
		this.nodeParts.add(this.classMethods);
		this.className.setNodeArtifact(this);
		this.classAttributes.setNodeArtifact(this);
		this.classMethods.setNodeArtifact(this);

	}
	public void addAttribute(final UMLClassAttribute_Yamazaki attribute) {
		this.classAttributes.add(attribute);
	}

	/**
	 * Add a {@link UMLClassMethod} to this class
	 *
	 * @param method
	 *            The method, sent to {@link ClassPartMethodsArtifact}
	 */

	public void addMethod(final UMLClassMethod method) {
		this.classMethods.add(method);
	}

	/**
	 * Getter for the attributes
	 *
	 * @return the list of attributes of this class
	 */
	public List<UMLClassAttribute_Yamazaki> getAttributes() {
		return this.classAttributes.getList();
	}

	/**
	 * Getter for the methods
	 *
	 * @return the list of methods of this class
	 */
	public List<UMLClassMethod> getMethods() {
		return this.classMethods.getList();
	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return this.className.getClassName();
	}

	@Override
	public MenuBarAndTitle getRightMenu() {
		// TODO 自動生成されたメソッド・スタブ
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		final MenuBarAndTitle classNameRightMenu = this.className.getRightMenu();
		final MenuBarAndTitle classAttributesRightMenu = this.classAttributes.getRightMenu();
		final MenuBarAndTitle classMethodsRightMenu = this.classMethods.getRightMenu();

		rightMenu.setName("Class:" + this.className.getClassName());

		rightMenu.addItem(classNameRightMenu.getName(), classNameRightMenu.getSubMenu());
		rightMenu.addItem(classAttributesRightMenu.getName(), classAttributesRightMenu.getSubMenu());
		rightMenu.addItem(classMethodsRightMenu.getName(), classMethodsRightMenu.getSubMenu());

		return rightMenu;
	}

	@Override
	public String toURL() {
		// TODO 自動生成されたメソッド・スタブ
		return "Class$" + this.getLocation() + "!" + this.className.toURL() + "!" + this.classAttributes.toURL() + "!" + this.classMethods.toURL();
	}

}
