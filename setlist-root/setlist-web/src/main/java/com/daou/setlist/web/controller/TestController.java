package com.daou.setlist.web.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.daou.setlist.web.domain.artist.Artist;
import com.daou.setlist.web.domain.artist.ArtistRepository;
import com.daou.setlist.web.domain.setlist.Setlist;
import com.daou.setlist.web.domain.setlist.SetlistRepository;
import com.daou.setlist.web.domain.setlist.Song;
import com.daou.setlist.web.domain.setlist.SongId;
import com.daou.setlist.web.domain.setlist.SongRepository;
import com.daou.setlist.web.domain.setlist.Tour;

@Controller
public class TestController {

	private static final Logger log = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private ArtistRepository artistRepository;
	
	@Autowired
	private SetlistRepository setlistRepository;
	
	@Autowired
	private SongRepository songRepository;
	
	@PostConstruct
	public void test() {
		Artist artist1 = new Artist("acdc", "AC/DC", "US", LocalDateTime.now());
		artistRepository.save(artist1);
		Artist artist2 = new Artist("dreamtheater", "Dream Theater", "US", LocalDateTime.now());
		artistRepository.save(artist2);
		Artist artist3 = new Artist("muse", "Muse", "UK", LocalDateTime.now());
		artistRepository.save(artist3);
		Artist artist4 = new Artist("metallica", "Metallica", "US", LocalDateTime.now());
		artistRepository.save(artist4);
		
		log.info("아티스트 저장 완료");
		
		Setlist setlist1 = new Setlist(artist3.getArtistId(), new Tour("Absolution Tour", "Seoul, Korea"), LocalDate.now());
		setlistRepository.save(setlist1);
		setlistRepository.save(new Setlist(artist3.getArtistId(), new Tour("Absolution Tour", "Pusan, Korea"), LocalDate.now()));
		
		log.info("세트리스트 저장 완료");

		songRepository.save(new Song(new SongId(setlist1.getSetlistNo(), 1), "Psycho", null));
		songRepository.save(new Song(new SongId(setlist1.getSetlistNo(), 2), "Plug In Baby", null));
		songRepository.save(new Song(new SongId(setlist1.getSetlistNo(), 3), "Interlude", null));
		songRepository.save(new Song(new SongId(setlist1.getSetlistNo(), 4), "Hysteria", "(AC/DC's 'Back In Black' riff outro)"));
		songRepository.save(new Song(new SongId(setlist1.getSetlistNo(), 5), "Assassin", null));
		songRepository.save(new Song(new SongId(setlist1.getSetlistNo(), 6), "Citizen Erased", null));
		
		List<Artist> artistList = artistRepository.findAll();
		log.debug("SELECT TEST = {}", artistList.size());
	}
}