package com.simpson.domain.url.mapper;

import com.simpson.SimpsonWeb;
import com.simpson.domain.property.Policy;
import com.simpson.domain.property.Url;
import com.simpson.domain.property.Property;
import com.simpson.domain.property.Controller;
import com.simpson.domain.url.urlloader.UrlLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import org.junit.Test;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UrlMapperTest {
	private static MockedStatic<SimpsonWeb> simpsonWeb;

	@Mock
	Property property;
	
	@Mock
	Policy policy;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	void initClass(String path) {
		simpsonWeb = mockStatic(SimpsonWeb.class);
		simpsonWeb.when(() -> SimpsonWeb.getProperty()).thenReturn(property);
		when(property.getPolicy()).thenReturn(policy);
		
		List<Url> policies = new ArrayList<>();
		List<Controller> controllers = new ArrayList<>();
		controllers.add(new Controller(path, "com.simpson.domain.url.mapper.example.ServiceA"));
		policies.add(new Url("a.com",
			"/a",
			"com.simpson.domain.url.mapper.example.LoaderA",
			controllers));
		when(policy.getPolicies()).thenReturn(policies);
	}
	
	void fintClass() {
		simpsonWeb.close();
	}
	
	@Test
	public void test_mappingPolicy_with_restricted_url() {
		initClass("/hello");

		when(policy.includeBlackList("/hello.exe")).thenReturn(true);
		UrlLoader policy = UrlMapper.mappingPolicy("a.com", "/hello.exe");
		fintClass();
		
		System.out.println(policy);
		
		assertEquals("com.simpson.domain.url.urlloader.ForbiddenLoader",
			policy.getClass().getName());
	}
	
	@Test
	public void test_mappingPolicy_with_right_url() {
		initClass("/hello");
		
		when(policy.includeBlackList(anyString())).thenReturn(false);
		UrlLoader policy = UrlMapper.mappingPolicy("a.com", "/hello.exe");
		fintClass();
		
		System.out.println(policy);
		
		assertEquals("com.simpson.domain.url.mapper.example.LoaderA",
			policy.getClass().getName());
	}
	
	@Test
	public void test_mappingPolicy_with_not_existed_url() {
		initClass("/hello");
		
		when(policy.includeBlackList(anyString())).thenReturn(false);
		UrlLoader policy = UrlMapper.mappingPolicy("b.com", "/hello.exe");
		fintClass();
		
		System.out.println(policy);
		
		assertEquals("com.simpson.domain.url.urlloader.UnsupportedLoader",
			policy.getClass().getName());
	}

	@Test
	public void testCheckPathSecurityWithFalse() {
		String reqPath = "/hello.exe";
		
		initClass("/hello");
		when(policy.includeBlackList(anyString())).thenReturn(true);
		boolean  ret = UrlMapper.checkPathSecurity(reqPath);
		fintClass();
		assertEquals(ret, false);
	}
}