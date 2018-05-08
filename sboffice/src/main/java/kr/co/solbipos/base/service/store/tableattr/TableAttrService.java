package kr.co.solbipos.base.service.store.tableattr;

import java.util.List;
import kr.co.common.data.structure.Result;
import kr.co.solbipos.application.domain.login.SessionInfoVO;
import kr.co.solbipos.base.domain.store.tableattr.TableAttrVO;

/**
 * 기초관리 - 매장관리 - 테이블속성
 *
 * @author 조병준
 *
 */
public interface TableAttrService {

    /**
     * 가맹점별 속성값이 없을 때 기본값 조회,
     * @param sessionInfoVO 세션정보
     * @return 기본테이블속성정보
     */
    List<TableAttrVO> selectTableAttrDefault();

    /**
     * 세션의 가맹점코드로 해당 가맹점의 테이블 속성 조회
     * @param sessionInfoVO 세션정보
     * @return XML_String
     */
    String selectTableAttrByStore(SessionInfoVO sessionInfoVO);

    /**
     * 세션의 가맹점코드에 테이블 속성 저장
     * @param sessionInfoVO 세션정보
     * @param xml 클라이인트로 부터 받은 xml 문자열
     * @return Result
     */
    Result setTableAttr(SessionInfoVO sessionInfoVO, String xml);


}
