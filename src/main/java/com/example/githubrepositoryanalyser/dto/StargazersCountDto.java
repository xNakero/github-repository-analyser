package com.example.githubrepositoryanalyser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class StargazersCountDto {

    @JsonProperty("stargazers_sum")
    private int stargazersSum;
}
