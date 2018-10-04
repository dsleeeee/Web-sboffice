<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--right--%>


<div class="w50 fr" id="membrInfoArea" >
  <div class="wj-TblWrapBr ml10 pd20" style="height:700px;">
    <%-- 선택 정보 --%>
    <h3 class="h3_tbl2 lh30">
      <spam><label id="membrNoNm"><s:message code="regist.membr.info"/></label></spam>
      <%-- 버튼 --%>
      <span class="fr">
        <a href="#" class="btn_grayS" id="btnNew"><s:message code="webMenu.new"/></a>
        <a href="#" class="btn_grayS" id="btnDel"><s:message code="cmm.delete"/></a>
        <a href="#" class="btn_grayS" id="btnSave"><s:message code="cmm.save"/></a>
      </span>
    </h3>
    </br>
    <%-- tab --%>
    <ul class="subTab">
      <li><a href="#" id="btnInfo" class="on"><s:message code="regist.info"/></a></li>
      <%--<li><a href="#" id="btnCard"><s:message code="regist.membr.card"/></a></li>--%>
    </ul>
  <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
  <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>

      <%-- 회원 선택 전 --%>
      <div class="w100 fr" id="noDataArea">
        <div class="wj-TblWrapBr" style="height:550px;">
          <p class="tc s18 mt200 bk"><s:message code="regist.require.select.store" /></p>
        </div>
      </div>

      <table class="searchTbl2" id="basicInfrm" style="display:none;">
      <colgroup>
        <col class="w15" />
        <col class="w35" />
        <col class="w15" />
        <col class="w35" />
      </colgroup>
      <input type="hidden" id="vMembrOrgnCd" />
      <tbody>
        <tr>
          <%-- 회원번호 --%>
          <th><s:message code="regist.membr.no"/><em class="imp">*</em></th>
          <td colspan="3">
            <div class="sb-select w30">
              <div id="vMembrNo"></div>
            </div>
            <%--<input type="text" class="sb-input w100" id="vMembrNo" />--%>
          </td>
        </tr>
        <tr>
          <%-- 회원명 --%>
          <th><s:message code="regist.membr.nm"/><em class="imp">*</em></th>
          <td>
            <div class="sb-select">
              <div id="vMembrNm"></div>
            </div>
          </td>
            <%-- 회원명 --%>
            <th><s:message code="regist.membr.nm.eng"/><em class="imp">*</em></th>
            <td>
              <div class="sb-select">
                <div id="vMembrNmEng"></div>
              </div>
            </td>
        </tr>
        <tr>
          <%-- 등록매장 --%>
          <th><s:message code="regist.reg.store.cd"/><em class="imp">*</em></th>
          <td>
            <div class="sb-select">
              <div id="vRegStore"></div>
            </div>
          </td>
          <%-- 회원등급분류 --%>
          <th><s:message code="regist.membr.class"/><em class="imp">*</em></th>
          <td>
            <div class="sb-select">
              <div id="vClassCd"></div>
            </div>
          </td>
        </tr>
        <tr>
          <%-- 회원카드번호 --%>
          <th><s:message code="regist.membr.card.no"/></th>
          <td>
            <div class="sb-select">
              <div id="vMembrCardNo"></div>
            </div>
          </td>
          <%-- 성별 --%>
          <th><s:message code="regist.gender"/></th>
          <td>
            <div class="sb-select">
              <div id="vGender"></div>
            </div>
          </td>
        </tr>
        <tr>
          <%-- 결혼유무 --%>
          <th><s:message code="regist.wedding"/></th>
          <td>
            <div class="sb-select">
              <div id="vWedding"></div>
            </div>
          </td>
          <%-- 생일 --%>
          <th><s:message code="regist.brthd"/></th>
          <td>
            <div class="sb-select">
              <span class="txtIn">
              <div id="vBrthdDt"></div>
              </span>
              </span>
            </div>
          </td>
        </tr>
        <tr>
          <%-- 전화번호 --%>
          <th><s:message code="regist.tel"/><em class="imp">*</em></th>
          <td>
            <div class="sb-select">
              <div id="vTel"></div>
            </div>
          </td>
          <%-- 사용유무 --%>
          <th><s:message code="cmm.useYn"/><em class="imp">*</em></th>
          <td>
            <div class="sb-select">
              <div id="vUseYn"></div>
            </div>
          </td>
        </tr>
        <tr>
          <%-- E-Mail --%>
          <th>E-Mail</th>
          <td colspan="3">
            <div class="sb-select">
              <div id="vEmail"></div>
            </div>
          </td>
        </tr>
        <tr>
          <%-- 주소 --%>
          <th><s:message code="cmm.addr"/></th>
          <td colspan="3">
            <%--우편번호찾기 버튼--%>
            <a href="#" class="btn_grayS ml5 fl">
              <s:message code="cmm.zip.find"/>
            </a>
            <div class="sb-select w50">
              <div id="vAddr1"></div>
            </div>
            </br>
            <div class="sb-select">
              <div id="vAddr2"></div>
            </div>
          </td>
        </tr>
        <tr>
          <%-- 이메일 수신 --%>
          <th><s:message code="regist.email.recv"/><em class="imp">*</em></th>
          <td>
            <div class="sb-select">
              <div id="vEmailRecv"></div>
            </div>
          </td>
          <%-- SMS 수신 --%>
          <th><s:message code="regist.sms.recv"/><em class="imp">*</em></th>
          <td>
            <div class="sb-select">
              <div id="vSmsRecv"></div>
            </div>
          </td>
        </tr>
        <tr>
          <%-- 비고 --%>
          <th><s:message code="cmm.remark"/></th>
          <td colspan="3">
            <div class="sb-select">
              <div id="vRemark"></div>
            </div>
          </td>
        </tr>
        <tr id="creditStore" style="display: none;">
          <%-- 후불회원 적용매장 --%>
          <th><s:message code="regist.credit.store"/></th>
          <td colspan="3">
            <div id="storeCd" style="display: none;"></div>
            <input type="text" id="storeCdText" class="sb-input w80" readonly="readonly"/>
            <a href="#" id="store" class="btn_grayS ml5"><s:message code="cmm.store.select" /></a>
          </td>
        </tr>
      </tbody>
    </table>
  <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
  <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
  <table class="searchTbl2" id="membrCardInfo" style="display:none;">
    <colgroup>
      <col class="w15" />
      <col class="w35" />
      <col class="w15" />
      <col class="w35" />
    </colgroup>
    <tbody>
      <tr>
        <th>회원카드</th>
        <td colspan="3"><input type="text" class="sb-input w100" /></td>
      </tr>
    </tbody>
  </table>
  <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
  <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
  </div>
</div>
<%--right--%>
<script>
$(document).ready(function() {
<%--test2--%>
});
</script>
