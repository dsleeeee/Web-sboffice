package kr.co.solbipos.store.manage.virtuallogin.service;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;

/**
 * 가맹점관리 > 매장관리 > 가상 로그인
 *
 * @author 노현수
 */
public interface VirtualLoginService {

    /**
     * 가상로그인 목록 조회
     *
     * @param
     * @return
     */
    List<DefaultMap<String>> selectVirtualLogin(VirtualLoginVO virtualLoginVO);

}
