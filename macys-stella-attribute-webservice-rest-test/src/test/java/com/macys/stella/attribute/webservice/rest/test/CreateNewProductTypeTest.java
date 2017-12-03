package com.macys.stella.attribute.webservice.rest.test;

import java.sql.SQLException;
import java.util.List;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.ScenarioType;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import com.macys.stella.attribute.dbreader.impl.StellaCommonDbQueries;
import com.macys.stella.attribute.webservice.rest.response.bean.CreateProductTypeJsonResponce;
import com.macys.stella.attribute.webservice.rest.util.JsonUtil;
import com.macys.stella.attribute.webservice.rest.util.PayloadCreationUtil;
import com.macys.stella.attribute.webservice.rest.util.ResponseCodeValidator;
import com.macys.stella.attribute.webservice.rest.util.RestService;
import com.macys.stella.attribute.webservice.rest.util.UtilConstant;

public class CreateNewProductTypeTest extends RestService{

	private static final Logger log = LoggerFactory
	.getLogger(CreateNewProductTypeTest.class);
	private String resource = "";
	private JsonUtil jsonUtil = new JsonUtil();
	private String tenantPort;
	private String url;
	private String serviceUrl;
	private String payload;
	private String visibleFlagStatus;
	private CreateProductTypeJsonResponce createProductTypeJsonResponce;
	private String productTypeId;
	
	
	
	@BeforeScenario(uponType=ScenarioType.EXAMPLE)
	public void setUpHttpClient() throws Exception
	{
		setUpHttpClientExample();
	}
	
	
	@Given("the product type create API resource available at: $resourceUrl")
	public void getResource(@Named("resourceUrl") String resourceUrl)
	{
		resource=resourceUrl;
	}
	
	@When("I create a product type for tenant <tenant> with a product type name as <productTypeName> is Active flag as <isActiveFlag> with department list as <departmentLists>")
	public void createProductType(@Named("tenant") String tenant,@Named("productTypeName") String prodTypeName,@Named("isActiveFlag") String isActiveFlag, @Named("departmentLists") List<String> deptLists) throws Exception
	{
		tenantPort=(tenant.equalsIgnoreCase("MCOM"))? "MCY":"BLM";
		url = resource.concat(tenantPort+UtilConstant.PATH_SEPARATOR+"productTypes");
		serviceUrl = getRestUrlStringBuilder(url);
		log.info("Service URL:" + serviceUrl);
		payload=PayloadCreationUtil.createJsonPayloadForCreate(prodTypeName, isActiveFlag, deptLists);
		log.info("Input Payload: " + payload);
		postJSONResource(serviceUrl, payload);
		createProductTypeJsonResponce=jsonUtil.getObjectFromJsonResponse(jsonResponse, CreateProductTypeJsonResponce.class);

	}
	
	@When("I connect to STELLA DB with tenant <tenant> for validating status of Visible flag in STELLA DB")
	public void connectStellaDB(@Named("tenant") String tenant) throws SQLException
	{
		visibleFlagStatus=StellaCommonDbQueries.getVisibleFlagStatus(getStellaDataAccessClient(tenant),productTypeId);	
	}
	
	@Then("I expect the create request to return with responseCode as <responseCode>")
	public void checkResponses(@Named("responseCode") int responseCode){

		log.info("Response Code: "+responseCode);
		new ResponseCodeValidator(responseCode).checkResponseCode(httpResponse);
	}
	
	@Then("I should see the Visible flag status as <visibleFlagStatus>")
	public void validateVisibleFlag(@Named("visibleFlagStatus") String visFlagSta)
	{
		Assert.assertThat("Visible flag status does not matched in STELLA DB",visibleFlagStatus,is(equalTo(visFlagSta)));
	}
	
	@Then("I expect a valid Product type id")
	public void getProductTypeIdFromResponce()
	{
		productTypeId=createProductTypeJsonResponce.getId();
		log.info("Product Type id:: "+productTypeId);
		
		Assert.assertNotNull("Product type id is not created through Service", createProductTypeJsonResponce.getId());
	}
	
	protected void postJSONResource(String url, String payload)
	throws Exception {
		HttpPost httppost = new HttpPost(url);

		httppost.setEntity(new StringEntity(payload, ContentType.APPLICATION_JSON));
		executeRequest(httppost, "JSON");
	}
	
	@AfterScenario(uponType=ScenarioType.EXAMPLE)
	public void tearDownHttpClient() throws Exception {
		tearDownHttpClientExample();
	}
	
}
