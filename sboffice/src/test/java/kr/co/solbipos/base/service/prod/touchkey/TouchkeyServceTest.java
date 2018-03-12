package kr.co.solbipos.base.service.prod.touchkey;

import static kr.co.solbipos.utils.DateUtil.currentDateTimeString;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.transaction.annotation.Transactional;
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
import kr.co.solbipos.base.domain.prod.touchkey.Touch;
import kr.co.solbipos.base.domain.prod.touchkey.TouchClass;
import kr.co.solbipos.base.enums.InFg;
import kr.co.solbipos.base.enums.Style;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TouchkeyServceTest {

    String xml = "";
    
    @Before
    public void init() {
        //test_100();
        xml = "<mxGraphModel>\r\n" + 
                "  <root>\r\n" + 
                "    <mxCell id=\"0\"/>\r\n" + 
                "    <mxCell id=\"1\" parent=\"0\"/>\r\n" + 
                "    <mxCell id=\"g1\" value=\"그룹명\" vertex=\"1\" parent=\"1\">\r\n" + 
                "      <mxGeometry width=\"80\" height=\"60\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"g2\" value=\"그룹명\" vertex=\"1\" parent=\"1\">\r\n" + 
                "      <mxGeometry x=\"80\" width=\"80\" height=\"60\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "  </root>\r\n" + 
                "</mxGraphModel>\r\n" +
                
                "|" +
                
                "<mxGraphModel>\r\n" + 
                "  <root>\r\n" + 
                "    <mxCell id=\"0\"/>\r\n" + 
                "    <mxCell id=\"1\" value=\"g1\" parent=\"0\" visible=\"0\"/>\r\n" + 
                "    <mxCell id=\"p1\" value=\"아메리카노\" style=\"prodCd=A000000000001;\" vertex=\"1\" parent=\"1\">\r\n" + 
                "      <mxGeometry width=\"80\" height=\"60\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"p2\" value=\"카페모카\" style=\"prodCd=A000000000002;\" vertex=\"1\" parent=\"1\">\r\n" + 
                "      <mxGeometry x=\"80\" width=\"80\" height=\"60\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"2\" value=\"g2\" parent=\"0\"/>\r\n" + 
                "    <mxCell id=\"p3\" value=\"카페라떼\" style=\"prodCd=A000000000003;\" vertex=\"1\" parent=\"2\">\r\n" + 
                "      <mxGeometry width=\"80\" height=\"120\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"p4\" value=\"에스프레소\" style=\"prodCd=A000000000004;\" vertex=\"1\" parent=\"2\">\r\n" + 
                "      <mxGeometry x=\"80\" width=\"160\" height=\"120\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "  </root>\r\n" + 
                "</mxGraphModel>\r\n" + 
                "";
        
    }

    /**
     * mxGraph XML 파싱 테스트
     * 
     */
    @Test
    //@Ignore
    //@Rollback(false)
    public void test_100() {
        try {
            String[] xmls = xml.split("\\|");
            log.info(XssPreventer.unescape(xmls[0]));
            log.info(XssPreventer.unescape(xmls[1]));
            
            
            List<TouchClass> touchClasss = new ArrayList<TouchClass>();
            TouchClass touchClass = TouchClass.builder().build();

            List<Touch> touchs = new ArrayList<Touch>();
            
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
                
                //좌표, 크기
                mxGeometry geo = cell.getGeometry();
                touchClass.setX((long)geo.getX());
                touchClass.setY((long)geo.getY());
                touchClass.setWidth((long)geo.getWidth());
                touchClass.setHeight((long)geo.getHeight());
                
                 //페이지 번호 계산 - 80*5
                long pageNo = (long)(touchClass.getX() / 400) + 1L;
                touchClass.setPageNo(pageNo);
                
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
                            case FONT_COLOR: touchClass.setFontColor(styleKeyValue[1]);
                                break;
                            case FILL_COLOR: touchClass.setFillColor(styleKeyValue[1]);
                                break;
                            case FONT_SIZE: touchClass.setFontSize(Long.parseLong(styleKeyValue[1]));
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
            assertTrue(true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private List<Touch> getTouchs(mxGraph graph, String layerId, TouchClass tableClass) {

        mxGraphModel model = (mxGraphModel) graph.getModel();

        List<Touch> touchs = new ArrayList<Touch>();
        Touch touch = null;
        mxCell layer = (mxCell)model.getCell(layerId.substring(1));

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
                        case PROD_CD: touch.setProdCd(styleKeyValue[1]);
                            break;
                        case FONT_COLOR: touch.setFontColor(styleKeyValue[1]);
                            break;
                        case FILL_COLOR: touch.setFillColor(styleKeyValue[1]);
                            break;
                        case FONT_SIZE: touch.setFontSize(Long.parseLong(styleKeyValue[1]));
                            break;
                        default:
                            break;
                    }
                }
            }

            //페이지 번호 계산 - 80*5
            long pageNo = (long)(touch.getX() / 400) + 1L;
            touch.setPageNo(pageNo);

            touch.setInFg(InFg.STORE);
            touch.setRegDt(tableClass.getRegDt());
            
            touchs.add(touch);
        }
        return touchs;
    }

}
