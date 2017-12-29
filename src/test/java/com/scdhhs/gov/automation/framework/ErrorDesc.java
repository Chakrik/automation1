package com.scdhhs.gov.automation.framework;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorDesc {
	
	@JsonProperty("fieldName")
	String fieldName;
	
	@JsonProperty("errorMessage")
	String errorMessage;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String toString() {
		return   fieldName + " : " + errorMessage ;
	}
	
}
