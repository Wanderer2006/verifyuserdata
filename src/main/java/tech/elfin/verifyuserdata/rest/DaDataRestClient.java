package tech.elfin.verifyuserdata.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import tech.elfin.verifyuserdata.model.VerifyDaDataResponse;
import tech.elfin.verifyuserdata.service.UserDataService;

/**
 * Клиент REST-сервиса DaData
 */
@Component
public class DaDataRestClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDataService.class);

    @Value("${dadata.passport.url}")
    private String url;

    @Value("${dadata.apiKey}")
    private String apiKey;

    @Value("${dadata.secretKey}")
    private String secretKey;

    @Value("${dadata.connectTimeout}")
    private int connectTimeout;

    @Value("${dadata.readTimeout}")
    private int readTimeout;

    /**
     * Проверка Серии + Номера паспорта в сервисе DaDate
     * @param passSeries - Серия паспорта
     * @param passNumber - Номер паспорта
     * @return - признак действителен/недействителен Паспорт
     */
    public boolean verifyPassport(String passSeries, String passNumber) {
        // Задаем заголовки запроса
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Token " + apiKey);
        headers.add("X-Secret", secretKey);

        // Задаем JSON-тело запроса
        String messageBody = "[ \"" + passSeries + " " + passNumber + "\" ]";
        HttpEntity<String> requestBody = new HttpEntity<>(messageBody, headers);
        LOGGER.info("HttpEntity: " + requestBody.toString());

        try {
            // Вызов REST-сервиса DaData
            RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
            ResponseEntity<VerifyDaDataResponse[]> result
                    = restTemplate.postForEntity(url, requestBody, VerifyDaDataResponse[].class);
            LOGGER.info("ResponseEntity: " + result.toString());

            // Проверяем статус HTTP-запроса
            if (result.getStatusCode() == HttpStatus.OK) {
                VerifyDaDataResponse[] verifyResultsArray = result.getBody();
                for (VerifyDaDataResponse res : verifyResultsArray) {
                    LOGGER.info("Response: " + res.toString());
                    // Статус Паспорта (0 - действителен)
                    if (res.getQc() == 0) {
                        return true;
                    }
                }
            }
        } catch (RestClientException e) {
            LOGGER.error("Error calling REST-service DaData", e);
        }

        return false;
    }


    /**
     * Переопределение таймаутов
     */
    private SimpleClientHttpRequestFactory getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();

        //Connect timeout
        clientHttpRequestFactory.setConnectTimeout(connectTimeout);

        //Read timeout
        clientHttpRequestFactory.setReadTimeout(readTimeout);
        return clientHttpRequestFactory;
    }
}
