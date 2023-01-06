package com.example.loboximdb.service;

import com.example.loboximdb.model.ImdbEntity;
import com.example.loboximdb.repository.ImdbRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author milad.mofidi@gmail.com
 */
@Service
public class ImdbServiceImpl implements ImdbService
{
    @Resource
    private ImdbRepository repository;

    public ImdbEntity createOrUpdateImdb(ImdbEntity entity){
        Optional<ImdbEntity> imdb = repository.findById(entity.getNconst());
        if (imdb.isPresent()){
            ImdbEntity imdbEntity = imdb.get();
            imdbEntity.setBirthYear(entity.getPrimaryName());
            imdbEntity.setDeathYear(entity.getDeathYear());
            imdbEntity.setBirthYear(entity.getBirthYear());
            imdbEntity.setKnownForTitles(entity.getKnownForTitles());
            imdbEntity.setPrimaryProfession(entity.getPrimaryProfession());
            imdbEntity = repository.save(imdbEntity);
            return imdbEntity;
        } else{
            entity = repository.save(entity);
            return entity;
        }
    }

}
