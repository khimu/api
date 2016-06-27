package com.api.cron.batch.services;

import com.api.cron.batch.CronJobException;

public interface TransactionRawServiceInterface {
	
	public void updateIsSaved(String ids) throws CronJobException;

}
