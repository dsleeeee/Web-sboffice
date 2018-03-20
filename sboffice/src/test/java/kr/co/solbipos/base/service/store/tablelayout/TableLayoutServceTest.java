package kr.co.solbipos.base.service.store.tablelayout;

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
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;
import kr.co.solbipos.base.domain.store.tablelayout.Table;
import kr.co.solbipos.base.domain.store.tablelayout.TableGroup;
import kr.co.solbipos.base.enums.Style;
import kr.co.solbipos.base.enums.TblGrpFg;
import kr.co.solbipos.base.enums.TblTypeFg;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TableLayoutServceTest {

    private String xml;
    mxGraph graph;
    mxIGraphModel model;
    
    @Before
    public void init() {
        xml = "<mxGraphModel>\r\n" + 
                "  <root>\r\n" + 
                "    <mxCell id=\"0\"/>\r\n" + 
                "    <mxCell id=\"1\" parent=\"0\"/>\r\n" + 
                "    <mxCell id=\"9\" style=\"ts2\" vertex=\"1\" parent=\"1\">\r\n" + 
                "      <mxGeometry x=\"312\" y=\"228\" width=\"40\" height=\"40\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"10\" value=\"1\" vertex=\"1\" parent=\"9\">\r\n" + 
                "      <mxGeometry x=\"0.3\" y=\"0.2\" relative=\"1\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"12\" style=\"ts2\" vertex=\"1\" parent=\"1\">\r\n" + 
                "      <mxGeometry x=\"411\" y=\"226\" width=\"40\" height=\"40\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"13\" value=\"2\" vertex=\"1\" parent=\"12\">\r\n" + 
                "      <mxGeometry x=\"0.3\" y=\"0.2\" relative=\"1\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"15\" style=\"ts2\" vertex=\"1\" parent=\"1\">\r\n" + 
                "      <mxGeometry x=\"485\" y=\"226\" width=\"40\" height=\"40\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"16\" value=\"3\" vertex=\"1\" parent=\"15\">\r\n" + 
                "      <mxGeometry x=\"0.3\" y=\"0.2\" relative=\"1\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"18\" style=\"ts2\" vertex=\"1\" parent=\"1\">\r\n" + 
                "      <mxGeometry x=\"485\" y=\"292\" width=\"40\" height=\"40\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"19\" value=\"4\" vertex=\"1\" parent=\"18\">\r\n" + 
                "      <mxGeometry x=\"0.3\" y=\"0.2\" relative=\"1\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"21\" style=\"ts2\" vertex=\"1\" parent=\"1\">\r\n" + 
                "      <mxGeometry x=\"450\" y=\"408\" width=\"40\" height=\"40\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"22\" value=\"5\" vertex=\"1\" parent=\"21\">\r\n" + 
                "      <mxGeometry x=\"0.3\" y=\"0.2\" relative=\"1\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"24\" style=\"ts2\" vertex=\"1\" parent=\"1\">\r\n" + 
                "      <mxGeometry x=\"338\" y=\"412\" width=\"40\" height=\"40\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"25\" value=\"6\" vertex=\"1\" parent=\"24\">\r\n" + 
                "      <mxGeometry x=\"0.3\" y=\"0.2\" relative=\"1\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"27\" style=\"ts2\" vertex=\"1\" parent=\"1\">\r\n" + 
                "      <mxGeometry x=\"256\" y=\"409\" width=\"40\" height=\"40\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"28\" value=\"7\" vertex=\"1\" parent=\"27\">\r\n" + 
                "      <mxGeometry x=\"0.3\" y=\"0.2\" relative=\"1\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"30\" style=\"ts2\" vertex=\"1\" parent=\"1\">\r\n" + 
                "      <mxGeometry x=\"204\" y=\"409\" width=\"40\" height=\"40\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"31\" value=\"8\" vertex=\"1\" parent=\"30\">\r\n" + 
                "      <mxGeometry x=\"0.3\" y=\"0.2\" relative=\"1\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"33\" style=\"ts2\" vertex=\"1\" parent=\"1\">\r\n" + 
                "      <mxGeometry x=\"152\" y=\"410\" width=\"40\" height=\"40\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"34\" value=\"9\" vertex=\"1\" parent=\"33\">\r\n" + 
                "      <mxGeometry x=\"0.3\" y=\"0.2\" relative=\"1\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"36\" style=\"ts2\" vertex=\"1\" parent=\"1\">\r\n" + 
                "      <mxGeometry x=\"102\" y=\"410\" width=\"40\" height=\"40\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"37\" value=\"10\" vertex=\"1\" parent=\"36\">\r\n" + 
                "      <mxGeometry x=\"0.3\" y=\"0.2\" relative=\"1\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"39\" style=\"ts2\" vertex=\"1\" parent=\"1\">\r\n" + 
                "      <mxGeometry x=\"29\" y=\"290\" width=\"40\" height=\"40\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"40\" value=\"11\" vertex=\"1\" parent=\"39\">\r\n" + 
                "      <mxGeometry x=\"0.3\" y=\"0.2\" relative=\"1\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"42\" style=\"ts2\" vertex=\"1\" parent=\"1\">\r\n" + 
                "      <mxGeometry x=\"81\" y=\"229\" width=\"40\" height=\"40\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"43\" value=\"12\" vertex=\"1\" parent=\"42\">\r\n" + 
                "      <mxGeometry x=\"0.3\" y=\"0.2\" relative=\"1\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"45\" style=\"ts2\" vertex=\"1\" parent=\"1\">\r\n" + 
                "      <mxGeometry x=\"134\" y=\"229\" width=\"40\" height=\"40\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"46\" value=\"13\" vertex=\"1\" parent=\"45\">\r\n" + 
                "      <mxGeometry x=\"0.3\" y=\"0.2\" relative=\"1\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"48\" style=\"ts2\" vertex=\"1\" parent=\"1\">\r\n" + 
                "      <mxGeometry x=\"191\" y=\"229\" width=\"40\" height=\"40\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "    <mxCell id=\"49\" value=\"14\" vertex=\"1\" parent=\"48\">\r\n" + 
                "      <mxGeometry x=\"0.3\" y=\"0.2\" relative=\"1\" as=\"geometry\"/>\r\n" + 
                "    </mxCell>\r\n" + 
                "  </root>\r\n" + 
                "</mxGraphModel>\r\n" + 
                "";
        graph = new mxGraph();
        Document doc = mxXmlUtils.parseXml(xml);
        mxCodec codec = new mxCodec(doc);

        model = graph.getModel();
        Element elt = doc.getDocumentElement();

        codec.decode(elt, model);
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
            //TODO 테이블 그룹
            /*
            List<mxCell> cells = new ArrayList<mxCell>();
            while(elt != null) {
                cells.add(e)
            }
            */
            List<TableGroup> tableGroups = new ArrayList<TableGroup>();
            TableGroup tableGroup = new TableGroup();

            List<Table> tables = new ArrayList<Table>();

            mxCell layer = new mxCell();
            for(int i = 0; i < model.getChildCount(model.getRoot()); i++) {
                layer = (mxCell)model.getChildAt(model.getRoot(), i);
                log.debug(layer.toString());
                
                tableGroup = new TableGroup();
                
                //TODO 테이블 그룹 파라미터 생성
                tableGroup.setStoreCd("S000001");
                //tableGroup.setTblGrpCd("");
                tableGroup.setTblGrpNm(String.valueOf(layer.getValue()));
                //TODO 배경이미지 그룹별로 넣을 수 있게 JS부터 개발할 것
                //tableGroup.setBgImgNm("");
                
                //스타일
                String styleStr = layer.getStyle();
                if(styleStr != null) {
                    String[] styles = styleStr.split(";");
                    for(String style : styles) {
                        
                        String[] styleKeyValue = style.split("=");
                        if(styleKeyValue.length < 2) {
                            continue;
                        }
                        //log.debug(styleKeyValue[0]);
                        switch(Style.getEnum(styleKeyValue[0])) {
                            case TBL_GRP_FG:
                                tableGroup.setTblGrpFg(TblGrpFg.getEnum(styleKeyValue[1]));
                                break;
                            case BG_COLOR:
                                tableGroup.setBgColor(styleKeyValue[1]);
                                break;
                            case BG_IMG:
                                tableGroup.setBgImgNm(styleKeyValue[1]);
                                break;
                            default:
                                break;
                        }
                    }
                }
                tableGroup.setDispSeq(Long.parseLong((String.valueOf(i+1))));
                tableGroup.setUseYn("Y");
                tableGroup.setRegDt(currentDateTimeString());
                tableGroup.setRegId("bjcho");
                
                tables = getTables(layer, tableGroup);
                
                tableGroup.setTables(tables);
                
                tableGroups.add(tableGroup);
                
                log.debug(tableGroups.toString());
            }
            assertTrue(true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * 레이어 id로 해당 레이어에 있는 테이블 List 추출
     * @param id
     * @return
     */
    private List<Table> getTables(mxCell layer, TableGroup tableGroup) {
        List<Table> tables = new ArrayList<Table>();
        Table table = null;
        Object[] cells = graph.getChildVertices(layer);
        for(Object c : cells) {
            mxCell cell = (mxCell) c;
            table = Table.builder().build();
            table.setStoreCd(tableGroup.getStoreCd());
            table.setTblNm(String.valueOf(cell.getValue()));
            table.setTblGrpCd(tableGroup.getTblGrpCd());
            
            //스타일
            String styleStr = cell.getStyle();
            if(styleStr != null) {
                String[] styles = styleStr.split(";");
                for(String style : styles) {
                    
                    String[] styleKeyValue = style.split("=");
                    if(styleKeyValue.length < 2) {
                        continue;
                    }
                    switch(Style.getEnum(styleKeyValue[0])) {
                        case TBL_SEAT_CNT: table.setTblSeatCnt(Long.parseLong(styleKeyValue[1]));
                            break;
                        case TBL_TYPE_FG: table.setTblTypeFg(TblTypeFg.getEnum(styleKeyValue[1]));
                            break;
                        default:
                            break;
                    }
                }
            }
            //좌표, 크기
            mxGeometry geo = cell.getGeometry();
            table.setX((long)geo.getX());
            table.setY((long)geo.getY());
            table.setWidth((long)geo.getWidth());
            table.setHeight((long)geo.getHeight());
            
            table.setUseYn(tableGroup.getUseYn());
            table.setRegDt(tableGroup.getRegDt());
            table.setRegId(tableGroup.getRegId());
            
            tables.add(table);
        }
        return tables;
    }

}
