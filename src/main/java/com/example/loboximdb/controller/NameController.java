package com.example.loboximdb.controller;

import com.example.loboximdb.exception.RecordNotFoundException;
import com.example.loboximdb.domain.dto.NameDTO;
import com.example.loboximdb.service.NameService;
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
@RequestMapping(value = "/api/imdb/name")
public class NameController
{
    private static final Logger LOGGER = LogManager.getLogger(NameController.class);
    @Resource
    private NameService nameService;

    @PostMapping
    public ResponseEntity<NameDTO> save(@RequestBody NameDTO input)
    {
        LOGGER.debug("REST request to save IMDB: {}", input);
        NameDTO result = nameService.insertOrUpdateImdb(input);
        if (result != null)
        {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping
    public ResponseEntity<Page> getAll(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "nconst") String sortColumn,
                                       @RequestParam(defaultValue = "asc") String sortOrder)
    {
        Sort sort =
                sortOrder.equalsIgnoreCase("desc") ? Sort.by(sortColumn).descending() : Sort.by(sortColumn).ascending();
        Pageable paging = PageRequest.of(page, size, sort);
        Page<NameDTO> result;


        LOGGER.debug("REST request to get all Names");
        result = nameService.findAll(paging);
        if (result != null)
        {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        else
        {
            throw new RecordNotFoundException("No Name item found");
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCountOfAll()
    {
        LOGGER.debug("REST request to get count of all Names");
        long count = nameService.countOfAllImdbs();
        if (count != 0)
        {
            return new ResponseEntity<>(count, HttpStatus.OK);
        }
        else
        {
            throw new RecordNotFoundException("No Name item found");
        }
    }

    @GetMapping("/getByNconst/{nconst}")
    public ResponseEntity<NameDTO> getImdbByNconst(@PathVariable String nconst)
    {
        LOGGER.debug("REST request to get Name with nconst: {}", nconst);
        NameDTO result = nameService.findByNconst(nconst);
        if (result != null)
        {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        else
        {
            throw new RecordNotFoundException("Invalid nconst: " + nconst);
        }
    }

    @GetMapping("/getByPrimaryName/{primaryName}")
    public ResponseEntity<NameDTO> getImdbByPrimaryName(@PathVariable String primaryName)
    {
        LOGGER.debug("REST request to get Name with primaryName: {}", primaryName);
        NameDTO result = nameService.findByPrimaryName(primaryName);
        if (result != null)
        {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        else
        {
            throw new RecordNotFoundException("Invalid primaryName: " + primaryName);
        }
    }
}

