package com.challenge.literAlura.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> tClass);
}
