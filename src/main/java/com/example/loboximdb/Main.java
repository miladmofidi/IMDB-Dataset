package com.example.loboximdb;

import com.example.loboximdb.model.ImdbEntity;
import com.example.loboximdb.service.ImdbService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

@Component
public class Main implements CommandLineRunner
{
    //@Resource
    //private ImdbRepository repository;
    @Resource
    private ImdbService imdbService;


    @Override
    public void run(String... args) throws Exception
    {
        readLinesFromGZ();
    }

    public List<String> readLinesFromGZ()
    {
        List<String> lines = new ArrayList<>();
        File file = new File("C:\\Users\\miladm\\Downloads\\newTsv25000.tsv.gzip");
        List<ImdbEntity> imdbEntityList = new ArrayList<>();

        try (GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(file));
             BufferedReader br = new BufferedReader(new InputStreamReader(gzip));)
        {
            String line = null;
            int removeHeaderCounter = 0;
            String[] header = null;

            while ((line = br.readLine()) != null)
            {
                if (removeHeaderCounter == 0)
                {
                    removeHeaderCounter++;
                    header = line.split("\t");
                    continue;
                }
                String[] lineItems = line.split("\t");

                if (lineItems.length > 0)
                {
                    ImdbEntity imdbEntity = new ImdbEntity();
                    imdbEntity.setNconst(lineItems[0]);
                    imdbEntity.setPrimaryName(lineItems[1]);
                    imdbEntity.setBirthYear(lineItems[2]);
                    imdbEntity.setDeathYear(lineItems[3]);
                    imdbEntity.setPrimaryProfession(Arrays.stream(lineItems[4].split(",")).collect(Collectors.toList()));
                    imdbEntity.setKnownForTitles(Arrays.stream(lineItems[5].split(",")).collect(Collectors.toList()));

                    //imdbEntityList.add(imdbEntity);
                    //imdbService.createOrUpdateImdb(imdbEntity);
                    System.out.println("New IMDB Saved! ");
                }
            }
/*            List<ImdbModel> collect = br
                    .lines()
                    .skip(1) //Skips the first n lines, in this case 1
                    .map(s -> {
                        //csv line parsing and xml logic here
                        String[] lineItems = s.split(",");
                        ImdbModel model = new ImdbModel();
                        model.setNconst(lineItems[0]);
                        model.setPrimaryName(lineItems[1]);
                        model.setBirthYear(Integer.parseInt(lineItems[2]));
                        model.setDeathYear(Integer.parseInt(lineItems[3]));
                        Stream<List<String>> listStream = lineItems[4].lines().map(primaryProfessionsLine -> {
                            String[] primaryProfessions = primaryProfessionsLine.split(",");
                            List<String> strings = Arrays.asList(primaryProfessions);
                            return strings;
                        });
                        model.setPrimaryProfession((List<String>) listStream);


                        return model;
                    }).collect(Collectors.toList());*/
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace(System.err);
        }
        catch (IOException e)
        {
            e.printStackTrace(System.err);
        }
        return lines;
    }

    public static List<String> readOnlineGZ() throws IOException
    {
        List<String> lines = new ArrayList<>();
        URL url = new URL("https://datasets.imdbws.com/name.basics.tsv.gz");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("User-Agent",
                                             "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
        httpURLConnection.setDoInput(true);


        try (GZIPInputStream gzip = new GZIPInputStream(httpURLConnection.getInputStream());
             BufferedReader br = new BufferedReader(new InputStreamReader(gzip));)
        {
            String line = null;
            while ((line = br.readLine()) != null)
            {
                //lines.add(line);
                System.out.println(line);
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace(System.err);
        }
        catch (IOException e)
        {
            e.printStackTrace(System.err);
        }

        httpURLConnection.disconnect();

        return lines;
    }


    @PreDestroy
    public void preDestroy() throws IOException
    {
        //Delete folder before bean destroy.
        FileUtils.deleteDirectory(new File("C:\\tempDb"));
    }
}
