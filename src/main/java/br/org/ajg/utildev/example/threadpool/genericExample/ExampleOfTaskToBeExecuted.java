package br.org.ajg.utildev.example.threadpool.genericExample;

import static br.org.ajg.utildev.example.threadpool.genericExample.LogUtil.log;
import static java.lang.String.format;

import java.util.concurrent.Callable;

public class ExampleOfTaskToBeExecuted implements Callable<TaskReturnExample> {

	private Integer myID;
	private long[] taskDuration;
	
	public ExampleOfTaskToBeExecuted(Integer idOfTask, long[] taskDuration){
		this.myID = idOfTask;
		this.taskDuration = taskDuration;
	}
	
	@Override
	public TaskReturnExample call() throws Exception {		
		TaskReturnExample taskReturn = methodToBeExecuted();
		return taskReturn;
	}

	private TaskReturnExample methodToBeExecuted() throws InterruptedException {
		log(myID, format("start..."));
		
	    long aleatoryTime = SleepUtil.pauseAleatoryTime(taskDuration);
	    
	    log(myID, format("end in %5d", aleatoryTime));
	    
	    return new TaskReturnExample(myID); 
	}

}
