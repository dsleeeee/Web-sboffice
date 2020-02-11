/**
 * get application
 */
var app = agrid.getApp();

/** 설정기간별(매출리스트) controller */
app.controller('prodStrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodStrl', $scope, $http, $timeout, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("prodStrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "prodClassNm") {
          	wijmo.addClass(e.cell, 'wijLink');
          	wijmo.addClass(e.cell, 'wj-custom-readonly');
        }
      }
    });

    // 그리드 클릭 이벤트-------------------------------------------------------------------------------------------------
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        var params       = {};
        	params.prodClassCd = selectedRow.prodClassCd;
        if (col.binding === "prodClassNm") { 
            $scope._broadcast('prodDtlStrl', params);
        }
      }
    });

  }

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("prodStrl", function (event, data) {
	  
	  //화면구분값
	  $("#gubun").val(data.gubun);
	  
	  $scope.prodSLayer.show(true);
	  $scope.searchProdList(true);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  
  //상품선택 대분류 리스트 조회
  $scope.searchProdList = function (isPageChk) {

    // 파라미터
    var params       = {};

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/sale/com/popup/classProd/view.sb", params);

    //메인그리드 조회후 상세그리드 조회.
	$scope.loadedRows = function(sender, args){

		var rows = sender.rows;

		var params		 = {};
		if(rows.length != 0) {
			params.prodClassCd   = rows[0].dataItem.prodClassCd;
	    }

	    // 코너별 매출현황 상세조회.
	    $scope._broadcast("prodDtlStrl", params);
	}
  };
}]);
/** 상품선택(상세) controller */
app.controller('prodDtlStrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

	 // 상위 객체 상속 : T/F 는 picker
	  angular.extend(this, new RootController('prodDtlStrl', $scope, $http, $timeout, true));
	 
	  // grid 초기화 : 생성되기전 초기화되면서 생성된다
	  $scope.initGrid = function (s, e) {

	    // picker 사용시 호출 : 미사용시 호출안함
	    $scope._makePickColumns("prodDtlStrl");

	    // 그리드 링크 효과
	    s.formatItem.addHandler(function (s, e) {
	      if (e.panel === s.cells) {
	        var col = s.columns[e.col];
	        if(col.binding === "prodCd") { 
	        	wijmo.addClass(e.cell, 'wijLink');
	        	wijmo.addClass(e.cell, 'wj-custom-readonly');
	        }
	      }
	    });

	    // 그리드 클릭 이벤트-------------------------------------------------------------------------------------------------
	    s.addEventListener(s.hostElement, 'mousedown', function (e) {
	      var ht = s.hitTest(e);
	      if (ht.cellType === wijmo.grid.CellType.Cell) {
	        var col         = ht.panel.columns[ht.col];
	        var selectedRow = s.rows[ht.row].dataItem;
	        var gubun =   $("#gubun").val();
	        if (col.binding === "prodCd") { 
	        	$("#srch"+gubun+"ProdCd").val(selectedRow.prodCd);
	        	
	            $("#srch"+gubun+"ProdNm").val(selectedRow.prodNm);
	            $("#srch"+gubun+"ProdClassCd").val($("#prodClassCd").val());
	            $scope.prodSLayer.hide(true);
	        }
	      }
	    });

	  }

	  // 다른 컨트롤러의 broadcast 받기
	  $scope.$on("prodDtlStrl", function (event, data) {
		  if(data != undefined){
			  $scope.prodClassCd = data.prodClassCd;
			  $("#prodClassCd").val(data.prodClassCd);
		  }

	    $scope.searchProdDtlList(true);
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });
	  
	  // 상품상세 리스트 조회
	  $scope.searchProdDtlList = function (isPageChk) {

	    var params       = {};
	    
	    if($scope.prodClassCd != null){
	    	 params.prodClassCd   = $scope.prodClassCd;
	    }else{
		     params.prodClassCd   = $("#prodClassCd").val();
	    }
	    params.srchProdCd    = $("#srchPopProdCd").val();
	    params.srchProdNm    = $("#srchPopProdNm").val();

	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/com/popup/prodNm/view.sb", params);
	  };

}]);
