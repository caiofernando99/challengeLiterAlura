package com.challenge.literAlura.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> tclass);

    <T> T obterLista(String json, Class<T> tclass);
}
