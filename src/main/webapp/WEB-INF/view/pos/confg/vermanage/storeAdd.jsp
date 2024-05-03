<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="gvHqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<%-- 매장추가 레이어 --%>
<wj-popup control="storeAddLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:750px;height:710px;">
  <div class="wj-dialog wj-dialog-columns title">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="verManage.store.registed" />
      <span id="versionDetailTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body" >

      <%-- 탭 --%>
      <ul class="subTab">
        <%-- 버전정보 --%>
        <li><a id="storeInfo" href="#" onclick="changeVerTab()"><s:message code="verManage.verInfo" /></a></li>
        <%-- 적용매장 --%>
        <li><a id="storeEnv" href="#" class="on"><s:message code="verManage.store.registed" /></a></li>
      </ul>

      <div  ng-controller="addStoreCtrl">
        <div class="oh">
          <table class="tblType01">
            <colgroup>
              <col class="w15" />
              <col class="w35" />
              <col class="w15" />
              <col class="w35" />
            </colgroup>
            <tbody>
            <tr>
              <%-- 본사 --%>
              <th><s:message code="verManage.store.hqOffice" /></th>
              <td>
                <div class="sb-select">
                  <c:if test="${orgnFg != 'HQ'}">
                  <wj-combo-box
                          id="srchHqOffice"
                          ng-model="hqOfficeCd"
                          control="hqOfficeCombo"
                          items-source="_getComboData('hqOffice')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          selected-index-changed="setSelectedHqOffice(s)">
                  </wj-combo-box>
                  </c:if>
                <c:if test="${orgnFg == 'HQ'}">
                  <input type="text" id="srchHqOffice" ng-model="hqOfficeCd" readonly/>
                </c:if>
                </div>
              </td>
              <%-- 매장상태구분 --%>
              <th><s:message code="verManage.store.sysStatFg" /></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchSysStatFg"
                          ng-model="sysStatFg"
                          items-source="_getComboData('sysStatFg')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          selected-index-changed="setSelectedSysStatFg(s)">
                  </wj-combo-box>
                </div>
              </td>
              <%-- 본사코드 --%>
              <%--
              <th><s:message code="verManage.store.hqOfficeCd" /></th>
              <td>
                <input type="text" id="srchHqOfficeCd" class="sb-input w100" maxlength="5" ng-value=""/>
              </td>
              --%>
              <%-- 본사명 --%>
              <%--
              <th><s:message code="verManage.store.hqOfficeNm" /></th>
              <td>
                <input type="text" id="srchHqOfficeNm" class="sb-input w100" maxlength="20" ng-value=""/>
              </td>
              --%>
            </tr>
            <tr>
              <%-- 매장코드(복수 검색 가능) --%>
              <th><s:message code="verManage.store.storeCd" /></th>
              <td>
                <input type="text" id="srchStoreCd" style="width:270px;" ng-value="" oninput="setText()"/>&nbsp;
                  <input type="checkbox" id="chkMulti" />
                  <label for="chkMulti">복수검색</label>
              </td>
              <%-- 매장명 --%>
              <th><s:message code="verManage.store.storeNm" /></th>
              <td>
                <input type="text" id="srchStoreNm" ng-value=""/>
              </td>
            </tr>
            <tr>
              <%-- 주소 --%>
              <th><s:message code="verManage.store.addr" /></th>
              <td>
                <input type="text" id="srchAddr" ng-value=""/>
              </td>
              <%-- 엑셀업로드 조회 --%>
              <th><s:message code="verManage.excel.srch"/></th>
              <td>
                <%-- 양식다운로드 --%>
                <button class="btn_skyblue ml2 " ng-click="sampleDownload()"><s:message code="cmm.excel.sampleDown" /></button>
                <%-- 엑셀업로드 --%>
                <button class="btn_skyblue ml2 " ng-click="excelUpload()"><s:message code="cmm.excel.excelUpload" /></button>
              </td>
            </tr>
            </tbody>
          </table>
          <div class="mt10 tr">
            <%-- 조회 --%>
            <button id="btnSearchStore" class="btn_skyblue" onclick="search()"><s:message code="cmm.search" /></button>
          </div>
        </div>

        <%-- 등록매장 그리드 --%>
        <div class="oh mt40 w50 fl">
          <div class="wj-TblWrap mr10" style="height:405px; overflow-y: hidden;">
            <div class="oh mb10">
              <span class="fl bk lh20 s14"><s:message code="verManage.store.registed"/></span>
              <span class="fr"><a href="#" class="btn_grayS2" ng-click="delete()"><s:message code="cmm.del" /></a></span>
              <%-- 엑셀다운로드 --%>
              <button class="btn_skyblue mr5 fr" ng-click="excelDownload()">
                <s:message code="cmm.excel.down" />
              </button>
            </div>
            <div id="regProdGrid" style="height: 370px;">
              <wj-flex-grid
                      autoGenerateColumns="false"
                      control="flex"
                      initialized="initGrid(s,e)"
                      sticky-headers="true"
                      selection-mode="Row"
                      items-source="data"
                      item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="32"></wj-flex-grid-column>
                <c:if test="${orgnFg != 'HQ'}">
                  <wj-flex-grid-column header="<s:message code="verManage.store.hqOfficeCd"/>" binding="hqOfficeCd" align="center" width="55" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="verManage.store.hqOfficeNm"/>" binding="hqOfficeNm" align="left" width="60" is-read-only="true"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="verManage.store.storeCd"/>" binding="storeCd" align="center" width="65" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="verManage.store.storeNm"/>" binding="storeNm" align="left" width="60" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="verManage.store.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="40" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="verManage.store.posCnt"/>" binding="posCnt"  width="50" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="verManage.store.addr"/>" binding="addr"  width="150" align="center" is-read-only="true" ></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
          </div>
        </div>
      </div>

      <%--- 미등록매장 그리드 --%>
      <div class="oh mt40 w50 ">
        <div class=" ">
          <div class="wj-TblWrap ml10" style="height:405px; overflow-y: hidden;" ng-controller="allStoreCtrl">
            <div class="oh mb10">
              <span class="fl bk lh20 s14"><s:message code="verManage.store.noRegisted" /></span>
              <span class="fr"><a href="#" class="btn_grayS2" ng-click="save()" ><s:message code="verManage.regist.new" /></a></span>
              <%-- 엑셀다운로드 --%>
              <button class="btn_skyblue mr5 fr" ng-click="excelDownload()">
                <s:message code="cmm.excel.down" />
              </button>
            </div>
            <div id="noRegProdGrid" style="height: 370px;">
              <wj-flex-grid
                      autoGenerateColumns="false"
                      control="flex"
                      initialized="initGrid(s,e)"
                      sticky-headers="true"
                      selection-mode="Row"
                      items-source="data"
                      item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="32"></wj-flex-grid-column>
                <c:if test="${orgnFg != 'HQ'}">
                  <wj-flex-grid-column header="<s:message code="verManage.store.hqOfficeCd"/>" binding="hqOfficeCd" align="center" width="55" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="verManage.store.hqOfficeNm"/>" binding="hqOfficeNm" align="left" width="60" is-read-only="true"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="verManage.store.storeCd"/>" binding="storeCd" align="center" width="65" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="verManage.store.storeNm"/>" binding="storeNm" align="left" width="60" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="verManage.store.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="40" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="verManage.store.posCnt"/>" binding="posCnt"  width="50" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="verManage.store.diffVerCnt"/>" binding="diffVerCnt"  width="90" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="verManage.store.addr"/>" binding="addr"  width="150" align="center" is-read-only="true" ></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
          </div>
        </div>
      </div>

        <%-- 양식다운로드, 엑셀 업로드 관련 --%>
        <div style="display: none;" ng-controller="verAddStoreExcelFileUploadCtrl">
          <input type="file" class="form-control" id="excelUpFile"
                 ng-model="excelUpFile"
                 onchange="angular.element(this).scope().excelFileChanged()"
                 accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>

          <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true">

              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="media.store.storeCd"/>" binding="storeCd" width="130" align="center" data-type="String"></wj-flex-grid-column>

            </wj-flex-grid>
          </div>
        </div>

    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/pos/confg/verManage/storeAdd.js?ver=20240426.01" charset="utf-8"></script>

<script>
  $(document).ready(function(){
    $("#chkMulti").change(function(){
      if($("#chkMulti").is(":checked")){
        setText();
      }else{
      }
    });
  });

  var sysStatFgTotal = ${ccu.getCommCodeSelect("005")};
  var orgnFg = "${orgnFg}";
  var orgnCd = "${orgnCd}";
</script>
