package kr.co.solbipos.base.service.prod.touchkey;

import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;
import com.nhncorp.lucy.security.xss.XssPreventer;
import kr.co.solbipos.base.common.enums.InFg;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyClassVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyVO;
import kr.co.solbipos.base.store.tableattr.enums.TouchKeyStyle;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static org.junit.Assert.assertTrue;

@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TouchkeyServceTest {
    
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    
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
            LOGGER.info(XssPreventer.unescape(xmls[0]));
            LOGGER.info(XssPreventer.unescape(xmls[1]));


            List<TouchKeyClassVO> touchKeyClassVOS = new ArrayList<TouchKeyClassVO>();
            TouchKeyClassVO touchKeyClassVO = new TouchKeyClassVO();

            List<TouchKeyVO> touchKeyVOS = new ArrayList<TouchKeyVO>();

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
                LOGGER.debug(cell.toString());

                touchKeyClassVO = new TouchKeyClassVO();

                touchKeyClassVO.setTukeyClassCd(cell.getId());
                touchKeyClassVO.setTukeyClassNm(String.valueOf(cell.getValue()));

                //좌표, 크기
                mxGeometry geo = cell.getGeometry();
                touchKeyClassVO.setX(geo.getX());
                touchKeyClassVO.setY(geo.getY());
                touchKeyClassVO.setWidth(geo.getWidth());
                touchKeyClassVO.setHeight(geo.getHeight());

                 //페이지 번호 계산 - 80*5
                Double pageNo = (touchKeyClassVO.getX() / 400) + 1;
                touchKeyClassVO.setPageNo(pageNo.intValue());

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
                        //log.debug(styleKeyValue[0]);
                        switch(TouchKeyStyle.getEnum(styleKeyValue[0])) {
                            case FONT_COLOR:
                                touchKeyClassVO.setFontColor(styleKeyValue[1]);
                                break;
                            case FILL_COLOR:
                                touchKeyClassVO.setFillColor(styleKeyValue[1]);
                                break;
                            case FONT_SIZE:
                                touchKeyClassVO.setFontSize(Integer.parseInt(styleKeyValue[1]));
                                break;
                            default:
                                break;
                        }
                    }
                }

                touchKeyClassVO.setRegDt(regDt);
                touchKeyVOS = getTouchs(graphTouch, cell.getId(), touchKeyClassVO);
                touchKeyClassVO.setTouchs(touchKeyVOS);
                touchKeyClassVOS.add(touchKeyClassVO);

                LOGGER.debug(touchKeyClassVOS.toString());
            }
            assertTrue(true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private List<TouchKeyVO> getTouchs(mxGraph graph, String layerId, TouchKeyClassVO touchKeyClassVO) {

        mxGraphModel model = (mxGraphModel) graph.getModel();

        List<TouchKeyVO> touchKeyVOS = new ArrayList<TouchKeyVO>();
        TouchKeyVO touchKeyVO = null;
        mxCell layer = (mxCell)model.getCell(layerId);

        Object[] cells = graph.getChildVertices(layer);
        for(Object c : cells) {
            mxCell cell = (mxCell) c;
            touchKeyVO = new TouchKeyVO();
            touchKeyVO.setStoreCd(touchKeyClassVO.getStoreCd());
            touchKeyVO.setTukeyClassCd(touchKeyClassVO.getTukeyClassCd());
            touchKeyVO.setTukeyCd(cell.getId());

            //좌표, 크기
            mxGeometry geo = cell.getGeometry();
            touchKeyVO.setX((long)geo.getX());
            touchKeyVO.setY((long)geo.getY());
            touchKeyVO.setWidth((long)geo.getWidth());
            touchKeyVO.setHeight((long)geo.getHeight());

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
                    switch(TouchKeyStyle.getEnum(styleKeyValue[0])) {
                        case PROD_CD:
                            touchKeyVO.setProdCd(styleKeyValue[1]);
                            break;
                        case FONT_COLOR:
                            touchKeyVO.setFontColor(styleKeyValue[1]);
                            break;
                        case FILL_COLOR:
                            touchKeyVO.setFillColor(styleKeyValue[1]);
                            break;
                        case FONT_SIZE:
                            touchKeyVO.setFontSize(Long.parseLong(styleKeyValue[1]));
                            break;
                        default:
                            break;
                    }
                }
            }

            //페이지 번호 계산 - 80*5
            long pageNo = (long)(touchKeyVO.getX() / 400) + 1L;
            touchKeyVO.setPageNo(pageNo);
            touchKeyVO.setInFg(touchKeyClassVO.getInFg());
            touchKeyVO.setRegDt(touchKeyClassVO.getRegDt());

            touchKeyVOS.add(touchKeyVO);
        }
        return touchKeyVOS;
    }

}
