package com.daou.setlist.web.domain.setlist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, SongId> {
	public List<Song> findBySongIdSetlistNo(Long setlistNo);
}