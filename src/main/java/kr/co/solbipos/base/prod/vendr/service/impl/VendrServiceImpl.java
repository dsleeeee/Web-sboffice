package kr.co.solbipos.base.prod.vendr.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.vendr.service.VendrService;
import kr.co.solbipos.base.prod.vendr.service.VendrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : TouchkeyServiceImpl.java
 * @Description : 기초관리 - 상품관리 - 거래처조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.08  노해민      최초생성
 *
 * @author NHN한국사이버결제 KCP 노해민
 * @since 2018. 08.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("vendrService")
public class VendrServiceImpl implements VendrService {

    private final MessageService messageService;
    private final VendrMapper vendrMapper;

    /** Constructor Injection */
    @Autowired
    public VendrServiceImpl(MessageService messageService, VendrMapper vendrMapper) {
        this.messageService = messageService;
        this.vendrMapper = vendrMapper;
    }


    /** 거래처 목록 조회 */
    @Override
    public List<DefaultMap<String>> list(VendrVO vendrVO, SessionInfoVO sessionInfoVO)
    {
        List<DefaultMap<String>> list = null;

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ)
        {
            vendrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            list = vendrMapper.getHqVendrList(vendrVO);

        }else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE)
        {
            vendrVO.setStoreCd(sessionInfoVO.getStoreCd());

            list = vendrMapper.getMsVendrList(vendrVO);

        }else
        {
            throw new JsonException(Status.FAIL, messageService.get("cmm.invalid.access"));
        }

        return list;
    }

    /** 거래처 상세정보 조회 */
    @Override
    public DefaultMap<String> dtlInfo(VendrVO vendrVO,SessionInfoVO sessionInfoVO) {

        DefaultMap<String> result = null;

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ)
        {
            vendrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            result = vendrMapper.getHqDtlInfo(vendrVO);

        }else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE)
        {
            vendrVO.setStoreCd(sessionInfoVO.getStoreCd());
            result = vendrMapper.getMsDtlInfo(vendrVO);
        }else
        {
            throw new JsonException(Status.FAIL, messageService.get("cmm.invalid.access"));
        }

        return result;
    }

    /** 거래처 등록 */
    @Override
    public int save(VendrVO vendrVO, SessionInfoVO sessionInfoVO) {

        int result = 0;

        /*
         * TODO
         * 1.배송구분 컬럼 SHIP_FG
         * 스토리보드상엔 입력받는 필드가 없으나
         * NULLABLE 컬럼이므로 수정 필요
         * */

        vendrVO.setShipFg("2");

        String currentDt = currentDateTimeString();

        vendrVO.setRegDt(currentDt);
        vendrVO.setRegId(sessionInfoVO.getUserId());
        vendrVO.setModDt(currentDt);
        vendrVO.setModId(sessionInfoVO.getUserId());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ)
        {
            vendrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            result = vendrMapper.insertHqVendr(vendrVO);

        }else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE)
        {
            //매장코드
            vendrVO.setStoreCd(sessionInfoVO.getStoreCd());

            result = vendrMapper.insertMsVendr(vendrVO);
        }else
        {
            throw new JsonException(Status.FAIL, messageService.get("cmm.invalid.access"));
        }

        return result;
    }

    /** 본사 수정 */
    @Override
    public int modify(VendrVO vendrVO, SessionInfoVO sessionInfoVO) {

        int result = 0;
        /*
         * TODO
         * 1. 배송구분 컬럼 SHIP_FG
         * 스토리보드상엔 입력받는 필드가 없으나
         * NULLABLE 컬럼이므로 수정 필요
         * */
        vendrVO.setShipFg("2");

        String currentDt = currentDateTimeString();

        vendrVO.setModDt(currentDt);
        vendrVO.setModId(sessionInfoVO.getUserId());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ)
        {
            vendrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            result = vendrMapper.modifyHqVendr(vendrVO);

        }else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE)
        {
            //매장코드
            vendrVO.setStoreCd(sessionInfoVO.getStoreCd());

            result = vendrMapper.modifyMsVendr(vendrVO);
        }else
        {
            throw new JsonException(Status.FAIL, messageService.get("cmm.invalid.access"));
        }


        return result;
    }

    /* 취급상품 조회 */
    @Override
    public List<DefaultMap<String>> vendrProdList(VendrVO vendrVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> list = null;

        /**
         * TODO
         * 1. 상품 검색 기준, 확인필요
         * 로그인한 아이디의 본사/매장 여부에 다른
         * 검색조건(본사코드, 브랜드사코드/매장코드, 거래처코드) 필요 여부 확인
         * ※ 현재 적용되어있음, 불필요시 삭제!
         *
         * 2. 취급상품 리스트에서 삭제되었을 경우
         *     DELETE가 아닌 USE_YN 값 UPDATE
         * ex) TB_HQ_VENDOR_PROD.USE_YN = 'N'
         */

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ)
        {
            vendrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            list = vendrMapper.getHqVendrProdList(vendrVO);

        }else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE)
        {
            //매장코드
            vendrVO.setStoreCd(sessionInfoVO.getStoreCd());
            list = vendrMapper.getMsVendrProdList(vendrVO);
        }else
        {
            throw new JsonException(Status.FAIL, messageService.get("cmm.invalid.access"));
        }


        return list;
    }

    /* 미취급상품 조회 */
    @Override
    public List<DefaultMap<String>> prodList(VendrVO vendrVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> list = null;

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ)
        {
            //본사코드
            vendrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            list = vendrMapper.getHqProdList(vendrVO);

        }else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE)
        {
            //매장코드
            vendrVO.setStoreCd(sessionInfoVO.getStoreCd());
            list = vendrMapper.getMsProdList(vendrVO);
        }else
        {
            throw new JsonException(Status.FAIL, messageService.get("cmm.invalid.access"));
        }


        return list;
    }

    /** 미취급상품 등록 */
    @Override
    public int modifyProd(VendrVO[] vendrVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        String currentDt = currentDateTimeString();

        for(VendrVO vendrVO : vendrVOs){

            vendrVO.setRegDt(currentDt);
            vendrVO.setRegId(sessionInfoVO.getUserId());
            vendrVO.setModDt(currentDt);
            vendrVO.setModId(sessionInfoVO.getUserId());

            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ)
            {
                vendrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                vendrVO.setUseYn(UseYn.Y);

                procCnt += vendrMapper.mergeHqVendrProd(vendrVO);

            }else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE)
            {
                //매장코드
                vendrVO.setStoreCd(sessionInfoVO.getStoreCd());
                vendrVO.setUseYn(UseYn.Y);

                procCnt += vendrMapper.mergeMsVendrProd(vendrVO);
            }else
            {
                throw new JsonException(Status.FAIL, messageService.get("cmm.invalid.access"));
            }

        }

        return procCnt;
    }

    /** 취급상품 삭제 */
    @Override
    public int deleteProd(VendrVO[] vendrVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        String currentDt = currentDateTimeString();

        for(VendrVO vendrVO : vendrVOs){

            vendrVO.setModDt(currentDt);
            vendrVO.setModId(sessionInfoVO.getUserId());

            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ)
            {
                vendrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                vendrVO.setUseYn(UseYn.N);

                procCnt += vendrMapper.mergeHqVendrProd(vendrVO);

            }else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE)
            {
                //매장코드
                vendrVO.setStoreCd(sessionInfoVO.getStoreCd());
                vendrVO.setUseYn(UseYn.N);

                procCnt += vendrMapper.mergeMsVendrProd(vendrVO);
            }else
            {
                throw new JsonException(Status.FAIL, messageService.get("cmm.invalid.access"));
            }

        }

        return procCnt;
    }
}
