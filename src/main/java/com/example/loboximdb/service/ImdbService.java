package com.example.loboximdb.service;

import com.example.loboximdb.domain.dto.TitleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 3/1/2023
 */
public interface ImdbService
{
    Page<TitleDTO> getAllLiveSameDrAndWr(Pageable pageable);
}
