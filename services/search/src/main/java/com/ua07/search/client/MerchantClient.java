package com.ua07.search.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "merchant-service", url = "${merchant.service.url}")
public interface MerchantClient {
}
