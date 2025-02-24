/****************************************************************
 *
 * 파일명 : monthPos2.js
 * 설  명 : 월별매출현황(포스별-영수건수) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.07.04     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  월별매출현황(포스별-영수건수) 그리드 생성
 */
app.controller('monthPosCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('monthPosCtrl', $scope, $http, true));

  // 검색조건에 조회기간
  var startMonth = new wijmo.input.InputDate('#startMonth', {
    format       : "yyyy-MM",
    selectionMode: "2" // 달력 선택 모드(1:day 2:month)
  });
  var endMonth = new wijmo.input.InputDate('#endMonth', {
    format       : "yyyy-MM",
    selectionMode: "2" // 달력 선택 모드(1:day 2:month)
  });

  // 브랜드 콤보박스 셋팅
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
    $scope._makePickColumns("monthPosCtrl");

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
    var dataItem         = {};
    dataItem.yearMonth    = messages["month.yearMonth"];
    dataItem.storeCnt    = messages["month.storeCnt"];
    dataItem.saleCnt    = messages["month.saleCnt"];
    dataItem.totSaleAmt    = messages["month.sale"];
    dataItem.totDcAmt    = messages["month.sale"];
    dataItem.realSaleAmt    = messages["month.sale"];
    dataItem.billCnt   = messages["month.sale"];
    dataItem.billUprc   = messages["month.sale"];
    dataItem.gaAmt   = messages["month.sale"];
    dataItem.vatAmt   = messages["month.sale"];
    dataItem.totTipAmt   = messages["month.totTipAmt"];
    dataItem.totEtcAmt   = messages["month.totEtcAmt"];
    dataItem.cupAmt   = messages["month.cupAmt"];
    dataItem.totPayAmt   = messages["month.payMethod"];
    dataItem.genRealSaleAmt   = messages["month.dlvrPack"];
    dataItem.genRealSaleCnt   = messages["month.dlvrPack"];
    dataItem.genRealSaleRate   = messages["month.dlvrPack"];
    dataItem.dlvrRealSaleAmt   = messages["month.dlvrPack"];
    dataItem.dlvrRealSaleCnt   = messages["month.dlvrPack"];
    dataItem.dlvrRealSaleRate   = messages["month.dlvrPack"];
    dataItem.packRealSaleAmt   = messages["month.dlvrPack"];
    dataItem.packRealSaleCnt   = messages["month.dlvrPack"];
    dataItem.packRealSaleRate   = messages["month.dlvrPack"];

    dataItem.genRealSaleCntPos  = messages["month.pos2"];
    dataItem.genRealSaleAmtPos  = messages["month.pos2"];
    dataItem.dlvrRealSaleCntPos = messages["month.pos2"];
    dataItem.dlvrRealSaleAmtPos = messages["month.pos2"];
    dataItem.packRealSaleCntPos = messages["month.pos2"];
    dataItem.packRealSaleAmtPos = messages["month.pos2"];

    dataItem.genRealSaleCntChannel  = messages["month.channel"];
    dataItem.genRealSaleAmtChannel  = messages["month.channel"];
    dataItem.dlvrRealSaleCntChannel = messages["month.channel"];
    dataItem.dlvrRealSaleAmtChannel = messages["month.channel"];
    dataItem.packRealSaleCntChannel = messages["month.channel"];
    dataItem.packRealSaleAmtChannel = messages["month.channel"];

    dataItem.genRealSaleCntKiosk  = messages["month.kiosk"];
    dataItem.genRealSaleAmtKiosk  = messages["month.kiosk"];
    dataItem.dlvrRealSaleCntKiosk = messages["month.kiosk"];
    dataItem.dlvrRealSaleAmtKiosk = messages["month.kiosk"];
    dataItem.packRealSaleCntKiosk = messages["month.kiosk"];
    dataItem.packRealSaleAmtKiosk = messages["month.kiosk"];
    
    // 결제수단 헤더머지 컬럼 생성
    for (var i = 0; i < arrPayCol.length; i++) {
      dataItem['pay' + arrPayCol[i]] = messages["month.payMethod"];
    }

    // 모바일페이상세 헤더머지 컬럼 생성
    for (var i = 0; i < arrMpayCol.length; i++) {
      dataItem['mpay' + arrMpayCol[i]] = messages["month.mpayMethod"];
    }

    // 주문채널별 header 셋팅
    for (var i = 0; i < arrDlvrInFgCol.length; i++) {
      dataItem['realSaleCnt' + arrDlvrInFgCol[i]] = dlvrInFgColList[i].dlvrInFgNm;
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
    // <-- //그리드 헤더2줄 -->

    //그리드 링크설정
    // ReadOnly 효과설정
    // s.formatItem.addHandler(function (s, e) {
    //   if (e.panel === s.cells) {
    //     var col = s.columns[e.col];
    //     if (col.binding === "yearMonth" || col.binding === "totDcAmt" || col.binding === "billCnt") {
    //       wijmo.addClass(e.cell, 'wijLink');
    //     }
    //
    //     // 결제수단
    //     for (var i = 0; i < payColList.length; i++) {
    //       if (col.binding === ("pay" + payColList[i].payCd)) {
    //         var item = s.rows[e.row].dataItem;
    //
    //         // 값이 있으면 링크 효과
    //         if (nvl(item[("pay" + payColList[i].payCd)], '') !== '' && nvl(item[("pay" + payColList[i].payCd)], '') != "0") {
    //           wijmo.addClass(e.cell, 'wijLink');
    //           wijmo.addClass(e.cell, 'wj-custom-readonly');
    //         }
    //       }
    //     }
    //   }
    // });

    // 그리드 선택 이벤트
    // s.addEventListener(s.hostElement, 'mousedown', function(e) {
    //   var ht = s.hitTest(e);
    //   if (ht.cellType === wijmo.grid.CellType.Cell) {
    //     var col = ht.panel.columns[ht.col];
    //
    //     var selectedRow = s.rows[ht.row].dataItem;
    //     var params = {};
    //     params.yearMonth = selectedRow.yearMonth.replace("-", "");
    //     params.storeCd = $("#monthPosStoreCd").val();
    //     if (orgnFg == "STORE") {
    //       params.storeCd = storeCd;
    //     }
    //     params.gubun = "month";
    //
    //     // 년월 클릭시 상세정보 조회
    //     if (col.binding === "yearMonth") {
    //       if (orgnFg === "HQ") {
    //         $scope._broadcast('dayStoreDtlCtrl', params);
    //       } else if (orgnFg === "STORE") {
    //         $scope._broadcast('dayDtlCtrl', params);
    //       }
    //     }
    //
    //     // 총할인 클릭시 상세정보 조회
    //     if (col.binding === "totDcAmt") {
    //       $scope._broadcast('dayStoreDcCtrl', params);
    //     }
    //
    //     // 영수건수 클릭시 상세정보 조회
    //     if (col.binding === "billCnt") {
    //       if (orgnFg === "HQ") {
    //         $scope._broadcast('dayStoreBillCtrl', params);
    //       } else if (orgnFg === "STORE") {
    //         $scope._broadcast('daySumAvgCtrl', params);
    //       }
    //     }
    //
    //     // 결제수단
    //     for (var i = 0; i < payColList.length; i++) {
    //       if (col.binding === ("pay" + payColList[i].payCd)) {
    //         var callCtrl = '';
    //
    //         // 값이 있으면 링크
    //         if (nvl(selectedRow[("pay" + payColList[i].payCd)], '') !== '' && nvl(selectedRow[("pay" + payColList[i].payCd)], '') != "0") {
    //           callCtrl = 'day' + (payColList[i].payMethod.substr(0, 1).toUpperCase() + payColList[i].payMethod.substr(1).toLowerCase()).replaceAll("_", "") + 'Ctrl';
    //           // 포인트 이름이 안맞음(dayMembr->dayPoint)
    //           if (callCtrl == 'dayMembrCtrl') {
    //             callCtrl = 'dayPointCtrl';
    //           }
    //           // 사원카드 이름이 안맞음(dayEmp_Card->dayEmpCard)
    //           if (callCtrl == 'dayEmp_cardCtrl') {
    //             callCtrl = 'dayEmpCardCtrl';
    //           }
    //           if (callCtrl == "dayCashCtrl") {
    //             params.cashGubun = "02";
    //           }
    //           if (callCtrl == "dayCashbillCtrl") {
    //             params.cashGubun = "021";
    //             callCtrl = 'dayCashCtrl';
    //           }
    //           console.log(callCtrl);
    //           $scope._broadcast(callCtrl, params);
    //         }
    //       }
    //     }
    //   }
    // });
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("monthPosCtrl", function (event, data) {
    $scope.searchMonthPosList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 영수증별매출상세현황 리스트 조회
  $scope.searchMonthPosList = function () {

    var startDt = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
    var endDt = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
    var diffMonth = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

    // 시작일자가 종료일자보다 빠른지 확인
    if(startDt.getTime() > endDt.getTime()){
      $scope._popMsg(messages['mobile.cmm.dateChk.error']);
      return false;
    }
    // 조회일자 최대 1년(12개월) 제한
    if (diffMonth > 12) {
      $scope._popMsg(messages['mobile.cmm.dateOver.1year.error']);
      return false;
    }

    var params = {};
    params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
    params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
    params.storeCds   = $("#monthPosStoreCd").val();
    params.payCol       = payCol;
    params.mpayCol      = mpayCol;
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

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/month/monthPos2/monthPos2/getMonthPos2List.sb", params);
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
  $scope.monthPosStoreShow = function () {
    $scope._broadcast('monthPosStoreCtrl');
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
        includeCellStyles: false,
        includeColumns: function (column) {
          return column.visible;
        }
      },
          messages["monthPos.monthPos"] + '_' + wijmo.Globalize.format(startMonth.value, 'yyyyMM') + '_' + wijmo.Globalize.format(endMonth.value, 'yyyyMM') + '_' + getCurDateTime() + '.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
    }, 10);
  };

}]);
