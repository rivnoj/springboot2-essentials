package com.rivnoj.springboot2.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

// import java.util.Arrays;
// import java.util.List;

// import org.springframework.core.ParameterizedTypeReference;
// import org.springframework.http.HttpMethod;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.rivnoj.springboot2.domain.Anime;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SpringClient {
  public static void main(String[] args) {
    // ResponseEntity<Anime> entity = new RestTemplate()
    //                                     .getForEntity("http://localhost:8080/animes/{id}", 
    //                                       Anime.class, 
    //                                       10);
    // log.info(entity);

    // Anime object = new RestTemplate().
    //                     getForObject("http://localhost:8080/animes/{id}", 
    //                       Anime.class, 
    //                       10);
    // log.info(object);

    // Anime[] allAnimes = new RestTemplate()
    //                           .getForObject("http://localhost:8080/animes/all", 
    //                             Anime[].class);
    // log.info(Arrays.toString(allAnimes));

    // ResponseEntity<List<Anime>> allAnimesWithExchange = new RestTemplate()
    //                                                       .exchange("http://localhost:8080/animes/all", 
    //                                                         HttpMethod.GET, 
    //                                                         null, 
    //                                                         new ParameterizedTypeReference<>() {});
    // log.info(allAnimesWithExchange.getBody());

    // Anime kindom = Anime.builder().name("Kindom").build();
    // Anime kindomSaved = new RestTemplate().postForObject("http://localhost:8080/animes", kindom, Anime.class);
    // log.info("saved anime {}", kindomSaved);

    // Anime samuraiChamploo = Anime.builder().name("Samurai Champloo").build();
    // ResponseEntity<Anime> samuraiChamplooSaved = new RestTemplate().
    //                               exchange("http://localhost:8080/animes", 
    //                                         HttpMethod.POST,
    //                                         new HttpEntity<>(samuraiChamploo, createJsonHeader()),
    //                                         Anime.class);
    // log.info("saved anime {}", samuraiChamplooSaved.getBody());

    Anime samuraiChamplooToBeUpdated = Anime.builder().id(20L).name("Samurai Champloo 2").build();

    ResponseEntity<Void> samuraiChamplooUpdated = new RestTemplate().
                                  exchange("http://localhost:8080/animes", 
                                            HttpMethod.PUT,
                                            new HttpEntity<>(samuraiChamplooToBeUpdated, createJsonHeader()),
                                            Void.class);
    log.info(samuraiChamplooUpdated);

    ResponseEntity<Void> samuraiChamplooDeleted = new RestTemplate().
                                  exchange("http://localhost:8080/animes/{id}", 
                                            HttpMethod.DELETE,
                                            null,
                                            Void.class,
                                            samuraiChamplooToBeUpdated.getId());
    log.info(samuraiChamplooDeleted);
  }

  private static HttpHeaders createJsonHeader() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    return httpHeaders;
  }
}
