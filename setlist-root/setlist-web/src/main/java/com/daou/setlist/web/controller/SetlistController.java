package com.daou.setlist.web.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.daou.setlist.common.model.JsonResult;
import com.daou.setlist.web.domain.artist.Artist;
import com.daou.setlist.web.domain.artist.ArtistRepository;
import com.daou.setlist.web.domain.setlist.Setlist;
import com.daou.setlist.web.domain.setlist.SetlistRepository;
import com.daou.setlist.web.domain.setlist.Song;
import com.daou.setlist.web.domain.setlist.SongId;
import com.daou.setlist.web.domain.setlist.SongRepository;
import com.daou.setlist.web.domain.setlist.Tour;
import com.daou.setlist.web.service.SetlistService;

@Controller
public class SetlistController {

	private static final Logger log = LoggerFactory.getLogger(SetlistController.class);

	@Autowired
	private SetlistService setlistService;
	
	@Autowired
	private ArtistRepository artistRepository;
	
	@Autowired
	private SetlistRepository setlistRepository;
	
	@Autowired
	private SongRepository songRepository;

	@GetMapping(path = "/artists")
	public String inquiryArtists(Model model, @RequestParam(required = false) String idxNm) {
		
		List<String> idxNms = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
		model.addAttribute("idxNms", idxNms);
		
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

		return "artists/artist";
	}

	@GetMapping(path = "/artists/{artistId}/{setlistNo}")
	public String inquirySetlist(
			Model model,
			@PathVariable("artistId") String artistId,
			@PathVariable("setlistNo") Long setlistNo
			) {
		
		Artist artist = artistRepository.findOne(artistId);
		model.addAttribute("artist", artist);
		
		Setlist setlist = setlistRepository.findOne(setlistNo);
		model.addAttribute("setlist", setlist);
		
		List<Song> songs = songRepository.findBySongIdSetlistNo(setlistNo);
		log.debug("songs={}", songs);
		
		model.addAttribute("songs", songs);

		return "artists/setlist";
	}

	@GetMapping(path = "/search")
	public String searchArtist(Model model, @RequestParam String keyword) {
		
		List<Artist> artists = artistRepository.findByArtistNameStartingWithIgnoreCase(keyword);
		model.addAttribute("artists", artists);

		return "artists/search";
	}

	@GetMapping(path = "/setlist", params = "write")
	public String setlistWriteForm() {
		
		return "artists/setlist-write";
	}

	@PostMapping(path = "/setlist")
	@ResponseBody
	public JsonResult writeSetlist(
			@RequestParam String artistId,
			@RequestParam String tourName,
			@RequestParam String venue,
			@RequestParam String eventDate,
			@RequestParam(name = "subject") String[] subject,
			@RequestParam(name = "remark") String[] remark) {
		
		Setlist setlist = new Setlist(artistId, new Tour(tourName, venue) , LocalDate.parse(eventDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));

		setlistService.registerSetlist(setlist, subject, remark);
		
		return new JsonResult();
	}
}