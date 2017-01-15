package com.daou.setlist.web.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.daou.setlist.web.domain.artist.Artist;
import com.daou.setlist.web.domain.artist.ArtistRepository;
import com.daou.setlist.web.domain.setlist.Setlist;
import com.daou.setlist.web.domain.setlist.SetlistRepository;
import com.daou.setlist.web.domain.setlist.Song;
import com.daou.setlist.web.domain.setlist.SongRepository;

@Controller
public class TestController {

	private static final Logger log = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private ArtistRepository artistRepository;

	@Autowired
	private SetlistRepository setlistRepository;

	@Autowired
	private SongRepository songRepository;

	@GetMapping(path = "db-save")
	@ResponseBody
	public void save() {
		List<Artist> artists = artistRepository.findAll();
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("src/main/resources/db-data/Artist.csv"))) {
			artists.parallelStream().forEach(artist -> {
				StringBuffer line = new StringBuffer();
				line.append(artist.getArtistId()).append("\t")
					.append(artist.getArtistName()).append("\t")
					.append(artist.getNationality()).append("\t")
					.append(artist.getRegDate()).append("\n");
				try {
					writer.write(line.toString());
				} catch (IOException e) {
					log.error("테스트 데이터 추출 에러", e);
				}
			});
		} catch (IOException e) {
			log.error("테스트 데이터 추출 에러", e);
		}

		List<Setlist> setlists = setlistRepository.findAll();
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("src/main/resources/db-data/Setlist.csv"))) {
			setlists.parallelStream().forEach(setlist -> {
				StringBuffer line = new StringBuffer();
				line.append(setlist.getSetlistNo()).append("\t")
					.append(setlist.getArtistId()).append("\t")
					.append(setlist.getEventDate()).append("\t")
					.append(setlist.getTour().getTourName()).append("\t")
					.append(setlist.getTour().getVenue()).append("\n");
				try {
					writer.write(line.toString());
				} catch (IOException e) {
					log.error("테스트 데이터 추출 에러", e);
				}
			});
		} catch (IOException e) {
			log.error("테스트 데이터 추출 에러", e);
		}

		List<Song> songs = songRepository.findAll();
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("src/main/resources/db-data/Song.csv"))) {
			songs.parallelStream().forEach(song -> {
				// 1	1		Psycho
				StringBuffer line = new StringBuffer();
				line.append(song.getSongId().getSetlistNo()).append("\t")
					.append(song.getSongId().getTrackNo()).append("\t")
					.append(song.getSubject()).append("\t")
					.append(song.getRemark()).append("\n");
				try {
					writer.write(line.toString());
				} catch (IOException e) {
					log.error("테스트 데이터 추출 에러", e);
				}
			});
		} catch (IOException e) {
			log.error("테스트 데이터 추출 에러", e);
		}
	}
}