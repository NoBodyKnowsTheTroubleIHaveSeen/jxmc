package org.whh.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "joke")
public class Joke extends EntityBase {
	public String content;
	public Boolean isUsed;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

}
