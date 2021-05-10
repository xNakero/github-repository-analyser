package com.example.githubrepositoryanalyser.service;

import com.example.githubrepositoryanalyser.exceptions.TooManyRequestsException;
import com.example.githubrepositoryanalyser.exceptions.UserNotFoundException;
import com.example.githubrepositoryanalyser.model.GithubRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class GithubRepositoryRestClient {

    private final WebClient webClient;
    private final int MAX_PAGE_SIZE = 100;

    public GithubRepositoryRestClient() {
        this.webClient = WebClient.create();
    }

    public int getSumOfStars(String user) {
        List<GithubRepository> repos = new ArrayList<>();
        boolean runs = true;
        int page = 1;
        while (runs) {
            repos.addAll(getReposPage(user, page, MAX_PAGE_SIZE));
            if (repos.size() % MAX_PAGE_SIZE != 0) {
                runs = false;
            }
            page++;
        }
        return repos.stream()
                .mapToInt(GithubRepository::getStars)
                .sum();
    }

    public List<GithubRepository> getReposPage(String user, int page, int perPage) {
        String uri = "https://api.github.com/users/" + user + "/repos?page=" + page + "&per_page=" + perPage;
        return webClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(
                        HttpStatus.NOT_FOUND::equals,
                        response -> Mono.error(new UserNotFoundException(user)))
                .onStatus(HttpStatus.FORBIDDEN::equals,
                        response -> Mono.error(new TooManyRequestsException()))
                .bodyToFlux(GithubRepository.class)
                .collectList()
                .block();
    }

}
