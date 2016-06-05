package org.whh.web;

public class ErrorMessage extends CommonMessage {
	public String title;
	private boolean isCustomException;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isCustomException() {
		return isCustomException;
	}

	public void setCustomException(boolean isCustomException) {
		this.isCustomException = isCustomException;
	}
}
