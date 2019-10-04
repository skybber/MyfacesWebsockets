package org.myfaces.websocket.test;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.Future;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.apache.deltaspike.scheduler.api.Scheduled;
import org.apache.deltaspike.scheduler.spi.Scheduler;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@ApplicationScoped
@Scheduled(cronExpression = "0/10 * * * * ?", onStartup = false)
@DisallowConcurrentExecution
public class Pinger implements Serializable, org.quartz.Job {

	private static final long serialVersionUID = 1L;

	@Inject
	private Scheduler<Job> jobScheduler;

	@Inject @Push(channel="dispatcherChannel")
	private PushContext dispatcherChannel;

	public void init(@Observes @Initialized(ApplicationScoped.class) ServletContext ctx)
	{
		jobScheduler.registerNewJob(Pinger.class);
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		Set<Future<Void>> futures = dispatcherChannel.send("onlineNotifPushEvent", 1L);
	}
}
