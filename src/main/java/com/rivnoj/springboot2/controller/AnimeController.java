package com.rivnoj.springboot2.controller;

//import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rivnoj.springboot2.domain.Anime;
import com.rivnoj.springboot2.requests.AnimePostRequestBody;
import com.rivnoj.springboot2.requests.AnimePutRequestBody;
import com.rivnoj.springboot2.service.AnimeService;
//import com.rivnoj.springboot2.util.DateUtil;

import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("animes")
//@Log4j2
//@AllArgsConstructor
@RequiredArgsConstructor //necessita que os fields sejam declarados com a keyword final
public class AnimeController {
  // private final DateUtil dateUtil;
  private final AnimeService animeService;
  
  @SuppressWarnings("null")
  @GetMapping
  public ResponseEntity<Page<Anime>> list(Pageable pageable) {
    //log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
    //return new ResponseEntity<>(animeService.listAll(), HttpStatus.OK); or...
    return ResponseEntity.ok(animeService.listAll(pageable));
  }
  @SuppressWarnings("null")
  @GetMapping("/all")
  public ResponseEntity<List<Anime>> listAll() {
    //log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
    return ResponseEntity.ok(animeService.listAllNonPageable());
  }

  @SuppressWarnings("null")
  @GetMapping(path = "/{id}")
  public ResponseEntity<Anime> findById(@PathVariable long id) {
    return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
  }

  @SuppressWarnings("null")
  @GetMapping(path = "/find")
  public ResponseEntity<List<Anime>> findByName(@RequestParam String name) {
    return ResponseEntity.ok(animeService.findByName(name));
  }

  @PostMapping
  public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody) {
    return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    animeService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping
  public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody) {
    animeService.replace(animePutRequestBody);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
