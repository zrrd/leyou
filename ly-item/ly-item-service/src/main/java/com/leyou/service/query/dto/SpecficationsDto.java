package com.leyou.service.query.dto;

import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate.Param;
import java.util.List;

/**
 * 规格.
 *
 * @author shaoyijiong
 * @date 2018/12/11
 */
public class SpecficationsDto {

  String group;
  List<SpecParamsDto> params;
}
