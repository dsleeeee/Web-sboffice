package kr.co.solbipos.base.store.storage.service;

import kr.co.solbipos.application.common.service.PageVO;

import java.util.Date;

/**
* @Class Name : StorageVO.java
* @Description : 가맹점 관리 > 매장관리 > 매장정보조회
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2020.03.24  조동훤      최초생성
*
* @author 조동훤
* @since 2018. 08.09
* @version 1.0
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public class StorageVO extends PageVO {

    private static final long serialVersionUID = -1652554561773776441L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 창고코드 */
    private String storageCd;
    /** 창고명 */
    private String storageNm;
    /** 사용여부 */
    private String useYn;
    /** 등록 일시 */
    private String regDt;
    /** 등록 아이디 */
    private String regId;
    /** 수정 일시 */
    private String modDt;
    /** 수정 아이디 */
    private String modId;
    /** 로그인 권한(M, H, S) */
    private String orgnFg;
    /**
     * @return the hqOfficeCd
     */
    public String getHqOfficeCd() {
      return hqOfficeCd;
    }
    /**
     * @param hqOfficeCd the hqOfficeCd to set
     */
    public void setHqOfficeCd(String hqOfficeCd) {
      this.hqOfficeCd = hqOfficeCd;
    }
    /**
     * @return the storeCd
     */
    public String getStoreCd() {
        return storeCd;
    }
    /**
     * @param storeCd the storeCd to set
     */
    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }
    /**
     * @return the storeNm
     */
    public String getStoreNm() {
        return storeNm;
    }
    /**
     * @param storeNm the storeNm to set
     */
    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }
	public String getOrgnFg() {
		return orgnFg;
	}
	public void setOrgnFg(String orgnFg) {
		this.orgnFg = orgnFg;
	}
	public String getStorageCd() {
		return storageCd;
	}
	public void setStorageCd(String storageCd) {
		this.storageCd = storageCd;
	}
	public String getStorageNm() {
		return storageNm;
	}
	public void setStorageNm(String storageNm) {
		this.storageNm = storageNm;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	
}
