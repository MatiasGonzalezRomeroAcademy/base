package com.academy.base.jobs;

import static java.lang.Thread.currentThread;
import static java.time.LocalDateTime.now;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.time.format.DateTimeFormatter;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ScheduledTask {
	private static final String EXECUTION = "Execution";
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	// @Scheduled(fixedRate = 2000)
	public void scheduleTaskWithFixedRate() {
		logTaskInfo("Fixed Rate", EXECUTION);
	}

	// @Scheduled(fixedDelay = 2000)
	public void scheduleTaskWithFixedDelay() {
		doTaskWithExcutionTime("Fixed Delay", 5);
	}

	// @Scheduled(fixedRate = 2000, initialDelay = 5000)
	public void scheduleTaskWithInitialDelay() {
		logTaskInfo("Fixed Rate", EXECUTION);
	}

	@Scheduled(cron = "* * * * * ?")
	public void scheduleTaskWithCronExpression() {
		doTaskWithExcutionTime("1 option", 5);
	}

	@Scheduled(cron = "* * * * * ?")
	public void scheduleTaskWithCronExpression2() {
		doTaskWithExcutionTime("2 option", 5);
	}

	private void doTaskWithExcutionTime(final String taskName, final int executionTime) {
		logTaskInfo(taskName, EXECUTION);

		try {
			SECONDS.sleep(executionTime);
		} catch (final InterruptedException ex) {
			log.error("Something goes wrong", ex);
			currentThread().interrupt(); // mark thread as interrupted
			throw new IllegalStateException(ex);
		}

		logTaskInfo(taskName, "Finished");
	}

	private void logTaskInfo(final String logMethod, final String taskType) {
		log.info("A {} task :: {} Time - {} current thread {}", //
				logMethod, //
				taskType, //
				dateTimeFormatter.format(now()), //
				currentThread().getName() //
		);
	}
}
