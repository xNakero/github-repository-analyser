package com.example.githubrepositoryanalyser.service;

import com.example.githubrepositoryanalyser.dto.UserReposNumberDto;
import com.example.githubrepositoryanalyser.exceptions.TooManyRequestsException;
import com.example.githubrepositoryanalyser.exceptions.UserNotFoundException;
import com.example.githubrepositoryanalyser.model.GithubRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GithubRepositoryRestClient {

    private final WebClient webClient;
    private final int MAX_PAGE_SIZE = 85;

    public GithubRepositoryRestClient() {
        this.webClient = WebClient.create();
    }

    public int getSumOfStars(String user) {
        List<GithubRepository> repos = new ArrayList<>(getReposPage(user, 1, MAX_PAGE_SIZE));
        if (repos.size() == MAX_PAGE_SIZE) {
            int numOfRepos = getNumPublicRepos(user);
            if (numOfRepos != MAX_PAGE_SIZE) {
                int numOfPages = ((numOfRepos - 1) / MAX_PAGE_SIZE) + 1;
                for (int i = 2; i <= numOfPages; i++) {
                    repos.addAll(getReposPage(user, i, MAX_PAGE_SIZE));
                }
            }
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

    public int getNumPublicRepos(String user) {
        return Objects.requireNonNull(webClient.get()
                .uri("https://api.github.com/users/" + user)
                .retrieve()
                .onStatus(
                        HttpStatus.NOT_FOUND::equals,
                        r -> Mono.error(new UserNotFoundException(user)))
                .onStatus(HttpStatus.FORBIDDEN::equals,
                        response -> Mono.error(new TooManyRequestsException()))
                .bodyToMono(UserReposNumberDto.class)
                .block())
                .getRepos();
    }
}
