package kr.co.solbipos.membr.info.regist.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.anals.postpaid.service.PostpaidStoreVO;
import kr.co.solbipos.membr.info.grade.service.MembrClassVO;
import kr.co.solbipos.membr.info.regist.service.MemberMappingVO;
import kr.co.solbipos.membr.info.regist.service.RegistService;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @author NHN한국사이버결제 KCP 정용길
 * @version 1.0
 * <p>
 * Copyright (C) by SOLBIPOS CORP. All right reserved.
 * @Class Name : RegistServiceImpl.java
 * @Description : 회원관리 > 회원정보 > 회원정보관리
 * @Modification Information
 * @
 * @ 수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 * @ 2018.08.02  이다솜      회원등록 시 회원단축번호 추가
 * @since 2018.05.01
 */
@Service("registService")
@Transactional
public class RegistServiceImpl implements RegistService {


    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final RegistMapper mapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public RegistServiceImpl(RegistMapper mapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.mapper = mapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 등록매장 리스트 조회
     */
    @Override
    public List<DefaultMap<String>> getRegistStore(SessionInfoVO sessionInfoVO) {

        // 회원정보 등록시 등록매장의 콤보박스 내용 조회
        // 본사 : 해당 본사에 소속된 매장만 조회한다.
        // 매장 : 해당 매장만 표시
        HqManageVO hqVO = new HqManageVO();

        hqVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return mapper.getRegistStore(hqVO);
    }

    /**
     * 회원등급 리스트 조회
     */
    @Override
    public List<DefaultMap<String>> getMembrClassList(SessionInfoVO sessionInfoVO) {

        MembrClassVO membrClassVO = new MembrClassVO();

        membrClassVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            membrClassVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            membrClassVO.setMembrOrgnCd(sessionInfoVO.getStoreCd());
        }

        List<DefaultMap<String>> resultList = mapper.getMemberClassList(membrClassVO);

        // 등록된 회원등급이 없을때는 기본등급을 리스트에 넣어줌.
        if (resultList.size() == 0) {
            DefaultMap<String> tmpList = new DefaultMap<String>();
            tmpList.put("value", "000");
            tmpList.put("name", "기본등급");
            resultList.add(tmpList);
        }
        return resultList;
    }

    /**
     * 선택한 회원 정보 조회
     */
    @Override
    public DefaultMap<String> getMemberInfo(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        // ERP 연동과 관련, 본사코드에 의해 조회하는 DB가 다름 (이다솜_2020.01.28)
        registVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return mapper.getMemberInfo(registVO);
    }

    /**
     * 회원 목록 조회
     */
    @Override
    public List<DefaultMap<String>> getMemberList(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        // 회원정보 조회시 해당 본사나 매장의 회원만 조회
        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            registVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if (!StringUtil.isEmpties(registVO.getRegStoreCd())) {
            registVO.setRegStoreCds(registVO.getRegStoreCd().split(","));
        }

        return mapper.getMemberList(registVO);
    }

    /**
     * 회원 등록
     */
    @Override
    public String registMemberInfo(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        registVO.setMembrNo(mapper.getNewMemberNo(registVO));

        // 회원단축번호 추가_2019.08.02 추가 이다솜
        registVO.setShortNo(registVO.getTelNo().substring(registVO.getTelNo().length() - 4, registVO.getTelNo().length()));

        registVO.setRegDt(dt);
        registVO.setRegId(sessionInfoVO.getUserId());
        registVO.setModDt(dt);
        registVO.setModId(sessionInfoVO.getUserId());

        // 회원등록
        int result = mapper.registMemberInfo(registVO);
        String membrNo = "";

        if (result == 1 ) {
            membrNo = registVO.getMembrNo();
//            // 회원카드 등록
//            if (mapper.insertMembrCard(registVO) <= 0) {
//                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
//            }
        }
        // 선불회원 등록 (자점회원)
        result = mapper.registMemberPrepaid(registVO);
        if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 회원-거래처 매핑코드 등록 (보나비)
        if (registVO.getCdCompany() != null && registVO.getCdPartner() != null) {
            result = mapper.registMemberMappingCode(registVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

//        System.out.println("test1111");
        LOGGER.info("회원등록 >>> 날짜 : " + dt + ", 본사코드 : " + registVO.getMembrOrgnCd() + ", 매장코드 : " + registVO.getRegStoreCd() + ", 회원코드 : " + registVO.getMembrNo());
        // 회원정보 등록,수정시 본사코드 A0007만
        if(("A0007").equals(registVO.getMembrOrgnCd())) {

            // 프로시저 결과
            String sResult = "";

            LOGGER.info("회원등록 >>> SP_NEOE_POSLINK_PTN 호출 : " + dt);
            sResult = mapper.registPoslinkPtn(registVO);
            /*if(result <= 0) {
                LOGGER.info("회원등록 >>> SP_NEOE_POSLINK_PTN Fail : " + dt);
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }*/

            LOGGER.info("회원등록 >>> SP_NEOE_SPOS_BILL_PTN 호출 : " + dt);
            sResult = mapper.registSposBillPtn(registVO);
            /*if(result <= 0) {
                LOGGER.info("회원등록 >>> SP_NEOE_SPOS_BILL_PTN Fail : " + dt);
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }*/
        }

        return membrNo;
    }

    /**
     * 회원 수정
     */
    @Override
    public int updateMemberInfo(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        registVO.setRegDt(dt);
        registVO.setRegId(sessionInfoVO.getUserId());
        registVO.setModDt(dt);
        registVO.setModId(sessionInfoVO.getUserId());

        // 회원 등록
        int result = mapper.updateMemberInfo(registVO);
        if (result == 1) {
            // 회원카드 수정
            mapper.updateMembrCard(registVO);
        }


        // 선불회원 등록 (자점회원)
        result = mapper.registMemberPrepaid(registVO);
        if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));


        // 회원-거래처 매핑코드 등록 (보나비)
        if (registVO.getCdCompany() != null && registVO.getCdPartner() != null) {
            result = mapper.registMemberMappingCode(registVO);
            if (result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

//        System.out.println("test1111");
        LOGGER.info("회원수정 >>> 날짜 : " + dt + ", 본사코드 : " + registVO.getMembrOrgnCd() + ", 매장코드 : " + registVO.getRegStoreCd() + ", 회원코드 : " + registVO.getMembrNo());
        // 회원정보 등록,수정시 본사코드 A0007만
        if(("A0007").equals(registVO.getMembrOrgnCd())) {

            // 프로시저 결과
            String sResult = "";

            LOGGER.info("회원수정 >>> SP_NEOE_POSLINK_PTN 호출 : " + dt);
            sResult = mapper.registPoslinkPtn(registVO);
            /*if(result <= 0) {
                LOGGER.info("회원수정 >>> SP_NEOE_POSLINK_PTN Fail : " + dt);
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }*/

            LOGGER.info("회원수정 >>> SP_NEOE_SPOS_BILL_PTN 호출 : " + dt);
            sResult = mapper.registSposBillPtn(registVO);
            /*if(result <= 0) {
                LOGGER.info("회원수정 >>> SP_NEOE_SPOS_BILL_PTN Fail : " + dt);
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }*/
        }

        return result;
    }

    /**
     * 회원 삭제
     */
    @Override
    public int deleteMemberInfo(RegistVO[] registVOs, SessionInfoVO sessionInfoVO) {

        String currentDate = currentDateTimeString();

        int registCnt = 0;

        for (RegistVO registVO : registVOs) {
            //registVO.setMembrOrgnCd(sessionInfoVO.getOrgnCd());
            registVO.setModId(sessionInfoVO.getUserId());
            registVO.setModDt(DateUtil.currentDateTimeString());

            int result = mapper.deleteMemberInfo(registVO);
            if (result <= 0) {
                throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            } else {
                registCnt += result;
            }
        }

        return registCnt;
    }

    /***
     * 후불 회원 등록 매장 조회
     * @param postpaidStoreVO
     * @return
     */
    @Override
    public List<DefaultMap<String>> getPostpaidStoreLists(PostpaidStoreVO postpaidStoreVO, SessionInfoVO sessionInfoVO) {

        // 기본매장이 있는 경우, 매장 조회시 기본매장은 제외하고 검색한다.
        String defaultStoreCd = "";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            defaultStoreCd = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));
            defaultStoreCd.replace("*", "");
        }

        postpaidStoreVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd()); // 본사에서 조회하는 메뉴
        postpaidStoreVO.setDefaultStoreCd(defaultStoreCd);

        List<DefaultMap<String>> resultList = null;

        // 등록매장 조회
        if (postpaidStoreVO.getRegYn() == UseYn.Y) {
            resultList = mapper.getRegStoreList(postpaidStoreVO);
        }
        // 미등록매장 조회
        else {
            resultList = mapper.getNoRegStoreList(postpaidStoreVO);
        }

        return resultList;
    }

    /**
     * 후불회원 매장 등록
     */
    @Override
    public int registPostpaidStore(PostpaidStoreVO[] postpaidStoreVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        for (PostpaidStoreVO postpaidStoreVO : postpaidStoreVOs) {

            postpaidStoreVO.setUseYn(UseYn.Y);
            postpaidStoreVO.setRegDt(dt);
            postpaidStoreVO.setRegId(sessionInfoVO.getUserId());
            postpaidStoreVO.setModDt(dt);
            postpaidStoreVO.setModId(sessionInfoVO.getUserId());

            result += mapper.registPostpaidStore(postpaidStoreVO);
        }
        return result;
    }

    /**
     * 후불회원 매장 삭제
     */
    @Override
    public int deletePostpaidStore(PostpaidStoreVO[] postpaidStoreVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        for (PostpaidStoreVO postpaidStoreVO : postpaidStoreVOs) {

            postpaidStoreVO.setModDt(dt);
            postpaidStoreVO.setModId(sessionInfoVO.getUserId());
            result += mapper.deletePostpaidStore(postpaidStoreVO);
        }
        return result;
    }

    /**
     * 회원 거래처 매핑코드 (보나비 전용 >> 비티스 추가 2020.01.28)
     */
    @Override
    public List<DefaultMap<String>> getMappingCompany(MemberMappingVO memberMappingVO, SessionInfoVO sessionInfoVO) {

        // ERP 연동과 관련, 본사코드에 의해 조회하는 DB가 다름 (이다솜_2020.01.28)
        memberMappingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return mapper.getMappingCompany(memberMappingVO);
    }

    /**
     * 회원 거래처 매핑 팝업 - 회원 거래처 매핑 조회
     */
    @Override
    public List<DefaultMap<String>> getMemberVendorMappingList(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        // 회원정보 조회시 해당 본사나 매장의 회원만 조회
        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        registVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        registVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            registVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if (!StringUtil.isEmpties(registVO.getRegStoreCd())) {
            registVO.setRegStoreCds(registVO.getRegStoreCd().split(","));
        }

        return mapper.getMemberVendorMappingList(registVO);
    }

    /**
     * 해당회원 카드정보
     */
    @Override
    public List<DefaultMap<String>> getCardList(RegistVO registVO, SessionInfoVO sessionInfoVO) {
        registVO.setOrgnFg(sessionInfoVO.getOrgnFg());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            registVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            registVO.setMembrOrgnCd(sessionInfoVO.getStoreCd());
        }
        return mapper.getCardList(registVO);
    }

    /**
     * 카드정보등록
     */
    @Override
    public int registCardInfo(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            registVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            registVO.setMembrOrgnCd(sessionInfoVO.getStoreCd());
        }

        registVO.setRegDt(dt);
        registVO.setRegId(sessionInfoVO.getUserId());
        registVO.setModDt(dt);
        registVO.setModId(sessionInfoVO.getUserId());

        // 회원카드 등록
        int result = mapper.insertMembrCard(registVO);
        if (result <= 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        return result;
    }

    @Override
    public DefaultMap<Object> getDlvrList(RegistVO registVO, SessionInfoVO sessionInfoVO) {
        DefaultMap<Object> result = new DefaultMap<>();
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            registVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            registVO.setMembrOrgnCd(sessionInfoVO.getStoreCd());
        }

        LOGGER.debug("regStoreCd: {}", registVO.getRegStoreCd());
        LOGGER.debug("sessionInfoStoreCd: {}", sessionInfoVO.getStoreCd());

        List dlvrList = mapper.getDlvrList(registVO);
        List lZoneList = mapper.getMembrLzoneList(registVO);
        result.put("dlvrList", dlvrList);
        result.put("lZoneList", lZoneList);
        return result;
    }
    @Override
    public DefaultMap<Object> getDlvrMzoneList(RegistVO registVO, SessionInfoVO sessionInfoVO) {
        DefaultMap<Object> result = new DefaultMap<>();
        List mZoneList = mapper.getDlvrMzoneList(registVO);
        result.put("mZoneList", mZoneList);
        return result;
    }

    @Override
    public List getLzoneList(RegistVO registVO, SessionInfoVO sessionInfoVO) {

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            registVO.setStoreCd(sessionInfoVO.getHqOfficeCd());
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            registVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        List<DefaultMap<String>> resultList = mapper.getDlvrList(registVO);

        // 등록된 회원등급이 없을때는 기본등급을 리스트에 넣어줌.
        if (resultList.size() == 0) {
            DefaultMap<String> tmpList = new DefaultMap<String>();
            tmpList.put("value", "000");
            tmpList.put("name", "없음");
            resultList.add(tmpList);
        }
        return resultList;
    }

    // 배달전화 리스트
    @Override
    public List<DefaultMap<String>> getDlvrTelList(RegistVO registVO, SessionInfoVO sessionInfoVO) {
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            registVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            registVO.setMembrOrgnCd(sessionInfoVO.getStoreCd());
        }
        return mapper.getDlvrTelList(registVO);
    }


    // 배달정보등록
    @Override
    public int registDlvrInfo(RegistVO registVO, SessionInfoVO sessionInfoVO) {
        String dt = currentDateTimeString();


        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            registVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            registVO.setMembrOrgnCd(sessionInfoVO.getStoreCd());
        }
        registVO.setRegDt(dt);
        registVO.setRegId(sessionInfoVO.getUserId());
        registVO.setModDt(dt);
        registVO.setModId(sessionInfoVO.getUserId());

        // 회원배달 등록
        int result = mapper.insertMembrDlvr(registVO);
        if (result <= 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        return result;
    }

    // 배달정보등록
    @Override
    public int registDlvrTelInfo(RegistVO registVO, SessionInfoVO sessionInfoVO) {
        String dt = currentDateTimeString();

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            registVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) { // 매장
            registVO.setMembrOrgnCd(sessionInfoVO.getStoreCd());
        }
        registVO.setRegDt(dt);
        registVO.setRegId(sessionInfoVO.getUserId());
        registVO.setModDt(dt);
        registVO.setModId(sessionInfoVO.getUserId());

        // 회원카드 등록
        int result = mapper.insertMembrDlvrTel(registVO);
        if (result <= 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        return result;
    }

}

