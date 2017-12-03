package com.macys.stella.attribute.webservice.rest.response.bean;

public class CreateProductTypeJsonResponce
{
    private String lastUpdatedDateTime;

    private String _warning;

    private String _success;

    private String[] _messages;

    private String id;

    private String isActive;

    private String createdBy;

    private String brandCode;

    private String productTypeName;

    private String[] deptIdList;

    private String lastUpdatedBy;

    private String createdDateTime;

    private ProductTypeDepts[] productTypeDepts;

    private String _info;

    public String getLastUpdatedDateTime ()
    {
        return lastUpdatedDateTime;
    }

    public void setLastUpdatedDateTime (String lastUpdatedDateTime)
    {
        this.lastUpdatedDateTime = lastUpdatedDateTime;
    }

    public String get_warning ()
    {
        return _warning;
    }

    public void set_warning (String _warning)
    {
        this._warning = _warning;
    }

    public String get_success ()
    {
        return _success;
    }

    public void set_success (String _success)
    {
        this._success = _success;
    }

    public String[] get_messages ()
    {
        return _messages;
    }

    public void set_messages (String[] _messages)
    {
        this._messages = _messages;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getIsActive ()
    {
        return isActive;
    }

    public void setIsActive (String isActive)
    {
        this.isActive = isActive;
    }

    public String getCreatedBy ()
    {
        return createdBy;
    }

    public void setCreatedBy (String createdBy)
    {
        this.createdBy = createdBy;
    }

    public String getBrandCode ()
    {
        return brandCode;
    }

    public void setBrandCode (String brandCode)
    {
        this.brandCode = brandCode;
    }

    public String getProductTypeName ()
    {
        return productTypeName;
    }

    public void setProductTypeName (String productTypeName)
    {
        this.productTypeName = productTypeName;
    }

    public String[] getDeptIdList ()
    {
        return deptIdList;
    }

    public void setDeptIdList (String[] deptIdList)
    {
        this.deptIdList = deptIdList;
    }

    public String getLastUpdatedBy ()
    {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy (String lastUpdatedBy)
    {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getCreatedDateTime ()
    {
        return createdDateTime;
    }

    public void setCreatedDateTime (String createdDateTime)
    {
        this.createdDateTime = createdDateTime;
    }

    public ProductTypeDepts[] getProductTypeDepts ()
    {
        return productTypeDepts;
    }

    public void setProductTypeDepts (ProductTypeDepts[] productTypeDepts)
    {
        this.productTypeDepts = productTypeDepts;
    }

    public String get_info ()
    {
        return _info;
    }

    public void set_info (String _info)
    {
        this._info = _info;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lastUpdatedDateTime = "+lastUpdatedDateTime+", _warning = "+_warning+", _success = "+_success+", _messages = "+_messages+", id = "+id+", isActive = "+isActive+", createdBy = "+createdBy+", brandCode = "+brandCode+", productTypeName = "+productTypeName+", deptIdList = "+deptIdList+", lastUpdatedBy = "+lastUpdatedBy+", createdDateTime = "+createdDateTime+", productTypeDepts = "+productTypeDepts+", _info = "+_info+"]";
    }
}
