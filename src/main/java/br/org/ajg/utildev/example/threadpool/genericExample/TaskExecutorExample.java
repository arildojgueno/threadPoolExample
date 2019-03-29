package br.org.ajg.utildev.example.threadpool.genericExample;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TaskExecutorExample {

	static Integer ID = 1;
	
	private int tasks;
	private long[] taskDuration;
	private int threads;
	private ExecutorService executorService;

	public TaskExecutorExample(int tasks, long[] taskDuration, int threads, ExecutorService executorService) {
		super();
		this.tasks = tasks;
		this.taskDuration = taskDuration;
		this.threads = threads;
		this.executorService = executorService;
	}
	
	
	

	public ExecutorService getExecutorService() {
		return executorService;
	}




	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}




	public void execute() throws InterruptedException, Exception {
		
		List<ExampleOfTaskToBeExecuted> taskList = createTasks();

		List<Future<br.org.ajg.utildev.example.threadpool.genericExample.TaskReturnExample>> results = executorService.invokeAll(taskList);

		executorService.shutdown();
		
		try {
			
			boolean sucess = executorService.awaitTermination(800, TimeUnit.MILLISECONDS);
			
			if (!sucess) {
				for(Future f : results){
					TaskReturnExample object = (TaskReturnExample) f.get(1000, TimeUnit.MILLISECONDS);
				}
				
			} else {
				executorService.shutdownNow();
			}
			
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			executorService.shutdownNow();
			throw e;
		}
	}

	private List<ExampleOfTaskToBeExecuted> createTasks() {

		List<ExampleOfTaskToBeExecuted> taskList = new ArrayList<>();

		for (int i = 0; i < tasks; i++) {
			taskList.add(new ExampleOfTaskToBeExecuted(ID++, taskDuration));
		}

		return taskList;
	}
}
