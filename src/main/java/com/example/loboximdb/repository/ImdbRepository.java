package com.example.loboximdb.repository;

import com.example.loboximdb.model.ImdbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * @author milad.mofidi@gmail.com
 */
@Repository
public interface ImdbRepository extends JpaRepository<ImdbEntity, String>
{
}
