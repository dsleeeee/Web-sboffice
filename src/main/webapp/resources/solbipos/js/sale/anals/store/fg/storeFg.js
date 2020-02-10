/**
 * get application
 */
var app = agrid.getApp();

/** 할인구분별(매출리스트) controller */
app.controller('storeFgCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeFgCtrl', $scope, $http, $timeout, true));

  $scope.srchStoreFgStartDate = wcombo.genDateVal("#srchStoreFgStartDate", gvStartDate);
  $scope.srchStoreFgEndDate   = wcombo.genDateVal("#srchStoreFgEndDate", gvEndDate);

  // 리스트 콤보박스 데이터 Set
  $scope._setComboData("storeFgListScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 조회조건 '매장구분 표시'
	$scope.getStoreFgComboList();

	// 콤보박스 데이터 Set
	$scope._setComboData('storeFglistScaleBox', gvListScaleBoxData);
	  
	// picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("storeFgCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
    
    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem         = {};
    dataItem.storeNm  = messages["store.storeFg"];
    dataItem.totSaleAmt       = messages["store.totSaleAmt"];
    dataItem.totDcAmt      = messages["store.totDcAmt"];
    dataItem.realSaleAmt   = messages["store.realSaleAmt"];
    dataItem.saleCnt   = messages["store.saleCnt"];
    dataItem.ratRealSaleAmt   = messages["store.rat"];
    dataItem.ratCnt   = messages["store.rat"];

    s.columnHeaders.rows[0].dataItem = dataItem;

    s.itemFormatter = function (panel, r, c, cell) {
      if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
        //align in center horizontally and vertically
        panel.rows[r].allowMerging    = true;
        panel.columns[c].allowMerging = true;
        wijmo.setCss(cell, {
          display    : 'table',
          tableLayout: 'fixed'
        });
        cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
        wijmo.setCss(cell.children[0], {
          display      : 'table-cell',
          verticalAlign: 'middle',
          textAlign    : 'center'
        });
      }
      // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
      else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
        // GroupRow 인 경우에는 표시하지 않는다.
        if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
          cell.textContent = '';
        } else {
          if (!isEmpty(panel._rows[r]._data.rnum)) {
            cell.textContent = (panel._rows[r]._data.rnum).toString();
          } else {
            cell.textContent = (r + 1).toString();
          }
        }
      }
      // readOnly 배경색 표시
      else if (panel.cellType === wijmo.grid.CellType.Cell) {
        var col = panel.columns[c];
        if (col.isReadOnly) {
          wijmo.addClass(cell, 'wj-custom-readonly');
        }
      }
    }
  }
  
  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("storeFgCtrl", function (event, data) {
    $scope.searchStoreFgList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 매장형태별 매출 리스트 조회
  $scope.searchStoreFgList = function () {
  if ($("#storeFgSelectProdCd").val() === '') {
      $scope._popMsg(messages["prodsale.day.require.selectStore"]); // 매장을 선택해주세요.
      return false;
    }

    // 파라미터
    var params       = {};
  //  params.startDate = wijmo.Globalize.format($scope.srchStoreFgStartDate.value, 'yyyyMMdd');
  //  params.endDate = wijmo.Globalize.format($scope.srchStoreFgEndDate.value, 'yyyyMMdd');
    params.prodCd   = $("#storeFgSelectProdCd").val();
    params.listScale = $scope.listScale; //-페이지 스케일 갯수
    
    // 등록일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchStoreFgStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchStoreFgEndDate.value, 'yyyyMMdd');
    }
    
    if(params.startDate > params.endDate){
   	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
   	 	return false;
    }
    // 매장구분 전체선택에 따른 params
    if(!$scope.isAll){
      params.storeFg = $scope.storeFg;
    }
	  
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/sale/anals/store/fg/list.sb", params);
  };

  //전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchStoreFgStartDate.isReadOnly = $scope.isChecked;
    $scope.srchStoreFgEndDate.isReadOnly = $scope.isChecked;
  };
  
  // 전체 매장구분 체크박스 클릭이벤트
  $scope.totalStoreFg = function() {
	  var grid = wijmo.Control.getControl("#srchStoreFgDisplay");
	  grid.isReadOnly = $scope.isAll;;
  };
  
  //상품선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.storeFgSelectProdShow = function () {
    $scope._broadcast('storeFgSelectProdCtrl');
  };

  //엑셀 다운로드
  $scope.excelDownloadStoreFg = function () {
    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles   : false,
        includeColumns      : function (column) {
          return column.visible;
        }
      }, 'storeFg.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };
  
  //조회조건 매장구분 리스트 조회
  $scope.getStoreFgComboList = function () {
    var url             = '/sale/anals/store/fg/storeFgComboList.sb';
    var comboParams     = {};
    comboParams.prodCd = $("#storeFgSelectProdCd").val();
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo", "srchStoreFgDisplay", null, url, comboParams, "A", null); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.
  };


  // DB 데이터를 조회해와서 그리드에서 사용할 Combo를 생성한다.
  // comboFg : map - 그리드에 사용할 Combo, combo - ComboBox 생성. 두가지 다 사용할경우 combo,map 으로 하면 둘 다 생성.
  // comboId : combo 생성할 ID
  // gridMapId : grid 에서 사용할 Map ID
  // url : 데이터 조회할 url 정보. 명칭관리 조회시에는 url 필요없음.
  // params : 데이터 조회할 url에 보낼 파라미터
  // option : A - combo 최상위에 전체라는 텍스트를 붙여준다. S - combo 최상위에 선택이라는 텍스트를 붙여준다. A 또는 S 가 아닌 경우는 데이터값만으로 생성
  // callback : queryCombo 후 callback 할 함수
  $scope._queryCombo = function (comboFg, comboId, gridMapId, url, params, option, callback) {
    var comboUrl = "/iostock/cmm/iostockCmm/getCombo.sb";
    if (url) {
      comboUrl = url;
    }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : comboUrl, /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data.list)) {
          var list       = response.data.data.list;
          var comboArray = [];
          var comboData  = {};

          if (comboFg.indexOf("combo") >= 0 && nvl(comboId, '') !== '') {
            comboArray = [];
            if (option === "A") {
              comboData.name  = messages["cmm.all"];
              comboData.value = "";
              comboArray.push(comboData);
            } else if (option === "S") {
              comboData.name  = messages["cmm.select"];
              comboData.value = "";
              comboArray.push(comboData);
            }

            for (var i = 0; i < list.length; i++) {
              comboData       = {};
              comboData.name  = list[i].nmcodeNm;
              comboData.value = list[i].nmcodeCd;
              comboArray.push(comboData);
            }
            $scope._setComboData(comboId, comboArray);
          }

          if (comboFg.indexOf("map") >= 0 && nvl(gridMapId, '') !== '') {
            comboArray = [];
            for (var i = 0; i < list.length; i++) {
              comboData      = {};
              comboData.id   = list[i].nmcodeCd;
              comboData.name = list[i].nmcodeNm;
              comboArray.push(comboData);
            }
            $scope[gridMapId] = new wijmo.grid.DataMap(comboArray, 'id', 'name');
          }
        }
      }
    }, function errorCallback(response) {
      $scope._popMsg(messages["cmm.error"]);
      return false;
    }).then(function () {
      if (typeof callback === 'function') {
        $timeout(function () {
          callback();
        }, 10);
      }
    });
  };
}]);