package com.macys.stella.attribute.webservice.rest.util;

import java.util.List;

import com.macys.stella.attribute.webservice.rest.request.bean.ProductTypeCreateRequest;

public class PayloadCreationUtil {
	private static JsonUtil jsonUtil = new JsonUtil();


	public static String createJsonPayloadForCreate(String productTypeName,String isActive,List<String> deptLists){


		//Creating json payload
		ProductTypeCreateRequest payloadBean = new ProductTypeCreateRequest();

		if(!productTypeName.isEmpty())
			payloadBean.setProductTypeName(productTypeName);		
		if(!isActive.isEmpty())
			payloadBean.setIsActive(isActive);
		if(!deptLists.isEmpty())
			payloadBean.setDeptIdList(deptLists);

		return jsonUtil.getJsonPayloadFromObject(payloadBean);
	}
}
