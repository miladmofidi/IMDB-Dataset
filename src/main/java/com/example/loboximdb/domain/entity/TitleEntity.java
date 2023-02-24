package com.example.loboximdb.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
public class TitleEntity
{
    @Id
    @Column(name="tconst")
    private String tconst;

    @Column(name="directors")
    @ElementCollection(targetClass=String.class)
    private List<String> directors;

    @Column(name="writers")
    @ElementCollection(targetClass=String.class)
    private List<String> writers;
}
