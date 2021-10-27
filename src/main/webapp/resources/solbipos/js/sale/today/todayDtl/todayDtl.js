/****************************************************************
 *
 * 파일명 : todayDtl.js
 * 설  명 : 당일매출현황 > 당일매출상세 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2000.00.00           1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 당일매출 집계 그리드 controller */
app.controller('todayDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('todayDtlCtrl', $scope, $http, true));

  $scope.srchTodayDtlStartDate = wcombo.genDateVal("#srchTodayDtlStartDate", gvStartDate);

  // 그리드 매출구분
  $scope.saleYnMap = new wijmo.grid.DataMap([
    {id: "Y", name: messages["todayDtl.saleY"]},
    {id: "N", name: messages["todayDtl.saleN"]}
  ], 'id', 'name');

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    $scope.getStorePosList();

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("todayDtlCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "saleYn") { // 구분
          var item = s.rows[e.row].dataItem;

          // 구분이 반품이면 글씨색을 red 로 변경한다.
          if (item.saleYn === 'Y') {
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          } else if (item.saleYn === 'N') {
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'red');
          }
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
        if (col.binding === "saleYn") { // 전표번호
          var params       = {};
          params.saleYn    = selectedRow.saleYn;
          params.startDate = $scope.searchedStartDate;
          params.storeCd   = $scope.searchedStoreCd;
          params.posNo     = $scope.searchedPosNo;
          $scope._broadcast('todayDtlDetailCtrl', params);
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

    // 첫째줄 헤더 생성
    var dataItem         = {};
    dataItem.saleYn      = messages["todayDtl.saleYn"];
    dataItem.totSaleAmt  = messages["todayDtl.totSaleAmt"];
    dataItem.totDcAmt    = messages["todayDtl.totDcAmt"];
    dataItem.realSaleAmt = messages["todayDtl.realSaleAmt"];
    dataItem.gaAmt       = messages["todayDtl.gaAmt"];
    dataItem.vatAmt      = messages["todayDtl.vatAmt"];
    dataItem.totTipAmt   = messages["todayDtl.totTipAmt"];
    dataItem.totEtcAmt   = messages["todayDtl.totEtcAmt"];
    dataItem.billCnt     = messages["todayDtl.billCnt"];
    dataItem.billUprc    = messages["todayDtl.billUprc"];
    dataItem.totPayAmt   = messages["todayDtl.payMethod"];

    // 결제수단 헤더머지 컬럼 생성
    for (var i = 0; i < arrPayCol.length; i++) {
      dataItem['pay' + arrPayCol[i]] = messages["todayDtl.payMethod"];
    }
    // 할인구분 헤더머지 컬럼 생성
    for (var i = 0; i < arrDcCol.length; i++) {
      dataItem['dc' + arrDcCol[i]] = messages["todayDtl.dcInfo"];
    }

    dataItem.totGuestCnt = messages["todayDtl.totGuestCnt"];

    // 객수 헤더머지 컬럼 생성
    for (var i = 0; i < arrGuestCol.length; i++) {
      dataItem['guest' + arrGuestCol[i]] = messages["todayDtl.guestCnt"];
    }

    dataItem.guestUprc               = messages["todayDtl.guestUprc"];
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
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("todayDtlCtrl", function (event, data) {
    $scope.searchTodayDtlList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 당일매출 집계내역 리스트 조회
  $scope.searchTodayDtlList = function () {
    if ($("#todayDtlSelectStoreCd").val() === '') {
      $scope._popMsg(messages["todayDtl.require.selectStore"]); // 매장을 선택해주세요.
      return false;
    }
    $scope.searchedStartDate = wijmo.Globalize.format($scope.srchTodayDtlStartDate.value, 'yyyyMMdd');
    $scope.searchedStoreCd   = $("#todayDtlSelectStoreCd").val();
    $scope.searchedPosNo     = $scope.posNo;

    // 파라미터
    var params       = {};
    params.startDate = $scope.searchedStartDate;
    params.storeCd   = $scope.searchedStoreCd;
    params.posNo     = $scope.posNo;
    params.payCol    = payCol;
    params.dcCol     = dcCol;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/today/todayDtl/todayDtl/list.sb", params, function () {
      var params       = {};
      params.saleYn    = '';
      params.startDate = $scope.searchedStartDate;
      params.storeCd   = $scope.searchedStoreCd;
      params.posNo     = $scope.searchedPosNo;
      $scope._broadcast('todayDtlDetailCtrl', params);

      // <-- 그리드 visible -->
      // 선택한 테이블에 따른 리스트 항목 visible
      var grid = wijmo.Control.getControl("#wjGridTodayDtlList");
      var columns = grid.columns;

      // 컬럼 총갯수
      var columnsCnt = 11 + 17 + 11 + 7;

      // 합계가 0이면 해당 컬럼 숨기기
      for (var j = 0; j < columnsCnt; j++) {
        if(columns[j].binding == "guest01" || columns[j].binding == "guest02" || columns[j].binding == "guest03" || columns[j].binding == "guest04" || columns[j].binding == "guest05" || columns[j].binding == "guest06") {
          // 합계행 값 가져오기
          if($scope.flex.columnFooters.getCellData(0, j, true) == 0) {
            columns[j].visible = false;
          } else {
            columns[j].visible = true;
          }
        }
      }
      // <-- //그리드 visible -->
    });
  };


  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.todayDtlSelectStoreShow = function () {
    $scope._broadcast('todayDtlSelectStoreCtrl');
  };


  // 매장의 POS 리스트 조회
  $scope.getStorePosList = function () {
    var url             = '/sale/today/todayDtl/todayDtl/storePosList.sb';
    var comboParams     = {};
    comboParams.storeCd = $("#todayDtlSelectStoreCd").val();
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
    $scope._queryCombo("combo", "srchPosNo", null, url, comboParams, "A", null); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.
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

  // 매출 엑셀 다운로드
  $scope.excelDownloadPeriodSale = function () {
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
      },
          messages["todayGnrlz.todayGnrlz"] + '(' + messages["todayDtl.todaySaleDtl"] + ')_' + getCurDateTime() +'.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
    }, 10);
  };

}]);


/** 당일매출 상세내역 그리드 controller */
app.controller('todayDtlDetailCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('todayDtlDetailCtrl', $scope, $http, true));

  // 그리드 전표구분
  $scope.saleYnMap = new wijmo.grid.DataMap([
    {id: "Y", name: messages["todayDtl.dtl.saleY"]},
    {id: "N", name: messages["todayDtl.dtl.saleN"]}
  ], 'id', 'name');

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("todayDtlDetailCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "billNo") { // 영수증번호
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }
        // if (col.binding === "saleYn") { // 구분
        //   var item = s.rows[e.row].dataItem;
        //
        //   // 구분이 반품이면 글씨색을 red 로 변경한다.
        //   if (item.saleYn === 'Y') {
        //     wijmo.addClass(e.cell, 'wijLink');
        //     wijmo.addClass(e.cell, 'wj-custom-readonly');
        //   } else if (item.saleYn === 'N') {
        //     wijmo.addClass(e.cell, 'wijLink');
        //     wijmo.addClass(e.cell, 'red');
        //   }
        // }

        // 결제수단
        for (var i = 0; i < payColList.length; i++) {
          if (col.binding === ("pay" + payColList[i].payCd)) {
            var item = s.rows[e.row].dataItem;

            // 값이 있으면 링크 효과
            if (nvl(item[("pay" + payColList[i].payCd)], '') !== '') {
              wijmo.addClass(e.cell, 'wijLink');
              wijmo.addClass(e.cell, 'wj-custom-readonly');
            }
          }
        }

        // 할인
        for (var i = 0; i < dcColList.length; i++) {
          if (col.binding === ("dc" + dcColList[i].dcCd)) {
            var item = s.rows[e.row].dataItem;

            // 07:포장할인, 08:현장할인 이 아닌 경우 링크효과
            if (dcColList[i].dcCd !== '07' && dcColList[i].dcCd !== '08') {
              // 값이 있으면 링크 효과
              if (nvl(item[("dc" + dcColList[i].dcCd)], '') !== '') {
                wijmo.addClass(e.cell, 'wijLink');
                wijmo.addClass(e.cell, 'wj-custom-readonly');
              }
            }
          }
        }

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        } else if (col.format === "dateTime") {
          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
        } else if (col.format === "time") {
          e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
        }
      }
    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var scope = agrid.getScope('todayDtlCtrl');
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        var params      = {};
        params.storeCd  = $scope.storeCd;
        params.saleDate = scope.searchedStartDate;
        params.posNo    = selectedRow.posNo;
        params.billNo   = selectedRow.billNo;
        params.saleYn   = selectedRow.saleYn;

        if (col.binding === "billNo") { // 영수증번호 클릭
          $scope._broadcast('billInfoCtrl', params);
        }
        // 결제수단
        for (var i = 0; i < payColList.length; i++) {
          if (col.binding === ("pay" + payColList[i].payCd)) {
            // var item = s.rows[e.row].dataItem;

            // 값이 있으면 링크
            if (nvl(selectedRow[("pay" + payColList[i].payCd)], '') !== '') {
              $scope._broadcast(payColList[i].payMethod.toLowerCase().replaceAll('_','') + 'Ctrl', params);
            }
          }
        }

        // 할인
        for (var i = 0; i < dcColList.length; i++) {
          if (col.binding === ("dc" + dcColList[i].dcCd)) {
            // var item = s.rows[e.row].dataItem;

            // 07:포장할인, 08:현장할인이 아닌 경우
            if (dcColList[i].dcCd !== '07' && dcColList[i].dcCd !== '08') {
              // 값이 있으면 링크
              if (nvl(selectedRow[("dc" + dcColList[i].dcCd)], '') !== '') {
                params.dcCd = dcColList[i].dcCd;
                $scope._broadcast(dcColList[i].dcMethod.toLowerCase() + 'DcCtrl', params);
              }
            }
          }
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
    // 첫째줄 헤더 생성
    var dataItem         = {};
    dataItem.posNo       = messages["todayDtl.dtl.posNo"];
    dataItem.billNo      = messages["todayDtl.dtl.billNo"];
    dataItem.billDt      = messages["todayDtl.dtl.billDt"];
    dataItem.saleYn      = messages["todayDtl.dtl.saleYn"];
    dataItem.totSaleAmt  = messages["todayDtl.dtl.totSaleAmt"];
    dataItem.totDcAmt    = messages["todayDtl.dtl.totDcAmt"];
    dataItem.realSaleAmt = messages["todayDtl.dtl.realSaleAmt"];
    dataItem.gaAmt       = messages["todayDtl.dtl.gaAmt"];
    dataItem.vatAmt      = messages["todayDtl.dtl.vatAmt"];
    dataItem.totTipAmt   = messages["todayDtl.dtl.totTipAmt"];
    dataItem.totEtcAmt   = messages["todayDtl.dtl.totEtcAmt"];
    dataItem.membrNo     = messages["todayDtl.dtl.membrNo"];
    dataItem.membrNm     = messages["todayDtl.dtl.membrNm"];
    dataItem.tblNm       = messages["todayDtl.dtl.tblNm"];
    dataItem.totPayAmt   = messages["todayDtl.dtl.payMethod"];

    // 결제수단 헤더머지 컬럼 생성
    for (var i = 0; i < arrPayCol.length; i++) {
      dataItem['pay' + arrPayCol[i]] = messages["todayDtl.dtl.payMethod"];
    }
    // 할인구분 헤더머지 컬럼 생성
    for (var i = 0; i < arrDcCol.length; i++) {
      dataItem['dc' + arrDcCol[i]] = messages["todayDtl.dtl.dcInfo"];
    }

    // dataItem.firstOrderDt = messages["todayDtl.dtl.firstOrderDt"];
    dataItem.totGuestCnt = messages["todayDtl.dtl.totGuestCnt"];

    // 객수 헤더머지 컬럼 생성
    for (var i = 0; i < arrGuestCol.length; i++) {
      dataItem['guest' + arrGuestCol[i]] = messages["todayDtl.dtl.guestCnt"];
    }

    dataItem.guestUprc               = messages["todayDtl.dtl.guestUprc"];
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
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("todayDtlDetailCtrl", function (event, data) {
    $scope.startDate = data.startDate;
    $scope.storeCd   = data.storeCd;
    $scope.posNo     = data.posNo;
    $scope.saleYn    = nvl(data.saleYn, '');

    $scope.searchTodayDtlDetailList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 당일매출 상세내역 리스트 조회
  $scope.searchTodayDtlDetailList = function () {
    // 파라미터
    var params       = {};
    params.startDate = $scope.startDate;
    params.storeCd   = $scope.storeCd;
    params.saleYn    = $scope.saleYn;
    params.posNo     = $scope.posNo;
    params.payCol    = payCol;
    params.dcCol     = dcCol;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/sale/today/todayDtl/todayDtlDetail/list.sb", params, function () {
      // <-- 그리드 visible -->
      // 선택한 테이블에 따른 리스트 항목 visible
      var grid = wijmo.Control.getControl("#wjGridTodayDtlDetailList");
      var columns = grid.columns;

      // 컬럼 총갯수
      var columnsCnt = 15 + 17 + 11 + 7;

      // 합계가 0이면 해당 컬럼 숨기기
      for (var j = 0; j < columnsCnt; j++) {
        if(columns[j].binding == "guest01" || columns[j].binding == "guest02" || columns[j].binding == "guest03" || columns[j].binding == "guest04" || columns[j].binding == "guest05" || columns[j].binding == "guest06") {
          // 합계행 값 가져오기
          if($scope.flexDtl.columnFooters.getCellData(0, j, true) == 0) {
            columns[j].visible = false;
          } else {
            columns[j].visible = true;
          }
        }
      }
      // <-- //그리드 visible -->
    });
  };

  // 매출상세 엑셀 다운로드
  $scope.excelDownloadPeriodSaleDtl = function () {
    if ($scope.flexDtl.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flexDtl, {
        includeColumnHeaders: true,
        includeCellStyles: true,
        includeColumns: function (column) {
          return column.visible;
        }
      },
          messages["todayGnrlz.todayGnrlz"] + '(' + messages["todayDtl.todaySaleDtl"] + '_' + messages["todayDtl.saleDtl"] + ')_' + getCurDateTime() +'.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
    }, 10);
  };

}]);
