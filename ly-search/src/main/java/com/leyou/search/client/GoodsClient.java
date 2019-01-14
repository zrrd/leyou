package com.leyou.search.client;

import com.leyou.service.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author shaoyijiong
 * @date 2018/12/27
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {

}
