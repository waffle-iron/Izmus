package com.izmus.data.configurations;

import java.io.Serializable;
import java.util.HashMap;

import org.springframework.web.servlet.ModelAndView;

public class ViewConfig implements Serializable {
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	private String view;
	private HashMap<String, Object> parameterMap;
	/*----------------------------------------------------------------------------------------------------*/
	public String getView() {
		if (view == null){
			setView("home");
		}
		return view;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void setView(String view) {
		this.view = view;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void addParameter(String key, Object value){
		if (parameterMap == null){
			parameterMap = new HashMap<>();
		}
		parameterMap.put(key, value);
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void addAllParameters(HashMap<? extends String, ? extends Object> allParameters){
		if (parameterMap == null){
			parameterMap = new HashMap<>();
		}
		parameterMap.putAll(allParameters);
	}
	/*----------------------------------------------------------------------------------------------------*/
	public HashMap<String, Object> getParameterMap() {
		if (parameterMap == null){
			parameterMap = new HashMap<>();
		}
		return parameterMap;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public ModelAndView getModelAndView() {
		ModelAndView returnView = new ModelAndView(getView());
		if (parameterMap != null){
			returnView.addAllObjects(parameterMap);
		}
		return returnView;
	}
	/*----------------------------------------------------------------------------------------------------*/
	public void copyConfig(ViewConfig config) {
		this.addAllParameters(config.getParameterMap());
		this.setView(config.getView());
	}
}
