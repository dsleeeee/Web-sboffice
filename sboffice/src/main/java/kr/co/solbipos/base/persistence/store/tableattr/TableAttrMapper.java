package kr.co.solbipos.base.persistence.store.tableattr;

import java.util.List;
import kr.co.solbipos.base.domain.store.tableattr.TableAttr;
import kr.co.solbipos.structure.DefaultMap;

/**
 * 기초관리 - 매장관리 - 테이블속성
 * 
 * @author 조병준
 *
 */
public interface TableAttrMapper {

    /** 
     * storeCd, confgFg 로 매장 테이블 속성 조회
     * @param param
     * @return
     */
    String selectXmlByStore(DefaultMap<String> param);
    
    /**
     * 매장 테이블 속성 DB 값이 없을 경우 Default 설정 조회
     * @param param
     * @return
     */
    List<TableAttr> selectDefaultXml();
    
    /**
     * 매장 환경설정 XML 값을 저장(Merge)
     * @param param
     * @return
     */
    int mergeStoreConfgXml(DefaultMap<String> param);
    
    /** 
     * 매장 테이블 속성 저장(Merge)
     * @param tableAttr
     * @return
     */
    int mergeStoreTableAttr(TableAttr tableAttr);
}
