package com.dummy.batch.partitioner;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Partition the number of records to process by evenly distributing 
 * them across the number of threads
 * 
 * @author Ung
 *
 */
@Component("rangePartitioner")
@Scope("step")
public class RangePartitioner implements Partitioner {
	
	/*
	 * quota for the job
	 */
	@Value("#{jobParameters['quota']}")
	private int quota;
	
	
	/*
	 * last primary key from previous job
	 */
	@Value("#{jobParameters['lastKey']}")
	private int lastKey;
	
	

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {

		Map<String, ExecutionContext> result 
                       = new HashMap<String, ExecutionContext>();

		int chunkSize = quota / gridSize;
		
		int remaining = quota % gridSize;
		
		if(remaining > 0) {
			chunkSize ++;
		}
		
		// range will be starting point plus one to starting point plus chunk size
		// next starting point will be starting point plus chunk size plus one to starting point plus chunk size times two
		// next starting point will be starting point plus chunk size times two plus one to starting point plus chunk size times 3
		// but the total chunk size added up thus far has to be less than the quota amount.
		// and if not, use the quota amunt as the ending point.
		
		int totalChunkSize = 0;

		for (int i = 0; i <= gridSize; i++) {
			ExecutionContext value = new ExecutionContext();
			
			// starting point id
			int fromId = lastKey + (i * chunkSize) + 1;
			
			// ending point id should always be the new starting point plus the chunk size
			int toId = lastKey + ((i + 1) * chunkSize);
			
			// track the number of chunks already allocated
			totalChunkSize += chunkSize;
			
			// This should be the last thread to fill the id range
			if(totalChunkSize > quota) {
				toId = quota;
			}
			

			System.out.println("\nStarting : Thread" + i);
			System.out.println("fromId : " + fromId);
			System.out.println("toId : " + toId);

			value.putInt("fromId", fromId); // 1 * {last_key}
			value.putInt("toId", toId);  // 1500 * {last_key}

			// give each thread a name, thread 1,2,3
			value.putString("name", "Thread" + i);
			
			result.put("partition" + i, value);
		}

		return result;
	}

}

