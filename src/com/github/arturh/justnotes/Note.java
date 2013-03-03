package com.github.arturh.justnotes;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "note")
public class Note extends Model {
	@Column(name="content")
	private String content;
	
	public Note() {
		super();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
