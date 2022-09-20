/**
 * get application
 */
var app = agrid.getApp();

var vSortFg = [
//	{"name":"매장형태","value":"1"}
  {"name":"직영구분","value":"1"}
  ,{"name":"매장용도","value":"2"}
];

var closeFgData = [
  {"name":"전체","value":""},
  {"name":"미마감","value":"0"},
  {"name":"마감","value":"1"}
];

/** 일별종합 controller */
app.controller('dayCloseCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dayCloseCtrl', $scope, $http, true));

  // 검색조건에 조회기간
  var startMonth = new wijmo.input.InputDate('#closeMonth', {
    format       : "yyyy-MM",
    selectionMode: "2" // 달력 선택 모드(1:day 2:month)
  });

  $scope._setComboData("closeFg", closeFgData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 그리드 DataMap 설정
    $scope.closeFgDataMap = new wijmo.grid.DataMap(closeFgData, 'value', 'name'); // 구분

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("dayCloseCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];

        // 회원번호, 회원명
        if (col.binding === "closeDate") {
          // var item = s.rows[e.row].dataItem;
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];

        //  수량합계 클릭시 상세정보 조회
        if ( col.binding === "closeDate") {
          var selectedRow = s.rows[ht.row].dataItem;
          var params      = {};
          params.storeCd      = selectedRow.storeCd;
          params.storeNm      = selectedRow.storeNm;
          params.closeDate    = selectedRow.closeDate;
          params.openDate     = selectedRow.openDate;
          params.closeFg      = selectedRow.closeFg;
          params.interestAmt  = selectedRow.interestAmt;
          params.inAmt        = selectedRow.inAmt;
          params.outAmt       = selectedRow.outAmt;
          params.groupAmt     = selectedRow.groupAmt;
          params.hockeyAmt    = selectedRow.hockeyAmt;
          params.etcAmt       = selectedRow.etcAmt;
          params.inDayAmt     = selectedRow.inDayAmt;
          params.inSum        = selectedRow.inSum;
          params.inMonthSum   = selectedRow.inMonthSum;
          params.inBMonthSum  = selectedRow.inBMonthSum;
          params.inTotalSum   = selectedRow.inTotalSum;
          params.outSum       = selectedRow.outSum;
          params.outMonthSum  = selectedRow.outMonthSum;
          params.outBMonthSum = selectedRow.outBMonthSum;
          params.outTotalSum  = selectedRow.outTotalSum;
          params.remark1      = selectedRow.remark1;
          params.remark2      = selectedRow.remark2;
          params.remark3      = selectedRow.remark3;
          params.remark4      = selectedRow.remark4;
          params.remark5      = selectedRow.remark5;
          params.remark6      = selectedRow.remark6;

          $scope.dayCloseDtlLayer.show(true);
          $scope._broadcast('dayCloseDtlCtrl', params);
          event.preventDefault();
        }
      }
    });
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("dayCloseCtrl", function (event, data) {
    $scope.searchDayClose();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 조회
  $scope.searchDayClose = function () {
    // 파라미터
    var params       = {};
    params.closeDate = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
    params.storeCd = $("#dayCloseStoreCd").val();

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/excclc/excclc/dayClose/dayClose/getDayCloseList.sb", params, function (){});
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.dayCloseStoreShow = function () {
    $scope._broadcast('dayCloseStoreCtrl');
  };

  // 엑셀 다운로드
  $scope.excelDownloadInfo = function () {

    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles: true,  //20220427 false 엑섹속도 cell 스타일 확인중
        includeColumns: function (column) {
          return column.visible;
        }
      }, messages["dayClose.dayCloseKwu"] + '_'+ getCurDateTime() +'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };
}]);
