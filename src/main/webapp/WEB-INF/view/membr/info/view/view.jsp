<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnCd">${sessionScope.sessionInfo.orgnCd}</c:set>


<div class="subCon">
  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
  </div>

  <table class="searchTbl">
    <colgroup>
      <col class="w15" />
      <col class="w35" />
      <col class="w15" />
      <col class="w35" />
    </colgroup>
    <tbody>
      <tr>
        <%-- 검색기간 --%>
        <th>
          <div class="sb-select">
            <div id="periodDate"></div>
          </div>
        </th>
        <td>
          <div class="sb-select">
            <span class="txtIn"> <input id="startDate" name="startDate" class="w200px" /></span>
            <span class="rg">~</span>
            <span class="txtIn"> <input id="endDate" name="endDate" class="w200px" /></span>
            </span>
          </div>
        </td>
        <%-- 생일, 결혼기념일 날짜 --%>
        <th>
          <div class="sb-select">
            <div id="anvrsDate"></div>
          </div>
        </th>
        <td>
          <div class="sb-select">
            <span class="txtIn"> <input id="anvrsStartDate" name="anvrsStartDate" class="w200px" /></span>
            <span class="rg">~</span>
            <span class="txtIn"> <input id="anvrsEndDate" name="anvrsEndDate" class="w200px" /></span>
            </span>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 회원번호 --%>
        <th><s:message code="regist.membr.no"/></th>
        <td>
          <div class="sb-select">
          <div id="memberNo"></div>
          </div>
        </td>
        <%-- 회원명 --%>
        <th><s:message code="regist.membr.nm"/></th>
        <td>
          <div class="sb-select">
            <div id="memberNm"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 등록매장 --%>
        <th><s:message code="regist.reg.store.cd"/></th>
        <td>
          <div class="sb-select">
            <div id="regStore"></div>
          </div>
        </td>
        <%-- 전화번호 --%>
        <th><s:message code="regist.tel"/></th>
        <td>
          <div class="sb-select">
            <div id="telNo"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 회원카드번호 --%>
        <th><s:message code="regist.membr.card.no"/></th>
        <td>
          <div class="sb-select">
            <div id="membrCardNo"></div>
          </div>
        </td>
        <%-- E-Mail --%>
        <th>E-Mail</th>
        <td>
          <div class="sb-select">
            <div id="membrEmail"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 이메일 수신 --%>
        <th><s:message code="regist.email.recv"/></th>
        <td>
          <div class="sb-select">
            <div id="emailRecvYn"></div>
          </div>
        </td>
        <%-- SMS 수신 --%>
        <th><s:message code="regist.sms.recv"/></th>
        <td>
          <div class="sb-select">
            <div id="smsRecvYn"></div>
          </div>
        </td>

      </tr>
      <tr>
        <%-- 회원등급 --%>
        <%--
        <th><s:message code="regist.class.cd"/></th>
        <td>
          <div class="sb-select">
            <div id="classCd"></div>
          </div>
        </td>
        --%>
        <%-- 성별 --%>
        <th><s:message code="regist.gender"/></th>
        <td>
          <div class="sb-select">
            <div id="gender"></div>
          </div>
        </td>
        <th></th>
        <td></td>
      </tr>
    </tbody>
  </table>

  <%-- 조회버튼 --%>
  <div class="mt10 pdb20 oh">
    <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search"/></button>
  </div>

  <div class="wj-TblWrap mt40">
    <%-- left --%>
    <div class="w50 fl">
      <div class="wj-TblWㅣrapBr mr10 pd20" style="height:700px;">
        <div id="theGrid"></div>
      </div>
    </div>
    <%-- left --%>
    <c:import url="/WEB-INF/view/membr/info/view/base.jsp">
    </c:import>
  </div>
</div>
<script>

var recvData     = ${ccu.getCommCode("072")}; <%--수신, 미수신--%>
var recvDataEx   = ${ccu.getCommCodeExcpAll("072")}; <%--수신, 미수신--%>
var genderData   = ${ccu.getCommCode("055")}; <%--여자, 남자, 사용안함--%>
var genderDataEx = ${ccu.getCommCodeExcpAll("055")}; <%--여자, 남자, 사용안함--%>
var useDataEx    = ${ccu.getCommCodeExcpAll("067")}; <%--사용, 미사용--%>
var periodDate    = ${ccu.getCommCodeExcpAll("077")}; <%--조회기간--%>
var weddingData   = ${ccu.getCommCodeExcpAll("076")}; <%--결혼유무--%>

</script>
<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberInfo.js?ver=20181108.01" charset="utf-8"></script>
