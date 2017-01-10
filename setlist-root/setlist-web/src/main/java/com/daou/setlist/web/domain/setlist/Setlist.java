package com.daou.setlist.web.domain.setlist;

import java.time.LocalDateTime;

import javax.persistence.Column;
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
	@Column(length = 10)
	private Long setlistNo;

	@ManyToOne(targetEntity = Artist.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "artist_no")
	private ArtistId artistNo;

	@Column(length = 100, nullable = false)
	private String city;

	@Column(length = 100, nullable = false)
	private String tourName;

	@Column(nullable = false)
	private LocalDateTime regDt;

}
