/**
 * 
 */
package com.nocode.config;

import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nocode.client.ConfigManager;
import com.nocode.constants.ConfigProperty;
import com.nocode.constants.DatabaseConstants;
import com.nocode.constants.ResourceFile;
import com.nocode.exception.DatabaseException;
import com.nocode.utils.JsonUtil;
import com.nocode.utils.PropertyUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class DataSourceConfig.
 *
 * @author Pavan.DV
 * @since 1.0.0
 */
@Configuration
public class DataSourceConfig {

	/**
	 * Gets the data source.
	 *
	 * @param system the system
	 * @return the data source
	 */
	@Bean
	public DataSource getDataSource(String system) {
		DataSourceBuilder<?> dataSourceBuilder = buildDataSource(system);
		return dataSourceBuilder.build();
	}

	/**
	 * Builds the data source.
	 *
	 * @param system the system
	 * @return the data source builder
	 */
	private DataSourceBuilder<?> buildDataSource(String system) {
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		JSONArray jsonArr = JsonUtil.readJsonFile(new PropertyUtil().getResourceFile(ResourceFile.DB_FILE));
		String env = ConfigManager.get(ConfigProperty.ENV);
		for (Object obj : jsonArr) {
			JSONObject json = new JSONObject(obj.toString());
			if (json.keySet().toString().toLowerCase().contains(env.toLowerCase())) {
				dataSourceBuilder.username(
						json.getJSONObject(env).getJSONObject(system).get(DatabaseConstants.USERNAME).toString());
				dataSourceBuilder.password(
						json.getJSONObject(env).getJSONObject(system).get(DatabaseConstants.PASSWORD).toString());
				String dbType = json.getJSONObject(env).getJSONObject(system).get(DatabaseConstants.DATABASE_TYPE)
						.toString().toLowerCase();
				String port = json.getJSONObject(env).getJSONObject(system).get(DatabaseConstants.PORT).toString();
				String host = json.getJSONObject(env).getJSONObject(system).get(DatabaseConstants.HOST).toString();
				String database = json.getJSONObject(env).getJSONObject(system).get(DatabaseConstants.DATABASE)
						.toString();
				switch (dbType) {
				case "mysql":
					dataSourceBuilder.driverClassName(DatabaseDriver.MYSQL.getDriverClassName());
					dataSourceBuilder
							.url(String.format("jdbc:mysql://%1$s%2$s%3$s%4$s%5$s", host, ":", port, "/", database));
					break;
				case "oracle":
					dataSourceBuilder.driverClassName(DatabaseDriver.ORACLE.getDriverClassName());
					dataSourceBuilder.url(
							String.format("jdbc:oracle:thin:@%1$s%2$s%3$s%4$s%5$s", host, ":", port, ":", database));

					break;
				case "postgresql":
					dataSourceBuilder.driverClassName(DatabaseDriver.POSTGRESQL.getDriverClassName());
					dataSourceBuilder.url(
							String.format("jdbc:postgresql://%1$s%2$s%3$s%4$s%5$s", host, ":", port, "/", database));
					break;
				case "derby":
					dataSourceBuilder.driverClassName(DatabaseDriver.DERBY.getDriverClassName());
					dataSourceBuilder
							.url(String.format("jdbc:derby://%1$s%2$s%3$s%4$s%5$s", host, ":", port, "/", database));
					break;
				case "h2":
					dataSourceBuilder.driverClassName(DatabaseDriver.H2.getDriverClassName());
					dataSourceBuilder.url("jdbc:h2:~/test");
					break;
				default:
					throw new DatabaseException(
							"Database type " + dbType + " not configured, please contact developer");
				}
			}
		}
		return dataSourceBuilder;
	}

}