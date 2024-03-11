package com.rivnoj.springboot2.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import com.rivnoj.springboot2.domain.Anime;
import com.rivnoj.springboot2.repository.AnimeRepository;
import com.rivnoj.springboot2.util.AnimeCreator;
import com.rivnoj.springboot2.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase // Usará um banco de dados em memória para os testes
class AnimeControllerIT {
  @Autowired
  private TestRestTemplate testRestTemplate;

  @LocalServerPort
  private int port;

  @Autowired
  private AnimeRepository animeRepository;

  @SuppressWarnings("null")
  @Test
  @DisplayName("list returns list of animes inside page object when successful")
  void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
    Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
    String expectedName = savedAnime.getName();
    
    PageableResponse<Anime> animePage = testRestTemplate.exchange("/animes", HttpMethod.GET, null, 
        new ParameterizedTypeReference<PageableResponse<Anime>>() {}).getBody();

    Assertions.assertThat(animePage).isNotNull();
    Assertions.assertThat(animePage.toList())
        .isNotEmpty()
        .hasSize(1);
    Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
  }
}
