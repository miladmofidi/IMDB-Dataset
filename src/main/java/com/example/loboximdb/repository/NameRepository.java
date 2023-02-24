package com.example.loboximdb.repository;

import com.example.loboximdb.domain.entity.Name;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author milad.mofidi@gmail.com
 */
@Repository
public interface NameRepository extends CrudRepository<Name, String>
{
    Page<Name> findAll(Pageable pageable);
    Name findByNconst(String nconst);
    Name findByPrimaryName(String primaryName);
}
