package kr.co.solbipos.store.manage.storemanage.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.security.EncUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyClassVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyVO;
import kr.co.solbipos.base.store.emp.enums.EmpResult;
import kr.co.solbipos.pos.loginstatus.enums.SysStatFg;
import kr.co.solbipos.store.common.enums.ConfgFg;
import kr.co.solbipos.store.hq.hqmanage.service.HqManageVO;
import kr.co.solbipos.store.manage.storemanage.service.*;
import kr.co.solbipos.store.manage.terminalManage.service.StoreCornerVO;
import kr.co.solbipos.sys.auth.authgroup.enums.IncldExcldFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : StoreManageServiceImpl.java
 * @Description : 가맹점관리 > 매장관리 > 매장정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018. 06.08  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 06.08
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service
public class StoreManageServiceImpl implements StoreManageService{

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final String DEFAULT_POS_NO = "01";         // 기본 포스 번호
    private final String DEFAULT_POS_EMPNO = "0000";    // 기본 포스 직원번호
    private final String DEFAULT_POS_PASSWORD = "1234"; // 기본 포스 패스워드
    private final String DEFAULT_WEB_PASSWORD = "0000"; // 기본 웹 패스워드

    private final String ENVST_FG_POS = "03";           // 환경설정구분 (포스)

    private final String EXCLUSIVE_HQ_OFFICE = "00000"; // 단독매장 등록 본사

    private final String CORNER_USE_YN = "2028";        // 환경변수 : 코너 사용여부
    private final String TABLE_ENVST_CD = "1003";       // 테이블 기본 그룹설정 정보
    private final String MAIN_POS_YN  = "4021";         // 메인포스여부
    private final String MAIN_POS_YN_DEFAULT = "2";     // 메인포스여부 : 서브

    private final String DEFAULT_PRODUCT_CLASS = "0000";// 상품 기본 분류

    private final String SYS_CLOSURE_DATE = "99991231"; // 시스템 종료일


    private final StoreManageMapper mapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public StoreManageServiceImpl(StoreManageMapper mapper, MessageService messageService) {
        this.mapper = mapper;
        this.messageService = messageService;
    }

    /** 매장 목록 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO) {

        // 소속구분, 총판의 부모총판 코드
        storeManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeManageVO.setpAgencyCd(sessionInfoVO.getpAgencyCd());

        // 총판인 경우, session의 AgencyCode 값 넣기
        if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            storeManageVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        return mapper.getStoreList(storeManageVO);
    }

    /** 매장 목록 엑셀조회 */
    @Override
    public List<DefaultMap<String>> getStoreExcelList(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO) {

        // 소속구분, 총판의 부모총판 코드
        storeManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeManageVO.setpAgencyCd(sessionInfoVO.getpAgencyCd());

        // 총판인 경우, session의 AgencyCode 값 넣기
        if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            storeManageVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        return mapper.getStoreExcelList(storeManageVO);
    }

    /** 매장정보 상세조회 */
    @Override
    public Map<String, Object> getStoreDetail(StoreManageVO storeManageVO) {

        Map<String,Object> result = new HashMap<String, Object>();

        // 매장 상세정보
        DefaultMap<String> storeDtlInfo = mapper.getStoreDetail(storeManageVO);

        // 설치 포스수 조회
        int instPosCnt = mapper.getInstPosCnt(storeManageVO);

        result.put("storeDtlInfo", storeDtlInfo);
        result.put("instPosCnt", instPosCnt);

        return result;
    }

    /** 매장 콤보리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreComboList(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO) {

        // 총판인 경우, session의 AgencyCode 값 넣기
        if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY){
            storeManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            storeManageVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        return mapper.getStoreComboList(storeManageVO);
    }

    /** 매장환경조회 팝업 데이터 조회 */
    @Override
    public Map<String, Object> getStoreEnvInfo(StoreManageVO storeManageVO) {

        Map<String, Object> result = new HashMap<String, Object>();

        // 매장정보 데이터 조회
        DefaultMap<String> storeInfo = mapper.getStoreEnvInfo(storeManageVO);

        // 매장 포스 기종 조회
        List<DefaultMap<String>> posInfo = mapper.getStorePosInfo(storeManageVO);

        // 매장 주방프린터 기종 조회
        List<DefaultMap<String>> printInfo = mapper.getStorePrintInfo(storeManageVO);

        result.put("storeInfo", storeInfo);
        result.put("posInfo", posInfo);
        result.put("printInfo", printInfo);

        return result;
    }

    /** 매장 신규 등록 */
    @Override
    public String saveStoreInfo(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        storeManageVO.setRegDt(dt);
        storeManageVO.setModDt(dt);
        storeManageVO.setRegId(sessionInfoVO.getUserId());
        storeManageVO.setModId(sessionInfoVO.getUserId());

        if(SysStatFg.CLOSE == storeManageVO.getSysStatFg()) {
            storeManageVO.setSysClosureDate(SYS_CLOSURE_DATE);
        } else  if(SysStatFg.DEMO == storeManageVO.getSysStatFg() ) {
            storeManageVO.setSysClosureDate(SYS_CLOSURE_DATE);
        } else {
            storeManageVO.setSysClosureDate(currentDateString());
        }

        // 매장 신규 코드
        String storeCd ="";
        if("1".equals(storeManageVO.getStoreCdInputType())){ // 수동채번
            if(storeManageVO.getStoreCd() != ""){
                storeCd = storeManageVO.getStoreCd();
            }
        }else{ // 자동채번
            storeCd = mapper.getStoreCd(storeManageVO);
        }


        if(storeCd != "") {

            String wUuserId = "";
            String wUserPwd = "";

            if("1".equals(storeManageVO.getStoreCdInputType()) && !"".equals(storeManageVO.getDigit8Store()) &&
                    !"".equals(storeManageVO.getUserId()) && !"".equals(storeManageVO.getUserPwd())){
                wUuserId = storeManageVO.getUserId(); // 웹 사용자 아이디
                wUserPwd = EncUtil.setEncSHA256(wUuserId + storeManageVO.getUserPwd()); // 웹 패스워드
            }else{
                wUuserId = storeCd.toLowerCase(); // 웹 사용자 아이디
                wUserPwd = EncUtil.setEncSHA256(wUuserId + DEFAULT_WEB_PASSWORD);    // 웹 패스워드
            }

            String pEmpNo = DEFAULT_POS_EMPNO; // 포스 기본 사용자 사원번호
            String pUserPwd = EncUtil.setEncSHA256(pEmpNo + DEFAULT_POS_PASSWORD);  // 포스 패스워드

            storeManageVO.setStoreCd(storeCd);
            storeManageVO.setUserId(wUuserId);
            storeManageVO.setUserPwd(wUserPwd);
            storeManageVO.setPosEmpNo(pEmpNo);
            storeManageVO.setPosUserPwd(pUserPwd);

            // 단독매장
            if(storeManageVO.getHqOfficeCd().equals("00000")) {
                storeManageVO.setAuthGrpCd("000004");
            // 매장
            } else {
                storeManageVO.setAuthGrpCd("000008");
            }

            // 마스터 관련 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■

            // 신규 매장정보 저장
            int procCnt = mapper.saveStoreInfo(storeManageVO);

            // 기본 사용자 등록
            procCnt += mapper.insertStoreDefaultUser(storeManageVO);

            // 웹 사용자 등록
            procCnt += mapper.insertStoreWebUser(storeManageVO);

            // 포스 출력물 마스터 등록 (단독, 프랜차이즈)
            if(EXCLUSIVE_HQ_OFFICE.equals(storeManageVO.getHqOfficeCd())) {

                // 포스 템플릿 등록
                procCnt += mapper.insertDefaultPrintTemplete(storeManageVO);

                //포스 실제출력물 등록
                procCnt += mapper.insertDefaultPrint(storeManageVO);

            } else {

                // 포스 템플릿 등록
                procCnt += mapper.insertHqPrintTemplete(storeManageVO);

                //포스 실제출력물 등록
                procCnt += mapper.insertHqPrint(storeManageVO);
            }

            // 포스 마스터 생성 (설치포수 개수만큼 포스 마스터 생성)

            String posNoStr = "";

            if(!"".equals(storeManageVO.getInstallPosCnt())){
                int installPosCnt =  Integer.parseInt(storeManageVO.getInstallPosCnt());

                for(int i=0; i<installPosCnt; i++){
                    posNoStr += String.valueOf(i+1);
                    if(i != (installPosCnt-1)) {
                        posNoStr += ",";
                    }

                    storeManageVO.setPosNo((i+1));
                    procCnt += mapper.insertPosInfo(storeManageVO);
                }
            }

            // 기본 코너 등록 (매장 기본코너)
            StoreCornerVO storeCornerVO = new StoreCornerVO();
            storeCornerVO.setStoreCd(storeCd);
            storeCornerVO.setUseYn("Y");
            storeCornerVO.setRegDt(dt);
            storeCornerVO.setRegId(sessionInfoVO.getUserId());
            storeCornerVO.setModDt(dt);
            storeCornerVO.setModId(sessionInfoVO.getUserId());

            procCnt += mapper.insertStoreCorner(storeCornerVO);

            // 기본 창고 등록 (매장 기본창고(000 매대 001 기본창고)
            procCnt += mapper.insertStorage(storeManageVO);

            // 회원등급 생성
            MemberClassVO memberClassVO = new MemberClassVO();
            memberClassVO.setMembrOrgnCd(storeCd);
            memberClassVO.setRegDt(dt);
            memberClassVO.setRegId(sessionInfoVO.getUserId());
            memberClassVO.setModDt(dt);
            memberClassVO.setModId(sessionInfoVO.getUserId());

            // 단독매장 회원등급 생성
            if(storeManageVO.getHqOfficeCd().equals("00000")) {
                procCnt += mapper.insertMemberClass(memberClassVO);
            }

            // 프랜차이즈매장: 본사에 등록된 결제수단분류/쿠폰/상품권 생성
            if(!storeManageVO.getHqOfficeCd().equals("00000")) {
                // 본사 결제수단분류 매장에 생성
                procCnt += mapper.insertTbMsPayMethodClass(storeManageVO);
                // 본사 상품권 매장에 생성
                procCnt += mapper.insertTbMsGift(storeManageVO);
                //쿠폰 생성하지 않음
                //procCnt += mapper.insertTbMsCoupon(storeManageVO);

                // 본사 상품분류 매장에 생성
                procCnt += mapper.insertStoreHqProductClass(storeManageVO);
                // 본사 사이드(속성) 매장에 생성
                procCnt += mapper.insertStoreHqSdattrClass(storeManageVO);
                procCnt += mapper.insertStoreHqSdattr(storeManageVO);
                // 본사 사이드(선택메뉴) 매장에 생성
                procCnt += mapper.insertStoreHqSdselGroup(storeManageVO);
                procCnt += mapper.insertStoreHqSdselClass(storeManageVO);
                procCnt += mapper.insertStoreHqSdselProd(storeManageVO);

               // 본사신규상품매장생성(0:자동생성, 1:생성안함)
                if(storeManageVO.getEnvst0043().equals("0")) {
                    // 본사 상품 매장에 생성
                    procCnt += mapper.insertStoreHqProduct(storeManageVO);
                    // 본사 판매가 매장에 생성
                    procCnt += mapper.insertStoreHqSaleUprc(storeManageVO);
                    // 본사 공급가 매장에 생성(아직 공급가 사용안함 개발필요)
//                    procCnt += mapper.insertStoreHqSplyUprc(storeManageVO);
                    // 본사 바코드 매장에 생성
                    procCnt += mapper.insertStoreHqBarcd(storeManageVO);
                    // 본사 상품을 취급상품 테이블에 생성
                    procCnt += mapper.insertStoreHqProductStore(storeManageVO);
                    // 본사 세트구성상품 매장에 생성
                    procCnt += mapper.insertStoreHqSetConfigProd(storeManageVO);
                }
            }

            // 기본 테이블 그룹 생성
            TableGroupVO tableGroupVO = new TableGroupVO();
            tableGroupVO.setStoreCd(storeCd);
            tableGroupVO.setRegDt(dt);
            tableGroupVO.setRegId(sessionInfoVO.getUserId());
            tableGroupVO.setModDt(dt);
            tableGroupVO.setModId(sessionInfoVO.getUserId());

            procCnt += mapper.insertTabGroup(tableGroupVO);

            // 프랜차이즈매장: 본사에 등록된 원산지, 알러지 정보 생성
            if(!storeManageVO.getHqOfficeCd().equals("00000")) {

                /** 본사 재료 및 원산지 정보 매장에 생성  */
                procCnt += mapper.insertStoreHqProductRecpOrigin(storeManageVO);
                /** 본사 상품-원산지 매핑정보 매장에 생성  */
                procCnt += mapper.insertStoreHqProductRecpProd(storeManageVO);

                /** 본사 재료 및 알레르기 정보 매장에 생성  */
                procCnt += mapper.insertStoreHqProductAlgiInfo(storeManageVO);
                /** 본사 상품-알레르기 매핑정보 매장에 생성  */
                procCnt += mapper.insertStoreHqProductAlgiProd(storeManageVO);
            }

            // 매장환경 복사 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■

            String[] copyEnv = storeManageVO.getCopyChkVal().split("\\|");

            if(copyEnv.length > 0) {

                for(int i=0; i<copyEnv.length; i++) {

                    // 매장환경 복사
                    if( "storeEnv".equals(copyEnv[i]) ) {
                        procCnt += mapper.insertStoreEnvInfo(storeManageVO);
                    }

                    // 포스환경 복사
                    if( "posEnv".equals(copyEnv[i]) ) {
                        procCnt += mapper.insertPosEnvInfo(storeManageVO);
//                    procCnt += mapper.insertPosFunKeyInfo(storeManageVO);
                    }

                    // 외식환경 복사
                    if( "foodEnv".equals(copyEnv[i]) ) {
                        procCnt += mapper.insertFoodEnvInfo(storeManageVO);
                    }

                    // 주방프린터 복사
                    if( "kitchenPrint".equals(copyEnv[i]) ) {
                        procCnt += mapper.insertKitchenPrintEnvInfo(storeManageVO);
                    }

                    // 상품 복사
                    if( "product".equals(copyEnv[i]) ) {
                        // 상품분류 생성
                        procCnt += mapper.insertStoreProductClass(storeManageVO);
                        // 상품 생성
                        procCnt += mapper.insertStoreProduct(storeManageVO);
                        // 판매가 생성(상품정보 REG_FG가 S인 상품만)
                        procCnt += mapper.insertStoreSaleUprc(storeManageVO);
                        // 공급가 생성(아직 공급가 사용안함 개발필요)
//                        procCnt += mapper.insertStoreSplyUprc(storeManageVO);
                        // 바코드 생성
                        procCnt += mapper.insertStoreBarcd(storeManageVO);
                        // 사이드(속성) 생성
                        procCnt += mapper.insertStoreSdattrClass(storeManageVO);
                        procCnt += mapper.insertStoreSdattr(storeManageVO);
                        // 사이드(선택메뉴) 생성
                        procCnt += mapper.insertStoreSdselGroup(storeManageVO);
                        procCnt += mapper.insertStoreSdselClass(storeManageVO);
                        procCnt += mapper.insertStoreSdselProd(storeManageVO);

                        // 프랜차이즈매장
                        if(!storeManageVO.getHqOfficeCd().equals("00000")) {
                            // 취급상품
                            procCnt += mapper.deleteStoreProductStore(storeManageVO);
                            procCnt += mapper.insertStoreProductStore(storeManageVO);
                        }
                    }

                    // 판매가 복사
                    if( "salePrice".equals(copyEnv[i]) ) {
                        // 판매가 생성(상품정보 REG_FG가 S인 상품만)
                        procCnt += mapper.insertStoreSaleUprc(storeManageVO);
                    }

                    // 공급가 복사
                    if( "supplyPrice".equals(copyEnv[i]) ) {
                        // 공급가 생성(아직 공급가 사용안함 개발필요)
                        procCnt += mapper.insertStoreSplyUprc(storeManageVO);
                    }

                    // 기능키 복사
                    if( "posFnkey".equals(copyEnv[i]) ) {

                        // 매장 기능키 복사
                        StoreFnkeyVO storeFnkeyVO = new StoreFnkeyVO();
                        storeFnkeyVO.setStoreCd(storeCd);
                        storeFnkeyVO.setCopyStoreCd(storeManageVO.getCopyStoreCd());
                        storeFnkeyVO.setRegDt(dt);
                        storeFnkeyVO.setRegId(sessionInfoVO.getUserId());
                        storeFnkeyVO.setModDt(dt);
                        storeFnkeyVO.setModId(sessionInfoVO.getUserId());

                        procCnt += mapper.copyStoreFnkey(storeFnkeyVO);

                        // 포스 기능키 복사 (포스 개수만큼)
                        PosFnkeyVO posFnkeyVO = new PosFnkeyVO();
                        posFnkeyVO.setStoreCd(storeCd);
                        posFnkeyVO.setCopyStoreCd(storeManageVO.getCopyStoreCd());
                        posFnkeyVO.setRegDt(dt);
                        posFnkeyVO.setRegId(sessionInfoVO.getUserId());
                        posFnkeyVO.setModDt(dt);
                        posFnkeyVO.setModId(sessionInfoVO.getUserId());

//                    int installPosCnt =  Integer.parseInt(storeManageVO.getInstallPosCnt());

//                    String posNoStr = "";
                        ////                    for(int p=0; p<installPosCnt; p++){
                        ////                        posNoStr += String.valueOf(p+1);
                        ////                        if(p != (installPosCnt-1)) {
                        ////                            posNoStr += ",";
                        ////                        }
                        ////                    }

                        posFnkeyVO.setArrPosNo(posNoStr.split(","));

                        procCnt += mapper.copyPosFnkey(posFnkeyVO);


                        // 매장설정 XML, 포스기능 XML  복사 (TB_WB_STORE_CONFG_XML, TB_WB_POS_CONFG_XML)
                        ConfgXmlVO confgXmlVO = new ConfgXmlVO();
                        confgXmlVO.setStoreCd(storeCd);
                        confgXmlVO.setCopyStoreCd(storeManageVO.getCopyStoreCd());
                        confgXmlVO.setRegDt(dt);
                        confgXmlVO.setRegId(sessionInfoVO.getUserId());
                        confgXmlVO.setModDt(dt);
                        confgXmlVO.setModId(sessionInfoVO.getUserId());

                        // 복사할 매장의 포스 MAX
                        String copyStorePosMax = mapper.getCopyStorePosMax(confgXmlVO);
                        confgXmlVO.setCopyStorePosMax(copyStorePosMax);

                        // 복사할 매장에 포스가 있는 경우만
                        if(!"-".equals(confgXmlVO.getCopyStorePosMax())) {
                            LOGGER.info(">>>>>>>>>>>>>>>>>> RIGIT >>>>>>>>>>>>>>>");
                            confgXmlVO.setConfgFg(ConfgFg.POS_FN_RIGHT); // 포스 기능키 (우)
//                            confgXmlVO.setArrPosNo(posNoStr.split(","));
                            procCnt += mapper.copyPosConfXml(confgXmlVO);

                            LOGGER.info(">>>>>>>>>>>>>>>>>> LEFT >>>>>>>>>>>>>>>");
                            confgXmlVO.setConfgFg(ConfgFg.POS_FN_LEFT); // 포스 기능키 (좌)
                            procCnt += mapper.copyPosConfXml(confgXmlVO);

                            LOGGER.info(">>>>>>>>>>>>>>>>>> DELIVERY >>>>>>>>>>>>>>>");
                            confgXmlVO.setConfgFg(ConfgFg.POS_FN_DELIVERY); // 포스 기능키 (배달메뉴)
                            procCnt += mapper.copyPosConfXml(confgXmlVO);

                            LOGGER.info(">>>>>>>>>>>>>>>>>> SELF >>>>>>>>>>>>>>>");
                            confgXmlVO.setConfgFg(ConfgFg.FUNC_KEY_SELF); // 포스 기능키 (셀프키)
                            procCnt += mapper.copyPosConfXml(confgXmlVO);
                        }

                        // 포스기능키별 적용매장 등록(TB_CM_POS_FNKEY_STORE)
                        procCnt += mapper.registPosFnkeyStore(storeFnkeyVO);

                    }

                    // 터치키 복사
                    if( "touchKey".equals(copyEnv[i]) ) {

                        // 매장설정 XML, 포스기능 XML  복사 (TB_WB_STORE_CONFG_XML, TB_WB_POS_CONFG_XML)
                        ConfgXmlVO confgXmlVO = new ConfgXmlVO();
                        confgXmlVO.setStoreCd(storeCd);
                        confgXmlVO.setCopyStoreCd(storeManageVO.getCopyStoreCd());
                        confgXmlVO.setRegDt(dt);
                        confgXmlVO.setRegId(sessionInfoVO.getUserId());
                        confgXmlVO.setModDt(dt);
                        confgXmlVO.setModId(sessionInfoVO.getUserId());

                        confgXmlVO.setConfgFg(ConfgFg.TOUCH_KEY); // 터치키
                        procCnt += mapper.copyStoreConfXml(confgXmlVO);

                        // 포스기능 XML 복사 (TB_WB_POS_CONFG_XML) - 포스 수 만큼
//                    int installPosCnt =  Integer.parseInt(storeManageVO.getInstallPosCnt());

//                    for(int p=0; p<installPosCnt; p++){
//                        confgXmlVO.setPosNo(String.valueOf(p+i));
//                    }

                        //confgXmlVO.setArrPosNo(posNoStr.split(","));

                        //procCnt += mapper.copyPosConfXml(confgXmlVO);

                        // 터치키 분류 복사
                        TouchKeyClassVO touchkeyClassVO = new TouchKeyClassVO();
                        touchkeyClassVO.setStoreCd(storeCd);
                        touchkeyClassVO.setCopyStoreCd(storeManageVO.getCopyStoreCd());
                        touchkeyClassVO.setRegDt(dt);
                        touchkeyClassVO.setRegId(sessionInfoVO.getUserId());
                        touchkeyClassVO.setModDt(dt);
                        touchkeyClassVO.setModId(sessionInfoVO.getUserId());

                        procCnt += mapper.copyFnkeyClassCopy(touchkeyClassVO);

                        // 터치키 복사
                        TouchKeyVO touchkeyVO = new TouchKeyVO();
                        touchkeyVO.setStoreCd(storeCd);
                        touchkeyVO.setRegDt(dt);
                        touchkeyVO.setRegId(sessionInfoVO.getUserId());
                        touchkeyVO.setModDt(dt);
                        touchkeyVO.setModId(sessionInfoVO.getUserId());

                        touchkeyVO.setCopyStoreCd(storeManageVO.getCopyStoreCd());
                        procCnt += mapper.copyFnkeyCopy(touchkeyVO);
                    }
                }
            }

            //TODO 판매가 테이블 생성시 추가
            if(!EXCLUSIVE_HQ_OFFICE.equals(storeManageVO.getHqOfficeCd())) { // 프랜차이즈
                // 프랜차이즈 설정 - 판매가 HD 복사
                // 프랜차이즈 설정 - 판매가 DT 복사
            }

            // 공통코드 복사
            StoreNmcodeVO storeNmcodeVO = new StoreNmcodeVO();
            storeNmcodeVO.setStoreCd(storeCd);
            String copyNmCode = mapper.copyCmmNameCode(storeNmcodeVO);

            // ERP 연동 매장 등록인 경우, NXPOS_STORE_CD 값을 Update 한다.
            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
                if(storeManageVO.getBbqStoreCd() != null && !"".equals(storeManageVO.getBbqStoreCd())) {

                    // 1. ERP 연동 매장 등록이 가능한 본사인지 확인
                    String strErpLinkHq = mapper.getErpLinkHq(storeManageVO);
                    if (strErpLinkHq != null && !"".equals(strErpLinkHq)) {

                        // 2. 선택한 ERP 연동 매장이 미등록 매장이 맞는지 확인
                        int erpUnRegCnt = mapper.getErpStoreUnRegConfm(storeManageVO);
                        if(erpUnRegCnt > 0){
                            // ERP 연동 매장에 NXPOS_STORE_CD 값 Update
                            procCnt += mapper.updateErpStore(storeManageVO);
                        }
                    }
                }
            }

            // todo 공통코드 중 CMM 코드 복사 (프로시져)


            // todo 환경변수 중 본사통제여부 관련 변수 내려받기 (프로시져)
        }

        return storeCd;
    }

    /** 매장 정보 수정 */
    @Override
    public int updateStoreInfo(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        storeManageVO.setModDt(dt);
        storeManageVO.setModId(sessionInfoVO.getUserId());

        if(SysStatFg.CLOSE == storeManageVO.getSysStatFg()) {
            storeManageVO.setSysClosureDate(SYS_CLOSURE_DATE);
        } else  if(SysStatFg.DEMO == storeManageVO.getSysStatFg() ) {
            storeManageVO.setSysClosureDate(SYS_CLOSURE_DATE);
        } else {
            storeManageVO.setSysClosureDate(currentDateString());
        }

        // 매장 정보 수정
        int procCnt = mapper.updateStoreInfo(storeManageVO);

        return procCnt;
    }

    /** 매장 포스승인정보 저장 */
    @Override public int saveStorePosVanInfo(StorePosVO[] storePosVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(StorePosVO storePosVO : storePosVOs) {

            storePosVO.setRegDt(dt);
            storePosVO.setRegId(sessionInfoVO.getUserId());
            storePosVO.setModDt(dt);
            storePosVO.setModId(sessionInfoVO.getUserId());

            procCnt += mapper.updateStorePosVanInfo(storePosVO);
        }

        return 0;
    }

    /** 매장환경 정보 조회 */
    @Override
    public List<DefaultMap<String>> getEnvGroupList(StoreEnvVO storeEnvVO) {
        return mapper.getEnvGroupList(storeEnvVO);
    }

    /** 매장환경 정보 저장 */
    @Override
    public int saveStoreConfig(StoreEnvVO[] storeEnvVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();
        String chkEnvstCd   = "";
        String procResult   = "";

        for(StoreEnvVO storeEnvVO : storeEnvVOs) {

            storeEnvVO.setRegDt(dt);
            storeEnvVO.setRegId(sessionInfoVO.getUserId());
            storeEnvVO.setModDt(dt);
            storeEnvVO.setModId(sessionInfoVO.getUserId());

            if(storeEnvVO.getStatus() == GridDataFg.INSERT) {
                storeEnvVO.setUseYn(UseYn.Y);
                procCnt += mapper.insertStoreConfig(storeEnvVO);
            }
            else if(storeEnvVO.getStatus() == GridDataFg.UPDATE) {
                procCnt += mapper.updateStoreConfig(storeEnvVO);
                chkEnvstCd = storeEnvVO.getEnvstCd();
                if(chkEnvstCd.equals("1041"))
                {
                    procResult = mapper.updateTouchKeyMng(storeEnvVO);
                }
            }
        }
        return procCnt;
    }

    /** 매장 포스환경 정보 조회 */
    @Override
    public List<DefaultMap<String>> getPosEnvGroupList(StorePosEnvVO storePosEnvVO) {

        // 포스환경정보 조회시, 포스번호 없으면 기본 포스번호 넣어줌.
        if("".equals(storePosEnvVO.getPosNo()) || storePosEnvVO.getPosNo() == null){
            storePosEnvVO.setPosNo(DEFAULT_POS_NO);
        }
        storePosEnvVO.setEnvstFg(ENVST_FG_POS);

        return mapper.getPosEnvGroupList(storePosEnvVO);
    }

    /** 매장 포스 환경정보 저장 */
    @Override
    public int savePosConfig(StorePosEnvVO[] storePosEnvVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(StorePosEnvVO storePosEnvVO : storePosEnvVOs) {
            storePosEnvVO.setPosFg("W"); // WEB
            storePosEnvVO.setRegDt(dt);
            storePosEnvVO.setRegId(sessionInfoVO.getUserId());
            storePosEnvVO.setModDt(dt);
            storePosEnvVO.setModId(sessionInfoVO.getUserId());

            if(storePosEnvVO.getStatus() == GridDataFg.INSERT) {
                storePosEnvVO.setUseYn(UseYn.Y);

                // 환경정보 저장
                procCnt += mapper.insertPosConfig(storePosEnvVO);

                // TODO 기능키 복사

            }
            else if(storePosEnvVO.getStatus() == GridDataFg.UPDATE) {
                procCnt += mapper.updatePosConfig(storePosEnvVO);
            }
        }
        return procCnt;
    }


    /** 포스 목록 조회 */
    @Override
    public List<DefaultMap<String>> getPosList(StorePosEnvVO storePosEnvVO) {
        return mapper.getPosList(storePosEnvVO);
    }

    /** 테이블그룹 (selectBox 용) */
    @Override
    public List<DefaultMap<String>> getGroupList(StorePosEnvVO storePosEnvVO) {
        return mapper.getGroupList(storePosEnvVO);
    }

    /** 테이블 그룹설정 정보 저장 */
    @Override
    public int savePosTabGrp(StorePosEnvVO[] storePosEnvVOs, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        int procCnt = 0;

        for(StorePosEnvVO storePosEnvVO : storePosEnvVOs) {

            storePosEnvVO.setEnvstCd(TABLE_ENVST_CD);
            storePosEnvVO.setRegDt(dt);
            storePosEnvVO.setRegId(sessionInfoVO.getUserId());
            storePosEnvVO.setModDt(dt);
            storePosEnvVO.setModId(sessionInfoVO.getUserId());

            procCnt += mapper.savePosTabGrp(storePosEnvVO);
        }
        return procCnt;
    }

    /** 테이블 명칭설정정보 저장 */
    @Override
    public int savePosTabNm(StorePosEnvVO[] storePosEnvVOs, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();
        int procCnt = 0;

        for(StorePosEnvVO storePosEnvVO : storePosEnvVOs) {
            storePosEnvVO.setModDt(dt);
            storePosEnvVO.setModId(sessionInfoVO.getUserId());

            procCnt += mapper.updatePosNm(storePosEnvVO);
        }
        return procCnt;
    }

    /** 포스 셋팅 복사 */
    @Override
    public int copyPosSetting(StorePosEnvVO storePosEnvVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();
        int procCnt = 0;

        storePosEnvVO.setRegDt(dt);
        storePosEnvVO.setRegId(sessionInfoVO.getUserId());
        storePosEnvVO.setModDt(dt);
        storePosEnvVO.setModId(sessionInfoVO.getUserId());

        // 복사
        // 1-1) TB_MS_POS_ENVST 데이터 삭제 (타겟 포스)
        procCnt += mapper.deletePosEnvTarget(storePosEnvVO);

        // 1-2) TB_MS_POS_ENVST 데이터 select insert
        procCnt += mapper.copyPosEnvInfo(storePosEnvVO);

        // 1-3) 메인포스는 하나여야 하므로 복사하는 대상포스는 서브포스가 되어야 함
        storePosEnvVO.setEnvstCd(MAIN_POS_YN);
        storePosEnvVO.setEnvstVal(MAIN_POS_YN_DEFAULT);
        procCnt += mapper.updatePosEnv(storePosEnvVO);

        // 기능키 복사
        // 3-1) TB_MS_POS_FNKEY 기존 데이터 삭제
        procCnt += mapper.deletePosFunkeyTarget(storePosEnvVO);

        // 3-2) TB_MS_POS_FNKEY select insert
        procCnt += mapper.copyPosFunKeyInfo(storePosEnvVO);

        // 3-3) TB_MS_POS_FNKEY 포스 기능 XML 복사
        DefaultMap<String> param = new DefaultMap<String>();
        param.put("storeCd", storePosEnvVO.getStoreCd());
        param.put("posNo", storePosEnvVO.getPosNo());

        param.put("useYn", "Y");
        param.put("regDt", currentDateTimeString());
        param.put("regId", sessionInfoVO.getUserId());
        param.put("modDt", currentDateTimeString());
        param.put("modId", sessionInfoVO.getUserId());

        // 포스기능키 XML 조회
        // 왼쪽
        param.put("confgFg", kr.co.solbipos.base.common.enums.ConfgFg.FUNC_KEY_LEFT.getCode());
        String leftConfXML = mapper.getFuncKeyXml(param);

//        System.out.println(">>>>>>>>>>>>>>> leftConfXML : "+ leftConfXML);


        // 오른쪽
        param.replace("confgFg", kr.co.solbipos.base.common.enums.ConfgFg.FUNC_KEY_RIGHT.getCode());
        String rightConfXML = mapper.getFuncKeyXml(param);

//        System.out.println(">>>>>>>>>>>>>>> rightConfXML : "+ rightConfXML);

        // 오른쪽
        param.replace("confgFg", kr.co.solbipos.base.common.enums.ConfgFg.FUNC_KEY_DELIVERY.getCode());
        String deliveryConfXML = mapper.getFuncKeyXml(param);

//        System.out.println(">>>>>>>>>>>>>>> rightConfXML : "+ rightConfXML);
        // 오른쪽
        param.replace("confgFg", kr.co.solbipos.base.common.enums.ConfgFg.FUNC_KEY_SELF.getCode());
        String selfConfXML = mapper.getFuncKeyXml(param);

//        System.out.println(">>>>>>>>>>>>>>> rightConfXML : "+ rightConfXML);

        // 포스기능키 XML 저장 (왼쪽)
        param.put("xml", leftConfXML);
        param.replace("confgFg", kr.co.solbipos.base.common.enums.ConfgFg.FUNC_KEY_LEFT.getCode());
        param.replace("posNo", storePosEnvVO.getTargetPosNo());

        procCnt = mapper.insertFuncKeyConfgXml(param);


        // 포스기능키 XML 조회 (오른쪽)
        param.replace("xml", rightConfXML);
        param.replace("confgFg", kr.co.solbipos.base.common.enums.ConfgFg.FUNC_KEY_RIGHT.getCode());
        param.replace("posNo", storePosEnvVO.getTargetPosNo());

        procCnt = mapper.insertFuncKeyConfgXml(param);

        // 포스기능키 XML 조회 (배달메뉴)
        param.replace("xml", deliveryConfXML);
        param.replace("confgFg", kr.co.solbipos.base.common.enums.ConfgFg.FUNC_KEY_DELIVERY.getCode());
        param.replace("posNo", storePosEnvVO.getTargetPosNo());

        procCnt = mapper.insertFuncKeyConfgXml(param);

        // 포스기능키 XML 조회 (셀프메뉴)
        param.replace("xml", selfConfXML);
        param.replace("confgFg", kr.co.solbipos.base.common.enums.ConfgFg.FUNC_KEY_SELF.getCode());
        param.replace("posNo", storePosEnvVO.getTargetPosNo());

        procCnt = mapper.insertFuncKeyConfgXml(param);


        return procCnt;
    }

    /** 포스 삭제 */
    @Override
    public int deletePos(StorePosEnvVO storePosEnvVO, SessionInfoVO sessionInfoVO) {
        String dt = currentDateTimeString();
        int procCnt = 0;

        storePosEnvVO.setRegDt(dt);
        storePosEnvVO.setRegId(sessionInfoVO.getUserId());
        storePosEnvVO.setModDt(dt);
        storePosEnvVO.setModId(sessionInfoVO.getUserId());

        // 포스 설치되어있는지 체크
        int installCnt = mapper.chkInstallPos(storePosEnvVO);

        if(installCnt > 0 ){
            throw new JsonException(Status.FAIL, messageService.get("storeManage.already.install.pos"));
        }

        // 매출자료가 있는지 체크 (TB_SL_SALE_HDR)
        int isSale = mapper.chkSaleYn(storePosEnvVO);

        if(isSale > 0 ){
            throw new JsonException(Status.FAIL, messageService.get("storeManage.already.sale.pos"));
        }

        // 포스 환경 삭제 (TB_MS_POS_ENVST)
        procCnt += mapper.deletePosEnv(storePosEnvVO);

        // 포스 기능키 삭제 (TB_MS_POS_FNKEY)
        procCnt += mapper.deletePosFunkey(storePosEnvVO);

        // 포스 마스터 삭제 (TB_MS_POS)
        procCnt += mapper.deletePosMaster(storePosEnvVO);

        return procCnt;
    }

    /** 주방프린터 목록 조회 */
    @Override
    public List<DefaultMap<String>> getKitchenPrintInfo(StoreEnvVO storeEnvVO) {
        return mapper.getKitchenPrintInfo(storeEnvVO);
    }

    /** 주방프린터 목록 저장 */
    @Override
    public int saveKitchenPrintInfo(KitchenPrintVO[] kitchenPrintVOs, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();
        int procCnt = 0;

        for(KitchenPrintVO kitchenPrintVO : kitchenPrintVOs){
            kitchenPrintVO.setRegDt(dt);
            kitchenPrintVO.setRegId(sessionInfoVO.getUserId());
            kitchenPrintVO.setModDt(dt);
            kitchenPrintVO.setModId(sessionInfoVO.getUserId());

            int result = 0;

            if(kitchenPrintVO.getStatus() == GridDataFg.INSERT) {
                result = mapper.insertKitchenPrint(kitchenPrintVO);
                if(result <= 0){
                    throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                } else {
                    procCnt ++;
                }
            }
            else if(kitchenPrintVO.getStatus() == GridDataFg.UPDATE) {
                result = mapper.updateKitchenPrint(kitchenPrintVO);
                if(result <= 0){
                    throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                } else {
                    procCnt ++;
                }
            }
            else if(kitchenPrintVO.getStatus() == GridDataFg.DELETE) {
                result = mapper.deleteKitchenPrint(kitchenPrintVO);
                if(result <= 0){
                    throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                } else {
                    procCnt ++;
                }
            }

        }

        return procCnt;
    }

    /** 주방프린터 출력/미출력 상품 조회 */
    @Override
    public List<StoreProductVO> getPrintProductInfo(StoreProductVO storeProductVO, UseYn useYn) {

        storeProductVO.setPrintYn(useYn);

        // 상품 목록 조회
        List<DefaultMap<String>> list = mapper.getPrintProductInfo(storeProductVO);

        // 검색 조건(상품명)이 있는 경우에만
        if(storeProductVO.getProdNm() != null && storeProductVO.getProdNm().length() > 0) {
            String str = "";
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    str = list.get(i).get("prodClassCd");
                } else {
                    if (str.indexOf(list.get(i).get("prodClassCd")) < 0) {
                        str += "," + list.get(i).get("prodClassCd");
                    }
                }
            }
            storeProductVO.setArrProdClassCd(str.split(","));
        }

        // 상품 분류 조회
        List<DefaultMap<String>> prodClassList = mapper.getProdClass(storeProductVO);

        // 상품 트리데이터 생성
        List<StoreProductVO> prodList = makeTreeData(prodClassList, list);

        return prodList;
    }

    /** 주방프린터 상품 조회 트리 데이터 생성  */
    public List<StoreProductVO> makeTreeData(List<DefaultMap<String>> prodClassLists, List<DefaultMap<String>> lists) {

        List<StoreProductVO> storeProductVOs = new ArrayList<StoreProductVO>();


        for(DefaultMap<String> prodClassList : prodClassLists ) {
            StoreProductVO storeProductVO = new StoreProductVO();

            storeProductVO.setProdClassCd(prodClassList.getStr("prodClassCd"));
            storeProductVO.setpProdClassCd(prodClassList.getStr("pProdClassCd"));

            storeProductVO.setProdCd(prodClassList.getStr("prodClassCd")); // 트리에서 상품 상위로 분류코드를 보여주기 위함
            storeProductVO.setProdNm(prodClassList.getStr("prodClassNm"));
            storeProductVO.setPrterNo(prodClassList.getStr("prterNo"));

            storeProductVO.setItems(new ArrayList<StoreProductVO>());
            storeProductVOs.add(storeProductVO);
        }

        Map<String, StoreProductVO> hm = new LinkedHashMap<String, StoreProductVO>();

        StoreProductVO child;
        StoreProductVO parent;

        // 분류
        for(StoreProductVO storeProductVO : storeProductVOs) {
            if(!hm.containsKey(storeProductVO.getProdClassCd())) {
                hm.put(storeProductVO.getProdClassCd(), storeProductVO);
            }

            child = hm.get(storeProductVO.getProdClassCd());
            if( child != null && !"".equals( storeProductVO.getpProdClassCd() ) && !DEFAULT_PRODUCT_CLASS.equals( storeProductVO.getpProdClassCd() )) {
                if(hm.containsKey( storeProductVO.getpProdClassCd() )) {
                    parent = hm.get(storeProductVO.getpProdClassCd());
                    parent.getItems().add(child);
                }
            }

            // 분류 하위 상품
            for(DefaultMap<String> list : lists ){
                if(child.getProdClassCd().equals(list.getStr("prodClassCd"))) {
                    StoreProductVO spVO = new StoreProductVO();
                    spVO.setStoreCd(list.getStr("storeCd"));
                    spVO.setProdCd(list.getStr("prodCd"));
                    spVO.setProdNm(list.getStr("prodNm"));
                    spVO.setProdClassCd(list.getStr("prodClassCd"));
                    spVO.setPrterNo(list.getStr("prterNo"));
                    child.getItems().add(spVO);
                }
            }
        }

        List<StoreProductVO> returnData = new ArrayList<StoreProductVO>();
        for(StoreProductVO storeProductVO : hm.values()) {
            if(storeProductVO.getpProdClassCd() == null || "".equals(storeProductVO.getpProdClassCd()) || EXCLUSIVE_HQ_OFFICE.equals(storeProductVO.getpProdClassCd())) {
                returnData.add(storeProductVO);
            }
        }

        return returnData;
    }

    /** 주방프린터 연결상품 등록 및 삭제  */
    @Override
    public int saveKitchenPrintProduct(StoreProductVO[] storeProductVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(StoreProductVO storeProductVO : storeProductVOs){
            storeProductVO.setRegDt(dt);
            storeProductVO.setRegId(sessionInfoVO.getUserId());
            storeProductVO.setModDt(dt);
            storeProductVO.setModId(sessionInfoVO.getUserId());

            if(storeProductVO.getStatus() == GridDataFg.INSERT) {
                procCnt += mapper.insertKitchenPrintProduct(storeProductVO);
            }
            else if(storeProductVO.getStatus() == GridDataFg.DELETE) {
                procCnt += mapper.deleteKitchenPrintProduct(storeProductVO);
            }
        }

        if(procCnt == storeProductVOs.length) {
            return procCnt;
        }
        else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 터치키 복사할 본사 목록 조회 */
    @Override
    public List<DefaultMap<String>> getHqList() {
        return mapper.getHqList();
    }

//    /** 터치키 복사할 브랜드 목록 조회 */
//    @Override
//    public List<DefaultMap<String>> getHqBrandList(HqBrandVO hqBrandVO) {
//        return mapper.getHqBrandList(hqBrandVO);
//    }

    /** 터치키 복사할 매장 목록 조회 */
    @Override
    public List<DefaultMap<String>> getTouchKeyStoreList(HqManageVO hqManageVO) {
        return mapper.getTouchKeyStoreList(hqManageVO);
    }

    /** 설치포스 수 추가 */
    @Override
    public int savePosCnt(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        storeManageVO.setRegDt(dt);
        storeManageVO.setRegId(sessionInfoVO.getUserId());
        storeManageVO.setModDt(dt);
        storeManageVO.setModId(sessionInfoVO.getUserId());

        // 설치포스 MAX Pos No값 조회
        int maxPosNo = mapper.getInstPosCntMax(storeManageVO);

        // 포스 마스터 생성 (설치포수 개수만큼 포스 마스터 생성)
        if(!"".equals(storeManageVO.getInstallPosCnt())){
            int installPosCnt =  Integer.parseInt(storeManageVO.getInstallPosCnt());

            for(int i = maxPosNo+1; i <= maxPosNo + installPosCnt; i++){
                storeManageVO.setPosNo((i));
                procCnt += mapper.insertPosInfo(storeManageVO);
            }
        }

        return procCnt;

    }

    /** 매장코드 중복체크 */
    public int getStoreCdCnt(StoreManageVO storeManageVO){
        return mapper.getStoreCdCnt(storeManageVO);
    }

    /** 권한그룹복사를 위한 본사목록 조회 */
    public List<DefaultMap<String>> authHqList(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO) {

        // 총판인 경우, session의 AgencyCode 값 넣기
        if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY){
            storeManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            storeManageVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        // 본사인 경우, 본사목록에 본인만 나오도록
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
            storeManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            storeManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        }

        return mapper.authHqList(storeManageVO);
    }

    /** 권한그룹복사를 위한 매장목록 조회 */
    public List<DefaultMap<String>> authStoreList(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO) {

        // 총판인 경우, session의 AgencyCode 값 넣기
        if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY){
            storeManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            storeManageVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        return mapper.authStoreList(storeManageVO);
    }

    /** 사용 메뉴 */
    @Override
    public List<DefaultMap<String>> avlblMenu(StoreManageVO storeManageVO) {
        return mapper.avlblMenu(storeManageVO);
    }

    /** 미사용 메뉴 */
    @Override
    public List<DefaultMap<String>> beUseMenu(StoreManageVO storeManageVO) {
        return mapper.beUseMenu(storeManageVO);
    }

    /** 메뉴권한복사 */
    @Override
    public int copyAuth(StoreMenuVO storeMenuVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        storeMenuVO.setRegDt(dt);
        storeMenuVO.setRegId(sessionInfoVO.getUserId());
        storeMenuVO.setModDt(dt);
        storeMenuVO.setModId(sessionInfoVO.getUserId());

        // storeCd : 복사 대상이 되는 매장
        // copyStoreCd : 복사할 기준이 되는 매장

        // 1. 메뉴 권한 복사
        int authGrpCopy = mapper.copyAuth(storeMenuVO);
        if(authGrpCopy <= 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

        // 2. 기존 메뉴권한 예외값 삭제
        mapper.removeAuthAll(storeMenuVO);

        // 3. 메뉴 권한 예외값이 있는지 확인 후, 복사
        int authExpCopy = 0;
        List<DefaultMap<String>> excepList = mapper.exceptMenu(storeMenuVO);

        if(excepList != null && excepList.size() > 0){

            for (int i = 0; i < excepList.size(); i++) {

                storeMenuVO.setResrceCd(excepList.get(i).getStr("resrceCd"));

                if("E".equals(excepList.get(i).getStr("incldExcldFg"))){
                    storeMenuVO.setIncldExcldFg(IncldExcldFg.EXCLUDE);
                }else{
                    storeMenuVO.setIncldExcldFg(IncldExcldFg.INCLUDE);
                }
                storeMenuVO.setUseYn(excepList.get(i).getStr("useYn"));

                int result = mapper.copyAuthExcp(storeMenuVO);
                if(result <= 0){
                    throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
                } else {
                    authExpCopy ++;
                }
            }
        }

        return (authGrpCopy+authExpCopy);
    }

    /** 사용메뉴 등록 */
    @Override
    public int addAuth(StoreMenuVO[] storeMenus, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String insertDt = currentDateTimeString();

        for(StoreMenuVO storeMenu : storeMenus){

            storeMenu.setIncldExcldFg(IncldExcldFg.EXCLUDE);
            storeMenu.setRegDt(insertDt);
            storeMenu.setRegId(sessionInfoVO.getUserId());
            storeMenu.setModDt(insertDt);
            storeMenu.setModId(sessionInfoVO.getUserId());

            // 권한 추가 테이블에 있는지 조회 후, 사용중인 권한이 있으면 삭제
            int isAuth = mapper.isAuth(storeMenu);

            if(isAuth > 0) {
                procCnt = mapper.removeAuth(storeMenu);
            }
        }

        return procCnt;
    }

    /** 미사용메뉴 등록 */
    @Override
    public int removeAuth(StoreMenuVO[] storeMenus, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String insertDt = currentDateTimeString();

        for(StoreMenuVO storeMenu : storeMenus){
            storeMenu.setIncldExcldFg(IncldExcldFg.INCLUDE);
            storeMenu.setRegDt(insertDt);
            storeMenu.setRegId(sessionInfoVO.getUserId());
            storeMenu.setModDt(insertDt);
            storeMenu.setModId(sessionInfoVO.getUserId());

            // 권한 예외 테이블에 있는지 조회 후, 예외로 들어간 권한이 있으면 삭제
            int isAuth = mapper.isAuth(storeMenu);

            if(isAuth > 0) {
                procCnt = mapper.removeAuth(storeMenu);
            }

            // 권한 삭제 처리
            storeMenu.setIncldExcldFg(IncldExcldFg.EXCLUDE);
            procCnt = mapper.addAuth(storeMenu);

        }
        return procCnt;
    }

    /** 사업자번호 중복체크 */
    @Override
    public DefaultMap<String> bizNoCheckCount(StoreManageVO storeManageVO,  SessionInfoVO sessionInfoVO) {
//        System.out.println("test1111");

        DefaultMap<String> resultMap = new DefaultMap<String>();

        resultMap = mapper.bizNoCheckCount(storeManageVO);

        return resultMap;
    }

    /** 본사 상태구분 값 조회 */
    @Override
    public String getHqSysStatFg(StoreManageVO storeManageVO,  SessionInfoVO sessionInfoVO) {

        storeManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return mapper.getHqSysStatFg(storeManageVO);
    }

    /** 매장코드 8 자리 이상 사용하는 본사인지 조회 */
    @Override
    public String getUseDigit8Store(StoreManageVO storeManageVO,  SessionInfoVO sessionInfoVO) {

        storeManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return mapper.getUseDigit8Store(storeManageVO);
    }

    /** 웹 사용자 아이디 체크 */
    public EmpResult chkUserId(StoreManageVO storeManageVO){

        // 매장코드 수동입력 이면서, 8자 이상사용인 경우 웹 사용자 아이디 입력 체크함.
        // BBQ매장은 웹 사용자 아이디 생성시 숫자로만 생성도 가능하지만 그 외 매장은 영문자 필수로 넣어야함.
        // 따라서, BBQ 매장과 그 외 매장을 구분해서 아이디 체크를 해야함.
        // 공통코드 [123]ERP매장연동(BBQ)을 기준으로 아이디 체크 구분.

        if(storeManageVO.getErpLinkHq() != null && !"".equals(storeManageVO.getErpLinkHq())){
            if(CmmUtil.checkUserId2(storeManageVO.getUserId()) != EmpResult.SUCCESS) {
                return CmmUtil.checkUserId2(storeManageVO.getUserId());
            }
        }else{
            if(CmmUtil.checkUserId(storeManageVO.getUserId()) != EmpResult.SUCCESS) {
                return CmmUtil.checkUserId(storeManageVO.getUserId());
            }
        }

        // 웹사용자아이디 중복체크
        if(mapper.getUserIdCnt(storeManageVO) < 1) {
            return EmpResult.SUCCESS;
        }
        else {
            return EmpResult.USER_ID_DUPLICATE;
        }
    }

    /** ERP를 연동하는 본사인지 확인 */
    @Override
    public String getErpLinkHq(StoreManageVO storeManageVO,  SessionInfoVO sessionInfoVO) {

        storeManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return mapper.getErpLinkHq(storeManageVO);
    }

    /** ERP 연동 매장 조회 */
    @Override
    public List<DefaultMap<String>> getErpStore(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO) {
        storeManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return mapper.getErpStore(storeManageVO);
    }

    /** 매장 메인포스 제외, 나머지 포스는 서브포스로 변경 */
    @Override
    public int updateToSubPos(StorePosEnvVO storePosEnvVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        storePosEnvVO.setModDt(dt);
        storePosEnvVO.setModId(sessionInfoVO.getUserId());

        return mapper.updateToSubPos(storePosEnvVO);
    }

    /** 매장포스목록 조회 */
    @Override
    public List<DefaultMap<String>> getEnvPosList(StoreManageVO storeManageVO) {
        return mapper.getEnvPosList(storeManageVO);
    }

    /** 매장포스 중 메인포스로 사용할 포스 조회 */
    @Override
    public String getUseMainPos(StoreManageVO storeManageVO){
        return mapper.getUseMainPos(storeManageVO);
    }

    /** 매장포스 환경설정값 변경 */
    @Override
    public int updatePosEnvVal(StorePosEnvVO storePosEnvVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        storePosEnvVO.setModDt(dt);
        storePosEnvVO.setModId(sessionInfoVO.getUserId());

        return mapper.updatePosEnvVal(storePosEnvVO);
    }

    /** 매장의 환경설정값 조회 */
    @Override
    public String getStoreEnvVal(StoreManageVO storeManageVO){
        return mapper.getStoreEnvVal(storeManageVO);
    }
}
