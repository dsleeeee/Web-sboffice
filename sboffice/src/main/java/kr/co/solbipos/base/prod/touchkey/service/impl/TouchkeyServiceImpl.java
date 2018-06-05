package kr.co.solbipos.base.prod.touchkey.service.impl;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.common.enums.ConfgFg;
import kr.co.solbipos.base.common.enums.InFg;
import kr.co.solbipos.base.prod.touchkey.service.TouchClassVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchkeyService;
import kr.co.solbipos.base.store.tableattr.enums.Style;
import kr.co.solbipos.base.store.tableattr.service.impl.TableAttrMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TouchkeyServiceImpl implements TouchkeyService {

    @Autowired
    MessageService messageService;

    @Autowired
    private TouchkeyMapper mapper;

    @Autowired
    private TableAttrMapper attrMapper;

    @Override
    public List<DefaultMap<String>> selectProdByStore(SessionInfoVO sessionInfoVO) {
        return mapper.selectProdByStore(sessionInfoVO.getOrgnCd());
    }

    @Override
    public String selectTouchkeyByStore(SessionInfoVO sessionInfoVO) {
        DefaultMap<String> param = new DefaultMap<String>();
        param.put("storeCd", sessionInfoVO.getOrgnCd());
        param.put("confgFg", ConfgFg.TOUCH_KEY.getCode());
        String returnStr = attrMapper.selectXmlByStore(param);
        return returnStr;
    }

    @Override
    public Result setTouchkey(SessionInfoVO sessionInfoVO, String xml) {

        //XML 저장
        DefaultMap<String> param = new DefaultMap<String>();
        param.put("storeCd", sessionInfoVO.getOrgnCd());
        param.put("confgFg", ConfgFg.TOUCH_KEY.getCode());
        param.put("xml", xml);
        param.put("useYn", "Y");
        param.put("regDt", currentDateTimeString());
        param.put("regId", sessionInfoVO.getUserId());

        if( attrMapper.selectXmlByStore(param) != null ) {
            if( attrMapper.updateStoreConfgXml(param) != 1 ) {
                throw new BizException( messageService.get("label.modifyFail") );
            }
        }
        else {
            if( attrMapper.insertStoreConfgXml(param) != 1 ) {
                throw new BizException( messageService.get("label.insertFail") );
            }
        }

        //XML 분석, TouchClass, Touch Domain 생성
        //터치키 분류 TABLE(TB_MS_TOUCH_CLASS)
        List<TouchClassVO> touchClasssVOs = parseXML(xml);

        //매장의 현재 설정정보 삭제
        mapper.deleteTouchClassByStore(sessionInfoVO.getOrgnCd());

        mapper.deleteTouchByStore(sessionInfoVO.getOrgnCd());

        //리스트의 아이템을 DB에 Merge
        for(TouchClassVO touchClassVO : touchClasssVOs) {
            //터치 분류 저장
            touchClassVO.setStoreCd(sessionInfoVO.getOrgnCd());
            touchClassVO.setRegId(sessionInfoVO.getUserId());

            if( mapper.insertTouchClassByStore(touchClassVO) != 1 ) {
                throw new BizException( messageService.get("label.modifyFail") );
            }
            //테이블 저장
            for(TouchVO touchVO : touchClassVO.getTouchs()) {
                touchVO.setStoreCd(sessionInfoVO.getOrgnCd());
                touchVO.setRegId(sessionInfoVO.getUserId());
                if( mapper.insertTouchByStore(touchVO) != 1 ) {
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
    private List<TouchClassVO> parseXML(String xml) {

        String[] xmls = xml.split("\\|");
        log.info(XssPreventer.unescape(xmls[0]));
        log.info(XssPreventer.unescape(xmls[1]));


        List<TouchClassVO> touchClassVOs = new ArrayList<TouchClassVO>();
        TouchClassVO touchClassVO = TouchClassVO.builder().build();

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
                log.debug(cell.toString());

                touchClassVO = TouchClassVO.builder().build();

                touchClassVO.setTukeyClassCd(cell.getId());
                touchClassVO.setTukeyClassNm(String.valueOf(cell.getValue()));

                //페이지 번호 계산 - 80*5
               long pageNo = (long)(touchClassVO.getX() / 400) + 1L;
               touchClassVO.setPageNo(pageNo);

                //좌표, 크기
                mxGeometry geo = cell.getGeometry();
                touchClassVO.setX((long)geo.getX());
                touchClassVO.setY((long)geo.getY());
                touchClassVO.setWidth((long)geo.getWidth());
                touchClassVO.setHeight((long)geo.getHeight());

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
                        //log.debug(styleKeyValue[0]);
                        switch(Style.getEnum(styleKeyValue[0])) {
                            case FONT_COLOR:
                                touchClassVO.setFontColor(styleKeyValue[1]);
                                break;
                            case FILL_COLOR:
                                touchClassVO.setFillColor(styleKeyValue[1]);
                                break;
                            case FONT_SIZE:
                                touchClassVO.setFontSize(Long.parseLong(styleKeyValue[1]));
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

                log.debug(touchClassVOs.toString());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return touchClassVOs;
    }
    /**
     * 레이어 id로 해당 레이어에 있는 테이블 List 추출
     * @param id
     * @return
     */
    private List<TouchVO> getTouchs(mxGraph graph, String layerId, TouchClassVO tableClassVO) {

        mxGraphModel model = (mxGraphModel) graph.getModel();

        List<TouchVO> touchVOs = new ArrayList<TouchVO>();
        TouchVO touchVO = null;
        mxCell layer = (mxCell)model.getCell(layerId);

        Object[] cells = graph.getChildVertices(layer);
        for(Object c : cells) {
            mxCell cell = (mxCell) c;
            touchVO = TouchVO.builder().build();
            touchVO.setStoreCd(tableClassVO.getStoreCd());
            touchVO.setTukeyClassCd(tableClassVO.getTukeyClassCd());
            touchVO.setTukeyCd(cell.getId());

            //좌표, 크기
            mxGeometry geo = cell.getGeometry();
            touchVO.setX((long)geo.getX());
            touchVO.setY((long)geo.getY());
            touchVO.setWidth((long)geo.getWidth());
            touchVO.setHeight((long)geo.getHeight());

            //스타일
            String styleStr = cell.getStyle();
            if(styleStr != null) {
                String[] styles = styleStr.split(";");
                for(String style : styles) {

                    String[] styleKeyValue = style.split("=");
                    if(styleKeyValue.length < 2) {
                        continue;
                    }
                    //log.debug(styleKeyValue[0]);
                    switch(Style.getEnum(styleKeyValue[0])) {
                        case PROD_CD:
                            touchVO.setProdCd(styleKeyValue[1]);
                            break;
                        case FONT_COLOR:
                            touchVO.setFontColor(styleKeyValue[1]);
                            break;
                        case FILL_COLOR:
                            touchVO.setFillColor(styleKeyValue[1]);
                            break;
                        case FONT_SIZE:
                            touchVO.setFontSize(Long.parseLong(styleKeyValue[1]));
                            break;
                        default:
                            break;
                    }
                }
            }
            //페이지 번호 계산 - 80*5
            long pageNo = (long)(touchVO.getX() / 400) + 1L;
            touchVO.setPageNo(pageNo);

            //TODO 본사/매장 구분값을 매장의 환경에 따르 설정-대리점,본사 값 애매..
            touchVO.setInFg(InFg.STORE);

            touchVO.setRegDt(tableClassVO.getRegDt());

            touchVOs.add(touchVO);
        }
        return touchVOs;
    }
}
