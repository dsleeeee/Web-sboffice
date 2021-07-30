/****************************************************************
 *
 * 파일명 : bilInfo.js
 * 설  명 : 매출상세정보(공통팝업-당일매출상세/반품현황(영수증별상세)에서 영수증번호클릭시 뜨는 팝업) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.02.01                1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 영수증 상세 내역 controller */
app.controller('billInfoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('billInfoCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
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
        // if (col.binding === "saleYn") { // 전표번호
        //   var params       = {};
        //   params.saleYn    = selectedRow.saleYn;
        //   params.startDate = $scope.searchedStartDate;
        //   params.storeCd   = $scope.searchedStoreCd;
        //   $scope._broadcast('todayDtlDetailCtrl', params);
        // }
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
    dataItem.prodCd      = messages["billInfo.prodCd"];
    dataItem.prodNm      = messages["billInfo.prodNm"];
    dataItem.saleQty     = messages["billInfo.saleQty"];
    dataItem.saleUprc    = messages["billInfo.saleUprc"];
    dataItem.saleAmt     = messages["billInfo.saleInfo"];
    dataItem.dcAmt       = messages["billInfo.saleInfo"];
    dataItem.realSaleAmt = messages["billInfo.saleInfo"];
    dataItem.gaAmt       = messages["billInfo.saleInfo"];
    dataItem.vatAmt      = messages["billInfo.saleInfo"];
    // 할인구분 헤더머지 컬럼 생성
    for (var i = 0; i < arrDcCol.length; i++) {
      dataItem['dc' + arrDcCol[i]] = messages["billInfo.dcInfo"];
    }

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
  $scope.$on("billInfoCtrl", function (event, data) {
    $scope.storeCd  = data.storeCd;
    $scope.saleDate = data.saleDate;
    $scope.posNo    = data.posNo;
    $scope.billNo   = data.billNo;
    $scope.saleYn   = data.saleYn;

    $scope.wjBillInfoLayer.show(true);

    $scope.getBillInfo();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 영수증 종합내역 조회
  $scope.getBillInfo = function () {
    // 로딩바 show
    $scope.$broadcast('loadingPopupActive');

    var params      = {};
    params.hqOfficeCd = hqOfficeCd;
    params.storeCd  = $scope.storeCd;
    params.saleDate = $scope.saleDate;
    params.posNo    = $scope.posNo;
    params.billNo   = $scope.billNo;

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/sale/cmmSalePopup/billInfo/billInfo/billInfo.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          var data = response.data.data;

          $("#billSubTitle").html('매장 : '+'['+data.storeCd+']'+ data.storeNm+'  |  매출일자 : '+getFormatDate($scope.saleDate)+'  |  포스번호 : '+$scope.posNo+'  |  영수번호 : '+$scope.billNo);

          data.totSaleAmt   = addComma(data.totSaleAmt);
          data.totDcAmt     = addComma(data.totDcAmt);
          data.realSaleAmt  = addComma(data.realSaleAmt);
          data.netSaleAmt   = addComma(data.netSaleAmt);
          data.noTaxSaleAmt = addComma(data.noTaxSaleAmt);
          data.taxSaleAmt   = addComma(data.taxSaleAmt);
          data.vatAmt       = addComma(data.vatAmt);
          data.totTipAmt    = addComma(data.totTipAmt);
          // 반품영수증인 경우 원거래 영수증값 세팅
          if ($scope.saleYn === 'N') {
            storeCdSize = data.orgBillNo.length - 14; // 매장코드 자리수 7자리 고정 >> 7~20 가변으로 수정됨에 따라 변경
            $scope.orgStoreCd  = data.orgBillNo.substr(0, storeCdSize);
            $scope.orgSaleDate = data.orgBillNo.substr(-14, 8);
            $scope.orgPosNo    = data.orgBillNo.substr(-6, 2);
            $scope.orgBillNo   = data.orgBillNo.substr(-4);
          }

          $scope.billInfo = data; // view 종합내역에 조회한 값 세팅
          $scope.getBillPayInfo(); // 결제내역 조회
        }
        $scope.$broadcast('loadingPopupInactive');
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      // 로딩바 hide
      $scope.$broadcast('loadingPopupInactive');
      if (response.data.message) {
        $scope._popMsg(response.data.message);
      } else {
        $scope._popMsg(messages['cmm.error']);
      }
      return false;
    }).then(function () {
      // "complete" code here
    });
  };


  // 영수증 결제내역 조회
  $scope.getBillPayInfo = function () {

    var params      = {};
    params.hqOfficeCd = hqOfficeCd;
    params.storeCd  = $scope.storeCd;
    params.saleDate = $scope.saleDate;
    params.posNo    = $scope.posNo;
    params.billNo   = $scope.billNo;
    params.payCol   = payCol;

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/sale/cmmSalePopup/billInfo/billInfo/billPayInfo.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          var data = response.data.data;

          // 조회한 결제수단 data에 addComma 추가
          for (var i = 0; i < arrPayCol.length; i++) {
            data['pay' + arrPayCol[i]] = (nvl(data['pay' + arrPayCol[i]], '') !== '' ? addComma(data['pay' + arrPayCol[i]]) : '');
          }

          $scope.billPayInfo = data; // view 결제내역에 조회한 값 세팅
          $scope.getBillGuestInfo(); // 방문인원 조회
        }
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      // 로딩바 hide
      $scope.$broadcast('loadingPopupInactive');
      if (response.data.message) {
        $scope._popMsg(response.data.message);
      } else {
        $scope._popMsg(messages['cmm.error']);
      }
      return false;
    }).then(function () {
      // "complete" code here
    });
  };


  // 영수증 방문인원 조회
  $scope.getBillGuestInfo = function () {

    var params      = {};
    params.hqOfficeCd = hqOfficeCd;
    params.storeCd  = $scope.storeCd;
    params.saleDate = $scope.saleDate;
    params.posNo    = $scope.posNo;
    params.billNo   = $scope.billNo;

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/sale/cmmSalePopup/billInfo/billInfo/billGuestInfo.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          var data = response.data.data;

          data.guest01 = (nvl(data.guest01, '') !== '' ? addComma(data.guest01) : '');
          data.guest02 = (nvl(data.guest02, '') !== '' ? addComma(data.guest02) : '');
          data.guest03 = (nvl(data.guest03, '') !== '' ? addComma(data.guest03) : '');
          data.guest04 = (nvl(data.guest04, '') !== '' ? addComma(data.guest04) : '');

          $scope.billGuestInfo = data;
          $scope.$broadcast('loadingPopupInactive');
        }

        // 영수증 상품내역 리스트 조회
        $scope.searchBillInfoProdList();

      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      // 로딩바 hide
      $scope.$broadcast('loadingPopupInactive');
      if (response.data.message) {
        $scope._popMsg(response.data.message);
      } else {
        $scope._popMsg(messages['cmm.error']);
      }
      return false;
    }).then(function () {
      // "complete" code here
    });
  };


  // 영수증 상품내역 리스트 조회
  $scope.searchBillInfoProdList = function () {
    // 파라미터
    var params       = {};
    params.hqOfficeCd = hqOfficeCd;
    params.storeCd   = $scope.storeCd;
    params.saleDate  = $scope.saleDate;
    params.posNo     = $scope.posNo;
    params.billNo    = $scope.billNo;
    params.dcCol     = dcCol;
    params.orgBillNo = nvl($scope.orgBillNo, '');

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/cmmSalePopup/billInfo/billInfo/billProdList.sb", params, function () {
      // 현재 영수증이 반품 영수증인 경우 원거래 영수증 조회
      if (nvl($scope.saleYn, '') === 'N') {
        var params       = {};
        params.storeCd   = $scope.orgStoreCd;
        params.saleDate  = $scope.orgSaleDate;
        params.posNo     = $scope.orgPosNo;
        params.billNo    = $scope.orgBillNo;
        $scope._broadcast('orgBillInfoCtrl', params);
      }
    });
  };


  // 팝업 닫기
  $scope.close = function(){

    // 초기화
    var oScope = agrid.getScope("billInfoCtrl");
    $("#billSubTitle").val("");
    $scope.billInfo = "";
    $scope.billPayInfo = "";
    $scope.billGuestInfo = "";
    oScope._gridDataInit();

    if($scope.saleYn === "N") {
      var scope = agrid.getScope("orgBillInfoCtrl");
      $("#orgBillSubTitle").val("");
      scope.orgBillInfo = "";
      scope.orgBillPayInfo = "";
      scope.orgBillGuestInfo = "";
      scope._gridDataInit();
    }

    $scope.wjBillInfoLayer.hide();
  };

}]);


/** 원거래영수증 상세 내역 controller */
app.controller('orgBillInfoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('orgBillInfoCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];

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
    dataItem.prodCd      = messages["billInfo.prodCd"];
    dataItem.prodNm      = messages["billInfo.prodNm"];
    dataItem.saleQty     = messages["billInfo.saleQty"];
    dataItem.saleUprc    = messages["billInfo.saleUprc"];
    dataItem.saleAmt     = messages["billInfo.saleInfo"];
    dataItem.dcAmt       = messages["billInfo.saleInfo"];
    dataItem.realSaleAmt = messages["billInfo.saleInfo"];
    dataItem.gaAmt       = messages["billInfo.saleInfo"];
    dataItem.vatAmt      = messages["billInfo.saleInfo"];
    // 할인구분 헤더머지 컬럼 생성
    for (var i = 0; i < arrDcCol.length; i++) {
      dataItem['dc' + arrDcCol[i]] = messages["billInfo.dcInfo"];
    }

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
  $scope.$on("orgBillInfoCtrl", function (event, data) {
    $scope.storeCd   = data.storeCd;
    $scope.saleDate  = data.saleDate;
    $scope.posNo     = data.posNo;
    $scope.billNo    = data.billNo;

    $("#orgBillSubTitle").html(messages['billInfo.orgBill']+'  |  원거래 매출일자 : '+getFormatDate($scope.saleDate)+'  |  원거래 포스번호 : '+$scope.posNo+'  |  원거래 영수번호 : '+$scope.billNo);

    $scope.getOrgBillInfo();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 원거래 영수증 종합내역 조회
  $scope.getOrgBillInfo = function () {
    // 로딩바 show
    $scope.$broadcast('loadingPopupActive');

    var params      = {};
    params.hqOfficeCd = hqOfficeCd;
    params.storeCd  = $scope.storeCd;
    params.saleDate = $scope.saleDate;
    params.posNo    = $scope.posNo;
    params.billNo   = $scope.billNo;

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/sale/cmmSalePopup/billInfo/billInfo/billInfo.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if(response.data.data == null){
          $scope._gridDataInit();   // 조회결과가 null이면 그리드 초기화(위에 그리드 정보를 가지고 있음)
        } else if (!$.isEmptyObject(response.data.data)) {
          var data = response.data.data;

          data.totSaleAmt   = addComma(data.totSaleAmt);
          data.totDcAmt     = addComma(data.totDcAmt);
          data.realSaleAmt  = addComma(data.realSaleAmt);
          data.netSaleAmt   = addComma(data.netSaleAmt);
          data.noTaxSaleAmt = addComma(data.noTaxSaleAmt);
          data.taxSaleAmt   = addComma(data.taxSaleAmt);
          data.vatAmt       = addComma(data.vatAmt);
          data.totTipAmt    = addComma(data.totTipAmt);

          $scope.orgBillInfo = data; // view 종합내역에 조회한 값 세팅
          $scope.getOrgBillPayInfo(); // 결제내역 조회
        }
        $scope.$broadcast('loadingPopupInactive');
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      // 로딩바 hide
      $scope.$broadcast('loadingPopupInactive');
      if (response.data.message) {
        $scope._popMsg(response.data.message);
      } else {
        $scope._popMsg(messages['cmm.error']);
      }
      return false;
    }).then(function () {
      // "complete" code here
    });
  };


  // 영수증 결제내역 조회
  $scope.getOrgBillPayInfo = function () {

    var params      = {};
    params.hqOfficeCd = hqOfficeCd;
    params.storeCd  = $scope.storeCd;
    params.saleDate = $scope.saleDate;
    params.posNo    = $scope.posNo;
    params.billNo   = $scope.billNo;
    params.payCol   = payCol;

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/sale/cmmSalePopup/billInfo/billInfo/billPayInfo.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          var data = response.data.data;

          // 조회한 결제수단 data에 addComma 추가
          for (var i = 0; i < arrPayCol.length; i++) {
            data['pay' + arrPayCol[i]] = (nvl(data['pay' + arrPayCol[i]], '') !== '' ? addComma(data['pay' + arrPayCol[i]]) : '');
          }

          $scope.orgBillPayInfo = data; // view 결제내역에 조회한 값 세팅
          $scope.getOrgBillGuestInfo(); // 방문인원 조회
        }
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      // 로딩바 hide
      $scope.$broadcast('loadingPopupInactive');
      if (response.data.message) {
        $scope._popMsg(response.data.message);
      } else {
        $scope._popMsg(messages['cmm.error']);
      }
      return false;
    }).then(function () {
      // "complete" code here
    });
  };

  // 영수증 방문인원 조회
  $scope.getOrgBillGuestInfo = function () {

    var params      = {};
    params.hqOfficeCd = hqOfficeCd;
    params.storeCd  = $scope.storeCd;
    params.saleDate = $scope.saleDate;
    params.posNo    = $scope.posNo;
    params.billNo   = $scope.billNo;

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : '/sale/cmmSalePopup/billInfo/billInfo/billGuestInfo.sb', /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data)) {
          var data = response.data.data;

          data.guest01 = (nvl(data.guest01, '') !== '' ? addComma(data.guest01) : '');
          data.guest02 = (nvl(data.guest02, '') !== '' ? addComma(data.guest02) : '');
          data.guest03 = (nvl(data.guest03, '') !== '' ? addComma(data.guest03) : '');
          data.guest04 = (nvl(data.guest04, '') !== '' ? addComma(data.guest04) : '');

          $scope.orgBillGuestInfo = data;
          $scope.$broadcast('loadingPopupInactive');

        }

        // 영수증 상품내역 리스트 조회
        $scope.searchOrgBillInfoProdList();
      }
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      // 로딩바 hide
      $scope.$broadcast('loadingPopupInactive');
      if (response.data.message) {
        $scope._popMsg(response.data.message);
      } else {
        $scope._popMsg(messages['cmm.error']);
      }
      return false;
    }).then(function () {
      // "complete" code here
    });
  };


  // 영수증 상품내역 리스트 조회
  $scope.searchOrgBillInfoProdList = function () {
    // 파라미터
    var params      = {};
    params.hqOfficeCd = hqOfficeCd;
    params.storeCd  = $scope.storeCd;
    params.saleDate = $scope.saleDate;
    params.posNo    = $scope.posNo;
    params.billNo   = $scope.billNo;
    params.dcCol    = dcCol;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/cmmSalePopup/billInfo/billInfo/billProdList.sb", params);
  };

}]);
