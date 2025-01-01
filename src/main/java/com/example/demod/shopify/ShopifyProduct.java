package com.example.demod.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShopifyProduct {
    @JsonProperty("product")
    private Products product;

    @Getter
    @Setter
    public static class Products {

        @JsonProperty("title")
        private String title;

        @JsonProperty("body_html")
        private String bodyHtml;

        @JsonProperty("vendor")
        private String vendor;

        @JsonProperty("product_type")
        private String productType;

        @JsonProperty("tags")
        private List<String> tags;

        @JsonProperty("variants")
        private List<Variant> variants;

        @Getter
        @Setter
        public static class Variant {

            @JsonProperty("option1")
            private String option1;

            @JsonProperty("price")
            private String price;

            @JsonProperty("sku")
            private String sku;

        }
    }
}
