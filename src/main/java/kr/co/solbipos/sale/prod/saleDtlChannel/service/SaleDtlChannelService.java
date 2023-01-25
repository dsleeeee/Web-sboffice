package kr.co.solbipos.sale.prod.saleDtlChannel.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SaleDtlChannelService.java
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
public interface SaleDtlChannelService {

    /** 매출상세현황(채널별) 조회 */
    List<DefaultMap<String>> getSaleDtlChannelList(SaleDtlChannelVO prodSaleRateMomsVO, SessionInfoVO sessionInfoVO);

//    /** 매출상세현황(채널별) 조회(엑셀용) */
//    List<DefaultMap<String>> getSaleDtlChannelExcelList(SaleDtlChannelVO prodSaleRateMomsVO, SessionInfoVO sessionInfoVO);

    /** 매출상세현황(채널별) 매출 다운로드 탭 - 조회 */
    List<DefaultMap<Object>> getSaleDtlChannelExcelList(SaleDtlChannelVO saleDtlChannelVO, SessionInfoVO sessionInfoVO);

    /** 매출상세현황(채널별) 매출 다운로드 탭 - 자료생성 저장 */
    int getSaleDtlChannelSave(SaleDtlChannelVO saleDtlChannelVO, SessionInfoVO sessionInfoVO);

    /** 매출상세현황(채널별) 매출 다운로드 탭 - 삭제 */
    int getSaleDtlChannelDel(SaleDtlChannelVO[] saleDtlChannelVOs, SessionInfoVO sessionInfoVO);

    /** 매출상세현황(채널별) 매출 다운로드 탭 - 자료생성 요청건 존재여부 확인 */
    DefaultMap<String> getSaleDtlChannelChk(SaleDtlChannelVO saleDtlChannelVO, SessionInfoVO sessionInfoVO);

}
