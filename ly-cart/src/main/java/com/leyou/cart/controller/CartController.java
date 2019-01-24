package com.leyou.cart.controller;

import com.leyou.cart.pojo.domain.Cart;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shaoyijiong
 * @date 2019/1/24
 */
@RestController
public class CartController {

  @PostMapping
  public ResponseEntity<Void> addChart(@RequestBody Cart cart) {
    return null;
  }
}
