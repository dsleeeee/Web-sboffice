package kr.co.solbipos.adi.mony.accntManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Class Name : DepositMapper.java
 * @Description : 부가서비스 > 금전처리 > 계정관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.12  김지은      최초생성
 * @ 2019.08.14  이다솜      본사에서 계정 등록 시 매장에도 적용되도록 추가
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 10.12
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface DepositMapper {

    /** 계정 조회 */
    List<DefaultMap<String>> getDepositAccntList(AccntVO accntVO);

    /** 계정 생성시 계정코드 생성  */
    String getAccntCode(AccntVO accntVO);

    /** 계정 추가 */
    int insertDepositAccntList(AccntVO accntVO);

    /** 계정 수정 */
    int updateDepositAccntList(AccntVO accntVO);

    /** 계정 삭제 */
    int deleteDepositAccntList(AccntVO accntVO);

    /** 부가서비스_금전처리_계정관리 생성 시 매장적용 */
    String insertAccntToStore(AccntVO accntVO);

    /** 부가서비스_금전처리_계정관리 수정 시 매장적용 */
    String updateAccntToStore(AccntVO accntVO);

    /** 부가서비스_금전처리_계정관리 삭제 시 매장적용 */
    String deleteAccntToStore(AccntVO accntVO);

}

