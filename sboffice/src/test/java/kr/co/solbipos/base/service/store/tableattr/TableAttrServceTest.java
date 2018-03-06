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
    @Ignore
    //@Rollback(false)
    public void test_100() {
        
        String xml;
        mxGraph graph;
        
        try {
            xml = "<mxGraphModel>  <root>    <mxCell id=\"0\"/>    <mxCell id=\"1\" parent=\"0\"/>    <mxCell id=\"5\" value=\"담당자\" style=\"tableAttr;fontStyle=2;\" vertex=\"1\" parent=\"1\">      <mxGeometry x=\"250\" y=\"200\" width=\"150\" height=\"50\" as=\"geometry\"/>    </mxCell>    <mxCell id=\"2\" value=\"테이블명\" style=\"tableAttr;align=left;verticalAlign=top;fontSize=17;\" vertex=\"1\" parent=\"1\">      <mxGeometry width=\"400\" height=\"50\" as=\"geometry\"/>    </mxCell>    <mxCell id=\"6\" value=\"경과시간\" style=\"tableAttr;verticalAlign=bottom;align=right;fontColor=#ff0000;fontSize=17;\" vertex=\"1\" parent=\"1\">      <mxGeometry x=\"150\" y=\"300\" width=\"250\" height=\"100\" as=\"geometry\"/>    </mxCell>    <mxCell id=\"4\" value=\"손님수\" style=\"tableAttr;fontStyle=1;\" vertex=\"1\" parent=\"1\">      <mxGeometry x=\"50\" y=\"200\" width=\"150\" height=\"50\" as=\"geometry\"/>    </mxCell>    <mxCell id=\"7\" value=\"메뉴리스트\" style=\"tableAttr;align=left;verticalAlign=top;\" vertex=\"1\" parent=\"1\">      <mxGeometry x=\"10\" y=\"50\" width=\"300\" height=\"260\" as=\"geometry\"/>    </mxCell>    <mxCell id=\"3\" value=\"주문금액\" style=\"tableAttr\" vertex=\"1\" parent=\"1\">      <mxGeometry y=\"320\" width=\"400\" height=\"80\" as=\"geometry\"/>    </mxCell>    <mxCell id=\"9\" value=\"배달연락처\" style=\"tableAttr;fontStyle=4;\" vertex=\"1\" parent=\"1\">      <mxGeometry x=\"200\" width=\"200\" height=\"50\" as=\"geometry\"/>    </mxCell>    <mxCell id=\"8\" value=\"배달주소\" style=\"tableAttr;fontFamily=Hanna;\" vertex=\"1\" parent=\"1\">      <mxGeometry x=\"100\" y=\"320\" width=\"300\" height=\"80\" as=\"geometry\"/>    </mxCell>  </root></mxGraphModel>";
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
                String[] styles = styleStr.split(";");
                for(String style : styles) {
                    
                    String[] styleKeyValue = style.split("=");
                    if(styleKeyValue.length < 2) {
                        continue;
                    }
                    //log.debug(styleKeyValue[0]);
                    switch(Style.getEnum(styleKeyValue[0])) {
                        case FONT_COLOR: tableAttr.setFontColor(styleKeyValue[1]);
                            break;
                        case FONT_NM: tableAttr.setFontNm(styleKeyValue[1]);
                            break;
                        case FONT_SIZE: tableAttr.setFontSize(Long.parseLong(styleKeyValue[1]));
                            break;
                        case FONT_STYLE_FG: {
                            tableAttr.setFontStyleFg(styleKeyValue[1]);
                            break;
                        }
                        case TEXTALIGN_FG: {
                            tableAttr.setTextalignFg(TextalignFg.getEnum(styleKeyValue[1]));
                            break;
                        }
                        case TEXTVALIGN_FG: {
                            tableAttr.setTextvalignFg(TextvalignFg.getEnum(styleKeyValue[1]));
                            break;
                        }
                        default:
                            break;
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
    @Test
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
