package kr.co.solbipos.base.service.prod.touchkey;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
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
import kr.co.solbipos.base.common.enums.InFg;
import kr.co.solbipos.base.prod.touchkey.service.TouchClassVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchVO;
import kr.co.solbipos.base.store.tableattr.enums.Style;
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
                "    <mxCell id=\"g3\" value=\"그룹명\" vertex=\"1\" parent=\"1\">\r\n" +
                "      <mxGeometry x=\"160\" width=\"80\" height=\"60\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "  </root>\r\n" +
                "</mxGraphModel>\r\n" +

                "|" +

                "<mxGraphModel>\r\n" +
                "  <root>\r\n" +
                "    <mxCell id=\"0\"/>\r\n" +
                "    <mxCell id=\"g3\" value=\"g3\" parent=\"0\"/>\r\n" +
                "    <mxCell id=\"6\" value=\"아메리카노\" style=\"prodCd=A000000000001;fillColor=#fff9e5;fontSize=12;\" vertex=\"1\" parent=\"g3\">\r\n" +
                "      <mxGeometry width=\"80\" height=\"60\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"7\" value=\"카페모카\" style=\"prodCd=A000000000002;fontSize=14;\" vertex=\"1\" parent=\"g3\">\r\n" +
                "      <mxGeometry x=\"80\" width=\"80\" height=\"60\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"8\" value=\"카페라떼\" style=\"prodCd=A000000000003;fontColor=#ff0000;\" vertex=\"1\" parent=\"g3\">\r\n" +
                "      <mxGeometry x=\"160\" width=\"80\" height=\"60\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"9\" value=\"에스프레소\" style=\"prodCd=A000000000004;fillColor=#ffff00;\" vertex=\"1\" parent=\"g3\">\r\n" +
                "      <mxGeometry x=\"240\" width=\"80\" height=\"60\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"10\" value=\"아메리카노\" style=\"prodCd=A000000000001;fillColor=#ffffe5;fontColor=#00b050;\" vertex=\"1\" parent=\"g3\">\r\n" +
                "      <mxGeometry y=\"60\" width=\"80\" height=\"60\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"11\" value=\"카페모카\" style=\"prodCd=A000000000002;fontSize=13;fontColor=#7030a0;fillColor=#f3ffe5;\" vertex=\"1\" parent=\"g3\">\r\n" +
                "      <mxGeometry x=\"80\" y=\"60\" width=\"80\" height=\"60\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"12\" value=\"카페라떼\" style=\"prodCd=A000000000003;\" vertex=\"1\" parent=\"g3\">\r\n" +
                "      <mxGeometry x=\"160\" y=\"60\" width=\"80\" height=\"60\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"13\" value=\"에스프레소\" style=\"prodCd=A000000000004;\" vertex=\"1\" parent=\"g3\">\r\n" +
                "      <mxGeometry x=\"240\" y=\"60\" width=\"80\" height=\"60\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"g2\" value=\"g2\" parent=\"0\" visible=\"0\"/>\r\n" +
                "    <mxCell id=\"4\" value=\"카페라떼\" style=\"prodCd=A000000000003;\" vertex=\"1\" parent=\"g2\">\r\n" +
                "      <mxGeometry width=\"80\" height=\"120\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"5\" value=\"에스프레소\" style=\"prodCd=A000000000004;\" vertex=\"1\" parent=\"g2\">\r\n" +
                "      <mxGeometry x=\"80\" width=\"160\" height=\"120\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"g1\" value=\"g1\" parent=\"0\" visible=\"0\"/>\r\n" +
                "    <mxCell id=\"2\" value=\"아메리카노\" style=\"prodCd=A000000000001;\" vertex=\"1\" parent=\"g1\">\r\n" +
                "      <mxGeometry width=\"80\" height=\"60\" as=\"geometry\"/>\r\n" +
                "    </mxCell>\r\n" +
                "    <mxCell id=\"3\" value=\"카페모카\" style=\"prodCd=A000000000002;\" vertex=\"1\" parent=\"g1\">\r\n" +
                "      <mxGeometry x=\"80\" width=\"80\" height=\"60\" as=\"geometry\"/>\r\n" +
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


            List<TouchClassVO> touchClassVOs = new ArrayList<TouchClassVO>();
            TouchClassVO touchClassVO = TouchClassVO.builder().build();

            List<TouchVO> touchVOs = new ArrayList<TouchVO>();

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

                //좌표, 크기
                mxGeometry geo = cell.getGeometry();
                touchClassVO.setX((long)geo.getX());
                touchClassVO.setY((long)geo.getY());
                touchClassVO.setWidth((long)geo.getWidth());
                touchClassVO.setHeight((long)geo.getHeight());

                 //페이지 번호 계산 - 80*5
                long pageNo = (long)(touchClassVO.getX() / 400) + 1L;
                touchClassVO.setPageNo(pageNo);

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
            assertTrue(true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private List<TouchVO> getTouchs(mxGraph graph, String layerId, TouchClassVO tableClass) {

        mxGraphModel model = (mxGraphModel) graph.getModel();

        List<TouchVO> touchVOs = new ArrayList<TouchVO>();
        TouchVO touchVO = null;
        mxCell layer = (mxCell)model.getCell(layerId);

        Object[] cells = graph.getChildVertices(layer);
        for(Object c : cells) {
            mxCell cell = (mxCell) c;
            touchVO = TouchVO.builder().build();
            touchVO.setStoreCd(tableClass.getStoreCd());
            touchVO.setTukeyClassCd(tableClass.getTukeyClassCd());
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

            touchVO.setInFg(InFg.STORE);
            touchVO.setRegDt(tableClass.getRegDt());

            touchVOs.add(touchVO);
        }
        return touchVOs;
    }

}
