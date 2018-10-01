package kr.co.solbipos.membr.info.regist.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.ObjectUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.info.grade.service.MembrClassVO;
import kr.co.solbipos.membr.info.regist.service.CreditStoreVO;
import kr.co.solbipos.membr.info.regist.service.RegistService;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class Name : RegistServiceImpl.java
 * @Description : 회원관리 > 회원정보 > 회원정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018.05.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("registService")
@Transactional
public class RegistServiceImpl implements RegistService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RegistMapper mapper;

    @Autowired
    CmmEnvUtil cmmEnvUtil;

    /** 등록매장 리스트 조회 */
    @Override
    public List<DefaultMap<String>> selectRgstrStore(SessionInfoVO sessionInfoVO) {

        // 회원정보 등록시 등록매장의 콤보박스 내용 조회
        // 본사 : 해당 본사에 소속된 매장만 조회한다.
        // 매장 : 해당 매장만 표시
        HqManageVO hqVO = new HqManageVO();

        hqVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return mapper.selectRgstrStore(hqVO);
    }

    /** 회원등급 리스트 조회 */
    @Override
    public List<DefaultMap<String>> selectMembrClassList(SessionInfoVO sessionInfoVO) {

        MembrClassVO membrClassVO = new MembrClassVO();

        membrClassVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            membrClassVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        }else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            membrClassVO.setMembrOrgnCd(sessionInfoVO.getStoreCd());
        }

        return mapper.selectMemberClassList(membrClassVO);
    }

    /** 회원정보 등록 */
    @Override
    public int insertRegistMember(RegistVO registVO) {
        return mapper.insertRegistMember(registVO);
    }

    /** 리스트에서 선택한 회원 정보 조회 */
    @Override
    public RegistVO selectMember(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        RegistVO resultVO = new RegistVO();

        // 회원 정보 조회
        resultVO = selectMember(registVO);

        // 본사 && 기본매장 관리하는 본사일 경우
        // 후불회원 적용 매장정보도 조회 필요
        String envstVal = cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025");

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ && !StringUtil.isEmpties(envstVal)){

            String currentCreditStore = mapper.getCurrentCreditStore(registVO);

            resultVO.setCreditStore(currentCreditStore);
        }

        return resultVO;
    }

    /** 회원정보 조회 */
    private RegistVO selectMember(RegistVO registVO) {
        return mapper.selectMember(registVO);
    }

    /** 회원 목록 조회 */
    @Override
    public <E> List<E> selectMembers(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        // 회원정보 조회시 해당 본사나 매장의 회원만 조회
        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            registVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return mapper.selectMembers(registVO);
    }

    /** 회원정보 수정 */
    @Override
    public int updateMember(RegistVO registVO) {
        return mapper.updateMember(registVO);
    }

    /** 회원 삭제 */
    @Override
    public int deleteMember(RegistVO registVO) {
        return mapper.deleteMember(registVO);
    }

    /** 회원 카드 등록 */
    @Override
    public int insertMembrCard(RegistVO registVO) {
        return mapper.insertMembrCard(registVO);
    }

    /** 회원 카드 수정 */
    @Override
    public int updateMembrCard(RegistVO registVO) {
        return mapper.updateMembrCard(registVO);
    }

    /** 회원등록 */
    @Override
    public int saveRegistMember(RegistVO registVO) {
        RegistVO chkR = selectMember(registVO);
        int result = 0;
        // 없는 회원이면 신규 저장
        if(ObjectUtil.isEmpty(chkR)) {
            result = insertRegistMember(registVO);
            if(result == 1 && (!StringUtil.isEmpties(registVO.getMembrCardNo()))) {
                // 회원카드 등록
                insertMembrCard(registVO);
            }
        }
        // 있는 회원이면 수정
        else {
            result = updateMember(registVO);
            if(result == 1) {
                // 회원카드 수정
                updateMembrCard(registVO);
            }
        }
        return result;
    }

    /** 후불회원 매장 등록 */
    @Override
    public int saveCreditStores(RegistVO registVO) {

        int result = 0;

        String creditStores[] = registVO.getCreditStore().split(",");

        if(creditStores.length > 0){

            // 현재 등록된 후불회원 매장
            String currentCreditStores = mapper.getCurrentCreditStore(registVO);
            String currentCreditStore[] = currentCreditStores.split(",");

LOGGER.info("currentCreditStore.length : "+ currentCreditStore.length);

            // 삭제할 매장
            List<String> delStore = new ArrayList<String>();

            if(currentCreditStore.length > 0) {

                for(int i=0; i<currentCreditStore.length; i++) {
                    boolean isChk = false;

                    String storeCd = currentCreditStore[i];

                    for(int j=0; j<creditStores.length; j++){
                        if(storeCd.equals(creditStores[j]))  {
                            isChk = true;
                        }
                    }
                    if(!isChk){
                        delStore.add(storeCd);
                    }
                }
            }

            // 삭제할 매장 삭제
            if(delStore.size() > 0) {
                for(String delStoreCd : delStore) {
                    CreditStoreVO creditStoreVO = new CreditStoreVO();

                    creditStoreVO.setHqOfficeCd(registVO.getMembrOrgnCd());
                    creditStoreVO.setMemberNo(registVO.getMembrNo());
                    creditStoreVO.setCreditStoreCd(delStoreCd);

                    result += mapper.deleteCreditStore(creditStoreVO);
                }
            }

            // 저장 또는 수정
            if(creditStores.length> 0 ){
                for(String regStoreCd : creditStores){
                    CreditStoreVO creditStoreVO = new CreditStoreVO();

                    creditStoreVO.setHqOfficeCd(registVO.getMembrOrgnCd());
                    creditStoreVO.setMemberNo(registVO.getMembrNo());
                    creditStoreVO.setCreditStoreCd(regStoreCd);
                    creditStoreVO.setRegDt(registVO.getRegDt());
                    creditStoreVO.setRegId(registVO.getRegId());
                    creditStoreVO.setModDt(registVO.getModDt());
                    creditStoreVO.setModId(registVO.getModId());

                    result += mapper.registCreditStore(creditStoreVO);
                }
            }
        }
        return result;
    }
}
