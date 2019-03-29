package br.org.ajg.utildev.example.threadpool.genericExample;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomUtils;


public abstract class SleepUtil {

	static final int MIN = 0;
	static final int MAX = 1;

	public static long pauseAleatoryTime(long[] taskDurationBetween) throws InterruptedException {

		long aleatoryTime = RandomUtils.nextLong(taskDurationBetween[MIN], taskDurationBetween[MAX]);

		TimeUnit.MILLISECONDS.sleep(aleatoryTime);

		return aleatoryTime;
	}
}
