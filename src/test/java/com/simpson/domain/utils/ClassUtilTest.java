package com.simpson.domain.utils;

import junit.framework.TestCase;
import org.junit.Test;


public class ClassUtilTest extends TestCase {
	
	@Test
	public void test_createPolicy_without_arg() {
		Object policy = ClassUtil.createInstance(
			"com.simpson.domain.url.mapper.example.LoaderA",
			null);
		System.out.println("test_CreatePolicy");
		System.out.println(policy);
		assertEquals(
			"com.simpson.domain.url.mapper.example.LoaderA",
			policy.getClass().getName());
	}
	
	@Test
	public void test_createPolicy_with_arg() {
		Object policy = ClassUtil.createInstance(
			"com.simpson.domain.url.mapper.example.LoaderA",
			"/a");
		System.out.println("test_CreatePolicy");
		System.out.println(policy);
		assertEquals( null, policy);
	}
	
	@Test
	public void test_createService_without_arg() {
		Object service = ClassUtil.createInstance(
			"com.simpson.domain.url.mapper.example.ServiceA",
			null);
		System.out.println("test_CreatePolicy");
		System.out.println(service);
		assertEquals( null, service);
	}
	
	@Test
	public void test_createService_with_arg() {
		Object service = ClassUtil.createInstance(
			"com.simpson.domain.url.mapper.example.ServiceA",
			"/a");
		System.out.println("test_CreatePolicy");
		System.out.println(service);
		assertEquals(
			"com.simpson.domain.url.mapper.example.ServiceA",
			service.getClass().getName());
	}
}