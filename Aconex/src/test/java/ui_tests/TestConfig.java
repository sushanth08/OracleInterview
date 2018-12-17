package ui_tests;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;

public class TestConfig {

	
	private String url;
	private String username;
	private String password;
	private InputStream input = null;
	
	public TestConfig() throws Exception{
		Properties prop = new Properties();
		input = new FileInputStream("src/test/java/configFiles/ServerDetails.properties");
		prop.load(input);
		setUrl(prop.getProperty("url"));
		setUsername(prop.getProperty("username"));
		setPassword(prop.getProperty("password"));
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
