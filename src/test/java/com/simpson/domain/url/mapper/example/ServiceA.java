package com.simpson.domain.url.mapper.example;

import com.simpson.domain.controller.AbstractSimpsonServlet;
import com.simpson.domain.http.request.HttpRequest;
import com.simpson.domain.http.response.HttpResponse;

public class ServiceA extends AbstractSimpsonServlet {
	public ServiceA(String rootDir) {
		super(rootDir);
	}
	
	@Override
	public void doGet(HttpRequest request, HttpResponse response) {
	
	}
	
	@Override
	public void doPost(HttpRequest request, HttpResponse response) {
	
	}
}
