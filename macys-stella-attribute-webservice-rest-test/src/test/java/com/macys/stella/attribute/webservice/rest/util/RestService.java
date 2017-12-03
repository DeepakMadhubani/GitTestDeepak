package com.macys.stella.attribute.webservice.rest.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.ScenarioType;

import com.macys.stella.attribute.dbreader.impl.DataAccessClient;
import com.macys.stellaspecific.util.Tenant;

public abstract class RestService extends JbehaveStoryMapper {
	private final static String  CONFIG_PROPERTIES="rest-csi-trunk-properties.properties";
	private static Logger logger = Logger.getLogger(RestService.class);
	protected Properties properties;
	private String restHost;
	private String restPort;	
	private String restScheme = "http";
	private String dbDriver="oracle.jdbc.driver.OracleDriver";
	private DataAccessClient mcomStellaDbInstance;
	private String mcomStellaDbServerUrl;
	private String mcomStellaDbUsername;
	private String mcomStellaDbPwd;
	private String bcomStellaDbServerUrl;
	private String bcomStellaDbUsername;
	private String bcomStellaDbPwd;
	private DataAccessClient stellaDbInstance;
	private DataAccessClient bcomStellaDbInstance;
	private HttpClient httpClient;
	public HttpResponse httpResponse = null;
	public String jsonResponse = null;

	
	public RestService()
	{
		String fileName = System.getProperty("rest-congruent-properties.properties");
		if (fileName == null) {
			fileName = CONFIG_PROPERTIES;
		}
		logger.info("ENV FILE NAME: " + fileName);
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/config/" + fileName + "");
		properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		restHost = properties.getProperty("rest.host");
		restPort = properties.getProperty("rest.port");
		mcomStellaDbServerUrl = properties.getProperty("mcom.stella.jdbc.url");
		mcomStellaDbUsername = properties.getProperty("mcom.jdbc.username");
		mcomStellaDbPwd = properties.getProperty("mcom.jdbc.password");

		bcomStellaDbServerUrl = properties.getProperty("bcom.stella.jdbc.url");
		bcomStellaDbUsername = properties.getProperty("bcom.jdbc.username");
		bcomStellaDbPwd = properties.getProperty("bcom.jdbc.password");
		
	}
		protected DataAccessClient getStellaDataAccessClient(String tenant) throws SQLException 
		{
			if(tenant.equalsIgnoreCase(Tenant.MCOM.toString()))
			{
				if(mcomStellaDbInstance==null)
				{
					mcomStellaDbInstance=new DataAccessClient(dbDriver,mcomStellaDbServerUrl,mcomStellaDbUsername,mcomStellaDbPwd);
				}
				stellaDbInstance=mcomStellaDbInstance;
			}
			else{
				if(bcomStellaDbInstance==null)
				{
					bcomStellaDbInstance=new DataAccessClient(dbDriver,bcomStellaDbServerUrl,bcomStellaDbUsername,bcomStellaDbPwd);
				}
				stellaDbInstance=bcomStellaDbInstance;
			}
			
			return stellaDbInstance;
		}
		
		@BeforeScenario
		public void setUpHttpClientExample() throws Exception {
			httpClient =HttpClientBuilder.create().build();

		}
		
		@AfterScenario(uponType=ScenarioType.EXAMPLE)
		public void tearDownHttpClientExample() throws Exception {
			if (httpClient != null) {
				HttpClientBuilder.create().build().close();
				
				
			}	
		}
		
		
		protected String getRestUrlStringBuilder(String resourceCollection) {
			StringBuilder builder = new StringBuilder();
			builder.append(restScheme).append(UtilConstant.HOST_SEPARATOR)
			.append(restHost.trim()).append(UtilConstant.PORT_SEPARATOR).append(restPort.trim());

			if (!resourceCollection.startsWith(UtilConstant.PATH_SEPARATOR)) {
				builder.append(UtilConstant.PATH_SEPARATOR);
			}
			return builder.append(resourceCollection.trim()).toString();		
		}
		
/*		protected void createJSONResource(String url, String payload)
		throws Exception {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("userId", "yc03dk1");
			httpPost.setEntity(new StringEntity(payload, ContentType.APPLICATION_JSON));
			executeRequest(httpPost, "JSON");
}*/
		protected void executeRequest(HttpUriRequest request,
				String pyldtype) throws Exception {
			httpResponse = null;
			jsonResponse = null;
			try {
				request.setHeader("Content-Type",
						ContentType.APPLICATION_JSON.getMimeType());
				request.setHeader("userId",
						"yc03dk1");
				request.setHeader("Client_id","ESS");
				request.setHeader("subClient_id","ATR-UI");
				request.setHeader("Accept",
						ContentType.APPLICATION_JSON.getMimeType());
				request.setHeader("Authorization","Racfid yc03dk1");
				logger.info("Request "+request);
				httpResponse = httpClient.execute(request);

				logger.info("httpResponse: "+ httpResponse);
				jsonResponse = EntityUtils.toString(httpResponse.getEntity());
				logger.info("jsonResponse: "+ jsonResponse);

			} catch (Exception e) {
				logger.info("unable to execute request"+ e);
				request.abort();
				throw e;
			}
			
		}
		
		
}


