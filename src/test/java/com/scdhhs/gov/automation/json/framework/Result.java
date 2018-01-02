package com.scdhhs.gov.automation.json.framework;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO representing the Error Description.
 * 
 * @author duser
 *
 */

public class Result {

	@JsonProperty("result")
	List<ErrorDesc> errorMessages; 
	
}
