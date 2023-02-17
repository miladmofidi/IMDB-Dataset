package com.example.loboximdb.repository;

import com.example.loboximdb.domain.ImdbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author milad.mofidi@gmail.com
 */
@Repository
public interface ImdbRepository extends CrudRepository<ImdbEntity, String>
{
    List<ImdbEntity> findAll();
    ImdbEntity findByNconst(String nconst);
    ImdbEntity findByPrimaryName(String primaryName);
}
