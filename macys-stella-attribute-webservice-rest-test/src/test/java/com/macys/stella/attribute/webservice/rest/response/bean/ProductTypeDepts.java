package com.macys.stella.attribute.webservice.rest.response.bean;

public class ProductTypeDepts
{
    private String lastUpdatedDateTime;

    private String id;

    private String createdBy;

    private String lastUpdatedBy;

    private String createdDateTime;

    private String departmentId;

    public String getLastUpdatedDateTime ()
    {
        return lastUpdatedDateTime;
    }

    public void setLastUpdatedDateTime (String lastUpdatedDateTime)
    {
        this.lastUpdatedDateTime = lastUpdatedDateTime;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getCreatedBy ()
    {
        return createdBy;
    }

    public void setCreatedBy (String createdBy)
    {
        this.createdBy = createdBy;
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

    public String getDepartmentId ()
    {
        return departmentId;
    }

    public void setDepartmentId (String departmentId)
    {
        this.departmentId = departmentId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lastUpdatedDateTime = "+lastUpdatedDateTime+", id = "+id+", createdBy = "+createdBy+", lastUpdatedBy = "+lastUpdatedBy+", createdDateTime = "+createdDateTime+", departmentId = "+departmentId+"]";
    }
}