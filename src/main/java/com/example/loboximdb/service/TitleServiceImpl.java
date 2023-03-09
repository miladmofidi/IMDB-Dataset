package com.example.loboximdb.service;

import com.example.loboximdb.domain.dto.TitleDTO;
import com.example.loboximdb.domain.entity.Title;
import com.example.loboximdb.domain.mappers.TitleMapper;
import com.example.loboximdb.repository.TitleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/24/2023
 */
@Service
public class TitleServiceImpl implements TitleService
{
    private static final Logger logger = LogManager.getLogger(TitleServiceImpl.class);
    private TitleRepository titleRepository;
    private TitleMapper mapper;

    public TitleServiceImpl(TitleRepository titleRepository, TitleMapper mapper)
    {
        this.titleRepository = titleRepository;
        this.mapper = mapper;
    }

    @Override
    public TitleDTO insertOrUpdateImdb(TitleDTO inputtedDto)
    {
        Optional<Title> title = titleRepository.findById(inputtedDto.getTconst());
        if (title.isPresent())
        {
            title.get().setDirectors(inputtedDto.getDirectors());
            title.get().setWriters(inputtedDto.getWriters());
            Title entity = titleRepository.save(title.get());
            return mapper.entityToDto(entity);
        }

        Title entity = mapper.dtoToEntity(inputtedDto);
        entity = titleRepository.save(entity);
        return mapper.entityToDto(entity);
    }

    @Override
    public Page<TitleDTO> findAll(Pageable pageable)
    {
        try
        {
            return titleRepository.findAll(pageable).map(mapper::entityToDto);
        }
        catch (Exception e)
        {
            logger.error("an exception occurred: {} : {}", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public long countOfAllImdbs()
    {
        return titleRepository.count();
    }

    @Override
    public TitleDTO findByTconst(String tconst)
    {
        Title entity = titleRepository.findByTconst(tconst);
        return mapper.entityToDto(entity);
    }

    @Override
    public List<TitleDTO> findAllTconstNotNull()
    {
        List<Title> titles = titleRepository.findAllNotNull();
        List<TitleDTO> dtos = new ArrayList<>();
        for (Title title:titles ) {
            dtos.add(mapper.entityToDto(title));
        }
        return dtos;
    }

    @Override
    public Page<TitleDTO> findSameDirectorsAndWriters(Pageable pageable){
        List<String> sameDirectorsAndWriters = findSameDirectorsAndWriters();
        List<TitleDTO> titles = sameDirectorsAndWriters.stream().map(this::findByTconst).collect(Collectors.toList());

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), sameDirectorsAndWriters.size());
        return new PageImpl<>(titles.subList(start, end), pageable, titles.size());
    }
    @Override
    public List<TitleDTO> findSameDirAndWri(){
        List<String> sameDirectorsAndWriters = findSameDirectorsAndWriters();
        List<TitleDTO> titles = sameDirectorsAndWriters.stream().map(this::findByTconst).collect(Collectors.toList());
        return titles;
    }

    private List<String> findSameDirectorsAndWriters()
    {
        Set<String> sameDirectorsAndWriters = new HashSet<>();
        titleRepository.findDirAndWri().forEach(title -> {
            title.getWriters().stream()
                    .forEach(writer -> {
                        title.getDirectors().stream()
                                .filter(director -> director.equals(writer))
                                //.limit(1)
                                .forEach(director -> {
                                    sameDirectorsAndWriters.add(title.getTconst());
                                });
                    });
        });
        return new ArrayList<>(sameDirectorsAndWriters);
    }

}
