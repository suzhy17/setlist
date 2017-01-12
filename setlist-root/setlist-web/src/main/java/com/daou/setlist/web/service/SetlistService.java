package com.daou.setlist.web.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daou.setlist.web.domain.artist.Artist;
import com.daou.setlist.web.domain.artist.ArtistRepository;
import com.daou.setlist.web.domain.setlist.Setlist;
import com.daou.setlist.web.domain.setlist.SetlistRepository;
import com.daou.setlist.web.domain.setlist.Song;
import com.daou.setlist.web.domain.setlist.SongId;
import com.daou.setlist.web.domain.setlist.SongRepository;

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
	 * @param subject 제목
	 * @param remark 비고
	 */
	public void registerSetlist(Setlist setlist, String[] subject, String[] remark) {
		
		log.info("세트리스트 등록");
		
		setlistRepository.save(setlist);

		List<Song> songs = new ArrayList<>();
		int trackNo = 1;
		for (int i = 0; i < subject.length; i++) {
			songs.add(new Song(new SongId(setlist.getSetlistNo(), trackNo++), subject[i], remark[i]));
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
	public List<Artist> searchArtists(String artistName) {
		if (StringUtils.isNotBlank(artistName)) {
			return artistRepository.findByArtistNameStartingWithIgnoreCase(artistName);
		} else {
			return artistRepository.findAll();
		}
	}
}
