package com.example.loboximdb.repository;

import com.example.loboximdb.domain.entity.Title;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author milad.mofidi@gmail.com
 */
@Repository
public interface TitleRepository extends CrudRepository<Title, String>
{
    Page<Title> findAll(Pageable pageable);
    Title findByTconst(String tconst);
}
