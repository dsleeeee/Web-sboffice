package kr.co.solbipos.base.store.myinfo.service.impl;

import kr.co.solbipos.base.store.myinfo.service.MyInfoVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqNmcodeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class Name : MyInfoMapper.java
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
@Mapper
public interface MyInfoMapper{
    List< HqNmcodeVO > findAllHqNmcodeByHqOfficeCdAndNmcodeGrpCd(HqNmcodeVO hqNmcode);

    MyInfoVO findById(String id);

    HqNmcodeVO findHqNmcodeByHqOfficeCdAndNmcodeGrpCdAndNmcodeCd(HqNmcodeVO hqNmcode);

    //int countHqNmcode( HqNmcodeVO hqNmcode );

    void saveHqNmcodeFromCmNmcode(HqNmcodeVO hqNmcode);

    int updateHqOffice(MyInfoVO myInfo);

    int insertHqNmcode(HqNmcodeVO hqNmcode);

    int updateHqNmcode(HqNmcodeVO hqNmcode);

    int deleteHqNmcode(HqNmcodeVO hqNmcode);
}
