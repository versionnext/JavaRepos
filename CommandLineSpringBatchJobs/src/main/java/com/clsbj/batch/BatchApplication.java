package com.clsbj.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@ComponentScan("com.clsbj.batch")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJms
public class BatchApplication implements CommandLineRunner {

	@Autowired
	ApplicationContext context;

	private static final Logger log = LoggerFactory.getLogger(BatchApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(BatchApplication.class, args);
		log.info("job finished");
		ctx.close();
	}

	public void run(String... args) throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {

		if (args != null && args.length > 0 && args[0] != null) {
			log.info("Job name - " + args[0]);
			JobLauncher jobLauncher = context.getBean(JobLauncher.class);
			Job myJob = null;
			if ("userfilewithoutprocessor".equalsIgnoreCase(args[0])) {
				myJob = context.getBean("userFileWithoutProcessorJob", Job.class);
			}else if ("userfilewithprocessor".equalsIgnoreCase(args[0])) {
				myJob = context.getBean("userFileWithProcessorJob", Job.class);
			}				
			
			if (myJob != null) {
				jobLauncher.run(myJob,
						new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters());
			}

		}

	}
}
