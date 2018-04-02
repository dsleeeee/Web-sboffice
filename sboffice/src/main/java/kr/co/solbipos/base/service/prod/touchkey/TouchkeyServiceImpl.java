package kr.co.solbipos.base.service.prod.touchkey;

import static kr.co.solbipos.utils.DateUtil.currentDateTimeString;
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
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.base.domain.prod.touchkey.Touch;
import kr.co.solbipos.base.domain.prod.touchkey.TouchClass;
import kr.co.solbipos.base.enums.ConfgFg;
import kr.co.solbipos.base.enums.InFg;
import kr.co.solbipos.base.enums.Style;
import kr.co.solbipos.base.persistence.prod.touchkey.TouchkeyMapper;
import kr.co.solbipos.base.persistence.store.tableattr.TableAttrMapper;
import kr.co.solbipos.enums.Status;
import kr.co.solbipos.exception.BizException;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.structure.Result;
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
    public List<DefaultMap<String>> selectProdByStore(SessionInfo sessionInfo) {
        return mapper.selectProdByStore(sessionInfo.getOrgnCd());
    }

    @Override
    public String selectTouchkeyByStore(SessionInfo sessionInfo) {
        DefaultMap<String> param = new DefaultMap<String>();
        param.put("storeCd", sessionInfo.getOrgnCd());
        param.put("confgFg", ConfgFg.TOUCH_KEY.getCode());
        String returnStr = attrMapper.selectXmlByStore(param);
        return returnStr;
    }

    @Override
    public Result setTouchkey(SessionInfo sessionInfo, String xml) {
        
        //XML 저장
        DefaultMap<String> param = new DefaultMap<String>();
        param.put("storeCd", sessionInfo.getOrgnCd());
        param.put("confgFg", ConfgFg.TOUCH_KEY.getCode());
        param.put("xml", xml);
        param.put("useYn", "Y");
        param.put("regDt", currentDateTimeString());
        param.put("regId", sessionInfo.getUserId());
        
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
        List<TouchClass> touchClasss = parseXML(xml);
        
        //매장의 현재 설정정보 삭제
        mapper.deleteTouchClassByStore(sessionInfo.getOrgnCd());
        
        mapper.deleteTouchByStore(sessionInfo.getOrgnCd());
        
        //리스트의 아이템을 DB에 Merge
        for(TouchClass touchClass : touchClasss) {
            //터치 분류 저장
            touchClass.setStoreCd(sessionInfo.getOrgnCd());
            touchClass.setRegId(sessionInfo.getUserId());
            
            if( mapper.insertTouchClassByStore(touchClass) != 1 ) {
                throw new BizException( messageService.get("label.modifyFail") );
            }
            //테이블 저장
            for(Touch touch : touchClass.getTouchs()) {
                touch.setStoreCd(sessionInfo.getOrgnCd());
                touch.setRegId(sessionInfo.getUserId());
                if( mapper.insertTouchByStore(touch) != 1 ) {
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
    private List<TouchClass> parseXML(String xml) {
        
        String[] xmls = xml.split("\\|");
        log.info(XssPreventer.unescape(xmls[0]));
        log.info(XssPreventer.unescape(xmls[1]));
        
        
        List<TouchClass> touchClasss = new ArrayList<TouchClass>();
        TouchClass touchClass = TouchClass.builder().build();

        List<Touch> touchs = new ArrayList<Touch>();
        
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
                
                touchClass = TouchClass.builder().build();
                
                touchClass.setTukeyClassCd(cell.getId());
                touchClass.setTukeyClassNm(String.valueOf(cell.getValue()));
                
                //페이지 번호 계산 - 80*5
               long pageNo = (long)(touchClass.getX() / 400) + 1L;
               touchClass.setPageNo(pageNo);
                
                //좌표, 크기
                mxGeometry geo = cell.getGeometry();
                touchClass.setX((long)geo.getX());
                touchClass.setY((long)geo.getY());
                touchClass.setWidth((long)geo.getWidth());
                touchClass.setHeight((long)geo.getHeight());

                touchClass.setInFg(InFg.STORE);

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
                                touchClass.setFontColor(styleKeyValue[1]);
                                break;
                            case FILL_COLOR:
                                touchClass.setFillColor(styleKeyValue[1]);
                                break;
                            case FONT_SIZE:
                                touchClass.setFontSize(Long.parseLong(styleKeyValue[1]));
                                break;
                            default:
                                break;
                        }
                    }
                }

                touchClass.setRegDt(regDt);
                
                touchs = getTouchs(graphTouch, cell.getId(), touchClass);
                
                touchClass.setTouchs(touchs);
                
                touchClasss.add(touchClass);
                
                log.debug(touchClasss.toString());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return touchClasss;
    }
    /**
     * 레이어 id로 해당 레이어에 있는 테이블 List 추출
     * @param id
     * @return
     */
    private List<Touch> getTouchs(mxGraph graph, String layerId, TouchClass tableClass) {

        mxGraphModel model = (mxGraphModel) graph.getModel();

        List<Touch> touchs = new ArrayList<Touch>();
        Touch touch = null;
        mxCell layer = (mxCell)model.getCell(layerId);

        Object[] cells = graph.getChildVertices(layer);
        for(Object c : cells) {
            mxCell cell = (mxCell) c;
            touch = Touch.builder().build();
            touch.setStoreCd(tableClass.getStoreCd());
            touch.setTukeyClassCd(tableClass.getTukeyClassCd());
            touch.setTukeyCd(cell.getId());

            //좌표, 크기
            mxGeometry geo = cell.getGeometry();
            touch.setX((long)geo.getX());
            touch.setY((long)geo.getY());
            touch.setWidth((long)geo.getWidth());
            touch.setHeight((long)geo.getHeight());
            
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
                            touch.setProdCd(styleKeyValue[1]);
                            break;
                        case FONT_COLOR:
                            touch.setFontColor(styleKeyValue[1]);
                            break;
                        case FILL_COLOR:
                            touch.setFillColor(styleKeyValue[1]);
                            break;
                        case FONT_SIZE:
                            touch.setFontSize(Long.parseLong(styleKeyValue[1]));
                            break;
                        default:
                            break;
                    }
                }
            }
            //페이지 번호 계산 - 80*5
            long pageNo = (long)(touch.getX() / 400) + 1L;
            touch.setPageNo(pageNo);
            
            //TODO 본사/매장 구분값을 매장의 환경에 따르 설정-대리점,본사 값 애매..
            touch.setInFg(InFg.STORE);

            touch.setRegDt(tableClass.getRegDt());
            
            touchs.add(touch);
        }
        return touchs;
    }
}
