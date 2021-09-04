package com.objetdirect.gwt.umlapi.client.yamazaki.dao;

import java.util.HashMap;
import java.util.List;

import com.objetdirect.gwt.umlapi.client.yamazaki.elements.IElements;



public interface IGetElements {
	HashMap<String, Integer> getArtifactMap();
	HashMap<String, IElements> getClassMap();
	HashMap<String, List<IElements>> getFieldMap();
	HashMap<String, List<IElements>> getMethodMap();
}
