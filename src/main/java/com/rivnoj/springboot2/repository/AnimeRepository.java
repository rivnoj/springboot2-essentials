package com.rivnoj.springboot2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rivnoj.springboot2.domain.Anime;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
  List<Anime>findByName(String name);
}
