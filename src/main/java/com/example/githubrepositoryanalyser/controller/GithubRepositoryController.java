package com.example.githubrepositoryanalyser.controller;

import com.example.githubrepositoryanalyser.dto.StarsDto;
import com.example.githubrepositoryanalyser.model.GithubRepository;
import com.example.githubrepositoryanalyser.service.GithubRepositoryRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<List<GithubRepository>> getRepos(@PathVariable String user,
                                                           @RequestParam(required = false, defaultValue = "1") int page,
                                                           @RequestParam(required = false,
                                                                   value = "per_page",
                                                                   defaultValue = "30") int perPage) {
        page = page <= 0 ? 1 : page;
        perPage = (perPage <= 0 || perPage > 100) ? 30 : perPage;
        List<GithubRepository> repos = githubRepositoryRestClient.getReposPage(user, page, perPage);
        return new ResponseEntity<>(repos, HttpStatus.OK);
    }

    @GetMapping("{user}/stars")
    public ResponseEntity<StarsDto> getStarsSum(@PathVariable String user) {
        int starsSum = githubRepositoryRestClient.getSumOfStars(user);
        return new ResponseEntity<>(new StarsDto(starsSum), HttpStatus.OK);
    }


}
