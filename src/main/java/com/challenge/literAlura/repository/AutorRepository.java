package com.challenge.literAlura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.challenge.literAlura.model.Autor;
import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNome(String nome);

    @Query("SELECT a FROM Autor a JOIN FETCH a.livros WHERE a.id = :id")
    Autor findByIdComLivros(@Param("id") Long id);

    //Para o case 4, se precisar usar o JOIN FETCH
    @Query("SELECT a FROM Autor a JOIN FETCH a.livros WHERE a.dataNascimento <= :ano AND a.dataMorte >= :ano")
    List<Autor> findByDataNascimentoLessThanEqualAndDataMorteGreaterThanEqualComLivros(@Param("ano") int ano);
}