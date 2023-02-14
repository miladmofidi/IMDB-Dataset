package com.example.loboximdb.service;

import com.example.loboximdb.domain.ImdbEntity;
import com.example.loboximdb.domain.enums.ImdbDataModelIndex;
import com.example.loboximdb.service.ImdbServiceImpl;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

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
import java.util.Optional;
import java.util.zip.GZIPInputStream;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/12/2023
 */
@Component
public class CsvParsingByCommonsCsv
{
    private static ImdbServiceImpl service;
    private static String fileName = "imdb_gzip_with_sample_data.gz";
    private static Path sourcePath = Paths.get("src", "main", "resources", "sample_gzip", fileName);

    public CsvParsingByCommonsCsv(ImdbServiceImpl service)
    {
        this.service = service;
    }

    public static void main(String[] args) throws IOException
    {
        run();
    }


    public static void run() throws IOException
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
                Optional<ImdbEntity>  imdbEntity = Optional.ofNullable(ImdbEntity.builder()
                                                                           .nconst(csvRecord.get("nconst"))
                                                                           .PrimaryName(csvRecord.get("primaryName"))
                                                                           .BirthYear(csvRecord.get("birthYear"))
                                                                           .DeathYear(csvRecord.get("deathYear"))
                                                                           .PrimaryProfession(Arrays.asList(
                                                                                   csvRecord.get("primaryProfession")
                                                                                           .split(",")))
                                                                           .KnownForTitles(Arrays.asList(
                                                                                   csvRecord.get("knownForTitles")
                                                                                           .split(",")))
                                                                           .build());
                imdbEntity.ifPresent(obj -> service.createOrUpdateImdb(obj));
                System.out.println("New imdb has been saved!");

            }
        }
    }
}
