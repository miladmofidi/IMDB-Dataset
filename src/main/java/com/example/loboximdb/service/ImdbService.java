package com.example.loboximdb.service;

import com.example.loboximdb.domain.ImdbDto;
import com.example.loboximdb.domain.ImdbEntity;

import java.util.List;

/**
 * @author milad.mofidi@gmail.com
 */
public interface ImdbService
{
    ImdbDto insertOrUpdateImdb(ImdbDto input);
    List<ImdbDto> findAllImdbs();
    long countOfAllImdbs();
    ImdbDto findByNconst(String nconst);
    ImdbDto findByPrimaryName(String nconst);
    
}
