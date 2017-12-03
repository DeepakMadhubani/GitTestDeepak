package com.macys.stella.attribute.webservice.rest.util;

import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.macys.stella.attribute.webservice.rest.util.ResponseCodeAsserter;

public class ResponseCodeValidator {
	private static final Logger LOG = LoggerFactory
			.getLogger(ResponseCodeValidator.class);

	public int responseCode;

	public ResponseCodeValidator(int responseCode) {
		super();
		this.responseCode = responseCode;
	}

	protected ResponseCodeAsserter assertThatTheResponseCode(
			HttpResponse httpResponse) {
		return new ResponseCodeAsserter(httpResponse);
	}

	public void checkResponseCode(HttpResponse httpResponse) {
		switch (responseCode) {
		case HttpServletResponse.SC_OK:
			assertThatTheResponseCode(httpResponse).isOk();
			break;
		case HttpServletResponse.SC_CREATED:
			assertThatTheResponseCode(httpResponse).isCreated();
			break;
		case HttpServletResponse.SC_BAD_REQUEST:
			assertThatTheResponseCode(httpResponse).isBadRequest();
			break;
		case HttpServletResponse.SC_NOT_FOUND:
			assertThatTheResponseCode(httpResponse).isNotFound();
			break;
		case HttpServletResponse.SC_METHOD_NOT_ALLOWED:
			Assert.assertEquals(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
					httpResponse.getStatusLine().getStatusCode());
			break;
		case HttpServletResponse.SC_CONFLICT:
			assertThatTheResponseCode(httpResponse).isConflict();
			break;
		case HttpServletResponse.SC_GONE:
			assertThatTheResponseCode(httpResponse).isGone();
			break;
		case HttpServletResponse.SC_UNAUTHORIZED:
			assertThatTheResponseCode(httpResponse).isUnauthorized();;
			break;
		case HttpServletResponse.SC_INTERNAL_SERVER_ERROR:
			assertThatTheResponseCode(httpResponse).isSystemError();
			break;
		default:
			LOG.info("!!!!!!!!!...ERROR...ERROR...ERROR... response code NOT FOUND!!!!!!!!! "
					+ httpResponse.getStatusLine().getStatusCode());
		}

	}
}
