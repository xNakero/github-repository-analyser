package com.example.githubrepositoryanalyser.exceptions;

public class TooManyRequestsException extends RuntimeException{

    public TooManyRequestsException() {
        super("Too many requests to GitHub API for this hour");
    }
}
