package br.com.khadijeelzein.base.domain.service;

import br.com.khadijeelzein.base.domain.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class AccountMovementService {
    @Value("${backend.uri}")
    private String uri;
    private CustomErrorResponse customErrorResponse;
    public void save(AccountMovementRequest data) {
        RestTemplate restTemplate = new RestTemplate();
        String url = uri + "/transactions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AccountMovementRequest> request = new HttpEntity<>(data, headers);
        var response = restTemplate.exchange(url, HttpMethod.POST,request, String.class);
        if(!response.getStatusCode().equals(HttpStatus.CREATED)){
            customErrorResponse = new CustomErrorResponse((HttpStatus) response.getStatusCode(),
                    response.getBody());
        }
    }
    public List<AccountMovementResponse> searchAccountMovementHistory(AccountMovementHistoryRequest data, Pageable pageable) {
        RestTemplate restTemplate = new RestTemplate();
        var uriString = uri + "/accounts/{id}/transactions";
        var uri = UriComponentsBuilder.fromUriString(uriString)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .queryParam("sort", pageable.getSort())
                .queryParam("startDate",data.getStartDate())
                .queryParam("endDate",data.getEndDate())
                .build(data.getId()).toString();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        var httpEntity = new HttpEntity<String>(headers);
        var response  = restTemplate.getForObject(uri, RestResponsePage.class,httpEntity);
        if(response!=null){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.convertValue(
                    response.getContent(),
                    new TypeReference<List<AccountMovementResponse>>() { });
        }
        return new ArrayList<>();
    }
}