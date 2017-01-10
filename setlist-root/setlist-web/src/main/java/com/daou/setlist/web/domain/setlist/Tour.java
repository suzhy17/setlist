package com.daou.setlist.web.domain.setlist;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author suzhy
 */
@Embeddable
public class Tour {

	@Column(length = 100, nullable = false)
	private String tourName;

	@Column(length = 100, nullable = false)
	private String venue;

	protected Tour() {
	}

	public Tour(String tourName, String venue) {
		this.tourName = tourName;
		this.venue = venue;
	}
}
