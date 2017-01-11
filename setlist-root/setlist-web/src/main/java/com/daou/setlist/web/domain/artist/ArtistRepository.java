package com.daou.setlist.web.domain.artist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, String> {
	public List<Artist> findByArtistNameStartingWithIgnoreCase(String startName);
}