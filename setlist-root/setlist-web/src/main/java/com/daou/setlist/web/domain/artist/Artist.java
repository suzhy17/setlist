package com.daou.setlist.web.domain.artist;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.daou.setlist.web.domain.setlist.Setlist;

/**
 * @author suzhy
 */
@Entity
@Table(name = "tb_artist")
public class Artist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long artistNo;

	@Column(length = 100, nullable = false)
	private String artistNm;

	@Column(length = 100, nullable = false)
	private String nationality;

	@Column(nullable = false)
	private LocalDateTime regDt;

	@OneToMany(mappedBy = "artistNo", cascade = CascadeType.ALL)
	private List<Setlist> setlists;

	protected Artist() {
	}
	
	public Artist(String artistNm, String nationality, LocalDateTime regDt) {
		this.artistNm = artistNm;
		this.nationality = nationality;
		this.regDt = regDt;
	}

	public Long getArtistNo() {
		return artistNo;
	}

	public String getArtistNm() {
		return artistNm;
	}

	public String getNationality() {
		return nationality;
	}

	public LocalDateTime getRegDt() {
		return regDt;
	}

	public List<Setlist> getSetlists() {
		return setlists;
	}
}
