package kr.co.solbipos.dlvr.anals.dlvrInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.dlvr.anals.dlvrInfo.service.DlvrInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name : DayDlvrMapper.java
 * @Description : 배달관리 > 배달분석 > 배달내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.07.09  Joshua      최초생성
 *
 * @author
 * @since 2020.07.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface DlvrInfoMapper {
  /** 배달내역조회 */
  public List<DefaultMap<Object>> getDlvrInfoList(DlvrInfoVO dlvrInfoVO);

  /** 영수증 상세 조회 */
  DefaultMap<String> getBillInfo(DlvrInfoVO dlvrInfoVO);

  /** 영수증 상세 조회 */
  List<DefaultMap<Object>> getBillInfoList(DlvrInfoVO dlvrInfoVO);
}
