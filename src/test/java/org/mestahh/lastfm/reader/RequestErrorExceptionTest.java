package org.mestahh.lastfm.reader;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RequestErrorExceptionTest {

	@Test
	public void it_stores_the_error_code_and_the_message() {
		RequestErrorException exception = new RequestErrorException("10", "Api error");
		assertEquals("10", exception.getErrorCode());
	}

}
