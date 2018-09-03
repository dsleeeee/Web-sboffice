package kr.co.solbipos.base.store.emp.store.service.enums;

public enum StoreEmpResult {
    SUCC(""),
    REGIST_FAIL("cmm.registFail"),
    MODIFY_FAIL("cmm.modifyFail"),
    WEBUSER_SAVE_FAIL("storeEmp.result.fail.webuser"),
    PWDHIST_SAVE_FAIL("storeEmp.result.fail.pwdhist"),
    PASSWORD_MODIFY_FAIL("storeEmp.result.fail.pwd"),
    PASSWORD_NOT_MATCH("storeEmp.result.fail.pwdconfirm"),
    PASSWORD_NOT_VALID_FORMAT("login.pw.chg.regexp"),
    PASSWORD_NEW_OLD_MATCH("login.layer.pwchg.match"),
    NOT_ALLOW("cmm.access.denied"),
    FAIL("cmm.error");

    String value;

    StoreEmpResult(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
