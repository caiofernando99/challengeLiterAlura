package com.challenge.literAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collection;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivros(@JsonAlias("title") String title,
                          @JsonAlias("autohrs") List<Autor> authors,
                          @JsonAlias("languages") List<String> languages,
                          @JsonAlias("download_count") int downloads) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Autor(
            @JsonAlias("name") String name
    ) {}
}
