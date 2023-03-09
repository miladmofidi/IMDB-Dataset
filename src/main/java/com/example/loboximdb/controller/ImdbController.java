package com.example.loboximdb.controller;

import com.example.loboximdb.domain.dto.TitleDTO;
import com.example.loboximdb.exception.RecordNotFoundException;
import com.example.loboximdb.service.ImdbService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping(value = "/api/imdb")
public class ImdbController
{
    private static final Logger LOGGER = LogManager.getLogger(ImdbController.class);

    @Resource
    private ImdbService imdbService;

    @GetMapping("/sameDrWr")
    public ResponseEntity<Page> getAll(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "nconst") String sortColumn,
                                       @RequestParam(defaultValue = "asc") String sortOrder)
    {
        Sort sort =
                sortOrder.equalsIgnoreCase("desc") ? Sort.by(sortColumn).descending() : Sort.by(sortColumn).ascending();
        Pageable paging = PageRequest.of(page, size, sort);
        Page<TitleDTO> result;


        LOGGER.debug("REST request to get all titles with same directors and writers");
        result = imdbService.getAllLiveSameDrAndWr(paging);
        if (result != null)
        {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        else
        {
            throw new RecordNotFoundException("No Imdb item found");
        }
    }
}

