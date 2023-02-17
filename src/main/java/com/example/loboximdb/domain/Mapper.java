package com.example.loboximdb.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/15/2023
 */
@Component
public class Mapper
{
    private static final Logger LOGGER = LogManager.getLogger(Mapper.class);

    public ImdbDto entityToDto(ImdbEntity entity)
    {
        try
        {
            if (entity == null)
            {
                return null;
            }
            return ImdbDto.builder().nconst(entity.getNconst()).PrimaryName(entity.getPrimaryName()).BirthYear(
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

    public ImdbEntity dtoToEntity(ImdbDto dto)
    {
        try
        {
            if (dto == null)
            {
                return null;
            }
            return ImdbEntity.builder().nconst(dto.getNconst()).primaryName(dto.getPrimaryName()).birthYear(
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