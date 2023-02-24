package com.example.loboximdb.domain.mappers;

import com.example.loboximdb.domain.dto.TitleDTO;
import com.example.loboximdb.domain.entity.TitleEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/15/2023
 */
@Component
public class TitleMapper
{
    private static final Logger LOGGER = LogManager.getLogger(TitleMapper.class);

    public TitleDTO entityToDto(TitleEntity entity)
    {
        try
        {
            if (entity == null)
            {
                return null;
            }
            return TitleDTO.builder().tconst(entity.getTconst())
                    .directors(entity.getDirectors()).writers(entity.getWriters())
                    .build();
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public TitleEntity dtoToEntity(TitleDTO dto)
    {
        try
        {
            if (dto == null)
            {
                return null;
            }
            return TitleEntity.builder().tconst(dto.getTconst())
                    .directors(dto.getDirectors())
                    .writers(dto.getWriters()).build();
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage());
            return null;
        }
    }
}