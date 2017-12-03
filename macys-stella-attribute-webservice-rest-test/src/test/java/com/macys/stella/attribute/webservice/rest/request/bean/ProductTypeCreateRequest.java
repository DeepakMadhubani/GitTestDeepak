package com.macys.stella.attribute.webservice.rest.request.bean;

import java.util.ArrayList;

import java.util.List;


public class ProductTypeCreateRequest {
	
	// Payload input: {"productTypeName":"2BCOMPT-yc03dk1","isActive":"true","deptIdList":[1,2,10]}
	private String isActive;
	private String productTypeName;
	private List<String> deptIdList=new ArrayList<String>();

/*  We can set the values of inputs by passing the values in parametrised constructor as well....
	public ProductTypeCreateRequest(String isActive,String prodTypeName,List<String> deptIdList)
	{
		
		this.isActive=isActive;
		this.productTypeName=prodTypeName;
		this.deptIdList=deptIdList;
	}*/


    public String getIsActive ()
    {
        return isActive;
    }

    public void setIsActive (String isActive)
    {
        this.isActive = isActive;
    }

    public List<String> getDeptIdList ()
    {
        return deptIdList;
    }

    public void setDeptIdList (List<String> deptIdList)
    {
        this.deptIdList = deptIdList;
    }

    public String getProductTypeName ()
    {
        return productTypeName;
    }

    public void setProductTypeName (String productTypeName)
    {
        this.productTypeName = productTypeName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [isActive = "+isActive+", deptIdList = "+deptIdList+", productTypeName = "+productTypeName+"]";
    }
}

