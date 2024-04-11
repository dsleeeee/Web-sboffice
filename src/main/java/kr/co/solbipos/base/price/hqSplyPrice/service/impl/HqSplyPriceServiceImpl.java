package kr.co.solbipos.base.price.hqSplyPrice.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.price.hqSplyPrice.service.HqSplyPriceService;
import kr.co.solbipos.base.price.hqSplyPrice.service.HqSplyPriceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : HqSplyPriceServiceImpl.java
 * @Description : 기초관리 - 가격관리 - 본사공급가관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.04  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.04.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("hqSplyPriceService")
public class HqSplyPriceServiceImpl implements HqSplyPriceService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final HqSplyPriceMapper hqSplyPriceMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public HqSplyPriceServiceImpl(HqSplyPriceMapper hqSplyPriceMapper, PopupMapper popupMapper) {
        this.hqSplyPriceMapper = hqSplyPriceMapper;
        this.popupMapper = popupMapper;
    }

    /** 본사 공급가관리 조회*/
    @Override
    public List<DefaultMap<String>> getHqSplyPriceList(HqSplyPriceVO hqSplyPriceVO, SessionInfoVO sessionInfoVO) {

        hqSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqSplyPriceVO.setUserId(sessionInfoVO.getUserId());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (hqSplyPriceVO.getProdHqBrandCd() == "" || hqSplyPriceVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (hqSplyPriceVO.getUserProdBrands() != null && !"".equals(hqSplyPriceVO.getUserProdBrands())) {
                    String[] userBrandList = hqSplyPriceVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        hqSplyPriceVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        return hqSplyPriceMapper.getHqSplyPriceList(hqSplyPriceVO);
    }

    /** 본사 공급가관리 엑셀다운로드 조회*/
    @Override
    public List<DefaultMap<String>> getHqSplyPriceExcelList(HqSplyPriceVO hqSplyPriceVO, SessionInfoVO sessionInfoVO) {

        hqSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqSplyPriceVO.setUserId(sessionInfoVO.getUserId());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 선택한 상품브랜드가 없을 때 (상품브랜드가 '전체' 일때)
            if (hqSplyPriceVO.getProdHqBrandCd() == "" || hqSplyPriceVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                if (hqSplyPriceVO.getUserProdBrands() != null && !"".equals(hqSplyPriceVO.getUserProdBrands())) {
                    String[] userBrandList = hqSplyPriceVO.getUserProdBrands().split(",");
                    if (userBrandList.length > 0) {
                        hqSplyPriceVO.setUserProdBrandList(userBrandList);
                    }
                }
            }
        }

        return hqSplyPriceMapper.getHqSplyPriceExcelList(hqSplyPriceVO);
    }

    /** 본사 공급가 저장 */
    @Override
    public int saveHqSplyPrice(HqSplyPriceVO[] hqSplyPriceVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for (HqSplyPriceVO hqSplyPriceVO : hqSplyPriceVOs) {

            hqSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqSplyPriceVO.setRegDt(currentDt);
            hqSplyPriceVO.setRegId(sessionInfoVO.getUserId());
            hqSplyPriceVO.setModDt(currentDt);
            hqSplyPriceVO.setModId(sessionInfoVO.getUserId());

            // 본사 공급가 변경
            result = hqSplyPriceMapper.saveHqSplyPrice(hqSplyPriceVO);

            // all - 전매장적용(판매가변경제한매장 미포함)
            // none - 미적용
            // tot - 전매장적용(판매가변경제한매장 포함)
            // choice - 선택한 매장만 적용
            if (hqSplyPriceVO.getApplyFg().equals("all") || hqSplyPriceVO.getApplyFg().equals("tot") || hqSplyPriceVO.getApplyFg().equals("choice")) {

                if (hqSplyPriceVO.getApplyFg().equals("choice")) {
                    if (!StringUtil.getOrBlank(hqSplyPriceVO.getSaveStoreCds()).equals("")) {
                        StoreVO storeVO = new StoreVO();
                        storeVO.setArrSplitStoreCd(CmmUtil.splitText(hqSplyPriceVO.getSaveStoreCds(), 3900));
                        hqSplyPriceVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
                    }
                }

                // 본사 공급가 변경에 따른 매장 공급가 변경
                hqSplyPriceMapper.saveStoreSplyPrice(hqSplyPriceVO);
            }
        }
        return result;
    }

    /** 본사 공급가 엑셀 양식다운로드 조회 */
    @Override
    public List<DefaultMap<String>> getHqSplyPriceExcelUploadSampleList(HqSplyPriceVO hqSplyPriceVO, SessionInfoVO sessionInfoVO) {

        hqSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqSplyPriceVO.setUserId(sessionInfoVO.getUserId());

        return hqSplyPriceMapper.getHqSplyPriceExcelUploadSampleList(hqSplyPriceVO);
    }

    /** 공급가 업로드 임시테이블 전체 삭제 */
    @Override
    public int deleteSplyPriceExcelUploadCheckAll(HqSplyPriceVO hqSplyPriceVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        hqSplyPriceVO.setSessionId(sessionInfoVO.getSessionId());
        hqSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        procCnt += hqSplyPriceMapper.deleteSplyPriceExcelUploadCheckAll(hqSplyPriceVO);

        return procCnt;
    }

    /** 공급가 업로드 임시테이블 삭제 */
    @Override
    public int deleteSplyPriceExcelUploadCheck(HqSplyPriceVO[] hqSplyPriceVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        for (HqSplyPriceVO hqSplyPriceVO : hqSplyPriceVOs) {
            hqSplyPriceVO.setSessionId(sessionInfoVO.getSessionId());
            hqSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

            procCnt += hqSplyPriceMapper.deleteSplyPriceExcelUploadCheck(hqSplyPriceVO);
        }

        return procCnt;
    }

    /** 공급가 업로드 임시테이블 저장 */
    @Override
    public int saveSplyPriceExcelUploadCheck(HqSplyPriceVO[] hqSplyPriceVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();

        for (HqSplyPriceVO hqSplyPriceVO : hqSplyPriceVOs) {
            hqSplyPriceVO.setRegDt(currentDt);
            hqSplyPriceVO.setRegId(sessionInfoVO.getUserId());
            hqSplyPriceVO.setModDt(currentDt);
            hqSplyPriceVO.setModId(sessionInfoVO.getUserId());

            hqSplyPriceVO.setSessionId(sessionInfoVO.getSessionId());
            hqSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqSplyPriceVO.setSeq(i);

            hqSplyPriceVO.setResult("검증전");

            procCnt += hqSplyPriceMapper.saveSplyPriceExcelUploadCheck(hqSplyPriceVO);
            i++;
        }

        return procCnt;
    }

    /** 공급가 업로드 임시테이블 데이터 조회 */
    @Override
    public List<DefaultMap<String>> getSplyPriceExcelUploadCheckList(HqSplyPriceVO hqSplyPriceVO, SessionInfoVO sessionInfoVO) {

        hqSplyPriceVO.setSessionId(sessionInfoVO.getSessionId());
        hqSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return hqSplyPriceMapper.getSplyPriceExcelUploadCheckList(hqSplyPriceVO);
    }

    /** 공급가 업로드 검증결과 저장 */
    @Override
    public int saveSplyPriceExcelUploadCheckResult(HqSplyPriceVO[] hqSplyPriceVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();
        String pattern = "^[0-9]*$"; //숫자만

        for (HqSplyPriceVO hqSplyPriceVO : hqSplyPriceVOs) {
            hqSplyPriceVO.setRegDt(currentDt);
            hqSplyPriceVO.setRegId(sessionInfoVO.getUserId());
            hqSplyPriceVO.setModDt(currentDt);
            hqSplyPriceVO.setModId(sessionInfoVO.getUserId());

            hqSplyPriceVO.setSessionId(sessionInfoVO.getSessionId());
            hqSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqSplyPriceVO.setSeq(i);

            // 공급가
            if (hqSplyPriceVO.getSplyUprc() != null && !"".equals(hqSplyPriceVO.getSplyUprc())) {
                if (Pattern.matches(pattern, hqSplyPriceVO.getSplyUprc())) {
                    hqSplyPriceVO.setSplyUprc(hqSplyPriceVO.getSplyUprc());
                } else {
                    hqSplyPriceVO.setResult("공급가는 숫자만 입력가능합니다.");
                }
            }

            // 상품코드 존재여부 체크
            if (hqSplyPriceMapper.getProdCdChk(hqSplyPriceVO) > 0) {
            } else {
                hqSplyPriceVO.setResult("존재하지 않는 상품코드입니다");
            }

            if (hqSplyPriceVO.getResult() == null || hqSplyPriceVO.getResult() == "") {
                hqSplyPriceVO.setResult("검증성공");
            }

            procCnt += hqSplyPriceMapper.saveSplyPriceExcelUploadCheck(hqSplyPriceVO);
            i++;
        }

        return procCnt;
    }

    /**
     * 본사 공급가 엑셀업로드 저장
     */
    @Override
    public int saveHqSplyPriceExcelUpload(HqSplyPriceVO[] hqSplyPriceVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for (HqSplyPriceVO hqSplyPriceVO : hqSplyPriceVOs) {

            hqSplyPriceVO.setSessionId(sessionInfoVO.getSessionId());
            hqSplyPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            hqSplyPriceVO.setRegDt(currentDt);
            hqSplyPriceVO.setRegId(sessionInfoVO.getUserId());
            hqSplyPriceVO.setModDt(currentDt);
            hqSplyPriceVO.setModId(sessionInfoVO.getUserId());

            if (("검증성공").equals(hqSplyPriceVO.getResult())) {

                // 본사 판매가 변경
                result = hqSplyPriceMapper.saveHqSplyPrice(hqSplyPriceVO);

                // all - 전매장적용(판매가변경제한매장 미포함)
                // none - 미적용
                // tot - 전매장적용(판매가변경제한매장 포함)
                // choice - 선택한 매장만 적용
                if (hqSplyPriceVO.getApplyFg().equals("all") || hqSplyPriceVO.getApplyFg().equals("tot") || hqSplyPriceVO.getApplyFg().equals("choice")) {

                    if (hqSplyPriceVO.getApplyFg().equals("choice")) {
                        if (!StringUtil.getOrBlank(hqSplyPriceVO.getSaveStoreCds()).equals("")) {
                            StoreVO storeVO = new StoreVO();
                            storeVO.setArrSplitStoreCd(CmmUtil.splitText(hqSplyPriceVO.getSaveStoreCds(), 3900));
                            hqSplyPriceVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
                        }
                    }

                    // 본사 공급가 변경에 따른 매장 공급가 변경
                    hqSplyPriceMapper.saveStoreSplyPrice(hqSplyPriceVO);
                }

                // 저장완료된 검증결과만 삭제
                result += hqSplyPriceMapper.deleteSplyPriceExcelUploadCheck(hqSplyPriceVO);

            }
        }

        return result;
    }
}
