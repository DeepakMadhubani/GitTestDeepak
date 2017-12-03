package com.macys.stella.attribute.webservice.rest.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;

public class ResponseCodeAsserter {
	
	private HttpResponse httpResponse;
	
	public ResponseCodeAsserter(HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
	}
	
	private int getResponseCode() {
		return httpResponse.getStatusLine().getStatusCode();
	}
	
	public void isOk() {
		 assertThat(getResponseCode(), equalTo(HttpServletResponse.SC_OK));
	}
	
	public void isCreated() {
		assertThat(getResponseCode(), equalTo(HttpServletResponse.SC_CREATED));
	}
	
	public void isConflict() {
		assertThat(getResponseCode(), equalTo(HttpServletResponse.SC_CONFLICT));
	}
	
	public void isGone() {
		assertThat(getResponseCode(), equalTo(HttpServletResponse.SC_GONE));
	}
	
	public void isSystemError() {
		assertThat(getResponseCode(), equalTo(HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
	}
	
	public void isBadRequest() {
		assertThat(getResponseCode(), equalTo(HttpServletResponse.SC_BAD_REQUEST));
	}
	
	public void isUnauthorized() {
		assertThat(getResponseCode(), equalTo(HttpServletResponse.SC_UNAUTHORIZED));
	}
	
	public void isNotFound() {
		assertThat(getResponseCode(), equalTo(HttpServletResponse.SC_NOT_FOUND));
	}

}

