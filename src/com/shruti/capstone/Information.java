package com.shruti.capstone;
import java.io.Serializable;

public class Information implements Serializable {

	private static final long serialVersionUID = 5196404678300433611L;

	private Infotype info;
	private String message;
	private byte[] content;

	public Infotype getInfo() {
		return info;
	}

	public void setInfo(Infotype info) {
		this.info = info;
	}
	public String getReply() {
		return message;
	}

	public void setReply(String message) {
		this.message = message;
	}
	public byte[] getContent() {
		return content;
	}

	
	public void setContent(byte[] content) {
		this.content = content;
	}

	

}
