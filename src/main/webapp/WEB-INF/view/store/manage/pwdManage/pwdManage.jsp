<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/store/manage/pwdManage/pwdManage/"/>

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
        <%-- 본사코드 --%>
        <th><s:message code="pwdManage.hqOfficeCd" /></th>
        <td>
          <div class="sb-select">
            <div id="srchHqOfficeCd"></div>
          </div>
        </td>
        <%-- 본사명 --%>
        <th><s:message code="pwdManage.hqOfficeNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchHqOfficeNm"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 매장코드 --%>
        <th><s:message code="pwdManage.storeCd" /></th>
        <td>
          <div class="sb-select">
            <div id="srchStoreCd"></div>
          </div>
        </td>
        <%-- 매장명 --%>
        <th><s:message code="pwdManage.storeNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchStoreNm"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 사용자ID --%>
        <th><s:message code="pwdManage.userId" /></th>
        <td>
          <div class="sb-select">
            <div id="srchUserId"></div>
          </div>
        </td>
        <%-- 사용자명 --%>
        <th><s:message code="pwdManage.userNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchUserNm"></div>
          </div>
        </td>
      </tr>
    </tbody>
  </table>

  <%-- 조회 --%>
  <div class="mt10 pdb20 oh">
      <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search" /></button>
  </div>

  <div class="mt20 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <div id="listScaleBox" class="w150 fl"></div>
    <%-- 엑셀 다운로드 --%>
    <button id="btnExcel" class="btn_skyblue fr"><s:message code="cmm.excel.down" /></button>
  </div>

  <%--위즈모 테이블--%>
  <div class="wj-TblWrapBr mt10" style="height: 400px;">
    <%-- 개발시 높이 조절해서 사용--%>
    <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
    <div id="theGrid" style="width:100%;height:393px;"></div>
  </div>
  <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="page" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<%-- 비밀번호 변경 레이어 --%>
<div id="pwdModifyTent" class="fullDimmed" style="display: none;"></div>
<div id="pwdModifyLayer" class="layer" style="display: none;">
  <div class="layer_inner">
    <div class="title w350">
      <%-- 비밀번호 변경 --%>
      <p class="tit"><s:message code="pwdManage.layer.modify" /></p>
      <a href="#" class="btn_close pwdModifyClose"></a>
      <input id="pwdModifyUserId" style="display: none;" />
      <input id="pwdModifyEmpNo" style="display: none;" />
      <div class="con">
        <div>
          <table class="tblType01">
            <colgroup>
              <col class="w40" />
              <col class="w60" />
            </colgroup>
            <tbody>
              <%-- 비밀번호 구분 --%>
              <tr>
                <th><s:message code="pwdManage.layer.pwdChgFg" /></th>
                <td>
                  <div class="sb-select">
                    <div id="layerPwdChgFg"></div>
                  </div>
              </tr>
              <%-- 사용자ID --%>
              <tr>
                <th><s:message code="pwdManage.layer.userId" /></th>
                <td id="layerUserId"></td>
              </tr>
              <%-- 사용자명 --%>
              <tr>
                <th><s:message code="pwdManage.layer.userNm" /></th>
                <td id="layerUserNm"></td>
              </tr>
              <%-- 새비밀번호 --%>
              <tr>
                <th><s:message code="pwdManage.layer.newPassword" /></th>
                <td>
                    <input id="layerNewPassword" type="password" maxlength="16"/>
                    <span id="newPasswordError" class="errorMsg" style="display: none"></span>
                </td>
              </tr>
              <%-- 새비밀번호확인 --%>
              <tr>
                <th><s:message code="pwdManage.layer.confirmPassword" /></th>
                <td>
                    <input id="layerConfirmPassword" type="password" maxlength="16" />
                    <span id="confirmPasswordError" class="errorMsg" style="display: none"></span>
                </td>
              </tr>
            </tbody>
          </table>
          <p class="mt20 s12">
            <s:message code="login.layer.pwchg.policy" arguments="6,20" />
            <br>
            <br>
            <s:message code="pwdManage.layer.msg" />
          </p>
        </div>
      </div>
      <div id="viewBtnArea" class="mt30 tc">
        <%-- 변경하기 --%>
        <button class="btn_skyblue" id="btnModify"><s:message code="pwdManage.layer.modifyBtn" /></button>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
  var serviceFg = ${ccu.getCommCodeExcpAll("007")};
  var webUseYn = ${ccu.getCommCodeExcpAll("067")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/pwdManage/pwdManage.js?ver=20180813" charset="utf-8"></script>
