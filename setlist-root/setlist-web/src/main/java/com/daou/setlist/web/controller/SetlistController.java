package com.daou.setlist.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.daou.setlist.web.domain.artist.Artist;
import com.daou.setlist.web.domain.artist.ArtistId;
import com.daou.setlist.web.domain.artist.ArtistRepository;

@Controller
public class SetlistController {

	private static final Logger log = LoggerFactory.getLogger(SetlistController.class);
	
	@Autowired
	private ArtistRepository artistRepository;

	@GetMapping(path = "/artists")
	public String inquiryArtists(Model model) {
		
		List<Artist> artists = artistRepository.findAll();
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
		
		Artist artist = artistRepository.findOne(new ArtistId(artistId));
		log.debug("artist={}", artist);
		
		model.addAttribute("artist", artist);

		return "artists/artist";
	}
}