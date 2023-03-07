package com.example.loboximdb.service;

import com.example.loboximdb.domain.dto.NameDTO;
import com.example.loboximdb.domain.dto.TitleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author milad.mofidi@gmail.com
 */
public interface TitleService
{
    TitleDTO insertOrUpdateImdb(TitleDTO input);
    Page<TitleDTO> findAll(Pageable pageable);
    long countOfAllImdbs();
    TitleDTO findByTconst(String tconst);
    List<TitleDTO> findAllTconstNotNull();

}
