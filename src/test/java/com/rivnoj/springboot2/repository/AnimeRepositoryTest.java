package com.rivnoj.springboot2.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.rivnoj.springboot2.domain.Anime;

@DataJpaTest
@DisplayName("Tests for Anime repository")
public class AnimeRepositoryTest {
  @Autowired
  private AnimeRepository animeRepository;

  @Test
  @DisplayName("Save created anime when successfull")
  void save_PersistAnime_WhenSuccessful() {
    Anime animeToBeSaved = createAnime();
    @SuppressWarnings("null")
    Anime savedAnime = this.animeRepository.save(animeToBeSaved);

    Assertions.assertThat(savedAnime).isNotNull();
    Assertions.assertThat(savedAnime.getId()).isNotNull();
    Assertions.assertThat(savedAnime.getName()).isEqualTo(animeToBeSaved.getName());
  }

  private Anime createAnime() {
    return Anime
            .builder()
            .name("Hajime no Ippo")
            .build();
  }
}
