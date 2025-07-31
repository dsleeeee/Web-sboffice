<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="dcDetailMrpizzaDcTypeView" name="dcDetailMrpizzaDcTypeView" class="subCon" style="display: none;">

     <div ng-controller="dcDetailMrpizzaDcTypeCtrl">
         <%-- 조회조건 --%>
          <div class="searchBar">
              <a href="#" class="open fl"> <s:message code="dcDetailMrpizza.dcType"/></a>
              <%-- 조회 --%>
              <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                  <button class="btn_blue fr" ng-click="_broadcast('dcDetailMrpizzaDcTypeCtrl',1)">
                      <s:message code="cmm.search"/>
                  </button>
              </div>
          </div>
     </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/mrpizza/dcDetailMrpizza/dcDetailMrpizzaDcType.js?ver=20250730.01" charset="utf-8"></script>
