package com.api.cron.batch.common.tasklet;

import org.springframework.stereotype.Component;

import com.api.cron.batch.jobitems.JobState;

@Component("duplicateCheck")
public class DuplicateCheck {

	public boolean isDuplicate(JobState state, String name) {
		synchronized(this) {
			if(name != null && state.getDupByStoreIds().keySet().contains(name)) {
				return true;
			}
			return false;
		}
		
	}
}
