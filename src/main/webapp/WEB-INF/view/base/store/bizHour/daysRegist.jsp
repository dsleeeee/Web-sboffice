<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<wj-popup id="wjDaysRegistLayer" control="wjDaysRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;">
  <div id="daysRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="daysRegistCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="bizHour.days"/> &nbsp;<s:message code="cmm.new.add"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body">
      <div class="mt10 oh sb-select dkbr">
        <p class="tl s13 mb10 ml5 fl">* <s:message code="bizHour.days.regMsg"/> </p>
      </div>
      <form id="daysForm" ng-submit="submitForm()">
        <table class="tblType01">
          <colgroup>
            <col class="w20"/>
            <col class="w30"/>
            <col class="w20"/>
            <col class="w30"/>
          </colgroup>
          <tbody>
          <tr>
            <%-- 요일 --%>
            <th><s:message code="bizHour.dayFg"/><em class="imp">*</em></th>
            <td>
              <div class="sb-select w50 fl">
                <wj-combo-box
                        id="daysRegistDayCombo"
                        ng-model="dayData.dayFg"
                        items-source="_getComboData('daysRegistDayCombo')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        control="daysRegistDayComboCtrl">
                </wj-combo-box>
              </div>
            </td>
            <c:if test="${orgnFg == 'HQ'}">
              <%-- 매장선택 --%>
              <th><s:message code="cmm.store.select"/><em class="imp">*</em></th>
              <td>
                <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                  <jsp:param name="targetTypeFg" value="S"/>
                  <jsp:param name="targetId" value="daysRegistStore"/>
                </jsp:include>
              </td>
            </c:if>
          </tr>
          <tr>
            <%-- 영업시간 --%>
            <th><s:message code="bizHour.bizTime"/><em class="imp">*</em></th>
            <td colspan="3">
              <div class="sb-select fl" style="width:72px;">
                <wj-combo-box id="daysStartHour" ng-model="dayData.startHour" items-source="_getComboData('daysStartHourCombo')" display-member-path="name" selected-value-path="value" is-editable="false" control="daysStartHourCtrl"></wj-combo-box>
              </div>
              <div class="fl pd5 s14"><label> : </label></div>
              <div class="sb-select fl" style="width:72px;">
                <wj-combo-box id="daysStartMs" ng-model="dayData.startMs" items-source="_getComboData('daysStartMsCombo')" display-member-path="name" selected-value-path="value" is-editable="false" control="daysStartMsCtrl"></wj-combo-box>
              </div>
              <div class="fl pd5 s14"><label> ~ </label></div>
              <div class="sb-select fl" style="width:72px;">
                <wj-combo-box id="daysEndHour" ng-model="dayData.endHour" items-source="_getComboData('daysEndHourCombo')" display-member-path="name" selected-value-path="value" is-editable="false" control="daysEndHourCtrl"></wj-combo-box>
              </div>
              <div class="fl pd5 s14"><label> : </label></div>
              <div class="sb-select fl" style="width:72px;">
                <wj-combo-box id="daysEndMs" ng-model="dayData.endMs" items-source="_getComboData('daysEndMsCombo')" display-member-path="name" selected-value-path="value" is-editable="false" control="daysEndMsCtrl"></wj-combo-box>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
        <div class="mt10 pdb20 oh bb">
          <button type="submit" id="btnDaysRegistSave" class="btn_blue fr"><s:message code="cmm.save"/></button>
        </div>
      </form>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/store/bizHour/daysRegist.js?ver=20261001.03" charset="utf-8"></script>
