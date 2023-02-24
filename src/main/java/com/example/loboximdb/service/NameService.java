package com.example.loboximdb.service;

import com.example.loboximdb.domain.dto.NameDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author milad.mofidi@gmail.com
 */
public interface NameService
{
    NameDTO insertOrUpdateImdb(NameDTO input);
    Page<NameDTO> findAll(Pageable pageable);
    long countOfAllImdbs();
    NameDTO findByNconst(String nconst);
    NameDTO findByPrimaryName(String nconst);
    
}
