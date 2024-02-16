package kr.co.solbipos.store.manage.accountManage.service.impl;

import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.enums.UserStatFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.store.manage.accountManage.enums.StatChgFg;
import kr.co.solbipos.store.manage.accountManage.service.AccountManageService;
import kr.co.solbipos.store.manage.accountManage.service.AccountManageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : AccountManageServiceImpl.java
 * @Description : 기초관리 > 매장정보관리 > 계정관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.02.14  이다솜      최초생성
 *
 * @author 솔비포스 IT개발실 WEB개발팀 이다솜
 * @since 2024.02.14
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("AccountManage")
public class AccountManageServiceImpl implements AccountManageService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final AccountManageMapper accountManageMapper;

    /** Constructor Injection */
    @Autowired
    public AccountManageServiceImpl(AccountManageMapper accountManageMapper) {
        this.accountManageMapper = accountManageMapper;
    }

    /** 계정관리 - 장기미사용 계정리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getLongTermUnusedList(AccountManageVO accountManageVO, SessionInfoVO sessionInfoVO) {

        // 사용자구분 배열형으로 변경
        accountManageVO.setOrgnFg2List(accountManageVO.getOrgnFg2().split(","));
        return accountManageMapper.getLongTermUnusedList(accountManageVO);
    }

    /** 계정관리 - 장기미사용 계정리스트 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getLongTermUnusedExcelList(AccountManageVO accountManageVO, SessionInfoVO sessionInfoVO) {

        // 사용자구분 배열형으로 변경
        accountManageVO.setOrgnFg2List(accountManageVO.getOrgnFg2().split(","));
        return accountManageMapper.getLongTermUnusedExcelList(accountManageVO);
    }

    /** 계정관리 - 장기미사용 계정 상태 변경 */
    @Override
    public int saveAccountStatChg(@RequestBody AccountManageVO[] accountManageVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(AccountManageVO accountManageVO : accountManageVOs) {

            accountManageVO.setRegDt(dt);
            accountManageVO.setRegId(sessionInfoVO.getUserId());
            accountManageVO.setModDt(dt);
            accountManageVO.setModId(sessionInfoVO.getUserId());

            if(StatChgFg.DORMANCY.getCode().equals(accountManageVO.getStatChgFg())){ // 휴면처리

                // 계정 상태 변경
                accountManageVO.setUserStatFg(UserStatFg.DORMANT_ACCOUNT.getCode());
                procCnt += accountManageMapper.updateStatusWebUser(accountManageVO);

            }else if(StatChgFg.STOP_USING.getCode().equals(accountManageVO.getStatChgFg())){ // 사용중지

                // 사원정보 계정사용여부(웹사용여부) 변경
                accountManageVO.setWebUseYn(UseYn.N.getCode());
                procCnt += updateWebUseYnEmp(accountManageVO);

            }else if(StatChgFg.ALLOWED_USE.getCode().equals(accountManageVO.getStatChgFg())){ // 사용허용

                // 사원정보 계정사용여부(웹사용여부) 변경
                accountManageVO.setWebUseYn(UseYn.Y.getCode());
                procCnt += updateWebUseYnEmp(accountManageVO);

            }else if(StatChgFg.DELETE.getCode().equals(accountManageVO.getStatChgFg())){ // 계정삭제

                accountManageVO.setUseYn(UseYn.N.getCode());

                // 사원정보 사용여부 변경
                procCnt += updateUseYnEmp(accountManageVO);

                // 계정 사용여부 변경
                accountManageMapper.updateUseYnWebUser(accountManageVO);

            }else if(StatChgFg.RECOVERY.getCode().equals(accountManageVO.getStatChgFg())){ // 계정복구

                accountManageVO.setUseYn(UseYn.Y.getCode());

                // 사원정보 사용여부 변경
                procCnt += updateUseYnEmp(accountManageVO);

                // 계정 사용여부 변경
                accountManageMapper.updateUseYnWebUser(accountManageVO);

            }
        }

        return procCnt;
    }

    /**
     * 사원정보 계정사용여부(웹사용여부) 변경
     * @param accountManageVO
     * @return
     */
    public int updateWebUseYnEmp(AccountManageVO accountManageVO){

        int procCnt = 0;

       // 사용자의 권한 파악
       if(OrgnFg.MASTER.getCode().equals(accountManageVO.getOrgnFg()) || OrgnFg.AGENCY.getCode().equals(accountManageVO.getOrgnFg())){

           procCnt = accountManageMapper.updateWebUseYnCmEmp(accountManageVO);

       }else if(OrgnFg.HQ.getCode().equals(accountManageVO.getOrgnFg())){

           procCnt = accountManageMapper.updateWebUseYnHqEmp(accountManageVO);

       }else if(OrgnFg.STORE.getCode().equals(accountManageVO.getOrgnFg())){

           procCnt = accountManageMapper.updateWebUseYnStoreEmp(accountManageVO);
       }else{
       }

       return procCnt;
    }

    /**
     * 사원정보 사용여부 변경
     * @param accountManageVO
     * @return
     */
    public int updateUseYnEmp(AccountManageVO accountManageVO){

        int procCnt = 0;

       // 사용자의 권한 파악
       if(OrgnFg.MASTER.getCode().equals(accountManageVO.getOrgnFg()) || OrgnFg.AGENCY.getCode().equals(accountManageVO.getOrgnFg()) ){

           procCnt = accountManageMapper.updateUseYnCmEmp(accountManageVO);

       }else if(OrgnFg.HQ.getCode().equals(accountManageVO.getOrgnFg())){

           procCnt = accountManageMapper.updateUseYnHqEmp(accountManageVO);

       }else if(OrgnFg.STORE.getCode().equals(accountManageVO.getOrgnFg())){

           procCnt = accountManageMapper.updateUseYnStoreEmp(accountManageVO);
       }else{
       }

       return procCnt;
    }
}
