package com.dummy.batch.listeners;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dummy.batch.model.BaseModel;

/**
 * 
 * 
 * @author Ung
 *
 */
@Component("cronWriteListener")
@Scope("step")
public class CronWriteListener implements ItemWriteListener<BaseModel> {
	private final static Logger logger = Logger.getLogger(CronWriteListener.class);

	@Override
	public void beforeWrite(List<? extends BaseModel> items) {
		logger.debug("ItemWriteListener - beforeWrite item count " + items.size());
	}

	
	
	@Override
	public void afterWrite(List<? extends BaseModel> items) {
		logger.debug("ItemWriteListener - afterWrite item count " + items.size());
	}

	
	
	@Override
	public void onWriteError(Exception exception, List<? extends BaseModel> items) {
		logger.debug("ItemWriteListener - onWriteError");
	}

}
