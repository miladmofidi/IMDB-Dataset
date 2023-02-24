package com.example.loboximdb.domain.mappers;

import com.example.loboximdb.domain.entity.Name;
import com.example.loboximdb.domain.dto.NameDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/15/2023
 */
@Service
public class ImdbDTOMapper implements Function<Name, NameDTO>
{
    @Override
    public NameDTO apply(Name name)
    {
        return new NameDTO(name.getNconst(), name.getPrimaryName(), name.getBirthYear(),
                           name.getDeathYear(), name.getPrimaryProfession(), name.getKnownForTitles());
    }
}
