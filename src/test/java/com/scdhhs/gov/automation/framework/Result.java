package com.scdhhs.gov.automation.framework;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {

	@JsonProperty("result")
	List<ErrorDesc> errorMessages; 
	
}
