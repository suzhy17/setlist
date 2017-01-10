package com.daou.setlist.web.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.daou.setlist.web.domain.artist.Artist;
import com.daou.setlist.web.domain.artist.ArtistRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ArtistTest {

	private static final Logger log = LoggerFactory.getLogger(ArtistTest.class);
	
	@Autowired
	private ArtistRepository artistRepository;

	@Test
	public void test() {
		Artist artist = new Artist("muse", "Muse", "UK", LocalDateTime.now());
		artistRepository.save(artist);
		log.debug("artist={}", artist);
		
		List<Artist> artistList = artistRepository.findAll();
		log.debug("artistList={}", artistList);
	}

}
