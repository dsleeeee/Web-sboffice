package kr.co.solbipos.base.store.posfunc.service.impl;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.posfunc.service.PosFuncVO;

/**
 * @Class Name : PosFuncMapper.java
 * @Description : 기초관리 > 매장관리 > 포스기능정의
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.07.26  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 06.26
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
public interface PosFuncMapper {

    /** 포스목록 조회 */
    List<DefaultMap<String>> getPosList(PosFuncVO posFuncVO);

    /** 포스기능목록 조회 */
    List<DefaultMap<String>> getPosFuncList(PosFuncVO posFuncVO);

    /** 포스기능상세 조회 */
    List<DefaultMap<String>> getPosConfDetail(PosFuncVO posFuncVO);

    /** 포스기능상세 저장 */
    int savePosConfDetail(PosFuncVO posFuncVO);

    /** 포스기능 복사 */
    int copyPosFunc(PosFuncVO posFuncVO);

    /** 포스기능 인증목록 조회 */
    List<DefaultMap<String>> getPosConfAuthDetail(PosFuncVO posFuncVO);

    /** 포스기능 인증허용대상 조회 */
    List<DefaultMap<String>> getAuthEmpList(PosFuncVO posFuncVO);

    /** 포스기능 인증허용대상 저장 */
    int saveAuthEmp(PosFuncVO posFuncVO);
}
