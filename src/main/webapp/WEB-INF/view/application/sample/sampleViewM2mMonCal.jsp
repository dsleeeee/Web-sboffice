<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="taxBillCtrl">

  <%-- 조회조건 --%>
  <div class="searchBar flddUnfld">
    <%-- <a href="#" class="open fl">${menuNm}</a> --%>
    <a href="#" class="open fl">월별판매분석</a>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w30" />
      <col class="w70" />
    </colgroup>
    <tbody>
    <tr>
      <%-- 조회월 --%>
      <th>조회월<!-- s:message code="" / --></th>
      <td>
        <div class="sb-select">
            <span class="txtIn w110px">
              <wj-input-date
                      value="startDate"
                      ng-model="startDate"
                      control="startDateCombo"
                      selection-mode="Month"
                      format="yyyy/M"   
                      initialized="_initDateBox(s)">
              </wj-input-date>
            </span>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 조회조건 --%>
      <th>조회조건<!-- s:message code="" / --></th>
      <td>
        <div class="sb-select txtIn w110px">
          <wj-combo-box
                  id="srchStatusFgCombo"
                  ng-model="statusFg"
                  items-source="_getComboData('srchStatusFgCombo')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
          </wj-combo-box>
        </div>
        <span class="txtIn chk ml10">
		  <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
		  <label for="chkDt">
		    <s:message code="cmm.all.day" />
		  </label>		  
		</span>
      </td>
    </tr>
    </tbody>
  </table>
  
    <%-- 조회 --%>
    <div class="fr mt10" style="position: relative">
      <button class="btn_blue fr" ng-click="_broadcast('taxBillCtrl')">
        <s:message code="cmm.search" />
      </button>
    </div>
    
    <div class="clearfix"></div>
    
    <!-- 월별판매분석 table start -->
	<div class="tbl-d type2">
	  <div class="tbl-tit-btn">
		  <div class="txtIn bk lh30">
		  	<span class="bk">2018</span>년 
		  	<span class="bk">1</span>월
		  </div>
		  <%-- 엑셀다운로드 //TODO--%>
		  <span class="fr">
		  	<button class="btn_skyblue" id="btnExcel"><s:message code="cmm.excel.down" /></button>
		  </span>
	  </div>
	  
	  <table class="tbl-cal">
	    <colgroup>
	      <col class="w11"/>
	      <col class="w11"/>
	      <col class="w11"/>
	      <col class="w11"/>
	      <col class="w11"/>
	      <col class="w11"/>
	      <col class="w11"/>
	      <col class="w11"/>
	      <col class="w11"/>	      	      	      	      	      	      	      
	    </colgroup>
	    <thead>
	    <tr>
	      <th class="red">일</th>
	      <th>월</th>
	      <th>화</th>
	      <th>수</th>
	      <th>목</th>
	      <th>금</th>
	      <th class="blue">토</th>
	      <th colspan="2">판매분석</th>
	    </tr>
	    </thead>
	    <tbody>
	    
	    <!-- date start -->
	    <tr class="cal-date">
	      <td></td>
	      <td class="red">1</td>
	      <td>2</td>
	      <td>3</td>
	      <td>4</td>
	      <td>5</td>
	      <td class="blue">6</td>
	      <td class="bg-total">주간합계</td>
	      <td class="bg-total">비율</td>
	    </tr>
	    <tr class="cal-txt">
	      <td>10,000원</td>
	      <td>10,000원</td>
	      <td>10,000원</td>
	      <td>10,000원</td>
	      <td>10,000원</td>
	      <td>10,000원</td>
	      <td>10,000원</td>
	      <td>60,000원</td>
	      <td class="tc">19.35%</td>                        
	    </tr>
	    <tr class="cal-date">
	      <td class="red">7</td>
	      <td>7</td>
	      <td>9</td>
	      <td>10</td>
	      <td>11</td>
	      <td>12</td>
	      <td class="blue">13</td>
	      <td class="bg-total">주간합계</td>
	      <td class="bg-total">비율</td>
	    </tr>
	    <tr class="cal-txt">
	      <td>10,000원</td>
	      <td>10,000원</td>
	      <td>10,000원</td>
	      <td>10,000원</td>
	      <td>10,000원</td>
	      <td>10,000원</td>
	      <td>10,000원</td>
	      <td>70,000원</td>
	      <td class="tc">22.58%</td>                        
	    </tr>	    
		<!-- //date end -->
		
	    <!-- day total start -->
	    <tr class="bg-total">
	      <td class="red">일요일합계</td>
	      <td>월요일합계</td>
	      <td>화요일합계</td>
	      <td>수요일합계</td>
	      <td>목요일합계</td>
	      <td>금요일합계</td>
	      <td class="blue">토요일합계</td>
	      <td colspan="2">총매출합계</td>
	    </tr>
	    <tr class="cal-txt">
	      <td>40,000원</td>
	      <td>50,000원</td>
	      <td>50,000원</td>
	      <td>50,000원</td>
	      <td>40,000원</td>
	      <td>40,000원</td>
	      <td>40,000원</td>
	      <td rowspan="3" colspan="2" class="tr red s20">310,000원</td>                        
	    </tr>
	    <tr class="bg-total">
	      <td>비율</td>
	      <td>비율</td>
	      <td>비율</td>
	      <td>비율</td>
	      <td>비율</td>
	      <td>비율</td>
	      <td class="blue">비율</td>
	    </tr>
	    <tr class="cal-txt">
	      <td>12.90%</td>
	      <td>16.13%</td>
	      <td>16.13%</td>
	      <td>16.13%</td>
	      <td>12.90%</td>
	      <td>12.90%</td>
	      <td>12.90%</td>                    
	    </tr>	    
		<!-- //day total end -->
	
	    </tbody>
	  </table>
	</div>
	<!-- // 월별판매분석 table end -->


</div>

<script>
  var statusDataFg  = ${ccu.getCommCode("100")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/membr/anals/taxBill/taxBill.js?ver=20181217.02" charset="utf-8"></script>


