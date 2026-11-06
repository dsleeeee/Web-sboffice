package kr.co.solbipos.base.store.posfunc.service.impl;

import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;
import com.nhncorp.lucy.security.xss.XssPreventer;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.exception.BizException;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.common.enums.ConfgFg;
import kr.co.solbipos.base.store.posfunc.service.PosFuncService;
import kr.co.solbipos.base.store.posfunc.service.PosFuncVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : PosFuncServiceImpl.java
 * @Description : 기초관리 > 매장관리 > 포스기능정의
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.07.26  김지은      최초생성
 * @ 2026.11.04  김유승      인증관리 - 사원별 인증허용 조회·일괄 저장 추가
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 06.26
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("posFunService")
public class PosFuncServiceImpl implements PosFuncService{

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PosFuncMapper mapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public PosFuncServiceImpl(PosFuncMapper mapper, MessageService messageService) {
        this.mapper = mapper;
        this.messageService = messageService;
    }


    /** 포스목록 조회 */
    @Override
    public List<DefaultMap<String>> getPosList(PosFuncVO posFuncVO) {
        return mapper.getPosList(posFuncVO);
    }

    /** 포스기능목록 조회 */
    @Override
    public List<DefaultMap<String>> getPosFuncList(PosFuncVO posFuncVO) {
        return mapper.getPosFuncList(posFuncVO);
    }

    /** 포스기능상세 조회 */
    @Override
    public List<DefaultMap<String>> getPosConfDetail(PosFuncVO posFuncVO) {
        return mapper.getPosConfDetail(posFuncVO);
    }

    /** 포스기능상세 저장 */
    @Override
    public int savePosConf(PosFuncVO[] posFuncVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(PosFuncVO posFuncVO : posFuncVOs) {
            posFuncVO.setRegDt(dt);
            posFuncVO.setRegId(sessionInfoVO.getUserId());
            posFuncVO.setModDt(dt);
            posFuncVO.setModId(sessionInfoVO.getUserId());

            if(posFuncVO.getStatus() == GridDataFg.UPDATE){
                procCnt = mapper.savePosConf(posFuncVO);
                if(procCnt <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }
        }
        return procCnt;
    }

    /** 포스기능 복사 */
    @Override
    public int copyPosFunc(PosFuncVO posFuncVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String date = currentDateTimeString();

        posFuncVO.setRegDt(date);
        posFuncVO.setRegId(sessionInfoVO.getUserId());
        posFuncVO.setModDt(date);
        posFuncVO.setModId(sessionInfoVO.getUserId());

        // 기존 포스 기능 삭제
        procCnt = mapper.deletePosFunc(posFuncVO);
//        if(procCnt <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 포스 기능 복사
        procCnt = mapper.copyPosFunc(posFuncVO);
//        if(procCnt <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        // 1. 포스기능키 XML 정보 복사 (6020)
        // XML 정보 사용안함(20230302)
        /*DefaultMap<String> param = new DefaultMap<String>();

        param.put("storeCd", posFuncVO.getStoreCd());
        param.put("posNo", posFuncVO.getCopyPos());

        param.put("useYn", "Y");
        param.put("regDt", currentDateTimeString());
        param.put("regId", sessionInfoVO.getUserId());
        param.put("modDt", currentDateTimeString());
        param.put("modId", sessionInfoVO.getUserId());

        // 포스기능키 XML 조회
        param.put("confgFg", ConfgFg.FUNC_KEY_LEFT.getCode());
        String leftConfXML = mapper.getFuncKeyXml(param);

        param.replace("confgFg", ConfgFg.FUNC_KEY_RIGHT.getCode());
        String rightConfXML = mapper.getFuncKeyXml(param);

        param.replace("confgFg", ConfgFg.FUNC_KEY_DELIVERY.getCode());
        String deliveryConfXML = mapper.getFuncKeyXml(param);

        param.replace("confgFg", ConfgFg.FUNC_KEY_SELF.getCode());
        String selfConfXML = mapper.getFuncKeyXml(param);

        // 포스기능키 XML 저장 (왼쪽)
        param.put("xml", leftConfXML);
        param.replace("confgFg", ConfgFg.FUNC_KEY_LEFT.getCode());
        param.replace("posNo", posFuncVO.getTargetPos());

        procCnt = mapper.insertFuncKeyConfgXml(param);
//        if(procCnt <= 0) throw new JsonException(Status.FAIL, messageService.get("label.insertFail"));

        // 포스기능키 XML 조회 (오른쪽)
        param.replace("xml", rightConfXML);
        param.replace("confgFg", ConfgFg.FUNC_KEY_RIGHT.getCode());
        param.replace("posNo", posFuncVO.getTargetPos());

        procCnt = mapper.insertFuncKeyConfgXml(param);
//        if(procCnt <= 0) throw new JsonException(Status.FAIL, messageService.get("label.insertFail"));

        // 포스기능키 XML 조회 (배달메뉴)
        param.replace("xml", deliveryConfXML);
        param.replace("confgFg", ConfgFg.FUNC_KEY_DELIVERY.getCode());
        param.replace("posNo", posFuncVO.getTargetPos());

        procCnt = mapper.insertFuncKeyConfgXml(param);
//        if(procCnt <= 0) throw new JsonException(Status.FAIL, messageService.get("label.insertFail"));

        // 포스기능키 XML 조회 (셀프메뉴)
        param.replace("xml", selfConfXML);
        param.replace("confgFg", ConfgFg.FUNC_KEY_SELF.getCode());
        param.replace("posNo", posFuncVO.getTargetPos());

        procCnt = mapper.insertFuncKeyConfgXml(param);
//        if(procCnt <= 0) throw new JsonException(Status.FAIL, messageService.get("label.insertFail"));*/

        return procCnt;
    }

    /** 포스기능키 목록 조회 */
    @Override
    public List<DefaultMap<String>> getPosFuncKeyList(PosFuncVO posFuncVO, SessionInfoVO sessionInfoVO) {

        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        // 소속구분 설정
        posFuncVO.setHqOfficeCd(hqOfficeCd);

        return mapper.getPosFuncKeyList(posFuncVO);
    }

    /** 포스기능키 기존설정 조회 */
    @Override
    public String getFuncKeyXml(PosFuncVO posFuncVO) {

        String result = "";

        // XML 조회 : 판매터치키 구성정보
        DefaultMap<String> param = new DefaultMap<String>();
        param.put("storeCd", posFuncVO.getStoreCd());
        param.put("posNo", posFuncVO.getPosNo());
        // 포스기능키 : 좌측
        if ("6020".equals(posFuncVO.getFnkeyFg())) {
            param.put("confgFg", ConfgFg.FUNC_KEY_LEFT.getCode());
        // 포스기능키 : 우측
        } else if ("6021".equals(posFuncVO.getFnkeyFg())) {
            param.put("confgFg", ConfgFg.FUNC_KEY_RIGHT.getCode());
        // 포스기능키 : 배달메뉴
        } else if ("6022".equals(posFuncVO.getFnkeyFg())) {
            param.put("confgFg", ConfgFg.FUNC_KEY_DELIVERY.getCode());
        } else if ("6023".equals(posFuncVO.getFnkeyFg())) {
            param.put("confgFg", ConfgFg.FUNC_KEY_SELF.getCode());
        }

        // 터치키 구성정보가 저장되어있는 XML
        result = mapper.getFuncKeyXmlByData(param);

        return result;
    }

    /** 포스기능키 저장 */
    @Override
    public Result saveFunckey(PosFuncVO posFuncVO, SessionInfoVO sessionInfoVO) {

        // XML 저장
        /*DefaultMap<String> param = new DefaultMap<String>();
        param.put("storeCd", posFuncVO.getStoreCd());
        param.put("posNo", posFuncVO.getPosNo());
        param.put("confgFg", posFuncVO.getConfgFg());
        param.put("xml", posFuncVO.getXml());
        param.put("useYn", "Y");
        param.put("regDt", currentDateTimeString());
        param.put("regId", sessionInfoVO.getUserId());

        if( mapper.getFuncKeyXml(param) != null ) {
            if( mapper.updateFuncKeyConfgXml(param) != 1 ) {
                throw new BizException( messageService.get("cmm.saveFail") );
            }
        } else {
            if( mapper.insertFuncKeyConfgXml(param) != 1 ) {
                throw new BizException( messageService.get("label.insertFail") );
            }
        }*/

        //XML 분석, TouchClass, Touch Domain 생성
        //포스기능키 파싱 (TB_MS_POS_KEY)
        List<PosFuncVO> posFuncVOs = parseXML(posFuncVO, posFuncVO.getXml());

        // 매장의 현재 포스기능키 정보 삭제
        PosFuncVO params = new PosFuncVO();
        params.setStoreCd(posFuncVO.getStoreCd());
        params.setPosNo(posFuncVO.getPosNo());
        params.setFnkeyFg(posFuncVO.getFnkeyFg());
        // 매장의 현재 포스기능키 정보 삭제
        mapper.deletePosFuncKey(params);

        // 리스트의 아이템을 DB에 Merge
        String regDt = currentDateTimeString();
        for(PosFuncVO posfuncKeyVO : posFuncVOs) {
            // 포스기능키 저장 파라미터 설정
            posfuncKeyVO.setRegId(sessionInfoVO.getUserId());
            posfuncKeyVO.setRegDt(regDt);
            posfuncKeyVO.setModId(sessionInfoVO.getUserId());
            posfuncKeyVO.setModDt(regDt);
            // 포스기능키 저장
            if( mapper.insertPosFuncKey(posfuncKeyVO) != 1 ) {
                throw new BizException( messageService.get("cmm.saveFail") );
            }
        }

        return new Result(Status.OK);
    }

    /**
     * XML 파싱하여 포스기능키 항목 추출
     * @param xml 파싱대상XML
     * @return 포스기능키
     */
    private List<PosFuncVO> parseXML(PosFuncVO posFuncVO, String xml) {

        List<PosFuncVO> result = new ArrayList<PosFuncVO>();
        PosFuncVO params = new PosFuncVO();

        try {

            //포스기능키 파싱
            mxGraph graphClass = new mxGraph();
            Document docClass = mxXmlUtils.parseXml(XssPreventer.unescape(xml));
            mxCodec codecClass = new mxCodec(docClass);
            mxIGraphModel modelClass = graphClass.getModel();
            Element eltClass = docClass.getDocumentElement();
            codecClass.decode(eltClass, modelClass);

            mxCell cell = new mxCell();
            Object[] cells = graphClass.getChildVertices(graphClass.getDefaultParent());
            for(Object c : cells) {

                params = new PosFuncVO();
                params.setStoreCd(posFuncVO.getStoreCd());
                params.setPosNo(posFuncVO.getPosNo());
                params.setFnkeyFg(posFuncVO.getFnkeyFg());

                // 좌표, 크기
                cell = (mxCell) c;
                mxGeometry geo = cell.getGeometry();
                params.setX(geo.getX());
                params.setY(geo.getY());
                params.setWidth(geo.getWidth());
                params.setHeight(geo.getHeight());

                //스타일
                String styleStr = cell.getStyle();
                if(styleStr != null) {
                    String[] styles = styleStr.split(";");
                    for(String style : styles) {
                        String[] styleKeyValue = style.split("=");
                        if(styleKeyValue.length < 2) {
                            continue;
                        }
                        if ("fnkeyNo".equals(styleKeyValue[0])) {
                            params.setFnkeyNo(styleKeyValue[1]);
                            continue;
                        } else if ("dispSeq".equals(styleKeyValue[0])) {
                            params.setDispSeq(styleKeyValue[1]);
                            continue;
                        } else if ("styleCd".equals(styleKeyValue[0])) {
                            params.setStyleCd(styleKeyValue[1]);
                            continue;
                        }
                    }
                }
                result.add(params);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }





    /** 포스기능 인증목록 조회 */
    @Override
    public List<DefaultMap<String>> getPosConfAuthDetail(PosFuncVO posFuncVO) {
        return mapper.getPosConfAuthDetail(posFuncVO);
    }

    /** 포스기능 인증허용대상 조회 */
    @Override public List<DefaultMap<String>> getAuthEmpList(PosFuncVO posFuncVO) {
        return mapper.getAuthEmpList(posFuncVO);
    }

    /** 포스기능 인증관리 인증여부 저장 */
    @Override
    public int savePosAuthConf(PosFuncVO[] posFuncVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String dt = currentDateTimeString();

        for(PosFuncVO posFuncVO : posFuncVOs) {
            posFuncVO.setModDt(dt);
            posFuncVO.setModId(sessionInfoVO.getUserId());

            if(posFuncVO.getStatus() == GridDataFg.UPDATE){
                result = mapper.savePosAuthConf(posFuncVO);
            }
            if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        return result;
    }

    /** 포스기능 인증허용대상 저장 */
    @Override public int saveAuthEmp(PosFuncVO[] posFuncVOs, SessionInfoVO sessionInfoVO) {
        int procCnt = 0;
        String dt = currentDateTimeString();

        for(PosFuncVO posFuncVO : posFuncVOs) {

            posFuncVO.setRegDt(dt);
            posFuncVO.setRegId(sessionInfoVO.getUserId());
            posFuncVO.setModDt(dt);
            posFuncVO.setModId(sessionInfoVO.getUserId());

            procCnt = mapper.saveAuthEmp(posFuncVO);
            if(procCnt <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        }
        return procCnt;
    }

    /** 포스기능 인증관리 - 재직 사원별 인증허용 기능키 조회 */
    @Override
    public List<DefaultMap<String>> getAuthEmpFnkeyList(PosFuncVO posFuncVO) {
        return mapper.getAuthEmpFnkeyList(posFuncVO);
    }

    /**
     * 포스기능 인증관리 - 인증여부 + 인증허용 사원 일괄 저장
     * 사원번호가 없는 행은 기능키 인증여부(AUTH_YN), 사원번호가 있는 행은 사원 인증허용(USE_YN) 저장
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int savePosAuthEmpConf(PosFuncVO[] posFuncVOs, SessionInfoVO sessionInfoVO) {

        if(posFuncVOs == null || posFuncVOs.length == 0) {
            throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.not.modify"));
        }

        // 저장 전 전체 검사 (하나라도 오류면 저장하지 않음)
        String storeCd = posFuncVOs[0].getStoreCd();
        String fnkeyFg = posFuncVOs[0].getFnkeyFg();
        if(StringUtil.isEmpty(storeCd) || StringUtil.isEmpty(fnkeyFg)) {
            throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.input.fail"));
        }

        // 해당 매장·기능구분의 기능키, 재직 사원
        PosFuncVO searchVO = new PosFuncVO();
        searchVO.setStoreCd(storeCd);
        searchVO.setFnkeyFg(fnkeyFg);

        Set<String> fnkeyNoSet = new HashSet<>();
        for(DefaultMap<String> fnkey : mapper.getPosConfAuthDetail(searchVO)) {
            fnkeyNoSet.add(fnkey.getStr("fnkeyNo"));
        }
        Set<String> empNoSet = new HashSet<>();
        for(DefaultMap<String> emp : mapper.getAuthEmpFnkeyList(searchVO)) {
            empNoSet.add(emp.getStr("empNo"));
        }

        for(PosFuncVO posFuncVO : posFuncVOs) {
            if(!storeCd.equals(posFuncVO.getStoreCd()) || !fnkeyFg.equals(posFuncVO.getFnkeyFg())
                    || !fnkeyNoSet.contains(posFuncVO.getFnkeyNo())) {
                throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.input.fail") + "(" + messageService.get("posFunc.fnkeyNo") + ")");
            }
            if(!StringUtil.isEmpty(posFuncVO.getEmpNo()) && !empNoSet.contains(posFuncVO.getEmpNo())) {
                throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.input.fail") + "(" + messageService.get("posFunc.empNo") + ")");
            }
        }

        int procCnt = 0;
        String dt = currentDateTimeString();

        for(PosFuncVO posFuncVO : posFuncVOs) {

            posFuncVO.setRegDt(dt);
            posFuncVO.setRegId(sessionInfoVO.getUserId());
            posFuncVO.setModDt(dt);
            posFuncVO.setModId(sessionInfoVO.getUserId());

            if(StringUtil.isEmpty(posFuncVO.getEmpNo())) {
                // 기능키 인증여부
                procCnt = mapper.savePosAuthConf(posFuncVO);
            } else {
                // 사원 인증허용
                procCnt = mapper.saveAuthEmp(posFuncVO);
            }
            if(procCnt <= 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
        }
        return procCnt;
    }
}
