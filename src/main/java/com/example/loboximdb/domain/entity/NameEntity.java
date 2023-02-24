package com.example.loboximdb.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
public class NameEntity
{
    @Id
    //@GeneratedValue
    @Column(name="nconst")
    private String nconst;

    @Column(name="primaryName")
    private String primaryName;

    @Column(name="birthYear")
    private String birthYear;

    @Column(name="deathYear")
    private String deathYear;

    @Column(name="primaryProfession")
    @ElementCollection(targetClass=String.class)
     //@CollectionTable(name = "TBL_IMDB", joinColumns = @JoinColumn(name = "nconst"))
    private List<String> primaryProfession;

    @Column(name="knownForTitles")
    @ManyToMany
    @ElementCollection(targetClass=String.class)
    private List<String> knownForTitles;
}
