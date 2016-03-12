package com.hantsylabs.example.spring.config;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.BatchConfigurationException;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
public class JpaBatchConfigurer implements BatchConfigurer {
	private static final Logger logger = LoggerFactory
			.getLogger(JpaBatchConfigurer.class);

	@Inject	
	private DataSource dataSource;
	
	@Inject
	private PlatformTransactionManager transactionManager;
	
	private JobRepository jobRepository;
	private JobLauncher jobLauncher;
	private JobExplorer jobExplorer;


	protected JpaBatchConfigurer() {
	}


	@Override
	@Bean
	public JobRepository getJobRepository() {
		return jobRepository;
	}

	@Override
	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	@Override
	@Bean
	public JobLauncher getJobLauncher() {
		return jobLauncher;
	}

	@Override
	@Bean
	public JobExplorer getJobExplorer() {
		return jobExplorer;
	}

	@PostConstruct
	public void initialize() {
		try {
			if (dataSource == null) {
				logger.warn("No datasource was provided...using a Map based JobRepository");

				if (this.transactionManager == null) {
					this.transactionManager = new ResourcelessTransactionManager();
				}

				MapJobRepositoryFactoryBean jobRepositoryFactory = new MapJobRepositoryFactoryBean(
						this.transactionManager);
				jobRepositoryFactory.afterPropertiesSet();
				this.jobRepository = jobRepositoryFactory.getObject();

				MapJobExplorerFactoryBean jobExplorerFactory = new MapJobExplorerFactoryBean(
						jobRepositoryFactory);
				jobExplorerFactory.afterPropertiesSet();
				this.jobExplorer = jobExplorerFactory.getObject();
			} else {
				this.jobRepository = createJobRepository();

				JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
				jobExplorerFactoryBean.setDataSource(this.dataSource);
				jobExplorerFactoryBean.afterPropertiesSet();
				this.jobExplorer = jobExplorerFactoryBean.getObject();
			}

			this.jobLauncher = createJobLauncher();
		} catch (Exception e) {
			throw new BatchConfigurationException(e);
		}
	}

	private JobLauncher createJobLauncher() throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		jobLauncher.setTaskExecutor( new SimpleAsyncTaskExecutor());
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}

	protected JobRepository createJobRepository() throws Exception {
		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
		factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
		factory.setDataSource(dataSource);
		factory.setTransactionManager(transactionManager);
		factory.setValidateTransactionState(false);
		factory.afterPropertiesSet();
		return factory.getObject();
	}
	
	@Bean
	public JobBuilderFactory jobBuilderFactory(JobRepository jobRepository){
		return new JobBuilderFactory(jobRepository);
	}
	
	@Bean	
	public StepBuilderFactory stepBuilderFactory(JobRepository jobRepository, PlatformTransactionManager transactionManager){
		return new StepBuilderFactory(jobRepository, transactionManager);
	}
}
