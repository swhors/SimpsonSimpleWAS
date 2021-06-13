package com.simpson.domain.utils;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileIoUtilsTest extends TestCase {
	
	@Test
	public void test_loadHtmlFile_right_case() {
		try {
			byte[] texts = FileIoUtils.loadHtmlFile("/error/400.html");
			
			System.out.println("test_loadHtmlFile_right_case");
			System.out.println("length = " + texts.length);
			System.out.println("data   = " + texts);
			assertEquals(156, texts.length);
			
		} catch (IOException | URISyntaxException | ClassNotFoundException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void test_loadHtmlFile_illegal_case() {
		try {
			byte[] texts = FileIoUtils.loadHtmlFile("/error/300.html");
			fail();
		} catch (IOException | URISyntaxException | ClassNotFoundException e) {
			System.out.println("test_loadHtmlFile_right_case");
			System.out.println("Exception = " + e.getMessage());
		}
	}
}