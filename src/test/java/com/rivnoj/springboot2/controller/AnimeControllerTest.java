package com.rivnoj.springboot2.controller;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rivnoj.springboot2.domain.Anime;
import com.rivnoj.springboot2.requests.AnimePostRequestBody;
import com.rivnoj.springboot2.requests.AnimePutRequestBody;
import com.rivnoj.springboot2.service.AnimeService;
import com.rivnoj.springboot2.util.AnimeCreator;
import com.rivnoj.springboot2.util.AnimePostRequestBodyCreator;
import com.rivnoj.springboot2.util.AnimePutRequestBodyCreator;

/*
 * @SpringBootTest 
 * Quando anotamos um teste com essa anotação, o contexto do spring não será inicializado 
 * dequadamente quando um recurso externo for esperado, como uma conexão ao banco de dados.
 * No caso de testes unitários, devemos usar uma outra anotação.
 */

@ExtendWith(SpringExtension.class) // neste caso, anotamos que desejamos usar o JUnit com Spring
public class AnimeControllerTest {

  /*
   * @InjectMocks é usado quando para testar a classe em si
   */

  /*
   * @Mock é usado para testar todas os atributos que estão dentro de uma classe
   */

  @InjectMocks
  private AnimeController animeController;

  @Mock
  private AnimeService animeServiceMock;

  @BeforeEach
  void setUp() {
    @SuppressWarnings("null")
    PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
    BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
        .thenReturn(animePage);

    BDDMockito.when(animeServiceMock.listAllNonPageable())
        .thenReturn(List.of(AnimeCreator.createValidAnime()));

    BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
        .thenReturn(AnimeCreator.createValidAnime());

    BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
        .thenReturn(List.of(AnimeCreator.createValidAnime()));
  
    BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
        .thenReturn(AnimeCreator.createValidAnime());

    BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class));

    BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());
}

  @SuppressWarnings("null")
  @Test
  @DisplayName("list returns list of animes inside page object when successful")
  void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
    String expectedName = AnimeCreator.createValidAnime().getName();
    Page<Anime> animePage = animeController.list(null).getBody();

    Assertions.assertThat(animePage).isNotNull();
    Assertions.assertThat(animePage.toList())
        .isNotEmpty()
        .hasSize(1);
    Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
  }

  @SuppressWarnings("null")
  @Test
  @DisplayName("listAll returns list of animes when successful")
  void listAll_ReturnsListOfAnimes_WhenSuccessful() {
    String expectedName = AnimeCreator.createValidAnime().getName();
    List<Anime> animes = animeController.listAll().getBody();

    Assertions.assertThat(animes)
        .isNotNull()
        .isNotEmpty()
        .hasSize(1);

    Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
  }

  @SuppressWarnings("null")
  @Test
  @DisplayName("findById returns anime when successful")
  void findById_ReturnsAnime_WhenSuccessful() {
    Long expectedId = AnimeCreator.createValidAnime().getId();
    Anime anime = animeController.findById(1L).getBody();

    Assertions.assertThat(anime)
        .isNotNull();

    Assertions.assertThat(anime.getId())
                .isNotNull()
                .isEqualTo(expectedId);
  }

  @SuppressWarnings("null")
  @Test
  @DisplayName("findByName returns a list of anime when successful")
  void findByName_ReturnsListOfAnime_WhenSuccessful() {
    String expectedName = AnimeCreator.createValidAnime().getName();
    List<Anime> animes = animeController.findByName("anime").getBody();

    Assertions.assertThat(animes)
        .isNotNull()
        .isNotEmpty()
        .hasSize(1);

    Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
  }

  @Test
  @DisplayName("findByName returns an empty list of anime when anime is not found")
  void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
    BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
        .thenReturn(Collections.emptyList());

    List<Anime> animes = animeController.findByName("anime").getBody();

    Assertions.assertThat(animes)
        .isNotNull()
        .isEmpty();
  }

  @Test
  @DisplayName("save returns anime when successful")
  void save_ReturnsAnime_WhenSuccessful() {
    Anime anime = animeController
                    .save(AnimePostRequestBodyCreator.createAnimePostRequestBody())
                    .getBody();

    Assertions.assertThat(anime)
                .isNotNull()
                .isEqualTo(AnimeCreator.createValidAnime());
  }

  @Test
  @DisplayName("replace updates anime when successful")
  void replace_UpdatesAnime_WhenSuccessful() {
    Assertions.assertThatCode(() -> animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
              .doesNotThrowAnyException();;
    
    ResponseEntity<Void> entity = animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());

    Assertions.assertThat(entity).isNotNull();
    Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  @DisplayName("delete removes anime when successful")
  void delete_RemovesAnime_WhenSuccessful() {
    Assertions.assertThatCode(() -> animeController.delete(1L))
              .doesNotThrowAnyException();;
    
    ResponseEntity<Void> entity = animeController.delete(1L);

    Assertions.assertThat(entity).isNotNull();
    Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }
}
