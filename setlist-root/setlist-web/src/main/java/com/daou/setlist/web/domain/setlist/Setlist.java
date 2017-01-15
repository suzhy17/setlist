package com.daou.setlist.web.domain.setlist;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author suzhy
 */
@Entity
@Table(name = "tb_setlist")
public class Setlist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long setlistNo;

//	@ManyToOne(targetEntity = Artist.class, fetch = FetchType.LAZY)
//	@JoinColumn(name = "artist_id")
	@Column
	private String artistId;

	@Embedded
	private Tour tour;
	
	@Column
	private LocalDate eventDate;

	protected Setlist() {
	}
	
	public Setlist(String artistId, Tour tour, LocalDate eventDate) {
		this.artistId = artistId;
		this.tour = tour;
		this.eventDate = eventDate;
	}
	
	public Setlist(Long setlistNo, String artistId, Tour tour, LocalDate eventDate) {
		this.setlistNo = setlistNo;
		this.artistId = artistId;
		this.tour = tour;
		this.eventDate = eventDate;
	}

	public Long getSetlistNo() {
		return setlistNo;
	}

	public String getArtistId() {
		return artistId;
	}

	public Tour getTour() {
		return tour;
	}

	public LocalDate getEventDate() {
		return eventDate;
	}
	
	
}
