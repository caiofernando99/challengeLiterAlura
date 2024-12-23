package com.challenge.literAlura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.Collection;
import java.util.List;

public class ConverteDados implements IConverteDados{
    private ObjectMapper mapper = new ObjectMapper();

    public void obterDados(String json){

    }

    @Override
    public <T> T obterDados(String json, Class<T> tclass) {
        try {
            return mapper.readValue(json, tclass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T obterLista(String json, Class<T> tclass) {
        CollectionType lista = mapper.getTypeFactory()
                .constructCollectionType(List.class, tclass);

        try {
            return mapper.readValue(json, lista);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
