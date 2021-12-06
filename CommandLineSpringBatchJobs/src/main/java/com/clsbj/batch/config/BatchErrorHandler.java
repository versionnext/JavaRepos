package com.clsbj.batch.config;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class BatchErrorHandler implements ResponseErrorHandler {
	  @Override
	  public void handleError(ClientHttpResponse response) throws IOException {	    
	  }

	  @Override
	  public boolean hasError(ClientHttpResponse response) throws IOException {
		  return true;
	  }
	}