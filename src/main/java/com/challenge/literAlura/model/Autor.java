//package com.challenge.literAlura.model;
//
//import jakarta.persistence.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "autores")
//public class Autor {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String nome;
//
//    private List<Livro> livros = new ArrayList<>();
//
//    @Override
//    public String toString() {
//        return
//                "Autor: '" + nome + '\'' +
//                ", livros=" + livros;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getNome() {
//        return nome;
//    }
//
//    public void setNome(String nome) {
//        this.nome = nome;
//    }
//
//    public List<Livro> getLivros() {
//        return livros;
//    }
//
//    public void setLivros(List<Livro> livros) {
//        this.livros = livros;
//    }
//}
