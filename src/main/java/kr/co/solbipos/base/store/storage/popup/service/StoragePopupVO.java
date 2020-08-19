package kr.co.solbipos.base.store.storage.popup.service;

import kr.co.solbipos.application.common.service.PageVO;

public class StoragePopupVO extends PageVO {

    private static final long serialVersionUID = -4091176508608651478L;

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
    /** 최종수정자 */
    private String empNm;
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
	public String getEmpNm() {
		return empNm;
	}
	public void setEmpNm(String empNm) {
		this.empNm = empNm;
	}
	
}
