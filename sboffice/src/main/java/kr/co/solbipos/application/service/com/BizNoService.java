package kr.co.solbipos.application.service.com;

import kr.co.solbipos.application.domain.com.BizNoVO;

/**
 * 사업자번호 유효성검사
 *
 * @author 노현수
 */
public interface BizNoService {

    /** 사업자번호 유효성검사 */
    boolean bizNoVerify(BizNoVO bizNoVO);

}
