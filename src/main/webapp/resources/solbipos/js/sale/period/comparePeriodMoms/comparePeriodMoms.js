/****************************************************************
 *
 * 파일명 : comparePeriodMoms.js
 * 설  명 : 가상로그인 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.12.06     이다솜      1.0
 *
 * **************************************************************/

/**
 * get application
 */
var app = agrid.getApp();

/** controller */
app.controller('comparePeriodMomsCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('comparePeriodMomsCtrl', $scope, $http, true));

    $scope.srchStartDate  = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate    = wcombo.genDateVal("#srchEndDate", gvEndDate);
    $scope.compStartDate  = wcombo.genDateVal("#compStartDate", gvStartDate);
    $scope.compEndDate    = wcombo.genDateVal("#compEndDate", gvEndDate);

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("srchStoreHqBrandCd", momsHqBrandCdComboList);            // 매장브랜드
    $scope._setComboData("srchMomsTeam", momsTeamComboList);                       // 팀별
    $scope._setComboData("srchMomsAcShop", momsAcShopComboList);                   // AC점포별
    $scope._setComboData("srchMomsAreaFg", momsAreaFgComboList);                   // 지역구분
    $scope._setComboData("srchMomsCommercial", momsCommercialComboList);           // 상권
    $scope._setComboData("srchMomsShopType", momsShopTypeComboList);               // 점포유형
    $scope._setComboData("srchMomsStoreManageType", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("srchBranchCd", branchCdComboList);                       // 지사

    // 팀별
    if(momsTeamComboList.length <= 1) {
        $("#srchMomsTeam").css('background-color', '#F0F0F0');
        $("#srchMomsTeam").attr("disabled", true);
    } else {
        $("#srchMomsTeam").css('background-color', '#FFFFFF');
        $("#srchMomsTeam").attr("disabled", false);
    }
    // AC점포별
    if(momsAcShopComboList.length <= 1) {
        $("#srchMomsAcShop").css('background-color', '#F0F0F0');
        $("#srchMomsAcShop").attr("disabled", true);
    } else {
        $("#srchMomsAcShop").css('background-color', '#FFFFFF');
        $("#srchMomsAcShop").attr("disabled", false);
    }
    // 지역구분
    if(momsAreaFgComboList.length <= 1) {
        $("#srchMomsAreaFg").css('background-color', '#F0F0F0');
        $("#srchMomsAreaFg").attr("disabled", true);
    } else {
        $("#srchMomsAreaFg").css('background-color', '#FFFFFF');
        $("#srchMomsAreaFg").attr("disabled", false);
    }
    // 상권
    if(momsCommercialComboList.length <= 1) {
        $("#srchMomsCommercial").css('background-color', '#F0F0F0');
        $("#srchMomsCommercial").attr("disabled", true);
    } else {
        $("#srchMomsCommercial").css('background-color', '#FFFFFF');
        $("#srchMomsCommercial").attr("disabled", false);
    }
    // 점포유형
    if(momsShopTypeComboList.length <= 1) {
        $("#srchMomsShopType").css('background-color', '#F0F0F0');
        $("#srchMomsShopType").attr("disabled", true);
    } else {
        $("#srchMomsShopType").css('background-color', '#FFFFFF');
        $("#srchMomsShopType").attr("disabled", false);
    }
    // 매장관리타입
    if(momsStoreManageTypeComboList.length <= 1) {
        $("#srchMomsStoreManageType").css('background-color', '#F0F0F0');
        $("#srchMomsStoreManageType").attr("disabled", true);
    } else {
        $("#srchMomsStoreManageType").css('background-color', '#FFFFFF');
        $("#srchMomsStoreManageType").attr("disabled", false);
    }
    // 지사
    if(branchCdComboList.length <= 1) {
        $("#srchBranchCd").css('background-color', '#F0F0F0');
        $("#srchBranchCd").attr("disabled", true);
    } else {
        $("#srchBranchCd").css('background-color', '#FFFFFF');
        $("#srchBranchCd").attr("disabled", false);
    }

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
          if (e.panel == s.cells) {
            var col = s.columns[e.col];
            var item = s.rows[e.row].dataItem;
            if (col.binding === "yoil") {
              if(item.yoil === "토") {
                wijmo.addClass(e.cell, 'blue');
              } else if(item.yoil === "일") {
                wijmo.addClass(e.cell, 'red');
              }
            }
          }
        });

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("comparePeriodMomsCtrl");

        //Grid Header 2줄 - START	----------------------------------------------------------------
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        //첫째줄 Header 생성
        var dataItem = {};
        dataItem.yoil = messages["periodMoms.yoil"];
        dataItem.realSaleCnt = messages["periodMoms.srchDate"];
        dataItem.billUprc = messages["periodMoms.srchDate"];
        dataItem.saleQty = messages["periodMoms.srchDate"];
        dataItem.totSaleAmt = messages["periodMoms.srchDate"];
        dataItem.totDcAmt = messages["periodMoms.srchDate"];
        dataItem.realSaleAmt = messages["periodMoms.srchDate"];
        dataItem.rate = messages["periodMoms.srchDate"];
        dataItem.realSaleCnt1 = messages["periodMoms.compDate"];
        dataItem.billUprc1 = messages["periodMoms.compDate"];
        dataItem.saleQty1 = messages["periodMoms.compDate"];
        dataItem.totSaleAmt1 = messages["periodMoms.compDate"];
        dataItem.totDcAmt1 = messages["periodMoms.compDate"];
        dataItem.realSaleAmt1 = messages["periodMoms.compDate"];
        dataItem.rate1 = messages["periodMoms.compDate"];
        dataItem.rateRealSaleCnt = messages["periodMoms.ratePer"];
        dataItem.rateBillUprc = messages["periodMoms.ratePer"];
        dataItem.rateSaleQty = messages["periodMoms.ratePer"];
        dataItem.rateTotSaleAmt = messages["periodMoms.ratePer"];
        dataItem.rateRealSaleAmt = messages["periodMoms.ratePer"];

        s.columnHeaders.rows[0].dataItem = dataItem;
        //Grid Header 2줄 - END		----------------------------------------------------------------

        s.itemFormatter = function (panel, r, c, cell) {
          if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
            panel.rows   [r].allowMerging	= true;
            panel.columns[c].allowMerging	= true;

            wijmo.setCss(cell, {
                  display    : 'table',
                  tableLayout: 'fixed'
                }
            );

            cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

            wijmo.setCss(cell.children[0], {
                  display      : 'table-cell',
                  verticalAlign: 'middle',
                  textAlign    : 'center'
                }
            );
          }
          else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
            if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
              cell.textContent = '';
            } else {
              if (!isEmpty(panel._rows[r]._data.rnum)) {
                cell.textContent = (panel._rows[r]._data.rnum).toString();
              } else {
                cell.textContent = (r + 1).toString();
              }
            }
          }
          else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
            var col = panel.columns[c];
            if (col.isReadOnly) {
              wijmo.addClass(cell, 'wj-custom-readonly');
            }
          }
        }	//s.itemFormatter = function (panel, r, c, cell) {

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("comparePeriodMomsCtrl", function (event, data) {
        // 조회
        $scope.searchList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 조회
    $scope.searchList = function(){

       // 조회기간
       var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
       var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
       var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

       // 대비기간
       var compStartDt = new Date(wijmo.Globalize.format($scope.compStartDate.value, 'yyyy-MM-dd'));
       var compEndDt = new Date(wijmo.Globalize.format($scope.compEndDate.value, 'yyyy-MM-dd'));
       var compDiffDay = (compEndDt.getTime() - compStartDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

       // 조회기간 시작일자가 종료일자보다 빠른지 확인
       if(startDt.getTime() > endDt.getTime()){
           s_alert.pop(messages['periodMoms.srchDate'] + '의 ' + messages['periodMoms.dateChk.error']);
           return false;
       }
       // 조회기간 조회일자 최대 한달(31일) 제한
       if (diffDay > 31) {
           s_alert.pop(messages['periodMoms.srchDate'] + '은 ' + messages['periodMoms.date.error']);
           return false;
       }

       // 대비기간 시작일자가 종료일자보다 빠른지 확인
       if(compStartDt.getTime() > compEndDt.getTime()){
           s_alert.pop(messages['periodMoms.compDate'] + '의 ' + messages['periodMoms.dateChk.error']);
           return false;
       }
       // 대비기간 조회일자 최대 한달(31일) 제한
       if (compDiffDay > 31) {
           s_alert.pop(messages['periodMoms.compDate'] + '은 ' + messages['periodMoms.date.error']);
           return false;
       }

       // 파라미터
       var params = {};
       params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
       params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
       params.compStartDate = wijmo.Globalize.format($scope.compStartDate.value, 'yyyyMMdd');
       params.compEndDate = wijmo.Globalize.format($scope.compEndDate.value, 'yyyyMMdd');

       if(orgnFg === "HQ") {
           params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
           params.storeCds = $("#comparePeriodMomsStoreCd").val();
           params.momsTeam = $scope.srchMomsTeamCombo.selectedValue;
           params.momsAcShop = $scope.srchMomsAcShopCombo.selectedValue;
           params.momsAreaFg = $scope.srchMomsAreaFgCombo.selectedValue;
           params.momsCommercial = $scope.srchMomsCommercialCombo.selectedValue;
           params.momsShopType = $scope.srchMomsShopTypeCombo.selectedValue;
           params.momsStoreManageType = $scope.srchMomsStoreManageTypeCombo.selectedValue;
           params.branchCd = $scope.srchBranchCdCombo.selectedValue;

           // '전체' 일때
           if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
               var momsHqBrandCd = "";
               for (var i = 0; i < momsHqBrandCdComboList.length; i++) {
                   if (momsHqBrandCdComboList[i].value !== null) {
                       momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                   }
               }
               params.userBrands = momsHqBrandCd;
           }
       }

       // 조회 수행 : 조회URL, 파라미터, 콜백함수
       $scope._inquiryMain("/sale/period/comparePeriodMoms/comparePeriodMoms/getComparePeriodList.sb", params);
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
          $("#tblSearchAddShow").show();
        } else {
          $("#tblSearchAddShow").hide();
        }
    };

    // 조회조건 엑셀다운로드
    $scope.excelDownload = function() {

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : true,
                includeColumns      : function (column) {
                return column.visible;
                }
            }, messages["periodMomsMoms.periodMomsMoms"]+getToday()+'.xlsx', function () {
                $timeout(function () {
                $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);

    };
    
    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.comparePeriodMomsStoreShow = function () {
        $scope._broadcast('comparePeriodMomsStoreCtrl');
    };

}]);