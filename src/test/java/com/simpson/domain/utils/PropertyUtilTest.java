package com.simpson.domain.utils;

import com.simpson.domain.property.Property;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;

public class PropertyUtilTest extends TestCase {
	@Test
	public void testLoadPropertiesFromJson() {
		try {
			Property property = PropertyUtil.loadPropertiesFromJson();
			assertEquals(2, property.getPolicy().getPolicies().size());
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}
}