<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<wj-popup control="prodStoreRegistLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:920px;">
  <div class="wj-dialog wj-dialog-columns title">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="prod.regStore" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">
      <table class="tblType01">
        <colgroup>
          <col class="w15" />
          <col class="w35" />
          <col class="w15" />
          <col class="w35" />
        </colgroup>
        <tbody>
        <tr>
          <th><s:message code="prod.storeCd"/></th>
          <td><input type="text" id="srchStoreCd" ng-model="storeCd" /></td>
          <th><s:message code="prod.storeNm"/></th>
          <td><input type="text" id="srchStoreNm" ng-model="storeNm" /></td>
        </tr>
        </tbody>
      </table>
      <%-- 조회 --%>
      <div class="mt10 tr">
        <button class="btn_skyblue" id="btnSearch" ng-click="_broadcast('regStoreCtrl')" ><s:message code="cmm.search" /></button>
      </div>
      <div class="oh mt20">
        <%--- 적용매장 그리드 --%>
        <div class="w50 fl">
          <div class="wj-TblWrap mr10" style="height:395px; overflow-y:hidden;" ng-controller="regStoreCtrl">
            <div class="oh">
              <span class="fl bk lh20 s14"><s:message code="prod.regStore"/></span>
              <span class="fr"><a href="#" class="btn_grayS2" ng-click="delete()"><s:message code="cmm.del" /></a></span>
            </div>
            <div id="regStoreGrid" class="mt10" style="height: 355px; overflow-y: hidden;">
              <wj-flex-grid
                      autoGenerateColumns="false"
                      control="flex"
                      initialized="initGrid(s,e)"
                      sticky-headers="true"
                      selection-mode="Row"
                      items-source="data"
                      item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.storeCd"/>" binding="storeCd" width="*" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.storeNm"/>" binding="storeNm" width="*" is-read-only="true" ></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
          </div>

          <%-- 페이지 리스트 --%>
          <div class="pageNum mt10">
            <%-- id --%>
            <ul id="regStoreCtrlPager" data-size="10">
            </ul>
          </div>
          <%--//페이지 리스트--%>


        </div>

        <%--- 미적용매장 그리드 --%>
        <div class="w50 fr">
          <div class="wj-TblWrap ml10" style="height:395px; overflow-y: hidden;" ng-controller="noRegStoreCtrl">
            <div class="oh">
              <span class="fl bk lh20 s14"><s:message code="prod.noRegStore"/></span>
              <span class="fr"><a href="#" class="btn_grayS2" ng-click="regist()" ><s:message code="prod.regist"/></a></span>
            </div>

            <div id="noRegStoreGrid" class="mt10" style="height: 355px; overflow-y: hidden;">
              <wj-flex-grid
                      autoGenerateColumns="false"
                      control="flex"
                      initialized="initGrid(s,e)"
                      sticky-headers="true"
                      selection-mode="Row"
                      items-source="data"
                      item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.storeCd"/>" binding="storeCd" width="*" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prod.storeNm"/>" binding="storeNm" width="*" is-read-only="true" ></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
          </div>

          <%-- 페이지 리스트 --%>
          <div class="pageNum mt10">
            <%-- id --%>
            <ul id="noRegStoreCtrlPager" data-size="10">
            </ul>
          </div>
          <%--//페이지 리스트--%>

        </div>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/prodStoreRegist.js?ver=20181204.01" charset="utf-8"></script>
