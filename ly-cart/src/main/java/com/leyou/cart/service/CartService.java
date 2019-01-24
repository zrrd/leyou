package com.leyou.cart.service;

import com.leyou.auth.entiy.UserInfo;
import com.leyou.cart.client.GoodsClient;
import com.leyou.cart.interceptor.LoginInterceptor;
import com.leyou.cart.pojo.domain.Cart;
import com.leyou.common.utils.JsonUtils;
import com.leyou.service.pojo.domain.Sku;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author shaoyijiong
 * @date 2019/1/24
 */
@Slf4j
@Service
public class CartService {

  private final RedisTemplate<String, Object> redisTemplate;

  private static final String KEY_PREFIX = "ly:cart:uid:";
  private final GoodsClient goodsClient;

  @Autowired
  public CartService(RedisTemplate<String, Object> redisTemplate, GoodsClient goodsClient) {
    this.redisTemplate = redisTemplate;
    this.goodsClient = goodsClient;
  }

  public void addCart(Cart cart) {
    UserInfo userInfo = LoginInterceptor.getLoginUser();
    String key = KEY_PREFIX + userInfo.getId();
    BoundHashOperations hashOps = redisTemplate.boundHashOps(key);
    Long skuId = cart.getSkuId();
    Integer num = cart.getNum();
    if (hashOps.hasKey(skuId.toString())) {
      // 存在，获取购物车数据
      String json = hashOps.get(skuId.toString()).toString();
      cart = JsonUtils.parse(json, Cart.class);
      // 修改购物车数量
      cart.setNum(cart.getNum() + num);
    } else {
      // 不存在，新增购物车数据
      cart.setUserId(userInfo.getId());
      // 其它商品信息， 需要查询商品服务
      Sku sku = goodsClient.querySkuById(skuId);
      cart.setImage(
          StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
      cart.setPrice(sku.getPrice());
      cart.setTitle(sku.getTitle());
      cart.setOwnSpec(sku.getOwnSpec());
    }
  }
}
