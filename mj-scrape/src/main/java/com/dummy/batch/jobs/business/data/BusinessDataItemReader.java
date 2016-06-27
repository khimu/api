package com.dummy.batch.jobs.business.data;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Queue;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dummy.batch.info.LeaflyLinkInfo;
import com.dummy.batch.info.LeaflyLinkInfo.LeaflyInfoBuilder;
import com.dummy.batch.info.LeaflyPageInfo;
import com.dummy.batch.info.LeaflyPageInfo.LeaflyPageBuilder;
import com.dummy.batch.jobitems.BusinessItem;
import com.dummy.batch.task.LeaflyLinkTask;
import com.dummy.batch.task.LeaflyPageTask;
import com.dummy.batch.task.TaskException;

/**
 * 
 * @author khimung
 *
 */
@Component("businessDataItemReader")
@Scope("step")
public class BusinessDataItemReader implements ItemReader<BusinessItem> { 
	
	private final static Logger logger = Logger.getLogger(BusinessDataItemReader.class);
	
	private String[] categories = new String[] {"hybrid", "indica", "sativa"};
	
	private Queue<String> links;
	
	
	@PostConstruct
	public void init() {
		
		links = new LinkedList<String>();

		/*
		 * One category only one info
		 */
		for(String category : categories) {
			buildInfos(category);
		}
	
	}
	
	private void buildInfos(String category) {
		try {
			LeaflyLinkInfo info = new LeaflyInfoBuilder().setCategory(category).setPage("").execute(); 
			LeaflyLinkTask task = new LeaflyLinkTask();
			task.execute(info);
			links.addAll(task.getLinks());
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		} catch (TaskException e) {
			logger.error(e.getMessage());
		}

	}

	
	@Override
	public BusinessItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
		if(links == null) {
			return null;
		}
		
		if(links.isEmpty()) {
			return null;
		}
		
		String link = links.remove();
		
		LeaflyPageInfo pageInfo = new LeaflyPageBuilder().setLink(link).execute(); 

		LeaflyPageTask pageTask = new LeaflyPageTask();
		pageTask.execute(pageInfo);
		
		BusinessItem item = new BusinessItem();
		item.setData(pageTask.getData());
		item.setFlavors(pageTask.getFlavors());
		item.setLink(link);
		
		return item;
	}

}
