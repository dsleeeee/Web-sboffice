package kr.co.solbipos.base.store.tableattr.service.impl;

import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.exception.BizException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.common.enums.ConfgFg;
import kr.co.solbipos.base.store.tableattr.enums.*;
import kr.co.solbipos.base.store.tableattr.service.TableAttrService;
import kr.co.solbipos.base.store.tableattr.service.TableAttrVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : TableAttrServiceImpl.java
 * @Description : 기초관리 > 매장관리 > 테이블속성
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  조병준      최초생성
 *
 * @author NHN한국사이버결제 KCP 조병준
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("tableAttrService")
@Transactional
public class TableAttrServiceImpl implements TableAttrService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MessageService messageService;
    @Autowired
    private TableAttrMapper mapper;

    @Override
    public List<TableAttrVO> selectTableAttrDefault() {
    	DefaultMap<String> param = new DefaultMap<String>();
    	param.put("tblTypeFg", "1");
        return mapper.selectDefaultXml(param);
    }

    @Override
    public List<TableAttrVO> selectTblAttrCommCode() {
        return mapper.selectTblAttrCommCode();
    }

    @Override
    public String selectTableAttrByStore(SessionInfoVO sessionInfoVO, TableAttrVO tableAttrVO) {
        DefaultMap<String> param = new DefaultMap<String>();
        param.put("storeCd", sessionInfoVO.getStoreCd());
        param.put("confgFg", ConfgFg.TABLE_ATTR_TYPE.getCode());
        param.put("confgSubFg", tableAttrVO.getConfgSubFg());

        String returnStr = mapper.selectXmlByStore(param);
        if( returnStr == null) {
            returnStr = parseDB(mapper.selectDefaultXml(param));
        }
        return returnStr;
    }

    @Override
    public Result setTableAttr(SessionInfoVO sessionInfoVO, TableAttrVO tableAttrVO, String xmlGraph, String xmlPreview) {

    	String confgSubFg = "1";

    	if (tableAttrVO.getConfgSubFg() != null && !"".equals(tableAttrVO.getConfgSubFg())) {
    		confgSubFg = tableAttrVO.getConfgSubFg();
    	}

        //XML 저장
        DefaultMap<String> param = new DefaultMap<String>();
        param.put("storeCd", sessionInfoVO.getStoreCd());
        param.put("confgFg", ConfgFg.TABLE_ATTR_TYPE.getCode());
        param.put("confgSubFg", confgSubFg);
        param.put("xml", xmlGraph);
        param.put("useYn", "Y");
        param.put("regDt", currentDateTimeString());
        param.put("regId", sessionInfoVO.getUserId());

        if( mapper.selectXmlByStore(param) != null ) {
            if( mapper.updateStoreConfgXml(param) != 1 ) {
                throw new BizException( messageService.get("cmm.saveFail") );
            }
        }
        else {
            if( mapper.insertStoreConfgXml(param) != 1 ) {
                throw new BizException( messageService.get("label.insertFail") );
            }
        }

        //XML 분석, 속성 코드별 TableAttr Domain 생성
        //테이블속성 TABLE(TB_MS_TABLE_ATTR)
        List<TableAttrVO> tableAttrVOs = parseXML(xmlPreview);

        //리스트의 아이템을 DB에 Merge
        for(TableAttrVO tableAttrTempVO : tableAttrVOs) {
        	tableAttrTempVO.setStoreCd(sessionInfoVO.getStoreCd());
        	tableAttrTempVO.setRegId(sessionInfoVO.getUserId());
            System.out.println(tableAttrTempVO.getAttrNm()+"속성명222");
            System.out.println(tableAttrTempVO.getImgNm()+"이미지명222");
            System.out.println(tableAttrTempVO.getFontNm()+"폰트명222");
            if( mapper.mergeStoreTableAttr(tableAttrTempVO) != 1 ) {
                throw new BizException( messageService.get("cmm.saveFail") );
            }
        }
        return new Result(Status.OK);
    }

    /**
     * XML 파싱하여 테이블 속성 항목 추출
     * @param xml 파싱대상XML
     * @return 테이블속성객체
     */
    private List<TableAttrVO> parseXML(String xml) {

        List<TableAttrVO> tableAttrVOs = new ArrayList<TableAttrVO>();

        try {
            mxGraph graph = new mxGraph();
            Document doc = mxXmlUtils.parseXml(xml);
            mxCodec codec = new mxCodec(doc);

            mxIGraphModel model = graph.getModel();
            Element elt = doc.getDocumentElement();

            codec.decode(elt, model);

            TableAttrVO tableAttrVO = new TableAttrVO();
            Object[] cells = graph.getChildVertices(graph.getDefaultParent());
            for(Object c : cells) {
                mxCell cell = (mxCell) c;

                //lombok 초기값 셋팅 사용
                tableAttrVO = new TableAttrVO();

                tableAttrVO.setAttrCd(AttrCd.getEnum(cell.getId()));
                tableAttrVO.setAttrNm(String.valueOf(cell.getValue()));

                //TEST
                //tableAttr.setStoreCd("S000001");
                //tableAttrVO.setTblTypeFg(TblTypeFg.SQUARE);

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
                        //LOGGER.debug(styleKeyValue[0]);
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
                            case TBL_TYPE_FG:
                                tableAttrVO.setTblTypeFg(styleKeyValue[1]);
                                break;
                            default:
                                break;
                        }
                    }
                }
                tableAttrVO.setUseYn("Y");
                tableAttrVO.setRegDt(currentDateTimeString());
                tableAttrVOs.add(tableAttrVO);
                //LOGGER.debug(tableAttr.toString());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return tableAttrVOs;
    }

    /**
     * XML 파싱하여 테이블 속성 항목 추출
     * @param tableAttrVOs List<TableAttrVO>
     * @return 테이블속성객체
     */
    @SuppressWarnings("deprecation")
    private String parseDB(List<TableAttrVO> tableAttrVOs) {
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
                styleStr += tableAttrVO.getTblTypeFg() != null ? (SM + TouchKeyStyle.TBL_TYPE_FG.getCode() +EQ+ tableAttrVO.getTblTypeFg()):"";
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
            return mxUtils.getXml(node);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
