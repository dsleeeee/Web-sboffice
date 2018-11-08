<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="hqInfoLayer" control="hqInfoLayer" show-trigger="Click" hide-trigger="Click" style="width:700px;height:620px;" fade-in="false" fade-out="false">
  <div class="wj-dialog wj-dialog-columns title" ng-controller="hqInfoCtrl">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="hqManage.hqInfo" />
      <span id="hqInfoTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">

      <%-- 탭 --%>
      <ul class="subTab">
        <%-- 매장정보 --%>
        <li><a id="hqInfoTab" href="#" class="on"><s:message code="hqManage.hqInfo" /></a></li>
        <%-- 매장환경 --%>
        <li><a id="envSettingTab" href="#" ng-click="showEnvSetting();"><s:message code="hqManage.envSetting" /></a></li>
        <%-- 메뉴관리 --%>
        <%--<li><a id="menuSettingTab" href="#" ng-click="showMenuSetting();"><s:message code="hqManage.menuSetting" /></a></li>--%>
      </ul>

      <div style="height:430px; overflow-y: auto;">
        <f:form id="regForm">
          <table class="searchTbl">
            <colgroup>
              <col class="w20" />
              <col class="w30" />
              <col class="w20" />
              <col class="w30" />
            </colgroup>
            <tbody>
            <tr>
              <%-- 본사코드 --%>
              <th><s:message code="hqManage.hqOfficeCd" /><em class="imp">*</em></th>
              <td colspan="3">
                <div id="rHqOfficeTxt">
                  <input type="text" id="rHqOfficeCd" ng-model="hq.hqOfficeCd" class="sb-input w100" readonly="readonly"/>
                </div>
              </td>
            </tr>
            <tr>
              <%-- 본사명 --%>
              <th><s:message code="hqManage.hqOfficeNm" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="rHqOfficeNm" ng-model="hq.hqOfficeNm" class="sb-input w100" maxlength="15"/>
              </td>
              <%-- 대표자명 --%>
              <th><s:message code="hqManage.ownerNm" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="rOwnerNm" ng-model="hq.ownerNm" class="sb-input w100" maxlength="10"/>
              </td>
            </tr>
            <tr>
              <%--사업자번호 --%>
              <th><s:message code="hqManage.bizNo" /><em class="imp">*</em></th>
              <td colspan="3">
                <input type="text" id="rBizNo1" ng-model="hq.bizNo1" class="sb-input w10" maxlength="3" />
                <input type="text" id="rBizNo2" ng-model="hq.bizNo2" class="sb-input w10" maxlength="2" />
                <input type="text" id="rBizNo3" ng-model="hq.bizNo3" class="sb-input w15" maxlength="5" />
                <input type="hidden" ng-model="hq.beforeBizNo" />
                <a href="#" class="btn_grayS" id="btnChkBizNo" ng-click="chkBizNo()"><s:message code="hqManage.chk.duplicate.bizNo" /></a>
              </td>
            </tr>
            <tr>
              <%-- 상호명/사업자매장명 --%>
              <th><s:message code="hqManage.bizStoreNm" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="rBizStoreNm" class="sb-input w100" ng-model="hq.bizStoreNm" maxlength="15"/>
              </td>
              <%-- 날씨표시지역 --%>
              <th><s:message code="hqManage.weatherArea" /><em class="imp">*</em></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="rWeatherArea"
                          ng-model="hq.areaCd"
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
              <%-- 전화번호 --%>
              <th><s:message code="hqManage.telNo" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="rTelNo" ng-model="hq.telNo" class="sb-input w100" maxlength="11" placeholder="<s:message code='hqManage.bizNo.comment'/>"/>
              </td>
              <%-- 팩스번호 --%>
              <th><s:message code="hqManage.faxNo" /></th>
              <td>
                <input type="text" id="rFaxNo" ng-model="hq.faxNo" class="sb-input w100" maxlength="11" placeholder="<s:message code='hqManage.bizNo.comment' />" />
              </td>
            </tr>
            <tr>
              <%-- 상태구분 --%>
              <th><s:message code="hqManage.sysStatFg" /><em class="imp">*</em></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="rSysStatFg"
                          ng-model="hq.sysStatFg"
                          control="sysStatFgCombo"
                          items-source="_getComboData('sysStatFg')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          selected-index-changed="setSysStatFgVal(s,e)">
                  </wj-combo-box>
                </div>
              </td>
              <%-- 시스템 오픈일자 --%>
              <th><s:message code="hqManage.sysOpenDate" /><em class="imp">*</em></th>
              <td>
                <div class="sb-select" >
                  <wj-input-date
                          value="rSysOpenDate"
                          ng-model="hq.sysOpenDate"
                          control="sysOpenDateCombo"
                          format="yyyy/MM/dd"
                          min="2000-01-01"
                          max="2099-12-31"
                          initialized="_initDateBox(s)">
                  </wj-input-date>
                </div>
              </td>
            </tr>
            <tr>
              <%-- 주소 //todo 주소검색 추가 필요 --%>
              <th><s:message code="hqManage.addr" /><em class="imp">*</em></th>
              <td colspan="3">
                <input type="text" id="postNo" ng-model="hq.postNo" class="sb-input w30" maxlength="5"/>
                <a id="btnSrchAddr" href="#" class="btn_grayS ml5" ng-click="searchAddr()">
                  <s:message code="hqManage.findAddr" />
                </a>
                <br>
                <input type="text" id="addr" ng-model="hq.addr" class="sb-input w100" maxlength="60"/>
                <input type="text" id="addrDtl" ng-model="hq.addrDtl" class="sb-input w100" maxlength="60"/>
              </td>
            </tr>
            <tr>
              <%-- 이메일주소 --%>
              <th><s:message code="hqManage.emailAddr" /></th>
              <td>
                <input type="text" id="rEmailAddr" ng-model="hq.emailAddr" class="sb-input w100" maxlength="50"/>
              </td>
              <%-- 홈페이지 --%>
              <th><s:message code="hqManage.hmpgAddr" /></th>
              <td>
                <input type="text" id="rHmpgAddr" ng-model="hq.hmpgAddr" class="sb-input w100" maxlength="30"/>
              </td>
            </tr>
            <tr>
              <%-- 관리업체 --%>
              <th><s:message code="hqManage.agency" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="rManageVanNm" ng-model="hq.agencyNm" class="sb-input w100" readonly="readonly" ng-click="searchManageVan()">
                <input type="hidden" name="rManageVanCd" id="rManageVanCd" ng-model="hq.agencyCd">
              </td>
              <%-- 용도구분 --%>
              <th><s:message code="hqManage.clsFg" /><em class="imp">*</em></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="rClsFg"
                          ng-model="hq.clsFg"
                          control="clsFgCombo"
                          items-source="_getComboData('clsFg')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          selected-index-changed="setClsFgVal(s,e)"
                          initialized="_initComboBox(s)">
                  </wj-combo-box>
                </div>
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
<script type="text/javascript" src="/resource/solbipos/js/store/manage/hqManage/hqInfo.js?ver=2018102301" charset="utf-8"></script>

<%-- 사업자번호 조회 --%>
<c:import url="/WEB-INF/view/application/layer/checkBizNo.jsp">
</c:import>

<%-- 관리업체 조회 --%>
<c:import url="/WEB-INF/view/application/layer/searchAgency.jsp">
</c:import>
