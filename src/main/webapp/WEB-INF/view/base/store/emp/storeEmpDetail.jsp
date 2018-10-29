<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 매장사원 상세정보 레이어 --%>

<div id="storeDetailDim" class="fullDimmed" style="display:none;"></div>
<div id="storeDetailLayer" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w870px">
      <p id="popTitle" class="tit"><s:message code="storeEmp.layer.empDetail" /></p>
      <a href="#" class="btn_close"></a>
      <div class="con">
        <div class="tabType1">
          <ul>
            <li><a id="empBasicInfoTab" href="#" class="on"><s:message code="storeEmp.layer.empBasicInfo" /></a></li>
          </ul>
        </div>

        <div id="viewArea" class="mt20 sc">
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
                  <div class="impWrap"><s:message code="storeEmp.empNo" /></div>
                </th>
                <td id="vEmpNo"></td>
                <%-- 사원명 --%>
                <th>
                  <div class="impWrap"><s:message code="storeEmp.empNm" /></div>
                </th>
                <td id="vEmpNm"></td>
              </tr>
              <tr>
                <%-- SMS수신여부 --%>
                <th>
                  <div class="impWrap"><s:message code="storeEmp.smsRecvYn" /></div>
                </th>
                <td id="vSmsRecvYn"></td>
                <%-- 웹사용자ID --%>
                <th>
                  <div class="impWrap"><s:message code="storeEmp.layer.userId" /></div>
                </th>
                <td id="vUserId"></td>
              </tr>
              <tr>
                <%-- 비밀번호 --%>
                <th>
                  <div class="impWrap"><s:message code="storeEmp.pwd" /></div>
                </th>
                <td>
                  <a class="btn_grayS2" id="btnChangePwd" href="#"><s:message code="storeEmp.layer.changeBtn" /></a>
                </td>
                <%-- 웹사용여부 --%>
                <th>
                  <div class="impWrap"><s:message code="storeEmp.webUseYn" /></div>
                </th>
                <td id="vWebUseYn"></td>
              </tr>
              <tr>
                <%-- 재직여부 --%>
                <th>
                  <div class="impWrap"><s:message code="storeEmp.serviceFg" /></div>
                </th>
                <td id="vServiceFg"></td>
                <%-- 휴대폰번호 --%>
                <th>
                  <div class="impWrap"><s:message code="storeEmp.mpNo" /></div>
                </th>
                <td id="vMpNo"></td>
              </tr>
            </tbody>
          </table>
        </div>

        <%-- 버튼 영역 --%>
        <div class="btnSet">
          <%-- 수정 --%>
          <span><a href="#" class="btn_blue" id="btnEdit"><s:message code="cmm.edit" /></a></span>
          <%-- 닫기 --%>
          <span><a href="#" class="btn_gray btnClose"><s:message code="cmm.close" /></a></span>
        </div>
      </div>
    </div>
  </div>
</div>

