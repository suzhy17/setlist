package com.daou.setlist.web.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.daou.setlist.common.exception.EmsJsonException;
import com.daou.setlist.web.domain.artist.Artist;
import com.daou.setlist.web.domain.artist.ArtistRepository;
import com.daou.setlist.web.domain.setlist.Setlist;
import com.daou.setlist.web.domain.setlist.SetlistRepository;
import com.daou.setlist.web.domain.setlist.Song;
import com.daou.setlist.web.domain.setlist.SongId;
import com.daou.setlist.web.domain.setlist.SongRepository;
import com.daou.setlist.web.domain.setlist.Tour;

@Service
public class SetlistService {

	private static final Logger log = LoggerFactory.getLogger(SetlistService.class);

	@Autowired
	private SetlistRepository setlistRepository;
	
	@Autowired
	private ArtistRepository artistRepository;
	
	@Autowired
	private SongRepository songRepository;

	/**
	 * 세트리스트 등록
	 * @param setlist 세트리스트
	 * @param subjects 제목
	 * @param remarks 비고
	 */
	public void registerSetlist(String artistId, String tourName, String venue, String eventDate, String[] subjects, String[] remarks) {
		
		log.info("세트리스트 등록");

		Setlist setlist = new Setlist(artistId, new Tour(tourName, venue) , LocalDate.parse(eventDate, DateTimeFormatter.ISO_DATE));

		Artist artist = artistRepository.findOne(setlist.getArtistId());
		if (artist == null) {
			throw new EmsJsonException("해당하는 아티스트가 없습니다.");
		}
		
		setlistRepository.save(setlist);

		List<Song> songs = new ArrayList<>();
		
		int trackNo = 1;
		int idx = 0;
		for (String subject : subjects) {
			if (StringUtils.isNotBlank(subject)) {
				songs.add(new Song(new SongId(setlist.getSetlistNo(), trackNo++), subject, remarks[idx]));
			}
			idx++;
		}

		for (Song song : songs) {
			songRepository.save(song);
		}
	}

	/**
	 * 아티스트 검색
	 * @param artistName 아티스트명 앞자리
	 * @return
	 */
//	@Cacheable(cacheNames = "artists", key = "#artistName")
	public List<Artist> searchArtists(String artistName) {
		if (StringUtils.isNotBlank(artistName)) {
			return artistRepository.findByArtistNameStartingWithIgnoreCase(artistName);
		} else {
			return artistRepository.findAll();
		}
	}
}
