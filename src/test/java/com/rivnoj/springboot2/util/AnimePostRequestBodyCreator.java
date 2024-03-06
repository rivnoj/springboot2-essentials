package com.rivnoj.springboot2.util;

import com.rivnoj.springboot2.requests.AnimePostRequestBody;

public class AnimePostRequestBodyCreator {
  public static AnimePostRequestBody createAnimePostRequestBody() {
    return AnimePostRequestBody
            .builder()
            .name(AnimeCreator.createAnimeToBeSaved().getName())
            .build();
  }
}
