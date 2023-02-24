package com.example.loboximdb.repository;

import com.example.loboximdb.domain.entity.NameEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author milad.mofidi@gmail.com
 */
@Repository
public interface NameRepository extends CrudRepository<NameEntity, String>
{
    Page<NameEntity> findAll(Pageable pageable);
    NameEntity findByNconst(String nconst);
    NameEntity findByPrimaryName(String primaryName);
}
