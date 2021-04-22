package com.example.githubrepositoryanalyser.controller;

import com.example.githubrepositoryanalyser.model.GithubRepository;
import com.example.githubrepositoryanalyser.dto.StargazersCountDto;
import com.example.githubrepositoryanalyser.service.GithubRepositoryRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class GithubRepositoryController {

    private final GithubRepositoryRestClient githubRepositoryRestClient;

    @Autowired
    public GithubRepositoryController(GithubRepositoryRestClient githubRepositoryRestClient) {
        this.githubRepositoryRestClient = githubRepositoryRestClient;
    }

    @GetMapping("{user}/repos")
    public ResponseEntity<List<GithubRepository>> getRepos(@PathVariable String user) {
        return new ResponseEntity<>(githubRepositoryRestClient.getRepos(user), HttpStatus.OK);
    }

    @GetMapping("{user}/stargazers")
    public ResponseEntity<StargazersCountDto> getStargazersSum(@PathVariable String user) {
        int stargazersCount = githubRepositoryRestClient.getSumOfStargazers(user);
        StargazersCountDto stargazersCountDto = new StargazersCountDto(stargazersCount);
        return new ResponseEntity<>(stargazersCountDto, HttpStatus.OK);
    }

}
