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
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyClassVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyService;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyStyleVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyVO;
import kr.co.solbipos.base.store.tableattr.enums.TouchKeyStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public List<DefaultMap<String>> getProductListForTouchKey(TouchKeyVO touchKeyVO) {
        return keyMapper.getProductListForTouchKey(touchKeyVO);
    }

    /** 터치키 스타일코드 목록 조회 */
    @Override
    public List<DefaultMap<String>> getTouchKeyStyleCdList() {
        return keyMapper.getTouchKeyStyleCdList();
    }

    /** 터치키 스타일 목록 조회 */
    @Override
    public List<DefaultMap<String>> getTouchKeyStyleList(TouchKeyStyleVO touchKeyStyleVO) {
        return keyMapper.getTouchKeyStyleList(touchKeyStyleVO);
    }

    /** 터치키 분류 페이지별 스타일 코드 조회 */
    @Override
    public String getTouchKeyPageStyleCd(TouchKeyClassVO touchKeyClassVO) {
        return keyMapper.getTouchKeyPageStyleCd(touchKeyClassVO);
    }


    private Object getKey(HashMap<String, String> m, Object value) {
        for (Object o : m.keySet()) {
            if (m.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    /**
     * 판매터치키 XML 정보 조회
     *
     * 상품명/상품금액 변경분 반영을 위해 xml 조회 후 해당 값 수정하여 반환한다.
     */
    @Override
    public String getTouchKeyXml(SessionInfoVO sessionInfoVO) {

        String result = "";
        // 상품정보 조회 : 판매터치키 갱신용
        TouchKeyVO touchKeyVO = new TouchKeyVO();
        touchKeyVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        touchKeyVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        touchKeyVO.setStoreCd(sessionInfoVO.getStoreCd());
        // 터치키에 등록되어있는 상품정보
        List<DefaultMap<String>> prodList = keyMapper.getTouchKeyProdInfoList(touchKeyVO);

        // XML 조회 : 판매터치키 구성정보
        DefaultMap<String> param = new DefaultMap<String>();
        param.put("orgnFg", sessionInfoVO.getOrgnFg().getCode());
        param.put("hqOfficeCd", sessionInfoVO.getHqOfficeCd());
        param.put("storeCd", sessionInfoVO.getStoreCd());
        param.put("confgFg", ConfgFg.TOUCH_KEY.getCode());
        // 터치키 구성정보가 저장되어있는 XML
        String xml = keyMapper.getTouchKeyXml(param);
        // | 를 기준으로 분류와 터치키 영역으로 나뉘어져있다.
        String[] xmls = xml.split("\\|");
        // XML 역 파싱 - 상품정보 변경 반영을 위해 DOM 파서로 파싱한다
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 공백 무시
        factory.setIgnoringElementContentWhitespace(true);

        try {

            DocumentBuilder builder = factory.newDocumentBuilder();
            //XML 문서 파싱
            Document document = builder.parse(new InputSource(new StringReader(xmls[1])));
            document.getDocumentElement().normalize();
            //루트 엘리먼트 객체 얻어오기
            NodeList mxCellList = document.getElementsByTagName("mxCell");
            String tukeyFg = "", styleStr= "", prodCd = "", prodNm= "", saleUprc;
            String[] styleKeyValue, styles;
            DefaultMap<String> prodInfo = new DefaultMap();
            //정규식 패턴 설정
            Pattern prodCdPattern = Pattern.compile("prodCd=([^=]*.(?=;))", Pattern.MULTILINE);
            Pattern tukeyFgPattern = Pattern.compile("tukeyFg=([^=]*.(?=;))", Pattern.MULTILINE);
            // 셀수만큼 처리 : 상품태그, 금액태그도 각각 1개의 셀로 본다
            for (int i = 0; i < mxCellList.getLength(); i++) {
                Node mxCellNode = mxCellList.item(i);
                Element cellElement = (Element)mxCellNode;
                styleStr = cellElement.getAttribute("style");
                Matcher tukeyFgMatcher = tukeyFgPattern.matcher(styleStr);
                // 정규식으로 상품코드, 터치키구분 추출
                if (tukeyFgMatcher.find()) {
                    Matcher prodCdMatcher = prodCdPattern.matcher(styleStr);
                    if (prodCdMatcher.find()) {
                        prodCd = prodCdMatcher.group(1);
                        // 상품코드로 해당 상품정보를 가져온다
                        prodInfo = getTouchKeyProdInfo(prodCd, prodList);
                    }
                    // 태그에서 상품코드 이용하여 상품정보 재설정
                    tukeyFg = tukeyFgMatcher.group(1);
                    if ( "02".equals(tukeyFg) ) {
                        // 상품명 설정
                        cellElement.setAttribute("value", prodInfo.get("prodNm"));
                    } else if ( "03".equals(tukeyFg) ) {
                        // 상품금액 설정
                        cellElement.setAttribute("value", String.valueOf(prodInfo.get("saleUprc")));
                    }
                }
            }

            result = xmls[0] + "|";
            // XML Document 문자열로 반환
            result += convertXMLDocumentToString(document);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;

    }

    /** 판매터치키 저장 상품정보 조회 */
    @Override
    public List<DefaultMap<String>> getTouchKeyProdInfoList(TouchKeyVO touchKeyVO) {
        return keyMapper.getTouchKeyProdInfoList(touchKeyVO);
    }

    private DefaultMap<String> getTouchKeyProdInfo(String prodCd, List<DefaultMap<String>> list) {
        DefaultMap<String> result = new DefaultMap();

        for (DefaultMap<String> obj : list) {
            if ( prodCd.equals(obj.get("prodCd")) ) {
                result = obj;
                break;
            }
        }

        return result;
    }

    /**
     * XML Document 변환
     *  > XML DOM 파싱하여 값 수정 후 String으로 반환하기 위한 메소드
     */
    private String convertXMLDocumentToString(Document document) {

        StringWriter sw = null;
        try {

            sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.transform(new DOMSource(document), new StreamResult(sw));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return sw.toString();
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
        List<TouchKeyClassVO> touchKeyClassVOS = parseXML(sessionInfoVO, xml);

        // 매장/본사의 현재 설정정보 삭제
        TouchKeyClassVO tcParams = new TouchKeyClassVO();
        tcParams.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        tcParams.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        tcParams.setStoreCd(sessionInfoVO.getStoreCd());
        // 매장/본사의 현재 설정정보 삭제
        keyMapper.deleteTouchKeyClass(tcParams);

        TouchKeyVO tParams = new TouchKeyVO();
        tParams.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        tParams.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        tParams.setStoreCd(sessionInfoVO.getStoreCd());
        keyMapper.deleteTouchKey(tParams);

        // 리스트의 아이템을 DB에 Merge
        for(TouchKeyClassVO touchKeyClassVO : touchKeyClassVOS) {

            // 터치 분류(그룹) 저장 파라미터 설정
            touchKeyClassVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            touchKeyClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            touchKeyClassVO.setStoreCd(sessionInfoVO.getOrgnCd());
            touchKeyClassVO.setRegId(sessionInfoVO.getUserId());
            // 터치 분류(그룹) 저장
            if( keyMapper.insertTouchKeyClass(touchKeyClassVO) != 1 ) {
                throw new BizException( messageService.get("label.modifyFail") );
            }

            for(TouchKeyVO touchKeyVO : touchKeyClassVO.getTouchs()) {
                // 터치키 저장 파라미터 설정
                touchKeyVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
                touchKeyVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                touchKeyVO.setStoreCd(sessionInfoVO.getOrgnCd());
                touchKeyVO.setRegId(sessionInfoVO.getUserId());
                // 터치키 저장
                if( keyMapper.insertTouchKey(touchKeyVO) != 1 ) {
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
    private List<TouchKeyClassVO> parseXML(SessionInfoVO sessionInfoVO, String xml) {

        String[] xmls = xml.split("\\|");
        List<TouchKeyClassVO> touchKeyClassVOS = new ArrayList<TouchKeyClassVO>();
        TouchKeyClassVO touchKeyClassVO = new TouchKeyClassVO();

        List<TouchKeyVO> touchKeyVOS = new ArrayList<TouchKeyVO>();

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

                touchKeyClassVO = new TouchKeyClassVO();
                // 터치키 그룹은 시즌,행사별 등 일종의 템플릿.
                // TODO : 터치키그룹 관리할 수 있는 화면 필요. ex.그룹키생성 : 20180919 노현수
                touchKeyClassVO.setTukeyGrpCd("01");
                touchKeyClassVO.setTukeyClassNm(String.valueOf(cell.getValue()));

                // 페이지 번호 계산 - 100*5
                Double pageNo = (touchKeyClassVO.getX() / 500) + 1;
                touchKeyClassVO.setPageNo(pageNo.intValue());
                // 분류의 페이지당 Rows ( 2 or 3 )
                String pageRows;
                if ( "H".equals(sessionInfoVO.getOrgnFg().getCode()) ) {
                    pageRows = cmmEnvUtil.getHqEnvst(sessionInfoVO, "0041");
                } else {
                    pageRows = cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1041");
                }
                touchKeyClassVO.setPageRows(Integer.parseInt(pageRows));

                // 좌표, 크기
                mxGeometry geo = cell.getGeometry();
                touchKeyClassVO.setX(geo.getX());
                touchKeyClassVO.setY(geo.getY());
                touchKeyClassVO.setWidth(geo.getWidth());
                touchKeyClassVO.setHeight(geo.getHeight());
                touchKeyClassVO.setInFg(InFg.STORE);

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
                                touchKeyClassVO.setTukeyClassCd(styleKeyValue[1]);
                                break;
                            case FONT_COLOR:
                                touchKeyClassVO.setFontColor(styleKeyValue[1]);
                                break;
                            case FILL_COLOR:
                                touchKeyClassVO.setFillColor(styleKeyValue[1]);
                                break;
                            case FONT_SIZE:
                                touchKeyClassVO.setFontSize(Integer.parseInt(styleKeyValue[1]));
                                break;
                            case STYLE_CD:
                                touchKeyClassVO.setStyleCd(styleKeyValue[1]);
                                break;
                            default:
                                break;
                        }
                    }
                }
                touchKeyClassVO.setRegDt(regDt);
                touchKeyVOS = getTouchKeyList(graphTouch, cell.getId(), touchKeyClassVO);
                touchKeyClassVO.setTouchs(touchKeyVOS);
                touchKeyClassVOS.add(touchKeyClassVO);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return touchKeyClassVOS;
    }

    /**
     * 레이어 id로 해당 레이어에 있는 터치키 List 추출
     * @param graph mxGraph
     * @param layerId String
     * @param touchKeyClassVO TouchKeyClassVO
     * @return List
     */
    private List<TouchKeyVO> getTouchKeyList(mxGraph graph, String layerId, TouchKeyClassVO touchKeyClassVO) {

        mxGraphModel model = (mxGraphModel) graph.getModel();

        List<TouchKeyVO> touchKeyVOS = new ArrayList<TouchKeyVO>();
        TouchKeyVO bTouchKeyVO = new TouchKeyVO();
        TouchKeyVO cTouchKeyVO = new TouchKeyVO();
        mxCell layer = (mxCell)model.getCell(layerId);

        Object[] cells = graph.getChildVertices(layer);
        for(Object obj : cells) {
            mxCell cell = (mxCell) obj;
            bTouchKeyVO = getTouchKeyInfo(cell, touchKeyClassVO, true);
            touchKeyVOS.add(bTouchKeyVO);
            // 버튼 + 상품태그 + 금액태그 추가
            if ( cell.getChildCount() > 0 ) {
                for(int i = 0; i < cell.getChildCount(); i++ ) {
                    mxCell child = (mxCell)cell.getChildAt(i);
                    cTouchKeyVO = getTouchKeyInfo(child, touchKeyClassVO, false);
                    // 하위속성은 상위버튼과 페이지번호 같다.
                    cTouchKeyVO.setPageNo(bTouchKeyVO.getPageNo());
                    touchKeyVOS.add(cTouchKeyVO);
                }
            }

        }
        return touchKeyVOS;
    }

    /**
     * 터치키 속성 설정 후 반환
     * @param cell mxCell
     * @param touchKeyClassVO TouchKeyClassVO
     * @param isButton boolean
     * @return TouchKeyVO
     */
    private TouchKeyVO getTouchKeyInfo(mxCell cell, TouchKeyClassVO touchKeyClassVO, boolean isButton) {

        TouchKeyVO result = new TouchKeyVO();
        result.setOrgnFg(touchKeyClassVO.getOrgnFg());
        result.setHqOfficeCd(touchKeyClassVO.getHqOfficeCd());
        result.setStoreCd(touchKeyClassVO.getStoreCd());
        result.setTukeyClassCd(touchKeyClassVO.getTukeyClassCd());

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
                    case STYLE_CD:
                        result.setStyleCd(styleKeyValue[1]);
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

        result.setInFg(touchKeyClassVO.getInFg());
        result.setRegDt(touchKeyClassVO.getRegDt());

        return result;
    }
}
