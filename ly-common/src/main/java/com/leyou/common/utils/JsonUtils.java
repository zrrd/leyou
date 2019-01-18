package com.leyou.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.internal.Nullable;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author shaoyijiong
 */
public class JsonUtils {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

  /**
   * 对象转json
   */
  @Nullable
  public static String serialize(Object obj) {
    if (obj == null) {
      return null;
    }
    if (obj.getClass() == String.class) {
      return (String) obj;
    }
    try {
      return MAPPER.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      logger.error("json序列化出错：" + obj, e);
      return null;
    }
  }

  /**
   * Json转对象
   */
  @Nullable
  public static <T> T parse(String json, Class<T> tClass) {
    try {
      return MAPPER.readValue(json, tClass);
    } catch (IOException e) {
      logger.error("json解析出错：" + json, e);
      return null;
    }
  }

  /**
   * Json转对象数组
   */
  @Nullable
  public static <E> List<E> parseList(String json, Class<E> eClass) {
    try {
      return MAPPER
          .readValue(json, MAPPER.getTypeFactory().constructCollectionType(List.class, eClass));
    } catch (IOException e) {
      logger.error("json解析出错：" + json, e);
      return null;
    }
  }

  /**
   * Json转map
   */
  @Nullable
  public static <K, V> Map<K, V> parseMap(String json, Class<K> kClass, Class<V> vClass) {
    try {
      return MAPPER
          .readValue(json, MAPPER.getTypeFactory().constructMapType(Map.class, kClass, vClass));
    } catch (IOException e) {
      logger.error("json解析出错：" + json, e);
      return null;
    }
  }

  /**
   * Json转泛型对象
   */
  @Nullable
  public static <T> T nativeRead(String json, TypeReference<T> type) {
    try {
      return MAPPER.readValue(json, type);
    } catch (IOException e) {
      logger.error("json解析出错：" + json, e);
      return null;
    }
  }
}
