package com.daou.setlist.web.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.daou.setlist.web.domain.artist.Artist;
import com.daou.setlist.web.domain.artist.ArtistRepository;

@Controller
public class TestController {

	private static final Logger log = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private ArtistRepository artistRepository;

	@GetMapping(path = "/")
	public String home() {
		
		log.debug("HOME");

		return "home";
	}
	
	@GetMapping(path = "/test")
	@ResponseBody
	public List<Artist> test() {
		
		Artist artist1 = new Artist("muse", "Muse", "UK", LocalDateTime.now());
		artistRepository.save(artist1);
		Artist artist2 = new Artist("metallica", "Metallica", "US", LocalDateTime.now());
		artistRepository.save(artist2);
		
		List<Artist> artistList = artistRepository.findAll();
		log.debug("SELECT TEST = {}", artistList.size());

		return artistList;
	}
}