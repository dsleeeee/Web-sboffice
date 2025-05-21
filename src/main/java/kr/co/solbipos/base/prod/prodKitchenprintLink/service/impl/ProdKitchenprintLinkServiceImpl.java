package kr.co.solbipos.base.prod.prodKitchenprintLink.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prodKitchenprintLink.service.ProdKitchenprintLinkService;
import kr.co.solbipos.base.prod.prodKitchenprintLink.service.ProdKitchenprintLinkVO;
import kr.co.solbipos.pos.loginstatus.enums.SysStatFg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ProdKitchenprintLinkServiceImpl.java
 * @Description : 기초관리 > 상품관리 > 상품-매장주방프린터연결
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.02.09  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.02.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodKitchenprintLinkService")
@Transactional
public class ProdKitchenprintLinkServiceImpl implements ProdKitchenprintLinkService {
    private final ProdKitchenprintLinkMapper prodKitchenprintLinkMapper; // 상품엑셀업로드
    private final PopupMapper popupMapper;

    private final String SYS_CLOSURE_DATE = "99991231"; // 시스템 종료일

    public ProdKitchenprintLinkServiceImpl(ProdKitchenprintLinkMapper prodKitchenprintLinkMapper, PopupMapper popupMapper) {
        this.prodKitchenprintLinkMapper = prodKitchenprintLinkMapper;
        this.popupMapper = popupMapper;
    }

    /** 상품목록 조회 */
    @Override
    public List<DefaultMap<String>> getProdList(@RequestBody ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO) {

        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        prodKitchenprintLinkVO.setHqOfficeCd(hqOfficeCd);
        prodKitchenprintLinkVO.setUserId(sessionInfoVO.getUserId());

        return prodKitchenprintLinkMapper.getProdList(prodKitchenprintLinkVO);
    }

    /* 연결된 프린터 */
    @Override
    public List<DefaultMap<String>> getLinkedList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO) {
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        prodKitchenprintLinkVO.setHqOfficeCd(hqOfficeCd);

        // 매장상태구분
        if(SysStatFg.CLOSE == prodKitchenprintLinkVO.getSysStatFg()) {
            prodKitchenprintLinkVO.setSysClosureDate(SYS_CLOSURE_DATE);
        } else  if(SysStatFg.DEMO == prodKitchenprintLinkVO.getSysStatFg() ) {
            prodKitchenprintLinkVO.setSysClosureDate(SYS_CLOSURE_DATE);
        } else {
            prodKitchenprintLinkVO.setSysClosureDate(currentDateString());
        }

        // 매장검색
        if(!StringUtil.getOrBlank(prodKitchenprintLinkVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodKitchenprintLinkVO.getStoreCd(), 3900));
            prodKitchenprintLinkVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return prodKitchenprintLinkMapper.getLinkedList(prodKitchenprintLinkVO);
    }

    /* 연결된 프린터 연결 해제 */
    @Override
    public int unlinkPrter(ProdKitchenprintLinkVO[] prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        for(ProdKitchenprintLinkVO Unlink : prodKitchenprintLinkVO){
            result += prodKitchenprintLinkMapper.unlinkPrter(Unlink);
        }
        return result;
    }

    /* 안연결된 프린터 */
    @Override
    public List<DefaultMap<String>> getUnlinkList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO) {
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        prodKitchenprintLinkVO.setHqOfficeCd(hqOfficeCd);

        // 매장상태구분
        if(SysStatFg.CLOSE == prodKitchenprintLinkVO.getSysStatFg()) {
            prodKitchenprintLinkVO.setSysClosureDate(SYS_CLOSURE_DATE);
        } else  if(SysStatFg.DEMO == prodKitchenprintLinkVO.getSysStatFg() ) {
            prodKitchenprintLinkVO.setSysClosureDate(SYS_CLOSURE_DATE);
        } else {
            prodKitchenprintLinkVO.setSysClosureDate(currentDateString());
        }

        // 매장검색
        if(!StringUtil.getOrBlank(prodKitchenprintLinkVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodKitchenprintLinkVO.getStoreCd(), 3900));
            prodKitchenprintLinkVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return prodKitchenprintLinkMapper.getUnlinkList(prodKitchenprintLinkVO);
    }

    /* 안연결된 프린터 연결 */
    @Override
    public int linkedPrter(ProdKitchenprintLinkVO[] prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();
        for(ProdKitchenprintLinkVO Linked : prodKitchenprintLinkVO){

            Linked.setRegDt(dt);
            Linked.setRegId(sessionInfoVO.getUserId());
            Linked.setModDt(dt);
            Linked.setModId(sessionInfoVO.getUserId());

            result += prodKitchenprintLinkMapper.linkedPrter(Linked);
        }
        return result;
    }

    // 그룹 조회
    @Override
    public List<DefaultMap<String>> getPrinterGroupList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO) {

        prodKitchenprintLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return prodKitchenprintLinkMapper.getPrinterGroupList(prodKitchenprintLinkVO);
    }

    // 그룹 저장
    @Override
    public int savePrinterGroup(ProdKitchenprintLinkVO[] prodKitchenprintLinkVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();
        for(ProdKitchenprintLinkVO prodKitchenprintLinkVO : prodKitchenprintLinkVOs){
            prodKitchenprintLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            prodKitchenprintLinkVO.setRegDt(dt);
            prodKitchenprintLinkVO.setRegId(sessionInfoVO.getUserId());
            prodKitchenprintLinkVO.setModDt(dt);
            prodKitchenprintLinkVO.setModId(sessionInfoVO.getUserId());

            if ( prodKitchenprintLinkVO.getStatus() == GridDataFg.INSERT ) { // 등록
                prodKitchenprintLinkVO.setPrinterGroupCd(prodKitchenprintLinkMapper.getPrinterGroupCode(prodKitchenprintLinkVO));

            }

            result += prodKitchenprintLinkMapper.savePrinterGroup(prodKitchenprintLinkVO);
        }
        return result;
    }

    // 매핑상품 조회
    @Override
    public List<DefaultMap<String>> getProdMapping(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO) {
        prodKitchenprintLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return prodKitchenprintLinkMapper.getProdMapping(prodKitchenprintLinkVO);
    }

    // 상품 조회
    @Override
    public List<DefaultMap<String>> getGroupProdList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO) {
        prodKitchenprintLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return prodKitchenprintLinkMapper.getGroupProdList(prodKitchenprintLinkVO);
    }

    // 상품 매핑 저장
    @Override
    public int saveProdMapping(ProdKitchenprintLinkVO[] prodKitchenprintLinkVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();

        List<DefaultMap<String>> printerList = new ArrayList<DefaultMap<String>>();

        for(ProdKitchenprintLinkVO prodKitchenprintLinkVO : prodKitchenprintLinkVOs){
            prodKitchenprintLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            prodKitchenprintLinkVO.setRegDt(dt);
            prodKitchenprintLinkVO.setRegId(sessionInfoVO.getUserId());
            prodKitchenprintLinkVO.setModDt(dt);
            prodKitchenprintLinkVO.setModId(sessionInfoVO.getUserId());

            if ( prodKitchenprintLinkVO.getStatus() == GridDataFg.INSERT ) { // 등록
                result += prodKitchenprintLinkMapper.saveProdMapping(prodKitchenprintLinkVO);

                // 실제 프린터-상품 연결 하기
                printerList = prodKitchenprintLinkMapper.getPrinterMapping(prodKitchenprintLinkVO);
                for (int i = 0; i < printerList.size(); i++) {
                    prodKitchenprintLinkVO.setStoreCd(printerList.get(i).get("storeCd"));
                    prodKitchenprintLinkVO.setPrterNo(printerList.get(i).get("prterNo"));

                    result += prodKitchenprintLinkMapper.linkedPrter(prodKitchenprintLinkVO);
                }
            } else if ( prodKitchenprintLinkVO.getStatus() == GridDataFg.DELETE ) { // 삭제
                result += prodKitchenprintLinkMapper.deleteProdMapping(prodKitchenprintLinkVO);
                // 실제 프린터-상품 연결도 끊기
                printerList = prodKitchenprintLinkMapper.getPrinterMapping(prodKitchenprintLinkVO);
                for (int i = 0; i < printerList.size(); i++) {
                    prodKitchenprintLinkVO.setStoreCd(printerList.get(i).get("storeCd"));
                    prodKitchenprintLinkVO.setPrterNo(printerList.get(i).get("prterNo"));

                    result += prodKitchenprintLinkMapper.unlinkPrter(prodKitchenprintLinkVO);
                }
            }
        }
        return result;
    }

    // 매핑프린터 조회
    @Override
    public List<DefaultMap<String>> getPrinterMapping(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO) {
        prodKitchenprintLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return prodKitchenprintLinkMapper.getPrinterMapping(prodKitchenprintLinkVO);
    }

    // 프린터 조회
    @Override
    public List<DefaultMap<String>> getPrinterList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO) {
        prodKitchenprintLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (prodKitchenprintLinkVO.getStoreHqBrandCd() == "" || prodKitchenprintLinkVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodKitchenprintLinkVO.getUserBrands().split(",");
                prodKitchenprintLinkVO.setUserBrandList(userBrandList);
            }
        }

        return prodKitchenprintLinkMapper.getPrinterList(prodKitchenprintLinkVO);
    }

    // 프린터 매핑 저장
    @Override
    public int savePrinterMapping(ProdKitchenprintLinkVO[] prodKitchenprintLinkVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String dt = currentDateTimeString();
        List<DefaultMap<String>> prodList = new ArrayList<DefaultMap<String>>();

        for(ProdKitchenprintLinkVO prodKitchenprintLinkVO : prodKitchenprintLinkVOs){
            prodKitchenprintLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            prodKitchenprintLinkVO.setRegDt(dt);
            prodKitchenprintLinkVO.setRegId(sessionInfoVO.getUserId());
            prodKitchenprintLinkVO.setModDt(dt);
            prodKitchenprintLinkVO.setModId(sessionInfoVO.getUserId());

            System.out.println("매장코드 확인 : " + prodKitchenprintLinkVO.getStoreCd());
            if ( prodKitchenprintLinkVO.getStatus() == GridDataFg.INSERT ) { // 등록
                result += prodKitchenprintLinkMapper.savePrinterMapping(prodKitchenprintLinkVO);
                // 실제 프린터-상품 연결 하기
                prodList = prodKitchenprintLinkMapper.getProdMapping(prodKitchenprintLinkVO);
                for (int i = 0; i < prodList.size(); i++) {
                    prodKitchenprintLinkVO.setProdCd(prodList.get(i).get("prodCd"));

                    result += prodKitchenprintLinkMapper.linkedPrter(prodKitchenprintLinkVO);
                }

            } else if ( prodKitchenprintLinkVO.getStatus() == GridDataFg.DELETE ) { // 삭제
                result += prodKitchenprintLinkMapper.deletePrinterMapping(prodKitchenprintLinkVO);
                // 실제 프린터-상품 연결도 끊기
                prodList = prodKitchenprintLinkMapper.getProdMapping(prodKitchenprintLinkVO);
                for (int i = 0; i < prodList.size(); i++) {
                    prodKitchenprintLinkVO.setProdCd(prodList.get(i).get("prodCd"));

                    result += prodKitchenprintLinkMapper.unlinkPrter(prodKitchenprintLinkVO);
                }
            }
        }
        return result;
    }

    /** 매장-상품 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreProdKitchenprintLinkList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO) {

        prodKitchenprintLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장검색
        if(!StringUtil.getOrBlank(prodKitchenprintLinkVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodKitchenprintLinkVO.getStoreCd(), 3900));
            prodKitchenprintLinkVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return prodKitchenprintLinkMapper.getStoreProdKitchenprintLinkList(prodKitchenprintLinkVO);
    }

    /** 매장-상품 탭 - 등록 상품 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreProdKitchenprintLinkProdList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO) {

        prodKitchenprintLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return prodKitchenprintLinkMapper.getStoreProdKitchenprintLinkProdList(prodKitchenprintLinkVO);
    }

    /** 매장-상품 탭 - 미등록 상품 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreProdKitchenprintLinkNoProdList(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO) {

        prodKitchenprintLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return prodKitchenprintLinkMapper.getStoreProdKitchenprintLinkNoProdList(prodKitchenprintLinkVO);
    }

    /** 매장-상품 탭 - 상품 저장 */
    @Override
    public int getStoreProdKitchenprintLinkSave(ProdKitchenprintLinkVO[] prodKitchenprintLinkVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(ProdKitchenprintLinkVO prodKitchenprintLinkVO : prodKitchenprintLinkVOs) {
            prodKitchenprintLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            prodKitchenprintLinkVO.setModDt(currentDt);
            prodKitchenprintLinkVO.setModId(sessionInfoVO.getUserId());

            if (prodKitchenprintLinkVO.getStatus() == GridDataFg.INSERT) {
                prodKitchenprintLinkVO.setRegDt(currentDt);
                prodKitchenprintLinkVO.setRegId(sessionInfoVO.getUserId());

                procCnt = prodKitchenprintLinkMapper.getStoreProdKitchenprintLinkSaveInsert(prodKitchenprintLinkVO);

            } else if (prodKitchenprintLinkVO.getStatus() == GridDataFg.DELETE) {
                procCnt = prodKitchenprintLinkMapper.getStoreProdKitchenprintLinkSaveDelete(prodKitchenprintLinkVO);
            }
        }

        return procCnt;
    }

    /** 매장-상품 탭 - 전체등록 저장 merge */
    @Override
    public int getStoreProdKitchenprintLinkAllRegistSave(ProdKitchenprintLinkVO prodKitchenprintLinkVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        prodKitchenprintLinkVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodKitchenprintLinkVO.setModDt(currentDt);
        prodKitchenprintLinkVO.setModId(sessionInfoVO.getUserId());
        prodKitchenprintLinkVO.setRegDt(currentDt);
        prodKitchenprintLinkVO.setRegId(sessionInfoVO.getUserId());

        procCnt = prodKitchenprintLinkMapper.getStoreProdKitchenprintLinkAllRegistSaveMerge(prodKitchenprintLinkVO);

        return procCnt;
    }
}