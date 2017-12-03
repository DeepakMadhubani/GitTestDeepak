package com.macys.stellaspecific.util;

public enum Tenant {
	MCOM,
	BCOM;

	@Override
	public String toString() {
		if(this == MCOM) {
			return "MCOM";
		}
		else {
			return "BCOM";
		}
    }
}
