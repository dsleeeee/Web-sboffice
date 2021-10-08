package kr.co.solbipos.base.store.myinfo.service;

import kr.co.common.data.enums.Status;
import kr.co.solbipos.base.store.myinfo.enums.NmcodeGrpCd;
import kr.co.solbipos.store.hq.hqmanage.service.HqNmcodeVO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * @Class Name : MyInfoService.java
 * @Description : 기초관리 > 매장관리 > 내정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.07.27  이호원      최초생성
 *
 * @author NHN한국사이버결제 이호원
 * @since 2018.07.27
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MyInfoService{

    /**
     * 본사 명칭코드 Grid 정보 가져오기
     * @param nmcodeGrpCd 명칭코드그룹코드
     * @return 명칭코드 Wijmo Grid
     */
    WijmoGridVO< HqNmcodeVO > getGridInfo(NmcodeGrpCd nmcodeGrpCd);

    /**
     * 본사 명칭코드 목록
     * @param nmcodeGrpCd 명칭코드그룹코드
     * @return 명칭코드
     */
    List< HqNmcodeVO > nmcodeList(NmcodeGrpCd nmcodeGrpCd);

    /**
     * 본사 명칭코드 등록
     * @param hqNmcode 명칭코드
     * @return 성공여부
     */
    Status addNmcode(HqNmcodeVO hqNmcode);

    /**
     * 본사 명칭코드 수정
     * @param hqNmcode 명칭코드
     * @return 성공여부
     */
    Status modifyNmcode(HqNmcodeVO hqNmcode);
    /**
     * 본사 명칭코드 삭제
     * @param hqNmcode 명칭코드
     * @return 성공여부
     */
    Status removeNmcode(HqNmcodeVO hqNmcode);

    /**
     * 본사 명칭코드 저장 ( 등록, 수정, 삭제 )
     * @param addList 등록 명칭코드
     * @param modList 수정 명칭코드
     * @param delList 삭제 명칭코드
     * @param nmcodeGrpCd 명칭코드그룹코드
     * @return 성공여부
     */
    Status saveMultipleNmcode(List<HqNmcodeVO> addList, List<HqNmcodeVO> modList,
        List<HqNmcodeVO> delList, NmcodeGrpCd nmcodeGrpCd);

    /**
     * 본사 정보
     * @return 본사 정보
     */
    MyInfoVO getMyInfo();

    /**
     * 본사 수정
     * @param myInfo 본사 정보
     * @return 성공여부
     */
    Status modifyMyInfo(MyInfoVO myInfo);

    /** 상단로고이미지 - 이미지저장 */
    String saveTitleImg(MultipartHttpServletRequest multi);

}
