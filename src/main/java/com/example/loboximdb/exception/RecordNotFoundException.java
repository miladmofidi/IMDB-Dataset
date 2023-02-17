package com.example.loboximdb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/17/2023
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException
{
    public RecordNotFoundException(String message)
    {
        super(message);
    }

    public RecordNotFoundException()
    {
        super("No records found");
    }
}
