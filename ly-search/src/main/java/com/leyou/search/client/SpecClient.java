package com.leyou.search.client;

import com.leyou.common.service.api.BrandApi;
import com.leyou.common.service.api.SpecApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author shaoyijiong
 * @date 2018/12/27
 */
@FeignClient("item-service")
public interface SpecClient extends SpecApi {

}
