package com.challenge.literAlura.service;

import com.challenge.literAlura.model.DadosLivros;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivrosWrapper(
        @JsonAlias("results") List<DadosLivros> results) {
}
