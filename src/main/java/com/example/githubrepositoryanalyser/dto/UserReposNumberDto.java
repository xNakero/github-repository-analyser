package com.example.githubrepositoryanalyser.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserReposNumberDto {

    @JsonProperty("repos")
    @JsonAlias("public_repos")
    private int repos;
}
