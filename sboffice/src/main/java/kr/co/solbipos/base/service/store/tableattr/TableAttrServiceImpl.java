package kr.co.solbipos.base.service.store.tableattr;

import static kr.co.solbipos.utils.DateUtil.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.base.domain.store.tableattr.TableAttr;
import kr.co.solbipos.base.enums.AttrCd;
import kr.co.solbipos.base.enums.ConfgFg;
import kr.co.solbipos.base.enums.Style;
import kr.co.solbipos.base.enums.TblTypeFg;
import kr.co.solbipos.base.enums.TextalignFg;
import kr.co.solbipos.base.enums.TextvalignFg;
import kr.co.solbipos.base.persistence.store.tableattr.TableAttrMapper;
import kr.co.solbipos.enums.Status;
import kr.co.solbipos.exception.BizException;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.structure.Result;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class TableAttrServiceImpl implements TableAttrService {

    @Autowired
    MessageService messageService;

    @Autowired
    private TableAttrMapper mapper;


    @Override
    public List<TableAttr> selectTableAttrDefault() {
        return mapper.selectDefaultXml();
    }

    @Override
    public String selectTableAttrByStore(SessionInfo sessionInfo) {
        DefaultMap<String> param = new DefaultMap<String>();
        param.put("storeCd", sessionInfo.getOrgnCd());
        param.put("confgFg", ConfgFg.TABLE_ATTR.getCode());
        
        String returnStr = mapper.selectXmlByStore(param);
        if( returnStr == null) {
            returnStr = parseDB(mapper.selectDefaultXml());
        }
        return returnStr;
    }

    @Override
    public Result setTableAttr(SessionInfo sessionInfo, String xml) {
        
        //XML 저장
        DefaultMap<String> param = new DefaultMap<String>();
        param.put("storeCd", sessionInfo.getOrgnCd());
        param.put("confgFg", ConfgFg.TABLE_ATTR.getCode());
        param.put("xml", xml);
        param.put("useYn", "Y");
        param.put("regDt", currentDateTimeString());
        param.put("regId", sessionInfo.getUserId());
        
        if( mapper.mergeStoreConfgXml(param) != 1 ) {
            throw new BizException( messageService.get("label.modifyFail") );
        }
        
        //XML 분석, 속성 코드별 TableAttr Domain 생성
        //테이블속성 TABLE(TB_MS_TABLE_ATTR)
        List<TableAttr> tableAttrs = parseXML(xml);
        
        //리스트의 아이템을 DB에 Merge
        for(TableAttr tableAttr : tableAttrs) {
            tableAttr.setStoreCd(sessionInfo.getOrgnCd());
            tableAttr.setRegId(sessionInfo.getUserId());
            if( mapper.mergeStoreTableAttr(tableAttr) != 1 ) {
                throw new BizException( messageService.get("label.modifyFail") );
            }
        }
        return new Result(Status.OK);
    }
    
    /**
     * XML 파싱하여 테이블 속성 항목 추출
     * @param xml 파싱대상XML
     * @return 테이블속성객체
     */
    private List<TableAttr> parseXML(String xml) {
        
        List<TableAttr> tableAttrs = new ArrayList<TableAttr>();
        
        try {
            mxGraph graph = new mxGraph();
            Document doc = mxXmlUtils.parseXml(xml);
            mxCodec codec = new mxCodec(doc);
    
            mxIGraphModel model = graph.getModel();
            Element elt = doc.getDocumentElement();
    
            codec.decode(elt, model);
            
            TableAttr tableAttr = TableAttr.builder().build();
            Object[] cells = graph.getChildVertices(graph.getDefaultParent());
            for(Object c : cells) {
                mxCell cell = (mxCell) c;
                
                tableAttr = TableAttr.builder().build();
                tableAttr.setAttrCd(AttrCd.getEnum(cell.getId()));
                tableAttr.setAttrNm(String.valueOf(cell.getValue()));
                
                //TEST
                //tableAttr.setStoreCd("S000001");
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
                tableAttrs.add(tableAttr);
                //log.debug(tableAttr.toString());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return tableAttrs;
    }

    /**
     * XML 파싱하여 테이블 속성 항목 추출
     * @param xml 파싱대상XML
     * @return 테이블속성객체
     */
    @SuppressWarnings("deprecation")
    private String parseDB(List<TableAttr> tableAttrs) {
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
            return mxUtils.getXml(node);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
