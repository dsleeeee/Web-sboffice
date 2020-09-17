<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<wj-popup id="wjSpeDateRegistLayer" control="wjSpeDateRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="speDateRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="speDateRegistCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="outstockReqDate.specificDate"/> &nbsp;<s:message code="cmm.new.add"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body">
      <form id="speDateForm" ng-submit="submitForm()">
        <table class="tblType01">
          <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
          </colgroup>
          <tbody>
          <tr>
            <th><s:message code="outstockReqDate.specificDate"/><em class="imp">*</em></th>
            <td>
              <div class="sb-select">
                <span class="txtIn"><input id="specificDate" class="w200px" ng-model="speDate.specificDate"></span>
              </div>
            </td>
            <th><s:message code="outstockReqDate.store"/><em class="imp">*</em></th>
            <td>
              <%-- 매장선택 모듈 싱글 선택 사용시 include
                   param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                closeFunc - 팝업 닫기시 호출할 함수
              --%>
              <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreS.jsp" flush="true">
                <jsp:param name="targetId" value="speDateRegistStore"/>
              </jsp:include>
              <%--// 매장선택 모듈 싱글 선택 사용시 include --%>
            </td>
          </tr>
          <tr>
            <th><s:message code="outstockReqDate.outstockReqYn"/></th>
            <td>
              <select id="outstockReqYn" ng-model="speDate.outstockReqYn">
                <option value="N"><s:message code="outstockReqDate.outstockReqYnN"/></option>
                <option value="Y"><s:message code="outstockReqDate.outstockReqYnY"/></option>
              </select>
            </td>
          </tr>
          <tr>
            <th><s:message code="outstockReqDate.specificDateRemark"/><em class="imp">*</em></th>
            <td colspan="3">
              <div>
                <textarea id="specificDateRemark" class="w100 tArea1" style="height:100px;" ng-model="speDate.specificDateRemark" maxlength="100"></textarea>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
        <div class="mt10 pdb20 oh bb">
          <button type="submit" id="btnSave" class="btn_blue fr"><s:message code="cmm.save"/></button>
        </div>
      </form>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">

</script>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/outstockReqDate/specificDateRegist.js?ver=20181224.05" charset="utf-8"></script>
