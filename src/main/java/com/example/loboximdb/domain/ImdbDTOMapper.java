package com.example.loboximdb.domain;

import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/15/2023
 */
@Service
public class ImdbDTOMapper implements Function<ImdbEntity,ImdbDto>
{
    @Override
    public ImdbDto apply(ImdbEntity imdbEntity)
    {
        return new ImdbDto(imdbEntity.getNconst(),imdbEntity.getPrimaryName(), imdbEntity.getBirthYear(),
                           imdbEntity.getDeathYear(), imdbEntity.getPrimaryProfession(), imdbEntity.getKnownForTitles());
    }
}
