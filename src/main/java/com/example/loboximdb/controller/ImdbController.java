package com.example.loboximdb.controller;

import com.example.loboximdb.domain.ImdbEntity;
import com.example.loboximdb.service.ImdbService;
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
@RestController
@RequestMapping(value = "/api/imdb")
public class ImdbController
{
    @Autowired
    private ImdbService imdbService;

    @PostMapping("/save")
    public ResponseEntity<ImdbEntity> saveImdb(@RequestBody ImdbEntity entity)
    {
        ImdbEntity result = imdbService.createOrUpdateImdb(entity);
        if (result != null)
        {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ImdbEntity>> getAllImdbs()
    {
        List<ImdbEntity> result = imdbService.findAllImdbs();
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
   // @GetMapping(value = "/{appId}", produces = "application/json", consumes = "application/json")
    @GetMapping("/getByNconst/{nconst}")
    public ResponseEntity<ImdbEntity> getImdbByNconst(@PathVariable String nconst)
    {
        ImdbEntity result = imdbService.findByNconst(nconst);
        if (result != null)
        {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
