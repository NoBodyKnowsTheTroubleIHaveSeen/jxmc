package org.whh.flush;

public class FlushStatus {
	// 是否启动
	private Boolean isStart = false;

	private String isStartStr = "否";
	// 浏览过的次数
	private Integer browsedCount = 0;
	// 总浏览的数量
	private Integer totalCount = 0;
	// 剩余数量
	private Integer leftedCount = 0;
	
	private Integer sleepTime = 0;

	public Boolean getIsStart() {
		return isStart;
	}

	public void setIsStart(Boolean isStart) {
		this.isStart = isStart;
	}

	public Integer getBrowsedCount() {
		return browsedCount;
	}

	public void setBrowsedCount(Integer browsedCount) {
		this.browsedCount = browsedCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getLeftedCount() {
		return leftedCount;
	}

	public void setLeftedCount(Integer leftedCount) {
		this.leftedCount = leftedCount;
	}

	public String getIsStartStr() {
		return isStartStr;
	}

	public void setIsStartStr(String isStartStr) {
		this.isStartStr = isStartStr;
	}

	public Integer getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(Integer sleepTime) {
		this.sleepTime = sleepTime;
	}

}
