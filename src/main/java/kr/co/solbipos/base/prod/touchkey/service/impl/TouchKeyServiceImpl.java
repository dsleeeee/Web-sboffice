package kr.co.solbipos.base.prod.touchkey.service.impl;

import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;
import com.nhncorp.lucy.security.xss.XssPreventer;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.exception.BizException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.common.enums.ConfgFg;
import kr.co.solbipos.base.common.enums.InFg;
import kr.co.solbipos.base.prod.touchkey.service.TouchClassVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyService;
import kr.co.solbipos.base.prod.touchkey.service.TouchVO;
import kr.co.solbipos.base.store.tableattr.enums.TouchKeyStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : TouchKeyServiceImpl.java
 * @Description : 기초관리 - 상품관리 - 판매터치키등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  조병준      최초생성
 * @ 2018.09.05  노현수      1차수정
 * @ 2018.09.19  노현수      메소드정리/분리
 *
 * @author NHN한국사이버결제 KCP 조병준
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("touchKeyService")
public class TouchKeyServiceImpl implements TouchKeyService {

    // logger
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    // Constructor Injection
    private final MessageService messageService;
    private final TouchKeyMapper keyMapper;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public TouchKeyServiceImpl(MessageService messageService, TouchKeyMapper keyMapper, CmmEnvUtil cmmEnvUtil) {
        this.messageService = messageService;
        this.keyMapper = keyMapper;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 상품목록 조회 : 판매터치키에서 사용 */
    @Override
    public List<DefaultMap<String>> getProductListForTouchKey(TouchVO touchVO) {
        return keyMapper.getProductListForTouchKey(touchVO);
    }

    /** 판매터치키 XML 정보 조회 */
    @Override
    public String getTouchKeyXml(SessionInfoVO sessionInfoVO) {
        DefaultMap<String> param = new DefaultMap<String>();
        param.put("orgnFg", sessionInfoVO.getOrgnFg().getCode());
        param.put("hqOfficeCd", sessionInfoVO.getHqOfficeCd());
        param.put("storeCd", sessionInfoVO.getStoreCd());
        param.put("confgFg", ConfgFg.TOUCH_KEY.getCode());
        return keyMapper.getTouchKeyXml(param);
    }

    /** 판매터치키 저장 */
    @Override
    public Result saveTouchkey(SessionInfoVO sessionInfoVO, String xml) {

        // XML 저장
        DefaultMap<String> param = new DefaultMap<String>();
        param.put("orgnFg", sessionInfoVO.getOrgnFg().getCode());
        param.put("hqOfficeCd", sessionInfoVO.getHqOfficeCd());
        param.put("storeCd", sessionInfoVO.getStoreCd());
        param.put("confgFg", ConfgFg.TOUCH_KEY.getCode());
        param.put("xml", xml);
        param.put("useYn", "Y");
        param.put("regDt", currentDateTimeString());
        param.put("regId", sessionInfoVO.getUserId());

        if( keyMapper.getTouchKeyXml(param) != null ) {
            if( keyMapper.updateTouchKeyConfgXml(param) != 1 ) {
                throw new BizException( messageService.get("label.modifyFail") );
            }
        }
        else {
            if( keyMapper.insertTouchKeyConfgXml(param) != 1 ) {
                throw new BizException( messageService.get("label.insertFail") );
            }
        }

        //XML 분석, TouchClass, Touch Domain 생성
        //터치키 분류 TABLE(TB_MS_TOUCH_CLASS)
        List<TouchClassVO> touchClassVOs = parseXML(sessionInfoVO, xml);

        // 매장/본사의 현재 설정정보 삭제
        TouchClassVO tcParams = new TouchClassVO();
        tcParams.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        tcParams.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        tcParams.setStoreCd(sessionInfoVO.getStoreCd());
        // 매장/본사의 현재 설정정보 삭제
        keyMapper.deleteTouchClass(tcParams);

        TouchVO tParams = new TouchVO();
        tParams.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        tParams.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        tParams.setStoreCd(sessionInfoVO.getStoreCd());
        keyMapper.deleteTouchKey(tParams);

        // 리스트의 아이템을 DB에 Merge
        for(TouchClassVO touchClassVO : touchClassVOs) {

            // 터치 분류(그룹) 저장 파라미터 설정
            touchClassVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            touchClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            touchClassVO.setStoreCd(sessionInfoVO.getOrgnCd());
            touchClassVO.setRegId(sessionInfoVO.getUserId());
            // 터치 분류(그룹) 저장
            if( keyMapper.insertTouchClass(touchClassVO) != 1 ) {
                throw new BizException( messageService.get("label.modifyFail") );
            }

            for(TouchVO touchVO : touchClassVO.getTouchs()) {
                // 터치키 저장 파라미터 설정
                touchVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
                touchVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                touchVO.setStoreCd(sessionInfoVO.getOrgnCd());
                touchVO.setRegId(sessionInfoVO.getUserId());
                // 터치키 저장
                if( keyMapper.insertTouchKey(touchVO) != 1 ) {
                    throw new BizException( messageService.get("label.modifyFail") );
                }
            }
        }

        return new Result(Status.OK);
    }

    /**
     * XML 파싱하여 판매터치키 항목 추출
     * @param xml 파싱대상XML
     * @return 테이블그룹객체
     */
    private List<TouchClassVO> parseXML(SessionInfoVO sessionInfoVO, String xml) {

        String[] xmls = xml.split("\\|");
//        LOGGER.info(XssPreventer.unescape(xmls[0]));
//        LOGGER.info(XssPreventer.unescape(xmls[1]));

        List<TouchClassVO> touchClassVOs = new ArrayList<TouchClassVO>();
        TouchClassVO touchClassVO = new TouchClassVO();

        List<TouchVO> touchVOs = new ArrayList<TouchVO>();

        try {

            //터치키분류 파싱
            mxGraph graphClass = new mxGraph();
            Document docClass = mxXmlUtils.parseXml(XssPreventer.unescape(xmls[0]));
            mxCodec codecClass = new mxCodec(docClass);
            mxIGraphModel modelClass = graphClass.getModel();
            Element eltClass = docClass.getDocumentElement();
            codecClass.decode(eltClass, modelClass);

            //터치키 파싱
            mxGraph graphTouch = new mxGraph();
            Document docTouch = mxXmlUtils.parseXml(XssPreventer.unescape(xmls[1]));
            mxCodec codecTouch = new mxCodec(docTouch);
            mxIGraphModel modelTouch = graphTouch.getModel();
            Element eltTouch = docTouch.getDocumentElement();
            codecTouch.decode(eltTouch, modelTouch);

            mxCell cell = new mxCell();
            String regDt = currentDateTimeString();
            Object[] cells = graphClass.getChildVertices(graphClass.getDefaultParent());
            for(Object c : cells) {
                cell = (mxCell) c;

                touchClassVO = new TouchClassVO();
                // 터치키 그룹은 시즌,행사별 등 일종의 템플릿.
                // TODO : 터치키그룹 관리할 수 있는 화면 필요. ex.그룹키생성 : 20180919 노현수
                touchClassVO.setTukeyGrpCd("01");
                touchClassVO.setTukeyClassNm(String.valueOf(cell.getValue()));

                // 페이지 번호 계산 - 100*5
                Double pageNo = (touchClassVO.getX() / 500) + 1;
                touchClassVO.setPageNo(pageNo.intValue());
                // 페이지당 Rows
                String pageRows = cmmEnvUtil.getHqEnvst(sessionInfoVO, "0018");
                touchClassVO.setPageRows(Integer.parseInt(pageRows));

                // 좌표, 크기
                mxGeometry geo = cell.getGeometry();
                touchClassVO.setX(geo.getX());
                touchClassVO.setY(geo.getY());
                touchClassVO.setWidth(geo.getWidth());
                touchClassVO.setHeight(geo.getHeight());
                touchClassVO.setInFg(InFg.STORE);

                //스타일
                String styleStr = cell.getStyle();
                if(styleStr != null) {
                    String[] styles = styleStr.split(";");
                    for(String style : styles) {

                        String[] styleKeyValue = style.split("=");
                        if(styleKeyValue.length < 2) {
                            continue;
                        }
                        switch(TouchKeyStyle.getEnum(styleKeyValue[0])) {
                            case CLASS_CD:
                                // 그룹코드는 layer 처리 문제로 커스텀태그의 값 활용하여 설정한다. : 20180920 노현수
                                touchClassVO.setTukeyClassCd(styleKeyValue[1]);
                                break;
                            case FONT_COLOR:
                                touchClassVO.setFontColor(styleKeyValue[1]);
                                break;
                            case FILL_COLOR:
                                touchClassVO.setFillColor(styleKeyValue[1]);
                                break;
                            case FONT_SIZE:
                                touchClassVO.setFontSize(Integer.parseInt(styleKeyValue[1]));
                                break;
                            default:
                                break;
                        }
                    }
                }
                touchClassVO.setRegDt(regDt);
                touchVOs = getTouchs(graphTouch, cell.getId(), touchClassVO);
                touchClassVO.setTouchs(touchVOs);
                touchClassVOs.add(touchClassVO);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return touchClassVOs;
    }

    /**
     * 레이어 id로 해당 레이어에 있는 터치키 List 추출
     * @param graph mxGraph
     * @param layerId String
     * @param touchClassVO TouchClassVO
     * @return List
     */
    private List<TouchVO> getTouchs(mxGraph graph, String layerId, TouchClassVO touchClassVO) {

        mxGraphModel model = (mxGraphModel) graph.getModel();

        List<TouchVO> touchVOs = new ArrayList<TouchVO>();
        TouchVO bTouchVO = null;
        TouchVO cTouchVO = null;
        mxCell layer = (mxCell)model.getCell(layerId);

        Object[] cells = graph.getChildVertices(layer);
        for(Object obj : cells) {
            mxCell cell = (mxCell) obj;
            bTouchVO = getTouchKeyInfo(cell, touchClassVO, true);
            touchVOs.add(bTouchVO);
            // 버튼 + 상품태그 + 금액태그 추가
            if ( cell.getChildCount() > 0 ) {
                for(int i = 0; i < cell.getChildCount(); i++ ) {
                    mxCell child = (mxCell)cell.getChildAt(i);
                    cTouchVO = getTouchKeyInfo(child, touchClassVO, false);
                    // 하위속성은 상위버튼과 페이지번호 같다.
                    cTouchVO.setPageNo(bTouchVO.getPageNo());
                    touchVOs.add(cTouchVO);
                }
            }

        }
        return touchVOs;
    }

    /**
     * 터치키 속성 설정 후 반환
     * @param cell mxCell
     * @param touchClassVO TouchClassVO
     * @param isButton boolean
     * @return TouchVO
     */
    private TouchVO getTouchKeyInfo(mxCell cell, TouchClassVO touchClassVO, boolean isButton) {

        TouchVO result = new TouchVO();
        result.setOrgnFg(touchClassVO.getOrgnFg());
        result.setHqOfficeCd(touchClassVO.getHqOfficeCd());
        result.setStoreCd(touchClassVO.getStoreCd());
        result.setTukeyClassCd(touchClassVO.getTukeyClassCd());

        //좌표, 크기
        mxGeometry geo = cell.getGeometry();
        result.setX((long)geo.getX());
        result.setY((long)geo.getY());
        result.setWidth((long)geo.getWidth());
        result.setHeight((long)geo.getHeight());

        //스타일
        String styleStr = cell.getStyle();
        if(styleStr != null) {
            String[] styles = styleStr.split(";");
            for(String style : styles) {

                String[] styleKeyValue = style.split("=");
                if(styleKeyValue.length < 2) {
                    continue;
                }
                switch(TouchKeyStyle.getEnum(styleKeyValue[0])) {
                    case TUKEY_CD:
                        result.setTukeyCd(styleKeyValue[1]);
                    case TUKEY_FG: // 터치키구분 - 01:버튼, 02:상품명태그, 03:금액태그
                        result.setTukeyFg(styleKeyValue[1]);
                    case PROD_CD:
                        result.setProdCd(styleKeyValue[1]);
                        break;
                    case FONT_COLOR:
                        result.setFontColor(styleKeyValue[1]);
                        break;
                    case FILL_COLOR:
                        result.setFillColor(styleKeyValue[1]);
                        break;
                    case FONT_SIZE:
                        result.setFontSize(Long.parseLong(styleKeyValue[1]));
                        break;
                    default:
                        break;
                }
            }
        }
        // 하위속성은 페이지번호 계산하지 않는다.
        if ( isButton ) {
            //페이지 번호 계산 - 100*5
            long pageNo = (long) (result.getX() / 500) + 1L;
            result.setPageNo(pageNo);
        }

        result.setInFg(touchClassVO.getOrgnFg());
        result.setRegDt(touchClassVO.getRegDt());

        return result;
    }
}
