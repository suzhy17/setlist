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
public class SetlistNo implements Serializable {

	private static final long serialVersionUID = 814580115275086577L;
	
	@Column(name = "setlist_no")
	private Long value;

	protected SetlistNo() {
	}

	public SetlistNo(Long value) {
		this.value = value;
	}

	public Long getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		SetlistNo that = (SetlistNo) o;

		return value != null ? value.equals(that.value) : that.value == null;

	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
