package com.dummy.batch.info;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LeaflyLinkInfo implements TaskInfo {
	

	private String host;

	public LeaflyLinkInfo(String serviceEndpoint) {
		this.host = serviceEndpoint;
	}

	/*
	 * Requires YellowPageMetadataBuilder to generate the correct download URL
	 */
	public String getServiceEndpoint(){
		return this.host;
	}
	
	public static class LeaflyInfoBuilder {
		private String serviceEndpoint = "https://www.leafly.com/explore/category-{category}/{page}";
		
		private Map<String, String> placeholder = new HashMap<String, String>();

		public LeaflyInfoBuilder setServiceEndpoint(String serviceEndpoint) {
			this.serviceEndpoint = serviceEndpoint;
			return this;
		}

		public LeaflyInfoBuilder setCategory(String category) throws UnsupportedEncodingException {
			this.placeholder.put("\\{category\\}", category);
			return this;
		}
		
		public LeaflyInfoBuilder setPage(String page) {
			this.placeholder.put("\\{page\\}", page);
			return this;
		}

		/*
		 * Build the metadata class
		 */
		public LeaflyLinkInfo execute() {
			for(Map.Entry<String, String> entry : placeholder.entrySet()) {
				this.serviceEndpoint = this.serviceEndpoint.replaceAll(entry.getKey(), entry.getValue());
			}
			
			return new LeaflyLinkInfo(this.serviceEndpoint);
		}

	}


}
