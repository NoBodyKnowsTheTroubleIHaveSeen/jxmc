package org.whh.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "joke_record")
public class JokeRecord extends EntityBase {

	public static final int JOKE_TYPE_QIUBAI = 1;// 糗事百科
	public static final int JOKE_TYPE_BAISI = 2;// 百思不得

	public String jokeId;// 笑话ID

	public Date jokeUploadDate;// 笑话上传日期

	public Integer jokeType;

	public String getJokeId() {
		return jokeId;
	}

	public void setJokeId(String jokeId) {
		this.jokeId = jokeId;
	}

	public Date getJokeUploadDate() {
		return jokeUploadDate;
	}

	public void setJokeUploadDate(Date jokeUploadDate) {
		this.jokeUploadDate = jokeUploadDate;
	}

	public Integer getJokeType() {
		return jokeType;
	}

	public void setJokeType(Integer jokeType) {
		this.jokeType = jokeType;
	}

}
