package com.example.loboximdb.service.data_initializer;

import com.example.loboximdb.domain.dto.TitleDTO;
import com.example.loboximdb.domain.entity.Title;
import com.example.loboximdb.domain.mappers.TitleMapper;
import com.example.loboximdb.service.TitleService;
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
public class TitlesDatasetInitializer implements CommandLineRunner
{
    private static final Logger LOGGER = LogManager.getLogger(TitlesDatasetInitializer.class);
    private final TitleService service;
    private final TitleMapper titleMapper;

    private static final String URL = "https://datasets.imdbws.com/title.crew.tsv.gz";
    private static final String FILE_NAME = "sample-title-crews.tsv.gz";
    private static final Path SOURCE_PATH = Paths.get("src", "main", "resources", "sample_gzip", FILE_NAME);
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36";
    @Value("${application.readOnlineData}")
    private boolean readOnline;

    public TitlesDatasetInitializer(TitleService service, TitleMapper titleMapper)
    {
        this.service = service;
        this.titleMapper = titleMapper;
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
            gzipFile = new File(SOURCE_PATH.toUri());
        }

        try (GZIPInputStream gzip = new GZIPInputStream(
                readOnline ? httpURLConnection.getInputStream() : Files.newInputStream(gzipFile.toPath()));
             BufferedReader br = new BufferedReader(new InputStreamReader(gzip, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(br, CSVFormat.TDF.builder()
                     .setHeader("tconst",
                                "directors",
                                "writers")
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
                    Optional<Title> titleEntity = buildImdbEntity(csvRecord);
                    titleEntity.ifPresent(obj -> {
                        TitleDTO titleDTO = titleMapper.entityToDto(titleEntity.get());
                        service.insertOrUpdateImdb(titleDTO);
                    });
                    System.out.println("New title saved in Database!");
                    itemCounter++;
                }
            }
            LOGGER.info("Count of saved Title items: " + itemCounter);
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

    private static Optional<Title> buildImdbEntity(CSVRecord csvRecord)
    {
        return Optional.ofNullable(Title.builder()
                                           .tconst(csvRecord.get("tconst").trim())
                                           .directors(Arrays.asList(
                                                   csvRecord.get("directors")
                                                           .trim().split(",")))
                                           .writers(Arrays.asList(
                                                   csvRecord.get("writers")
                                                           .trim().split(",")))
                                           .build());
    }


    @PreDestroy
    public void preDestroy() throws IOException
    {
        FileUtils.deleteDirectory(new File("C:\\temp-Imdb"));
    }
}
