<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
#content div {
    font-family:inherit;
    font-weight:inherit;
    font-size:inherit;
    color:inherit;
}
.wj-topleft div.wj-row:first-child .wj-cell {background:#e8e8e8 center center no-repeat; background-size:21px 20px;}
</style>
<script>
var urlParams = (function(url) {
  var result = {};
  var idx = url.lastIndexOf('?');

  if (idx > 0) {
    var params = url.substring(idx + 1).split('&');

    for (var i = 0; i < params.length; i++) {
      idx = params[i].indexOf('=');

      if (idx > 0) {
        result[params[i].substring(0, idx)] = params[i].substring(idx + 1);
      }
    }
  }

  return result;
})(window.location.href);

// Default resources are included in grapheditor resources
mxLoadResources = false;

// urlParams is null when used for embedding
window.urlParams = window.urlParams || {};

window.MAX_REQUEST_SIZE = window.MAX_REQUEST_SIZE  || 10485760;

window.RESOURCES_PATH = window.RESOURCES_PATH || '/resource/graph/resources';
window.RESOURCE_BASE = window.RESOURCE_BASE || window.RESOURCES_PATH + '/message';
window.STYLE_PATH = window.STYLE_PATH || '/resource/graph/styles';
window.CSS_PATH = window.CSS_PATH || '/resource/graph/styles';
window.IMAGE_PATH = window.IMAGE_PATH || '/resource/graph/images';
window.STENCIL_PATH = window.STENCIL_PATH || '/resource/graph/stencils';
//window.CONFIG_PATH = window.CONFIG_PATH || '/resource/graph/config';

window.TABLELAYOUT_OPEN_URL = window.TABLELAYOUT_OPEN_URL || '/base/store/tableLayout/tableLayout/view.sb';
window.TABLELAYOUT_SAVE_URL = window.TABLELAYOUT_SAVE_URL || '/base/store/tableLayout/tableLayout/save.sb';

window.mxBasePath = window.mxBasePath || '/resource/vendor/mxgraph/src';
window.mxLanguage = window.mxLanguage || urlParams['lang'];
window.mxLanguages = window.mxLanguages || ['ko'];

</script>
<script type="text/javascript" src="/resource/vendor/mxgraph/mxClient.js" charset="utf-8"></script>
<!--script type="text/javascript" src="/resource/vendor/mxgraph/mxClient.min.js"></script-->
<script type="text/javascript" src="/resource/graph/sanitizer/sanitizer.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/resource/graph/js/TableLayout.js" charset="utf-8"></script>


<%--서브컨텐츠--%>
<div class="subCon2" ng-controller="tbLayoutCtrl">

	<%--테이블속성, 테이블관리, 판매터치키 page에만 쓰임--%>
	<div class="posWrap oh">

		<%-- left --%>
		<div class="w70 fl">

		   <%--그룹--%>
		   <div class="mr10 mb20">
		     <h2 class="h2_tit2 fl"><s:message code="tableLayout.group"/></h2>

				<div class="fr">
		      <%-- 버튼 --%>
					<div class="updownSet oh mb5 fl">
						<button class="btn_skyblue fl ml10" id="">그룹추가</button>
		      	<button class="btn_skyblue fr ml10" id="">저장</button>
					</div>
					
				</div>
				<div class="clearfix"></div>		     
		     
		     <div class="frGroupWrap t2" id="divLayers">
		       <span><a href="#" class="on">2층</a></span>
		       <span><a href="#" class="on">2층</a></span>
		     </div>
		   </div>
		   <%--//그룹--%>

	    <%--구성요소--%>
	    <div class="fl w110px">
	     <h2 class="h2_tit2" style="margin-bottom:px"><s:message code="tableLayout.component"/></h2>
	     <div class="cfgWrap" style="height:490px">
	       <ul class="tableImg" id="divCompos">
	         <%--<li><a href="#"><img src="img/img_table2.png" alt="" /></a></li>--%>
	       </ul>
	     </div>
	    </div>
	     <%--//구성요소--%>
		    				
			<%-- 미리보기 --%>
			<div class="fl ml10" style="width:402px">		
				<h2 class="h2_tit2"><s:message code="tableLayout.preview"/></h2>			
	    				
				<div class="prev1 patternBg t2" id="content" tabindex="-1" style="width:402px; height:490px">
					<%--점 패턴 background classname : "patternBg"--%>
					<%--510 * 510 pixel 입니다.--%>
				</div>
			</div>
			<%-- //미리보기 --%>
			
		</div>
		<%-- //left --%>

		<%--right--%>
		<div class="w30 fr">
			<h2 class="h2_tit2"><s:message code="tableLayout.component"/></h2>
			
			<%--구성요소--%>
			<div class="cfgWrap pd10" style="height:605px">
 				<%-- 테이블명 --%>
        <div class="touch-box">
	        <div class="bk lh30 s12">테이블명</div>
	        <div class="mb5 s12">
						<input type="text" class="sb-input w125px" id="">
						<button class="btn_skyblue ml5" id="">테이블추가</button>
	        </div>
        </div>				
				
	      <%-- 테이블자리수/유형 --%>
        <div class="touch-box mt10">
        	
        	<%-- 테이블자리수 --%>
        	<div class="fl w45">
		        <span class="bk lh30 s12">테이블자리수</span>
          	<%-- 셀렉트 --%>
						<div class="sb-select">
							<span class="txtIn">
								<wj-combo-box
									items-source="combobox2"
									is-editable="false">
								</wj-combo-box>
							</span>
						</div>
	      	</div>
					
					<%-- 테이블유형 --%>
        	<div class="fr w45">
	          <span class="bk lh30 s12">테이블유형</span>                 
          	<%-- 셀렉트 --%>
						<div class="sb-select">
							<span class="txtIn">
								<wj-combo-box
									items-source="combobox3"
									is-editable="false">
								</wj-combo-box>
							</span>
						</div>
          </div>
          
				</div>
				<%-- //테이블자리수/유형 --%>
				 
	      <%-- 위치 --%>
				<div class="touch-box mt10">
	        <span class="bk lh30 s12">위치</span>
	        <div class="touch-box mb10 s12">
		        <div class="fl w45 mb5">
		          <span class="txtIn bk lh30 s14 w30px">X</span>
		          <input type="text" class="fr sb-input w50px tc" id="" maxlength="4">
		        </div>
		        <div class="fr w45 mb5">
	            <span class="txtIn bk lh30 s14 w30px">Y</span>
	            <input type="text" class="fr sb-input w50px tc" id="" maxlength="4">
		        </div>
		        <div class="clearfix"></div>
		        <div class="fl w45">
	            <span class="bk lh30 s12 w30px">높이</span>
	            <input type="text" class="fr sb-input w50px tc" id="" maxlength="4">
		        </div>
		        <div class="fr w45">
	            <span class="bk lh30 s12 w30px">넓이</span>
	            <input type="text" class="fr sb-input w50px tc" id="" maxlength="4">
		        </div>
	        </div>
					<div class="clearfix"></div>
				</div>
				<%-- //위치 --%>
				
				<button class="btn_skyblue w100" id="btnLayerConfig">테이블속성 설정</button>
				
			</div>
			<%--//구성요소--%>			
		</div>
		<%--//right--%>
		
	</div>
	<%--// posWrap end --%>
	
	<%--layer:For Center screen--%>
	<div class="fullDimmed floorLayer" id="floorMask"  style="display: none;"></div>
	<div class="layer floorLayer" id="floorLayer" style="display: none;">
		<div class="layer_inner">
			
			<%--layerContent--%>
			<div class="title" style="width:960px; padding-bottom:20px">
				<p class="tit">테이블속성 설정</p>
				<a href="#" class="btn_close _btnClose"></a>
				
				<%-- layer content inner --%>
				<div class="con sc" style="height:600px;">
					
					<%-- left --%>
					<div class="tch-inner mr10">
					 	왼쪽 그리드
					</div>				
					<%-- //left --%>
					
					<%-- center --%>
					<div class="tch-inner t2 mr10">
						가운데 영역
					</div>
					<%--//center--%>
					
					<%-- right --%>
					<div class="tch-inner">				
	         	<%-- 셀렉트 --%>
						<div class="sb-select">
							<span class="txtIn">
								<wj-combo-box
									items-source="combobox4"
									is-editable="false">
								</wj-combo-box>
							</span>
						</div>							
					
			      <%-- 위치 --%>
						<div class="touch-box mt10">
			        <span class="bk lh30 s12">위치</span>
			        <div class="touch-box mb10 s12">
				        <div class="fl w45 mb5">
				          <span class="txtIn bk lh30 s14 w30px">X</span>
				          <input type="text" class="fr sb-input w50px tc" id="" maxlength="4">
				        </div>
				        <div class="fr w45 mb5">
			            <span class="txtIn bk lh30 s14 w30px">Y</span>
			            <input type="text" class="fr sb-input w50px tc" id="" maxlength="4">
				        </div>
				        <div class="clearfix"></div>
				        <div class="fl w45">
			            <span class="bk lh30 s12 w30px">높이</span>
			            <input type="text" class="fr sb-input w50px tc" id="" maxlength="4">
				        </div>
				        <div class="fr w45">
			            <span class="bk lh30 s12 w30px">넓이</span>
			            <input type="text" class="fr sb-input w50px tc" id="" maxlength="4">
				        </div>
			        </div>
							<div class="clearfix"></div>
						</div>
						<%-- //위치 --%>				
	
				    <%-- 상세 폰트 설정 Start --%>
						<div id="divProdNavWrap" class="prodNavWrap t2">
							<div id="keyStyle" class="keyStyleWrap">						
								<div id="fontStyleWrap" class="w100">
									<span class="s12 lh30 bk"><s:message code="touchKey.font"/></span>
									<div class="sb-select w100 mb5">
										<%-- 폰트컬러 --%>
										<div id="fontColor"></div>
									</div>
									<div class="sb-select w100 mb5">
										<%-- 폰트사이즈 --%>
										<div id="fontSize"></div>
									</div>
								</div>
								
								<div id="colorStyleWrap" class="mb5">
									<span class="s12 fl lh30 bk mt5"><s:message code="touchKey.fill"/></span>
									<div class="sb-select txtIn w100">
										<%-- 채우기 --%>
										<div id="fillColor"></div>
									</div>
								</div>
							</div>
						</div>
						<div class="clearfix"></div>
						<%-- //상세 폰트 설정 end --%>				
	
						<%-- 사용여부 --%>
						<span class="bk lh30 s12">사용여부</span>
						<div class="w100">
					    <span class="rdo">
				        <input type="radio" id="rdo1" name="1" checked />
				        <label for="rdo1" class="rdo-po">예</label>
					    </span>
					    <span class="rdo ml20">
				        <input type="radio" id="rdo2" name="1" />
				        <label for="rdo2" class="rdo-po">아니오</label>
					    </span>
						</div>
						
					</div>
					<div class="clearfix"></div>
					<%--//right--%>
				
				</div>
				<%-- //layer content inner --%>
				
				<div class="btnSet" style="padding-top:20px">
					<span><a href="#" class="btn_blue _btnClose" id="btnApply">저장</a></span>
					<span><a href="#" class="btn_gray _btnClose"><s:message code="cmm.close"/></a></span>
				</div>
			</div>
			<%--//layerContent--%>
				
		</div>
	</div>
	<%--//layer:For Center screen--%>	
	
	
</div>
<%--//서브컨텐츠--%>

<script>
(function() {
  var tableLayoutInit = TableLayout.prototype.init;
  TableLayout.prototype.init = function() {
    tableLayoutInit.apply(this, arguments);
  };
  
  if (!mxClient.isBrowserSupported()) {
    // Displays an error message if the browser is not supported.
    mxUtils.error('Browser is not supported!', 200, false);
  }
  else {
    // Adds required resources (disables loading of fallback properties, this can only
    // be used if we know that all keys are defined in the language specific file)
    mxResources.loadDefaultBundle = false;
    var bundle = mxResources.getDefaultBundle(RESOURCE_BASE, mxLanguage)
        || mxResources.getSpecialBundle(RESOURCE_BASE, mxLanguage);

    // Fixes possible asynchronous requests
    mxUtils.getAll(
      [ bundle, STYLE_PATH + '/tablelayout.xml' ],
      function(xhr) {
        // Adds bundle text to resources
        mxResources.parse(xhr[0].getText());

        // Configures the default graph theme
        var themes = {};
        themes[Graph.prototype.defaultThemeName] = xhr[1].getDocumentElement();

        // Main
        var tablelayout = new TableLayout(themes);
      },
      function() {
        document.body.innerHTML = '<center style="margin-top:10%;">Error loading resource files. Please check browser console.</center>';
      });
  }
})();
</script>


<%-- 기존 페이지 스크립트 및 폰트/채우기등 추가 --%>
<script>

$(document).ready(function() {

	$("#btnLayerConfig").click(function(e) {
	  $("div.floorLayer").show();
	  //wijmo.grid.FlexGrid.refreshAll();
	});

	$("._btnClose").click(function(e) {
	  $("div.floorLayer").hide();
	});

});


var app = agrid.getApp();

/**********************************************************************
 *  테스트 페이지
 **********************************************************************/
app.controller('tbLayoutCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('tbLayoutCtrl', $scope, $http, true));

		$scope.combobox1 = ['1층','2층','테라스'];
		$scope.combobox2 = ['전체','01','02','03','04'];
		$scope.combobox3 = ['사각','원'];
		$scope.combobox4 = ['테이블명','구분1','구분2'];

	}

]);

/**
 * 폰트 색상 설정 시작
 */
this.fontColor = new wijmo.input.InputColor('#fontColor', {
  placeholder: 'Select the color',
  value: '#000000'
/*   valueChanged: function (s, e) {
    // cell 영역 선택시에만
    if (s.graph) {
      if(s.value.length > 7) {
        var rgb = s.value;
        rgb = rgb.match(/^rgba?[\s+]?\([\s+]?(\d+)[\s+]?,[\s+]?(\d+)[\s+]?,[\s+]?(\d+)[\s+]?/i);
        s.value = (rgb && rgb.length === 4) ? "#" +
          ("0" + parseInt(rgb[1],10).toString(16)).slice(-2) +
          ("0" + parseInt(rgb[2],10).toString(16)).slice(-2) +
          ("0" + parseInt(rgb[3],10).toString(16)).slice(-2) : '';
      }
      var cellType = graph.cellTypeCombo.selectedValue;
      var cell = s.graph.getSelectionCells()[0];
      if (cell.children) {
        if ( cellType === "02" ) {
          s.graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, s.value, new Array(cell.children[0]));
        } else if ( cellType === "03" ) {
          s.graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, s.value, new Array(cell.children[1]));
        }
      }
    }
  } */
});


/**
 * 폰트 크기 설정 시작
 */
this.fontSize = new wijmo.input.InputNumber('#fontSize', {
  format: 'n0',
  step: 1,
  min: 8,
  max: 20,
  value: 10
/*   valueChanged: function (s, e) {
    // cell 영역 선택시에만
    if (s.graph) {
      var cellType = graph.cellTypeCombo.selectedValue;
      var cell = s.graph.getSelectionCells()[0];
      if (cell.children) {
        if ( cellType === "02" ) {
          s.graph.setCellStyles(mxConstants.STYLE_FONTSIZE, s.value, new Array(cell.children[0]));
        } else if ( cellType === "03" ) {
          s.graph.setCellStyles(mxConstants.STYLE_FONTSIZE, s.value, new Array(cell.children[1]));
        }
      }
    }
  } */
});

/**
 * 셀 채우기 색상 설정 시작
 */
this.fillColor = new wijmo.input.InputColor('#fillColor', {
  placeholder: 'Select the color',
  value: '#000000'
/*   valueChanged: function (s, e) {
    // cell 영역 선택시에만
    if (s.graph) {
      if(s.value.length > 7) {
        var rgb = s.value;
        rgb = rgb.match(/^rgba?[\s+]?\([\s+]?(\d+)[\s+]?,[\s+]?(\d+)[\s+]?,[\s+]?(\d+)[\s+]?/i);
        s.value = (rgb && rgb.length === 4) ? "#" +
          ("0" + parseInt(rgb[1],10).toString(16)).slice(-2) +
          ("0" + parseInt(rgb[2],10).toString(16)).slice(-2) +
          ("0" + parseInt(rgb[3],10).toString(16)).slice(-2) : '';
      }
      var cell = s.graph.getSelectionCells()[0];
      // 하위속성 존재시 하위속성 색상도 같이 변경
      if (cell.children) {
        s.graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, s.value, cell.children);
      }
      s.graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, s.value, null);
    }
  } */
});

</script>
