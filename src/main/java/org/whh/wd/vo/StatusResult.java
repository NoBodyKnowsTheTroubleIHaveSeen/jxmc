package org.whh.wd.vo;

public class StatusResult {

	private String result;

	private Status status;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public class Status {
		private Integer status_code;
		private String status_reason;

		public Integer getStatus_code() {
			return status_code;
		}

		public void setStatus_code(Integer status_code) {
			this.status_code = status_code;
		}

		public String getStatus_reason() {
			return status_reason;
		}

		public void setStatus_reason(String status_reason) {
			this.status_reason = status_reason;
		}

	}

}
