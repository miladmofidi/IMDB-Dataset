package com.example.loboximdb.service;

import com.example.loboximdb.domain.dto.NameDTO;
import com.example.loboximdb.domain.entity.Name;
import com.example.loboximdb.domain.mappers.NameMapper;
import com.example.loboximdb.repository.NameRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author milad.mofidi@gmail.com
 */
@Service
public class NameServiceImpl implements NameService
{
    private static final Logger logger = LogManager.getLogger(NameServiceImpl.class);

    private NameRepository repository;
    private final NameMapper mapper;

    public NameServiceImpl(NameRepository repository, NameMapper mapper)
    {
        this.repository = repository;
        this.mapper = mapper;
    }

    public NameDTO insertOrUpdateImdb(NameDTO inputtedDto)
    {
        Optional<Name> name = repository.findById(inputtedDto.getNconst());
        if (name.isPresent())
        {
            name.get().setPrimaryName(inputtedDto.getPrimaryName());
            name.get().setDeathYear(inputtedDto.getDeathYear());
            name.get().setBirthYear(inputtedDto.getBirthYear());
            name.get().setKnownForTitles(inputtedDto.getKnownForTitles());
            name.get().setPrimaryProfession(inputtedDto.getPrimaryProfession());
            Name entity = repository.save(name.get());
            return mapper.entityToDto(entity);
        }

        Name entity = mapper.dtoToEntity(inputtedDto);
        entity = repository.save(entity);
        return mapper.entityToDto(entity);
    }




    public Page<NameDTO> findAll(Pageable pageable)
    {
        try
        {
            return repository.findAll(pageable).map(mapper::entityToDto);
        }
        catch (Exception e)
        {
            logger.error("an exception occurred: {} : {}", e.getMessage(), e);
        }
        return null;
    }

    public long countOfAllImdbs()
    {
        return repository.count();
    }

    public NameDTO findByNconst(String nconst)
    {
        Name entity = repository.findByNconst(nconst);
        return mapper.entityToDto(entity);
    }

    public NameDTO findByPrimaryName(String primaryName)
    {
        Name entity = repository.findByPrimaryName(primaryName);
        return mapper.entityToDto(entity);
    }
}
