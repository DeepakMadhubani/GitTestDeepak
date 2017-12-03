package com.macys.stella.attribute.dbreader.impl;

import java.sql.SQLException;

public class StellaCommonDbQueries {
	public static final String GET_VISIBLE_FLAG_STATUS = "select Visible_flag from product_type where prt_product_type_id= '%s'";

	public static String getVisibleFlagStatus(
			DataAccessClient stellaDataAccessClient, String productTypeId) throws SQLException {
		
		return stellaDataAccessClient.executeQueryReturnRandomResult(String.format(GET_VISIBLE_FLAG_STATUS,productTypeId));
	}

}
