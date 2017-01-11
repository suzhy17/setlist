package com.daou.setlist.web.domain.setlist;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author suzhy
 */
@Entity
@Table(name = "tb_song")
public class Song {

	@EmbeddedId
	private SongId songId;

	@Column(length = 200)
	private String subject;

	@Column(length = 200)
	private String remark;
	
	protected Song() {
	}
	
	public Song(SongId songId, String subject, String remark) {
		this.songId = songId;
		this.subject = subject;
		this.remark = remark;
	}

	public SongId getSongId() {
		return songId;
	}

	public String getSubject() {
		return subject;
	}

	public String getRemark() {
		return remark;
	}

}
