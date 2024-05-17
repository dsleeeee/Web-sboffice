package kr.co.solbipos.base.price.chgCostPrice.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.price.chgCostPrice.service.ChgCostPriceService;
import kr.co.solbipos.base.price.chgCostPrice.service.ChgCostPriceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : ChgCostPriceServiceImpl.java
 * @Description : 기초관리 - 가격관리 - 원가임의변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.29  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.04.29
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("chgCostPriceService")
public class ChgCostPriceServiceImpl  implements ChgCostPriceService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ChgCostPriceMapper chgCostPriceMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public ChgCostPriceServiceImpl(ChgCostPriceMapper chgCostPriceMapper) {
        this.chgCostPriceMapper = chgCostPriceMapper;
    }

    /** 원가임의변경 원가 조회*/
    @Override
    public List<DefaultMap<String>> getCostPriceList(ChgCostPriceVO chgCostPriceVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        chgCostPriceVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        chgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        chgCostPriceVO.setUserId(sessionInfoVO.getUserId());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            chgCostPriceVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 본사
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 변경항목 선택에 따른 원가 조회
            if ("0".equals(chgCostPriceVO.getCostUprcType())) {
                //  본사 상품 마스터 원가 조회
                result = chgCostPriceMapper.getHqCostPriceList(chgCostPriceVO);
            } else {
                // 본사 수불 원가 조회
                result = chgCostPriceMapper.getHqIostockCostPriceList(chgCostPriceVO);
            }
        }

        // 매장
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            // 변경항목 선택에 따른 원가 조회
            if ("0".equals(chgCostPriceVO.getCostUprcType())) {
                // 매장 상품 마스터 원가 조회
                result = chgCostPriceMapper.getStoreCostPriceList(chgCostPriceVO);
            } else {
                // 매장 수불 원가 조회
                result = chgCostPriceMapper.getStoreIostockCostPriceList(chgCostPriceVO);
            }
        }

        return result;
    }

    /** 원가임의변경 원가 엑셀다운로드 조회*/
    @Override
    public List<DefaultMap<String>> getCostPriceExcelList(ChgCostPriceVO chgCostPriceVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        chgCostPriceVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        chgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        chgCostPriceVO.setUserId(sessionInfoVO.getUserId());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            chgCostPriceVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 본사
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 변경항목 선택에 따른 원가 엑셀다운로드 조회
            if ("0".equals(chgCostPriceVO.getCostUprcType())) {
                //  본사 상품 마스터 원가 엑셀다운로드 조회
                result = chgCostPriceMapper.getHqCostPriceExcelList(chgCostPriceVO);
            } else {
                // 본사 수불 원가 엑셀다운로드 조회
                result = chgCostPriceMapper.getHqIostockCostPriceExcelList(chgCostPriceVO);
            }
        }

        // 매장
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            // 변경항목 선택에 따른 원가 엑셀다운로드 조회
            if ("0".equals(chgCostPriceVO.getCostUprcType())) {
                // 매장 상품 마스터 원가 엑셀다운로드 조회
                result = chgCostPriceMapper.getStoreCostPriceExcelList(chgCostPriceVO);
            } else {
                // 매장 수불 원가 엑셀다운로드 조회
                result = chgCostPriceMapper.getStoreIostockCostPriceExcelList(chgCostPriceVO);
            }
        }

        return result;
    }

    /** 원가임의변경 원가 변경 */
    @Override
    public int saveCostPrice(ChgCostPriceVO[] chgCostPriceVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for (ChgCostPriceVO chgCostPriceVO : chgCostPriceVOs) {

            chgCostPriceVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            chgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            chgCostPriceVO.setRegDt(currentDt);
            chgCostPriceVO.setRegId(sessionInfoVO.getUserId());
            chgCostPriceVO.setModDt(currentDt);
            chgCostPriceVO.setModId(sessionInfoVO.getUserId());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                chgCostPriceVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            // 본사
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                // 변경항목 선택에 따른 원가 변경
                if ("0".equals(chgCostPriceVO.getCostUprcType())) {
                    //  본사 상품 마스터 원가 변경
                    result = chgCostPriceMapper.saveHqCostPrice(chgCostPriceVO);
                } else {
                    // 본사 수불 원가 변경
                    result = chgCostPriceMapper.saveHqIostockCostPrice(chgCostPriceVO);

                    // 기존원가와 수정원가가 다르면
                    if(!chgCostPriceVO.getbCostUprc().equals(chgCostPriceVO.getCostUprc())){
                        chgCostPriceVO.setProcFg("U"); // 진행상태

                        // 본사 수불 원가 변경에 따른 History 등록
                        chgCostPriceMapper.saveHqIostockCostPriceHistory(chgCostPriceVO);
                    }
                }
            }

            // 매장
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                // 변경항목 선택에 따른 원가 변경
                if ("0".equals(chgCostPriceVO.getCostUprcType())) {
                    //  매장 상품 마스터 원가 변경
                    result = chgCostPriceMapper.saveStoreCostPrice(chgCostPriceVO);
                } else {
                    // 매장 수불 원가 변경
                    result = chgCostPriceMapper.saveStoreIostockCostPrice(chgCostPriceVO);

                    // 기존원가와 수정원가가 다르면
                    if(!chgCostPriceVO.getbCostUprc().equals(chgCostPriceVO.getCostUprc())){
                        chgCostPriceVO.setProcFg("U"); // 진행상태

                        // 매장 수불 원가 변경에 따른 History 등록
                        chgCostPriceMapper.saveStoreIostockCostPriceHistory(chgCostPriceVO);
                    }
                }
            }
        }
        return result;
    }

    /** 원가임의변경 엑셀 양식다운로드 조회 */
    @Override
    public List<DefaultMap<String>> getCostPriceExcelUploadSampleList(ChgCostPriceVO chgCostPriceVO, SessionInfoVO sessionInfoVO){

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        chgCostPriceVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        chgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        chgCostPriceVO.setUserId(sessionInfoVO.getUserId());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            chgCostPriceVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 본사
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 변경항목 선택에 따른 엑셀 양식다운로드 조회
            if ("0".equals(chgCostPriceVO.getCostUprcType())) {
                //  본사 상품 마스터 원가 엑셀 양식다운로드 조회
                result = chgCostPriceMapper.getHqCostPriceExcelUploadSampleList(chgCostPriceVO);
            } else {
                // 본사 수불 원가 엑셀 양식다운로드 조회
                result = chgCostPriceMapper.getHqIostockCostPriceExcelUploadSampleList(chgCostPriceVO);
            }
        }

        // 매장
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            // 변경항목 선택에 따른 엑셀 양식다운로드 조회
            if ("0".equals(chgCostPriceVO.getCostUprcType())) {
                // 매장 상품 마스터 원가 엑셀 양식다운로드 조회
                result = chgCostPriceMapper.getStoreCostPriceExcelUploadSampleList(chgCostPriceVO);
            } else {
                // 매장 수불 원가 엑셀 양식다운로드 조회
                result = chgCostPriceMapper.getStoreIostockCostPriceExcelUploadSampleList(chgCostPriceVO);
            }
        }

        return result;
    };

    /** 원가임의변경 엑셀업로드 원가 업로드 임시테이블 전체 삭제 */
    @Override
    public int deleteCostPriceExcelUploadCheckAll(ChgCostPriceVO chgCostPriceVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        chgCostPriceVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        chgCostPriceVO.setSessionId(sessionInfoVO.getSessionId());
        chgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            chgCostPriceVO.setStoreCd(sessionInfoVO.getStoreCd());
        }


        procCnt += chgCostPriceMapper.deleteCostPriceExcelUploadCheckAll(chgCostPriceVO);

        return procCnt;
    }

    /** 원가임의변경 엑셀업로드 원가 업로드 임시테이블 삭제 */
    @Override
    public int deleteCostPriceExcelUploadCheck(ChgCostPriceVO[] chgCostPriceVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        for (ChgCostPriceVO chgCostPriceVO : chgCostPriceVOs) {
            chgCostPriceVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            chgCostPriceVO.setSessionId(sessionInfoVO.getSessionId());
            chgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                chgCostPriceVO.setStoreCd(sessionInfoVO.getStoreCd());
            }


            procCnt += chgCostPriceMapper.deleteCostPriceExcelUploadCheck(chgCostPriceVO);
        }

        return procCnt;
    }

    /** 원가임의변경 원가 업로드 임시테이블 저장 */
    @Override
    public int saveCostPriceExcelUploadCheck(ChgCostPriceVO[] chgCostPriceVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();

        for (ChgCostPriceVO chgCostPriceVO : chgCostPriceVOs) {
            chgCostPriceVO.setRegDt(currentDt);
            chgCostPriceVO.setRegId(sessionInfoVO.getUserId());
            chgCostPriceVO.setModDt(currentDt);
            chgCostPriceVO.setModId(sessionInfoVO.getUserId());

            chgCostPriceVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            chgCostPriceVO.setSessionId(sessionInfoVO.getSessionId());
            chgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                chgCostPriceVO.setStoreCd(sessionInfoVO.getStoreCd());
            }

            chgCostPriceVO.setSeq(i);

            chgCostPriceVO.setResult("검증전");

            procCnt += chgCostPriceMapper.saveCostPriceExcelUploadCheck(chgCostPriceVO);
            i++;
        }

        return procCnt;
    }

    /** 원가임의변경 원가 업로드 임시테이블 데이터 조회 */
    @Override
    public List<DefaultMap<String>> getCostPriceExcelUploadCheckList(ChgCostPriceVO chgCostPriceVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        chgCostPriceVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        chgCostPriceVO.setSessionId(sessionInfoVO.getSessionId());
        chgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            chgCostPriceVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 본사
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 변경항목 선택에 따른 임시테이블 데이터 조회
            if ("0".equals(chgCostPriceVO.getCostUprcType())) {
                // 본사 상품 마스터 원가 임시테이블 데이터 조회
                result = chgCostPriceMapper.getHqCostPriceExcelUploadCheckList(chgCostPriceVO);
            } else {
                // 본사 수불 원가 임시테이블 데이터 조회
                result = chgCostPriceMapper.getHqIostockCostPriceExcelUploadCheckList(chgCostPriceVO);
            }
        }

        // 매장
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            // 변경항목 선택에 따른 임시테이블 데이터 조회
            if ("0".equals(chgCostPriceVO.getCostUprcType())) {
                // 매장 상품 마스터 원가 임시테이블 데이터 조회
                result = chgCostPriceMapper.getStoreCostPriceExcelUploadCheckList(chgCostPriceVO);
            } else {
                // 매장 수불 원가 임시테이블 데이터 조회
                result = chgCostPriceMapper.getStoreIostockCostPriceExcelUploadCheckList(chgCostPriceVO);
            }
        }

        return result;
    }

    /** 원가임의변경 원가 업로드 검증결과 저장 */
    @Override
    public int saveCostPriceExcelUploadCheckResult(ChgCostPriceVO[] chgCostPriceVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        int i = 1;
        String currentDt = currentDateTimeString();
        String pattern = "^[0-9]*$"; //숫자만

        for (ChgCostPriceVO chgCostPriceVO : chgCostPriceVOs) {
            chgCostPriceVO.setRegDt(currentDt);
            chgCostPriceVO.setRegId(sessionInfoVO.getUserId());
            chgCostPriceVO.setModDt(currentDt);
            chgCostPriceVO.setModId(sessionInfoVO.getUserId());

            chgCostPriceVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            chgCostPriceVO.setSessionId(sessionInfoVO.getSessionId());
            chgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                chgCostPriceVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            chgCostPriceVO.setSeq(i);

            // 원가
            if (chgCostPriceVO.getCostUprc() != null && !"".equals(chgCostPriceVO.getCostUprc())) {
                if (Pattern.matches(pattern, chgCostPriceVO.getCostUprc())) {
                    chgCostPriceVO.setCostUprc(chgCostPriceVO.getCostUprc());
                } else {
                    chgCostPriceVO.setResult("원가는 숫자만 입력가능합니다.");
                }
            }

            // 본사
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                // 변경항목 선택에 따른 상품코드 존재여부 체크
                if ("0".equals(chgCostPriceVO.getCostUprcType())) {
                    // 본사 상품 상품코드 존재여부 체크
                    if (chgCostPriceMapper.getHqProdCdChk(chgCostPriceVO) > 0) {
                    } else {
                        chgCostPriceVO.setResult("존재하지 않는 상품코드입니다");
                    }
                } else {
                    // 본사 수불 상품코드 존재여부 체크
                    if (chgCostPriceMapper.getHqIostockProdCdChk(chgCostPriceVO) > 0) {
                    } else {
                        chgCostPriceVO.setResult("해당년월에 수불내역이 존재하지 않는 상품코드입니다");
                    }
                }
            }

            // 매장
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                // 변경항목 선택에 따른 상품코드 존재여부 체크
                if ("0".equals(chgCostPriceVO.getCostUprcType())) {
                    // 매장 상품 상품코드 존재여부 체크
                    if (chgCostPriceMapper.getStoreProdCdChk(chgCostPriceVO) > 0) {
                    } else {
                        chgCostPriceVO.setResult("존재하지 않는 상품코드입니다");
                    }
                } else {
                    // 매장 수불 상품코드 존재여부 체크
                    if (chgCostPriceMapper.getStoreIostockProdCdChk(chgCostPriceVO) > 0) {
                    } else {
                        chgCostPriceVO.setResult("해당년월에 수불내역이 존재하지 않는 상품코드입니다");
                    }
                }
            }

            if (chgCostPriceVO.getResult() == null || chgCostPriceVO.getResult() == "") {
                chgCostPriceVO.setResult("검증성공");
            }

            procCnt += chgCostPriceMapper.saveCostPriceExcelUploadCheck(chgCostPriceVO);
            i++;
        }

        return procCnt;
    }

    /** 원가임의변경 원가 엑셀업로드 저장 */
    @Override
    public int saveCostPriceExcelUpload(ChgCostPriceVO[] chgCostPriceVOs, SessionInfoVO sessionInfoVO){

        int result = 0;
        String currentDt = currentDateTimeString();

        for (ChgCostPriceVO chgCostPriceVO : chgCostPriceVOs) {

            chgCostPriceVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            chgCostPriceVO.setSessionId(sessionInfoVO.getSessionId());
            chgCostPriceVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                chgCostPriceVO.setStoreCd(sessionInfoVO.getStoreCd());
            }
            chgCostPriceVO.setRegDt(currentDt);
            chgCostPriceVO.setRegId(sessionInfoVO.getUserId());
            chgCostPriceVO.setModDt(currentDt);
            chgCostPriceVO.setModId(sessionInfoVO.getUserId());

            if (("검증성공").equals(chgCostPriceVO.getResult())) {

                // 본사
                if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                    // 변경항목 선택에 따른 원가 변경
                    if ("0".equals(chgCostPriceVO.getCostUprcType())) {
                        //  본사 상품 마스터 원가 변경
                        result = chgCostPriceMapper.saveHqCostPrice(chgCostPriceVO);
                    } else {
                        // 본사 수불 원가 변경
                        result = chgCostPriceMapper.saveHqIostockCostPrice(chgCostPriceVO);

                        // 기존원가와 수정원가가 다르면
                        if(!chgCostPriceVO.getbCostUprc().equals(chgCostPriceVO.getCostUprc())){
                            chgCostPriceVO.setProcFg("U"); // 진행상태

                            // 본사 수불 원가 변경에 따른 History 등록
                            chgCostPriceMapper.saveHqIostockCostPriceHistory(chgCostPriceVO);
                        }
                    }
                }

                // 매장
                if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                    // 변경항목 선택에 따른 원가 변경
                    if ("0".equals(chgCostPriceVO.getCostUprcType())) {
                        //  매장 상품 마스터 원가 변경
                        result = chgCostPriceMapper.saveStoreCostPrice(chgCostPriceVO);
                    } else {
                        // 매장 수불 원가 변경
                        result = chgCostPriceMapper.saveStoreIostockCostPrice(chgCostPriceVO);

                        // 기존원가와 수정원가가 다르면
                        if(!chgCostPriceVO.getbCostUprc().equals(chgCostPriceVO.getCostUprc())){
                            chgCostPriceVO.setProcFg("U"); // 진행상태

                            // 매장 수불 원가 변경에 따른 History 등록
                            chgCostPriceMapper.saveStoreIostockCostPriceHistory(chgCostPriceVO);
                        }
                    }
                }

                // 저장완료된 검증결과만 삭제
                result += chgCostPriceMapper.deleteCostPriceExcelUploadCheck(chgCostPriceVO);

            }
        }

        return result;
    }
}
