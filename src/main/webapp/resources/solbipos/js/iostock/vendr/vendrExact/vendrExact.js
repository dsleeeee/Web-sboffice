/**
 * get application
 */
var app = agrid.getApp();

/** 거래처정산 그리드 controller */
app.controller('vendrExactCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('vendrExactCtrl', $scope, $http, true));

  $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("vendrExactCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "prevUnExcclcTot" || col.binding === "totExcclcTot" || col.binding === "afterExcclcTot" || col.binding === "nowUnExcclcTot") {
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        } else if (col.format === "dateTime") {
          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
        }
      }
    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if (col.binding === "prevUnExcclcTot" || col.binding === "totExcclcTot" || col.binding === "afterExcclcTot" || col.binding === "nowUnExcclcTot") {
          var params       = {};
          params.vendrCd   = selectedRow.vendrCd;
          params.startDate = $scope.searchedStartDate;
          params.endDate   = $scope.searchedEndDate;
          params.colbindFg = col.binding;
          $scope._broadcast('vendrExactDtlCtrl', params);
        }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    s.columnHeaders.rows[0].dataItem = {
      vendrNm        : messages["vendrExact.vendrNm"],
      prevUnExcclcTot: messages["vendrExact.prevUnExcclcTot"],
      inExcclcTot    : messages["vendrExact.searchPeriod"],
      payExcclcTot   : messages["vendrExact.searchPeriod"],
      totExcclcTot   : messages["vendrExact.searchPeriod"],
      afterExcclcTot : messages["vendrExact.afterExcclcTot"],
      nowUnExcclcTot : messages["vendrExact.nowUnExcclcTot"],
      vendrCd        : messages["vendrExact.vendr"],
    };

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
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("vendrExactCtrl", function (event, data) {
    $scope.searchVendrExactList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 조회 버튼 클릭으로 조회
  $scope.fnSearch = function () {
    // 거래처별 정산 그리드 조회 후 상세내역 그리드 초기화
    var vendrExactDtlScope = agrid.getScope('vendrExactDtlCtrl');
    vendrExactDtlScope.dtlGridDefault();

    $scope.searchVendrExactList();
  };


  // 거래처정산 리스트 조회
  $scope.searchVendrExactList = function () {
    $scope.searchedStartDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    $scope.searchedEndDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');

    // 파라미터
    var params       = {};
    params.startDate = $scope.searchedStartDate;
    params.endDate   = $scope.searchedEndDate;
    params.vendrCd   = $("#vendrExactSelectVendrCd").val();

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/vendr/vendrExact/vendrExact/list.sb", params);
  };


  // 지급액 신규등록
  $scope.newVendrExactRegist = function () {
    var params = {};
    $scope._broadcast('vendrExactRegistCtrl', params);
  };


  // 거래처선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.vendrExactSelectVendrShow = function () {
    $scope._broadcast('vendrExactSelectVendrCtrl');
  };

}]);


/** 거래처정산 상세 그리드 controller */
app.controller('vendrExactDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('vendrExactDtlCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("vendrExactDtlCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col  = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (item.excclcFg === '2' && col.binding === 'payExcclcTot') { // 지급액
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        } else if (col.format === "dateTime") {
          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
        }
      }
    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if (selectedRow.excclcFg === '2' && col.binding === "payExcclcTot") { // 지급액 클릭
          var params        = {};
          params.vendrCd    = $scope.vendrCd;
          params.excclcDate = selectedRow.excclcDate;
          params.seqNo      = selectedRow.seqNo;
          $scope._broadcast('vendrExactRegistCtrl', params);
        }
      }
    });
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("vendrExactDtlCtrl", function (event, data) {
    $scope.vendrCd   = data.vendrCd;
    $scope.startDate = data.startDate;
    $scope.endDate   = data.endDate;
    $scope.colbindFg = data.colbindFg;

    $scope.searchVendrExactDtlList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 거래처정산 상세 리스트 조회
  $scope.searchVendrExactDtlList = function () {
    // 파라미터
    var params       = {};
    params.vendrCd   = $scope.vendrCd;
    params.startDate = $scope.startDate;
    params.endDate   = $scope.endDate;
    params.colbindFg = $scope.colbindFg;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/vendr/vendrExact/vendrExactDtl/list.sb", params);
  };


  // 상세 그리드 초기화
  $scope.dtlGridDefault = function () {
    $timeout(function () {
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;
      $scope.flex.refresh();
    }, 10);
  };

}]);
