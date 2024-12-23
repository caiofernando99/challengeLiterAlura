package com.challenge.literAlura.repository;

import com.challenge.literAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
//    List<Livro> findByLanguages(String language);
//    List<Livro> findByTitleContaining(String title);
}
