/**
 * 
 * @author Sumit
 * PropertyManager class used to Get values from config file.
 */

package com.gorest.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
	private static PropertyManager _instance = null;
	public Properties testData;
	public InputStream fileConfig = null;

	/**
	 * Constructor of PropertyManager class to load config files
	 */
	public PropertyManager() {
		try {
			this.testData = new Properties();
			this.fileConfig = getClass().getClassLoader().getResourceAsStream("propertiesFile/config.properties");
			if (this.fileConfig != null) {
				this.testData.load(this.fileConfig);
				this.fileConfig.close();
			} else {
				System.out.println("Error on reading config file");
			}
		} catch (IOException e) {
			System.out.println("Error" + e);
		} finally {
			if (this.fileConfig != null) {
				try {
					this.fileConfig.close();
				} catch (IOException e) {
					System.out.println("Func:  Error occured while closing config file" + e);
				}
			}
		}
	}

	/**
	 * getInstance --> Check If the instance is null, if not returns existing else
	 * new instance of PropertyManager
	 * 
	 * @return PropertyManager class instance
	 */
	public static synchronized PropertyManager getInstance() {

		if (_instance == null) {
			_instance = new PropertyManager();
		}
		return _instance;
	}

	/**
	 * getValueForKey --> Returns value from config files 
	 * @param key  --> Name of key from config file
	 * @return     --> Value from Config file
	 */
	public String getValueForKey(String key) {
		return this.testData.getProperty(key);
	}

	/**
	 * valueFromConfig return value from CONFIG properties if value from environment
	 * is null.
	 * 
	 * @return--> The value from config file or from environment
	 */
	public String valueFromConfig(String key) {

		// Checking if value from Environment variable is null, if it is null then
		// value from config.properties will return
		try {
			if (System.getenv(key) == null) {
				return this.getValueForKey(key);
			}
			return System.getenv(key);

		} catch (NullPointerException | SecurityException e) {
			System.out.println("Error while getting value from config/environment because of :-" + e);
		}

		return null;
	}
}
