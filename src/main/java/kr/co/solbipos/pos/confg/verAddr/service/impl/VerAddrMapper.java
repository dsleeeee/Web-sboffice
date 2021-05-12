package kr.co.solbipos.pos.confg.verAddr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.pos.confg.verAddr.service.AddrApplcStoreVO;
import kr.co.solbipos.pos.confg.verAddr.service.AddrVerInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @Class Name : VerAddrMapper.java
* @Description : 포스관리 > POS 설정관리 > 주소 버전 관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2021.05.10  권지현      최초생성
*
* @author 솔비포스 개발본부 WEB개발팀 권지현
* @since 2021.05.10
* @version 1.0
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Mapper
@Repository
public interface VerAddrMapper {

    /** 포스버전 목록 조회 */
    List<DefaultMap<String>> getList(AddrVerInfoVO verInfo);

    /** 포스버전정보 상세 조회 */
    DefaultMap<String> dtlInfo(AddrVerInfoVO verInfo);

    /** 버전 삭제 */
    int verDelete(AddrVerInfoVO verInfo);

    /** 등록 매장 목록 */
    List<DefaultMap<String>> storeList(AddrVerInfoVO verInfo);

    /** 일련번호 중복 체크 */
    int chkVerSerNo(AddrVerInfoVO verInfo);

    /** 버전 등록 */
    int verRegist(AddrVerInfoVO verInfo);

    /** 버전 수정 */
    int verModify(AddrVerInfoVO verInfo);

    /** 추가가능한 매장 목록 조회  */
    List<DefaultMap<String>> srchStoreList(AddrApplcStoreVO applcStore);

    /** 버전 적용 매장 등록 */
    String registStore(AddrApplcStoreVO applcStore);

    /** 버전 적용 매장 삭제 */
    int removeStore(AddrApplcStoreVO applcStore);
}
