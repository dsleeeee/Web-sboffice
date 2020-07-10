/****************************************************************
 *
 * 파일명 : dlvrInfo.js
 * 설  명 : 배달 내역JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.07.09     Joshua      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  일자별회원 구매내역 그리드 생성
 */
app.controller('dayDlvrCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dayDlvrCtrl', $scope, $http, true));
  /*
  // 접속사용자의 권한(H : 본사, S: 매장)
  $scope.orgnFg = gvOrgnFg;

  // 매장권한으로 로그인 한 경우, 본인매장만 내역 조회가능.
  if($scope.orgnFg === 'S') {
     $scope.storeCds = gvStoreCd;
  }
 */
  // 검색조건에 조회기간
  var startDate = wcombo.genDateVal("#startDate", gvStartDate);
  var endDate = wcombo.genDateVal("#endDate", gvEndDate);

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정

    // 합계
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // <-- 그리드 헤더2줄 -->
    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem = {};
    dataItem.nonDlvrSaleDate = messages["dayDlvr.saleDate"];
    dataItem.cntDlvrBillNo = messages["dayDlvr.dlvrSale"];
    dataItem.sumDlvrRealSaleAmt = messages["dayDlvr.dlvrSale"];
    dataItem.cntBillNo = messages["dayDlvr.nonDlvrSale"];
    dataItem.sumRealSaleAmt = messages["dayDlvr.nonDlvrSale"];

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
  $scope.$on("dayDlvrCtrl", function (event, data) {
    $scope.searchDlvrInfo();
    event.preventDefault();
  });

  // 일자별회원 구매내역 그리드 조회
  $scope.searchDlvrInfo = function () {

    var params = {};
    params.listScale = $scope.listScale;
    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
    params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간

    $scope._inquiryMain("/dlvr/manage/anals/dayDlvr/getDayDlvrList.sb", params, function () {
    }, false);

    //
    // // ajax 통신 설정
    // $http({
    //   method: 'POST', //방식
    //   url: '/dlvr/manage/anals/dayDlvr/getDayDlvrList.sb', /* 통신할 URL */
    //   params: params, /* 파라메터로 보낼 데이터 */
    //   headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    // }).then(function successCallback(response) {
    //   // 로딩바 hide
    //   let isView = true
    //   $scope.$broadcast('loadingPopupInactive');
    //   if ($scope._httpStatusCheck(response, true)) {
    //     // this callback will be called asynchronously
    //     // when the response is available
    //     var list = response.data.data.list;
    //     if (list.length === undefined || list.length === 0) {
    //       $scope.data = new wijmo.collections.CollectionView([]);
    //       if (isView && response.data.message) {
    //         $scope._popMsg(response.data.message);
    //       }
    //       return false;
    //     }
    //
    //     $scope.data = new wijmo.collections.CollectionView(list);
    //     data.trackChanges = true;
    //
    //     // 페이징 처리
    //     if ($scope._getPagingInfo('curr') > 0) {
    //       params['curr'] = $scope._getPagingInfo('curr');
    //     } else {
    //       params['curr'] = 1;
    //     }
    //     // 가상로그인 대응한 session id 설정
    //     if (document.getElementsByName('sessionId')[0]) {
    //       params['sid'] = document.getElementsByName('sessionId')[0].value;
    //     }
    //
    //     // 페이징 처리
    //     if (response.data.data.page && response.data.data.page.curr) {
    //       var pagingInfo = response.data.data.page;
    //       $scope._setPagingInfo('ctrlName', $scope.name);
    //       $scope._setPagingInfo('pageScale', pagingInfo.pageScale);
    //       $scope._setPagingInfo('curr', pagingInfo.curr);
    //       $scope._setPagingInfo('totCnt', pagingInfo.totCnt);
    //       $scope._setPagingInfo('totalPage', pagingInfo.totalPage);
    //
    //       $scope._broadcast('drawPager');
    //     }
    //   }
    // }, function errorCallback(response) {
    //   // 로딩바 hide
    //   $scope.$broadcast('loadingPopupInactive');
    //   // called asynchronously if an error occurs
    //   // or server returns response with an error status.
    //   if (response.data.message) {
    //     $scope._popMsg(response.data.message);
    //   } else {
    //     $scope._popMsg(messages['cmm.error']);
    //   }
    //   return false;
    // }).then(function () {
    //   // 'complete' code here
    //   if (typeof callback === 'function') {
    //     setTimeout(function () {
    //       callback();
    //     }, 10);
    //   }
    // });
  };

  $scope.groupBy = (data, key) => {
    return data.reduce(function(prev, cur) {
      (prev[cur[key]] = prev[cur[key]] || []).push(cur);
      return prev;
    }, {});
  };

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
      }, '일자별 배달내역_' + getToday() + '.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

}]);