package id.its.akademik.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class ErrorMessage {
	@JsonProperty("error_message")
	private String errorMessage;
	@JsonProperty("status")
	private String status;

	public ErrorMessage(String msg) {
		this.errorMessage = msg;
	}

	public ErrorMessage(String msg, String status) {
		this.errorMessage = msg;
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
