package kr.co.solbipos.base.service.store.tablelayout;

import static kr.co.solbipos.utils.DateUtil.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.base.domain.store.tablelayout.Table;
import kr.co.solbipos.base.domain.store.tablelayout.TableGroup;
import kr.co.solbipos.base.enums.ConfgFg;
import kr.co.solbipos.base.enums.TblGrpFg;
import kr.co.solbipos.base.enums.TblTypeFg;
import kr.co.solbipos.base.persistence.store.tableattr.TableAttrMapper;
import kr.co.solbipos.base.persistence.store.tablelayout.TableLayoutMapper;
import kr.co.solbipos.enums.Status;
import kr.co.solbipos.exception.BizException;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.structure.Result;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class TableLayoutServiceImpl implements TableLayoutService {

    @Autowired
    MessageService messageService;

    @Autowired
    private TableLayoutMapper mapper;

    @Autowired
    private TableAttrMapper attrMapper;

    @Override
    public String selectTableLayoutByStore(SessionInfo sessionInfo) {
        DefaultMap<String> param = new DefaultMap<String>();
        param.put("storeCd", sessionInfo.getOrgnCd());
        param.put("confgFg", ConfgFg.TABLE_LAYOUT.getCode());
        String returnStr = attrMapper.selectXmlByStore(param);
        return returnStr;
    }

    @Override
    public Result setTableLayout(SessionInfo sessionInfo, String xml) {
        
        //XML 저장
        DefaultMap<String> param = new DefaultMap<String>();
        param.put("storeCd", sessionInfo.getOrgnCd());
        param.put("confgFg", ConfgFg.TABLE_LAYOUT.getCode());
        param.put("xml", xml);
        param.put("useYn", "Y");
        param.put("regDt", currentDateTimeString());
        param.put("regId", sessionInfo.getUserId());
        
        if( attrMapper.mergeStoreConfgXml(param) != 1 ) {
            throw new BizException( messageService.get("label.modifyFail") );
        }

        //XML 분석, TableGroup, Table Domain 생성
        //테이블속성 TABLE(TB_MS_TABLE_GROUP, TB_MS_TABLE)
        List<TableGroup> tableGroups = parseXML(xml);
        
        //매장의 현재 설정정보 삭제
        mapper.deleteTableGroupByStore(sessionInfo.getOrgnCd());
        mapper.deleteTableByStore(sessionInfo.getOrgnCd());
        
        //리스트의 아이템을 DB에 Merge
        for(TableGroup tableGroup : tableGroups) {
            //테이블 그룹 저장
            tableGroup.setStoreCd(sessionInfo.getOrgnCd());
            tableGroup.setRegId(sessionInfo.getUserId());
            
            if( mapper.mergeTableGroupByStore(tableGroup) != 1 ) {
                throw new BizException( messageService.get("label.modifyFail") );
            }
            //테이블 저장
            for(Table table : tableGroup.getTables()) {
                table.setStoreCd(sessionInfo.getOrgnCd());
                table.setRegId(sessionInfo.getUserId());
                if( mapper.mergeTableByStore(table) != 1 ) {
                    throw new BizException( messageService.get("label.modifyFail") );
                }
            }
        }
        return new Result(Status.OK);
    }
    
    /**
     * XML 파싱하여 테이블 구성 항목 추출
     * @param xml 파싱대상XML
     * @return 테이블그룹객체
     */
    private List<TableGroup> parseXML(String xml) {
        
        List<TableGroup> tableGroups = new ArrayList<TableGroup>();
        TableGroup tableGroup = new TableGroup();

        List<Table> tables = new ArrayList<Table>();
        
        try {
            mxGraph graph = new mxGraph();
            Document doc = mxXmlUtils.parseXml(xml);
            mxCodec codec = new mxCodec(doc);
    
            mxIGraphModel model = graph.getModel();
            Element elt = doc.getDocumentElement();
    
            codec.decode(elt, model);
            
            mxCell layer = new mxCell();
            String regDt = currentDateTimeString();
            
            for(int i = 0; i < model.getChildCount(model.getRoot()); i++) {
                layer = (mxCell)model.getChildAt(model.getRoot(), i);
                log.debug(layer.toString());
                
                tableGroup = new TableGroup();
                
                tableGroup.setTblGrpCd(layer.getId());
                tableGroup.setTblGrpNm(String.valueOf(layer.getValue()));
                //TODO 테이블그룹구분, JS부터 개발할 것
                tableGroup.setTblGrpFg(TblGrpFg.NORMAL);
                //TODO 배경이미지 그룹별로 넣을 수 있게 JS부터 개발할 것
                //tableGroup.setBgImgNm("")
                
                tableGroup.setDispSeq(Long.parseLong((String.valueOf(i+1))));
                tableGroup.setUseYn("Y");
                tableGroup.setRegDt(regDt);
                
                tables = getTables(graph, layer, tableGroup);
                
                tableGroup.setTables(tables);
                
                tableGroups.add(tableGroup);
                
                log.debug(tableGroups.toString());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return tableGroups;
    }
    /**
     * 레이어 id로 해당 레이어에 있는 테이블 List 추출
     * @param id
     * @return
     */
    private List<Table> getTables(mxGraph graph, mxCell layer, TableGroup tableGroup) {
        List<Table> tables = new ArrayList<Table>();
        Table table = null;
        mxCell cell = new mxCell();
        Object[] cells = graph.getChildVertices(layer);
        for(Object c : cells) {
            cell = (mxCell) c;
            table = Table.builder().build();
            table.setStoreCd(tableGroup.getStoreCd());
            table.setTblCd(cell.getId());
            table.setTblNm(getLabel(graph, cell));
            table.setTblGrpCd(tableGroup.getTblGrpCd());
            //TODO 테이블 좌석 수 설정
            //table.setTblSeatCnt(tblSeatCnt);
            //좌표, 크기
            mxGeometry geo = cell.getGeometry();
            table.setX((long)geo.getX());
            table.setY((long)geo.getY());
            table.setWidth((long)geo.getWidth());
            table.setHeight((long)geo.getHeight());
            
            table.setTblTypeFg(TblTypeFg.SQUARE);
            table.setUseYn(tableGroup.getUseYn());
            table.setRegDt(tableGroup.getRegDt());
            
            tables.add(table);
        }
        return tables;
    }
    /**
     * 주어진 셀에서 라벨 추출
     * @param id
     * @return
     */
    private String getLabel(mxGraph graph, mxCell cell) {
        Object[] labels = graph.getChildVertices(cell);
        String finalLabel = "";
        for(Object label : labels) {
            mxCell lbl = (mxCell) label;
            
            if("label".equals(lbl.getStyle())) {
                finalLabel = String.valueOf(lbl.getValue());
            }
        }
        return finalLabel;
    }
}
