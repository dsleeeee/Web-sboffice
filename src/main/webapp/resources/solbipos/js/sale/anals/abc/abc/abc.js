/**
 * get application
 */
var app = agrid.getApp();

var vSortFg = [
    {"name":"금액순","value":"1"},
    {"name":"수량순","value":"2"}
];

/** 할인구분별(매출리스트) controller */
app.controller('abcCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('abcCtrl', $scope, $http, $timeout, true));

  // 상품 본사통제구분 (H : 본사, S: 매장)
  $scope.prodEnvstVal = prodEnvstVal;
  $scope.userOrgnFg = gvOrgnFg;

  // 본사에서 들어왔을때는 매장코드가 없다. (가상로그인 후, 세로고침 몇번 하면 gvOrgnFg가 바뀌는 것 예방)
  $scope.userStoreCd = gvStoreCd;
  $scope.btnShowFg = false;

  if(($scope.prodEnvstVal === 'HQ' && isEmptyObject($scope.userStoreCd))
    || ($scope.prodEnvstVal === 'STORE' &&  !isEmptyObject($scope.userStoreCd))) {
        $scope.btnShowFg = true;
  }

  // 상품 상세 정보
  $scope.prodInfo = {};
  $scope.setProdInfo = function(data){
    $scope.prodInfo = data;
  };
  $scope.getProdInfo = function(){
    return $scope.prodInfo;
  };

  $scope.srchAbcStartDate = wcombo.genDateVal("#srchAbcStartDate", getToday());
  $scope.srchAbcEndDate   = wcombo.genDateVal("#srchAbcEndDate", getToday());

  $scope.orgnFg = gvOrgnFg;
  $scope.hqOfficeCd = gvHqOfficeCd;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

	// 콤보박스 데이터 Set
	$scope._setComboData('abclistScaleBox', gvListScaleBoxData);
	$scope._setComboData("srchAbcDisplay", vSortFg);

	// picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("abcCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 그리드 링크 효과
	s.formatItem.addHandler(function (s, e) {
		if (e.panel === s.cells) {
			var col = s.columns[e.col];
			if (col.binding === "prodCd") { // 상품코드
	          	wijmo.addClass(e.cell, 'wijLink');
	        }
		}
	});

	// 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if( col.binding === "prodCd") {
            $scope.searchProdDetail(selectedRow.prodCd);
        }
      }
    });


  }

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("abcCtrl", function (event, data) {
    $scope.searchAbcList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  //다른 컨트롤러의 broadcast 받기
  $scope.$on("abcCtrlSrch", function (event, data) {
    $scope.searchAbcList(false);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 매장형태별 매출 리스트 조회
  $scope.searchAbcList = function (isPageChk) {

    // 파라미터
    var params       = {};
    params.orgnFg    = $scope.orgnFg;
    params.hqOfficeCd    = $scope.hqOfficeCd;
    params.gradeA = $("#abcGradeA").val();
    params.gradeB = $("#abcGradeB").val();
    params.gradeC = $("#abcGradeC").val();
    params.sortFg   = $scope.sortFg;
    params.storeCd = $("#abcSelectStoreCd").val();
    params.listScale = $scope.listScale; //-페이지 스케일 갯수
    params.isPageChk = isPageChk;

    // 등록일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchAbcStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchAbcEndDate.value, 'yyyyMMdd');
    }

    if(params.startDate > params.endDate){
   	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
   	 	return false;
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/anals/abc/abc/abcList.sb", params);

  };

  //전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchAbcStartDate.isReadOnly = $scope.isChecked;
    $scope.srchAbcEndDate.isReadOnly = $scope.isChecked;
  };
  
  // 상품분류 항목표시 체크에 따른 대분류, 중분류, 소분류 표시
  $scope.isChkProdClassDisplay = function(){
	  var columns = $scope.flex.columns;

	  for(var i=0; i<columns.length; i++){
		  if(columns[i].binding === 'lv1Nm' || columns[i].binding === 'lv2Nm' || columns[i].binding === 'lv3Nm'){
			  $scope.ChkProdClassDisplay ? columns[i].visible = true : columns[i].visible = false;
		  }
	  }
  }
  
  //매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.abcSelectStoreShow = function () {
	  $scope._broadcast('abcSelectStoreCtrl');
  };

  //엑셀 다운로드
  $scope.excelDownloadAbc = function () {
    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles   : true,
        includeColumns      : function (column) {
          return column.visible;
        }
      }, '매출분석_상품ABC분석'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

  // 상세정보 팝업
  $scope.searchProdDetail = function(prodCd) {
    var detailPopUp = $scope.prodDetailLayer;
    setTimeout(function() {
      detailPopUp.show(true, function (s) {
        // 수정 버튼 눌렀을때만
        if (s.dialogResult === "wj-hide-apply") {
          // 상품정보 수정 팝업
          var modifyPopUp = $scope.prodModifyLayer;
          modifyPopUp.show();
          /*modifyPopUp.show(true, function (s) {
            // 상품정보 수정 팝업 - 저장
            if (s.dialogResult === "wj-hide-apply") {
              // 팝업 속성에서 상품정보 get
              var params = s.data;
              // 저장수행
              $scope._postJSONSave.withPopUp("/base/prod/prod/prod/save.sb", params, function () {
                $scope._popMsg(messages["cmm.saveSucc"]);
              });
            }
          });*/
        }
      });
    }, 50);
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 상품상세정보 팝업 핸들러 추가
    $scope.prodDetailLayer.shown.addHandler(function (s) {
      var selectedRow = $scope.flex.selectedRows[0]._data;
      setTimeout(function() {
        var params = {};
        params.prodCd = selectedRow.prodCd;
        $scope._broadcast('prodDetailCtrl', params);
      }, 50);
    });
  });
}]);