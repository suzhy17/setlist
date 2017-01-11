package com.daou.setlist.web.domain.setlist;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author suzhy
 */
@Embeddable
@Access(AccessType.FIELD)
public class SongId implements Serializable {

	private static final long serialVersionUID = 2919178127581144422L;

	@Column
	private Long setlistNo;

	@Column
	private Integer trackNo;
	
	protected SongId() {
	}

	public SongId(Long setlistNo, Integer trackNo) {
		if (setlistNo == null || trackNo == null) {
			throw new IllegalArgumentException("파라미터 값 에러");
		}
		this.setlistNo = setlistNo;
		this.trackNo = trackNo;
	}

	public Long getSetlistNo() {
		return setlistNo;
	}

	public Integer getTrackNo() {
		return trackNo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		SongId that = (SongId) o;

		boolean eq = (setlistNo != null ? setlistNo.equals(that.setlistNo) : that.setlistNo == null)
				&& (trackNo != null ? trackNo.equals(that.trackNo) : that.trackNo == null);
		return eq;

	}
}
