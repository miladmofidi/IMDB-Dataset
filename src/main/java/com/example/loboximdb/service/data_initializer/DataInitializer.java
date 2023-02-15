package com.example.loboximdb.service.data_initializer;

import com.example.loboximdb.controller.ImdbController;
import com.example.loboximdb.domain.ImdbDto;
import com.example.loboximdb.domain.ImdbEntity;
import com.example.loboximdb.domain.Mapper;
import com.example.loboximdb.domain.enums.ImdbDataModelIndex;
import com.example.loboximdb.service.ImdbServiceImpl;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.zip.GZIPInputStream;

/**
 * @author milad mofidi
 * user: miladm on 1/6/2023
 */
@Component
public class DataInitializer implements CommandLineRunner
{
    private static final Logger LOGGER = LogManager.getLogger(DataInitializer.class);

    private final ImdbServiceImpl service;
    private final Mapper mapper;

    private static final String URL = "https://datasets.imdbws.com/name.basics.tsv.gz";
    private static String fileName = "imdb_gzip_with_sample_data.tsv.gz";
    private static Path sourcePath = Paths.get("src", "main", "resources", "sample_gzip", fileName);

    public DataInitializer(ImdbServiceImpl service, Mapper mapper)
    {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public void run(String... args) throws Exception
    {
        /*Uncomment below line if you want to fetch IMDB dataset from application local path*/
        //readImdbDatasetFromAppLocalPath();

        /*Fetching IMDB dataset from IMDB's server*/
        readImdbDatasetFromImdbServer();
    }


    private void readImdbDatasetFromImdbServer() throws IOException
    {
        List<String> lines = new ArrayList<>();

        URL url = new URL(URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("User-Agent",
                                             "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
        httpURLConnection.setDoInput(true);


        try (GZIPInputStream gzip = new GZIPInputStream(httpURLConnection.getInputStream());
             BufferedReader br = new BufferedReader(new InputStreamReader(gzip));
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
                    Optional<ImdbEntity> imdbEntity =
                            Optional.ofNullable(ImdbEntity.builder()
                                                        .nconst(csvRecord.get(ImdbDataModelIndex.NCONST.label).trim())
                                                        .PrimaryName(csvRecord.get(ImdbDataModelIndex.PRIMARYNAME.label)
                                                                             .trim())
                                                        .BirthYear(csvRecord.get(
                                                                ImdbDataModelIndex.BIRTHYEAR.label.trim()))
                                                        .DeathYear(csvRecord.get(ImdbDataModelIndex.DEATHYEAR.label)
                                                                           .trim())
                                                        .PrimaryProfession(Arrays.asList(
                                                                csvRecord.get(
                                                                                ImdbDataModelIndex.PRIMARYPROFESSION.label)
                                                                        .trim().split(",")))
                                                        .KnownForTitles(Arrays.asList(
                                                                csvRecord.get(ImdbDataModelIndex.KNOWNFORTITLES.label)
                                                                        .trim().split(",")))
                                                        .build());
                    imdbEntity.ifPresent(obj -> {
                        ImdbDto imdbDto = mapper.entityToDto(imdbEntity.get());
                        service.insertOrUpdateImdb(imdbDto);
                    });
                    System.out.println("New imdb saved in Database!");
                    itemCounter++;
                }
            }
            LOGGER.info("Count of saved items: " + itemCounter);
        }
        catch (IOException e)
        {
            LOGGER.error("An error has happened in reading data from the IMDB server!");
            e.printStackTrace(System.err);
        }

        httpURLConnection.disconnect();
    }

    private void readImdbDatasetFromAppLocalPath() throws IOException
    {
        File gzipFile = new File(sourcePath.toUri());

        try (
                GZIPInputStream gzip = new GZIPInputStream(Files.newInputStream(gzipFile.toPath()));
                Reader reader = new BufferedReader(new InputStreamReader(gzip, StandardCharsets.UTF_8));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.TDF.builder()
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
                    Optional<ImdbEntity> imdbEntity =
                            Optional.ofNullable(ImdbEntity.builder()
                                                        .nconst(csvRecord.get(ImdbDataModelIndex.NCONST.label).trim())
                                                        .PrimaryName(csvRecord.get(ImdbDataModelIndex.PRIMARYNAME.label)
                                                                             .trim())
                                                        .BirthYear(csvRecord.get(
                                                                ImdbDataModelIndex.BIRTHYEAR.label.trim()))
                                                        .DeathYear(csvRecord.get(ImdbDataModelIndex.DEATHYEAR.label)
                                                                           .trim())
                                                        .PrimaryProfession(Arrays.asList(
                                                                csvRecord.get(
                                                                                ImdbDataModelIndex.PRIMARYPROFESSION.label)
                                                                        .trim().split(",")))
                                                        .KnownForTitles(Arrays.asList(
                                                                csvRecord.get(ImdbDataModelIndex.KNOWNFORTITLES.label)
                                                                        .trim().split(",")))
                                                        .build());
                    imdbEntity.ifPresent(obj -> {
                        ImdbDto imdbDto = mapper.entityToDto(imdbEntity.get());
                        service.insertOrUpdateImdb(imdbDto);
                    });
                    System.out.println("New imdb saved in Database!");
                    itemCounter++;
                }
            }
            LOGGER.info("Count of saved items: " + itemCounter);
        }
        catch (IOException e)
        {
            LOGGER.error("An error has happened in reading data from the IMDB server!");
            e.printStackTrace(System.err);
        }
    }


    @PreDestroy
    public void preDestroy() throws IOException
    {
        FileUtils.deleteDirectory(new File("C:\\tempDb"));
    }
}
