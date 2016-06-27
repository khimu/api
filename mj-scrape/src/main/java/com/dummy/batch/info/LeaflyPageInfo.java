package com.dummy.batch.info;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LeaflyPageInfo implements TaskInfo {
	
	public final static String root = "https://www.leafly.com/";

	private String host;

	public LeaflyPageInfo(String serviceEndpoint) {
		this.host = serviceEndpoint;
	}

	/*
	 * Requires YellowPageMetadataBuilder to generate the correct download URL
	 */
	public String getServiceEndpoint(){
		return this.host;
	}
	
	public static class LeaflyPageBuilder {
		private String serviceEndpoint = "https://www.leafly.com{link}";
		
		private Map<String, String> placeholder = new HashMap<String, String>();

		public LeaflyPageBuilder setServiceEndpoint(String serviceEndpoint) {
			this.serviceEndpoint = serviceEndpoint;
			return this;
		}

		public LeaflyPageBuilder setLink(String link) throws UnsupportedEncodingException {
			this.placeholder.put("\\{link\\}", link);
			return this;
		}

		/*
		 * Build the metadata class
		 */
		public LeaflyPageInfo execute() {
			for(Map.Entry<String, String> entry : placeholder.entrySet()) {
				this.serviceEndpoint = this.serviceEndpoint.replaceAll(entry.getKey(), entry.getValue());
			}
			
			return new LeaflyPageInfo(this.serviceEndpoint);
		}

	}

}
