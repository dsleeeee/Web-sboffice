<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 팝업 부분 설정 - width 는 강제 해주어야함.. 해결방법? 확인 필요 : 20180829 노현수 --%>
<wj-popup control="itemSelLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:800px;">
    <div class="wj-dialog wj-dialog-columns" ng-controller="printCodeCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="kind.layer.gridNm" />
            <a href="javascript:;" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body">
            <%-- 팝업 내 버튼영역 사용시...
            <div class="updownSet oh mb10">
                <button class="btn_skyblue">
                    버튼
                </button>
            </div>
            --%>
            <div class="wj-dialog-content" style="height:393px;">
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
                    <wj-flex-grid-column header="<s:message code="item.prtCd"/>" binding="prtCd" width="100" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="item.prtNm"/>" binding="prtNm" width="100" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="item.samplYn"/>" binding="samplYn" width="60" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="item.content"/>" binding="content" width="*" is-read-only="true"
                                         multi-line="true"
                                         word-wrap="true"
                                         css-class="wj-grid-multi-editor"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
        <div class="wj-dialog-footer">
            <button class="btn wj-hide-apply btn_blue"><s:message code="cmm.chk" /></button>
        </div>
    </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/sys/bill/kind/popUpKind.js?ver=2018091401" charset="utf-8"></script>
