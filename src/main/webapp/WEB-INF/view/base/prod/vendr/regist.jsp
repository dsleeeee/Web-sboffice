<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<%-- 우편번호 찾기 팝업 --%>
<%-- 선택한 주소를 부모창에 바인딩 하기 위해, 각 화면마다 구분자를 지정하여 element id명을 파악한다. --%>
<%-- jsp:param 방식은 API 호출 시, 파라미터 사용을 불허하기 때문에 호출이 거부됨. --%>
<input type="hidden" id="pageNm" value="vendrRegist" />
<%@ include file="/WEB-INF/view/application/layer/searchAddr.jsp" %>

<wj-popup id="wjVendrRegistLayer" control="wjVendrRegistLayer"  show-trigger="Click" hide-trigger="Click" style="display: none;width:800px;">
  <div id="vendrRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="vendrRegistCtrl">
    <div class="layer_inner">
      <div class="title w800px">
        <%-- 타이틀 --%>
        <p class="tit" id="popTitleReg" style="display: none;"><s:message code='vendr.layer.regist.title' /></p>
        <p class="tit" id="popTitleMod" style="display: none;"><s:message code='vendr.layer.modify.title' /></p>
        <a href="#" class="btn_close" ng-click="close()"></a>
        <div class="con">
          <%-- 거래처등록, 취급상품 탭 --%>
          <div class="tabType1">
            <ul>
              <%-- 거래처등록 탭 --%>
              <li><a href="#" id="vendrTab" class="on" ng-click="changeTab('1')"><s:message code="vendr.regst" /></a></li>
              <%-- 취급상품 탭 --%>
              <li><a href="#" id="trtMntTab" ng-click="changeTab('2')"><s:message code="vendr.trtMnt" /></a></li>
            </ul>
          </div>

            <table class="tblType01">
              <colgroup>
                <col class="w15" />
                <col class="w35" />
                <col class="w15" />
                <col class="w35" />
              </colgroup>
              <tbody>
              <tr class="brt">
                <%-- 거래처코드 --%>
                <th><s:message code="vendr.vendrCd" /> <em class="imp">*</em></th>
                <td><input type="text" class="sb-input w100" id="rVendrCd" ng-model="rVendrCd" readonly/></td>
                <%-- 거래처명 --%>
                <th><s:message code="vendr.vendrNm" /> <em class="imp">*</em></th>
                <td><input type="text" class="sb-input w100" id="rVendrNm" ng-model="rVendrNm" maxlength="50"/></td>
              </tr>
              <tr>
                <%-- 대표자명 --%>
                <th><s:message code="vendr.ownerNm" /> <em class="imp">*</em></th>
                <td><input type="text" class="sb-input w100" id="rOwnerNm" ng-model="rOwnerNm" maxlength="50"/></td>
                <%-- 거래처구분 --%>
                <th><s:message code="vendr.vendorFg" /> <em class="imp">*</em></th>
                <td>
                  <div class="sb-select">
                    <wj-combo-box
                            id="rVendorFg"
                            ng-model="vendorFg"
                            items-source="_getComboData('rVendorFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="vendorFgCombo">
                    </wj-combo-box>
                  </div>
                </td>
              </tr>
              <tr>
                <%-- 부가세 포함여부 --%>
                <th><s:message code="vendr.vatIncldYn" /> <em class="imp">*</em></th>
                <td>
                  <div class="sb-select">
                    <wj-combo-box
                            id="rVatIncldYn"
                            ng-model="vatIncldYn"
                            items-source="_getComboData('rVatIncldYn')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="vatIncldYnCombo">
                    </wj-combo-box>
                  </div>
                </td>
                <%-- 사용여부 --%>
                <th><s:message code="vendr.useYn" /></th>
                <td>
                  <div class="sb-select">
                    <wj-combo-box
                            id="rUseYn"
                            ng-model="useYn"
                            items-source="_getComboData('rUseYn')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="useYnCombo">
                    </wj-combo-box>
                  </div>
                </td>
              </tr>
              <tr>
                <%-- 사업자번호 --%>
                <th><s:message code="vendr.bizNo" /></th>
                <td>
                  <input type="text" id="rBizNo1" maxlength="3" style="width:50px;" maxlength="3"/>
                  <input type="text" id="rBizNo2" maxlength="2" style="width:40px;" maxlength="2"/>
                  <input type="text" id="rBizNo3" maxlength="5" style="width:110px;" maxlength="5"/>
                </td>
                <th><s:message code="vendr.telNo" /></th>
                <td><input type="text" class="sb-input w100" id="rTelNo" ng-model="rTelNo" maxlength="15"/></td>
              </tr>
              <tr>
                <th><s:message code="vendr.emailAddr" /></th>
                <td><input type="text" class="sb-input w100" id="rEmailAddr" ng-model="rEmailAddr" maxlength="200"/></td>
                <th><s:message code="vendr.faxNo" /></th>
                <td><input type="text" class="sb-input w100" id="rFaxNo" ng-model="rFaxNo" maxlength="15"/></td>
              </tr>
              <tr>
                <th><s:message code="vendr.addr" /></th>
                <td colspan="3">
                  <input type="text" id="rPostNo" ng-model="rPostNo" class="sb-input" placeholder="우편번호" maxlength="5" style="width: 80px;" readonly/>
                  <a id="btnSrchAddr" href="#" class="btn_grayS ml5" onclick="searchAddr()">
                    <s:message code="storeManage.srchAddr" />
                  </a>
                  <input type="text" id="rAddr" ng-model="rAddr" class="sb-input w100" placeholder="주소1" maxlength="100" style="margin:4px 0px;" readonly/>
                  <input type="text" id="rAddrDtl" ng-model="rAddrDtl" class="sb-input w100" placeholder="주소2" maxlength="100"/>
                </td>
              </tr>
              <tr>
                <th><s:message code="vendr.remark" /></th>
                <td colspan="3">
                  <input type="text" class="sb-input w100" id="rRemark" ng-model="rRemark" maxlength="500"/>
                </td>
              </tr>
              </tbody>
            </table>
        </div>

        <%-- 공통 버튼 영역 --%>
        <div class="btnSet">
          <%-- 신규등록 --%>
          <span><a href="#" class="btn_blue" id="btnReg" style="display:none;" ng-click="regVendr()"><s:message code="cmm.new.add" /></a></span>
          <%-- 저장 --%>
          <span><a href="#" class="btn_blue" id="btnMod" style="display:none;" ng-click="modVendr()"><s:message code="cmm.save" /></a></span>
          <%-- 닫기 --%>
          <span><a href="#" class="btn_gray" id="btnClose" ng-click="close()"><s:message code="cmm.close" /></a></span>
        </div>

      </div>
    </div>
  </div>
</wj-popup>

<script>
  var vendorFgData     = ${ccu.getCommCodeExcpAll("011")};
  <%--var vatIncldYnData   = ${ccu.getCommCodeExcpAll("067")};--%>
  var useYnData        = ${ccu.getCommCodeExcpAll("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/vendr/regist.js?ver=20200507.02" charset="utf-8"></script>
