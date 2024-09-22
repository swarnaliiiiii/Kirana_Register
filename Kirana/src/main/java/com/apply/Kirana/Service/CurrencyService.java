package com.apply.Kirana.Service;

import com.apply.Kirana.Model.CurrencyResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CurrencyService {


    private final String apiUrl = "https://api.fxratesapi.com/latest";

    public double getExchangeRate(String currency) {
        RestTemplate restTemplate = new RestTemplate();
        CurrencyResponse response = restTemplate.getForObject(apiUrl, CurrencyResponse.class);
        if (response != null && response.getRates() != null) {
            return response.getRates().get(currency);
        }
        throw new RuntimeException("Failed to fetch exchange rates");
    }
}

