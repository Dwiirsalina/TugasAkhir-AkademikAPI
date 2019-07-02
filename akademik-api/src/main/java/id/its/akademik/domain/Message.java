package id.its.akademik.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class Message {
	@JsonProperty("message")
	private String message;

	public Message(String msg) {
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
