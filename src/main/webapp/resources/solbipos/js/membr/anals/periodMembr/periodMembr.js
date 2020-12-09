/****************************************************************
 *
 * 파일명 : periodMembr.js
 * 설  명 : 기간회원 구매내역 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.09.06     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  기간회원 구매내역 그리드 생성
 */
app.controller('periodMembrCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('periodMembrCtrl', $scope, $http, true));

  // 검색조건에 조회기간
  var startDate = wcombo.genDateVal("#startDate", gvStartDate);
  var endDate = wcombo.genDateVal("#endDate", gvEndDate);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // 합계
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    //그리드 링크설정
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "realSaleAmt" || col.binding === "membrNm") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];

        // 구매액 클릭시 상세정보 조회
        if (col.binding === "realSaleAmt") {
          var params = s.rows[ht.row].dataItem;
          params.gubun = "periodMembr";
          $scope.setSelectedStore(params);
          $scope.dayMembrPurchsViewLayer.show(true);
          event.preventDefault();
        }

        // 회원명 클릭시 상세정보 조회
        if (col.binding === "membrNm") {
          $scope.setSelectedStore(s.rows[ht.row].dataItem);
          $scope.dayMembrDetailViewLayer.show(true);
          event.preventDefault();
        }
      }
    });

    // <-- 그리드 헤더2줄 -->
    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem = {};
    dataItem.membrNo = messages["periodMembr.membrNo"];
    dataItem.membrNm = messages["periodMembr.membrNm"];
    dataItem.membrCardNo = messages["periodMembr.membrCardNo"];
    dataItem.saleCount = messages["periodMembr.saleCount"];
    dataItem.realSaleAmt = messages["periodMembr.realSaleAmt"];
    dataItem.dcAmt = messages["periodMembr.dcAmt"];
    dataItem.membrSavePoint = messages["periodMembr.point"];
    dataItem.membrUsePoint = messages["periodMembr.point"];
    dataItem.minRegDt = messages["periodMembr.date"];
    dataItem.maxRegDt = messages["periodMembr.date"];

    s.columnHeaders.rows[0].dataItem = dataItem;

    s.itemFormatter = function (panel, r, c, cell) {
      if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
        //align in center horizontally and vertically
        panel.rows[r].allowMerging = true;
        panel.columns[c].allowMerging = true;
        wijmo.setCss(cell, {
          display: 'table',
          tableLayout: 'fixed'
        });
        cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
        wijmo.setCss(cell.children[0], {
          display: 'table-cell',
          verticalAlign: 'middle',
          textAlign: 'center'
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
    // <-- //그리드 헤더2줄 -->
  };

  // <-- 검색 호출 -->
  $scope.$on("periodMembrCtrl", function (event, data) {
    $scope.searchPeriodMembr();
    event.preventDefault();
  });

  // 기간회원 구매내역 그리드 조회
  $scope.searchPeriodMembr = function () {

    var params = {};
    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
    params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간

    $scope._inquiryMain("/membr/anals/periodMembr/periodMembr/getPeriodMembrList.sb", params, function () {
    }, false);
  };
  // <-- //검색 호출 -->

  // 선택 매장
  $scope.selectedStore;
  $scope.setSelectedStore = function (store) {
    $scope.selectedStore = store;
  };

  $scope.getSelectedStore = function () {
    return $scope.selectedStore;
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {

    // 구매액 상세정보 팝업 핸들러 추가
    $scope.dayMembrPurchsViewLayer.shown.addHandler(function (s) {
      setTimeout(function () {
        $scope._broadcast('dayMembrPurchsCtrl', $scope.getSelectedStore());
      }, 50)
    });

    // 회원명 상세정보 팝업 핸들러 추가
    $scope.dayMembrDetailViewLayer.shown.addHandler(function (s) {
      setTimeout(function () {
        $scope._broadcast('dayMembrDetailCtrl', $scope.getSelectedStore());
      }, 50)
    });
  });

  // 엑셀 다운로드
  $scope.excelDownload = function () {

    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles: true,
        includeColumns: function (column) {
          return column.visible;
        }
      }, '회원관리_회원분석_기간회원 구매내역_' + getToday() + '.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

}]);