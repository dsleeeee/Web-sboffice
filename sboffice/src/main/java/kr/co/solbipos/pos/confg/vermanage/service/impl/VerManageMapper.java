package kr.co.solbipos.pos.confg.vermanage.service.impl;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.pos.confg.vermanage.service.ApplcStoreVO;
import kr.co.solbipos.pos.confg.vermanage.service.VerInfoVO;

/**
* @Class Name : VerManageMapper.java
* @Description : 포스관리 > POS 설정관리 > POS 버전 관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  김지은      최초생성
*
* @author 솔비포스 차세대개발실 김지은
* @since 2018. 05.01
* @version 1.0
* @see
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Mapper
public interface VerManageMapper {

    /**
     * 포스버전 목록 조회
     * 
     * @param verInfo
     * @return
     */
    List<DefaultMap<String>> getList(VerInfoVO verInfo);

    /**
     * 포스버전정보 상세 조회
     * 
     * @param verInfo
     * @return
     */
    DefaultMap<String> dtlInfo(VerInfoVO verInfo);

    /**
     * 버전 삭제
     * 
     * @param verInfo
     * @return
     */
    int verDelete(VerInfoVO verInfo);

    /**
     * 등록 매장 목록
     * 
     * @param verInfo
     * @return
     */
    List<DefaultMap<String>> storeList(VerInfoVO verInfo);

    /**
     * 매장추가 매장검색
     * 
     * @param applcStore
     * @return
     */
    List<DefaultMap<String>> srchStoreList(ApplcStoreVO applcStore);

    /**
     * 버전 적용 매장 등록
     * 
     * @param applcStore
     * @return
     */
    int registStore(ApplcStoreVO applcStore);
    
    /**
     * 일련번호 중복 체크
     * 
     * @param verInfo
     * @return
     */
    int chkVerSerNo(VerInfoVO verInfo);
    
    /**
     * 버전 등록 
     * 
     * @param verInfo
     */
    int verRegist(VerInfoVO verInfo);

    /**
     * 버전 수정
     * 
     * @param verInfo
     * @return
     */
    int verModify(VerInfoVO verInfo);

   
}
