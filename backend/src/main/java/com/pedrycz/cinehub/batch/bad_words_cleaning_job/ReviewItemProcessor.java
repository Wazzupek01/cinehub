package com.pedrycz.cinehub.batch.bad_words_cleaning_job;

import com.pedrycz.cinehub.model.dto.BadWordApiResponseDto;
import com.pedrycz.cinehub.model.entities.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewItemProcessor implements ItemProcessor<Review, Review> {

    private static final String URL_TEMPLATE = "https://api.api-ninjas.com/v1/profanityfilter?text=%s";

    private final RestTemplate restTemplate;

    @Value("${security.profanity-filter-api-key}")
    private String apiKey;

    @Override
    public Review process(Review item) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Api-Key", apiKey);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        String url = String.format(URL_TEMPLATE, item.getContent());

        ResponseEntity<BadWordApiResponseDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                BadWordApiResponseDto.class
        );

        if (response.hasBody() && response.getBody() != null) {
            if (!item.getContent().equals(response.getBody().censored())) {
                item.setContent(response.getBody().censored());
                log.info("Censored comment: {}", response.getBody().censored());
            }
        }

        return item;
    }
}
