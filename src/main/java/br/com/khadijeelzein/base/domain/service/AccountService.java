package br.com.khadijeelzein.base.domain.service;

import br.com.khadijeelzein.base.domain.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@Service
@Data

public class AccountService {
    @Value("${backend.uri}")
    private String uri;
    private boolean isSuccess;
    private AccountBalanceResponse accountBalanceResponse;
    private AccountNbrResponse accountNbrResponse;
    private CustomErrorResponse customErrorResponse;

    public void save(AccountRequest data) {
        RestTemplate restTemplate = new RestTemplate();
        String url = uri + "/accounts";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AccountRequest> request = new HttpEntity<>(data, headers);
        var response = restTemplate.exchange(url, HttpMethod.POST, request, AccountNbrResponse.class);
        if(response.getStatusCode().equals(HttpStatus.CREATED)){
            isSuccess = true;
            this.accountNbrResponse=response.getBody();
        }
    }

    public void searchAccountBalance(Long accountNbr) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = uri + "/accounts/{id}/balance";
        var uriString = UriComponentsBuilder.fromUriString(url)
                .build(accountNbr).toString();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        var httpEntity = new HttpEntity<String>(headers);
        var obj = restTemplate.getForObject(uriString, Object.class, httpEntity);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(obj);
        var account = objectMapper.readValue(json, AccountBalanceResponse.class);
        accountBalanceResponse = account;
    }
}