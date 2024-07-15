package org.oapen.memoproject.clientweb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

public class MimeTypeServiceTests {
	
	@Test
	void testMimeType() throws FileNotFoundException {
		
		MimeTypeService mts = new MimeTypeService();
		assertEquals("application/json", mts.getMimeTypeFromFileName("test.json"));
		assertEquals("application/xml", mts.getMimeTypeFromFileName("test.xml"));
		assertEquals("application/xml", mts.getMimeTypeFromFileName("test.onix"));
		assertEquals("text/html", mts.getMimeTypeFromFileName("test.html"));

	}

}
