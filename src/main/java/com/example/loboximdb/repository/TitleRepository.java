package com.example.loboximdb.repository;

import com.example.loboximdb.domain.entity.Title;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author milad.mofidi@gmail.com
 */
@Repository
public interface TitleRepository extends CrudRepository<Title, String>
{
    Page<Title> findAll(Pageable pageable);
    Title findByTconst(String tconst);

    //@Query("from Title where tconst is")
    @Query("FROM Title WHERE tconst IS NOT NULL")
    List<Title> findAllNotNull();
    @Query("SELECT title FROM Title title")
    List<Title> findDirAndWri();
}
