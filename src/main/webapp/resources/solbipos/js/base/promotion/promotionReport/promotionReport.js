/****************************************************************
 *
 * 파일명 : promotionReport.js
 * 설  명 : 프로모션정산 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.02.06     권지현      1.0
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
app.controller('promotionReportCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('promotionReportCtrl', $scope, $http, true));

  var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  var endDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

  // 브랜드 콤보박스 셋팅
  $scope._setComboData("gubunCombo", gubunComboData); // 구분
  $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드
  $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
  $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
  $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
  $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
  $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
  $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
  $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹
  $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList); // 매장그룹
  $scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList); // 매장그룹2
  $scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList); // 매장그룹3
  $scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList); // 매장그룹4
  $scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList); // 매장그룹5

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("promotionReportCtrl");

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
                $scope.promotionReportDtlLayer.show(true);
                $scope._broadcast('promotionReportDtlCtrl', params);
            }
            // else if (col.binding === "realSaleQtyPromoCd") { //판매량 팝업
            //   var params = {};
            //   params.promotionCd = selectedRow.promotionCd;
            //   params.promoType = selectedRow.promoType;
            //   $scope.promotionSelectProdLayer.show(true);
            //   $scope._broadcast('promotionSelectProdGridCtrl', params);
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
    dataItem.promotionNm          = messages["promotionReport.promotionNm"];
    dataItem.promotionCd          = messages["promotionReport.promotionCd"];
    dataItem.startYmd             = messages["promotionReport.startYmd"];
    dataItem.endYmd               = messages["promotionReport.endYmd"];
    dataItem.storeCd              = messages["promotionReport.storeCd"];
    dataItem.storeNm              = messages["promotionReport.storeNm"];
    dataItem.bizNo                = messages["promotionReport.bizNo"];
    /*dataItem.hdRealSaleCnt        = messages["promotionReport.hdRealSaleCnt"];
    dataItem.dtRealSaleCnt        = messages["promotionReport.dtRealSaleCnt"];
    dataItem.realSaleQty          = messages["promotionReport.realSaleQty"];*/
    dataItem.hdRealSaleCntPromoCd = messages["promotionReport.hdRealSaleCntPromoCd"];
    dataItem.dtRealSaleCntPromoCd = messages["promotionReport.dtRealSaleCntPromoCd"];
    dataItem.realSaleQtyPromoCd   = messages["promotionReport.realSaleQtyPromoCd"];
    dataItem.chargeDs             = messages["promotionReport.chargeDs"];
    dataItem.hqChargeUprcMPromoCd      = messages["promotionReport.chargeUprc2"];
    dataItem.msChargeUprcMPromoCd      = messages["promotionReport.chargeUprc2"];
    dataItem.partnerChargeUprcMPromoCd = messages["promotionReport.chargeUprc2"];
    dataItem.hqChargeUprcPromoCd       = messages["promotionReport.totChargeUprc2"];
    dataItem.msChargeUprcPromoCd       = messages["promotionReport.totChargeUprc2"];
    dataItem.partnerChargeUprcPromoCd  = messages["promotionReport.totChargeUprc2"];
    /*dataItem.hqChargeUprcM         = messages["promotionReport.chargeUprcM"];
    dataItem.msChargeUprcM         = messages["promotionReport.chargeUprcM"];
    dataItem.partnerChargeUprcM    = messages["promotionReport.chargeUprcM"];
    dataItem.totHqChargeUprcM      = messages["promotionReport.totChargeUprcM"];
    dataItem.totMsChargeUprcM      = messages["promotionReport.totChargeUprcM"];
    dataItem.totPartnerChargeUprcM = messages["promotionReport.totChargeUprcM"];
    dataItem.hqChargeUprc         = messages["promotionReport.chargeUprc"];
    dataItem.msChargeUprc         = messages["promotionReport.chargeUprc"];
    dataItem.partnerChargeUprc    = messages["promotionReport.chargeUprc"];
    dataItem.totHqChargeUprc      = messages["promotionReport.totChargeUprc"];
    dataItem.totMsChargeUprc      = messages["promotionReport.totChargeUprc"];
    dataItem.totPartnerChargeUprc = messages["promotionReport.totChargeUprc"];*/
    dataItem.depositYmd           = messages["promotionReport.depositYmd"];
    dataItem.remark               = messages["promotionReport.remark"];

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
  $scope.$on("promotionReportCtrl", function (event, data) {
    $scope.searchPromotionReportList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 영수증별매출상세현황 리스트 조회
  $scope.searchPromotionReportList = function () {

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
    params.promotionCds   = $("#promotionReportPromotionCd").val();
    params.storeCds   = $("#promotionReportStoreCd").val();
    params.bizNo      = $scope.bizNo;
    params.momsTeam   = $scope.momsTeam;
    params.momsAcShop = $scope.momsAcShop;
    params.momsAreaFg = $scope.momsAreaFg;
    params.momsCommercial = $scope.momsCommercial;
    params.momsShopType = $scope.momsShopType;
    params.momsStoreManageType = $scope.momsStoreManageType;
    params.branchCd = $scope.branchCd;
    params.storeHqBrandCd = $scope.storeHqBrandCd;
    // '전체' 일때
    if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
      var momsHqBrandCd = "";
      for(var i=0; i < momsHqBrandCdComboList.length; i++){
        if(momsHqBrandCdComboList[i].value !== null) {
          momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
        }
      }
      params.userBrands = momsHqBrandCd;
    }
    params.momsStoreFg01 = $scope.momsStoreFg01;
    params.momsStoreFg02 = $scope.momsStoreFg02;
    params.momsStoreFg03 = $scope.momsStoreFg03;
    params.momsStoreFg04 = $scope.momsStoreFg04;
    params.momsStoreFg05 = $scope.momsStoreFg05;
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
    $.postJSON("/base/promotion/promotionReport/promotionReport/getPromotionReportList.sb", params, function (response) {
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

  // 확장조회 숨김/보임
  $scope.searchAddShowChange = function(){
    if( $("#tblSearchAddShow").css("display") === 'none') {
      $("#tblSearchAddShow").show();
    } else {
      $("#tblSearchAddShow").hide();
    }
  };

  // 프로모션 선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.promotionReportPromotionShow = function () {
    $scope._broadcast('promotionReportPromotionCtrl');
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
    params.promotionCds   = $("#promotionReportPromotionCd").val();
    params.storeCds     = $("#promotionReportStoreCd").val();
    params.momsTeam = $scope.momsTeam;
    params.momsAcShop = $scope.momsAcShop;
    params.momsAreaFg = $scope.momsAreaFg;
    params.momsCommercial = $scope.momsCommercial;
    params.momsShopType = $scope.momsShopType;
    params.momsStoreManageType = $scope.momsStoreManageType;
    params.branchCd = $scope.branchCd;
    params.storeHqBrandCd = $scope.storeHqBrandCd;
    // '전체' 일때
    if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
      var momsHqBrandCd = "";
      for(var i=0; i < momsHqBrandCdComboList.length; i++){
        if(momsHqBrandCdComboList[i].value !== null) {
          momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
        }
      }
      params.userBrands = momsHqBrandCd;
    }
    params.momsStoreFg01 = $scope.momsStoreFg01;
    params.momsStoreFg02 = $scope.momsStoreFg02;
    params.momsStoreFg03 = $scope.momsStoreFg03;
    params.momsStoreFg04 = $scope.momsStoreFg04;
    params.momsStoreFg05 = $scope.momsStoreFg05;

    console.log(params);

    $scope._broadcast('promotionReportExcelCtrl', params);
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
app.controller('promotionReportExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('promotionReportExcelCtrl', $scope, $http, false));

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
    dataItem.promotionNm          = messages["promotionReport.promotionNm"];
    dataItem.promotionCd          = messages["promotionReport.promotionCd"];
    dataItem.startYmd             = messages["promotionReport.startYmd"];
    dataItem.endYmd               = messages["promotionReport.endYmd"];
    dataItem.storeCd              = messages["promotionReport.storeCd"];
    dataItem.storeNm              = messages["promotionReport.storeNm"];
    dataItem.bizNo                = messages["promotionReport.bizNo"];
    /*dataItem.hdRealSaleCnt        = messages["promotionReport.hdRealSaleCnt"];
    dataItem.dtRealSaleCnt        = messages["promotionReport.dtRealSaleCnt"];
    dataItem.realSaleQty          = messages["promotionReport.realSaleQty"];*/
    dataItem.hdRealSaleCntPromoCd = messages["promotionReport.hdRealSaleCntPromoCd"];
    dataItem.dtRealSaleCntPromoCd = messages["promotionReport.dtRealSaleCntPromoCd"];
    dataItem.realSaleQtyPromoCd   = messages["promotionReport.realSaleQtyPromoCd"];
    dataItem.chargeDs             = messages["promotionReport.chargeDs"];
    dataItem.hqChargeUprcMPromoCd      = messages["promotionReport.chargeUprc2"];
    dataItem.msChargeUprcMPromoCd      = messages["promotionReport.chargeUprc2"];
    dataItem.partnerChargeUprcMPromoCd = messages["promotionReport.chargeUprc2"];
    dataItem.hqChargeUprcPromoCd       = messages["promotionReport.totChargeUprc2"];
    dataItem.msChargeUprcPromoCd       = messages["promotionReport.totChargeUprc2"];
    dataItem.partnerChargeUprcPromoCd  = messages["promotionReport.totChargeUprc2"];
    /*dataItem.hqChargeUprcM         = messages["promotionReport.chargeUprcM"];
    dataItem.msChargeUprcM         = messages["promotionReport.chargeUprcM"];
    dataItem.partnerChargeUprcM    = messages["promotionReport.chargeUprcM"];
    dataItem.totHqChargeUprcM      = messages["promotionReport.totChargeUprcM"];
    dataItem.totMsChargeUprcM      = messages["promotionReport.totChargeUprcM"];
    dataItem.totPartnerChargeUprcM = messages["promotionReport.totChargeUprcM"];
    dataItem.hqChargeUprc         = messages["promotionReport.chargeUprc"];
    dataItem.msChargeUprc         = messages["promotionReport.chargeUprc"];
    dataItem.partnerChargeUprc    = messages["promotionReport.chargeUprc"];
    dataItem.totHqChargeUprc      = messages["promotionReport.totChargeUprc"];
    dataItem.totMsChargeUprc      = messages["promotionReport.totChargeUprc"];
    dataItem.totPartnerChargeUprc = messages["promotionReport.totChargeUprc"];*/
    dataItem.depositYmd           = messages["promotionReport.depositYmd"];
    dataItem.remark               = messages["promotionReport.remark"];

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
  $scope.$on("promotionReportExcelCtrl", function (event, data) {

    $scope.searchExcelList(data);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 엑셀 리스트 조회
  $scope.searchExcelList = function (params) {

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $.postJSON("/base/promotion/promotionReport/promotionReport/getPromotionReportExcelList.sb", params, function (response){
      var grid = $scope.excelFlex;
      grid.itemsSource = response.data.list;
      grid.itemsSource.trackChanges = true;

      if ($scope.excelFlex.rows.length <= 0) {
        $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
        return false;
      }
      var excelName;
      if(params.gubun === "D"){
        excelName = messages["promotionReport.promotionReport"] + '_' + params.startDate + '_' + params.endDate + '_';
      } else {
        excelName = messages["promotionReport.promotionReport"] + '_' ;
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