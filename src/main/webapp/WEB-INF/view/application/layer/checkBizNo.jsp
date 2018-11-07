<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<wj-popup control="checkBizNoLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:570px;">
  <div class="wj-dialog wj-dialog-columns title">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="application.bizInfo" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body" ng-controller="checkBizNoCtrl">

      <%--- 사업자번호 사용현황 그리드 --%>
      <div class="w100">
        <div class="wj-TblWrap mr10" style="height:150px; overflow-y: hidden;">
          <div id="bizGrid" style="height: 140px; overflow-y: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="application.bizInfo.storeFg"/>" binding="storeFg" data-map="storeFgDataMap" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="application.bizInfo.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="application.bizInfo.storeNm"/>" binding="storeNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="application.bizInfo.ownerNm"/>" binding="ownerNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="application.bizInfo.clsFg"/>" binding="clsFgNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="application.bizInfo.sysStatFg"/>" binding="sysStatFgNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
          </div>
        </div>

        <%-- 페이지 리스트 --%>
        <div class="pageNum mt10">
          <%-- id --%>
          <ul id="checkBizNoCtrlPager" data-size="10">
          </ul>
        </div>
        <%--//페이지 리스트--%>
      </div>

        <div class="wj-TblWrap mr10">
          <div class="oh mb10">
            <span class="fl bk lh20 s14">수신자선택</span>
          </div>
          <table class="tblType01">
            <colgroup>
              <col class="w15" />
              <col class="w98px" />
              <col class="w15" />
              <col class="w98px" />
              <col class="w15" />
              <col class="w98px" />
            </colgroup>
            <tbody>
            <tr>
              <%-- 코드 --%>
              <th>
                <div class="impWrap"><s:message code="hqManage.bizInfo.storeCd" /></div>
              </th>
              <td id="bStoreCd" ng-model="bizInfo.storeCd"></td>
              <%-- 명칭 --%>
              <th>
                <div class="impWrap"><s:message code="hqManage.bizInfo.storeNm" /></div>
              </th>
              <td id="bStoreNm" ng-model="bizInfo.storeNm"></td>
              <%-- 상태 --%>
              <th>
                <div class="impWrap"><s:message code="hqManage.sysStatFg" /></div>
              </th>
              <td id="bSysStatFg" ng-model="bizInfo.sysStatFg"></td>
            </tr>
            <tr>
              <%-- 대표자 --%>
              <th>
                <div class="impWrap"><s:message code="hqManage.ownerNm" /></div>
              </th>
              <td id="bOwnerNm" ng-model="bizInfo.ownerNm"></td>
              <%-- 사업자번호 --%>
              <th>
                <div class="impWrap"><s:message code="hqManage.bizNo" /></div>
              </th>
              <td id="bBizNo" ng-model="bizInfo.bizNo"></td>
              <%-- 상호명 --%>
              <th>
                <div class="impWrap"><s:message code="hqManage.bizStoreNm" /></div>
              </th>
              <td id="bBizStoreNm" ng-model="bizInfo.bizStoreNm"></td>
            </tr>
            <tr>
              <%-- 지역 --%>
              <th>
                <div class="impWrap"><s:message code="hqManage.area" /></div>
              </th>
              <td id="bArea" ng-model="bizInfo.area"></td>
              <%-- 휴대폰번호 --%>
              <th>
                <div class="impWrap"><s:message code="hqManage.telNo" /></div>
              </th>
              <td id="bTelNo" ng-model="bizInfo.telNo"></td>
              <%-- 팩스번호 --%>
              <th>
                <div class="impWrap"><s:message code="hqManage.faxNo" /></div>
              </th>
              <td id="bFaxNo" ng-model="bizInfo.faxNo"></td>
            </tr>
            <tr>
              <%-- 관리업체 --%>
              <th>
                <div class="impWrap"><s:message code="hqManage.agency" /></div>
              </th>
              <td id="bAgency" ng-model="bizInfo.agency"></td>
              <%-- 용도구분 --%>
              <th>
                <div class="impWrap"><s:message code="hqManage.clsFg" /></div>
              </th>
              <td id="bClsFg" ng-model="bizInfo.clsFg"></td>
              <%-- 시스템 오픈일자 --%>
              <th>
                <div class="impWrap"><s:message code="hqManage.sysOpenDate" /></div>
              </th>
              <td id="bSysOpenDate" ng-model="bizInfo.sysOpenDate"></td>
            </tr>
            <tr>
              <%-- 주소 --%>
              <th>
                <div class="impWrap"><s:message code="hqManage.addr" /></div>
              </th>
              <td colspan="5" id="bAddr" ng-model="bizInfo.addr"></td>
            </tr>
            </tbody>
          </table>
        </div>

    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/application/layer/checkBizNo.js?ver=2018102301" charset="utf-8"></script>
