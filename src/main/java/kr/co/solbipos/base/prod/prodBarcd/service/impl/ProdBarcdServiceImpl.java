package kr.co.solbipos.base.prod.prodBarcd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prodBarcd.service.ProdBarcdService;
import kr.co.solbipos.base.prod.prodBarcd.service.ProdBarcdVO;
import kr.co.solbipos.stock.adj.adj.service.impl.AdjMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : BarcdServiceImpl.java
 * @Description : 기초관리 - 상품관리 - 상품바코드등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.07.01  권지현       최초생성

 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.07.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodBarcdService")
public class ProdBarcdServiceImpl implements ProdBarcdService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MessageService messageService;
    private final ProdBarcdMapper prodBarcdMapper;
    private final CmmEnvUtil cmmEnvUtil;
    private final AdjMapper adjMapper; // 조정관리

    /** Constructor Injection */
    @Autowired
    public ProdBarcdServiceImpl(ProdBarcdMapper prodBarcdMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService, AdjMapper adjMapper) {
        this.prodBarcdMapper = prodBarcdMapper;
        this.cmmEnvUtil = cmmEnvUtil;
        this.messageService = messageService;
        this.adjMapper = adjMapper;
    }

    /** 상품목록 조회 */
    @Override
    public List<DefaultMap<String>> getProdList(@RequestBody ProdBarcdVO prodBarcdVO, SessionInfoVO sessionInfoVO) {

        String orgnFg = sessionInfoVO.getOrgnFg().getCode();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();

        // 소속구분 설정
        prodBarcdVO.setOrgnFg(orgnFg);
        prodBarcdVO.setHqOfficeCd(hqOfficeCd);
        prodBarcdVO.setStoreCd(storeCd);

        prodBarcdVO.setUserId(sessionInfoVO.getUserId());

        return prodBarcdMapper.getProdList(prodBarcdVO);
    }

    /** 상품목록 조회(엑셀다운로드용) */
    @Override
    public List<DefaultMap<String>> getProdExcelList(@RequestBody ProdBarcdVO prodBarcdVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        prodBarcdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodBarcdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodBarcdVO.setStoreCd(sessionInfoVO.getStoreCd());


        if (prodBarcdVO.getExcelGubun().equals("T")) { // 전체 엑셀다운로드시(T) 조회조건 날림
            prodBarcdVO.setProdCd(null);
            prodBarcdVO.setProdNm(null);
            prodBarcdVO.setProdClassCd(null);
            prodBarcdVO.setBarCd(null);
            prodBarcdVO.setUseYn(null);
            prodBarcdVO.setHqBrandNm(null);
            prodBarcdVO.setBarcdYn(null);
        }

        prodBarcdVO.setUserId(sessionInfoVO.getUserId());

        return prodBarcdMapper.getProdExcelList(prodBarcdVO);
    }

    /** 상품 상세정보 조회 */
    @Override
    public DefaultMap<String> getProdDetail(ProdBarcdVO prodBarcdVO, SessionInfoVO sessionInfoVO) {

        DefaultMap result = new DefaultMap<>();

        String orgnFg = sessionInfoVO.getOrgnFg().getCode();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();

        // 소속구분 설정
        prodBarcdVO.setOrgnFg(orgnFg);
        prodBarcdVO.setHqOfficeCd(hqOfficeCd);
        prodBarcdVO.setStoreCd(storeCd);

        // 상품상세정보 조회
        result = prodBarcdMapper.getProdDetail(prodBarcdVO);

        // 연결상품목록 조회
        List<DefaultMap<String>> linkedProdList = prodBarcdMapper.getLinkedProdList(prodBarcdVO);
        result.put("linkedProdList", linkedProdList);

        return result;
    }

    /** 바코드 중복체크 */
    public List<DefaultMap<String>> chkBarCd(ProdBarcdVO prodBarcdVO, SessionInfoVO sessionInfoVO) {

        prodBarcdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodBarcdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodBarcdVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return prodBarcdMapper.chkBarCd(prodBarcdVO);
    }

    /** 바코드 중복체크 */
    public List<DefaultMap<String>> chkBarCds(ProdBarcdVO[] prodBarcdVOs, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        for(ProdBarcdVO prodBarcdVO : prodBarcdVOs) {

            prodBarcdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            prodBarcdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                prodBarcdVO.setStoreCd(sessionInfoVO.getStoreCd());
                result = prodBarcdMapper.chkBarCds(prodBarcdVO);
            } else if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ){

                List<DefaultMap<String>> list = null;
                // 본사 바코드 등록 시 중복된 바코드 체크
                list = prodBarcdMapper.chkBarCdsHq(prodBarcdVO);
                if (list.size() != 0) {
                    // 중복된 바코드가 있음
                    if(StringUtil.getOrBlank(list.get(0).get("hqCnt")).equals("0") && StringUtil.getOrBlank(list.get(0).get("msCnt")).equals("1")){
                        // 중복된 바코드가 매장테이블에만 있을 경우 매장 데이터 삭제 후 저장
                        prodBarcdVO.setModDt(currentDateTimeString());
                        prodBarcdVO.setModId(sessionInfoVO.getUserId());
                        prodBarcdMapper.deleteProdBarcdStoreHq(prodBarcdVO);
                    } else {
                        // 그외 바코드 중복 메시지 창 띄움
                        result = prodBarcdMapper.chkBarCds(prodBarcdVO);
                    }
                }
            }
        }
        return result;
    }

    /** 바코드 저장 */
    @Override
    public int saveBarcd(ProdBarcdVO[] prodBarcdVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        int storeResult = 0;
        String currentDt = currentDateTimeString();

        for(ProdBarcdVO prodBarcdVO : prodBarcdVOs){
            prodBarcdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            prodBarcdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            prodBarcdVO.setStoreCd(sessionInfoVO.getStoreCd());
            prodBarcdVO.setRegDt(currentDt);
            prodBarcdVO.setRegId(sessionInfoVO.getUserId());
            prodBarcdVO.setModDt(currentDt);
            prodBarcdVO.setModId(sessionInfoVO.getUserId());

            // 수정(바코드 중복이 있는지 확인 후 중복은 저장X)
            if ( prodBarcdVO.getStatus() == GridDataFg.UPDATE ) {
                result += prodBarcdMapper.saveBarcd(prodBarcdVO);
                // 본사일때 매장바코드도 수정
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    storeResult = prodBarcdMapper.saveProdBarcdStore(prodBarcdVO);
                }
                // 삭제
            } else if ( prodBarcdVO.getStatus() == GridDataFg.DELETE ) {
                result += prodBarcdMapper.deleteBarcd(prodBarcdVO);
                // 본사일때 매장바코드도 삭제
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    storeResult = prodBarcdMapper.deleteProdBarcdStore(prodBarcdVO);
                }
            }
        }
        return result;
    }

    /** 검증결과 삭제 */
    @Override
    public int getExcelUploadCheckDeleteAll(ProdBarcdVO prodBarcdVO, SessionInfoVO sessionInfoVO) {
        int result = 0;

        prodBarcdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodBarcdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            prodBarcdVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        prodBarcdVO.setRegId(sessionInfoVO.getUserId());

        result = prodBarcdMapper.getExcelUploadCheckDeleteAll(prodBarcdVO);

        return result;
    }

    /** 검증결과 저장 */
    @Override
    public int getExcelUploadCheckSave(ProdBarcdVO[] prodBarcdVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        int i = 1;
        String currentDt = currentDateTimeString();

        for (ProdBarcdVO prodBarcdVO : prodBarcdVOs) {
            prodBarcdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            prodBarcdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                prodBarcdVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            prodBarcdVO.setRegDt(currentDt);
            prodBarcdVO.setRegId(sessionInfoVO.getUserId());
            prodBarcdVO.setModDt(currentDt);
            prodBarcdVO.setModId(sessionInfoVO.getUserId());
            prodBarcdVO.setSeq(i);

            if(prodBarcdVO.getProdCd() != null && prodBarcdVO.getProdCd().length() >= 1 ){

                List<DefaultMap<String>> list = prodBarcdMapper.chkExcelUpload(prodBarcdVO);

                if (prodBarcdVO.getBarCd() != null && prodBarcdVO.getBarCd().length() >= 1 && prodBarcdVO.getBarCd().getBytes(StandardCharsets.UTF_8).length > 40) {
                    prodBarcdVO.setResult("바코드 길이가 40byte를 넘습니다.");
                } else if (list.size() != 0) {
                    if (StringUtil.getOrBlank(list.get(0).get("cntProd")).equals("0")) {
                        prodBarcdVO.setResult("해당 상품이 존재하지 않습니다.");
                    } else if (!StringUtil.getOrBlank(list.get(0).get("cntBar")).equals("0")) {
                        prodBarcdVO.setResult("중복된 바코드가 있습니다.");
                    }
                } else {
                    prodBarcdVO.setResult("검증성공");
                }

                result = prodBarcdMapper.getExcelUploadCheckSave(prodBarcdVO);
            }
            i++;
        }
        return result;
    }

    /** 임시테이블 정보 조회 */
    @Override
    public List<DefaultMap<String>> getExcelList(ProdBarcdVO prodBarcdVO, SessionInfoVO sessionInfoVO) {
        // 소속구분 설정
        prodBarcdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodBarcdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        prodBarcdVO.setStoreCd(sessionInfoVO.getStoreCd());
        prodBarcdVO.setRegId(sessionInfoVO.getUserId());
        return prodBarcdMapper.getExcelList(prodBarcdVO);
    }

    /** 바코드 저장 */
    @Override
    public int saveBarcdExcel(ProdBarcdVO[] prodBarcdVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        int storeResult = 0;
        String currentDt = currentDateTimeString();

        for(ProdBarcdVO prodBarcdVO : prodBarcdVOs){
            prodBarcdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            prodBarcdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            prodBarcdVO.setStoreCd(sessionInfoVO.getStoreCd());

            prodBarcdVO.setRegDt(currentDt);
            prodBarcdVO.setRegId(sessionInfoVO.getUserId());
            prodBarcdVO.setModDt(currentDt);
            prodBarcdVO.setModId(sessionInfoVO.getUserId());

            List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();

            if(prodBarcdVO.getBarCd() != null) {
                list = prodBarcdMapper.chkExcelUpload(prodBarcdVO);

                if (prodBarcdVO.getBarCd().getBytes(StandardCharsets.UTF_8).length > 40) {
                    prodBarcdVO.setResult("바코드 길이가 40byte를 넘습니다.");
                } else if (list.size() != 0) {
                    if (StringUtil.getOrBlank(list.get(0).get("cntProd")).equals("0")) {
                         prodBarcdVO.setResult("해당 상품이 존재하지 않습니다.");
                    } else if (!StringUtil.getOrBlank(list.get(0).get("cntBar")).equals("0")) {
                        prodBarcdVO.setResult("중복된 바코드가 있습니다.");
                    }
                } else {
                    prodBarcdVO.setResult("검증성공");
                }

                if (prodBarcdVO.getResult().equals("검증성공")) {
                    result += prodBarcdMapper.saveBarcdExcel(prodBarcdVO);
                    // 본사일때 매장바코드도 수정
                    if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                        storeResult = prodBarcdMapper.saveProdBarcdStore(prodBarcdVO);
                    }
                }

            }
        }
        return result;
    }
}