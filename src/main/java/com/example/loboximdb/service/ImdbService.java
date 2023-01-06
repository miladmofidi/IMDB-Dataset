package com.example.loboximdb.service;

import com.example.loboximdb.model.ImdbEntity;
/**
 * @author milad.mofidi@gmail.com
 */
public interface ImdbService
{
    ImdbEntity createOrUpdateImdb(ImdbEntity entity);
}
