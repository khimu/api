package com.dummy.batch.jobitems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusinessItem extends BaseItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, Map<String, Float>> data = new HashMap<String, Map<String, Float>>();
	private List<String> flavors = new ArrayList<String>();
	private String link;

	public Map<String, Map<String, Float>> getData() {
		return data;
	}

	public void setData(Map<String, Map<String, Float>> data) {
		this.data = data;
	}

	public List<String> getFlavors() {
		return flavors;
	}

	public void setFlavors(List<String> flavors) {
		this.flavors = flavors;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
