package com.dummy.batch.task;

import com.dummy.batch.info.TaskInfo;

/**
 * Each service has a list of task to execute and each task has a specific priority order
 * 
 * @author Ung
 *
 */
public interface Task {
	
	/*
	 * Each task has an operation to execute
	 */
	public void execute(TaskInfo info) throws TaskException;

}
