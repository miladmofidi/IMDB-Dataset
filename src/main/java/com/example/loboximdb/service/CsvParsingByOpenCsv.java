package com.example.loboximdb.service;

import com.example.loboximdb.domain.dto.NameDTO;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/12/2023
 */
public class CsvParsingByOpenCsv
{
    private static String fileName = "sample-name-basics.tsv.gz";
    private static Path sourcePath = Paths.get("src", "main", "resources", "sample_gzip", fileName);

    public static void main(String[] args) throws IOException
    {
        File gzipFile = new File(sourcePath.toUri());
        GZIPInputStream gzip = new GZIPInputStream(Files.newInputStream(gzipFile.toPath()));
        Reader reader = new BufferedReader(new InputStreamReader(gzip, StandardCharsets.UTF_8));

        List<NameDTO> beans = new CsvToBeanBuilder(reader)
                .withType(NameDTO.class)
                .build()
                .parse();

        beans.forEach(System.out::println);

    }


}

