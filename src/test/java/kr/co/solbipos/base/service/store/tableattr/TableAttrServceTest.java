package kr.co.solbipos.base.service.store.tableattr;

import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;
import kr.co.solbipos.base.store.tableattr.enums.*;
import kr.co.solbipos.base.store.tableattr.service.TableAttrVO;
import kr.co.solbipos.config.AbstractApplicationContextTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static org.junit.Assert.assertTrue;

@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TableAttrServceTest extends AbstractApplicationContextTest {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    List<TableAttrVO> tableAttrVOs = null;

    @Before
    public void init() {
        //test_100();
    }

    /**
     * mxGraph XML 파싱 테스트
     *
     */
    @Test
    //@Ignore
    //@Rollback(false)
    public void test_100() {

        String xml;
        mxGraph graph;

        try {
            xml =   "<mxGraphModel>\r\n" +
                    "  <root>\r\n" +
                    "    <mxCell id=\"0\"/>\r\n" +
                    "    <mxCell id=\"1\" parent=\"0\"/>\r\n" +
                    "    <mxCell id=\"01\" value=\"1번\" style=\"tableAttr;fontFamily=NotoR;fontStyle=1;fontSize=16;fontColor=#000000;align=left\" parent=\"1\" vertex=\"1\">\r\n" +
                    "      <mxGeometry width=\"400\" height=\"50\" as=\"geometry\"/>\r\n" +
                    "    </mxCell>\r\n" +
                    "    <mxCell id=\"02\" value=\"12000\" style=\"tableAttr;fontFamily=NotoR;fontStyle=0;fontSize=10;fontColor=#000000;align=center\" parent=\"1\" vertex=\"1\">\r\n" +
                    "      <mxGeometry y=\"320\" width=\"400\" height=\"80\" as=\"geometry\"/>\r\n" +
                    "    </mxCell>\r\n" +
                    "    <mxCell id=\"03\" value=\"2명\" style=\"tableAttr;fontFamily=NotoR;fontStyle=0;fontSize=10;fontColor=#000000;align=center\" parent=\"1\" vertex=\"1\">\r\n" +
                    "      <mxGeometry x=\"50\" y=\"200\" width=\"150\" height=\"50\" as=\"geometry\"/>\r\n" +
                    "    </mxCell>\r\n" +
                    "    <mxCell id=\"04\" value=\"직원\" style=\"tableAttr;fontFamily=NotoR;fontStyle=0;fontSize=10;fontColor=#000000;align=center\" parent=\"1\" vertex=\"1\">\r\n" +
                    "      <mxGeometry x=\"250\" y=\"200\" width=\"150\" height=\"50\" as=\"geometry\"/>\r\n" +
                    "    </mxCell>\r\n" +
                    "    <mxCell id=\"05\" value=\"10분\" style=\"tableAttr;fontFamily=NotoR;fontStyle=0;fontSize=10;fontColor=#000000;align=center\" parent=\"1\" vertex=\"1\">\r\n" +
                    "      <mxGeometry x=\"150\" y=\"300\" width=\"250\" height=\"100\" as=\"geometry\"/>\r\n" +
                    "    </mxCell>\r\n" +
                    "    <mxCell id=\"07\" value=\"메뉴리스트\" style=\"tableAttr;fontFamily=NotoR;fontStyle=0;fontSize=10;fontColor=#000000;align=center\" parent=\"1\" vertex=\"1\">\r\n" +
                    "      <mxGeometry x=\"100\" y=\"320\" width=\"300\" height=\"80\" as=\"geometry\"/>\r\n" +
                    "    </mxCell>\r\n" +
                    "    <mxCell id=\"08\" value=\"테이블상태-사용\" style=\"tableAttr;fontFamily=NotoR;fontStyle=0;fontSize=10;fontColor=#000000;align=center\" parent=\"1\" vertex=\"1\">\r\n" +
                    "      <mxGeometry x=\"200\" width=\"200\" height=\"50\" as=\"geometry\"/>\r\n" +
                    "    </mxCell>\r\n" +
                    "    <mxCell id=\"09\" value=\"테이블상태-분할\" style=\"tableAttr;fontFamily=NotoR;fontStyle=0;fontSize=10;fontColor=#000000;align=center\" parent=\"1\" vertex=\"1\">\r\n" +
                    "      <mxGeometry x=\"200\" width=\"200\" height=\"50\" as=\"geometry\"/>\r\n" +
                    "    </mxCell>\r\n" +
                    "    <mxCell id=\"10\" value=\"태아블상태-예약\" style=\"tableAttr;fontFamily=NotoR;fontStyle=0;fontSize=10;fontColor=#000000;align=center\" parent=\"1\" vertex=\"1\">\r\n" +
                    "      <mxGeometry x=\"200\" width=\"200\" height=\"50\" as=\"geometry\"/>\r\n" +
                    "    </mxCell>\r\n" +
                    "    <mxCell id=\"11\" value=\"배달상태\" style=\"tableAttr;fontFamily=NotoR;fontStyle=0;fontSize=10;fontColor=#000000;align=center\" parent=\"1\" vertex=\"1\">\r\n" +
                    "      <mxGeometry x=\"200\" width=\"200\" height=\"50\" as=\"geometry\"/>\r\n" +
                    "    </mxCell>\r\n" +
                    "    <mxCell id=\"12\" value=\"배달주소\" style=\"tableAttr;fontFamily=NotoR;fontStyle=0;fontSize=10;fontColor=#000000;align=center\" parent=\"1\" vertex=\"1\">\r\n" +
                    "      <mxGeometry x=\"200\" width=\"200\" height=\"50\" as=\"geometry\"/>\r\n" +
                    "    </mxCell>\r\n" +
                    "    <mxCell id=\"13\" value=\"배달연락처\" style=\"tableAttr;fontFamily=NotoR;fontStyle=0;fontSize=10;fontColor=#000000;align=center\" parent=\"1\" vertex=\"1\">\r\n" +
                    "      <mxGeometry x=\"200\" width=\"200\" height=\"50\" as=\"geometry\"/>\r\n" +
                    "    </mxCell>\r\n" +
                    "    <mxCell id=\"14\" value=\"배달원명\" style=\"tableAttr;fontFamily=NotoR;fontStyle=0;fontSize=10;fontColor=#000000;align=center\" parent=\"1\" vertex=\"1\">\r\n" +
                    "      <mxGeometry x=\"200\" width=\"200\" height=\"50\" as=\"geometry\"/>\r\n" +
                    "    </mxCell>\r\n" +
                    "    <mxCell id=\"15\" value=\"교환번호\" style=\"tableAttr;fontFamily=NotoR;fontStyle=0;fontSize=10;fontColor=#000000;align=center\" parent=\"1\" vertex=\"1\">\r\n" +
                    "      <mxGeometry x=\"200\" width=\"200\" height=\"50\" as=\"geometry\"/>\r\n" +
                    "    </mxCell>\r\n" +
                    "    <mxCell id=\"06\" value=\"솔비포스\" style=\"tableAttr\" vertex=\"1\" parent=\"1\">\r\n" +
                    "      <mxGeometry x=\"50\" y=\"50\" width=\"300\" height=\"300\" as=\"geometry\"/>\r\n" +
                    "    </mxCell>\r\n" +
                    "  </root>\r\n" +
                    "</mxGraphModel>\r\n" +
                    "";
            graph = new mxGraph();
            Document doc = mxXmlUtils.parseXml(xml);
            mxCodec codec = new mxCodec(doc);

            mxIGraphModel model = graph.getModel();
            Element elt = doc.getDocumentElement();

            codec.decode(elt, model);

            tableAttrVOs = new ArrayList<TableAttrVO>();
            TableAttrVO tableAttrVO = new TableAttrVO();
            Object[] cells = graph.getChildVertices(graph.getDefaultParent());
            for(Object c : cells) {
                mxCell cell = (mxCell) c;

                tableAttrVO = new TableAttrVO();
                tableAttrVO.setAttrCd(AttrCd.getEnum(cell.getId()));
                tableAttrVO.setAttrNm(String.valueOf(cell.getValue()));

                //TEST
                tableAttrVO.setStoreCd("S000001");
                tableAttrVO.setTblTypeFg(TblTypeFg.SQUARE);

                //좌표, 크기
                mxGeometry geo = cell.getGeometry();
                tableAttrVO.setX((long)geo.getX());
                tableAttrVO.setY((long)geo.getY());
                tableAttrVO.setWidth((long)geo.getWidth());
                tableAttrVO.setHeight((long)geo.getHeight());

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
                                tableAttrVO.setFontColor(styleKeyValue[1]);
                                break;
                            case FONT_NM:
                                tableAttrVO.setFontNm(styleKeyValue[1]);
                                break;
                            case FONT_SIZE:
                                tableAttrVO.setFontSize(Long.parseLong(styleKeyValue[1]));
                                break;
                            case FONT_STYLE_FG:
                                tableAttrVO.setFontStyleFg(StringUtils.leftPad(Integer.toBinaryString(Integer.parseInt(styleKeyValue[1])), 3, "0"));
                                break;
                            case ALIGN:
                                tableAttrVO.setTextalignFg(TextalignFg.getEnum(styleKeyValue[1]));
                                break;
                            case VERTICAL_ALIGN:
                                tableAttrVO.setTextvalignFg(TextvalignFg.getEnum(styleKeyValue[1]));
                                break;
                            default:
                                break;
                        }
                    }
                }

                tableAttrVO.setUseYn("Y");
                tableAttrVO.setRegDt(currentDateTimeString());
                tableAttrVO.setRegId("bjcho");
                tableAttrVOs.add(tableAttrVO);
                LOGGER.debug(tableAttrVO.toString());
            }
            assertTrue(true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * mxGraph XML 파싱 테스트
     *
     */
    //@Test
    @Ignore
    //@Rollback(false)
    public void test_200() {

        try {
            mxGraph graph = new mxGraph();
            Object parent = graph.getDefaultParent();

            String styleStr = "tableAttr";
            final String EQ = "=";
            final String SM = ";";
            mxCell cell = new mxCell();
            for(TableAttrVO tableAttrVO : tableAttrVOs) {
                cell = new mxCell();
                cell = (mxCell) graph.insertVertex(parent,
                        tableAttrVO.getAttrCd().getCode(),
                        tableAttrVO.getAttrNm(),
                        Double.parseDouble(String.valueOf(tableAttrVO.getX())),
                        Double.parseDouble(String.valueOf(tableAttrVO.getY())),
                        Double.parseDouble(String.valueOf(tableAttrVO.getWidth())),
                        Double.parseDouble(String.valueOf(tableAttrVO.getHeight())));

                //스타일 셋팅
                styleStr = "tableAttr";
                styleStr += tableAttrVO.getFontNm() != null ? (SM + TouchKeyStyle.FONT_NM.getCode() +EQ+ tableAttrVO.getFontNm()):"";
                styleStr += tableAttrVO.getFontStyleFg() != null ? (SM + TouchKeyStyle.FONT_STYLE_FG.getCode() +EQ+ tableAttrVO.getFontStyleFg()):"";
                styleStr += tableAttrVO.getFontSize() != null ? (SM + TouchKeyStyle.FONT_SIZE.getCode() +EQ+ tableAttrVO.getFontSize()):"";
                styleStr += tableAttrVO.getFontColor() != null ? (SM + TouchKeyStyle.FONT_COLOR.getCode() +EQ+ tableAttrVO.getFontColor()):"";
                styleStr += tableAttrVO.getTextalignFg() != null ? (SM + TouchKeyStyle.ALIGN.getCode() +EQ+ tableAttrVO.getTextalignFg().getDesc()):"";
                styleStr += tableAttrVO.getTextvalignFg() != null ? (SM + TouchKeyStyle.VERTICAL_ALIGN.getCode() +EQ+ tableAttrVO.getTextvalignFg().getDesc()):"";
                cell.setStyle(styleStr);
            }
            mxCodec codec = new mxCodec();
            Node node = codec.encode(graph.getModel());
            LOGGER.debug(mxUtils.getPrettyXml(node));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
