<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<wj-popup control="wjProductErrInfoLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="productErrInfoCtrl">

        <div class="wj-dialog-header wj-dialog-header-font">
           <s:message code="product.upload.fail.list"/>
           <a href="#" class="wj-hide btn_close"></a>
       </div>

       <div class="wj-dialog-body sc2">
           <div class="w100 mt10 mb20">
               <%--위즈모 테이블--%>
               <div class="wj-gridWrap" style="height: 390px; overflow-y: hidden; overflow-x: hidden;">
                   <wj-flex-grid
                       autoGenerateColumns="false"
                       selection-mode="Row"
                       items-source="data"
                       control="flex"
                       initialized="initGrid(s,e)"
                       is-read-only="true"
                       item-formatter="_itemFormatter">

                       <!-- define columns -->
                       <wj-flex-grid-column header="<s:message code="product.prodStatus"/>" binding="prodStatus" width="90" align="center" data-map="prodStatusMap"></wj-flex-grid-column>
                       <wj-flex-grid-column header="<s:message code="product.barCd"/>" binding="prodWtUprc" width="200" align="center" data-map="prodStatusMap"></wj-flex-grid-column>

                   </wj-flex-grid>
               </div>
               <%--//위즈모 테이블--%>
           </div>
       </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/stock/product/product/productErrInfo.js?ver=20220706.01" charset="utf-8"></script>