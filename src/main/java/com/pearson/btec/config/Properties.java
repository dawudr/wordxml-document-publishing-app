package com.pearson.btec.config;


import java.io.IOException;
import java.io.InputStream;


public class Properties {

	private static java.util.Properties props = loadProperties();
	
	public static String host = props.getProperty("bca.host");
	
	public static int port = Integer.parseInt(props.getProperty("bca.port"));
	
	public static String user = props.getProperty("bca.writer_user");
	
	public static String password = props.getProperty("bca.writer_password");
	
	protected static String admin_user = props.getProperty("bca.admin_user");
	
	protected static String admin_password = props.getProperty("bca.admin_password");
	
	public static String host_path = props.getProperty("bca.host.path");
	
	public static String host_input_path = props.getProperty("bca.host.input.path");
	
	public static String host_output_path = props.getProperty("bca.host.output.path");
	
	public static String host_server_drive = props.getProperty("bca.host.server.drive");

	public static String host_server_path = props.getProperty("bca.host.server.path");
	
	public static String host_xsl_path = props.getProperty("bca.host.xsl.path");

	// get the configuration for the example
	private static java.util.Properties loadProperties() {
	    try {
			String propsName = "Config.properties";
			InputStream propsStream =
				Properties.class.getClassLoader().getResourceAsStream(propsName);
			if (propsStream == null)
				throw new IOException("Could not read config properties");

			java.util.Properties props = new java.util.Properties();
			props.load(propsStream);

			return props;

	    } catch (final IOException exc) {
	        throw new Error(exc);
	    }  
	}
}
