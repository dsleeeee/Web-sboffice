<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="baseUrl" value="/sale/cmmSalePopup/billInfo/billInfo/"/>

<wj-popup id="wjBillInfoLayer" control="wjBillInfoLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="billInfoLayer" class="wj-dialog wj-dialog-columns" ng-controller="billInfoCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <span id="billSubTitle"></span>
      <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <%-- 영수증내역 --%>
      <div class="w100">
        <%-- 종합내역 --%>
        <div class="fl" style="width:25%;">
          <div class="oh sb-select mb10">
            <span class="fl bk lh30"><s:message code='billInfo.totalInfo'/></span>
          </div>
          <table class="tblType01">
            <colgroup>
              <col class="w25"/>
              <col class="w25"/>
            </colgroup>
            <tbody>
            <tr>
              <%-- 총매출액 --%>
              <th><s:message code="billInfo.saleAmt"/></th>
              <td class="tr" ng-bind-html="billInfo.totSaleAmt"></td>
            </tr>
            <tr>
              <%-- 총할인액 --%>
              <th><s:message code="billInfo.dcAmt"/></th>
              <td class="tr" ng-bind-html="billInfo.totDcAmt"></td>
            </tr>
            <tr>
              <%-- 실매출액 --%>
              <th><s:message code="billInfo.realSaleAmt"/></th>
              <td class="tr" ng-bind-html="billInfo.realSaleAmt"></td>
            </tr>
            <tr>
              <%-- 순매출액 --%>
              <th><s:message code="billInfo.netSaleAmt"/></th>
              <td class="tr" ng-bind-html="billInfo.netSaleAmt"></td>
            </tr>
            <tr>
              <%-- 면세매출액 --%>
              <th><s:message code="billInfo.noTaxSaleAmt"/></th>
              <td class="tr" ng-bind-html="billInfo.noTaxSaleAmt"></td>
            </tr>
            <tr>
              <%-- 과세매출액 --%>
              <th><s:message code="billInfo.taxSaleAmt"/></th>
              <td class="tr" ng-bind-html="billInfo.taxSaleAmt"></td>
            </tr>
            <tr>
              <%-- 부가세액 --%>
              <th><s:message code="billInfo.vatAmt"/></th>
              <td class="tr" ng-bind-html="billInfo.vatAmt"></td>
            </tr>
            <tr>
              <%-- 봉사료 --%>
              <th><s:message code="billInfo.totTipAmt"/></th>
              <td class="tr" ng-bind-html="billInfo.totTipAmt"></td>
            </tr>
            </tbody>
          </table>
        </div>

        <%-- 결제내역 --%>
        <div class="fl ml5" style="width:56%;">
          <div class="oh sb-select mb10">
            <span class="fl bk lh30"><s:message code='billInfo.payInfo'/></span>
          </div>
          <table class="tblType01">
            <colgroup>
              <col class="w25"/>
              <col class="w25"/>
              <col class="w25"/>
              <col class="w25"/>
            </colgroup>
            <tbody>
            <%-- 결제수단 생성--%>
            <c:forEach var="payCol" items="${payColList}" varStatus="status">
              <c:if test="${(status.count%2) != 0}">
                <tr>
              </c:if>
              <th>${payCol.payNm}</th>
              <td class="tr" ng-bind-html="billPayInfo.pay${payCol.payCd}"></td>
              <c:if test="${(status.count%2) == 0 || status.last}">
                <c:if test="${(status.count%2) != 0 && status.last}">
                  <th></th>
                  <td></td>
                </c:if>
                </tr>
              </c:if>
            </c:forEach>
            </tbody>
          </table>
        </div>

        <%-- 방문인원 --%>
        <div class="fr" style="width:18%;">
          <div class="oh sb-select mb10">
            <span class="fl bk lh30"><s:message code='billInfo.guestInfo'/></span>
          </div>
          <table class="tblType01">
            <colgroup>
              <col class="w40"/>
              <col class="w60"/>
            </colgroup>
            <tbody>
            <%-- 객수 생성--%>
            <c:forEach var="guestCol" items="${guestColList}">
              <tr>
                <th>${guestCol.guestNm}</th>
                <td class="tr" ng-bind-html="billGuestInfo.guest${guestCol.guestCd}"></td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
        </div>
      </div>

      <div style="clear: both; "></div>

      <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 200px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="billInfo.billDtlNo"/>" binding="billDtlNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="billInfo.prodCd"/>" binding="prodCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="billInfo.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="billInfo.orderAddFg"/>" binding="orderAddFg" width="100" align="center" is-read-only="true" data-map="orderAddFgDataMap" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="billInfo.saleQty"/>" binding="saleQty" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="billInfo.saleUprc"/>" binding="saleUprc" width="80" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="billInfo.saleAmt"/>" binding="saleAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="billInfo.dcAmt"/>" binding="dcAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="billInfo.realSaleAmt"/>" binding="realSaleAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="billInfo.gaAmt"/>" binding="gaAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="billInfo.vatAmt"/>" binding="vatAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <%-- 할인 컬럼 생성--%>
            <c:forEach var="dcCol" items="${dcColList}">
              <wj-flex-grid-column header="${dcCol.dcNm}" binding="dc${dcCol.dcCd}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </c:forEach>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
      <%-- //영수증 내역 --%>

      <%-- 원거래영수증 내역 --%>
      <div class="w100 mt20" ng-if="saleYn == 'N'" ng-controller="orgBillInfoCtrl">
        <p class="s16 bk mb5" id="orgBillSubTitle"></p>
        <div class="w100">
          <%-- 종합내역 --%>
          <div class="fl" style="width:25%;">
            <div class="oh sb-select mb10">
              <span class="fl bk lh30"><s:message code='billInfo.totalInfo'/></span>
            </div>
            <table class="tblType01">
              <colgroup>
                <col class="w25"/>
                <col class="w25"/>
              </colgroup>
              <tbody>
              <tr>
                <%-- 총매출액 --%>
                <th><s:message code="billInfo.saleAmt"/></th>
                <td class="tr" ng-bind-html="orgBillInfo.totSaleAmt"></td>
              </tr>
              <tr>
                <%-- 총할인액 --%>
                <th><s:message code="billInfo.dcAmt"/></th>
                <td class="tr" ng-bind-html="orgBillInfo.totDcAmt"></td>
              </tr>
              <tr>
                <%-- 실매출액 --%>
                <th><s:message code="billInfo.realSaleAmt"/></th>
                <td class="tr" ng-bind-html="orgBillInfo.realSaleAmt"></td>
              </tr>
              <tr>
                <%-- 순매출액 --%>
                <th><s:message code="billInfo.netSaleAmt"/></th>
                <td class="tr" ng-bind-html="orgBillInfo.netSaleAmt"></td>
              </tr>
              <tr>
                <%-- 면세매출액 --%>
                <th><s:message code="billInfo.noTaxSaleAmt"/></th>
                <td class="tr" ng-bind-html="orgBillInfo.noTaxSaleAmt"></td>
              </tr>
              <tr>
                <%-- 과세매출액 --%>
                <th><s:message code="billInfo.taxSaleAmt"/></th>
                <td class="tr" ng-bind-html="orgBillInfo.taxSaleAmt"></td>
              </tr>
              <tr>
                <%-- 부가세액 --%>
                <th><s:message code="billInfo.vatAmt"/></th>
                <td class="tr" ng-bind-html="orgBillInfo.vatAmt"></td>
              </tr>
              <tr>
                <%-- 봉사료 --%>
                <th><s:message code="billInfo.totTipAmt"/></th>
                <td class="tr" ng-bind-html="orgBillInfo.totTipAmt"></td>
              </tr>
              </tbody>
            </table>
          </div>

          <%-- 결제내역 --%>
          <div class="fl ml5" style="width:56%;">
            <div class="oh sb-select mb10">
              <span class="fl bk lh30"><s:message code='billInfo.payInfo'/></span>
            </div>
            <table class="tblType01">
              <colgroup>
                <col class="w25"/>
                <col class="w25"/>
                <col class="w25"/>
                <col class="w25"/>
              </colgroup>
              <tbody>
              <%-- 결제수단 생성--%>
              <c:forEach var="payCol" items="${payColList}" varStatus="status">
                <c:if test="${(status.count%2) != 0}">
                  <tr>
                </c:if>
                <th>${payCol.payNm}</th>
                <td class="tr" ng-bind-html="orgBillPayInfo.pay${payCol.payCd}"></td>
                <c:if test="${(status.count%2) == 0 || status.last}">
                  <c:if test="${(status.count%2) != 0 && status.last}">
                    <th></th>
                    <td></td>
                  </c:if>
                  </tr>
                </c:if>
              </c:forEach>
              </tbody>
            </table>
          </div>

          <%-- 방문인원 --%>
          <div class="fr" style="width:18%;">
            <div class="oh sb-select mb10">
              <span class="fl bk lh30"><s:message code='billInfo.guestInfo'/></span>
            </div>
            <table class="tblType01">
              <colgroup>
                <col class="w40"/>
                <col class="w60"/>
              </colgroup>
              <tbody>
              <%-- 객수 생성--%>
              <c:forEach var="guestCol" items="${guestColList}">
                <tr>
                  <th>${guestCol.guestNm}</th>
                  <td class="tr" ng-bind-html="orgBillGuestInfo.guest${guestCol.guestCd}"></td>
                </tr>
              </c:forEach>
              </tbody>
            </table>
          </div>
        </div>

        <div style="clear: both;"></div>

        <div class="w100 mt10">
          <%--위즈모 테이블--%>
          <div class="wj-gridWrap" style="height: 200px;">
            <wj-flex-grid
              autoGenerateColumns="false"
              selection-mode="Row"
              items-source="data"
              control="flex"
              initialized="initGrid(s,e)"
              is-read-only="true"
              item-formatter="_itemFormatter">

              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="billInfo.billDtlNo"/>" binding="billDtlNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="billInfo.prodCd"/>" binding="prodCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="billInfo.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="billInfo.orderAddFg"/>" binding="orderAddFg" width="100" align="center" is-read-only="true" data-map="orderAddFgDataMap" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="billInfo.saleQty"/>" binding="saleQty" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="billInfo.saleUprc"/>" binding="saleUprc" width="80" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="billInfo.saleAmt"/>" binding="saleAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="billInfo.dcAmt"/>" binding="dcAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="billInfo.realSaleAmt"/>" binding="realSaleAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="billInfo.gaAmt"/>" binding="gaAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="billInfo.vatAmt"/>" binding="vatAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
              <%-- 할인 컬럼 생성--%>
              <c:forEach var="dcCol" items="${dcColList}">
                <wj-flex-grid-column header="${dcCol.dcNm}" binding="dc${dcCol.dcCd}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              </c:forEach>

            </wj-flex-grid>
          </div>
          <%--//위즈모 테이블--%>
        </div>
      </div>
      <%-- 원거래영수증 내역 --%>
    </div>
  </div>

</wj-popup>
<script>
    var orgnCd = "${orgnCd}";
    var hqOfficeCd = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/cmmSalePopup/billInfo/billInfo.js?ver=20190130.20" charset="utf-8"></script>
