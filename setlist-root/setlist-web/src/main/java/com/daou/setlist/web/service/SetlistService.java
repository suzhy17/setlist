package com.daou.setlist.web.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.daou.setlist.web.domain.setlist.Setlist;
import com.daou.setlist.web.domain.setlist.SetlistRepository;
import com.daou.setlist.web.domain.setlist.Song;
import com.daou.setlist.web.domain.setlist.SongId;
import com.daou.setlist.web.domain.setlist.SongRepository;

@Service
public class SetlistService {

	private static final Logger log = LoggerFactory.getLogger(SetlistService.class);

	@Autowired
	private Environment env;

	@Autowired
	private SetlistRepository setlistRepository;
	
	@Autowired
	private SongRepository songRepository;

	public void registerSetlist(Setlist setlist, String[] subject, String[] remark) {
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
}
