package kr.co.solbipos.sale.prod.saleDtlChannel.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.prod.saleDtlChannel.service.SaleDtlChannelVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SaleDtlChannelMapper.java
 * @Description : 맘스터치 > 상품매출분석 > 매출상세현황(채널별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.28   권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.12.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SaleDtlChannelMapper {

    /** 매출상세현황(채널별) 조회 */
    List<DefaultMap<String>> getSaleDtlChannelList(SaleDtlChannelVO saleDtlChannelVO);

    /** 매출상세현황(채널별) 조회(엑셀용) */
//    List<DefaultMap<String>> getSaleDtlChannelExcelList(SaleDtlChannelVO saleDtlChannelVO);

    /** 매출상세현황(채널별) 매출 다운로드 탭 - 조회 */
    List<DefaultMap<Object>> getSaleDtlChannelExcelList(SaleDtlChannelVO saleDtlChannelVO);

    /** 매출상세현황(채널별) 매출 다운로드 탭 - 자료생성 저장 insert */
    int getSaleDtlChannelSaveInsert(SaleDtlChannelVO saleDtlChannelVO);

    /** 매출상세현황(채널별) 매출 다운로드 탭 - 삭제 */
    int getSaleDtlChannelDel(SaleDtlChannelVO saleDtlChannelVO);

    /** 매출상세현황(채널별) 매출 다운로드 탭 - 자료생성 요청건 존재여부 확인 */
//    DefaultMap<String> getSaleDtlChannelChk(SaleDtlChannelVO saleDtlChannelVO);
}
