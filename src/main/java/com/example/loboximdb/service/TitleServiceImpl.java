package com.example.loboximdb.service;

import com.example.loboximdb.domain.dto.TitleDTO;
import com.example.loboximdb.domain.entity.TitleEntity;
import com.example.loboximdb.domain.mappers.TitleMapper;
import com.example.loboximdb.repository.TitleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Optional<TitleEntity> title = titleRepository.findById(inputtedDto.getTconst());
        if (title.isPresent())
        {
            title.get().setDirectors(inputtedDto.getDirectors());
            title.get().setWriters(inputtedDto.getWriters());
            TitleEntity entity = titleRepository.save(title.get());
            return mapper.entityToDto(entity);
        }

        TitleEntity entity = mapper.dtoToEntity(inputtedDto);
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
        TitleEntity entity = titleRepository.findByTconst(tconst);
        return mapper.entityToDto(entity);
    }
}
