package br.org.ajg.utildev.example.threadpool.genericExample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.apache.commons.lang.StringUtils;

import br.org.ajg.utildev.example.threadpool.Chronometer;

public class Teste {
	
	public static void main(String[] args) throws Exception {

		int tasks = 200;// ......................... number of tasks
		long[] taskDuration = { 1000, 2000 };// ...... { minimumTime, maxTime }
		int nThreads = 100; // ....................... number of tasks executors
		int increment = 20;// ........................ 10 em 10, 20 em 20 ... etc

		LogUtil.disable();
		
		
		List<String> executorsSelected = new ArrayList<>(Arrays.asList(new String[]{
				"CachedThreadPool"
				,"CachedThreadPoolTF"
				,"FixedThreadPool"
				,"FixedThreadPoolTF"
				,"SingleThreadExecutor"
				,"SingleThreadExecutorTF"
				,"WorkStealingPool"
				,"WorkStealingPoolTF"
				,"ScheduledThreadPool"
				,"ScheduledThreadPoolTF"
				,"SingleThreadScheduledExecutor"
				,"SingleThreadScheduledExecutorTF"
			}));
		
		Map<String, TaskExecutorExample> taskExecutors;
		
		Map<Integer, Long[]> tempos = new HashMap<>(); 

		
		for(nThreads = increment; nThreads <= tasks; nThreads += increment){
			
			taskExecutors = createTaskExecutors(tasks, taskDuration, nThreads, executorsSelected);
			tempos.put(nThreads, new Long[executorsSelected.size()]);
			
			for(String executorName : executorsSelected){
				
				if(taskExecutors.get(executorName) != null){
					Chronometer cron = new Chronometer();
					cron.start();
					
					System.out.println(String.format("Executing %s tasks with %s executor using %d threads..." , tasks, executorName, nThreads));
					taskExecutors.get(executorName).execute();
					
					cron.end();
					
					tempos.get(nThreads)[executorsSelected.indexOf(executorName)] = cron.getTempoGasto();
				}
				
			}
			
		}
		
		imprimirTempoGasto(tempos, tasks, nThreads, increment, createTaskExecutors(tasks, taskDuration, nThreads, executorsSelected));
	}

	private static Map<String, TaskExecutorExample> createTaskExecutors(int tasks, long[] taskDuration, int nThreads, List<String> executorsSelected) {
		
		Map<String, TaskExecutorExample> allTaskExecutors = new HashMap<String, TaskExecutorExample>();
		
		ThreadFactory threadFactory = new CustomThreadFactoryBuilder()
					.setDaemon(false)
					.setNamePrefix("ajgPool")
					.setPriority(Thread.MAX_PRIORITY).build(); 
		
		//-- CachedThreadPool -----------------
		allTaskExecutors.put(allExecutorNames.get(0),new TaskExecutorExample(
				tasks, 
				taskDuration, 
				nThreads,Executors.newCachedThreadPool()));
		
		allTaskExecutors.put(allExecutorNames.get(1),new TaskExecutorExample(
				tasks, 
				taskDuration, 
				nThreads,Executors.newCachedThreadPool(threadFactory)));
		
		//-- FixedThreadPool -----------------
		allTaskExecutors.put(allExecutorNames.get(2),new TaskExecutorExample(
				tasks, 
				taskDuration, 
				nThreads,Executors.newFixedThreadPool(nThreads)));
		
		allTaskExecutors.put(allExecutorNames.get(3),new TaskExecutorExample(
				tasks, 
				taskDuration, 
				nThreads,Executors.newFixedThreadPool(nThreads, threadFactory)));
		
		//-- SingleThreadExecutor -----------------		
		allTaskExecutors.put(allExecutorNames.get(4),new TaskExecutorExample(
				tasks, 
				taskDuration, 
				nThreads,Executors.newSingleThreadExecutor()));
		
		allTaskExecutors.put(allExecutorNames.get(5),new TaskExecutorExample(
				tasks, 
				taskDuration, 
				nThreads,Executors.newSingleThreadExecutor(threadFactory)));;
				
		//-- WorkStealingPool -----------------
		allTaskExecutors.put(allExecutorNames.get(6),new TaskExecutorExample(
				tasks, 
				taskDuration, 
				nThreads,Executors.newWorkStealingPool()));
		
		allTaskExecutors.put(allExecutorNames.get(7),new TaskExecutorExample(
				tasks, 
				taskDuration, 
				nThreads,Executors.newWorkStealingPool(8)));
		
		//-- ScheduledThreadPool --------------
		allTaskExecutors.put(allExecutorNames.get(8),new TaskExecutorExample(
				tasks, 
				taskDuration, 
				nThreads,Executors.newScheduledThreadPool(nThreads)));
		
		allTaskExecutors.put(allExecutorNames.get(9),new TaskExecutorExample(
				tasks, 
				taskDuration, 
				nThreads,Executors.newScheduledThreadPool(nThreads, threadFactory)));
		
		//-- SingleThreadScheduledExecutor --------
		allTaskExecutors.put(allExecutorNames.get(10),new TaskExecutorExample(
				tasks, 
				taskDuration, 
				nThreads,Executors.newSingleThreadScheduledExecutor()));
		
		allTaskExecutors.put(allExecutorNames.get(11),new TaskExecutorExample(
				tasks, 
				taskDuration, 
				nThreads,Executors.newSingleThreadScheduledExecutor(threadFactory)));
		
		Map<String, TaskExecutorExample> onlySelectedTaskExecutors = new HashMap<String, TaskExecutorExample>();
		
		for(String name : executorsSelected){
			onlySelectedTaskExecutors.put(name, allTaskExecutors.get(name));
		}
		
		return onlySelectedTaskExecutors;
	}

	private static void imprimirTempoGasto(Map<Integer, Long[]> tempos, int tasks, int nThreads, int increment, Map<String, TaskExecutorExample> taskExecutors) {
		
		imprimirCabecalho(taskExecutors);
		Locale.setDefault(Locale.US);
		
		for(nThreads = increment; nThreads <= tasks; nThreads += increment){
			System.out.println(nThreads + ";" + StringUtils.join(tempos.get(nThreads), ";"));
		}
		
	}

	private static void imprimirCabecalho(Map<String, TaskExecutorExample> taskExecutors) {
		List<String> executors = new ArrayList<>();
		
		for(String name : allExecutorNames){
			if(taskExecutors.get(name) != null){
				executors.add(name);				
			}
		}
		
		
		System.out.println("nThreads;" + StringUtils.join(executors, ";"));
	}
	
	
	static List<String> allExecutorNames = new ArrayList<>(Arrays.asList(new String[]{
			"CachedThreadPool",
			"CachedThreadPoolTF",
			"FixedThreadPool",
			"FixedThreadPoolTF",
			"SingleThreadExecutor",
			"SingleThreadExecutorTF",
			"WorkStealingPool",
			"WorkStealingPoolTF",
			"ScheduledThreadPool",
			"ScheduledThreadPoolTF",
			"SingleThreadScheduledExecutor",
			"SingleThreadScheduledExecutorTF"}));
}
