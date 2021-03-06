package com.example.githubrepositoryanalyser.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GithubRepository {

    @JsonProperty("name")
    private String name;
    @JsonAlias("stargazers_count")
    @JsonProperty("stars")
    private int stars;
}
