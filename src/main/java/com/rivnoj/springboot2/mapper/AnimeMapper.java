package com.rivnoj.springboot2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.rivnoj.springboot2.domain.Anime;
import com.rivnoj.springboot2.requests.AnimePostRequestBody;
import com.rivnoj.springboot2.requests.AnimePutRequestBody;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {
  public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

  @Mapping(target = "id", ignore = true)
  public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);
  public abstract Anime toAnime(AnimePutRequestBody animePutRequestBody);
}
