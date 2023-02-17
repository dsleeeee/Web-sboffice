/****************************************************************
 *
 * 파일명 : prodSaleRateMoms.js
 * 설  명 : 상품판매비율 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.12.13     이다솜      1.0
 *
 * **************************************************************/

/**
 * get application
 */
var app = agrid.getApp();

// 상품표시옵션
var prodOptionComboData = [
    {"name":"단품/세트","value":"1"},
    {"name":"단품/구성","value":"2"},
    {"name":"단품/세트/구성","value":"3"},
    {"name":"모두표시","value":"4"}
];
// 일자표시옵션
var dayOptionComboData = [
    {"name":"일자별","value":"1"},
    {"name":"기간합","value":"2"}
];

/** controller */
app.controller('prodSaleRateMomsCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodSaleRateMomsCtrl', $scope, $http, true));

    // 조회일자
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("srchProdOption", prodOptionComboData);                   // 상품표시옵션
    $scope._setComboData("srchDayOption", dayOptionComboData);                     // 일자표시옵션
    $scope._setComboData("srchStoreHqBrandCd", momsHqBrandCdComboList);            // 매장브랜드
    $scope._setComboData("srchProdHqBrand", momsHqBrandCdComboList);               // 상품브랜드
    $scope._setComboData("srchMomsTeam", momsTeamComboList);                       // 팀별
    $scope._setComboData("srchMomsAcShop", momsAcShopComboList);                   // AC점포별
    $scope._setComboData("srchMomsAreaFg", momsAreaFgComboList);                   // 지역구분
    $scope._setComboData("srchMomsCommercial", momsCommercialComboList);           // 상권
    $scope._setComboData("srchMomsShopType", momsShopTypeComboList);               // 점포유형
    $scope._setComboData("srchMomsStoreManageType", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("srchBranchCd", branchCdComboList);                       // 지사

    // // 팀별
    // if(momsTeamComboList.length <= 1) {
    //     $("#srchMomsTeam").css('background-color', '#F0F0F0');
    //     $("#srchMomsTeam").attr("disabled", true);
    // } else {
    //     $("#srchMomsTeam").css('background-color', '#FFFFFF');
    //     $("#srchMomsTeam").attr("disabled", false);
    // }
    // // AC점포별
    // if(momsAcShopComboList.length <= 1) {
    //     $("#srchMomsAcShop").css('background-color', '#F0F0F0');
    //     $("#srchMomsAcShop").attr("disabled", true);
    // } else {
    //     $("#srchMomsAcShop").css('background-color', '#FFFFFF');
    //     $("#srchMomsAcShop").attr("disabled", false);
    // }
    // // 지역구분
    // if(momsAreaFgComboList.length <= 1) {
    //     $("#srchMomsAreaFg").css('background-color', '#F0F0F0');
    //     $("#srchMomsAreaFg").attr("disabled", true);
    // } else {
    //     $("#srchMomsAreaFg").css('background-color', '#FFFFFF');
    //     $("#srchMomsAreaFg").attr("disabled", false);
    // }
    // // 상권
    // if(momsCommercialComboList.length <= 1) {
    //     $("#srchMomsCommercial").css('background-color', '#F0F0F0');
    //     $("#srchMomsCommercial").attr("disabled", true);
    // } else {
    //     $("#srchMomsCommercial").css('background-color', '#FFFFFF');
    //     $("#srchMomsCommercial").attr("disabled", false);
    // }
    // // 점포유형
    // if(momsShopTypeComboList.length <= 1) {
    //     $("#srchMomsShopType").css('background-color', '#F0F0F0');
    //     $("#srchMomsShopType").attr("disabled", true);
    // } else {
    //     $("#srchMomsShopType").css('background-color', '#FFFFFF');
    //     $("#srchMomsShopType").attr("disabled", false);
    // }
    // // 매장관리타입
    // if(momsStoreManageTypeComboList.length <= 1) {
    //     $("#srchMomsStoreManageType").css('background-color', '#F0F0F0');
    //     $("#srchMomsStoreManageType").attr("disabled", true);
    // } else {
    //     $("#srchMomsStoreManageType").css('background-color', '#FFFFFF');
    //     $("#srchMomsStoreManageType").attr("disabled", false);
    // }
    // // 지사
    // if(branchCdComboList.length <= 1) {
    //     $("#srchBranchCd").css('background-color', '#F0F0F0');
    //     $("#srchBranchCd").attr("disabled", true);
    // } else {
    //     $("#srchBranchCd").css('background-color', '#FFFFFF');
    //     $("#srchBranchCd").attr("disabled", false);
    // }

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

        // <-- 그리드 헤더3줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.saleDate = messages["prodRankMoms.saleDate"];
        dataItem.yoil = messages["prodRankMoms.yoil"];
        dataItem.dayFrom = messages["prodRankMoms.dayFrom"];
        dataItem.dayTo = messages["prodRankMoms.dayTo"];
        dataItem.branchNm = messages["prodRankMoms.branchNm"];
        dataItem.storeCd = messages["prodRankMoms.storeCd"];
        dataItem.storeNm = messages["prodRankMoms.storeNm"];
        dataItem.lClassCd = messages["prodRankMoms.lClassCd"];
        dataItem.lClassNm = messages["prodRankMoms.lClassNm"];
        dataItem.mClassCd = messages["prodRankMoms.mClassCd"];
        dataItem.mClassNm = messages["prodRankMoms.mClassNm"];
        dataItem.sClassCd = messages["prodRankMoms.sClassCd"];
        dataItem.sClassNm = messages["prodRankMoms.sClassNm"];
        dataItem.prodCd = messages["prodRankMoms.prodCd"];
        dataItem.sideProdCd = messages["prodRankMoms.sideProdCd"];
        dataItem.selTypeFg = messages["prodRankMoms.selTypeFg"];
        dataItem.prodNm = messages["prodRankMoms.prodNm"];
        dataItem.saleQty1 = messages["prodRankMoms.totSaleQty"];
        dataItem.saleQty2 = messages["prodRankMoms.totSaleQty"];
        dataItem.saleQty3 = messages["prodRankMoms.totSaleQty"];
        dataItem.totSaleAmt = messages["prodRankMoms.totSaleAmt"];
        dataItem.dcAmt = messages["prodRankMoms.dcAmt"];
        dataItem.realSaleAmt1 = messages["prodRankMoms.realSaleAmt"];
        dataItem.realSaleAmt2 = messages["prodRankMoms.realSaleAmt"];
        dataItem.realSaleAmt3 = messages["prodRankMoms.realSaleAmt"];

        // header 셋팅
        for (var i = 0; i < vDlvrOrderFg.length; i++) {
          dataItem[vDlvrOrderFg[i] + "SaleQty1"] = messages["prodRankMoms." + vDlvrOrderFg[i]];
          dataItem[vDlvrOrderFg[i] + "SaleQty2"] = messages["prodRankMoms." + vDlvrOrderFg[i]];
          dataItem[vDlvrOrderFg[i] + "SaleQty3"] = messages["prodRankMoms." + vDlvrOrderFg[i]];
          dataItem[vDlvrOrderFg[i] + "RealSaleAmt1"] = messages["prodRankMoms." + vDlvrOrderFg[i]];
          dataItem[vDlvrOrderFg[i] + "RealSaleAmt2"] = messages["prodRankMoms." + vDlvrOrderFg[i]];
          dataItem[vDlvrOrderFg[i] + "RealSaleAmt3"] = messages["prodRankMoms." + vDlvrOrderFg[i]];
        }

        // header 셋팅
        for (var j = 0; j < vDlvrOrderFg.length; j++) {
            for (var i = 0; i < arrDlvrInFgCol.length; i++) {
                dataItem[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "SaleQty1"] = messages["prodRankMoms." + vDlvrOrderFg[j]];
                dataItem[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "SaleQty2"] = messages["prodRankMoms." + vDlvrOrderFg[j]];
                dataItem[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "SaleQty3"] = messages["prodRankMoms." + vDlvrOrderFg[j]];
                dataItem[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "RealSaleAmt1"] = messages["prodRankMoms." + vDlvrOrderFg[j]];
                dataItem[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "RealSaleAmt2"] = messages["prodRankMoms." + vDlvrOrderFg[j]];
                dataItem[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "RealSaleAmt3"] =messages["prodRankMoms." + vDlvrOrderFg[j]];
            }
        }

        s.columnHeaders.rows[0].dataItem = dataItem;


        // 둘째줄 헤더 생성
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        var dataItem1 = {};
        dataItem1.saleDate = messages["prodRankMoms.saleDate"];
        dataItem1.yoil = messages["prodRankMoms.yoil"];
        dataItem1.dayFrom = messages["prodRankMoms.dayFrom"];
        dataItem1.dayTo = messages["prodRankMoms.dayTo"];
        dataItem1.branchNm = messages["prodRankMoms.branchNm"];
        dataItem1.storeCd = messages["prodRankMoms.storeCd"];
        dataItem1.storeNm = messages["prodRankMoms.storeNm"];
        dataItem1.lClassCd = messages["prodRankMoms.lClassCd"];
        dataItem1.lClassNm = messages["prodRankMoms.lClassNm"];
        dataItem1.mClassCd = messages["prodRankMoms.mClassCd"];
        dataItem1.mClassNm = messages["prodRankMoms.mClassNm"];
        dataItem1.sClassCd = messages["prodRankMoms.sClassCd"];
        dataItem1.sClassNm = messages["prodRankMoms.sClassNm"];
        dataItem1.prodCd = messages["prodRankMoms.prodCd"];
        dataItem1.sideProdCd = messages["prodRankMoms.sideProdCd"];
        dataItem1.selTypeFg = messages["prodRankMoms.selTypeFg"];
        dataItem1.prodNm = messages["prodRankMoms.prodNm"];
        dataItem1.saleQty1 = messages["prodRankMoms.saleQty1"];
        dataItem1.saleQty2 = messages["prodRankMoms.saleQty2"];
        dataItem1.saleQty3 = messages["prodRankMoms.saleQty3"];
        dataItem1.totSaleAmt = messages["prodRankMoms.totSaleAmt"];
        dataItem1.dcAmt = messages["prodRankMoms.dcAmt"];
        dataItem1.realSaleAmt1 = messages["prodRankMoms.realSaleAmt1"];
        dataItem1.realSaleAmt2 = messages["prodRankMoms.realSaleAmt2"];
        dataItem1.realSaleAmt3 = messages["prodRankMoms.realSaleAmt3"];

        // header 셋팅
        for (var j = 0; j < vDlvrOrderFg.length; j++) {
            dataItem1[vDlvrOrderFg[j] + "SaleQty1"] = messages["prodRankMoms.saleQty1"];
            dataItem1[vDlvrOrderFg[j] + "SaleQty2"] = messages["prodRankMoms.saleQty2"];
            dataItem1[vDlvrOrderFg[j] + "SaleQty3"] = messages["prodRankMoms.saleQty3"];
            dataItem1[vDlvrOrderFg[j] + "RealSaleAmt1"] = messages["prodRankMoms.realSaleAmt1"];
            dataItem1[vDlvrOrderFg[j] + "RealSaleAmt2"] = messages["prodRankMoms.realSaleAmt2"];
            dataItem1[vDlvrOrderFg[j] + "RealSaleAmt3"] = messages["prodRankMoms.realSaleAmt3"];
        }

        // header 셋팅
        for (var j = 0; j < vDlvrOrderFg.length; j++) {
            for (var i = 0; i < arrDlvrInFgCol.length; i++) {
                dataItem1[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "SaleQty1"] = arrDlvrInFgColNm[i];
                dataItem1[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "SaleQty2"] = arrDlvrInFgColNm[i];
                dataItem1[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "SaleQty3"] = arrDlvrInFgColNm[i];
                dataItem1[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "RealSaleAmt1"] = arrDlvrInFgColNm[i];
                dataItem1[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "RealSaleAmt2"] = arrDlvrInFgColNm[i];
                dataItem1[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "RealSaleAmt3"] = arrDlvrInFgColNm[i];
            }
        }
        s.columnHeaders.rows[1].dataItem = dataItem1;

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

            if ((panel.grid.columnHeaders.rows.length - 1) === r) {
              // 헤더의 전체선택 클릭 로직
              var flex   = panel.grid;
              var column = flex.columns[c];
              // check that this is a boolean column
              if (column.binding === 'gChk' || column.format === 'checkBox' || column.format === 'checkBoxText') {
                // prevent sorting on click
                column.allowSorting = false;
                // count true values to initialize checkbox
                var cnt             = 0;
                for (var i = 0; i < flex.rows.length; i++) {
                  if (flex.getCellData(i, c) === true) {
                    cnt++;
                  }
                }
                // create and initialize checkbox
                if (column.format === 'checkBoxText') {
                  cell.innerHTML = '<input id=\"' + column.binding + '\" type=\"checkbox\" class=\"wj-cell-check\" />'
                      + '<label for=\"' + column.binding + '\" class=\"wj-header-label\">' + cell.innerHTML + '</label>';
                } else {
                  cell.innerHTML = '<input type=\"checkbox\" class=\"wj-cell-check\" />';
                }
                var cb           = cell.firstChild;
                cb.checked       = cnt > 0;
                cb.indeterminate = cnt > 0 && cnt < flex.rows.length;
                // apply checkbox value to cells
                cb.addEventListener('click', function (e) {
                  flex.beginUpdate();
                  for (var i = 0; i < flex.rows.length; i++) {
                    if(!flex.rows[i].isReadOnly) {
                      flex.setCellData(i, c, cb.checked);
                    }
                  }
                  flex.endUpdate();
                });
              }
            }
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
        // <-- //그리드 헤더3줄 -->

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("prodSaleRateMomsCtrl");

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("prodSaleRateMomsCtrl", function (event, data) {
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

       // 시작일자가 종료일자보다 빠른지 확인
       if(startDt.getTime() > endDt.getTime()){
           $scope._popMsg(messages['cmm.dateChk.error']);
           return false;
       }

       // 조회일자 최대 한달(31일) 제한
       if (diffDay > 7) {
           s_alert.pop(messages['cmm.dateOver.7day.error']);
           return false;
       }

       // 파라미터
       var params = {};
       params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
       params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
       params.prodClassCd = $scope.prodClassCd;
       params.dayOption = $scope.srchDayOptionCombo.selectedValue;
       params.prodOption = $scope.srchProdOptionCombo.selectedValue;
       params.prodCd = $("#srchProdCd").val();
       params.prodNm = $("#srchProdNm").val();
       params.storeCds = $("#prodSaleRateMomsStoreCd").val();
       params.prodCds = $("#prodSaleRateMomsSelectCd").val();
       params.dlvrInFgCol = dlvrInFgCol;
       params.listScale = 500; //-페이지 스케일 갯수

       if(orgnFg === "HQ"){
           params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
           params.prodHqBrandCd = $scope.srchProdHqBrandCombo.selectedValue;
           params.momsTeam = $scope.srchMomsTeamCombo.selectedValue;
           params.momsAcShop = $scope.srchMomsAcShopCombo.selectedValue;
           params.momsAreaFg = $scope.srchMomsAreaFgCombo.selectedValue;
           params.momsCommercial = $scope.srchMomsCommercialCombo.selectedValue;
           params.momsShopType = $scope.srchMomsShopTypeCombo.selectedValue;
           params.momsStoreManageType = $scope.srchMomsStoreManageTypeCombo.selectedValue;
           params.branchCd = $scope.srchBranchCdCombo.selectedValue;

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
       }

       // 조회 수행 : 조회URL, 파라미터, 콜백함수
       $scope._inquiryMain("/sale/prod/prodSaleRateMoms/prodSaleRateMoms/getProdSaleRateList.sb", params, function (){

           // 선택한 테이블에 따른 리스트 항목 visible
           var grid = wijmo.Control.getControl("#wjGridList");
           var columns = grid.columns;

           // 컬럼 총갯수
           var columnsCnt = columns.length;

           for (var i = 0; i < columnsCnt; i++) {
               columns[i].visible = true;
           }

           // 일자표시옵션
           if(params.dayOption === "1"){  // 일자별
             columns[0].visible = true;
             columns[1].visible = true;
             columns[2].visible = false;
             columns[3].visible = false;
           } else if(params.dayOption === "2"){  // 기간합
             columns[0].visible = false;
             columns[1].visible = false;
             columns[2].visible = true;
             columns[3].visible = true;
           }

           // 상품표시옵션에 따른 컬럼 제어
           if(params.prodOption === "1"){  // 단품+세트

               // 총계
               columns[18].visible = false;
               columns[19].visible = false;
               columns[23].visible = false;
               columns[24].visible = false;

               // 내점,포장,배달 계
               for(j = 25 ; j < columnsCnt; j++){
                   if(j%3 !== 1){
                       columns[j].visible = false;
                   }
               }

           }else if(params.prodOption === "2"){   // 단품+구성

               // 총계
               columns[17].visible = false;
               columns[19].visible = false;
               columns[22].visible = false;
               columns[24].visible = false;

               // 내점,포장,배달 계
               for(j = 25 ; j < columnsCnt; j++){
                   if(j%3 < 2){
                       columns[j].visible = false;
                   }
               }

           }else if(params.prodOption === "3") {  // 단품+세트+구성

               // 총계
               columns[17].visible = false;
               columns[18].visible = false;
               columns[22].visible = false;
               columns[23].visible = false;

               // 내점,포장,배달 계
               for(j = 25 ; j < columnsCnt; j++){
                   if(0 < j%3){
                       columns[j].visible = false;
                   }
               }
           }

       });
    };

    // 엑셀 다운로드
    $scope.excelDownload = function(){

        // 조회기간
       var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
       var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
       var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

       // 시작일자가 종료일자보다 빠른지 확인
       if(startDt.getTime() > endDt.getTime()){
           $scope._popMsg(messages['cmm.dateChk.error']);
           return false;
       }

       // 조회일자 최대 한달(31일) 제한
       if (diffDay > 7) {
           s_alert.pop(messages['cmm.dateOver.7day.error']);
           return false;
       }

       // 파라미터
       var params = {};
       params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
       params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
       params.prodClassCd = $scope.prodClassCd;
       params.dayOption = $scope.srchDayOptionCombo.selectedValue;
       params.prodOption = $scope.srchProdOptionCombo.selectedValue;
       params.prodCd = $("#srchProdCd").val();
       params.prodNm = $("#srchProdNm").val();
       params.storeCds = $("#prodSaleRateMomsStoreCd").val();
       params.prodCds = $("#prodSaleRateMomsSelectCd").val();
       params.dlvrInFgCol = dlvrInFgCol;

       if(orgnFg === "HQ"){
           params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
           params.prodHqBrandCd = $scope.srchProdHqBrandCombo.selectedValue;
           params.momsTeam = $scope.srchMomsTeamCombo.selectedValue;
           params.momsAcShop = $scope.srchMomsAcShopCombo.selectedValue;
           params.momsAreaFg = $scope.srchMomsAreaFgCombo.selectedValue;
           params.momsCommercial = $scope.srchMomsCommercialCombo.selectedValue;
           params.momsShopType = $scope.srchMomsShopTypeCombo.selectedValue;
           params.momsStoreManageType = $scope.srchMomsStoreManageTypeCombo.selectedValue;
           params.branchCd = $scope.srchBranchCdCombo.selectedValue;

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
       }

        $scope._broadcast('prodSaleRateMomsExcelCtrl', params);
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
    $scope.prodSaleRateMomsStoreShow = function () {
        $scope._broadcast('prodSaleRateMomsStoreCtrl');
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.prodSaleRateMomsSelectShow = function () {
        $scope._broadcast('prodSaleRateMomsSelectCtrl');
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {
        var popUp = $scope.prodClassPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function(response){
                      $scope.prodClassCd = prodClassCd;
                      $scope.prodClassNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassNm = "";
    };

}]);

/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('prodSaleRateMomsExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodSaleRateMomsExcelCtrl', $scope, $http, false));

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

        // <-- 그리드 헤더3줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.saleDate = messages["prodRankMoms.saleDate"];
        dataItem.yoil = messages["prodRankMoms.yoil"];
        dataItem.dayFrom = messages["prodRankMoms.dayFrom"];
        dataItem.dayTo = messages["prodRankMoms.dayTo"];
        dataItem.branchNm = messages["prodRankMoms.branchNm"];
        dataItem.storeCd = messages["prodRankMoms.storeCd"];
        dataItem.storeNm = messages["prodRankMoms.storeNm"];
        dataItem.lClassCd = messages["prodRankMoms.lClassCd"];
        dataItem.lClassNm = messages["prodRankMoms.lClassNm"];
        dataItem.mClassCd = messages["prodRankMoms.mClassCd"];
        dataItem.mClassNm = messages["prodRankMoms.mClassNm"];
        dataItem.sClassCd = messages["prodRankMoms.sClassCd"];
        dataItem.sClassNm = messages["prodRankMoms.sClassNm"];
        dataItem.prodCd = messages["prodRankMoms.prodCd"];
        dataItem.sideProdCd = messages["prodRankMoms.sideProdCd"];
        dataItem.selTypeFg = messages["prodRankMoms.selTypeFg"];
        dataItem.prodNm = messages["prodRankMoms.prodNm"];
        dataItem.saleQty1 = messages["prodRankMoms.totSaleQty"];
        dataItem.saleQty2 = messages["prodRankMoms.totSaleQty"];
        dataItem.saleQty3 = messages["prodRankMoms.totSaleQty"];
        dataItem.totSaleAmt = messages["prodRankMoms.totSaleAmt"];
        dataItem.dcAmt = messages["prodRankMoms.dcAmt"];
        dataItem.realSaleAmt1 = messages["prodRankMoms.realSaleAmt"];
        dataItem.realSaleAmt2 = messages["prodRankMoms.realSaleAmt"];
        dataItem.realSaleAmt3 = messages["prodRankMoms.realSaleAmt"];

        // header 셋팅
        for (var i = 0; i < vDlvrOrderFg.length; i++) {
          dataItem[vDlvrOrderFg[i] + "SaleQty1"] = messages["prodRankMoms." + vDlvrOrderFg[i]];
          dataItem[vDlvrOrderFg[i] + "SaleQty2"] = messages["prodRankMoms." + vDlvrOrderFg[i]];
          dataItem[vDlvrOrderFg[i] + "SaleQty3"] = messages["prodRankMoms." + vDlvrOrderFg[i]];
          dataItem[vDlvrOrderFg[i] + "RealSaleAmt1"] = messages["prodRankMoms." + vDlvrOrderFg[i]];
          dataItem[vDlvrOrderFg[i] + "RealSaleAmt2"] = messages["prodRankMoms." + vDlvrOrderFg[i]];
          dataItem[vDlvrOrderFg[i] + "RealSaleAmt3"] = messages["prodRankMoms." + vDlvrOrderFg[i]];
        }

        // header 셋팅
        for (var j = 0; j < vDlvrOrderFg.length; j++) {
            for (var i = 0; i < arrDlvrInFgCol.length; i++) {
                dataItem[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "SaleQty1"] = messages["prodRankMoms." + vDlvrOrderFg[j]];
                dataItem[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "SaleQty2"] = messages["prodRankMoms." + vDlvrOrderFg[j]];
                dataItem[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "SaleQty3"] = messages["prodRankMoms." + vDlvrOrderFg[j]];
                dataItem[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "RealSaleAmt1"] = messages["prodRankMoms." + vDlvrOrderFg[j]];
                dataItem[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "RealSaleAmt2"] = messages["prodRankMoms." + vDlvrOrderFg[j]];
                dataItem[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "RealSaleAmt3"] =messages["prodRankMoms." + vDlvrOrderFg[j]];
            }
        }

        s.columnHeaders.rows[0].dataItem = dataItem;


        // 둘째줄 헤더 생성
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        var dataItem1 = {};
        dataItem1.saleDate = messages["prodRankMoms.saleDate"];
        dataItem1.yoil = messages["prodRankMoms.yoil"];
        dataItem1.dayFrom = messages["prodRankMoms.dayFrom"];
        dataItem1.dayTo = messages["prodRankMoms.dayTo"];
        dataItem1.branchNm = messages["prodRankMoms.branchNm"];
        dataItem1.storeCd = messages["prodRankMoms.storeCd"];
        dataItem1.storeNm = messages["prodRankMoms.storeNm"];
        dataItem1.lClassCd = messages["prodRankMoms.lClassCd"];
        dataItem1.lClassNm = messages["prodRankMoms.lClassNm"];
        dataItem1.mClassCd = messages["prodRankMoms.mClassCd"];
        dataItem1.mClassNm = messages["prodRankMoms.mClassNm"];
        dataItem1.sClassCd = messages["prodRankMoms.sClassCd"];
        dataItem1.sClassNm = messages["prodRankMoms.sClassNm"];
        dataItem1.prodCd = messages["prodRankMoms.prodCd"];
        dataItem1.sideProdCd = messages["prodRankMoms.sideProdCd"];
        dataItem1.selTypeFg = messages["prodRankMoms.selTypeFg"];
        dataItem1.prodNm = messages["prodRankMoms.prodNm"];
        dataItem1.saleQty1 = messages["prodRankMoms.saleQty1"];
        dataItem1.saleQty2 = messages["prodRankMoms.saleQty2"];
        dataItem1.saleQty3 = messages["prodRankMoms.saleQty3"];
        dataItem1.totSaleAmt = messages["prodRankMoms.totSaleAmt"];
        dataItem1.dcAmt = messages["prodRankMoms.dcAmt"];
        dataItem1.realSaleAmt1 = messages["prodRankMoms.realSaleAmt1"];
        dataItem1.realSaleAmt2 = messages["prodRankMoms.realSaleAmt2"];
        dataItem1.realSaleAmt3 = messages["prodRankMoms.realSaleAmt3"];

        // header 셋팅
        for (var j = 0; j < vDlvrOrderFg.length; j++) {
            dataItem1[vDlvrOrderFg[j] + "SaleQty1"] = messages["prodRankMoms.saleQty1"];
            dataItem1[vDlvrOrderFg[j] + "SaleQty2"] = messages["prodRankMoms.saleQty2"];
            dataItem1[vDlvrOrderFg[j] + "SaleQty3"] = messages["prodRankMoms.saleQty3"];
            dataItem1[vDlvrOrderFg[j] + "RealSaleAmt1"] = messages["prodRankMoms.realSaleAmt1"];
            dataItem1[vDlvrOrderFg[j] + "RealSaleAmt2"] = messages["prodRankMoms.realSaleAmt2"];
            dataItem1[vDlvrOrderFg[j] + "RealSaleAmt3"] = messages["prodRankMoms.realSaleAmt3"];
        }

        // header 셋팅
        for (var j = 0; j < vDlvrOrderFg.length; j++) {
            for (var i = 0; i < arrDlvrInFgCol.length; i++) {
                dataItem1[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "SaleQty1"] = arrDlvrInFgColNm[i];
                dataItem1[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "SaleQty2"] = arrDlvrInFgColNm[i];
                dataItem1[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "SaleQty3"] = arrDlvrInFgColNm[i];
                dataItem1[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "RealSaleAmt1"] = arrDlvrInFgColNm[i];
                dataItem1[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "RealSaleAmt2"] = arrDlvrInFgColNm[i];
                dataItem1[vDlvrOrderFg[j] + 'Difg' + arrDlvrInFgCol[i] + "RealSaleAmt3"] = arrDlvrInFgColNm[i];
            }
        }
        s.columnHeaders.rows[1].dataItem = dataItem1;

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

            if ((panel.grid.columnHeaders.rows.length - 1) === r) {
              // 헤더의 전체선택 클릭 로직
              var flex   = panel.grid;
              var column = flex.columns[c];
              // check that this is a boolean column
              if (column.binding === 'gChk' || column.format === 'checkBox' || column.format === 'checkBoxText') {
                // prevent sorting on click
                column.allowSorting = false;
                // count true values to initialize checkbox
                var cnt             = 0;
                for (var i = 0; i < flex.rows.length; i++) {
                  if (flex.getCellData(i, c) === true) {
                    cnt++;
                  }
                }
                // create and initialize checkbox
                if (column.format === 'checkBoxText') {
                  cell.innerHTML = '<input id=\"' + column.binding + '\" type=\"checkbox\" class=\"wj-cell-check\" />'
                      + '<label for=\"' + column.binding + '\" class=\"wj-header-label\">' + cell.innerHTML + '</label>';
                } else {
                  cell.innerHTML = '<input type=\"checkbox\" class=\"wj-cell-check\" />';
                }
                var cb           = cell.firstChild;
                cb.checked       = cnt > 0;
                cb.indeterminate = cnt > 0 && cnt < flex.rows.length;
                // apply checkbox value to cells
                cb.addEventListener('click', function (e) {
                  flex.beginUpdate();
                  for (var i = 0; i < flex.rows.length; i++) {
                    if(!flex.rows[i].isReadOnly) {
                      flex.setCellData(i, c, cb.checked);
                    }
                  }
                  flex.endUpdate();
                });
              }
            }
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
        // <-- //그리드 헤더3줄 -->

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("prodSaleRateMomsCtrl");

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("prodSaleRateMomsExcelCtrl", function (event, data) {

        $scope.searchExcelList(data);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/prod/prodSaleRateMoms/prodSaleRateMoms/getProdSaleRateExcelList.sb", params, function (){

            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

           // 선택한 테이블에 따른 리스트 항목 visible
           var grid = wijmo.Control.getControl("#wjGridExcelList");
           var columns = grid.columns;

           // 컬럼 총갯수
            var columnsCnt = columns.length;

           for (var i = 0; i < columnsCnt; i++) {
               columns[i].visible = true;
           }

           // 일자표시옵션
           if(params.dayOption === "1"){  // 일자별
               columns[0].visible = true;
               columns[1].visible = true;
               columns[2].visible = false;
               columns[3].visible = false;
           } else if(params.dayOption === "2"){  // 기간합
               columns[0].visible = false;
               columns[1].visible = false;
               columns[2].visible = true;
               columns[3].visible = true;
           }

            // 상품표시옵션에 따른 컬럼 제어
           if(params.prodOption === "1"){  // 단품+세트

               // 총계
               columns[18].visible = false;
               columns[19].visible = false;
               columns[23].visible = false;
               columns[24].visible = false;

               // 내점,포장,배달 계
               for(j = 25 ; j < columnsCnt; j++){
                   if(j%3 !== 1){
                       columns[j].visible = false;
                   }
               }

           }else if(params.prodOption === "2"){   // 단품+구성

               // 총계
               columns[17].visible = false;
               columns[19].visible = false;
               columns[22].visible = false;
               columns[24].visible = false;

               // 내점,포장,배달 계
               for(j = 25 ; j < columnsCnt; j++){
                   if(j%3 < 2){
                       columns[j].visible = false;
                   }
               }

           }else if(params.prodOption === "3") {  // 단품+세트+구성

               // 총계
               columns[17].visible = false;
               columns[18].visible = false;
               columns[22].visible = false;
               columns[23].visible = false;

               // 내점,포장,배달 계
               for(j = 25 ; j < columnsCnt; j++){
                   if(0 < j%3){
                       columns[j].visible = false;
                   }
               }
           }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : true,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, messages["prodSaleRateMoms.prodSaleRateMoms"] + "_" + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });

    };

}]);