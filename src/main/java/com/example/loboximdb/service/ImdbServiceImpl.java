package com.example.loboximdb.service;

import com.example.loboximdb.domain.ImdbDto;
import com.example.loboximdb.domain.ImdbEntity;
import com.example.loboximdb.domain.Mapper;
import com.example.loboximdb.repository.ImdbRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author milad.mofidi@gmail.com
 */
@Service
public class ImdbServiceImpl implements ImdbService
{
    private ImdbRepository repository;
    private final Mapper mapper;

    public ImdbServiceImpl(ImdbRepository repository, Mapper mapper)
    {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ImdbDto insertOrUpdateImdb(ImdbDto inputtedDto)
    {
        Optional<ImdbEntity> imdb = repository.findById(inputtedDto.getNconst());
        if (imdb.isPresent())
        {
            imdb.get().setPrimaryName(inputtedDto.getPrimaryName());
            imdb.get().setDeathYear(inputtedDto.getDeathYear());
            imdb.get().setBirthYear(inputtedDto.getBirthYear());
            imdb.get().setKnownForTitles(inputtedDto.getKnownForTitles());
            imdb.get().setPrimaryProfession(inputtedDto.getPrimaryProfession());
            ImdbEntity entity = repository.save(imdb.get());
            return mapper.entityToDto(entity);
        }

        ImdbEntity entity = mapper.dtoToEntity(inputtedDto);
        entity = repository.save(entity);
        return mapper.entityToDto(entity);
    }


    public List<ImdbDto> findAllImdbs()
    {
        return repository.findAll().stream().map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    public long countOfAllImdbs()
    {
        return repository.count();
    }

    public ImdbDto findByNconst(String nconst)
    {
        ImdbEntity entity = repository.findByNconst(nconst);
        return mapper.entityToDto(entity);
    }

    public ImdbDto findByPrimaryName(String primaryName)
    {
        ImdbEntity entity = repository.findByPrimaryName(primaryName);
        return mapper.entityToDto(entity);
    }
}
