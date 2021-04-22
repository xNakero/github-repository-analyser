package com.example.githubrepositoryanalyser.service;

import com.example.githubrepositoryanalyser.model.GithubRepository;
import com.example.githubrepositoryanalyser.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GithubRepositoryRestClient {

    private final WebClient webClient;

    public GithubRepositoryRestClient() {
        this.webClient = WebClient.create();
    }

    public List<GithubRepository> getRepos(String user) {
        String uri = "https://api.github.com/users/" + user + "/repos";
        return webClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(
                        HttpStatus.NOT_FOUND::equals,
                        response -> Mono.error(new UserNotFoundException(user)))
                .bodyToFlux(GithubRepository.class)
                .collectList()
                .block();
    }

    public int getSumOfStargazers(String user) {
        List<GithubRepository> repos = getRepos(user);
        return repos.stream()
                .mapToInt(GithubRepository::getStargazersCount)
                .sum();
    }

}
