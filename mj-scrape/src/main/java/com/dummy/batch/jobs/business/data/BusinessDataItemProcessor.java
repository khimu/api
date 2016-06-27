package com.dummy.batch.jobs.business.data;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dummy.batch.jobitems.BusinessItem;
import com.dummy.batch.model.BusinessData;

@Component("businessDataItemProcessor")
@Scope("step")
public class BusinessDataItemProcessor implements ItemProcessor<BusinessItem, BusinessData> { 
	
	@Override
	public BusinessData process(BusinessItem item) throws Exception {
		String[] temp = item.getLink().split("/");
		
		BusinessData data = new BusinessData();
		data.setData(item.getData());
		data.setFlavors(item.getFlavors());
		data.setName(temp[2]);
		data.setCategory(temp[1]);

		return data;
	}

}
