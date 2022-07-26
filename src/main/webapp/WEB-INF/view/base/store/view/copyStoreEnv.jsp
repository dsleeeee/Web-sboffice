<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="copyStoreEnvLayer" show-trigger="Click" hide-trigger="Click" style="display:none; width:600px; height:570px;">
  <div class="wj-dialog wj-dialog-columns title" ng-controller="copyStoreEnvCtrl">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="storeView.copy.store" />
      <a href="" class="wj-hide btn_close" ng-click="close()"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body" style="height:520px; overflow-y: auto;">
      <%-- 매장검색영역 --%>
      <div class="searchBar_s">
        <a href="#" class="open">환경복사 매장 선택</a><!--하단 검색테이블 열기 .open, 하단 검색테이블 닫기 .close-->
        <p class="noFolding" style="display:none;">환경복사 매장 선택</p><!-- 접기기능 사용하지 않을경우 -->
      </div>
      <div class="tblBr mb20" >
        <table class="tblType01">
          <colgroup>
            <col class="w30" />
            <col class="w70" />
          </colgroup>
          <tbody>
          <tr>
            <%-- 기준매장 --%>
            <th><s:message code="storeView.original.storeCd" /></th>
            <td>
              <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
              <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
                <jsp:param name="targetId" value="originalStore"/>
              </jsp:include>
            </td>
          </tr>
          <tr>
            <%-- 적용대상매장 --%>
            <th><s:message code="storeView.target.storeCd" /></th>
            <td>
              <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
              <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
                <jsp:param name="targetId" value="targetStore"/>
              </jsp:include>
            </td>
          </tr>
          <tr>
            <td colspan="2">
              <p class="s12 bk mt10 lh20">
                * [기준매장]의 환경설정값을 복사하여, [적용대상매장]에 적용합니다.<br />
              </p>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <%--위즈모 테이블--%>
      <div class="theGrid" style="height: 280px; overflow-y: hidden; overflow-x: hidden;">
        <wj-flex-grid
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                control="flex"
                initialized="initGrid(s,e)"
                item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeView.copy.env"/>" binding="nmcodeNm" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeView.copy.env"/>" binding="nmcodeCd" visible="false"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>

      <div class="btnSet2">
        <%-- 복사 --%>
        <span><a href="#" class="btn_blue" ng-click="copy()"><s:message code="cmm.copy" /></a></span>
        <%-- 닫기 --%>
        <span><a href="#" class="btn_gray" ng-click="close()"><s:message code="cmm.close" /></a></span>
      </div>

      <div class="mt10 oh sb-select dkbr">
        <p class="s12 bk mt10 lh20">
          1. 매장환경 : 매장공통코드, 매장환경을 복사합니다.<br />
          2. 외식환경 : 외식환경을 복사합니다.<br />
          3. 포스환경 : 포스환경을 복사합니다.<br />
          - 기준매장에 포스가 없으면 복사가 안됨<br />
          - 포스수가 '기준매장 < 적용대상매장' 일 경우 기준매장의 마지막 포스정보가 들어감<br />
          4. 판매가격 : 판매가격을 복사합니다.<br />
          - 적용대상매장에 있는 상품의 판매가격만 적용됨<br />
          5. 포스기능키 : 매장별 포스기능키, 포스별 포스기능키, 포스기능키 적용매장, 기능키 XML(왼쪽키,오른쪽키,배달메뉴키,셀프키)을 복사합니다.<br />
          - 포스수가 '기준매장 < 적용대상매장' 일 경우 기준매장의 마지막 포스정보가 들어감<br />
          6. 판매터치키 : 터치키분류, 터치키, 터치키 XML을 복사합니다.<br />
          7. 쿠폰분류 : 쿠폰분류를 복사합니다.<br />
          8. 상품권 : 상품권분류, 상품권을 복사합니다.<br />
          9. 입금/출금계정 : 입금/출금계정을 복사합니다.<br />
          10. 원산지 : 재료-원산지정보, 재료-상품맵핑정보를 복사합니다.<br />
          11. 식품 알레르기 : 재료-알레르기정보, 재료-상품맵핑정보를 복사합니다.<br />
        </p>
        <p class="s12 blue mt10 lh20">
          ※ '객층', '상품이미지', '키오스크키맵', '배달시스템 상품명칭매핑'은 해당 메뉴에서 매장복사 하실 수 있습니다.<br />
        </p>
      </div>
    </div>
    <%-- //body --%>

  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/copyStoreEnv.js?ver=20210715.01" charset="utf-8"></script>
