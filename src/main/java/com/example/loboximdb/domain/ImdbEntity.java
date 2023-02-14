package com.example.loboximdb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
/**
 * @author milad.mofidi@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
//@Table(name = "tbl_imdb")
public class ImdbEntity
{
    @Id
    //@GeneratedValue
    @Column(name="nconst")
    private String nconst;

    @Column(name="primaryName")
    private String PrimaryName;

    @Column(name="birthYear")
    private String BirthYear;

    @Column(name="deathYear")
    private String DeathYear;

    @Column(name="primaryProfession")
    @ElementCollection(targetClass=String.class)
     //@CollectionTable(name = "TBL_IMDB", joinColumns = @JoinColumn(name = "nconst"))
    private List<String> PrimaryProfession;

    @Column(name="knownForTitles")
    @ElementCollection(targetClass=String.class)
    private List<String> KnownForTitles;
}