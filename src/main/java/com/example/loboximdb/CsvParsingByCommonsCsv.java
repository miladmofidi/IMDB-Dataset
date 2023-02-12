package com.example.loboximdb;

import com.example.loboximdb.model.ImdbEntity;
import com.example.loboximdb.model.enums.ImdbDataModelIndex;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/12/2023
 */
public class CsvParsingByCommonsCsv
{
    private static String fileName = "imdb_gzip_with_sample-data.gzip";
    private static Path sourcePath = Paths.get("src", "main", "resources", "sample_gzip", fileName);


    public static void main(String[] args) throws IOException
    {
        File gzipFile = new File(sourcePath.toUri());

        try (
                GZIPInputStream gzip = new GZIPInputStream(Files.newInputStream(gzipFile.toPath()));
                Reader reader = new BufferedReader(new InputStreamReader(gzip, StandardCharsets.UTF_8));

  /*              CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withHeader("nconst", "primaryName", "birthYear", "deathYear", "primaryProfession", "knownForTitles")
                        .withIgnoreHeaderCase()
                        .withTrim());*/

                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder()
                        .setHeader(ImdbDataModelIndex.NCONST.label, ImdbDataModelIndex.PRIMARYNAME.label,
                                   ImdbDataModelIndex.BIRTHYEAR.label, ImdbDataModelIndex.DEATHYEAR.label,
                                   ImdbDataModelIndex.PRIMARYPROFESSION.label,
                                   ImdbDataModelIndex.KNOWNFORTITLES.label)
                        .setSkipHeaderRecord(true)
                        .setTrim(true)
                        .build());
        )
        {

            for (CSVRecord csvRecord : csvParser)
            {
                ImdbEntity entity = ImdbEntity.builder()
                        .nconst(csvRecord.get("nconst"))
                        .PrimaryName(csvRecord.get("primaryName"))
                        .BirthYear(csvRecord.get("birthYear"))
                        .DeathYear(csvRecord.get("deathYear"))
                        .PrimaryProfession(Arrays.asList(csvRecord.get("primaryProfession").split(",")))
                        .KnownForTitles(Arrays.asList(csvRecord.get("knownForTitles").split(",")))
                        .build();
            }
        }
    }
}
