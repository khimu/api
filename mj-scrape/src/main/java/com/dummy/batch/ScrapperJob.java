package com.dummy.batch;

import org.apache.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author khimung
 *
 */
public class ScrapperJob {
	private final static Logger logger = Logger.getLogger(ScrapperJob.class);
	
	/*
	 * This needs to be an argument passed in
	 */
	public final static Integer QUOTA = 150000;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		String[] springConfig  = 
			{	
				"spring/batch/jobs/common-context.xml",
				"spring/batch/jobs/scrapper-context.xml"
			};

		
		ApplicationContext context = 
				new ClassPathXmlApplicationContext(springConfig);
		
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("mjScrapperJob");

		try {
			
			JobParametersBuilder builder = new JobParametersBuilder();
			
			JobExecution execution = jobLauncher.run(job, builder.toJobParameters());
			logger.info("Exit Status : " + execution.getStatus());
			
			
			if(execution.getStatus() == BatchStatus.FAILED) {
				for(Throwable t : execution.getAllFailureExceptions()) {
					t.printStackTrace();
				}
				
				logger.error("Batch job failed");
				System.exit(1);
			}else {
				logger.info("Batch job successful");
				System.exit(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.exit(1);
	}
	
}
