package com.example.loboximdb.service;

import com.example.loboximdb.domain.ImdbEntity;

import java.util.List;

/**
 * @author milad.mofidi@gmail.com
 */
public interface ImdbService
{
    ImdbEntity createOrUpdateImdb(ImdbEntity entity);
    List<ImdbEntity> findAllImdbs();
    long countOfAllImdbs();
    ImdbEntity findByNconst(String nconst);
}
