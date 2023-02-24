package com.example.loboximdb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/12/2023
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NameDTO
{
    //@CsvBindByPosition(position = 0)
    //@CsvBindByName
    private String nconst;
    //@CsvBindByPosition(position = 1)
    //@CsvBindByName
    private String PrimaryName;
    //@CsvBindByPosition(position = 2)
    //@CsvBindByName
    private String BirthYear;
    //@CsvBindByPosition(position = 3)
    //@CsvBindByName
    private String DeathYear;
    //@CsvBindByPosition(position = 4)
    //@CsvBindByName
    private List<String> PrimaryProfession;
    //@CsvBindByPosition(position = 5)
    //@CsvBindByName
    private List<String> KnownForTitles;
}
