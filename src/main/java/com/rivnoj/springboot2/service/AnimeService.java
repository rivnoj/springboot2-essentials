package com.rivnoj.springboot2.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rivnoj.springboot2.domain.Anime;
import com.rivnoj.springboot2.exception.BadRequestException;
import com.rivnoj.springboot2.mapper.AnimeMapper;
import com.rivnoj.springboot2.repository.AnimeRepository;
import com.rivnoj.springboot2.requests.AnimePostRequestBody;
import com.rivnoj.springboot2.requests.AnimePutRequestBody;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimeService {
  private final AnimeRepository animeRepository;
  
  @SuppressWarnings("null")
  public Page<Anime> listAll(Pageable pageable) {
    return animeRepository.findAll(pageable);
  }

  public List<Anime> listAllNonPageable() {
    return animeRepository.findAll();
  }

  public List<Anime> findByName(String name) {
    return animeRepository.findByName(name);
  }

  public Anime findByIdOrThrowBadRequestException(long id) {
    return animeRepository.findById(id)
            // .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
            .orElseThrow(() -> new BadRequestException("Anime not found"));
  }

  @SuppressWarnings("null")
  @Transactional
  public Anime save(AnimePostRequestBody animePostRequestBody) {
    Anime anime = AnimeMapper.INSTANCE.toAnime(animePostRequestBody);
    //Anime anime =  Anime.builder().name(animePostRequestBody.getName()).build();
    return animeRepository.save(anime);
  }

  @SuppressWarnings("null")
  public void delete(long id) {
    animeRepository.delete(findByIdOrThrowBadRequestException(id));
  }

  public void replace(AnimePutRequestBody animePutRequestBody) {
    Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
    // Anime anime =  Anime.builder()
    //                       .id(savedAnime.getId())
    //                       .name(animePutRequestBody.getName())
    //                       .build();
    Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
    anime.setId(savedAnime.getId());
    animeRepository.save(anime);
  }
}
