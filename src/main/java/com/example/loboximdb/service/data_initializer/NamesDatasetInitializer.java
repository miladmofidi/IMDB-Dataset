package com.example.loboximdb.service.data_initializer;

import com.example.loboximdb.domain.dto.NameDTO;
import com.example.loboximdb.domain.entity.Name;
import com.example.loboximdb.domain.mappers.NameMapper;
import com.example.loboximdb.domain.enums.ImdbDataModelIndex;
import com.example.loboximdb.service.NameServiceImpl;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.zip.GZIPInputStream;

/**
 * @author milad mofidi
 * user: miladm on 1/6/2023
 */
@Component
public class NamesDatasetInitializer implements CommandLineRunner
{
    private static final Logger LOGGER = LogManager.getLogger(NamesDatasetInitializer.class);
    private final NameServiceImpl service;
    private final NameMapper nameMapper;

    private static final String URL = "https://datasets.imdbws.com/name.basics.tsv.gz";
    private static String fileName = "sample-name-basics.tsv.gz";
    private static Path sourcePath = Paths.get("src", "main", "resources", "sample_gzip", fileName);
    private final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36";
    @Value("${application.readOnlineData}")
    private boolean readOnline;

    public NamesDatasetInitializer(NameServiceImpl service, NameMapper nameMapper)
    {
        this.service = service;
        this.nameMapper = nameMapper;
    }

    @Override
    public void run(String... args)
    {
        /*If you want fetch sample IMDB data from application root you should set onlineRead to false */
        readAndSaveImdbData(readOnline);
    }

    private void readAndSaveImdbData(boolean readOnline)
    {
        URL url;
        File gzipFile = null;
        HttpURLConnection httpURLConnection = null;
        if (readOnline)
        {
            try
            {
                url = new URL(URL);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);
                httpURLConnection.setDoInput(true);
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
        else
        {
            gzipFile = new File(sourcePath.toUri());
        }

        try (GZIPInputStream gzip = new GZIPInputStream(
                readOnline ? httpURLConnection.getInputStream() : Files.newInputStream(gzipFile.toPath()));
             BufferedReader br = new BufferedReader(new InputStreamReader(gzip, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(br, CSVFormat.TDF.builder()
                     .setHeader(ImdbDataModelIndex.NCONST.label,
                                ImdbDataModelIndex.PRIMARYNAME.label,
                                ImdbDataModelIndex.BIRTHYEAR.label,
                                ImdbDataModelIndex.DEATHYEAR.label,
                                ImdbDataModelIndex.PRIMARYPROFESSION.label,
                                ImdbDataModelIndex.KNOWNFORTITLES.label)
                     .setSkipHeaderRecord(true)
                     .setTrim(true)
                     .build());
        )
        {
            int itemCounter = 0;
            for (CSVRecord csvRecord : csvParser)
            {
                if (csvRecord.size() >= csvParser.getHeaderMap().size())
                {
                    Optional<Name> imdbEntity = buildImdbEntity(csvRecord);
                    imdbEntity.ifPresent(obj -> {
                        NameDTO nameDTO = nameMapper.entityToDto(imdbEntity.get());
                        service.insertOrUpdateImdb(nameDTO);
                    });
                    System.out.println("New name saved in Database!");
                    itemCounter++;
                }
            }
            LOGGER.info("Count of saved Name items: " + itemCounter);
        }
        catch (Exception e)
        {
            LOGGER.error("An error has happened in reading data from the IMDB data source!");
            e.printStackTrace(System.err);
        }
        if (httpURLConnection != null)
        {
            httpURLConnection.disconnect();
        }
    }

    private static Optional<Name> buildImdbEntity(CSVRecord csvRecord)
    {
        return Optional.ofNullable(Name.builder()
                                           .nconst(csvRecord.get(ImdbDataModelIndex.NCONST.label).trim())
                                           .primaryName(csvRecord.get(ImdbDataModelIndex.PRIMARYNAME.label)
                                                                .trim())
                                           .birthYear(csvRecord.get(
                                                   ImdbDataModelIndex.BIRTHYEAR.label.trim()))
                                           .deathYear(csvRecord.get(ImdbDataModelIndex.DEATHYEAR.label)
                                                              .trim())
                                           .primaryProfession(Arrays.asList(
                                                   csvRecord.get(
                                                                   ImdbDataModelIndex.PRIMARYPROFESSION.label)
                                                           .trim().split(",")))
                                           .knownForTitles(Arrays.asList(
                                                   csvRecord.get(ImdbDataModelIndex.KNOWNFORTITLES.label)
                                                           .trim().split(",")))
                                           .build());
    }


    @PreDestroy
    public void preDestroy() throws IOException
    {
        FileUtils.deleteDirectory(new File("C:\\temp-Imdb"));
    }
}
