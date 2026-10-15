/****************************************************************
 *
 * 파일명 : promotionReportBenson.js
 * 설  명 : (벤슨) 프로모션정산 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.10.13     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 구분
var gubunComboData = [
  {"name":"입금예정일","value":"D"},
  {"name":"프로모션","value":"P"}
];

/**
 *  프로모션정산 그리드 생성
 */
app.controller('promotionReportBensonCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('promotionReportBensonCtrl', $scope, $http, true));

  var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  var endDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

  // 브랜드 콤보박스 셋팅
  $scope._setComboData("gubunCombo", gubunComboData); // 구분
  $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("promotionReportBensonCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "promotionCd" || col.binding === "promotionNm") {
          wijmo.addClass(e.cell, 'wijLink');
        }
        // else if(col.binding ==="realSaleQtyPromoCd"){ //판매량 팝업
        //   wijmo.addClass(e.cell, 'wijLink');
        // }
      }
    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
        var ht = s.hitTest(e);
        if (ht.cellType === wijmo.grid.CellType.Cell) {
            var col         = ht.panel.columns[ht.col];
            var selectedRow = s.rows[ht.row].dataItem;
            if (col.binding === "promotionCd" || col.binding === "promotionNm") {
                var params = {};
                params.promotionCd = selectedRow.promotionCd;
                params.startYmd = selectedRow.startYmd.replaceAll('-', '');
                params.endYmd = selectedRow.endYmd.replaceAll('-', '');
                params.storeCd = selectedRow.storeCd;
                $scope.promotionReportBensonDtlLayer.show(true);
                $scope._broadcast('promotionReportBensonDtlCtrl', params);
            }
            // else if (col.binding === "realSaleQtyPromoCd") { //판매량 팝업
            //   var params = {};
            //   params.promotionCd = selectedRow.promotionCd;
            //   params.promoType = selectedRow.promoType;
            //   $scope.promotionSelectProdBensonLayer.show(true);
            //   $scope._broadcast('promotionSelectProdBensonGridCtrl', params);
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
    var dataItem                  = {};
    dataItem.promotionNm          = messages["promotionReportBenson.promotionNm"];
    dataItem.promotionCd          = messages["promotionReportBenson.promotionCd"];
    dataItem.startYmd             = messages["promotionReportBenson.startYmd"];
    dataItem.endYmd               = messages["promotionReportBenson.endYmd"];
    dataItem.storeCd              = messages["promotionReportBenson.storeCd"];
    dataItem.storeNm              = messages["promotionReportBenson.storeNm"];
    dataItem.bizNo                = messages["promotionReportBenson.bizNo"];
    dataItem.hdRealSaleCntPromoCd = messages["promotionReportBenson.hdRealSaleCntPromoCd"];
    dataItem.dtRealSaleCntPromoCd = messages["promotionReportBenson.dtRealSaleCntPromoCd"];
    dataItem.realSaleQtyPromoCd   = messages["promotionReportBenson.realSaleQtyPromoCd"];
    dataItem.chargeDs             = messages["promotionReportBenson.chargeDs"];
    dataItem.hqChargeUprcMPromoCd      = messages["promotionReportBenson.chargeUprc2"];
    dataItem.msChargeUprcMPromoCd      = messages["promotionReportBenson.chargeUprc2"];
    dataItem.partnerChargeUprcMPromoCd = messages["promotionReportBenson.chargeUprc2"];
    dataItem.hqChargeUprcPromoCd       = messages["promotionReportBenson.totChargeUprc2"];
    dataItem.msChargeUprcPromoCd       = messages["promotionReportBenson.totChargeUprc2"];
    dataItem.partnerChargeUprcPromoCd  = messages["promotionReportBenson.totChargeUprc2"];
    dataItem.depositYmd           = messages["promotionReportBenson.depositYmd"];
    dataItem.remark               = messages["promotionReportBenson.remark"];

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
  $scope.$on("promotionReportBensonCtrl", function (event, data) {
    $scope.searchPromotionReportBensonList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 프로모션정산 리스트 조회
  $scope.searchPromotionReportBensonList = function () {

    var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
    var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
    var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

    // 시작일자가 종료일자보다 빠른지 확인
    if(startDt.getTime() > endDt.getTime()){
      $scope._popMsg(messages['cmm.dateChk.error']);
      return false;
    }
    // 조회일자 최대 3달(93일) 제한
    if (diffDay > 93) {
      $scope._popMsg(messages['cmm.dateOver.3month.error']);
      return false;
    }

    // 파라미터
    var params        = {};
    params.gubun = $scope.gubunCombo;
    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
    params.promotionCds   = $("#promotionReportBensonPromotionCd").val();
    params.storeCds   = $("#promotionReportBensonStoreCd").val();
    params.bizNo      = $scope.bizNo;
    params.storeHqBrandCd = $scope.storeHqBrandCd;
    // '전체' 일때
    if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
      var momsHqBrandCd = "";
      for(var i=0; i < momsHqBrandCdComboList.length; i++){
        if(momsHqBrandCdComboList[i].value !== null) {
          momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
        }
      }
      params.userBrands = momsHqBrandCd;
    }
    params.listScale=500;

    console.log(params);

    // 페이징 처리
    if ($scope._getPagingInfo('curr') > 0) {
      params['curr'] = $scope._getPagingInfo('curr');
    } else {
      params['curr'] = 1;
    }
    // 가상로그인 대응한 session id 설정
    if (document.getElementsByName('sessionId')[0]) {
      params['sid'] = document.getElementsByName('sessionId')[0].value;
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $.postJSON("/base/promotion/promotionReportBenson/promotionReportBenson/getPromotionReportBensonList.sb", params, function (response) {
      var grid = $scope.flex;
      grid.itemsSource = response.data.list;
      grid.itemsSource.trackChanges = true;

      var list = response.data.list;
      if (list.length === undefined || list.length === 0) {
        $scope.data = new wijmo.collections.CollectionView([]);
        if (true && response.message) {

          // 페이징 처리
          $scope._setPagingInfo('ctrlName', $scope.name);
          $scope._setPagingInfo('pageScale', 10);
          $scope._setPagingInfo('curr', 1);
          $scope._setPagingInfo('totCnt', 1);
          $scope._setPagingInfo('totalPage', 1);

          $scope._broadcast('drawPager');

          $scope._popMsg(response.message);
        }
        return false;
      }
      var data = new wijmo.collections.CollectionView(list);
      data.trackChanges = true;
      $scope.data = data;

      // 페이징 처리
      if (response.data.page && response.data.page.curr) {
        var pagingInfo = response.data.page;
        $scope._setPagingInfo('ctrlName', $scope.name);
        $scope._setPagingInfo('pageScale', pagingInfo.pageScale);
        $scope._setPagingInfo('curr', pagingInfo.curr);
        $scope._setPagingInfo('totCnt', pagingInfo.totCnt);
        $scope._setPagingInfo('totalPage', pagingInfo.totalPage);
        $scope._broadcast('drawPager');
      }
    });
  };

  // 프로모션 선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.promotionReportBensonPromotionShow = function () {
    $scope._broadcast('promotionReportBensonPromotionCtrl');
  };

  // 엑셀 다운로드
  $scope.excelDownload = function () {

    var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
    var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
    var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

    // 시작일자가 종료일자보다 빠른지 확인
    if(startDt.getTime() > endDt.getTime()){
      $scope._popMsg(messages['cmm.dateChk.error']);
      return false;
    }
    // 조회일자 최대 3달(93일) 제한
    if (diffDay > 93) {
      $scope._popMsg(messages['cmm.dateOver.3month.error']);
      return false;
    }

    // 파라미터
    var params       = {};
    params.gubun = $scope.gubunCombo;
    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
    params.promotionCds   = $("#promotionReportBensonPromotionCd").val();
    params.storeCds     = $("#promotionReportBensonStoreCd").val();
    params.bizNo      = $scope.bizNo;
    params.storeHqBrandCd = $scope.storeHqBrandCd;
    // '전체' 일때
    if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
      var momsHqBrandCd = "";
      for(var i=0; i < momsHqBrandCdComboList.length; i++){
        if(momsHqBrandCdComboList[i].value !== null) {
          momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
        }
      }
      params.userBrands = momsHqBrandCd;
    }

    console.log(params);

    $scope._broadcast('promotionReportBensonExcelCtrl', params);
  };

  $scope.changeGubun = function (s){
    if(s.selectedValue === 'D') {
      $("#depositYmd").show();
      $("#promotion").hide();
    } else {
      $("#depositYmd").hide();
      $("#promotion").show();
    }
  }
}]);


/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('promotionReportBensonExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('promotionReportBensonExcelCtrl', $scope, $http, false));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    // 첫째줄 헤더 생성
    var dataItem                  = {};
    dataItem.promotionNm          = messages["promotionReportBenson.promotionNm"];
    dataItem.promotionCd          = messages["promotionReportBenson.promotionCd"];
    dataItem.startYmd             = messages["promotionReportBenson.startYmd"];
    dataItem.endYmd               = messages["promotionReportBenson.endYmd"];
    dataItem.storeCd              = messages["promotionReportBenson.storeCd"];
    dataItem.storeNm              = messages["promotionReportBenson.storeNm"];
    dataItem.bizNo                = messages["promotionReportBenson.bizNo"];
    dataItem.hdRealSaleCntPromoCd = messages["promotionReportBenson.hdRealSaleCntPromoCd"];
    dataItem.dtRealSaleCntPromoCd = messages["promotionReportBenson.dtRealSaleCntPromoCd"];
    dataItem.realSaleQtyPromoCd   = messages["promotionReportBenson.realSaleQtyPromoCd"];
    dataItem.chargeDs             = messages["promotionReportBenson.chargeDs"];
    dataItem.hqChargeUprcMPromoCd      = messages["promotionReportBenson.chargeUprc2"];
    dataItem.msChargeUprcMPromoCd      = messages["promotionReportBenson.chargeUprc2"];
    dataItem.partnerChargeUprcMPromoCd = messages["promotionReportBenson.chargeUprc2"];
    dataItem.hqChargeUprcPromoCd       = messages["promotionReportBenson.totChargeUprc2"];
    dataItem.msChargeUprcPromoCd       = messages["promotionReportBenson.totChargeUprc2"];
    dataItem.partnerChargeUprcPromoCd  = messages["promotionReportBenson.totChargeUprc2"];
    dataItem.depositYmd           = messages["promotionReportBenson.depositYmd"];
    dataItem.remark               = messages["promotionReportBenson.remark"];

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
  $scope.$on("promotionReportBensonExcelCtrl", function (event, data) {

    $scope.searchExcelList(data);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 엑셀 리스트 조회
  $scope.searchExcelList = function (params) {

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $.postJSON("/base/promotion/promotionReportBenson/promotionReportBenson/getPromotionReportBensonExcelList.sb", params, function (response){
      var grid = $scope.excelFlex;
      grid.itemsSource = response.data.list;
      grid.itemsSource.trackChanges = true;

      if ($scope.excelFlex.rows.length <= 0) {
        $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
        return false;
      }
      var excelName;
      if(params.gubun === "D"){
        excelName = messages["promotionReportBenson.promotionReportBenson"] + '_' + params.startDate + '_' + params.endDate + '_';
      } else {
        excelName = messages["promotionReportBenson.promotionReportBenson"] + '_' ;
      }
      $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
      $timeout(function () {
        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
          includeColumnHeaders: true,
          includeCellStyles: true,
          includeColumns: function (column) {
            return column.visible;
          }
        },
            excelName + getCurDateTime() +'.xlsx', function () {
              $timeout(function () {
                $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
              }, 10);
            });
      }, 10);
    });

  };

}]);
