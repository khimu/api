package com.dummy.batch.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "business_info")
public class BusinessData extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, Map<String, Float>> data = new HashMap<String, Map<String, Float>>();
	private List<String> flavors = new ArrayList<String>();

	private String name;
	private String category;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
