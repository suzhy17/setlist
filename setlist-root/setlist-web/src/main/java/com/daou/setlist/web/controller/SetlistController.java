package com.daou.setlist.web.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.daou.setlist.web.domain.artist.Artist;
import com.daou.setlist.web.domain.artist.ArtistRepository;
import com.daou.setlist.web.domain.setlist.Setlist;

@Controller
public class SetlistController {

	private static final Logger log = LoggerFactory.getLogger(SetlistController.class);
	
	@Autowired
	private ArtistRepository artistRepository;

	@GetMapping(path = "/artists")
	public String inquiryArtists(Model model, @RequestParam(required = false) String idxNm) {
		
		
		List<Artist> artists = null;
		if (StringUtils.isNotBlank(idxNm)) {
			artists = artistRepository.findByArtistNameStartingWithIgnoreCase(idxNm);
		} else {
			artists = artistRepository.findAll();
		}
		
		log.debug("artists={}", artists);
		
		model.addAttribute("artists", artists);

		return "artists/artists";
	}
	
	@GetMapping(value = "/artists", params = "write")
	public String artistWriteForm(Model model) {
		
		List<Artist> artists = artistRepository.findAll();
		log.debug("artists={}", artists);
		
		model.addAttribute("artists", artists);

		return "artists/artist-write";
	}

	@GetMapping(path = "/artists/{artistId}")
	public String inquiryArtist(Model model, @PathVariable("artistId") String artistId) {
		
		Artist artist = artistRepository.findOne(artistId);
		log.debug("artist={}", artist);
		
		for (Setlist setlist : artist.getSetlists()) {
			log.debug("setlist.getSetlistNo()={}", setlist.getSetlistNo());
			log.debug("setlist.getTour().getTourName()={}", setlist.getTour().getTourName());
			log.debug("setlist={}", setlist.getTour().getVenue());
			log.debug("setlist={}", setlist.getEventDate());
		}
		
		model.addAttribute("artist", artist);
		//model.addAttribute("setlists", artist.getSetlists());

		return "artists/artist";
	}
}