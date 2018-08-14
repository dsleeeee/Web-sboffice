package kr.co.solbipos.store.manage.storemanage.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.pos.confg.loginstatus.enums.SysStatFg;
import kr.co.solbipos.store.hq.brand.service.HqBrandVO;
import kr.co.solbipos.store.manage.storemanage.service.*;
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

    @Autowired
    StoreManageMapper mapper;
    @Autowired
    MessageService messageService;

    /** 매장 목록 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(StoreManageVO storeManageVO) {
        return mapper.getStoreList(storeManageVO);
    }

    /** 매장정보 상세조회 */
    @Override
    public Map<String, Object> getStoreDetail(StoreManageVO storeManageVO) {

        Map<String,Object> result = new HashMap<String, Object>();

        // 매장 상세정보
        DefaultMap<String> storeDtlInfo = mapper.getStoreDetail(storeManageVO);

        // 벤사, 코너 조회
        List<DefaultMap<String>> vanCornrList =mapper.getVanCornrList(storeManageVO);

        // 설치 포스수 조회
        int instPosCnt = mapper.getInstPosCnt(storeManageVO);

        // 코너별 승인
        List<DefaultMap<String>> cornrApproveList = mapper.getCornrApproveList(storeManageVO);

        // 포스별 승인
        List<DefaultMap<String>> posApproveList = mapper.getPosApproveList(storeManageVO);

        result.put("storeDtlInfo", storeDtlInfo);
        result.put("vanCornrList", vanCornrList);
        result.put("instPosCnt", instPosCnt);
        result.put("cornrApproveList", cornrApproveList);
        result.put("posApproveList", posApproveList);

        return result;
    }

    /** 매장 콤보리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStoreComboList(StoreManageVO storeManageVO) {
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
    public int saveStoreInfo(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        storeManageVO.setRegDt(dt);
        storeManageVO.setModDt(dt);
        storeManageVO.setRegId(sessionInfoVO.getUserId());
        storeManageVO.setModId(sessionInfoVO.getUserId());

        if(SysStatFg.CLOSE == storeManageVO.getSysStatFg()) {
            storeManageVO.setSysClosureDate("99991231");
        } else  if(SysStatFg.DEMO == storeManageVO.getSysStatFg() ) {
            storeManageVO.setSysClosureDate("99991231");
        } else {
            storeManageVO.setSysClosureDate(currentDateString());
        }

        // 매장 신규 코드 조회
        String storeCd = mapper.getStoreCd(storeManageVO);

        storeManageVO.setStoreCd(storeCd);

        // 포스 프로그램 구분
        String posEnvValue = "";

        if(!"00000".equals(storeManageVO.getHqOfficeCd())) { // 프랜차이즈의 경우

            // 본사의 포스 프로그램 구분 조회
            posEnvValue = mapper.getPosEnvValue(storeManageVO);

            LOGGER.debug("posEnvValue : "+ posEnvValue);

            if(posEnvValue == null ){
                // 메세지 변경 필요 (본사의 환경[100]이 설정되지 않았습니다.)
                throw new JsonException(Status.FAIL, messageService.get("storeManage.require.regist.env100"));
            }
        } else {
            posEnvValue = "0";
        }

        // 마스터 관련 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■

        // 신규 매장정보 저장
        int procCnt = mapper.saveStoreInfo(storeManageVO);

        // 기본 사용자 등록
        procCnt += mapper.insertStoreDefaultUser(storeManageVO);

        // 포스 출력물 마스터 등록
        if("00000".equals(storeManageVO.getHqOfficeCd())) { // 단독매장
            procCnt += mapper.insertDefaultPrintTemplete(storeManageVO);
        } else {    // 프랜차이즈
            procCnt += mapper.insertHqPrintTemplete(storeManageVO);
        }

        // 포스 마스터 생성 (설치포수 개수만큼 포스 마스터 생성)
        if(!"".equals(storeManageVO.getInstallPosCnt())){
            int installPosCnt =  Integer.parseInt(storeManageVO.getInstallPosCnt());

            for(int i=0; i<installPosCnt; i++){
                storeManageVO.setPosNo((i+1));
                procCnt += mapper.insertPosInfo(storeManageVO);
            }
        }

        // 테이블 그룹 생성
        procCnt += mapper.insertTabGroup(storeManageVO);

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
                    procCnt += mapper.insertPosFunKeyInfo(storeManageVO);
                }

                // 외식환경 복사
                if( "foodEnv".equals(copyEnv[i]) ) {
                    procCnt += mapper.insertFoodEnvInfo(storeManageVO);
                }

                // 주방프린터 복사
                if( "kitchenPrint".equals(copyEnv[i]) ) {
                    procCnt += mapper.insertKitchenPrintEnvInfo(storeManageVO);
                }

                // 상품 복사 ?
                if( "product".equals(copyEnv[i]) ) {

                }

                // 판매가 복사
                if( "salePrice".equals(copyEnv[i]) ) {
                    procCnt += mapper.copySaleUprc(storeManageVO);
                }

                // 공급가 복사
                if( "supplyPrice".equals(copyEnv[i]) ) {
                    procCnt += mapper.updateSplyUprc(storeManageVO);
                }

                // 터치키 복사
                if( "touchKey".equals(copyEnv[i]) ) {

                    storeManageVO.setInFg("S");
                    procCnt += mapper.copyTouchkeyCls(storeManageVO);
                    procCnt += mapper.copyTouchkey(storeManageVO);

                    storeManageVO.setInFg("T");
                    procCnt += mapper.copyTouchkeyCls(storeManageVO);
                    procCnt += mapper.copyTouchkey(storeManageVO);
                }
            }
        }

        //TODO 판매가 테이블 생성시 추가
        if(!"00000".equals(storeManageVO.getHqOfficeCd())) { // 프랜차이즈
            // 프랜차이즈 설정 - 판매가 HD 복사
            // 프랜차이즈 설정 - 판매가 DT 복사
        }
        return procCnt;
    }

    /** 매장 정보 수정 */
    @Override
    public int updateStoreInfo(StoreManageVO storeManageVO, SessionInfoVO sessionInfoVO) {

        String dt = currentDateTimeString();

        storeManageVO.setModDt(dt);
        storeManageVO.setModId(sessionInfoVO.getUserId());

        if(SysStatFg.CLOSE == storeManageVO.getSysStatFg()) {
            storeManageVO.setSysClosureDate("99991231");
        } else {
            storeManageVO.setSysClosureDate(currentDateString());
        }

        // 매장 정보 수정
        int procCnt = mapper.updateStoreInfo(storeManageVO);


        return procCnt;
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
            }
        }
        return procCnt;
    }

    /** 매장 포스환경 정보 조회 */
    @Override
    public List<DefaultMap<String>> getPosEnvGroupList(StorePosEnvVO storePosEnvVO) {

        // 포스환경정보 조회시, 포스번호 없으면 기본 포스번호 넣어줌.
        if("".equals(storePosEnvVO.getPosNo()) || storePosEnvVO.getPosNo() == null){
            storePosEnvVO.setPosNo("01");
        }
        return mapper.getPosEnvGroupList(storePosEnvVO);
    }

    /** 매장 포스 환경정보 저장 */
    @Override
    public int savePosConfig(StorePosEnvVO[] storePosEnvVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(StorePosEnvVO storePosEnvVO : storePosEnvVOs) {
            storePosEnvVO.setPosFg("W");    // WEB
            storePosEnvVO.setRegDt(dt);
            storePosEnvVO.setRegId(sessionInfoVO.getUserId());
            storePosEnvVO.setModDt(dt);
            storePosEnvVO.setModId(sessionInfoVO.getUserId());

            if(storePosEnvVO.getStatus() == GridDataFg.INSERT) {
                storePosEnvVO.setUseYn(UseYn.Y);

                // 환경정보 저장
                procCnt += mapper.insertPosConfig(storePosEnvVO);

                // 기능키 복사


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

    /** 포스그룹설정 selectBox 용 그룹목록 조회 */
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

            storePosEnvVO.setEnvstCd("540");
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
        storePosEnvVO.setEnvstCd("205");
        storePosEnvVO.setEnvstVal("0");
        procCnt += mapper.updatePosEnv(storePosEnvVO);

        // 2-1) TB_MS_POS 타겟 포스 데이터 삭제
        procCnt += mapper.deletePosTarget(storePosEnvVO);

        // 2-2) TB_MS_POS 데이터 insert
        procCnt += mapper.copyPosInfo(storePosEnvVO);

        // 기능키 복사
        // 1-1) TB_MS_POS_FNKEY 기존 데이터 삭제
        procCnt += mapper.deletePosFunkeyTarget(storePosEnvVO);

        // 1-2) TB_MS_POS_FNKEY select insert
        procCnt += mapper.copyPosFunKeyInfo(storePosEnvVO);

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

            if(kitchenPrintVO.getStatus() == GridDataFg.INSERT) {
                procCnt += mapper.insertKitchenPrint(kitchenPrintVO);
            }
            else if(kitchenPrintVO.getStatus() == GridDataFg.UPDATE) {
                procCnt += mapper.updateKitchenPrint(kitchenPrintVO);
            }
            else if(kitchenPrintVO.getStatus() == GridDataFg.DELETE) {
                procCnt += mapper.deleteKitchenPrint(kitchenPrintVO);
            }
        }

        return procCnt;
    }

    /** 추방프린터 출력/미출력 상품 조회 */
    @Override
    public List<StoreProductVO> getPrintProductInfo(StoreProductVO storeProductVO, UseYn useYn) {

        storeProductVO.setPrintYn(useYn);

        // 상품 분류 조회
        List<DefaultMap<String>> prodClassList = mapper.getProdClass(storeProductVO);

        // 상품 목록 조회
        List<DefaultMap<String>> list = mapper.getPrintProductInfo(storeProductVO);

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
            if( child != null && !"".equals( storeProductVO.getpProdClassCd() ) && !"0000".equals( storeProductVO.getpProdClassCd() )) {
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
            if(storeProductVO.getpProdClassCd() == null || "".equals(storeProductVO.getpProdClassCd()) || "00000".equals(storeProductVO.getpProdClassCd())) {
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

    /** 터치키 복사할 브랜드 목록 조회 */
    @Override
    public List<DefaultMap<String>> getHqBrandList(HqBrandVO hqBrandVO) {
        return mapper.getHqBrandList(hqBrandVO);
    }

    /** 터치키 복사할 매장 목록 조회 */
    @Override
    public List<DefaultMap<String>> getTouchKeyStoreList(HqBrandVO hqBrandVO) {
        return mapper.getTouchKeyStoreList(hqBrandVO);
    }
}
