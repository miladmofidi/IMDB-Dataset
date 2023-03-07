package com.example.loboximdb.controller;

import com.example.loboximdb.domain.dto.NameDTO;
import com.example.loboximdb.domain.dto.TitleDTO;
import com.example.loboximdb.exception.RecordNotFoundException;
import com.example.loboximdb.service.TitleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/14/2023
 */

/*This Controller was generated just for demonstration of the data insert and read process.*/
@RestController
@RequestMapping(value = "/api/imdb/title")
public class TitleController
{
    private static final Logger LOGGER = LogManager.getLogger(TitleController.class);
    @Resource
    private TitleService titleService;

    @PostMapping
    public ResponseEntity<TitleDTO> save(@RequestBody TitleDTO input)
    {
        LOGGER.debug("REST request to save Title: {}", input);
        TitleDTO result = titleService.insertOrUpdateImdb(input);
        if (result != null)
        {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping
    public ResponseEntity<Page> getAll(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "tconst") String sortColumn,
                                       @RequestParam(defaultValue = "asc") String sortOrder)
    {
        Sort sort =
                sortOrder.equalsIgnoreCase("desc") ? Sort.by(sortColumn).descending() : Sort.by(sortColumn).ascending();
        Pageable paging = PageRequest.of(page, size, sort);
        Page<TitleDTO> result;


        LOGGER.debug("REST request to get all Titles");
        result = titleService.findAll(paging);
        if (result != null)
        {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        else
        {
            throw new RecordNotFoundException("No Title item found");
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCountOfAll()
    {
        LOGGER.debug("REST request to get count all of Titles");
        long count = titleService.countOfAllImdbs();
        if (count != 0)
        {
            return new ResponseEntity<>(count, HttpStatus.OK);
        }
        else
        {
            throw new RecordNotFoundException("No Title item found");
        }
    }

    @GetMapping("/tconst/{tconst}")
    public ResponseEntity<TitleDTO> getByTconst(@PathVariable String tconst)
    {
        LOGGER.debug("REST request to get Title by tconst: {}", tconst);
        TitleDTO result = titleService.findByTconst(tconst);
        if (result != null)
        {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        else
        {
            throw new RecordNotFoundException("Invalid tconst: " + tconst);
        }
    }


}

