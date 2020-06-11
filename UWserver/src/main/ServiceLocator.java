package main;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
/* 用ServiceLocator取得context.xml的連線池設定 */
public class ServiceLocator {
	private Context initalContext;

	private static ServiceLocator serviceLocator = new ServiceLocator();

	public static ServiceLocator getInstance() {
		return serviceLocator;
	}

	private ServiceLocator() {
		try {
			this.initalContext = new InitialContext();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public DataSource getDataSource() {
		DataSource datasource = null;
		try {
			Context ctx = (Context) initalContext.lookup("java:comp/env");
			datasource = (DataSource) ctx.lookup("jdbc/UWISH");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return datasource;
	}
	
	public DataSource getDataSource(String dataSourceName) {
		DataSource datasource = null;
		try {
			Context ctx = (Context) initalContext.lookup("java:comp/env");
			datasource = (DataSource) ctx.lookup(dataSourceName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return datasource;
	}
}