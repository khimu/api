package com.dummy.batch.task;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.client.RestTemplate;

import com.dummy.batch.info.LeaflyLinkInfo;
import com.dummy.batch.info.TaskInfo;

public class LeaflyLinkTask implements Task {
	
	private final static Logger logger = Logger.getLogger(LeaflyPageTask.class);
	
	private int APPROX_ITEMS_PER_PAGE = 48;
	
	@Resource
	private RestTemplate restTemplate;

	private Queue<String> links = new LinkedList<String>();
	
	private int totalstrainResults;
	
	@Override
	public void execute(TaskInfo info) throws TaskException {
		LeaflyLinkInfo linkInfo = (LeaflyLinkInfo) info;
		 
		try {
			logger.info(linkInfo.getServiceEndpoint());
			Document doc = extractLinks(linkInfo.getServiceEndpoint());
			
			Elements totalPages = doc.select("strong[ng-bind=totalResults]");
			logger.info("data " + totalPages.html());
			for(Element t : totalPages) {
				logger.info( t.text() );
				totalstrainResults = Integer.parseInt( StringUtils.trim( t.text() ) );
			}
			
			int pages = totalstrainResults / APPROX_ITEMS_PER_PAGE;
			
			if(totalstrainResults % APPROX_ITEMS_PER_PAGE > 0){
				pages ++;
			}
			
			if(pages > 1) {
				for(int page = 0; page < pages; page ++) {
					extractLinks(linkInfo.getServiceEndpoint() + "page-" + page);
				}
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new TaskException("Error: " + e.getMessage());
		}
	}
	
	private Document extractLinks(String link) throws IOException {
		logger.info("link " + link);
		Document doc = Jsoup.connect(link).get();
		
		List<Element> temp = doc.select("a[class=ga_Explore_Strain_Tile]");
		
		logger.info("link size " + temp.size());
		
		if(temp != null) {
			temp.stream().forEach(t -> {
				links.add("" + t.attr("href"));
				logger.info("href " + t.attr("href"));
			});
		}
		
		return doc;
	}

	public Queue<String> getLinks() {
		return links;
	}

}
