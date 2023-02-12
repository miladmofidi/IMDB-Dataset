package com.example.loboximdb.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import java.util.List;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/12/2023
 */

@Getter
@Setter
public class ImdbDto
{
    //@CsvBindByPosition(position = 0)
    @CsvBindByName
    private String nconst;
    //@CsvBindByPosition(position = 1)
    @CsvBindByName
    private String PrimaryName;
    //@CsvBindByPosition(position = 2)
    @CsvBindByName
    private String BirthYear;
    //@CsvBindByPosition(position = 3)
    @CsvBindByName
    private String DeathYear;
    //@CsvBindByPosition(position = 4)
    @CsvBindByName
    private List<String> PrimaryProfession;
    //@CsvBindByPosition(position = 5)
    @CsvBindByName
    private List<String> KnownForTitles;
}
