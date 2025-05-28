package br.com.khadijeelzein.base.domain.service;

import br.com.khadijeelzein.base.domain.model.ClientRequest;
import br.com.khadijeelzein.base.domain.model.CustomErrorResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Data
public class ClientService {
    @Value("${backend.uri}")
    private String uri;
    private boolean isSuccess;
    public void save(ClientRequest data) {
        RestTemplate restTemplate = new RestTemplate();
        String url = uri+"/clients";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ClientRequest> request = new HttpEntity<>(data, headers);
        var response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        if(response.getStatusCode().equals(HttpStatus.CREATED)){
            isSuccess = true;
        }
    }
}
