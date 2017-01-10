package com.daou.setlist.web.domain.artist;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * @author suzhy
 */
@Embeddable
@Access(AccessType.FIELD)
public class ArtistNo implements Serializable {

	private static final long serialVersionUID = 2919178127581144422L;

	@Column(name = "artist_no", length = 10)
	private Long value;

	protected ArtistNo() {
	}

	public ArtistNo(Long value) {
		this.value = value;
	}

	public Long getArtistNo() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ArtistNo that = (ArtistNo) o;

		return value != null ? value.equals(that.value) : that.value == null;

	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}
