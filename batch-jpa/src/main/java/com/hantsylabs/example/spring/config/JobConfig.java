
package com.hantsylabs.example.spring.config;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.hantsylabs.example.spring.job.ConferenceItemReader;
import com.hantsylabs.example.spring.job.ConferenceItemWriter;
import com.hantsylabs.example.spring.model.Conference;

/**
 * @author hantsy
 */
@Configuration
//@EnableBatchProcessing
public class JobConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	public void initialize(){
		ResourceDatabasePopulator populator=new ResourceDatabasePopulator();
		populator.setContinueOnError(true);
		populator.addScript(resourceLoader.getResource("classpath:/org/springframework/batch/core/schema-h2.sql"));
		
		DatabasePopulatorUtils.execute(populator, dataSource);
	}
	
	@Bean
	@JobScope
	public  ConferenceItemReader itemReader() {
		return new ConferenceItemReader(entityManagerFactory, "select c from Conference	");
	}

	@Bean
	@StepScope
	public ConferenceItemWriter itemWriter() {
		ConferenceItemWriter itemWriter = new ConferenceItemWriter();
		return itemWriter;
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Conference, Conference>chunk(5)
				.reader(itemReader())
				.writer(itemWriter())
				.build();
	}

	@Bean
	public Job javaJob() {
		return jobBuilderFactory.get("javaJob")
				.start(step1())
				.build();
	}

}