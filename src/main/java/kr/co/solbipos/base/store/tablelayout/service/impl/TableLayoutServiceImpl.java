package kr.co.solbipos.base.store.tablelayout.service.impl;

import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.exception.BizException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.common.enums.ConfgFg;
import kr.co.solbipos.base.store.tableattr.enums.TblGrpFg;
import kr.co.solbipos.base.store.tableattr.enums.TblTypeFg;
import kr.co.solbipos.base.store.tableattr.enums.TouchKeyStyle;
import kr.co.solbipos.base.store.tableattr.service.impl.TableAttrMapper;
import kr.co.solbipos.base.store.tablelayout.service.TableGroupVO;
import kr.co.solbipos.base.store.tablelayout.service.TableLayoutService;
import kr.co.solbipos.base.store.tablelayout.service.TableVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : TableLayoutServiceImpl.java
 * @Description : 기초관리 > 매장관리 > 테이블관리
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
@Service("tableLayoutService")
@Transactional
public class TableLayoutServiceImpl implements TableLayoutService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MessageService messageService;

    @Autowired
    private TableLayoutMapper mapper;

    @Autowired
    private TableAttrMapper attrMapper;

    @Override
    public String selectTableLayoutByStore(SessionInfoVO sessionInfoVO) {
        DefaultMap<String> param = new DefaultMap<String>();
        param.put("storeCd", sessionInfoVO.getStoreCd());
        param.put("confgFg", ConfgFg.TABLE_LAYOUT.getCode());
        String returnStr = attrMapper.selectXmlByStore(param);
        return returnStr;
    }

    @Override
    public Result setTableLayout(SessionInfoVO sessionInfoVO, String xml) {

        //XML 저장
        DefaultMap<String> param = new DefaultMap<String>();
        param.put("storeCd", sessionInfoVO.getStoreCd());
        param.put("confgFg", ConfgFg.TABLE_LAYOUT.getCode());
        param.put("confgSubFg", "1");
        param.put("xml", xml);
        param.put("useYn", "Y");
        param.put("regDt", currentDateTimeString());
        param.put("regId", sessionInfoVO.getUserId());

        if( attrMapper.selectXmlByStore(param) != null ) {
            if( attrMapper.updateStoreConfgXml(param) != 1 ) {
                throw new BizException( messageService.get("cmm.saveFail") );
            }
        }
        else {
            if( attrMapper.insertStoreConfgXml(param) != 1 ) {
                throw new BizException( messageService.get("label.insertFail") );
            }
        }

        //XML 분석, TableGroup, Table Domain 생성
        //테이블속성 TABLE(TB_MS_TABLE_GROUP, TB_MS_TABLE)
        List<TableGroupVO> tableGroupVOs = parseXML(xml);

        //매장의 현재 설정정보 삭제
        mapper.deleteTableGroupByStore(sessionInfoVO.getStoreCd());
        mapper.deleteTableByStore(sessionInfoVO.getStoreCd());

        //리스트의 아이템을 DB에 Merge
        for(TableGroupVO tableGroupVO : tableGroupVOs) {
            //테이블 그룹 저장
            tableGroupVO.setStoreCd(sessionInfoVO.getStoreCd());
            tableGroupVO.setRegId(sessionInfoVO.getUserId());

            if( mapper.mergeTableGroupByStore(tableGroupVO) != 1 ) {
                throw new BizException( messageService.get("cmm.saveFail") );
            }
            //테이블 저장
            for(TableVO tableVO : tableGroupVO.getTables()) {
                tableVO.setStoreCd(sessionInfoVO.getStoreCd());
                tableVO.setRegId(sessionInfoVO.getUserId());
                if( mapper.mergeTableByStore(tableVO) != 1 ) {
                    throw new BizException( messageService.get("cmm.saveFail") );
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
    private List<TableGroupVO> parseXML(String xml) {

        List<TableGroupVO> tableGroupVOs = new ArrayList<TableGroupVO>();
        TableGroupVO tableGroupVO = new TableGroupVO();

        List<TableVO> tableVOs = new ArrayList<TableVO>();

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
                LOGGER.debug(layer.toString());

                tableGroupVO = new TableGroupVO();

                tableGroupVO.setTblGrpCd(layer.getId());
                tableGroupVO.setTblGrpNm(String.valueOf(layer.getValue()));

                //스타일
                String styleStr = layer.getStyle();
                if(styleStr != null) {
                    String[] styles = styleStr.split(";");
                    for(String style : styles) {

                        String[] styleKeyValue = style.split("=");
                        if(styleKeyValue.length < 2) {
                            continue;
                        }
                        LOGGER.debug(styleKeyValue[0]);
                        if (TouchKeyStyle.getEnum(styleKeyValue[0]) != null) {
                        	switch(TouchKeyStyle.getEnum(styleKeyValue[0])) {
	                            case TBL_GRP_FG:
	                                tableGroupVO.setTblGrpFg(TblGrpFg.getEnum(styleKeyValue[1]));
	                                break;
	                            case BG_COLOR:
	                                tableGroupVO.setBgColor(styleKeyValue[1]);
	                                break;
	                            case BG_IMG_NM:
	                                tableGroupVO.setBgImgNm(styleKeyValue[1]);
	                            break;
	                            default:
	                                break;
	                        }
                        }
                        
                    }
                }

                tableGroupVO.setDispSeq(Long.parseLong((String.valueOf(i+1))));
                tableGroupVO.setUseYn("Y");
                tableGroupVO.setRegDt(regDt);

                tableVOs = getTables(graph, layer, tableGroupVO);

                tableGroupVO.setTables(tableVOs);

                tableGroupVOs.add(tableGroupVO);

                LOGGER.debug(tableGroupVOs.toString());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return tableGroupVOs;
    }
    /**
     * 레이어 id로 해당 레이어에 있는 테이블 List 추출
     * @param id
     * @return
     */
    private List<TableVO> getTables(mxGraph graph, mxCell layer, TableGroupVO tableGroupVO) {
        List<TableVO> tableVOs = new ArrayList<TableVO>();
        TableVO tableVO = null;
        mxCell cell = new mxCell();
        Object[] cells = graph.getChildVertices(layer);
        for(Object c : cells) {
            cell = (mxCell) c;
            tableVO = new TableVO();
            tableVO.setStoreCd(tableGroupVO.getStoreCd());
            tableVO.setTblCd(cell.getId());

            //swimlane으로 했을 때 테이블 번호(명)
            tableVO.setTblNm(String.valueOf(cell.getValue()));
            //swimlane 아니고 박스로 할 경우 라벨 추출
            //tableVO.setTblNm(getLabel(graph, cell));

            tableVO.setTblGrpCd(tableGroupVO.getTblGrpCd());

            //테이블 좌석 수 설정
            //스타일
            String styleStr = cell.getStyle();
            if(styleStr != null) {
                String[] styles = styleStr.split(";");
                for(String style : styles) {

                    String[] styleKeyValue = style.split("=");
                    if(styleKeyValue.length < 2) {
                        continue;
                    }

                    if (TouchKeyStyle.getEnum(styleKeyValue[0]) != null) {
                    	switch(TouchKeyStyle.getEnum(styleKeyValue[0])) {
	                        case TBL_SEAT_CNT: tableVO.setTblSeatCnt(Long.parseLong(styleKeyValue[1]));
	                            break;
	                        case TBL_TYPE_FG: tableVO.setTblTypeFg(TblTypeFg.getEnum(styleKeyValue[1]));
	                            break;
	                        case BG_IMG: tableVO.setImgNm(styleKeyValue[1]);
	                        	break;
	                        default:
	                            break;
	                    }
                    }
                }
            }

            //좌표, 크기
            mxGeometry geo = cell.getGeometry();
            tableVO.setX((long)geo.getX());
            tableVO.setY((long)geo.getY());
            tableVO.setWidth((long)geo.getWidth());
            tableVO.setHeight((long)geo.getHeight());

            //TODO 원탁의 경우가 생기면 XML의 STYLE에 셋팅하고 여기서 저장
//            tableVO.setTblTypeFg(TblTypeFg.SQUARE);            
            tableVO.setUseYn(tableGroupVO.getUseYn());
            tableVO.setRegDt(tableGroupVO.getRegDt());

            tableVOs.add(tableVO);
        }
        return tableVOs;
    }
    /**
     * 주어진 셀에서 라벨 추출
     * @param id
     * @return
     */
    @SuppressWarnings("unused")
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

    @Override
    public String uploadFile(String uploadPath, String storeCd, String originalName, byte[] fileData) throws IOException {
        // UUID 발급
        UUID uuid = UUID.randomUUID();
        // 저장할 파일명 = UUID + 원본이름
        String savedName = uuid.toString() + "_" + originalName;
        // 업로드할 디렉토리(날짜별 폴더) 생성
        String savedPath = calcPath(uploadPath, storeCd);
        // 파일 경로(기존의 업로드경로+날짜별경로), 파일명을 받아 파일 객체 생성
        File target = new File(uploadPath + savedPath, savedName);
        // 임시 디렉토리에 업로드된 파일을 지정된 디렉토리로 복사
        FileCopyUtils.copy(fileData, target);

        return savedName;
    }

    // 날짜별 디렉토리 추출
    private static String calcPath(String uploadPath, String storeCd) {
        // File.separator : 디렉토리 구분자(\\)
        // 매장코드
        String storePath = File.separator + storeCd;
        // 디렉토리 생성 메서드 호출
        makeDir(uploadPath, storePath);
        return storePath;
    }

    // 디렉토리 생성
    private static void makeDir(String uploadPath, String... paths) {
        // 디렉토리가 존재하면
        if (new File(paths[paths.length - 1]).exists()){
            return;
        }
        // 디렉토리가 존재하지 않으면
        for (String path : paths) {
            //
            File dirPath = new File(uploadPath + path);
            // 디렉토리가 존재하지 않으면
            if (!dirPath.exists()) {
                dirPath.mkdir(); //디렉토리 생성
            }
        }
    }

}
