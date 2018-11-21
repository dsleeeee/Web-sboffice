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
  <div>
    <%-- 터미널 사용 설정 --%>
    <div class="searchBar flddUnfld">
      <a href="#" class="open fl">${menuNm}</a>
      <%-- 조회 --%>
      <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
        <button class="btn_blue fr" id="btnSearch" ng-click="$broadcast('terminalCtrl')">
          <s:message code="cmm.search" />
        </button>
      </div>
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

    <div class="wj-TblWrap mt40" >
      <div class="wj-TblWrapBr pd20" style="height:470px; overflow-y: hidden;">

        <%-- 터미널 선택 --%>
        <div class="updownSet oh mb10" ng-controller="terminalCtrl">
          <%-- 터미널 선택 --%>
          <div class="sb-select w30 fl">
            <wj-combo-box
                    id="sTerminalFg"
                    ng-model="terminalFg"
                    items-source="_getComboData('terminalFg')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)"
                    selected-index-changed="changeTerminalFg(s,e)">
            </wj-combo-box>
            <input type="hidden" id="terminalFgVal" value={{terminalFg}} />
          </div>
          <%-- 포스 선택 --%>
          <div class="sb-select w10 fl" id="posListArea" style="display: none;">
            <wj-combo-box
                    id="sPosFg"
                    ng-model="posFg"
                    items-source="posFgArr"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    control="comboDt.posCombo"
                    selected-index-changed="setPosFgVal(s,e)">
            </wj-combo-box>
          </div>

          <%-- 코너 선택 --%>
          <div class="sb-select w10 fl" id="cornerListArea" style="display: none;">
            <wj-combo-box
                    id="sCornerFg"
                    ng-model="cornerFg"
                    items-source="cornerFgArr"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    control="comboDt.cornerCombo"
                    selected-index-changed="setCornerFgVal(s,e)">
            </wj-combo-box>
          </div>

          <div class="fr mb10" id="posBtnArea" style="display:none">
            <button class="btn_skyblue" ng-click="copyEnv()"><s:message code="terminalManage.copy.posEnvst"/></button>
            <button class="btn_skyblue" ng-click="posAddRow()"><s:message code="cmm.add"/></button>
            <button class="btn_skyblue" ng-click="posSave()"><s:message code="cmm.save"/></button>
          </div>
          <div class="fr mb10" id="cornerBtnArea" style="display:none">
            <button class="btn_skyblue" ng-click="cornerAddRow()"><s:message code="cmm.add"/></button>
            <button class="btn_skyblue" ng-click="cornerSave()"><s:message code="cmm.save"/></button>
          </div>
        </div>

        <%-- 포스 설정 --%>
        <div class="wj-gridWrap" style="height:430px; overflow-y: hidden;" ng-controller="posCtrl" id="posArea">
          <wj-flex-grid
                  autoGenerateColumns="false"
                  control="flex"
                  initialized="initGrid(s,e)"
                  sticky-headers="true"
                  selection-mode="Row"
                  items-source="data"
                  item-formatter="_itemFormatter"
                  beginning-edit="changeVendorFg(s,e)">
            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.storeCd"/>" binding="storeCd" width="*" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.posNo"/>" binding="posNo" width="*" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.vendorFg"/>" binding="vendorFg" data-map="vendorFgDataMap" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.vendorFgNm"/>" binding="vendorFgNm" visible="false" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.vendorCd"/>" binding="vendorNm" data-map="vanCdDataMap" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.vendorTermnlNo"/>" binding="vendorTermnlNo" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.vendorSerNo"/>" binding="vendorSerNo" width="*"></wj-flex-grid-column>
          </wj-flex-grid>
        </div>

        <%-- 코너 설정 --%>
        <div class="wj-gridWrap" style="height:430px; overflow-y: hidden;" ng-controller="cornerCtrl" id="cornerArea" style="display: none;">
          <wj-flex-grid
                  autoGbeginning-editenerateColumns="false"
                  control="flex"
                  initialized="initGrid(s,e)"
                  sticky-headers="true"
                  selection-mode="Row"
                  items-source="data"
                  item-formatter="_itemFormatter"
                  beginning-edit="changeVendorFg(s,e)">
            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.cornrCd"/>" binding="cornrCd" width="*" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.vendorFg"/>" binding="vendorFg" data-map="vendorFgDataMap" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.vendorFgNm"/>" binding="vendorFgNm" visible="false" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.vendorCd"/>" binding="vendorNm" data-map="vanCdDataMap" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.vendorTermnlNo"/>" binding="vendorTermnlNo" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="terminalManage.vendorSerNo"/>" binding="vendorSerNo" width="*"></wj-flex-grid-column>
          </wj-flex-grid>
        </div>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
var orgnFg = "${orgnFg}";
var baseUrl = "${baseUrl}";
var vendorFg = ${ccu.getCommCodeExcpAll("078")};
var vandorList = ${vendorList};
var terminalFg = ${cnv.getEnvCodeExcpAll("2028")};
var useYnFg    = ${ccu.getCommCodeExcpAll("067")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/terminalManage/terminal.js?ver=20181006.01" charset="utf-8"></script>

<%-- 매장선택 레이어 팝업 --%>
<c:import url="/WEB-INF/view/store/manage/terminalManage/store.jsp">
</c:import>
