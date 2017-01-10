package com.daou.setlist.web.domain.artist;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.daou.setlist.web.domain.setlist.Setlist;

/**
 * @author suzhy
 */
@Entity
@Table(name = "tb_artist")
public class Artist {

	@EmbeddedId
	private ArtistId artistId;

	@Column(length = 100, nullable = false)
	private String artistNm;

	@Column(length = 100, nullable = false)
	private String nationality;

	@Column(nullable = false)
	private LocalDateTime regDt;

	@OneToMany(mappedBy = "artistNo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Setlist> setlists;

	protected Artist() {
	}
	
	public Artist(ArtistId artistId, String artistNm, String nationality) {
		this.artistId = artistId;
		this.artistNm = artistNm;
		this.nationality = nationality;
		this.regDt = LocalDateTime.now();
	}

	public String getArtistId() {
		return artistId.getValue();
	}

	public String getArtistNm() {
		return artistNm;
	}

	public String getNationality() {
		return nationality;
	}

	public String getRegDt() {
		return regDt.format(DateTimeFormatter.ISO_DATE);
	}

	public List<Setlist> getSetlists() {
		return setlists;
	}
}
