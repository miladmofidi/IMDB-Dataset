package com.example.loboximdb.exception;

import java.util.List;

/**
 * @author milad mofidi
 * email: milad.mofidi@gmail.com
 * user: miladm on 2/17/2023
 */
public class ErrorResponse
{
    public ErrorResponse(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }

    private String message;

    private List<String> details;

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public List<String> getDetails()
    {
        return details;
    }

    public void setDetails(List<String> details)
    {
        this.details = details;
    }
}
