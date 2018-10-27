<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 매장사원 등록/수정 레이어 --%>

<div id="storeSaveDim" class="fullDimmed" style="display:none;"></div>
<div id="storeSaveLayer" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w870px">
      <p id="popTitle" class="tit"><s:message code="storeEmp.layer.empRegist" /></p>
      <a href="#" class="btn_close"></a>
      <div class="con">
        <div class="tabType1">
          <ul>
            <li><a id="empBasicInfoTab" href="#" class="on"><s:message code="storeEmp.layer.empBasicInfo" /></a></li>
          </ul>
        </div>

        <div id="regArea"  style="height:210px;">
          <form id="regForm">
            <table class="tblType01">
              <colgroup>
                <col class="w15" />
                <col class="w35" />
                <col class="w15" />
                <col class="w35" />
              </colgroup>
              <tbody>
                <tr>
                  <%-- 사원번호 --%>
                  <th>
                    <div class="impWrap"><s:message code="storeEmp.empNo" /><em class="imp">*</em></div>
                  </th>
                  <td>
                    <div class="msgWrap">
                      <div class="sb-select regist">
                        <div id="rEmpNo"></div>
                      </div>
                      <span id="empNoErrorMsg" class="errorMsg" style="display:none;"></span>
                      <span id="empNo" class="s12"></span>
                    </div>
                  </td>
                  <%-- 사원명 --%>
                  <th>
                    <div class="impWrap"><s:message code="storeEmp.empNm" /><em class="imp">*</em></div>
                  </th>
                  <td>
                    <div class="msgWrap">
                      <div class="sb-select">
                        <div id="rEmpNm"></div>
                      </div>
                      <span id="empNmErrorMsg" class="errorMsg" style="display:none;"></span>
                    </div>
                  </td>
                </tr>
                <tr>
                  <%-- SMS수신여부 --%>
                  <th>
                    <div class="impWrap"><s:message code="storeEmp.smsRecvYn" /><em class="imp">*</em></div>
                  </th>
                  <td>
                    <div class="sb-select">
                      <div id="rSmsRecvYn"></div>
                    </div>
                  </td>
                  <%-- 웹사용자ID --%>
                  <th>
                    <div class="impWrap"><s:message code="storeEmp.layer.userId" /><em class="imp webUseY">*</em></div>
                  </th>
                  <td>
                    <div id="webUserRegistView">
                      <div class="msgWrap">
                        <div class="sb-select regist">
                          <div id="rUserId"></div>
                        </div>
                        <span id="userIdErrorMsg" class="errorMsg" style="display:none; font-size:12px;"></span>
                      </div>
                      <a class="btn_grayS2 regist" id="btnCheckDup" href="#"><s:message code="storeEmp.layer.chk.dup" /></a>
                    </div>
                    <div id="webUserModifyView">
                      <span id="userId" class="s12"></span>
                    </div>
                  </td>
                </tr>
                <tr>
                  <%-- 비밀번호 --%>
                  <th>
                    <div class="impWrap"><s:message code="storeEmp.pwd" /><em class="imp webUseY">*</em></div>
                  </th>
                  <td>
                    <div id="detailPwdArea" class="txtIn msgWrap">
                      <input id="rNewPwd" type="password" maxlength="16" style="width:45%; font-size:12px;"/>
                      <span id="newPwdErrorMsg" class="errorMsg" style="display:none;"></span>
                      <input id="rNewPwdConfirm" type="password" maxlength="16" placeholder="<s:message code='storeEmp.pwdConfirm' />" style="width:45%; font-size:12px;"/>
                      <span id="newPwdConfirmErrorMsg" class="errorMsg" style="display:none; left:110px;"></span>
                    </div>
                  </td>
                  <%-- 웹사용여부 --%>
                  <th>
                    <div class="impWrap"><s:message code="storeEmp.webUseYn" /><em class="imp">*</em></div>
                  </th>
                  <td>
                    <div class="sb-select">
                      <div id="rWebUseYn"></div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <%-- 재직여부 --%>
                  <th>
                    <div class="impWrap"><s:message code="storeEmp.serviceFg" /><em class="imp">*</em></div>
                  </th>
                  <td>
                    <div class="sb-select">
                      <div id="rServiceFg"></div>
                    </div>
                  </td>
                  <%-- 휴대폰번호 --%>
                  <th>
                    <div class="impWrap"><s:message code="storeEmp.mpNo" /><em class="imp">*</em></div>
                  </th>
                  <td>
                    <div class="msgWrap">
                      <div class="sb-select">
                        <div id="rMpNo"></div>
                      </div>
                      <span id="mpNoErrorMsg" class="errorMsg" style="display:none;"></span>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </form>
        </div>

        <%-- 버튼 영역 --%>
        <div class="btnSet">
          <%-- 저장 --%>
          <span><a href="#" class="btn_blue" id="btnSave"><s:message code="cmm.save" /></a></span>
          <%-- 닫기 --%>
          <span><a href="#" class="btn_gray btnClose"><s:message code="cmm.close" /></a></span>
        </div>
      </div>
    </div>
  </div>
</div>

<script>
var serviceFgExcpAll = ${ccu.getCommCodeExcpAll("007")};
var useYnExcpAll = ${ccu.getCommCodeExcpAll("067")};
var RecvYnExcpAll = ${ccu.getCommCodeExcpAll("072")};
</script>

