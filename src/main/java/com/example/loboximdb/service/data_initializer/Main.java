package com.example.loboximdb.service.data_initializer;

import com.example.loboximdb.service.ImdbService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * @author milad.mofidi@gmail.com
 */
@Component
public class Main implements CommandLineRunner
{

    @Resource
    private ImdbService imdbService;


    @Override
    public void run(String... args) throws Exception
    {
        //read Gzip file from local
        DataInitializer.readLinesFromLocalGzip();

        //read Gzip file from IMDB server
        DataInitializer.readLinesFromOnlineImdbGzip();
    }



    @PreDestroy
    public void preDestroy() throws IOException
    {
        //Delete folder before bean destroy.
        FileUtils.deleteDirectory(new File("C:\\tempDb"));
    }
}
