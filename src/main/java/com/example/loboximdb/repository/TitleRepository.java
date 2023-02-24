package com.example.loboximdb.repository;

import com.example.loboximdb.domain.entity.TitleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author milad.mofidi@gmail.com
 */
@Repository
public interface TitleRepository extends CrudRepository<TitleEntity, String>
{
    Page<TitleEntity> findAll(Pageable pageable);
    TitleEntity findByTconst(String tconst);
}
