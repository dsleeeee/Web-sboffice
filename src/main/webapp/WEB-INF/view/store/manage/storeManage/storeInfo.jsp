<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<wj-popup id="storeInfoLayer" control="storeInfoLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:800px;height:600px;">
  <div class="wj-dialog wj-dialog-columns title">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="storeManage.storeInfo" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body" ng-controller="storeInfoCtrl">

      <%-- 탭 --%>
      <ul class="subTab">
        <%-- 매장정보 --%>
        <li><a id="storeInfo" href="#" class="on"><s:message code="storeManage.storeInfo" /></a></li>
        <%-- 매장환경 --%>
        <li><a id="storeEnv"  href="#"><s:message code="storeManage.storeEnv" /></a></li>
      </ul>

      <div style="height:400px; overflow-y: auto;">
        <f:form id="viewForm">
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
              <th><s:message code="storeManage.hqOfficeCd" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="hqOfficeCd" class="sb-input w100" ng-model="hqOfficeCd" readonly="readonly"/>
              </td>
              <%-- 본사명 --%>
              <th><s:message code="storeManage.hqOfficeNm" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="hqOfficeNm" class="sb-input w100" ng-model="hqOfficeNm" readonly="readonly"/>
              </td>
            </tr>
            <tr>
              <%-- 매장코드 --%>
              <th><s:message code="storeManage.storeCd" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="storeCd" class="sb-input w100" ng-model="storeCd" readonly="readonly"/>
              </td>
              <%-- 매장명 --%>
              <th><s:message code="storeManage.storeNm" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="storeNm" class="sb-input w100" ng-model="storeNm" maxlength="15"/>
              </td>
            </tr>
            <tr>
              <%-- 상호명 --%>
              <th><s:message code="storeManage.bizStoreNm" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="bizStoreNm" class="sb-input w100" ng-model="bizStoreNm" maxlength="15"/>
              </td>
              <%-- 대표자명 --%>
              <th><s:message code="storeManage.onwerNm" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="ownerNm" class="sb-input w100" ng-model="ownerNm" maxlength="10"/>
              </td>
            </tr>
            <tr>
              <%-- 포스개점일자 --%>
              <th><s:message code="storeManage.posOpenDate" /></th>
              <td>
                <div class="sb-select">
                  <span class="txtIn">
                    <input id="openPosDate" name="startDt" class="w200" />
                  </span>
                </div>
              </td>
              <%-- 날씨표시지역 --%>
              <th><s:message code="storeManage.weatherArea" /><em class="imp">*</em></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="areaCd"
                          ng-model="areaCd"
                          items-source="_getComboData('areaCd')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)">
                  </wj-combo-box>
                </div>
              </td>
            </tr>
            <tr>
              <%-- 용도 --%>
              <th><s:message code="storeManage.cls" /><em class="imp">*</em></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="clsFg"
                          ng-model="clsFg"
                          items-source="_getComboData('clsFg')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)">
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
                          items-source="_getComboData('sysStatFg')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)">
                  </wj-combo-box>
                </div>
              </td>
            </tr>
            <tr>
              <%-- 설치포스수 --%>
              <th><s:message code="storeManage.installPosCnt" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="installPosCnt" class="sb-input w100" readonly="readonly"/>
              </td>
              <td></td>
              <td></td>
            </tr>
            <tr>
              <%-- 사업자번호 --%>
              <th><s:message code="storeManage.bizNo" /><em class="imp">*</em></th>
              <td colspan="3">
                <input type="text" id="rBizNo1" class="sb-input w10" maxlength="3"/>-
                <input type="text" id="rBizNo2" class="sb-input w10" maxlength="2"/>-
                <input type="text" id="rBizNo3" class="sb-input w15" maxlength="5"/>
                <a id="btnChkBizNo" href="#" class="btn_grayS ml5"><s:message code="storeManage.chk.duplicate" /></a><Br />
              </td>
            </tr>
            <tr>
              <%-- 전화번호 --%>
              <th><s:message code="storeManage.telNo" /><em class="imp">*</em></th>
              <td>
                <input type="text" id="telNo" class="sb-input w100" maxlength="11" placeholder="<s:message code='storeManage.bizNo.comment' />" />
              </td>
              <%-- 팩스번호 --%>
              <th><s:message code="storeManage.faxNo" /></th>
              <td>
                <input type="text" id="faxNo" class="sb-input w100" maxlength="11" placeholder="<s:message code='storeManage.bizNo.comment' />" />
              </td>
            </tr>
            <tr>
              <%-- 이메일 --%>
              <th><s:message code="storeManage.emailAddr" /></th>
              <td colspan="3">
                <input type="text" id="emailAddr" class="sb-input w100" maxlength="50"/>
              </td>
            </tr>
            <tr>
              <%-- 홈페이지 --%>
              <th><s:message code="storeManage.hmpgAddr" /></th>
              <td colspan="3">
                <input type="text" id="hmpgAddr" class="sb-input w100" maxlength="30"/>
              </td>
            </tr>
            <tr>
              <%-- 주소 //TODO 주소검색 추가 필요 --%>
              <th><s:message code="storeManage.addr" /><em class="imp">*</em></th>
              <td colspan="3">
                <input type="text" id="postNo" class="sb-input w30" maxlength="5"/>
                <a id="btnSrchAddr" href="#" class="btn_grayS ml5">
                  <s:message code="storeManage.srchAddr" />
                </a>
                <br>
                <input type="text" id="addr" class="sb-input w100" maxlength="60"/>
                <input type="text" id="addrDtl" class="sb-input w100" maxlength="60"/>
              </td>
            </tr>
            <tr>
              <%-- 관리업체 //TODO 팝업으로 변경 --%>
              <th><s:message code="storeManage.manageVan" /><em class="imp">*</em></th>
              <td>
                <input type="text" name="manageVan" id="manageVan" class="sb-input w100" readonly="readonly" ng-click="searchManageVan()">
              </td>
                <%-- 대리점 //TODO 팝업으로 변경 --%>
              <th><s:message code="storeManage.agency" /><em class="imp">*</em></th>
              <td>
                <input type="text" name="agency" id="agency" class="sb-input w100" readonly="readonly" ng-click="searchAgency()">
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
                <input id="sysRemark" type="text" class="sb-input w100" maxlength="100"/>
              </td>
            </tr>
            <tr>
              <%-- 본사비고 --%>
              <th><s:message code="storeManage.hdRemark" /></th>
              <td colspan="3">
                <input id="hdRemark" type="text" class="sb-input w100" maxlength="100"/>
              </td>
            </tr>
            <tr>
              <%-- 특이사항 --%>
              <th><s:message code="storeManage.uniqueRemark" /></th>
              <td colspan="3">
                <input id="rStoreRemark" type="text" class="sb-input w100" maxlength="100"/>
              </td>
            </tr>
            </tbody>
          </table>
        </f:form>
      </div>

      <div class="btnSet">
        <%-- 저장 --%>
        <span><a href="#" class="btn_blue" id="btnSave" ng-click="save()"><s:message code="cmm.save" /></a></span>
        <%-- 닫기 --%>
        <%--<span><a href="#" class="btn_gray" id="btnClose"><s:message code="cmm.close" /></a></span>--%>
      </div>
    </div>

  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/storeInfo.js?ver=2018102301" charset="utf-8"></script>
