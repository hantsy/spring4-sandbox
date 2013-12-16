import org.apache.commons.dbcp.BasicDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean

import com.hantsylabs.example.spring.jpa.JpaConferenceDaoImpl
import com.hantsylabs.example.spring.service.ConferenceService




beans {
     dataSource(BasicDataSource) {		 
		 driverClassName = "org.h2.Driver"
         url = "jdbc:h2:mem:spring4-sandbox"
         username = "sa"
         password = ""
     }
	 
	 entityManagerFactory(LocalContainerEntityManagerFactoryBean){
		 persistenceProviderClass="org.hibernate.ejb.HibernatePersistence"
		 dataSource=dataSource
		 persistenceUnitName="persistenceUnit"
		 packagesToScan=["com.hantsylabs.example.spring.model"]
		 jpaProperties=[
			 "hibernate.format_sql":"true",
			 "hibernate.show_sql":"true",
			 "hibernate.hbm2ddl.auto":"create"
			 ]
	 }
	 
	 transactionManager(JpaTransactionManager){
		 entityManagerFactory=entityManagerFactory
	 }
	 
	 conferenceDao(JpaConferenceDaoImpl){
	 }
	 
	 conferenceService(ConferenceService){
		 conferenceDao=conferenceDao
	 }

 }