<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="storeRegistLayer" show-trigger="Click" hide-trigger="Click" style="width:800px;height:600px;" fade-in="false" fade-out="false">
  <div class="wj-dialog wj-dialog-columns title" ng-controller="storeInfoCtrl">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="storeManage.storeInfo" />
      <span id="storeInfoTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">

      <%-- 탭 --%>
      <ul class="subTab" style="display: none;">
        <%-- 매장정보 --%>
        <li><a id="storeInfo" href="#" class="on"><s:message code="storeManage.storeInfo" /></a></li>
        <%-- 매장환경 --%>
        <li><a id="storeEnv" href="#" ng-click="changeTab();"><s:message code="storeManage.storeEnv" /></a></li>
      </ul>

      <div style="height:400px; overflow-y: auto;">
        <f:form id="registForm">
          <h3 class="h3_tbl"><s:message code="storeManage.basicInfo" /></h3>
          <table class="searchTbl">
            <colgroup>
              <col class="w15" />
              <col class="w35" />
              <col class="w15" />
              <col class="w35" />
            </colgroup>
            <tbody>
            <tr>
              <%-- 본사코드 --%>
              <th><s:message code="storeManage.hqOfficeCd" /></th>
              <td>
                <input type="text" class="sb-input w100" ng-model="hqOfficeCd" readonly="readonly"/>
              </td>
              <%-- 본사명 --%>
              <th><s:message code="storeManage.hqOfficeNm" /></th>
              <td>
                <input type="text" class="sb-input w100" ng-model="hqOfficeNm" readonly="readonly"/>
              </td>
            </tr>
            <tr>
              <%-- 매장코드 --%>
              <th><s:message code="storeManage.storeCd" /></th>
              <td>
                <input type="text" class="sb-input w100" ng-model="storeCd" readonly="readonly"/>
              </td>
              <%-- 매장명 --%>
              <th><s:message code="storeManage.storeNm" /><em class="imp">*</em></th>
              <td>
                <input type="text" class="sb-input w100" ng-model="storeNm" maxlength="15"/>
              </td>
            </tr>
            <tr>
              <%-- 상호명 --%>
              <th><s:message code="storeManage.bizStoreNm" /><em class="imp">*</em></th>
              <td>
                <input type="text" class="sb-input w100" ng-model="bizStoreNm" maxlength="15"/>
              </td>
              <%-- 대표자명 --%>
              <th><s:message code="storeManage.onwerNm" /><em class="imp">*</em></th>
              <td>
                <input type="text" class="sb-input w100" ng-model="ownerNm" maxlength="10"/>
              </td>
            </tr>
            <tr>
              <%-- 포스개점일자 --%>
              <th><s:message code="storeManage.posOpenDate" /></th>
              <td>
                <div class="sb-select" >
                  <%--<input id="openPosDate" class="w100" readonly="readonly">--%>
                  <wj-input-date
                          value="openPosDate"
                          ng-model="openPosDate"
                          control="startDateCombo"
                          min="2000-01-01"
                          max="2099-12-31"
                          initialized="_initDateBox(s)">
                  </wj-input-date>
                </div>
              </td>
              <%-- 날씨표시지역 --%>
              <th><s:message code="storeManage.weatherArea" /><em class="imp">*</em></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="areaCd"
                          ng-model="areaCd"
                          control="areaCdCombo"
                          items-source="_getComboData('areaCd')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          selected-index-changed="setAreaCdVal(s,e)">
                  </wj-combo-box>
                </div>
              </td>
            </tr>
            <tr>
              <%-- 용도 --%>
              <th><s:message code="storeManage.cls" /></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="clsFg"
                          ng-model="clsFg"
                          control="clsFgCombo"
                          items-source="_getComboData('clsFg')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          selected-index-changed="setClsFgVal(s,e)"
                          is-read-only="true">
                  </wj-combo-box>
                </div>
              </td>
              <%-- 매장상태 --%>
              <th><s:message code="storeManage.sysStatFg" /><em class="imp">*</em></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="sysStatFg"
                          ng-model="sysStatFg"
                          control="sysStatFgCombo"
                          items-source="_getComboData('sysStatFg')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          selected-index-changed="setSysStatFgVal(s,e)"
                          control="sysStatFgCombo">
                  </wj-combo-box>
                </div>
              </td>
            </tr>
            <tr>
              <%-- 설치포스수 --%>
              <th><s:message code="storeManage.installPosCnt" /></th>
              <td>
                <input type="text" ng-model="installPosCnt" class="sb-input w100" readonly="readonly"/>
              </td>
              <td></td>
              <td></td>
            </tr>
            <tr>
              <%-- 사업자번호 --%>
              <th><s:message code="storeManage.bizNo" /><em class="imp">*</em></th>
              <td colspan="3">
                <input type="text" ng-model="bizNo1" class="sb-input w10" maxlength="3"/>-
                <input type="text" ng-model="bizNo2" class="sb-input w10" maxlength="2"/>-
                <input type="text" ng-model="bizNo3" class="sb-input w15" maxlength="5"/>
                <a href="#" class="btn_grayS ml5" ng-click="checkBizNoDuplicate()"><s:message code="storeManage.chk.duplicate" /></a><Br />
              </td>
            </tr>
            <tr>
              <%-- 전화번호 --%>
              <th><s:message code="storeManage.telNo" /><em class="imp">*</em></th>
              <td>
                <input type="text" ng-model="telNo" class="sb-input w100" maxlength="11" placeholder="<s:message code='storeManage.bizNo.comment' />" />
              </td>
              <%-- 팩스번호 --%>
              <th><s:message code="storeManage.faxNo" /></th>
              <td>
                <input type="text" ng-model="faxNo" class="sb-input w100" maxlength="11" placeholder="<s:message code='storeManage.bizNo.comment' />" />
              </td>
            </tr>
            <tr>
              <%-- 이메일 --%>
              <th><s:message code="storeManage.emailAddr" /></th>
              <td colspan="3">
                <input type="text" ng-model="emailAddr" class="sb-input w100" maxlength="50"/>
              </td>
            </tr>
            <tr>
              <%-- 홈페이지 --%>
              <th><s:message code="storeManage.hmpgAddr" /></th>
              <td colspan="3">
                <input type="text" ng-model="hmpgAddr" class="sb-input w100" maxlength="30"/>
              </td>
            </tr>
            <tr>
              <%-- 주소 //TODO 주소검색 추가 필요 --%>
              <th><s:message code="storeManage.addr" /><em class="imp">*</em></th>
              <td colspan="3">
                <input type="text" ng-model="postNo" class="sb-input w30" maxlength="5"/>
                <a href="#" class="btn_grayS ml5" ng-click="searchAddr()">
                  <s:message code="storeManage.srchAddr" />
                </a>
                <br>
                <input type="text" ng-model="addr" class="sb-input w100" maxlength="60"/>
                <input type="text" ng-model="addrDtl" class="sb-input w100" maxlength="60"/>
              </td>
            </tr>
            <tr>
              <%-- 관리업체 --%>
              <th><s:message code="storeManage.manageVan" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="manageVanNm" class="sb-input w100" readonly="readonly" ng-click="searchManageVan()">
                <input type="hidden" id="manageVanCd" ng-model="vanCd">
              </td>
                <%-- 대리점 --%>
              <th><s:message code="storeManage.agency" /><em class="imp">*</em></th>
              <td>
                <input type="text" name="agencyNm" id="agencyNm" class="sb-input w100" readonly="readonly" ng-click="searchAgency()">
                <input type="hidden" name="agencyCd" id="agencyCd" ng-model="agencyCd">
              </td>
            </tr>
            </tbody>
          </table>
          <%-- 비고 --%>
          <h3 class="h3_tbl"><s:message code="storeManage.remark" /></h3>
          <table class="searchTbl">
            <colgroup>
              <col class="w15" />
              <col class="w35" />
              <col class="w15" />
              <col class="w35" />
            </colgroup>
            <tbody>
            <tr>
              <%-- 시스템비고 --%>
              <th><s:message code="storeManage.systemRemark" /></th>
              <td colspan="3">
                <input id="sysRemark" type="text" class="sb-input w100" maxlength="100" ng-model="sysRemark"/>
              </td>
            </tr>
            <tr>
              <%-- 본사비고 --%>
              <th><s:message code="storeManage.hdRemark" /></th>
              <td colspan="3">
                <input id="hdRemark" type="text" class="sb-input w100" maxlength="100" ng-model="hdRemark" />
              </td>
            </tr>
            <tr>
              <%-- 특이사항 --%>
              <th><s:message code="storeManage.uniqueRemark" /></th>
              <td colspan="3">
                <input id="remark" type="text" class="sb-input w100" maxlength="100" ng-model="remark"/>
              </td>
            </tr>
            </tbody>
          </table>
        </f:form>
      </div>

      <div class="btnSet">
        <%-- 저장 --%>
        <span><a href="#" class="btn_blue pd20" id="btnSave" ng-click="save()"><s:message code="cmm.save" /></a></span>
      </div>
    </div>

  </div>
</wj-popup>
<script>
  var startDate = "${sessionScope.sessionInfo.startDate}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/newStoreRegist.js?ver=2018102301" charset="utf-8"></script>

<%-- 관리업체 조회 --%>
<c:import url="/WEB-INF/view/application/layer/searchVan.jsp">
</c:import>

<%-- 대리점 조회 --%>
<c:import url="/WEB-INF/view/application/layer/searchAgency.jsp">
</c:import>
