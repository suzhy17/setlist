package com.daou.setlist.web.domain.artist;

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
public class ArtistId implements Serializable {

	private static final long serialVersionUID = 2919178127581144422L;

	@Column(name = "artist_id", length = 10)
	private String value;

	protected ArtistId() {
	}

	public ArtistId(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ArtistId that = (ArtistId) o;

		return value != null ? value.equals(that.value) : that.value == null;

	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}

	@Override
	public String toString() {
		return value;
	}
}
