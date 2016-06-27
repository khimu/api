package com.dummy.batch.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.client.RestTemplate;

import com.dummy.batch.info.LeaflyPageInfo;
import com.dummy.batch.info.TaskInfo;


public class LeaflyPageTask implements Task {
	
	private final static Logger logger = Logger.getLogger(LeaflyPageTask.class);

	@Resource
	private RestTemplate restTemplate;
	
	private Map<String, Map<String, Float>> data = new HashMap<String, Map<String, Float>>();
	private List<String> flavors = new ArrayList<String>();
	
	@Override
	public void execute(TaskInfo info) throws TaskException {
		LeaflyPageInfo pageInfo = (LeaflyPageInfo) info;
		
		try {
			long start = System.currentTimeMillis();
			logger.info("Link " + pageInfo.getServiceEndpoint());
			Document doc = Jsoup.connect(pageInfo.getServiceEndpoint()).get();
			
			List<Element> headlines = doc.select("div[class=m-histogram]");
			
			if(headlines != null) {
				logger.info("headlines " + headlines.size());
				headlines.stream().forEach(t -> {
					String key = t.attr("ng-show").split("===")[1].replaceAll("'", "");
					logger.info("strain attribute " + key);
					List<Element> items = t.select("div[class=m-histogram-item-wrapper]");
					logger.info("histogram size " + items.size());
					Map<String, Float> descriptions = new HashMap<String, Float>();
					
					items.stream().forEach(i -> {
						String label = i.child(0).html();
						String value = i.select("div[class=m-attr-bar]").attr("style").split(":")[1].replaceAll("%", "");
						logger.info("label " + label + " value " + value);
						descriptions.put(label, Float.parseFloat(value));
					});
					data.put(key, descriptions);
				});
			}
			List<Element> elements = doc.select("section[class=strain__flavors]");
			logger.info("flavors " + elements.size());
			if(elements != null && elements.size() > 0) {
				List<Element> liElements = elements.get(0).select("li");
				liElements.stream().forEach(li -> {	
					logger.info("flavors " + li.select("i").get(0).text());
					flavors.add(li.select("i").get(0).text().replaceAll("([0-9]|\\.)+", "").toLowerCase());
				});
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new TaskException("Error: " + e.getMessage());
		}
	}

	public Map<String, Map<String, Float>> getData() {
		return data;
	}

	public List<String> getFlavors() {
		return flavors;
	}

}
