package edu.sjsu.cmpe.c272;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.nosql.json.api.DB;
import com.ibm.nosql.json.api.DBCollection;
import com.ibm.nosql.json.api.NoSQLClient;

public class DB2JsonStore {

	private DB dbStore;
	private static Log log = LogFactory.getLog(DB2JsonStore.class);
	
	public DB getDbStore() {
		return dbStore;
	}

	public void setDbStore(DB dbStore) {
		this.dbStore = dbStore;
	}
	
	public void setDbStore(File dbPropertiesFile) throws Exception {
		System.out.println("Loading properties");
		Properties properties = new Properties();
		try {
			properties.load(new FileReader(dbPropertiesFile));
		} catch (Exception e) {
			String errorMessage = "Failed to load properties file " + dbPropertiesFile.getName(); 
			log.error(errorMessage);
			throw new Exception (errorMessage);
		}
		
		String jdbcUrl = properties.getProperty("jdbcUrl");
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		String schema = properties.getProperty("schema");
		
		this.dbStore = NoSQLClient.getDB(jdbcUrl, username, password, schema);
		
		if (this.dbStore == null) {
			String errorMessage = "Failed to get DB Connection. Make sure the properties are correct.";
			log.error(errorMessage);
			throw new Exception (errorMessage);
		} else {
			log.info("Successful connection.");
		}
		
	}
	
	public void setDbStore(String jdbcUrl) throws Exception {
		this.dbStore = NoSQLClient.getDB(jdbcUrl);
		if (this.dbStore == null) {
			String errorMessage = "Failed to get DB Connection. Make sure the JDBC url is correct.";
			log.error(errorMessage);
			throw new Exception (errorMessage);
		}
	}
	
	public void setDbStore(Connection connection) throws Exception {
		this.dbStore = NoSQLClient.getDB(connection);
		if (this.dbStore == null) {
			String errorMessage = "Failed to get DB Connection. Make sure the connection information is correct";
			log.error(errorMessage);
			throw new Exception (errorMessage);
		}
	}
	
	public void setDbStore(DataSource dataSource) throws Exception {
		this.dbStore = NoSQLClient.getDB(dataSource);
		if (this.dbStore == null) {
			String errorMessage = "Failed to get DB Connection. Make sure the datasource information is correct";
			log.error(errorMessage);
			throw new Exception (errorMessage);
		}
	}
	
	public void storeData(String jsonTweet, String collectionName) {
		DB jsonDB = getDbStore();
		DBCollection collection = null;
		if (jsonDB != null) {
			collection = jsonDB.getCollection(collectionName);
			collection.insert(jsonTweet);
		} else {
			log.error("DB is not set!!");
		}		
	}
	
}
