package com.dummy.batch.jobs.business.data;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.dummy.batch.model.BaseModel;
import com.dummy.batch.model.BusinessData;
import com.dummy.batch.task.TaskException;

/**
 * retrieve store by name
 * retrieve store category by category store id
 * 
 * if duplicate log and skip
 * 
 * retrieve business location with geocode
 * retrieve business data with google places API
 * 
 * @author Ung
 *
 */
@Component("businessDataItemWriter")
@Scope("step")
public class BusinessDataItemWriter implements ItemWriter<BaseModel>{
	
	@Resource
	private MongoTemplate mongoTemplate;

	@Override
	public void write(List<? extends BaseModel> items) throws Exception {
		try {
			for(BaseModel item : items) {
				BusinessData data = (BusinessData) item;
				mongoTemplate.save(data);
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new TaskException(e.getMessage());
		}
	}

}
