
package com.hantsylabs.example.spring.config;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.hantsylabs.example.spring.job.ConferenceItemProcessor;
import com.hantsylabs.example.spring.job.ConferenceItemReader;
import com.hantsylabs.example.spring.job.ConferenceItemWriter;
import com.hantsylabs.example.spring.model.Conference;

/**
 * @author hantsy
 */
@Configuration
@EnableBatchProcessing
public class JobConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
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
		return new ConferenceItemReader(dataSource);
	}

	@Bean
	@StepScope
	public ConferenceItemWriter itemWriter() {
		return new ConferenceItemWriter(dataSource);
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Conference, Conference>chunk(5)
				.reader(itemReader())
				.processor(new ConferenceItemProcessor())
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