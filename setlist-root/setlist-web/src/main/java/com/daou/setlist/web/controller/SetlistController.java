package com.daou.setlist.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.daou.setlist.web.domain.artist.Artist;
import com.daou.setlist.web.domain.artist.ArtistRepository;

@Controller
public class SetlistController {

	private static final Logger log = LoggerFactory.getLogger(SetlistController.class);
	
	@Autowired
	private ArtistRepository artistRepository;

	@GetMapping(path = "/artists/{artistId}")
	public String inquiryArtist(Model model) {
		
		log.debug("HOME");
		List<Artist> artistList = artistRepository.findAll();
		
		model.addAttribute("artistList", artistList);

		return "home";
	}
}