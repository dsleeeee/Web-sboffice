<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/store/manage/terminalManage/" />

<div class="subCon">
  <div >
    <%-- 터미널 사용 설정 --%>
    <div class="searchBar flddUnfld">
      <a href="#" class="open">${menuNm}</a>
    </div>
    <table class="searchTbl">
      <colgroup>
        <col class="w15" />
        <col class="w35" />
        <col class="w15" />
        <col class="w35" />
      </colgroup>
      <tbody>
        <tr class="brt">
          <th><s:message code="terminalManage.store"/></th>
          <td class="oh" colspan="3">
            <input type="text" class="sb-input w40" name="storeInfo" id="storeInfo" readonly="readonly" >
            <input type="hidden" id="storeCd" ng-model="storeCd" >
          </td>
        </tr>
      </tbody>
    </table>

    <%-- 조회 --%>
    <div class="mt10 oh">
      <button class="btn_blue fr" id="btnSearch" ng-click="$broadcast('posCtrl')">
        <s:message code="cmm.search" />
      </button>
    </div>

    <%-- POS 설정 --%>
    <div class="wj-TblWrap mt40" ng-controller="posCtrl" id="posArea">
        <div class="wj-TblWrapBr pd20" style="height:470px; overflow-y: hidden;">
          <div class="updownSet oh mb10">

            <div class="sb-select w30 fl">
              <wj-combo-box
                      id="terminalPosFg"
                      ng-model="terminalFg"
                      items-source="_getComboData('terminalPosFg')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)"
                      selected-index-changed="changeTerminalPosFg(s,e)">
              </wj-combo-box>
              <input type="hidden" id="terminalPosFgVal" value={{terminalPosFg}} />
              <a href="#" class="btn_grayS"><s:message code="cmm.save"/></a>
            </div>
            <div class="fr mb10" id="posBtnArea" style="display:none">
              <button class="btn_skyblue" ng-click="copyEnv()"><s:message code="terminalManage.copy.posEnvst"/></button>
              <button class="btn_skyblue" ng-click="addRow()"><s:message code="cmm.add"/></button>
              <button class="btn_skyblue" ng-click="save()"><s:message code="cmm.save"/></button>
            </div>
          </div>

          <div id="posGrid" class="wj-gridWrap" style="height:430px; overflow-y: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">
              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="terminalManage.posNo"/>" binding="posNo" width="*"  is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="terminalManage.posNm"/>" binding="posNm" width="*"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="terminalManage.hwAuthKey"/>" binding="hwAuthKey" width="*"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="terminalManage.vanCertYn"/>" binding="vanCertYn" data-map="useYnFgDataMap" width="*"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="terminalManage.vanCd"/>" binding="vanCd" data-map="vanCdDataMap" width="*"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="terminalManage.vanTermnlNo"/>" binding="vanTermnlNo" width="*"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="terminalManage.vanSerNo"/>" binding="vanSerNo" width="*"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="terminalManage.vanCertStartDate"/>" binding="vanCertStartDate" is-read-only="true" width="*"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="terminalManage.vanCertEndDate"/>" binding="vanCertEndDate" is-read-only="true" width="*"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="terminalManage.vanCertCnt"/>" binding="vanCertCnt" is-read-only="true"  width="*"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="terminalManage.remark"/>" binding="remark" width="120"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="terminalManage.useYn"/>" binding="useYn" data-map="useYnFgDataMap" width="*"></wj-flex-grid-column>
            </wj-flex-grid>
          </div>

        </div>
    </div>


    <%-- 코너 설정 --%>
    <div class="wj-TblWrap mt40" ng-controller="cornerCtrl" id="cornerArea" style="display: none;">
      <div class="wj-TblWrapBr pd20" style="height:470px; overflow-y: hidden;">
        <div class="updownSet oh mb10">

          <div class="sb-select w30 fl">
            <wj-combo-box
                    id="terminalCornerFg"
                    ng-model="terminalFg"
                    items-source="_getComboData('terminalCornerFg')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    selected-index-changed="changeTerminalCornerFg(s,e)">
            </wj-combo-box>
            <input type="hidden" id="terminalCornerFgVal" value={{terminalCornerFg}} />
            <a href="#" class="btn_grayS"><s:message code="cmm.save"/></a>
          </div>
          <div class="fr mb10" id="cornerBtnArea" style="display:none">
            <button class="btn_skyblue" ng-click="addRow()"><s:message code="cmm.add"/></button>
            <button class="btn_skyblue" ng-click="save()"><s:message code="cmm.save"/></button>
          </div>
        </div>

        <div id="cornerGrid" class="wj-gridWrap" style="height:430px; overflow-y: hidden;">
          <wj-flex-grid
                  autoGenerateColumns="false"
                  control="flex"
                  initialized="initGrid(s,e)"
                  sticky-headers="true"
                  selection-mode="Row"
                  items-source="data"
                  item-formatter="_itemFormatter">
            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.cornrCd"/>" binding="cornrCd" width="*"  is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.cornrNm"/>" binding="cornrNm" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.owner"/>" binding="owner" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.bizNo"/>" binding="bizNo" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.telNo"/>" binding="telNo" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.vanCd"/>" binding="vanCd" data-map="vanCdDataMap" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.vanTermnlNo"/>" binding="vanTermnlNo" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.vanSerNo"/>" binding="vanSerNo" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.useYn"/>" binding="useYn" data-map="useYnFgDataMap" width="*"></wj-flex-grid-column>
          </wj-flex-grid>
        </div>

      </div>
    </div>



  </div>
</div>

<script type="text/javascript">
var orgnFg = "${orgnFg}";
var baseUrl = "${baseUrl}";
var vanCdFg = ${ccu.getVanList()};
var terminalFg = ${cnv.getEnvCodeExcpAll("2028")};
var useYnFg    = ${ccu.getCommCodeExcpAll("067")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/terminalManage/terminal.js?ver=2018100601" charset="utf-8"></script>

<%-- 매장선택 레이어 팝업 --%>
<c:import url="/WEB-INF/view/store/manage/terminalManage/store.jsp">
</c:import>
