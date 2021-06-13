package com.simpson.domain.url.mapper.example;

import com.simpson.domain.controller.SimpsonServlet;
import com.simpson.domain.http.request.HttpRequest;
import com.simpson.domain.url.urlloader.AbstractUrlLoader;

public class LoaderA extends AbstractUrlLoader {
	
	@Override
	public SimpsonServlet getServlet(HttpRequest request) {
		return new ServiceA("/a");
	}
}
