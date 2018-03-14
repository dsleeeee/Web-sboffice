package kr.co.solbipos.base.service.store.tableattr;

import static kr.co.solbipos.utils.DateUtil.currentDateTimeString;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;
import kr.co.solbipos.base.domain.store.tableattr.TableAttr;
import kr.co.solbipos.base.enums.AttrCd;
import kr.co.solbipos.base.enums.Style;
import kr.co.solbipos.base.enums.TblTypeFg;
import kr.co.solbipos.base.enums.TextalignFg;
import kr.co.solbipos.base.enums.TextvalignFg;
import kr.co.solbipos.config.AbstractApplicationContextTest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TableAttrServceTest extends AbstractApplicationContextTest {

    List<TableAttr> tableAttrs = null;
    
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

            tableAttrs = new ArrayList<TableAttr>();
            TableAttr tableAttr = TableAttr.builder().build();
            Object[] cells = graph.getChildVertices(graph.getDefaultParent());
            for(Object c : cells) {
                mxCell cell = (mxCell) c;
                
                tableAttr = TableAttr.builder().build();
                tableAttr.setAttrCd(AttrCd.getEnum(cell.getId()));
                tableAttr.setAttrNm(String.valueOf(cell.getValue()));
                
                //TEST
                tableAttr.setStoreCd("S000001");
                tableAttr.setTblTypeFg(TblTypeFg.SQUARE);
                
                //좌표, 크기
                mxGeometry geo = cell.getGeometry();
                tableAttr.setX((long)geo.getX());
                tableAttr.setY((long)geo.getY());
                tableAttr.setWidth((long)geo.getWidth());
                tableAttr.setHeight((long)geo.getHeight());
                
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
                                tableAttr.setFontColor(styleKeyValue[1]);
                                break;
                            case FONT_NM:
                                tableAttr.setFontNm(styleKeyValue[1]);
                                break;
                            case FONT_SIZE:
                                tableAttr.setFontSize(Long.parseLong(styleKeyValue[1]));
                                break;
                            case FONT_STYLE_FG:
                                tableAttr.setFontStyleFg(styleKeyValue[1]);
                                break;
                            case TEXTALIGN_FG:
                                tableAttr.setTextalignFg(TextalignFg.getEnum(styleKeyValue[1]));
                                break;
                            case TEXTVALIGN_FG:
                                tableAttr.setTextvalignFg(TextvalignFg.getEnum(styleKeyValue[1]));
                                break;
                            default:
                                break;
                        }
                    }
                }

                tableAttr.setUseYn("Y");
                tableAttr.setRegDt(currentDateTimeString());
                tableAttr.setRegId("bjcho");
                tableAttrs.add(tableAttr);
                log.debug(tableAttr.toString());
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
            for(TableAttr tableAttr : tableAttrs) {
                cell = new mxCell();
                cell = (mxCell) graph.insertVertex(parent,
                        tableAttr.getAttrCd().getCode(),
                        tableAttr.getAttrNm(),
                        Double.parseDouble(String.valueOf(tableAttr.getX())),
                        Double.parseDouble(String.valueOf(tableAttr.getY())),
                        Double.parseDouble(String.valueOf(tableAttr.getWidth())),
                        Double.parseDouble(String.valueOf(tableAttr.getHeight())));

                //스타일 셋팅
                styleStr = "tableAttr";
                styleStr += tableAttr.getFontNm() != null ? (SM + Style.FONT_NM.getCode() +EQ+ tableAttr.getFontNm()):"";
                styleStr += tableAttr.getFontStyleFg() != null ? (SM + Style.FONT_STYLE_FG.getCode() +EQ+ tableAttr.getFontStyleFg()):"";
                styleStr += tableAttr.getFontSize() != null ? (SM + Style.FONT_SIZE.getCode() +EQ+ tableAttr.getFontSize()):"";
                styleStr += tableAttr.getFontColor() != null ? (SM + Style.FONT_COLOR.getCode() +EQ+ tableAttr.getFontColor()):"";
                styleStr += tableAttr.getTextalignFg() != null ? (SM + Style.TEXTALIGN_FG.getCode() +EQ+ tableAttr.getTextalignFg().getDesc()):"";
                styleStr += tableAttr.getTextvalignFg() != null ? (SM + Style.TEXTVALIGN_FG.getCode() +EQ+ tableAttr.getTextvalignFg().getDesc()):"";
                cell.setStyle(styleStr);
            }
            mxCodec codec = new mxCodec();
            Node node = codec.encode(graph.getModel());
            log.debug(mxUtils.getPrettyXml(node));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
