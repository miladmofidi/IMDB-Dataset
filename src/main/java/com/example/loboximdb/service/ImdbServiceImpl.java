package com.example.loboximdb.service;

import com.example.loboximdb.domain.dto.NameDTO;
import com.example.loboximdb.domain.dto.TitleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 3/1/2023
 */
@Service
public class ImdbServiceImpl implements ImdbService
{
    private final NameService nameService;
    private final TitleService titleService;

    public ImdbServiceImpl(NameService nameService, TitleService titleService)
    {
        this.nameService = nameService;
        this.titleService = titleService;
    }

    @Override
    public Page<TitleDTO> getAllLiveSameDrAndWr(Pageable pageable)
    {
        List<TitleDTO> result = new ArrayList<>();

        List<TitleDTO> sameDirAndWri = titleService.findSameDirAndWri();
        sameDirAndWri.forEach(titleDTO -> {
            titleDTO.getDirectors().forEach(director -> {
                NameDTO foundedName = nameService.findByNconst(director);
                if (foundedName.getDeathYear() != null && foundedName.getDeathYear().equalsIgnoreCase("\\N"))
                {
                    result.add(titleDTO);
                }
            });
        });
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), result.size());
        return new PageImpl<>(result.subList(start, end), pageable, result.size());
    }
}
