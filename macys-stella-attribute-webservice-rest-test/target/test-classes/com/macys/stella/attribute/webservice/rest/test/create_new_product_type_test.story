Description: Visble flag in Stella Product Type table should be defaulted to Y. 

Version one Story B-44427 
link - API changes: New Product Type create API to check Visible status flag.

@feature  API changes: New Product Type create API to check Visible status flag.



Scenario:(1) Verify the Create Product type Create API for given Product type name with Department lists

Given the product type create API resource available at: product-type-service/v1/brands/

When I create a product type for tenant <tenant> with a product type name as <productTypeName> is Active flag as <isActiveFlag> with department list as <departmentLists>
Then I expect the create request to return with responseCode as <responseCode>
And I expect a valid Product type id
When I connect to STELLA DB with tenant <tenant> for validating status of Visible flag in STELLA DB
Then I should see the Visible flag status as <visibleFlagStatus>

Examples:                            

|S.No|tenant|productTypeName         |departmentLists|isActiveFlag|responseCode|visibleFlagStatus|
|1   |BCOM  |006pTBCOM4-yc03dk1         |1,2,10         |true        |201         |Y                | 
|2   |MCOM  |006pTMCOM4-yc03dk1         |1,2,10         |true        |201         |Y                | 
