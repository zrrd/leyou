package com.leyou.service.query.mapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyou.service.query.dto.BrandQueryDto;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌相关mapper.
 *
 * @author shaoyijiong
 * @date 2018/11/28
 */
public interface BrandQueryMapper {

  IPage<BrandQueryDto> queryBrandByPage(Page page, @Param("key") String key);
}
