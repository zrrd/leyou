package com.leyou.auth.client;

import com.leyou.service.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author shaoyijiong
 * @date 2019/1/18
 */
@FeignClient(value = "user-service")
public interface UserClient extends UserApi {

}
