package com.example.loboximdb.service;

import com.example.loboximdb.domain.ImdbEntity;
import com.example.loboximdb.repository.ImdbRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author milad.mofidi@gmail.com
 */
@Service
public class ImdbServiceImpl implements ImdbService
{
    @Resource
    private ImdbRepository repository;

    public ImdbEntity createOrUpdateImdb(ImdbEntity entity)
    {
        Optional<ImdbEntity> imdb = repository.findById(entity.getNconst());
        if (imdb.isPresent())
        {
            ImdbEntity imdbEntity = imdb.get();
            imdbEntity.setBirthYear(entity.getPrimaryName());
            imdbEntity.setDeathYear(entity.getDeathYear());
            imdbEntity.setBirthYear(entity.getBirthYear());
            imdbEntity.setKnownForTitles(entity.getKnownForTitles());
            imdbEntity.setPrimaryProfession(entity.getPrimaryProfession());
            imdbEntity = repository.save(imdbEntity);
            return imdbEntity;
        }
        else
        {
            entity = repository.save(entity);
            return entity;
        }
    }

    public List<ImdbEntity> findAllImdbs()
    {
        List<ImdbEntity> all = repository.findAll();
        if (all != null)
        {
            return all;
        }
        return null;
    }
    public long countOfAllImdbs()
    {
        long count = repository.count();
        if (count != 0)
        {
            return count;
        }
        return 0;
    }
    public ImdbEntity findByNconst(String nconst)
    {
        ImdbEntity entity = repository.findByNconst(nconst);
        if (entity!=null){
            return entity;
        }
        return null;
    }

}
