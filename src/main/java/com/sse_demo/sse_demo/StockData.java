package com.sse_demo.sse_demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockData {

    @JsonProperty("credit_rating")
    private int credit_rating;
}
