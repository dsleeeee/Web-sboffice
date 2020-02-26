<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ufn" uri="solbipos/function" %>

<style>
/*  grid 영역 임시 탭 */
.tab {padding:20px 20px 0 20px; border-bottom:1px solid #ddd; overflow: hidden;}
.tab button {background-color: inherit; float: left;cursor: pointer;padding: 14px 16px;transition: 0.3s; font-size: 17px}
.tab button:hover {background-color: #ddd}
.tab button.active {background-color: #ccc}
.tabcontent {display: none}
</style>

<div class="tab">
  <button class="tablinks active" onclick="tabCol(event, 'col-1')">가로 1/2단 col</button>
  <button class="tablinks" onclick="tabCol(event, 'col-2')">가로 3/4단 col</button>
  <button class="tablinks" onclick="tabCol(event, 'col-3')">세로 2단 이상</button>
  <button class="tablinks" onclick="tabCol(event, 'col-4')">좌측그리드 2단 이상</button>
  <button class="tablinks" onclick="tabCol(event, 'col-5')">우측그리드 2단 이상</button>
  <button class="tablinks" onclick="tabCol(event, 'col-6')">좌우그리드 detail</button>    
</div>

<!-- grid 영역 임시 탭 1 : 개발시 불필요 Start -->
<div id="col-1" class="tabcontent" style="display:block">

<!-- contents start -->
<div class="subCon" ng-controller="">
	
	<p>1단 col</p>
	
	<%-- wj grid start --%>
  <div class="mt40 oh sb-select dkbr">
  	<span>페이지 스케일 영역</span>
    <%-- 페이지 스케일  --%>
    <wj-combo-box
            class="w100px fl"
            id="listScaleBox"
            ng-model="listScale"
            items-source="_getComboData('listScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="initComboBox(s)">
    </wj-combo-box>
    <%--// 페이지 스케일  --%>
	  
	  <span class="fr">
	    <%-- 신규매장등록 --%>
	    <button class="btn_skyblue ml5" id="btnAddRepresent" ng-click="addStore()">
	      <s:message code="storeManage.regist.new.store" />
	    </button>
	  	<%-- 엑셀다운로드 //TODO--%>
	  	<button class="btn_skyblue" id="btnExcel"><s:message code="cmm.excel.down" /></button>	    
	  </span>    
	  <div class="clearfix"></div>
  </div>
  
	<div class="wj-gridWrap mt10">
		<div style="height:50px">grid 영역</div>
	</div>
	
	<%-- //wj grid end --%>	

</div>
<!-- //contents end -->

<!-- contents start -->
<div class="subCon" ng-controller="">

	<p class="mt30 mb10">2단 col 좌측: 25% / 우측: 75%</p>
	
	<%-- wj grid start --%>	
	<div class="wj-TblWrap">
		<%-- left --%>
		<div class="w25 fl">
			<div class="wj-TblWrapBr mr10 pd20">
				<div style="height:50px">grid 영역</div>
			</div>
		</div>
	
		<%-- right --%>
		<div class="w75 fr">
			<div class="wj-TblWrapBr ml10 pd20">
				<div style="height:50px">grid 영역</div>
			</div>
		</div>	
		
		<div class="clearfix"></div>
	</div>
	<%-- //wj grid end --%>
	
</div>
<!-- //contents end -->


<!-- contents start -->
<div class="subCon" ng-controller="">
	
	<p class="mt30 mb10">2단 col 좌측: 40% / 우측: 60%</p>
	
	<%-- wj grid start --%>	
	<div class="wj-TblWrap">
		<%-- left --%>
		<div class="w40 fl">
			<div class="wj-TblWrapBr mr10 pd20">
				<div style="height:50px">grid 영역</div>
			</div>
		</div>
	
		<%-- right --%>
		<div class="w60 fr">
			<div class="wj-TblWrapBr ml10 pd20">
				<div style="height:50px">grid 영역</div>
			</div>
		</div>	
		
		<div class="clearfix"></div>
	</div>
	<%-- //wj grid end --%>	

</div>
<!-- //contents end -->


<!-- contents start -->
<div class="subCon" ng-controller="">

	<p class="mt35 mb10">2단 col 좌측: 50% / 우측: 50%</p>
	
	<%-- wj grid start --%>
	<div class="wj-TblWrap">
		<%-- left --%>
		<div class="w50 fl">
			<div class="wj-TblWrapBr mr10 pd20">
			
				<%-- title/button area start --%>
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.authGroup"/></span>
          <button id="btnAdd" class="btn_skyblue"><s:message code="cmm.add"/></button>
          <button id="btnDel" class="btn_skyblue"><s:message code="cmm.del"/></button>
          <button id="btnSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
        
				<div style="height:50px">grid 영역</div>        			
			</div>
		</div>
	
		<%-- right --%>
		<div class="w50 fr">
			<div class="wj-TblWrapBr ml10 pd20">
			
				<%-- title/button area start --%>
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
        
        <div style="height:50px">grid 영역</div>				
			</div>
		</div>	
		
		<div class="clearfix"></div>
	</div>
	<%-- //wj grid end --%>	

</div>
<!-- //contents end -->


<!-- contents start -->
<div class="subCon" ng-controller="">

	<p class="mt35 mb10">2단 col 좌측: 60% / 우측: 40%</p>
	
	<%-- wj grid start --%>	
	<div class="wj-TblWrap">
		<%-- left --%>
		<div class="w60 fl">
			<div class="wj-TblWrapBr mr10 pd20">
				<div style="height:50px">grid 영역</div>
			</div>
		</div>
	
		<%-- right --%>
		<div class="w40 fr">
			<div class="wj-TblWrapBr ml10 pd20">
				<div style="height:50px">grid 영역</div>
			</div>
		</div>	
		
		<div class="clearfix"></div>
	</div>
	<%-- //wj grid end --%>			

</div>
<!-- //contents end -->


<!-- contents start -->
<div class="subCon" ng-controller="">
	
	<p class="mt35 mb10">2단 col 좌측: 75% / 우측: 25%</p>
	
	<%-- wj grid start --%>	
	<div class="wj-TblWrap">
		<%-- left --%>
		<div class="w75 fl">
			<div class="wj-TblWrapBr mr10 pd20">
				<div style="height:50px">grid 영역</div>
			</div>
		</div>
	
		<%-- right --%>
		<div class="w25 fr">
			<div class="wj-TblWrapBr ml10 pd20">
				<div style="height:50px">grid 영역</div>
			</div>
		</div>	
		
		<div class="clearfix"></div>
	</div>
	<%-- //wj grid end --%>		
	
</div>
<!-- //contents end -->

</div>
<!-- //grid 영역 임시 탭  1: 개발시 불필요 end -->


<!-- grid 영역 임시 탭 2 : 개발시 불필요 Start -->
<div id="col-2" class="tabcontent">

<!-- contents start -->
<div class="subCon" ng-controller="">
	
	<p class="mt35 mb10">3단 col grid 영역 별 넓이: 33%</p>
	
	<%-- wj grid start --%>	
	<div class="wj-TblWrap">
		<%-- left --%>
		<div class="w33 fl">
			<div class="wj-TblWrapBr mr10 pd20">
				<div style="height:50px">grid 영역</div>
			</div>
		</div>

		<%-- center --%>
		<div class="w33 fl">
			<div class="wj-TblWrapBr mr10 pd20">
				<div style="height:50px">grid 영역</div>
			</div>
		</div>
		
		<%-- right --%>
		<div class="w33 fr">
			<div class="wj-TblWrapBr pd20">
				<div style="height:50px">grid 영역</div>
			</div>
		</div>	
		
		<div class="clearfix"></div>
	</div>
	<%-- //wj grid end --%>		
	
</div>
<!-- //contents end -->


<!-- contents start -->
<div class="subCon" ng-controller="">
	
	<p class="mt35 mb10">4단 col grid 영역 별 넓이: 25%</p>
	
	<%-- wj grid start --%>	
	<div class="wj-TblWrap">
		<%-- left 1--%>
		<div class="w25 fl">
			<div class="wj-TblWrapBr mr10 pd20">
				<div style="height:50px">grid 영역</div>
			</div>
		</div>

		<%-- left 2 --%>
		<div class="w25 fl">
			<div class="wj-TblWrapBr mr10 pd20">
				<div style="height:50px">grid 영역</div>
			</div>
		</div>
		
		<%-- right 1 --%>
		<div class="w25 fl">
			<div class="wj-TblWrapBr mr10 pd20">
				<div style="height:50px">grid 영역</div>
			</div>
		</div>
		
		<%-- right 2 --%>
		<div class="w25 fr">
			<div class="wj-TblWrapBr pd20">
				<div style="height:50px">grid 영역</div>
			</div>
		</div>	
		
		<div class="clearfix"></div>
	</div>
	<%-- //wj grid end --%>		
	
</div>
<!-- //contents end -->

<!-- contents start -->
<div class="subCon" ng-controller="">
	
	<p class="mt35 mb10">
		4단 col 가변<br/>
		width:1024px 이하  grid 영역 별 넓이: 50%<br/><br/>
		width:1024px 이상  grid 영역 별 넓이: 25%
	</p>
	<%-- wj grid start --%>	
	<div class="wj-TblWrap">
		<%-- left 1--%>
		<div class="w-r">
			<div class="wj-TblWrapBr">
				<div style="height:50px">grid 영역</div>
			</div>
		</div>

		<%-- left 2 --%>
		<div class="w-r">
			<div class="wj-TblWrapBr">
				<div style="height:50px">grid 영역</div>
			</div>
		</div>
		
		<%-- right 1 --%>
		<div class="w-r">
			<div class="wj-TblWrapBr">
				<div style="height:50px">grid 영역</div>
			</div>
		</div>
		
		<%-- right 2 --%>
		<div class="w-r">
			<div class="wj-TblWrapBr">
				<div style="height:50px">grid 영역</div>
			</div>
		</div>	
		
		<div class="clearfix"></div>
	</div>
	<%-- //wj grid end --%>		
	
</div>
<!-- //contents end -->

</div>
<!-- //grid 영역 임시 탭 2 : 개발시 불필요 end -->


<!-- grid 영역 임시 탭 3 : 개발시 불필요 Start -->
<div id="col-3" class="tabcontent">

<!-- contents start -->
<div class="subCon" ng-controller="">

	<%-- 1 col 2개 이상 start --%>
	<p>1단 2개 이상</p>
	
	<%-- gird 1 --%>
	<h2 class="h2_tit mt40"><s:message code="myInfo.title2" /></h2>
	
	<%-- title/button area start --%>
  <div class="updownSet oh">
    <span class="fl bk lh30"><s:message code="myInfo.storeType.title" /></span>
    <div class="txtIn">
      <button id="storeTypeAddRowBtn" name="addRowBtn" class="btn_skyblue"><s:message code="cmm.add" /></button>
      <button id="storeTypeDelRowBtn" name="delRowBtn" class="btn_skyblue"><s:message code="cmm.delete" /></button>
      <button id="storeTypeSaveBtn" name="saveBtn" class="btn_skyblue"><s:message code="cmm.save" /></button>
    </div>
  </div>
  <%-- //title/button area end --%>
  
	<div class="wj-gridWrap mt10">
		<div style="height:50px">grid 영역</div>
	</div>
	
	<%-- gird 2 --%>
	<%-- title/button area start --%>
  <div class="updownSet oh mt40">
    <span class="fl bk lh30"><s:message code="myInfo.grp.title" /></span>
    <div class="txtIn">
      <button id="storeTypeAddRowBtn" name="addRowBtn" class="btn_skyblue"><s:message code="cmm.add" /></button>
      <button id="storeTypeDelRowBtn" name="delRowBtn" class="btn_skyblue"><s:message code="cmm.delete" /></button>
      <button id="storeTypeSaveBtn" name="saveBtn" class="btn_skyblue"><s:message code="cmm.save" /></button>
    </div>
  </div>
  <%-- //title/button area end --%>
  
	<div class="wj-gridWrap mt10">
		<div style="height:50px">grid 영역</div>
	</div>	
	
  <%-- //1col 2개 이상 end --%>

	<%-- //wj grid end --%>	

</div>
<!-- //contents end -->

<!-- contents start -->
<div class="subCon" ng-controller="">
	
	<p>상단 1단 하단 2단</p>
	
	<%-- wj grid start --%>
	<%-- 1col start --%>
  <div class="oh sb-select dkbr">
  	<span>페이지 스케일 영역</span>
    <%-- 페이지 스케일  --%>
    <wj-combo-box
            class="w100px fl"
            id="listScaleBox"
            ng-model="listScale"
            items-source="_getComboData('listScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="initComboBox(s)">
    </wj-combo-box>
    <%--// 페이지 스케일  --%>
	  
	  <span class="fr">
	    <%-- 신규매장등록 --%>
	    <button class="btn_skyblue ml5" id="btnAddRepresent" ng-click="addStore()">
	      <s:message code="storeManage.regist.new.store" />
	    </button>
	  	<%-- 엑셀다운로드 //TODO--%>
	  	<button class="btn_skyblue" id="btnExcel"><s:message code="cmm.excel.down" /></button>	    
	  </span>    
	  <div class="clearfix"></div>
  </div>
  
	<div class="wj-gridWrap mt10">
		<div style="height:50px">grid 영역</div>
	</div>
	
	<%-- //1col end --%>
	
	<%-- 2col start --%>
	<div class="wj-TblWrap mt30">
		<%-- left --%>
		<div class="w50 fl">
			<div class="wj-TblWrapBr mr10 pd20">
			
				<%-- title/button area start --%>
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.authGroup"/></span>
          <button id="btnAdd" class="btn_skyblue"><s:message code="cmm.add"/></button>
          <button id="btnDel" class="btn_skyblue"><s:message code="cmm.del"/></button>
          <button id="btnSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
        
        <div style="height:50px">grid 영역</div>
        
        <!--  
				<%-- 페이지 리스트 --%>
				<div class="pageNum2 mt20">
				  <ul id="page1" data-size="10">
				  </ul>
				</div>
				<%--//페이지 리스트--%>  -->
			
				<%-- 페이지 리스트 --%>
				<div class="pageNum2 mt20">
					<ul id="gridCtrlPager" data-size="10">
						<li class="btn_previous"><a href="unsafe:javascript:void(0);" onclick="return false;" ng-click="_Previous($event, 'gridCtrl', '0');"></a></li>
						<li><a href="unsafe:javascript:void(0);" onclick="return false;" class="on pagenav" ng-click="_pageView('gridCtrl', '1');">1</a></li>
						<li><a href="unsafe:javascript:void(0);" onclick="return false;" class="pagenav" ng-click="_pageView('gridCtrl', '2');">2</a></li>
						<li><a href="unsafe:javascript:void(0);" onclick="return false;" class="pagenav" ng-click="_pageView('gridCtrl', '3');">3</a></li>
						<li><a href="unsafe:javascript:void(0);" onclick="return false;" class="pagenav" ng-click="_pageView('gridCtrl', '4');">4</a></li>
						<li><a href="unsafe:javascript:void(0);" onclick="return false;" class="pagenav" ng-click="_pageView('gridCtrl', '5');">5</a></li>
						<li><a href="unsafe:javascript:void(0);" onclick="return false;" class="pagenav" ng-click="_pageView('gridCtrl', '6');">6</a></li>
						<li><a href="unsafe:javascript:void(0);" onclick="return false;" class="pagenav" ng-click="_pageView('gridCtrl', '7');">7</a></li>
						<li><a href="unsafe:javascript:void(0);" onclick="return false;" class="pagenav" ng-click="_pageView('gridCtrl', '8');">8</a></li>
						<li><a href="unsafe:javascript:void(0);" onclick="return false;" class="pagenav" ng-click="_pageView('gridCtrl', '9');">9</a></li>
						<li><a href="unsafe:javascript:void(0);" onclick="return false;" class="pagenav" ng-click="_pageView('gridCtrl', '10');">10</a></li>
						<li class="btn_next"><a href="unsafe:javascript:void(0);" onclick="return false;" ng-click="_pageNext($event, 'gridCtrl', '11');"></a></li>
					</ul>
				</div>
				<%--//페이지 리스트--%>
				
			</div>
		</div>
	
		<%-- right --%>
		<div class="w50 fr">
			<div class="wj-TblWrapBr ml10 pd20">
			
				<%-- title/button area start --%>
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
        
        <div style="height:50px">grid 영역</div>
	
        <!--  
				<%-- 페이지 리스트 --%>
				<div class="pageNum3 mt20">
				  <ul id="page1" data-size="10">
				  </ul>
				</div>
				<%--//페이지 리스트--%>  -->
					
				<%-- 페이지 리스트 --%>
				<div class="pageNum3 mt20">
					<ul id="gridCtrlPager" data-size="10">
						<li class="btn_previous"><a href="unsafe:javascript:void(0);" onclick="return false;" ng-click="_Previous($event, 'gridCtrl', '0');"></a></li>
						<li><a href="unsafe:javascript:void(0);" onclick="return false;" class="on pagenav" ng-click="_pageView('gridCtrl', '1');">1</a></li>
						<li><a href="unsafe:javascript:void(0);" onclick="return false;" class="pagenav" ng-click="_pageView('gridCtrl', '2');">2</a></li>
						<li><a href="unsafe:javascript:void(0);" onclick="return false;" class="pagenav" ng-click="_pageView('gridCtrl', '3');">3</a></li>
						<li><a href="unsafe:javascript:void(0);" onclick="return false;" class="pagenav" ng-click="_pageView('gridCtrl', '4');">4</a></li>
						<li><a href="unsafe:javascript:void(0);" onclick="return false;" class="pagenav" ng-click="_pageView('gridCtrl', '5');">5</a></li>
						<li><a href="unsafe:javascript:void(0);" onclick="return false;" class="pagenav" ng-click="_pageView('gridCtrl', '6');">6</a></li>
						<li><a href="unsafe:javascript:void(0);" onclick="return false;" class="pagenav" ng-click="_pageView('gridCtrl', '7');">7</a></li>
						<li><a href="unsafe:javascript:void(0);" onclick="return false;" class="pagenav" ng-click="_pageView('gridCtrl', '8');">8</a></li>
						<li><a href="unsafe:javascript:void(0);" onclick="return false;" class="pagenav" ng-click="_pageView('gridCtrl', '9');">9</a></li>
						<li><a href="unsafe:javascript:void(0);" onclick="return false;" class="pagenav" ng-click="_pageView('gridCtrl', '10');">10</a></li>
						<li class="btn_next"><a href="unsafe:javascript:void(0);" onclick="return false;" ng-click="_pageNext($event, 'gridCtrl', '11');"></a></li>
					</ul>
				</div>
				<%--//페이지 리스트--%>
				        
			</div>
		</div>	
		
		<div class="clearfix"></div>
	</div>
	
	<%-- //2col end --%>

	<%-- //wj grid end --%>	

</div>
<!-- //contents end -->

</div>
<!-- //grid 영역 임시 탭 3 : 개발시 불필요 end -->


<!-- grid 영역 임시 탭 4 : 개발시 불필요 Start -->
<div id="col-4" class="tabcontent">

<!-- contents start -->
<div class="subCon" ng-controller="">

	<p class="mt35 mb10">2단 col 좌측: 60%(inner) / 우측: 40%</p>
	
	<%-- wj grid start --%>	
	<div class="wj-TblWrap">
		<%-- left --%>
		<div class="w60 fl">
		
			<div class="wj-TblWrapBr mr10 pd20">
			
				<%-- title/button area start --%>
			  <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
        
				<div style="height:50px">grid 영역</div>
				
				<%-- title/button area start --%>
				<div class="updownSet oh mb10 mt20">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
        
				<div style="height:50px">grid 영역</div>

				<%-- title/button area start --%>
				<div class="updownSet oh mb10 mt20">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
        		
				<div style="height:50px">grid 영역</div>
				
			</div>
		</div>
	
		<%-- right --%>
		<div class="w40 fr">
			<div class="wj-TblWrapBr ml10 pd20">
			
				<%-- title/button area start --%>
				<div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
        		
				<div style="height:250px">grid 영역</div>
			</div>
		</div>	
		
		<div class="clearfix"></div>
	</div>
	<%-- //wj grid end --%>

</div>
<!-- //contents end -->


<!-- contents start -->
<div class="subCon" ng-controller="">

	<p class="mt35 mb10">2단 col 좌측: 60%(outer) / 우측: 40%</p>
	
	<%-- wj grid start --%>	
	<div class="wj-TblWrap">
		<%-- left --%>
		<div class="w60 fl">
		
			<div class="wj-TblWrapBr mr10 pd20">
			
				<%-- title/button area start --%>
				<div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
        	
				<div style="height:50px">grid 영역</div>
			</div>
			
			<div class="wj-TblWrapBr mt10 mr10 pd20">
			
				<%-- title/button area start --%>
				<div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
        	
				<div style="height:50px">grid 영역</div>
			</div>
			
			<div class="wj-TblWrapBr mt10 mr10 pd20">
			
				<%-- title/button area start --%>
				<div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
        	
				<div style="height:50px">grid 영역</div>
			</div>
								
		</div>
	
		<%-- right --%>
		<div class="w40 fr">
			<div class="wj-TblWrapBr ml10 pd20">
				<div style="height:250px">grid 영역</div>
			</div>
		</div>	
		
		<div class="clearfix"></div>
	</div>
	<%-- //wj grid end --%>

</div>
<!-- //contents end -->


</div>
<!-- //grid 영역 임시 탭 4 : 개발시 불필요 end -->


<!-- grid 영역 임시 탭 5 : 개발시 불필요 Start -->
<div id="col-5" class="tabcontent">

<!-- contents start -->
<div class="subCon" ng-controller="">

	<p class="mt35 mb10">2단 col 좌측: 40% / 우측: 60%(inner)</p>
	
	<%-- wj grid start --%>	
	<div class="wj-TblWrap">
		<%-- left --%>
		<div class="w40 fl">
		
			<div class="wj-TblWrapBr mr10 pd20">
			
				<%-- title/button area start --%>
				<div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
        		
				<div style="height:250px">grid 영역</div>
				
			</div>
		</div>
	
		<%-- right --%>
		<div class="w60 fr">
			<div class="wj-TblWrapBr ml10 pd20">
				
				<%-- title/button area start --%>
			  <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
        
				<div style="height:50px">grid 영역</div>
				
				<%-- title/button area start --%>
				<div class="updownSet oh mb10 mt20">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
        
				<div style="height:50px">grid 영역</div>

				<%-- title/button area start --%>
				<div class="updownSet oh mb10 mt20">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
        		
				<div style="height:50px">grid 영역</div>
								
			</div>
		</div>	
		
		<div class="clearfix"></div>
	</div>
	<%-- //wj grid end --%>

</div>
<!-- //contents end -->


<!-- contents start -->
<div class="subCon" ng-controller="">

	<p class="mt35 mb10">2단 col 좌측: 40% / 우측: 60%(outer)</p>
	
	<%-- wj grid start --%>	
	<div class="wj-TblWrap">
		<%-- left --%>
		<div class="w40 fl">
					
			<div class="wj-TblWrapBr mr10 pd20">
				<div style="height:250px">grid 영역</div>
			</div>		

		</div>
	
		<%-- right --%>
		<div class="w60 fr">
			<div class="wj-TblWrapBr ml10 pd20">
			
				<%-- title/button area start --%>
				<div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
        	
				<div style="height:50px">grid 영역</div>
			</div>
			
			<div class="wj-TblWrapBr mt10 ml10 pd20">
			
				<%-- title/button area start --%>
				<div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
        	
				<div style="height:50px">grid 영역</div>
			</div>
			
			<div class="wj-TblWrapBr mt10 ml10 pd20">
			
				<%-- title/button area start --%>
				<div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
        	
				<div style="height:50px">grid 영역</div>
			</div>

		</div>	
		
		<div class="clearfix"></div>
	</div>
	<%-- //wj grid end --%>

</div>
<!-- //contents end -->


</div>
<!-- //grid 영역 임시 탭 5 : 개발시 불필요 end -->


<!-- grid 영역 임시 탭 6 : 개발시 불필요 Start -->
<div id="col-6" class="tabcontent">

<!-- contents start -->
<div class="subCon" ng-controller="">

	<p class="mt35 mb10">2단 col 좌측: 60%(inner detail) / 우측: 40%</p>
	
	<%-- wj grid start --%>	
	<div class="wj-TblWrap">
		<%-- left --%>
		<div class="w60 fl">
		
			<div class="wj-TblWrapBr mr10 pd20" style="height:250px">
			
				<%-- title/button area start --%>
				<div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
        		
				<%-- detail left --%>
				<div class="w30 fl mt20">
					<div class="wj-TblWrapBr mr10 pd20">
						
							<%-- title/button area start --%>
						  <div class="updownSet oh mb10">
			          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
			          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
			        </div>
			        <%-- //title/button area end --%>
			        
							<div style="height:50px">grid 영역</div>	
						</div>	
				</div>
				
				<%-- detail right --%>
				<div class="w70 fr mt20">
					<div class="wj-TblWrapBr ml10 pd20">
							
							<%-- title/button area start --%>
						  <div class="updownSet oh mb10">
			          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
			          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
			        </div>
			        <%-- //title/button area end --%>
			        
							<div style="height:50px">grid 영역</div>
						</div>			
				</div>
				
			</div>
		</div>
	
		<%-- right --%>
		<div class="w40 fr">
			<div class="wj-TblWrapBr ml10 pd20">
				
				<%-- title/button area start --%>
			  <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
				
				<div style="height:250px">grid 영역</div>			
			</div>
		</div>	
		
		<div class="clearfix"></div>
	</div>
	<%-- //wj grid end --%>

</div>
<!-- //contents end -->


<!-- contents start -->
<div class="subCon" ng-controller="">

	<p class="mt35 mb10">2단 col 좌측: 40% / 우측: 60%(inner detail)</p>
	
	<%-- wj grid start --%>	
	<div class="wj-TblWrap">
		<%-- left --%>
		<div class="w40 fl">
		
			<div class="wj-TblWrapBr mr10 pd20">
			
				<%-- title/button area start --%>
				<div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
        		
				<div style="height:250px">grid 영역</div>
				
			</div>
		</div>
	
		<%-- right --%>
		<div class="w60 fr">
			<div class="wj-TblWrapBr ml10 pd20" style="height:250px">
				
				<%-- title/button area start --%>
			  <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
        </div>
        <%-- //title/button area end --%>
				
				<%-- detail left --%>
				<div class="w30 fl mt20">
					<div class="wj-TblWrapBr mr10 pd20">
						
							<%-- title/button area start --%>
						  <div class="updownSet oh mb10">
			          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
			          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
			        </div>
			        <%-- //title/button area end --%>
			        
							<div style="height:50px">grid 영역</div>
						</div>	
				</div>
				
				<%-- detail right --%>
				<div class="w70 fr mt20">
					<div class="wj-TblWrapBr ml10 pd20">
							
							<%-- title/button area start --%>
						  <div class="updownSet oh mb10">
			          <span class="fl bk lh30"><s:message code="authGroup.resrcInfo"/></span>
			          <button id="btnResrceSave" class="btn_skyblue"><s:message code="cmm.save"/></button>
			        </div>
			        <%-- //title/button area end --%>
			        
							<div style="height:50px">grid 영역</div>
						</div>			
				</div>
												
			</div>
		</div>	
		
		<div class="clearfix"></div>
	</div>
	<%-- //wj grid end --%>

</div>
<!-- //contents end -->

</div>
<!-- //grid 영역 임시 탭 6 : 개발시 불필요 end -->



<script>
// 임시 탭
function tabCol(evt, cityName) {
  var i, tabcontent, tablinks;
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }
  document.getElementById(cityName).style.display = "block";
  evt.currentTarget.className += " active";
}

</script>
