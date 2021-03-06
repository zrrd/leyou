package com.leyou.goods.client;

import com.leyou.service.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author shaoyijiong
 * @date 2018/12/27
 */
@FeignClient("item-service")
public interface BrandClient extends BrandApi {

}
