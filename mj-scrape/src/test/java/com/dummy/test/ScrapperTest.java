package com.dummy.test;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.dummy.batch.info.LeaflyLinkInfo;
import com.dummy.batch.info.LeaflyLinkInfo.LeaflyInfoBuilder;
import com.dummy.batch.info.LeaflyPageInfo;
import com.dummy.batch.info.LeaflyPageInfo.LeaflyPageBuilder;
import com.dummy.batch.jobitems.BusinessItem;
import com.dummy.batch.task.LeaflyLinkTask;
import com.dummy.batch.task.LeaflyPageTask;
import com.dummy.batch.task.TaskException;

public class ScrapperTest {
	
	private String[] categories = new String[] {"hybrid", "indica", "sativa"};
	

	@Test
	public void testLeafly() {
		try {
			LeaflyLinkInfo info = new LeaflyInfoBuilder().setCategory("hybrid").setPage("").execute(); 
			LeaflyLinkTask linkTask = new LeaflyLinkTask();
			linkTask.execute(info);
			
			
			String link = linkTask.getLinks().remove();
			
			LeaflyPageInfo pageInfo = new LeaflyPageBuilder().setLink(link).execute(); 

			LeaflyPageTask pageTask = new LeaflyPageTask();
			pageTask.execute(pageInfo);
			
			BusinessItem item = new BusinessItem();
			item.setData(pageTask.getData());
			item.setFlavors(pageTask.getFlavors());
			
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		} catch (TaskException e) {
			System.out.println(e.getMessage());
		}

	}
}
