package kr.co.solbipos.base.prod.touchkey.service.impl;

import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.exception.BizException;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
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

    private final MessageService messageService;
    private final TouchKeyMapper keyMapper;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public TouchKeyServiceImpl(MessageService messageService, TouchKeyMapper keyMapper, CmmEnvUtil cmmEnvUtil) {
        this.messageService = messageService;
        this.keyMapper = keyMapper;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 상품목록 조회 : 판매터치키에서 사용 */
    @Override
    public List<DefaultMap<String>> getProductListForTouchKey(TouchKeyVO touchKeyVO, SessionInfoVO sessionInfoVO) {

        String orgnFg = sessionInfoVO.getOrgnFg().getCode();
        String hqOfficeCd = sessionInfoVO.getHqOfficeCd();
        String storeCd = sessionInfoVO.getStoreCd();

        // 소속구분 설정
        touchKeyVO.setOrgnFg(orgnFg);
        touchKeyVO.setHqOfficeCd(hqOfficeCd);
        touchKeyVO.setStoreCd(storeCd);

        return keyMapper.getProductListForTouchKey(touchKeyVO);
    }

    /** 터치키 스타일코드 목록 조회 */
    @Override
    public List<DefaultMap<String>> getTouchKeyStyleCdList() {
        return keyMapper.getTouchKeyStyleCdList();
    }

    /** 터치키 스타일 목록 조회 */
    @Override
    public List<DefaultMap<String>> getTouchKeyStyleList(TouchKeyStyleVO touchKeyStyleVO, SessionInfoVO sessionInfoVO) {

        return keyMapper.getTouchKeyStyleList(touchKeyStyleVO);
    }

    /** 터치키 분류 페이지별 스타일 코드 조회 */
    @Override
    public String getTouchKeyPageStyleCd(SessionInfoVO sessionInfoVO, String tukeyGrpCd) {

        TouchKeyClassVO touchKeyClassVO = new TouchKeyClassVO();
        touchKeyClassVO.setTukeyGrpCd(tukeyGrpCd);
        touchKeyClassVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        touchKeyClassVO.setStoreCd(sessionInfoVO.getStoreCd());
        touchKeyClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        touchKeyClassVO.setPageNo(1);

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
    public String getTouchKeyXml(SessionInfoVO sessionInfoVO, String tukeyGrpCd) {

        String result = "";
        // 상품정보 조회 : 판매터치키 갱신용
        TouchKeyVO touchKeyVO = new TouchKeyVO();
        touchKeyVO.setTukeyGrpCd(tukeyGrpCd);
        touchKeyVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        touchKeyVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        touchKeyVO.setStoreCd(sessionInfoVO.getStoreCd());
        // 터치키에 등록되어있는 상품정보
        List<DefaultMap<String>> prodList = keyMapper.getTouchKeyProdInfoList(touchKeyVO);

        // XML 조회 : 판매터치키 구성정보
        DefaultMap<String> param = new DefaultMap<String>();
        param.put("tukeyGrpCd", tukeyGrpCd);
        param.put("orgnFg", sessionInfoVO.getOrgnFg().getCode());
        param.put("hqOfficeCd", sessionInfoVO.getHqOfficeCd());
        param.put("storeCd", sessionInfoVO.getStoreCd());
        param.put("confgFg", ConfgFg.TOUCH_KEY.getCode());
        // 터치키 구성정보가 저장되어있는 XML
        String xml = keyMapper.getTouchKeyXmlByData(param);
        if ( xml != null ) {
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

                // 엔터값 xml에서 지원하는 방식으로 치환
                result = xmls[0].replace("∧│enter│∧", "&#10;") + "|";
                // XML Document 문자열로 반환
                result += convertXMLDocumentToString(document);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        return result;

    }

    /** 판매터치키 저장 상품정보 조회 */
    @Override
    public List<DefaultMap<String>> getTouchKeyProdInfoList(TouchKeyVO touchKeyVO, SessionInfoVO sessionInfoVO) {
        // 소속구분 설정
        touchKeyVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        touchKeyVO.setStoreCd(sessionInfoVO.getStoreCd());
        touchKeyVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return keyMapper.getTouchKeyProdInfoList(touchKeyVO);
    }

    /** 판매터치키 저장 특정 상품정보 조회  */
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
    public Result saveTouchkey(SessionInfoVO sessionInfoVO, String xml, String tukeyGrpCd, String tukeyGrpNm) {

        boolean result = true;

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

        // XML 저장 처리 ( MERGE INTO )
        if(tukeyGrpCd != null && tukeyGrpCd.length() > 0){
            param.put("tukeyGrpCd", tukeyGrpCd);
            /*if ( keyMapper.getTouchKeyXml(param) != null ) {
                if( keyMapper.updateTouchKeyConfgXml(param) != 1 ) {
                    System.out.println("터치키 저장 에러 : updateTouchKeyConfgXml");
                    result = false;
                    throw new BizException( messageService.get("cmm.modifyFail") );
                }
            } else {
                tukeyGrpCd = keyMapper.getTouchKeyGrpCd(param);
                param.put("tukeyGrpCd", tukeyGrpCd);
                if( keyMapper.insertTouchKeyConfgXml(param) != 1 ) {
                    System.out.println("터치키 저장 에러 : insertTouchKeyConfgXml1");
                    result = false;
                    throw new BizException( messageService.get("cmm.registFail") );
                }
            }*/
        }else{
            tukeyGrpCd = keyMapper.getTouchKeyGrpCd(param);
            param.put("tukeyGrpCd", tukeyGrpCd);
            param.put("tukeyGrpNm", tukeyGrpNm);
            /*if( keyMapper.insertTouchKeyConfgXml(param) != 1 ) {
                System.out.println("터치키 저장 에러 : insertTouchKeyConfgXml2");
                result = false;
                throw new BizException( messageService.get("cmm.registFail") );
            }*/
            // TB_HQ/MS_TOUCH_KEY_GROUP 저장로직
            TouchKeyVO grpParams = new TouchKeyVO();
            grpParams.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            grpParams.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            grpParams.setStoreCd(sessionInfoVO.getStoreCd());
            grpParams.setTukeyGrpCd(tukeyGrpCd);
            grpParams.setTukeyGrpNm(tukeyGrpNm);
            grpParams.setModDt(currentDateTimeString());
            grpParams.setModId(sessionInfoVO.getUserId());
            grpParams.setRegDt(currentDateTimeString());
            grpParams.setRegId(sessionInfoVO.getUserId());
            keyMapper.saveGrpNm(grpParams);
        }

        //XML 분석, TouchClass, Touch Domain 생성
        //터치키 분류 TABLE(TB_MS_TOUCH_CLASS)
        List<TouchKeyClassVO> touchKeyClassVOS = parseXML(sessionInfoVO, xml, tukeyGrpCd);

        // 매장/본사의 현재 설정정보 삭제
        TouchKeyClassVO tcParams = new TouchKeyClassVO();
        tcParams.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        tcParams.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        tcParams.setStoreCd(sessionInfoVO.getStoreCd());
        tcParams.setTukeyGrpCd(tukeyGrpCd);
        String envstVal1248 = CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1248"), "0");

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE && envstVal1248.equals("2")){

            TouchKeyVO tParams = new TouchKeyVO();
            tParams.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            tParams.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            tParams.setStoreCd(sessionInfoVO.getStoreCd());
            tParams.setTukeyGrpCd(tukeyGrpCd);
            // 매장의 현재 터치키 정보 중 매장수정허용 터치키 삭제
            keyMapper.deleteTouchKey2(tParams);

        } else {

            // 매장/본사의 현재 설정정보 삭제
            // 매장/본사의 현재 터치키분류 정보 삭제
            keyMapper.deleteTouchKeyClass(tcParams);

            TouchKeyVO tParams = new TouchKeyVO();
            tParams.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            tParams.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            tParams.setStoreCd(sessionInfoVO.getStoreCd());
            tParams.setTukeyGrpCd(tukeyGrpCd);
            // 매장/본사의 현재 터치키 정보 삭제
            keyMapper.deleteTouchKey(tParams);

        }

        //저장할 터치키 분류 없는 경우 xml/터치키분류 기본값으로 변경
        if(touchKeyClassVOS.size() == 0)
        {
            //터치키 XML
            //keyMapper.deleteTouchKeyConfgXml(param);
            //keyMapper.defaultTouchKeyConfgXml(param);
            //터치키 분류
            tcParams.setRegId(sessionInfoVO.getUserId());
            keyMapper.defaultTouchKeyClass(tcParams);
        }

        // 리스트의 아이템을 DB에 Merge
        for(TouchKeyClassVO touchKeyClassVO : touchKeyClassVOS) {
            // 터치 분류(그룹) 저장 파라미터 설정
            touchKeyClassVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            touchKeyClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            touchKeyClassVO.setStoreCd(sessionInfoVO.getOrgnCd());
            touchKeyClassVO.setRegId(sessionInfoVO.getUserId());

            // 터치키 분류코드는 새로 생성한다.(MAX + 1, 데이터 기준으로 XML을 만들면서 실제 분류순서와 분류코드가 일치하지않아, 새 분류 생성시 오류 발생)
            DefaultMap<String> nParams = new DefaultMap<String>();
            nParams.put("orgnFg", sessionInfoVO.getOrgnFg().getCode());
            nParams.put("hqOfficeCd", sessionInfoVO.getHqOfficeCd());
            nParams.put("storeCd", sessionInfoVO.getStoreCd());
            nParams.put("tukeyGrpCd", tukeyGrpCd);

            String nTukeyClassCd="";
            int insertChk = keyMapper.insertTouchKeyChk(touchKeyClassVO);

            // 본사, envstVal1248 상관없음, [매장수정허용분류] 상관없이 수정
            // 매장, envstVal1248 0/1 이면 [매장수정허용분류] 상관없이 수정
            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ
                || sessionInfoVO.getOrgnFg() == OrgnFg.STORE && (envstVal1248.equals("0") || envstVal1248.equals("1"))) {

                nTukeyClassCd = keyMapper.getTouchKeyClassCd(nParams);
                touchKeyClassVO.setTukeyClassCd(nTukeyClassCd);

                // 터치 분류(그룹) 저장
                if (keyMapper.insertTouchKeyClass(touchKeyClassVO) != 1) {
                    System.out.println("터치키 저장 에러 : insertTouchKeyClass");
                    result = false;
                    throw new BizException(messageService.get("label.modifyFail"));
                }
            }
            // 매장, envstVal1248 2 이면 [매장수정허용분류] 상관없이 수정불가
            else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE && envstVal1248.equals("2")) {
                nTukeyClassCd = touchKeyClassVO.getTukeyClassCd();
                touchKeyClassVO.setTukeyClassCd(nTukeyClassCd);
            }

            for (TouchKeyVO touchKeyVO : touchKeyClassVO.getTouchs()) {
                // 터치키 저장 파라미터 설정
                touchKeyVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
                touchKeyVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
                touchKeyVO.setStoreCd(sessionInfoVO.getOrgnCd());
                touchKeyVO.setRegId(sessionInfoVO.getUserId());
                touchKeyVO.setTukeyGrpCd(tukeyGrpCd);
                touchKeyVO.setTukeyClassCd(nTukeyClassCd);

                // 본사 저장 시 228이 N인 분류의 터치키만 저장
                // 매장 저장 시 1248이 0/1 이거나 1248이 2일땐 228이 Y인 분류의 터치키만 저장
                if ((sessionInfoVO.getOrgnFg() == OrgnFg.HQ && insertChk == 0)
                    || (sessionInfoVO.getOrgnFg() == OrgnFg.STORE && (envstVal1248.equals("0") || envstVal1248.equals("1") || (envstVal1248.equals("2") && insertChk > 0)))){
                    // 터치키 저장
                    if (keyMapper.insertTouchKey(touchKeyVO) != 1) {
                        System.out.println("터치키 저장 에러 : insertTouchKey");
                        result = false;
                        throw new BizException(messageService.get("label.modifyFail"));
                    }
                }
            }
        }
        if(result){
            return new Result(Status.OK, tukeyGrpCd);
        } else {
            return new Result(Status.FAIL, tukeyGrpCd);
        }
    }

    /** 터치키미적용상품 */
    @Override
    public List<DefaultMap<String>> getNoTouchKey(TouchKeyVO touchKeyVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        touchKeyVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        touchKeyVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        touchKeyVO.setStoreCd(sessionInfoVO.getStoreCd());

        return keyMapper.getNoTouchKey(touchKeyVO);
    }

    /** 매장수정허용분류_조회 */
    @Override
    public List<DefaultMap<String>> getStoreModGrpList(TouchKeyVO touchKeyVO, SessionInfoVO sessionInfoVO) {

        touchKeyVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        touchKeyVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        touchKeyVO.setStoreCd(sessionInfoVO.getStoreCd());

        return keyMapper.getStoreModGrpList(touchKeyVO);
    }

    /** 매장수정허용분류_저장 */
    @Override
    public int saveStoreModGrp(TouchKeyVO[] touchKeyVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( TouchKeyVO touchKeyVO : touchKeyVOs ) {

            // 소속구분 설정
            touchKeyVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            // 기본입력정보 설정
            touchKeyVO.setRegDt(currentDt);
            touchKeyVO.setRegId(sessionInfoVO.getUserId());
            touchKeyVO.setModDt(currentDt);
            touchKeyVO.setModId(sessionInfoVO.getUserId());

            // 매장에 터치키 XML 정보 업데이트
            keyMapper.saveStoreModGrp(touchKeyVO);

        }

        if ( result >= 0 ) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 터치키그룹_조회 */
    @Override
    public List<DefaultMap<String>> getGrpList(TouchKeyVO touchKeyVO, SessionInfoVO sessionInfoVO) {

        touchKeyVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        touchKeyVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        touchKeyVO.setStoreCd(sessionInfoVO.getStoreCd());

        return keyMapper.getGrpList(touchKeyVO);
    }

    /** 터치키그룹 저장 */
    @Override
    public int saveGrpNm(TouchKeyVO[] touchKeyVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( TouchKeyVO touchKeyVO : touchKeyVOs ) {

            touchKeyVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            // 소속구분 설정
            touchKeyVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            touchKeyVO.setStoreCd(sessionInfoVO.getStoreCd());
            // 기본입력정보 설정
            touchKeyVO.setRegDt(currentDt);
            touchKeyVO.setRegId(sessionInfoVO.getUserId());
            touchKeyVO.setModDt(currentDt);
            touchKeyVO.setModId(sessionInfoVO.getUserId());

            // 매장에 터치키 XML 정보 업데이트
            keyMapper.saveGrpNm(touchKeyVO);

        }

        if ( result >= 0 ) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 분류 삭제 전 매장수정허용분류 체크 */
    @Override
    public int getDeleteClassChk(TouchKeyVO touchKeyVO, SessionInfoVO sessionInfoVO) {
        touchKeyVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return keyMapper.getDeleteClassChk(touchKeyVO);
    }

    /** 매장목록 조회 */
    @Override
    public List<DefaultMap<String>> getStoreList(TouchKeyVO touchKeyVO, SessionInfoVO sessionInfoVO) {

        // 소속구분 설정
        touchKeyVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        touchKeyVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return keyMapper.getStoreList(touchKeyVO);
    }

    /** 터치키 매장적용 */
    @Override
    public int saveTouchKeyToStore(TouchKeyVO[] TouchKeyVOs, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for ( TouchKeyVO touchKeyVO : TouchKeyVOs ) {

            // 소속구분 설정
            touchKeyVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            // 기본입력정보 설정
            touchKeyVO.setRegDt(currentDt);
            touchKeyVO.setRegId(sessionInfoVO.getUserId());
            touchKeyVO.setModDt(currentDt);
            touchKeyVO.setModId(sessionInfoVO.getUserId());

            sessionInfoVO.setStoreCd(touchKeyVO.getStoreCd());

            String envstVal1248 = CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1248"), "0");

            // 매장에 터치키 XML 정보 업데이트
            keyMapper.saveStoreConfgXml(touchKeyVO);
            touchKeyVO.setOrgnFg("S");
            // 기적용된 터치키 그룹 정보 삭제
            keyMapper.deleteTouchkeyGrp(touchKeyVO);
            // 기적용된 터치키 정보 삭제
            keyMapper.deleteTouchKeyClassToStore(touchKeyVO);
            // 1248이 0/1이면 전체 삭제
            // 1248이 2이면 228 Y 제외 삭제
            if(envstVal1248.equals("0") || envstVal1248.equals("1")) {
                keyMapper.deleteTouchKeyToStore(touchKeyVO);
            } else if(envstVal1248.equals("2")){
                keyMapper.deleteTouchKeyToStore2(touchKeyVO);
            }
            // 터치키 매장적용
            result  = keyMapper.mergeStoreEnvst(touchKeyVO);
            result  = keyMapper.insertTouchKeyGroupToStore(touchKeyVO);
            result += keyMapper.insertTouchKeyClassToStore(touchKeyVO);
            // 1248이 0/1이면 전체 삽입
            // 1248이 2이면 228 Y 제외 삽입
            if(envstVal1248.equals("0") || envstVal1248.equals("1")) {
                result += keyMapper.insertTouchKeyToStore(touchKeyVO);
            } else if(envstVal1248.equals("2")){
                result += keyMapper.insertTouchKeyToStore2(touchKeyVO);
            }
        }

        if ( result >= 0 ) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /**
     * XML 파싱하여 판매터치키 항목 추출
     * @param xml 파싱대상XML
     * @return 테이블그룹객체
     */
    private List<TouchKeyClassVO> parseXML(SessionInfoVO sessionInfoVO, String xml, String tukeyGrpCd) {

        String[] xmls = xml.split("\\|");
        List<TouchKeyClassVO> touchKeyClassVOS = new ArrayList<TouchKeyClassVO>();
        TouchKeyClassVO touchKeyClassVO = new TouchKeyClassVO();

        List<TouchKeyVO> touchKeyVOS = new ArrayList<TouchKeyVO>();

        try {

            //터치키분류 파싱
            mxGraph graphClass = new mxGraph();
            Document docClass = mxXmlUtils.parseXml(xmls[0]);
            mxCodec codecClass = new mxCodec(docClass);
            mxIGraphModel modelClass = graphClass.getModel();
            Element eltClass = docClass.getDocumentElement();
            codecClass.decode(eltClass, modelClass);

            //터치키 파싱
            mxGraph graphTouch = new mxGraph();
            Document docTouch = mxXmlUtils.parseXml(xmls[1]);
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
                //touchKeyClassVO.setTukeyGrpCd("01");
                touchKeyClassVO.setTukeyGrpCd(tukeyGrpCd);
                touchKeyClassVO.setTukeyClassNm(String.valueOf(cell.getValue()));

                // 분류의 페이지당 Rows ( 1 or 2 or 3 )
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

                // 페이지 번호 계산 - 100*5
                Double pageNo = Math.floor(touchKeyClassVO.getX() / 500) + 1d;
                if(pageNo < 1) {
                    pageNo = 1d;
                } else if(pageNo > 1) {
                    // 1페이지 초과 인경우 x좌표 재계산 > 포스에서는 페이지단위로 움직인다. : 20181102 노현수
                    Double posX = touchKeyClassVO.getX() - ( pageNo - 1 ) * 500 ;
                    touchKeyClassVO.setX(posX);
                }
                touchKeyClassVO.setPageNo(pageNo.intValue());

                //스타일
                String styleStr = cell.getStyle();
                if(styleStr != null) {

                    //스타일 비교값 조회
                    TouchKeyStyleVO params = new TouchKeyStyleVO();
                    params.setStyleCd(getStyleCd(styleStr));
                    params.setButtonFg("G");
                    params.setButtonTagFg("01");
                    DefaultMap<String> styleInfo = keyMapper.getTouchKeyStyleForCompare(params);

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
                            case STYLE_CD:
                                touchKeyClassVO.setStyleCd(styleKeyValue[1]);
                                break;
                            case FONT_SIZE:
                                if( Integer.valueOf(String.valueOf(styleInfo.get("fontSize"))) != Integer.parseInt(styleKeyValue[1])) {
                                    touchKeyClassVO.setFontSize(Integer.parseInt(styleKeyValue[1]));
                                }
                                break;
                            case FONT_COLOR:
                                if(!styleKeyValue[1].equals(styleInfo.get("fontOffColor"))) {
                                    touchKeyClassVO.setFontColor(styleKeyValue[1]);
                                }
                                break;
                            case FILL_COLOR:
                                if(!styleKeyValue[1].equals(styleInfo.get("buttonOffColor"))) {
                                    touchKeyClassVO.setFillColor(styleKeyValue[1]);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
                touchKeyClassVO.setRegDt(regDt);
                //해당 분류의 터치키 추출
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

        //터치키코드
        Integer tukeySeq = 1;

        mxGraphModel model = (mxGraphModel) graph.getModel();

        List<TouchKeyVO> touchKeyVOS = new ArrayList<TouchKeyVO>();
        TouchKeyVO pTouchKeyVO = new TouchKeyVO();
        TouchKeyVO cTouchKeyVO = new TouchKeyVO();
        mxCell layer = (mxCell)model.getCell(layerId);

        Object[] cells = graph.getChildVertices(layer);
        for(Object obj : cells) {

            mxCell cell = (mxCell) obj;
            // 터치키정보 반환
            pTouchKeyVO = getTouchKeyInfo(cell, touchKeyClassVO, true);
            // 터치키코드 생성 : sequential 처리
            pTouchKeyVO.setTukeyCd(String.format("%03d", tukeySeq++));

            touchKeyVOS.add(pTouchKeyVO);
            // 버튼 + 상품태그 + 금액태그 추가
            if ( cell.getChildCount() > 0 ) {
                for(int i = 0; i < cell.getChildCount(); i++ ) {
                    mxCell child = (mxCell)cell.getChildAt(i);
                    cTouchKeyVO = getTouchKeyInfo(child, touchKeyClassVO, false);
                    // 하위속성의 터치키코드 set
                    cTouchKeyVO.setTukeyCd(pTouchKeyVO.getTukeyCd());
                    // 하위속성은 상위버튼과 페이지번호 같다.
                    cTouchKeyVO.setPageNo(pTouchKeyVO.getPageNo());
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
        //result.setTukeyClassCd(touchKeyClassVO.getTukeyClassCd());

        //좌표, 크기
        mxGeometry geo = cell.getGeometry();
        result.setX((long)geo.getX());
        result.setY((long)geo.getY());
        result.setWidth((long)geo.getWidth());
        result.setHeight((long)geo.getHeight());

        //스타일
        String styleStr = cell.getStyle();
        if(styleStr != null) {

            //스타일 비교값 조회
            TouchKeyStyleVO params = new TouchKeyStyleVO();
            params.setStyleCd(getStyleCd(styleStr));
            params.setButtonFg("P");
            params.setButtonTagFg(getTukeyFg(styleStr));
            DefaultMap<String> styleInfo = keyMapper.getTouchKeyStyleForCompare(params);

            // 스타일코드 파싱
            String[] styles = styleStr.split(";");
            for(String style : styles) {
                // style 코드로 부가정보 생성
                String[] styleKeyValue = style.split("=");
                if(styleKeyValue.length < 2) {
                    continue;
                }
                switch(TouchKeyStyle.getEnum(styleKeyValue[0])) {
                    case TUKEY_FG: // 터치키구분 - 01:버튼, 02:상품명태그, 03:금액태그
                        result.setTukeyFg(styleKeyValue[1]);
                        break;
                    case PROD_CD:
                        result.setProdCd(styleKeyValue[1]);
                        break;
                    case STYLE_CD:
                        result.setStyleCd(styleKeyValue[1]);
                        break;
                    case FONT_SIZE:
                        if(Integer.valueOf(String.valueOf(styleInfo.get("fontSize"))) != Integer.parseInt(styleKeyValue[1])) {
                            result.setFontSize(Integer.parseInt(styleKeyValue[1]));
                        }
                        break;
                    case FONT_COLOR:
                        if(!styleKeyValue[1].equals(styleInfo.get("fontOffColor"))) {
                            result.setFontColor(styleKeyValue[1]);
                        }
                        break;
                    case FILL_COLOR:
                        if(!styleKeyValue[1].equals(styleInfo.get("buttonOffColor"))) {
                            result.setFillColor(styleKeyValue[1]);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        // 하위속성은 페이지번호 계산하지 않는다.
        if ( isButton ) {
            // 페이지 번호 계산 - 100*5
            Long pageNo = (long)Math.floor(result.getX() / 500) + 1L;
            if (pageNo < 1) {
                pageNo = 1L;
            } else if (pageNo > 1) {
                // 1페이지 초과 인경우 x좌표 재계산 > 포스에서는 페이지단위로 움직인다. : 20181102 노현수
                Long posX = result.getX() - ( pageNo - 1 ) * 500 ;
                result.setX(posX);
            }
            result.setPageNo(pageNo);
        }

        result.setInFg(touchKeyClassVO.getInFg());
        result.setRegDt(touchKeyClassVO.getRegDt());

        return result;
    }

    /**
     * 스타일코드 추출
     *
     * @param styleStr
     * @return
     */
    private String getStyleCd(String styleStr) {

        String result = "00";
        //정규식 패턴 설정
        Pattern styleCdPattern = Pattern.compile("styleCd=([^=]*.(?=;))", Pattern.MULTILINE);
        Matcher styleCdMatcher = styleCdPattern.matcher(styleStr);
        // 정규식으로 스타일코드 추출
        if (styleCdMatcher.find()) {
            result = styleCdMatcher.group(1);
        }

        return result;
    }

    /**
     * 터치키구분 추출
     *
     * @param styleStr
     * @return
     */
    private String getTukeyFg(String styleStr) {

        String result = "00";
        //정규식 패턴 설정
        Pattern tukeyFgPattern = Pattern.compile("tukeyFg=([^=]*.(?=;))", Pattern.MULTILINE);
        Matcher tukeyFgMatcher = tukeyFgPattern.matcher(styleStr);
        // 정규식으로 터치키구분 추출
        if (tukeyFgMatcher.find()) {
            result = tukeyFgMatcher.group(1);
        }

        return result;
    }

    /** 판매터치키 그룹 조회 */
    @Override
    public List<DefaultMap<String>> getTouchKeyGrp(TouchKeyVO touchKeyVO, SessionInfoVO sessionInfoVO) {

        return keyMapper.getTouchKeyGrp(touchKeyVO);
    }

    /** 터치키그룹 복사 */
    @Override
    public Result copyTouchKeyGrp(TouchKeyVO touchKeyVO, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        // 소속구분 설정
        touchKeyVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        touchKeyVO.setStoreCd(sessionInfoVO.getStoreCd());
        touchKeyVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // 기본입력정보 설정
        touchKeyVO.setRegDt(currentDt);
        touchKeyVO.setRegId(sessionInfoVO.getUserId());
        touchKeyVO.setModDt(currentDt);
        touchKeyVO.setModId(sessionInfoVO.getUserId());

        // 새 터치키 그룹코드 조회하여 셋팅
        DefaultMap<String> param = new DefaultMap<String>();
        param.put("orgnFg", sessionInfoVO.getOrgnFg().getCode());
        param.put("hqOfficeCd", sessionInfoVO.getHqOfficeCd());
        param.put("storeCd", sessionInfoVO.getStoreCd());

        String tukeyGrpCd = keyMapper.getTouchKeyGrpCd(param);
        touchKeyVO.setTukeyGrpCd(tukeyGrpCd);

        // 1. XML 복사
        result += keyMapper.copyTouchKeyGrpXml(touchKeyVO);

        // 2. 터치키 Class 복사
        result += keyMapper.copyTouchKeyGrpClass(touchKeyVO);

        // 3. 터치키 복사
        result += keyMapper.copyTouchKeyGrp(touchKeyVO);

        if ( result >= 0 ) {
            return new Result(Status.OK, tukeyGrpCd);
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

    }

    /** 판매터치키 초기화 */
    @Override
    public int deleteTouchKey(TouchKeyVO touchKeyVO, SessionInfoVO sessionInfoVO) {
        System.out.println("판매터치키 초기화 버튼 클릭");
        int result = 0;

        TouchKeyClassVO touchKeyClassVO = new TouchKeyClassVO();
        touchKeyClassVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        touchKeyClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        touchKeyClassVO.setStoreCd(sessionInfoVO.getStoreCd());
        keyMapper.deleteTouchKeyClass(touchKeyClassVO);

        touchKeyVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        touchKeyVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        touchKeyVO.setStoreCd(sessionInfoVO.getStoreCd());
        touchKeyVO.setTukeyGrpCd(null);
        keyMapper.deleteTouchkeyGrp(touchKeyVO);
        keyMapper.deleteTouchKey(touchKeyVO);

        DefaultMap<String> param = new DefaultMap<String>();
        param.put("orgnFg", sessionInfoVO.getOrgnFg().getCode());
        param.put("hqOfficeCd", sessionInfoVO.getHqOfficeCd());
        param.put("storeCd", sessionInfoVO.getStoreCd());
        param.put("confgFg", ConfgFg.TOUCH_KEY.getCode());
        keyMapper.deleteTouchKeyConfgXml(param);
        return result;
    }

}
