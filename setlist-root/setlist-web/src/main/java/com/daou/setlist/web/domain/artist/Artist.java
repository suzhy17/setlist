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
	private String artistName;

	@Column(length = 100, nullable = false)
	private String nationality;

	@Column(nullable = false)
	private LocalDateTime regDt;

	@OneToMany(mappedBy = "artistNo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Setlist> setlists;

	protected Artist() {
	}
	
	public Artist(String artistId, String artistName, String nationality, LocalDateTime regDt) {
		this.artistId = new ArtistId(artistId);
		this.artistName = artistName;
		this.nationality = nationality;
		this.regDt = regDt;
	}

	public String getArtistId() {
		return artistId.getValue();
	}

	public String getArtistName() {
		return artistName;
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

	@Override
	public String toString() {
		StringBuffer string = new StringBuffer();
		string
			.append("artistId=").append(artistId)
			.append(", artistName=").append(artistName)
			.append(", nationality=").append(nationality)
			.append(", regDt=").append(regDt);
		return string.toString();
	}
}
