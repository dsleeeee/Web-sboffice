package kr.co.solbipos.base.persistence.store.tableattr;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.domain.store.tableattr.TableAttrVO;

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
    List<TableAttrVO> selectDefaultXml();

    /**
     * 매장 환경설정 XML 값을 insert
     * @param param
     * @return
     */
    int insertStoreConfgXml(DefaultMap<String> param);

    /**
     * 매장 환경설정 XML 값을 update
     * @param param
     * @return
     */
    int updateStoreConfgXml(DefaultMap<String> param);

    /**
     * 매장 테이블 속성 저장(Merge)
     * @param tableAttrVO
     * @return
     */
    int mergeStoreTableAttr(TableAttrVO tableAttrVO);
}
