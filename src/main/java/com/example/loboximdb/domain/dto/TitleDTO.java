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
public class TitleDTO
{
    private String tconst;
    private List<String> directors;
    private List<String> writers;
}
