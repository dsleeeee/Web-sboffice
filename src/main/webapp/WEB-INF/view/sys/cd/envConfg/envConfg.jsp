<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sys/cd/envConfg/envConfg/" />

<div class="subCon">

  <div class="searchBar">
    <a href="javascript:;" class="open">${menuNm}</a>
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
        <%-- 설정코드 --%>
        <th><s:message code="envConfg.envstCd" /></th>
        <td>
          <div class="sb-select">
            <div id="srchEnvstCd"></div>
          </div>
        </td>
        <%-- 설정명 --%>
        <th><s:message code="envConfg.envstNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchEnvstNm"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 설정구분 --%>
        <th><s:message code="envConfg.envstFgNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchEnvstFg"></div>
          </div>
        </td>
        <%-- 환경그룹 --%>
        <th><s:message code="envConfg.envstGrpCdNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchEnvstGrpCd"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 대상구분 --%>
        <th><s:message code="envConfg.targtFgNm" /></th>
        <td colspan="3">
          <div class="sb-select" style="width: 40%;">
            <div id="srchTargtFg"></div>
          </div>
        </td>
      </tr>
    </tbody>
  </table>

  <%-- 조회 --%>
  <div class="mt10 pdb20 oh">
      <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search" /></button>
  </div>

  <div class="w50 fl" style="width: 60%">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr mr10 pd20" style="height: 400px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='systemCd.grpGridNm' /></span>
        <button class="btn_skyblue" id="btnAddRepresent" style="display: none;">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelRepresent" style="display: none;">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSaveRepresent" style="display: none;">
          <s:message code="cmm.save" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div id="gridRepresent" style="height:310px"></div>
    </div>
    <%--//위즈모 테이블--%>
  </div>

  <div class="w50 fr" style="width: 40%">
    <%--위즈모 테이블--%>
    <div class="wj-TblWrapBr ml10 pd20" style="height: 400px;">
      <div class="updownSet oh mb10">
        <span class="fl bk lh30"><s:message code='systemCd.gridNm' /></span>
        <button class="btn_skyblue" id="btnAddDetail" style="display: none;">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" id="btnDelDetail" style="display: none;">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" id="btnSaveDetail" style="display: none;">
          <s:message code="cmm.save" />
        </button>
      </div>
      <%-- 개발시 높이 조절해서 사용--%>
      <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
      <div id="gridDetail" style="height:310px;"></div>
    </div>
    <%--//위즈모 테이블--%>
  </div>

</div>

<script type="text/javascript">
  var envstFgNm = ${ccu.getCommCode("003")};
  var envstGrpCdNm = ${ccu.getCommCode("004")};
  var targtFg = ${ccu.getCommCode("038")};

  <%--
  var envstFgNm = ${ccu.getCommCodeExcpAll("003")};
  var envstGrpCdNm = ${ccu.getCommCodeExcpAll("004")};
  var targtFg = ${ccu.getCommCodeExcpAll("038")};
  --%>
</script>
<script type="text/javascript" src="/resource/solbipos/js/sys/cd/envConfg/envConfg.js?ver=2018082101" charset="utf-8"></script>
