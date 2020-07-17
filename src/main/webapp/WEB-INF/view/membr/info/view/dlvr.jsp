<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>

<div class="subCon" ng-controller="dlvrCtrl">

  <%-- 조회조건 --%>
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('dlvrCtrl', 1)">
        <s:message code="cmm.search"/>
      </button>
    </div>
  </div>

  <%-- 상단 타이틀 --%>
  <div class="w100 mt10 mb10">
    <table class="searchTbl">
      <colgroup>
        <col class="w5"/>
        <col class="w15"/>
        <col class="w7"/>
        <col class="w5"/>
        <col class="w5"/>
        <col class="w7"/>
        <col class="w5"/>
        <col class="w5"/>
        <col class="w15"/>
        <col class="w7"/>
        <col class="w5"/>
        <col class="w5"/>
      </colgroup>
      <tbody>

      <%-- First Row--%>
      <tr class="brt">
        <%-- 회원번호 --%>
        <th><s:message code="dlvr.membr.no"/></th>
        <td colspan="2">
          <input type="text" class="sb-input w100" ng-model="inputData.membrNo" id="membrNo"/>
        </td>
        <%-- 회원명  --%>
        <th><s:message code="dlvr.membr.name"/></th>
        <td>
          <input type="text" class="sb-input w100" ng-model="inputData.membrNm" id="membrNm"/>
        </td>
        <%-- 회원등급  --%>
        <th><s:message code="dlvr.membr.grade"/></th>
        <td>
          <select type="text" class="sb-select w100" ng-model="memberClass" id="membrGrade"
                  ng_options="class.name for class in classList">
          </select>
        </td>
        <%-- 검색줄수  --%>
        <td colspan="7"></td>
      </tr>

      <%-- Second Row--%>
      <tr class="brt">
        <%-- 배달구역 --%>
        <th><s:message code="dlvr.membr.area"/></th>
        <td colspan="2">
          <div class="sb-select">
            <select type="text" style="border:1px solid #e8e8e8" class="w40" ng-model="dlArea" id="areaOne"
                    ng_options="item.name for item in areaList">
            </select>
            ~
            <select type="text" style="border:1px solid #e8e8e8" class="w40" ng-model="dlArea" id="areaTwo"
                    ng_options="item.name for item in areaList">
            </select>
          </div>
        </td>
        <%-- 상세주소  --%>
        <th><s:message code="dlvr.membr.areaDetail"/></th>
        <td>
          <input type="text" class="sb-input w100" ng-model="inputData.areaDetail" id=""/>
        </td>
        <%-- 배달지사용  --%>
        <th><s:message code="dlvr.membr.areaUseYn"/></th>
        <td>
          <select type="text" class="sb-select w100" ng-model="useYn" id=""
                  ng-options="item.name for item in useYnList">
          </select>
        </td>
        <%-- 전화  --%>
        <th><s:message code="dlvr.membr.phone"/></th>
        <td colspan="2">
          <input type="text" class="sb-input w28" ng-model="telNo" id=""/> -
          <input type="text" class="sb-input w28" ng-model="inputData.phone" id=""/> -
          <input type="text" class="sb-input w28" ng-model="inputData.phone" id=""/>
        </td>
        <%-- 전화사용  --%>
        <th><s:message code="dlvr.membr.phoneUseYn"/></th>
        <td>
          <select type="text" class="sb-select w100" ng-model="phoneUseYn" id=""
                  ng-options="item.name for item in phoneUseYnList">
          </select>
        </td>
        <td colspan="2">
        </td>
      </tr>

      <%-- Thrid Row--%>
      <tr class="brt">
        <%-- 배달구역 --%>
        <th><s:message code="dlvr.membr.area"/></th>
        <td>
          <select type="text" style="border:1px solid #e8e8e8" class="w45" ng-model="addr" id="addr"
                  ng_options="item.name for item in areaList">
          </select>
          ~
          <select type="text" style="border:1px solid #e8e8e8" class="w45" ng-model="addrDtl" id="addrDtl"
                  ng_options="item.name for item in areaList">
          </select>
        </td>
        <td>
          <button class="btn_blk sb-input w100" ng-click=""><s:message code="cmm.apply"/></button>
        </td>
        <%-- 상세주소  --%>
        <th><s:message code="dlvr.membr.areaDetail"/></th>
        <td>
          <input type="text" class="sb-input w100" ng-model="addrDtl" id=""/>
        </td>
        <%-- 배달지사용  --%>
        <th><s:message code="dlvr.membr.areaUseYn"/></th>
        <td>
          <select type="text" class="sb-select w100" ng-model="dlvrUseYn" id="dlvrUseYn
                  ng-options=" item.name for item in useYnList">
          </select>
        </td>
        <%-- 전화  --%>
        <th><s:message code="dlvr.membr.phoneNumber"/></th>
        <td>
          <input type="text" class="sb-input w28" ng-model="inputData.phoneNumber" id=""/> -
          <input type="text" class="sb-input w28" ng-model="inputData.phoneNumber" id=""/> -
          <input type="text" class="sb-input w28" ng-model="inputData.phoneNumber" id=""/>
        </td>
        <td>
          <button class="btn_blk sb-input" ng-click=""><s:message code="cmm.apply"/></button>
        </td>
        <%-- 전화사용  --%>
        <th><s:message code="dlvr.membr.phoneUseYn"/></th>
        <td>
          <select type="text" class="sb-select w100" ng-model="dlvrTeluseYn" id="dlvrTeluseYn"
                  ng-options="item.name for item in phoneUseYnList">
          </select>
        </td>
        <td>
          <button class="btn_blk sb-input" ng-click=""><s:message code="cmm.apply"/></button>
        </td>
        <td>
        </td>
      </tr>

      </tbody>
    </table>
  </div>

  <%-- 그리드 left --%>
  <%-- 페이지 스케일  --%>
  <div class="mt20 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <wj-combo-box
            class="w100px fl"
            id="listScaleBox"
            ng-model="listScale"
            items-source="_getComboData('listScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="initComboBox(s)">
    </wj-combo-box>
  </div>
  <div class="w50 fl" style="width:55%;">
    <div class="wj-gridWrap" style="height:600px; overflow-y: hidden; overflow-x: hidden;">
      <h2 class="h2_tit oh lh30">
        <s:message code="dlvr.membr.adressList"/>
        <button class="btn_skyblue sb-input" style="float: right; margin-top: 1%; margin-right: 1%"
                ng-click="infoDelete('addr')">
          <s:message code="cmm.delete"/>
        </button>
        <button class="btn_skyblue sb-input" style="float: right; margin-top: 1%; margin-right: 1%">
          <s:message code="cmm.save"/>
        </button>
        <button class="btn_skyblue sb-input" ng-click="excelDownload()"
                style="float: right; margin-top: 1%; margin-right: 1%">
          <s:message code="cmm.excel"/>
        </button>
      </h2>
      <wj-flex-grid
              autoGenerateColumns="false"
              control="flex"
              initialized="initGrid(s,e)"
              sticky-headers="true"
              selection-mode="Row"
              items-source="data"
              item-formatter="_itemFormatter"
              is-read-only="true">
        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="dlvr.membr.name"/>" binding="searchResult"
                             width="90" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"
                             is-read-only="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvr.membr.addr"/>" binding="memberClassNm" width="220"
                             is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvr.membr.areaDetail"/>" binding="memberNo" width="200"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvr.membr.useYn"/>" binding="memberNm" width="90"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvr.membr.finalDlvrDate"/>" binding="memberCardNo" width="120"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvr.membr.time"/>" binding="avablPoint" width="110"
                             is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>

  <%-- 그리드 right --%>
  <div class="w50 fr" style="width:44%;">
    <div class="wj-gridWrap" style="height:600px; overflow-y: hidden; overflow-x: hidden;">
      <h2 class="h2_tit oh lh30">
        <s:message code="dlvr.membr.phoneNumberList"/>
        <button class="btn_skyblue sb-input" style="float: right; margin-top: 1%; margin-right: 1%"
                ng-click="infoDelete('tel')">
          <s:message code="cmm.delete"/>
        </button>
        <button class="btn_skyblue sb-input" style="float: right; margin-top: 1%; margin-right: 1%">
          <s:message code="cmm.save"/>
        </button>
        <button class="btn_skyblue sb-input" ng-click="excelDownload()"
                style="float: right; margin-top: 1%; margin-right: 1%">
          <s:message code="cmm.excel"/>
        </button>
      </h2>
      <wj-flex-grid
              autoGenerateColumns="false"
              control="flex"
              initialized="initGrid(s,e)"
              sticky-headers="true"
              selection-mode="Row"
              items-source="data"
              item-formatter="_itemFormatter"
              is-read-only="true">
        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="dlvr.membr.name"/>" binding="membrNm"
                             width="90" is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"
                             is-read-only="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvr.membr.phoneNumber"/>" binding="telNo" width="180"
                             is-read-only="true" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvr.membr.useYn"/>" binding="useYn" width="90"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvr.membr.finalInputDate"/>" binding="regDt" width="140"
                             is-read-only="true" align="right"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dlvr.membr.finalUpdateDate"/>" binding="modDt" width="140"
                             is-read-only="true" align="right"></wj-flex-grid-column>
      </wj-flex-grid>
    </div>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/dlvr.js?ver=2019052801.11"
        charset="utf-8"></script>