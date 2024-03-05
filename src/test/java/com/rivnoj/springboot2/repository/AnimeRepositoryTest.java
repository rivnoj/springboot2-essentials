package com.rivnoj.springboot2.repository;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.rivnoj.springboot2.domain.Anime;
import com.rivnoj.springboot2.util.AnimeCreator;

@DataJpaTest
@DisplayName("Tests for Anime repository")
public class AnimeRepositoryTest {
  @Autowired
  private AnimeRepository animeRepository;

  @Test
  @DisplayName("Save persists anime when successfull")
  void save_PersistAnime_WhenSuccessful() {
    Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
    
    @SuppressWarnings("null")
    Anime savedAnime = this.animeRepository.save(animeToBeSaved);

    Assertions.assertThat(savedAnime).isNotNull();
    
    Assertions.assertThat(savedAnime.getId()).isNotNull();
    
    Assertions.assertThat(savedAnime.getName()).isEqualTo(animeToBeSaved.getName());
  }

  @Test
  @DisplayName("Save updates anime when successfull")
  void save_UpdateAnime_WhenSuccessful() {
    Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
    
    @SuppressWarnings("null")
    Anime savedAnime = this.animeRepository.save(animeToBeSaved);

    savedAnime.setName("Overlord");

    Anime updatedAnime = this.animeRepository.save(savedAnime);
    
    Assertions.assertThat(updatedAnime).isNotNull();
    
    Assertions.assertThat(updatedAnime.getId()).isNotNull();
    
    Assertions.assertThat(updatedAnime.getName()).isEqualTo(savedAnime.getName());
  }
  
  @Test
  @DisplayName("Delete removes anime when successfull")
  void delete_RemovesAnime_WhenSuccessful() {
    Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
    
    @SuppressWarnings("null")
    Anime savedAnime = this.animeRepository.save(animeToBeSaved);

    this.animeRepository.delete(savedAnime);

    @SuppressWarnings("null")
    Optional<Anime> animeOptional = this.animeRepository.findById(savedAnime.getId());

    Assertions.assertThat(animeOptional).isEmpty();
  }

  @Test
  @DisplayName("Find by name returns list of anime when successful")
  void findByName_ReturnsListOfAnime_WhenSuccessful() {
    Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
    
    @SuppressWarnings("null")
    Anime savedAnime = this.animeRepository.save(animeToBeSaved);

    String name = savedAnime.getName();

    List<Anime> animes = this.animeRepository.findByName(name);

    Assertions.assertThat(animes)
                .isNotEmpty()
                .contains(savedAnime);
  }

  @Test
  @DisplayName("Find by name returns empty list when no anime is found")
  void findByName_ReturnsEmptyList_WhenAnimeIsNotFound() {
    List<Anime> animes = this.animeRepository.findByName("sdcwcew");

    Assertions.assertThat(animes).isEmpty();
  }

  @Test
  @DisplayName("Save throws ConstraintViolationException when name is empty")
  void save_ThrowsConstraintViolationException_WhenNameIsEmpty() {
    Anime anime = new Anime();
    
    //Alternativa 1
    //Assertions.assertThatThrownBy(() -> this.animeRepository.save(anime))
    //            .isInstanceOf(ConstraintViolationException.class);

    Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.animeRepository.save(anime))
                .withMessageContaining("The anime name cannot be empty");
  }
}
