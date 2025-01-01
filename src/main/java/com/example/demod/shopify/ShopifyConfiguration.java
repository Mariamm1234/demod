package com.example.demod.shopify;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Configuration
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyConfiguration {

    // @Value("${shopify.api.key}")
    private String apiKey="6331492cdc3ea5329d95c0291ecede89";

    // @Value("${shopify.api.secret}")
    private String apiSecret="8e3625386bd272244def1b42309c155a";

    @Value("${shopify.access.token}")
    private String accessToken;

    @Value("${shopify.api.base-url}")
    private String baseUrl;

}
