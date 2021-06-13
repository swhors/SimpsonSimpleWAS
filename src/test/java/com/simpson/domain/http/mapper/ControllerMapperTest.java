package com.simpson.domain.http.mapper;

import com.simpson.SimpsonWeb;
import com.simpson.domain.controller.SimpsonServlet;
import com.simpson.domain.property.Url;
import com.simpson.domain.property.Property;
import com.simpson.domain.property.Controller;
import com.simpson.domain.property.Policy;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ControllerMapperTest extends TestCase {
	private static MockedStatic<SimpsonWeb> simpsonWeb;
	
	@Mock
	Property property;
	
	@Mock
	Policy policy;
	
	void initClass(String path, boolean initUrl) {
		simpsonWeb = mockStatic(SimpsonWeb.class);
		simpsonWeb.when(() -> SimpsonWeb.getProperty()).thenReturn(property);
		when(property.getPolicy()).thenReturn(policy);
		
		if (initUrl) {
			List<Controller> controllers = new ArrayList<>();
			controllers.add(new Controller(path, "com.simpson.domain.url.mapper.example.ServiceA"));
			Url url = new Url("a.com",
				"/a",
				"com.simpson.domain.url.mapper.example.LoaderA",
				controllers);
			when(policy.getPolicy(anyString())).thenReturn(Optional.of(url));
		}
	}
	
	void fintClass() {
		simpsonWeb.close();
	}
	
	public void setUp() throws Exception {
		super.setUp();
		MockitoAnnotations.openMocks(this);
	}
	
	public void tearDown() throws Exception {
	}
	
	@Test
	public void test_loadContollers_matched_path() {
		initClass("/hello", true);
		Map<String, SimpsonServlet> contollers = ControllerMapper.loadContollers("a.com");
		System.out.println("test_loadContollers_matched_path");
		System.out.println(contollers);
		fintClass();
		assertEquals(1, contollers.size());
	}
}