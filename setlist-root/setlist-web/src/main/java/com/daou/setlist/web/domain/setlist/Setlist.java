package com.daou.setlist.web.domain.setlist;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.daou.setlist.web.domain.artist.Artist;
import com.daou.setlist.web.domain.artist.ArtistId;

/**
 * @author suzhy
 */
@Entity
@Table(name = "tb_setlist")
public class Setlist {

	@Id
	private Long setlistNo;

	@ManyToOne(targetEntity = Artist.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "artist_id")
	private ArtistId artistId;

	@Embedded
	private Tour tour;
	
	@Column
	private LocalDate eventDate;

	protected Setlist() {
	}
	
	public Setlist(ArtistId artistId, Tour tour, LocalDate eventDate) {
		this.artistId = artistId;
		this.tour = tour;
		this.eventDate = eventDate;
	}
}
