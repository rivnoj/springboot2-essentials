package com.rivnoj.springboot2.controller;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rivnoj.springboot2.domain.Anime;
import com.rivnoj.springboot2.service.AnimeService;
import com.rivnoj.springboot2.util.AnimeCreator;

/*
 * @SpringBootTest 
 * Quando anotamos um teste com essa anotação, o contexto do spring não será inicializado 
 * dequadamente quando um recurso externo for esperado, como uma conexão ao banco de dados.
 * No caso de testes unitários, devemos usar uma outra anotação.
 */

 @ExtendWith(SpringExtension.class)//neste caso, anotamos que desejamos usar o JUnit com Spring
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

  }

  @SuppressWarnings("null")
  @Test
  @DisplayName("List returns list of animes inside page object when successful")
  void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
    String expectedName = AnimeCreator.createValidAnime().getName();
    Page<Anime> animePage = animeController.list(null).getBody();
    
    Assertions.assertThat(animePage).isNotNull();
    Assertions.assertThat(animePage.toList())
                                      .isNotEmpty()
                                      .hasSize(1);
    Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
  }
}
