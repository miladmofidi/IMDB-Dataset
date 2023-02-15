package com.example.loboximdb.controller;

import com.example.loboximdb.domain.ImdbDto;
import com.example.loboximdb.domain.ImdbEntity;
import com.example.loboximdb.service.ImdbService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/14/2023
 */

/*This Controller was generated just for demonstration of the data insert and read process.*/
@RestController
@RequestMapping(value = "/api/imdb")
public class ImdbController
{
    private static final Logger LOGGER = LogManager.getLogger(ImdbController.class);
    @Autowired
    private ImdbService imdbService;

    @PostMapping("/save")
    public ResponseEntity<ImdbDto> saveImdb(@RequestBody ImdbDto input)
    {
        LOGGER.debug("REST request to save IMDB: {}", input);
        ImdbDto result = imdbService.insertOrUpdateImdb(input);
        if (result != null)
        {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ImdbDto>> getAllImdbs()
    {
        LOGGER.debug("REST request to get all IMDBs");
        List<ImdbDto> result = imdbService.findAllImdbs();
        if (result != null)
        {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/getCountOfAll")
    public ResponseEntity<Long> getCountOfAll()
    {
        long count = imdbService.countOfAllImdbs();
        if (count != 0)
        {
            return new ResponseEntity<>(count, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/getByNconst/{nconst}")
    public ResponseEntity<ImdbDto> getImdbByNconst(@PathVariable String nconst)
    {
        LOGGER.debug("REST request to get IMDB with 'nconst': {}", nconst);
        ImdbDto result = imdbService.findByNconst(nconst);
        if (result != null)
        {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

