package com.example.loboximdb.domain.mappers;

import com.example.loboximdb.domain.dto.NameDTO;
import com.example.loboximdb.domain.entity.NameEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/15/2023
 */
@Component
public class NameMapper
{
    private static final Logger LOGGER = LogManager.getLogger(NameMapper.class);

    public NameDTO entityToDto(NameEntity entity)
    {
        try
        {
            if (entity == null)
            {
                return null;
            }
            return NameDTO.builder().nconst(entity.getNconst()).PrimaryName(entity.getPrimaryName()).BirthYear(
                            entity.getBirthYear()).DeathYear(entity.getDeathYear())
                    .PrimaryProfession(entity.getPrimaryProfession()).KnownForTitles(entity.getKnownForTitles())
                    .build();
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public NameEntity dtoToEntity(NameDTO dto)
    {
        try
        {
            if (dto == null)
            {
                return null;
            }
            return NameEntity.builder().nconst(dto.getNconst()).primaryName(dto.getPrimaryName()).birthYear(
                            dto.getBirthYear()).deathYear(dto.getDeathYear()).primaryProfession(dto.getPrimaryProfession())
                    .knownForTitles(dto.getKnownForTitles()).build();
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage());
            return null;
        }
    }
}