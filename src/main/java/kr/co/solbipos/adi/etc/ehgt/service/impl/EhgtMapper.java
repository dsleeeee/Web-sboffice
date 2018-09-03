package kr.co.solbipos.adi.etc.ehgt.service.impl;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.etc.ehgt.service.EhgtVO;
import kr.co.solbipos.adi.etc.ehgt.service.CrncyCdVO;

/**
 * @Class Name : EhgtMapper.java
 * @Description : 부가서비스 > 환율 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.09  조병준      최초생성
 *
 * @author NHN한국사이버결제 조병준
 * @since 2018. 08.09
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
public interface EhgtMapper {

    /**
     * 본사 - 환율 관리 리스트 조회
     *
     * @param ehgtVO
     * @return
     */
    List<DefaultMap<String>> getHqEhgtListBySaleDt(EhgtVO ehgtVO);

    /**
     * 본사 - 환율 등록
     *
     * @param ehgtVO
     * @return
     */
    int insertHqEhgt(EhgtVO ehgtVO);

    /**
     * 본사 - 환율 수정
     *
     * @param ehgtVO
     * @return
     */
    int updateHqEhgt(EhgtVO ehgtVO);

    /**
     * 가맹점 - 환율 관리 리스트 조회
     *
     * @param ehgtVO
     * @return
     */
    List<DefaultMap<String>> getMsEhgtListBySaleDt(EhgtVO ehgtVO);

    /**
     * 가맹점 - 환율 등록
     *
     * @param ehgtVO
     * @return
     */
    int insertMsEhgt(EhgtVO ehgtVO);

    /**
     * 가맹점 - 환율 수정
     *
     * @param ehgtVO
     * @return
     */
    int updateMsEhgt(EhgtVO ehgtVO);

    

    /**
     * 프랜차이즈 본사/매장 공통 코드 조회
     *
     * @param crncyCdVO
     * @return
     */
    List<DefaultMap<String>> getHqCdListByGrpCd(CrncyCdVO crncyCdVO);

    /**
     * 단독매장 공통 코드 조회
     *
     * @param crncyCdVO
     * @return
     */
    List<DefaultMap<String>> getMsCdListByGrpCd(CrncyCdVO crncyCdVO);

    /**
     * 본사 공통 코드 수정
     *
     * @param crncyCdVO
     * @return
     */
    int updateHqCdUseYn(CrncyCdVO crncyCdVO);

    /**
     * 매장 공통 코드 수정
     *
     * @param crncyCdVO
     * @return
     */
    int updateMsCdUseYn(CrncyCdVO crncyCdVO);
}
