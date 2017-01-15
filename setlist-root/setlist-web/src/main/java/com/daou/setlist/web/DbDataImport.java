package com.daou.setlist.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import com.daou.setlist.web.domain.artist.Artist;
import com.daou.setlist.web.domain.artist.ArtistRepository;
import com.daou.setlist.web.domain.setlist.Setlist;
import com.daou.setlist.web.domain.setlist.SetlistRepository;
import com.daou.setlist.web.domain.setlist.Song;
import com.daou.setlist.web.domain.setlist.SongId;
import com.daou.setlist.web.domain.setlist.SongRepository;
import com.daou.setlist.web.domain.setlist.Tour;

/**
 * 테스트용 데이터 등록용 클래스 
 * @author HanYS
 */
@ActiveProfiles(profiles = {"", "default"})
@Component
public class DbDataImport implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(DbDataImport.class);

	@Autowired
	private ArtistRepository artistRepository;
	
	@Autowired
	private SetlistRepository setlistRepository;
	
	@Autowired
	private SongRepository songRepository;
	
	@Override
	public void run(String... args) throws Exception {

		log.info("아티스트 로드");
		
		try (Stream<String> stream = Files.lines(Paths.get("src/main/resources/db-data/Artist.csv"))) {

			stream.forEach(str -> {
				if (StringUtils.isBlank(str)) {
					return;
				}
				log.info(str);
				String[] line = str.split("\t");
				artistRepository.save(new Artist(line[0], line[1], line[2], LocalDateTime.parse(StringUtils.substringBeforeLast(line[3], "."), DateTimeFormatter.ISO_DATE_TIME)));
			});

		} catch (IOException e) {
			log.error("테스트 데이터 로드 에러", e);
		}

		log.info("세트리스트 로드");
		
		try (Stream<String> stream = Files.lines(Paths.get("src/main/resources/db-data/Setlist.csv"))) {

			stream.forEach(str -> {
				if (StringUtils.isBlank(str)) {
					return;
				}
				log.info(str);
				String[] line = str.split("\t");
				setlistRepository.save(new Setlist(Long.parseLong(line[0]), line[1], new Tour(line[3], line[4]),
						LocalDate.parse(line[2], DateTimeFormatter.ISO_DATE)));
			});

		} catch (IOException e) {
			log.error("테스트 데이터 로드 에러", e);
		}

		log.info("곡 로드");
		
		try (Stream<String> stream = Files.lines(Paths.get("src/main/resources/db-data/Song.csv"))) {

			stream.forEach(str -> {
				if (StringUtils.isBlank(str)) {
					return;
				}
				log.info(str);
				String[] line = str.split("\t");
				String remark = "";
				if (line.length == 4) {
					remark = line[3];					
				}
				songRepository.save(new Song(new SongId(Long.parseLong(line[0]), Integer.parseInt(line[1])), line[2], remark));
			});

		} catch (IOException e) {
			log.error("테스트 데이터 로드 에러", e);
		}
	}

}