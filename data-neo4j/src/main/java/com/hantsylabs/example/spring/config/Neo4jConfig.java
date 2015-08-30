package com.hantsylabs.example.spring.config;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = { "com.hantsylabs.example.spring.jpa" })
@EnableNeo4jRepositories(basePackages = { "com.hantsylabs.example.spring.neo4j" })
@EnableTransactionManagement()
public class Neo4jConfig extends Neo4jConfiguration {

	@Bean
	public Neo4jServer neo4jServer() {
		return new RemoteServer("http://localhost:7474");
	}

	@Bean
	public SessionFactory getSessionFactory() {
		// with domain entity base package(s)
		return new SessionFactory("com.hantsylabs.example.spring.model");
	}

	// needed for session in view in web-applications
	@Bean
	@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public Session getSession() throws Exception {
		return super.getSession();
	}

//	final CloseableHttpClient httpClient = HttpClients.createDefault();
//
//	@Bean
//	ApplicationListener<AfterSaveEvent> afterSaveEventApplicationListener() {
//		return new ApplicationListener<AfterSaveEvent>() {
//			@Override
//			public void onApplicationEvent(AfterSaveEvent event) {
//				Neo4jRequest<String> neo4jRequest = new DefaultRequest(httpClient);
//				if (event.getEntity() instanceof Person) {
//					Person person = (Person) event.getEntity();
//					// Construct the JSON statements
//					neo4jRequest.execute(endpoint, json);
//				}
//			}
//		};
//	}
	
//	
//	@Bean
//    ApplicationListener<BeforeSaveEvent> beforeSaveEventApplicationListener() {
//        return new ApplicationListener<BeforeSaveEvent>() {
//            @Override
//            public void onApplicationEvent(BeforeSaveEvent event) {
//                AcmeEntity entity = (AcmeEntity) event.getEntity();
//                entity.setUniqueId(acmeIdFactory.create());
//            }
//        };
//    }
//
//    @Bean
//    ApplicationListener<AfterSaveEvent> afterSaveEventApplicationListener() {
//        return new ApplicationListener<AfterSaveEvent>() {
//            @Override
//            public void onApplicationEvent(AfterSaveEvent event) {
//                AcmeEntity entity = (AcmeEntity) event.getEntity();
//                auditLog.onEventSaved(entity);
//            }
//
//    }
//
//    @Bean
//    ApplicationListener<AfterDeleteEvent> deleteEventApplicationListener() {
//        return new ApplicationListener<AfterDeleteEvent>() {
//            @Override
//            public void onApplicationEvent(AfterDeleteEvent event) {
//                AcmeEntity entity = (AcmeEntity) event.getEntity();
//                auditLog.onEventDeleted(entity);
//            }
//        };
//    }

}
