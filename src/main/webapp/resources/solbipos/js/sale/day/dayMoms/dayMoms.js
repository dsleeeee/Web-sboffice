/**
 * get application
 */
var app = agrid.getApp();

/** 승인 controller */
app.controller('dayMomsCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dayMomsCtrl', $scope, $http, true));

  var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  var endDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

  // 브랜드 콤보박스 셋팅
  $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드
  $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
  $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
  $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
  $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
  $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
  $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
  $scope._setComboData("branchCdCombo", branchCdComboList); // 지사

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("dayMomsCtrl");

    // 그리드 링크 효과
    // s.formatItem.addHandler(function (s, e) {
    //   if (e.panel === s.cells) {
    //     var col = s.columns[e.col];
    //     if (col.binding === "saleDate") { // 일자
    //       wijmo.addClass(e.cell, 'wijLink');
    //       wijmo.addClass(e.cell, 'wj-custom-readonly');
    //     }
    //     if (col.binding === "billCnt") { // 영수건수
    //       wijmo.addClass(e.cell, 'wijLink');
    //       wijmo.addClass(e.cell, 'wj-custom-readonly');
    //     }
    //     if (col.binding === "totDcAmt") { // 총할인
    //       wijmo.addClass(e.cell, 'wijLink');
    //       wijmo.addClass(e.cell, 'wj-custom-readonly');
    //     }
    //
    //     // 결제수단
    //     for (var i = 0; i < payColList.length; i++) {
    //       if (col.binding === ("pay" + payColList[i].payCd)) {
    //         var item = s.rows[e.row].dataItem;
    //
    //         // 값이 있으면 링크 효과
    //         if (nvl(item[("pay" + payColList[i].payCd)], '') !== '') {
    //           wijmo.addClass(e.cell, 'wijLink');
    //           wijmo.addClass(e.cell, 'wj-custom-readonly');
    //         }
    //       }
    //     }
    //
    //     if (col.format === "date") {
    //       e.cell.innerHTML = getFormatDate(e.cell.innerText);
    //     } else if (col.format === "dateTime") {
    //       e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
    //     } else if (col.format === "time") {
    //       e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
    //     }
    //   }
    // });

    // 그리드 클릭 이벤트
    // s.addEventListener(s.hostElement, 'mousedown', function (e) {
    //   var ht = s.hitTest(e);
    //   if (ht.cellType === wijmo.grid.CellType.Cell) {
    //     var col         = ht.panel.columns[ht.col];
    //     var selectedRow = s.rows[ht.row].dataItem;
    //     var params      = {};
    //     params.storeCd  = $("#dayMomsStoreCd").val();
    //     params.saleDate = selectedRow.saleDate.replaceAll("-","");
    //     params.gubun = "day";
    //
    //     if (col.binding === "saleDate") { // 일자 클릭
    //       if(orgnFg ==="HQ") {
    //         $scope._broadcast('dayStoreDtlCtrl', params);
    //       } else if(orgnFg === "STORE"){
    //         $scope._broadcast('dayProdDtlCtrl', params);
    //       }
    //     }
    //     if (col.binding === "billCnt") { // 영수건수 클릭
    //       if(orgnFg ==="HQ") {
    //         $scope._broadcast('dayStoreBillCtrl', params);
    //       } else if(orgnFg === "STORE"){
    //         $scope._broadcast('dayStoreBill2Ctrl', params);
    //       }
    //     }
    //     if (col.binding === "totDcAmt") { // 총할인 클릭
    //       $scope._broadcast('dayStoreDcCtrl', params);
    //     }
    //
    //     // 결제수단
    //     for (var i = 0; i < payColList.length; i++) {
    //       if (col.binding === ("pay" + payColList[i].payCd)) {
    //         // var item = s.rows[e.row].dataItem;
    //         var callCtrl = '';
    //
    //         // 값이 있으면 링크
    //         if (nvl(selectedRow[("pay" + payColList[i].payCd)], '') !== '') {
    //           callCtrl = 'day'+ (payColList[i].payMethod.substr(0,1).toUpperCase() + payColList[i].payMethod.substr(1).toLowerCase()).replaceAll("_", "") + 'Ctrl';
    //           if(callCtrl == 'dayCashCtrl') {
    //             params.cashGubun = "02"
    //           }
    //           // 현금영수증 클릭시 -> 현금 팝업
    //           if(callCtrl == 'dayCashbillCtrl') {
    //             params.cashGubun = "021";
    //             callCtrl = 'dayCashCtrl';
    //           }
    //           $scope._broadcast(callCtrl, params);
    //         }
    //       }
    //     }
    //
    //   }
    // });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    // 첫째줄 헤더 생성
    var dataItem         = {};
    dataItem.saleDate    = messages["day.dayTotal.saleDate"];
    dataItem.yoil        = messages["day.dayTotal.yoil"];
    dataItem.storeCnt    = messages["day.dayTotal.storeCnt"];
    dataItem.storeCd     = messages["day.dayTotal.storeCd"];
    dataItem.totSaleAmt  = messages["day.dayTotal.saleInfo"];
    dataItem.totDcAmt    = messages["day.dayTotal.saleInfo"];
    dataItem.realSaleAmt = messages["day.dayTotal.saleInfo"];
    dataItem.billCnt     = messages["day.dayTotal.saleInfo"];
    dataItem.billUprc    = messages["day.dayTotal.saleInfo"];
    dataItem.gaAmt       = messages["day.dayTotal.saleInfo"];
    dataItem.vatAmt      = messages["day.dayTotal.saleInfo"];
    dataItem.totTipAmt   = messages["day.dayTotal.totTipAmt"];
    dataItem.totEtcAmt   = messages["day.dayTotal.totEtcAmt"];
    dataItem.cupAmt   = messages["day.dayTotal.cupAmt"];
    dataItem.totPayAmt   = messages["day.dayTotal.payMethod"];
    dataItem.genRealSaleAmt   = messages["day.dayTotal.dlvrPack"];
    dataItem.genRealSaleRate   = messages["day.dayTotal.dlvrPack"];
    dataItem.dlvrRealSaleAmt   = messages["day.dayTotal.dlvrPack"];
    dataItem.dlvrRealSaleRate   = messages["day.dayTotal.dlvrPack"];
    dataItem.packRealSaleAmt   = messages["day.dayTotal.dlvrPack"];
    dataItem.packRealSaleRate   = messages["day.dayTotal.dlvrPack"];

    // 결제수단 헤더머지 컬럼 생성
    for (var i = 0; i < arrPayCol.length; i++) {
      dataItem['pay' + arrPayCol[i]] = messages["day.dayTotal.payMethod"];
    }

    // 주문채널별 header 셋팅
    for (var i = 0; i < arrDlvrInFgCol.length; i++) {
      dataItem['saleQty' + arrDlvrInFgCol[i]] = dlvrInFgColList[i].dlvrInFgNm;
      dataItem['realSaleAmt' + arrDlvrInFgCol[i]] = dlvrInFgColList[i].dlvrInFgNm;
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
  $scope.$on("dayMomsCtrl", function (event, data) {
    $scope.searchDayMomsList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 영수증별매출상세현황 리스트 조회
  $scope.searchDayMomsList = function () {

    var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
    var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
    var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

    // 시작일자가 종료일자보다 빠른지 확인
    if(startDt.getTime() > endDt.getTime()){
      $scope._popMsg(messages['cmm.dateChk.error']);
      return false;
    }
    // 조회일자 최대 1달(31일) 제한
    if (diffDay > 30) {
      $scope._popMsg(messages['cmm.dateOver.1month.error']);
      return false;
    }

    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
    params.storeCds     = $("#dayMomsStoreCd").val();
    params.payCol       = payCol;
    params.dlvrInFgCol  = dlvrInFgCol;
    params.momsTeam = $scope.momsTeam;
    params.momsAcShop = $scope.momsAcShop;
    params.momsAreaFg = $scope.momsAreaFg;
    params.momsCommercial = $scope.momsCommercial;
    params.momsShopType = $scope.momsShopType;
    params.momsStoreManageType = $scope.momsStoreManageType;
    params.branchCd = $scope.branchCd;
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

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/day/dayMoms/dayMoms/getDayMomsList.sb", params);
  };

  // 확장조회 숨김/보임
  $scope.searchAddShowChange = function(){
    if( $("#tblSearchAddShow").css("display") === 'none') {
      $("#tblSearchAddShow").show();
    } else {
      $("#tblSearchAddShow").hide();
    }
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.dayMomsStoreShow = function () {
    $scope._broadcast('dayMomsStoreCtrl');
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
      }, 
          messages["dayMomsMoms.dayMomsMoms"] + getCurDateTime() + '.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
    }, 10);
  };

}]);
