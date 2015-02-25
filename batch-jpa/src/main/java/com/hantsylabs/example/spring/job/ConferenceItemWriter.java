package com.hantsylabs.example.spring.job;

import java.util.Date;
import java.util.List;

import org.springframework.batch.item.database.JpaItemWriter;

import com.hantsylabs.example.spring.model.Conference;

public class ConferenceItemWriter extends JpaItemWriter<Conference> {

	@Override
	public void write(List<? extends Conference> items) {
		for(Conference conf:items){
			conf.setName(conf.getName()+"@"+(new Date()));
		}
		super.write(items);
	}
}
