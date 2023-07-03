/****************************************************************
 *
 * 파일명 : saleDtlChannel.js
 * 설  명 : 매출상세현황(채널별) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.12.28     권지현      1.0
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

/** controller */
app.controller('saleDtlChannelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleDtlChannelCtrl', $scope, $http, true));

    // 조회일자
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 그리드 매출구분
    $scope.saleYnMap = new wijmo.grid.DataMap([
        {id: "Y", name: messages["todayBillSaleDtl.saleY"]},
        {id: "N", name: messages["todayBillSaleDtl.saleN"]}
    ], 'id', 'name');

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("srchProdOption", prodOptionComboData);                   // 상품표시옵션
    $scope._setComboData("srchStoreHqBrandCd", momsHqBrandCdComboList);            // 매장브랜드
    $scope._setComboData("srchProdHqBrand", momsHqBrandCdComboList);               // 상품브랜드
    $scope._setComboData("srchMomsTeam", momsTeamComboList);                       // 팀별
    $scope._setComboData("srchMomsAcShop", momsAcShopComboList);                   // AC점포별
    $scope._setComboData("srchMomsAreaFg", momsAreaFgComboList);                   // 지역구분
    $scope._setComboData("srchMomsCommercial", momsCommercialComboList);           // 상권
    $scope._setComboData("srchMomsShopType", momsShopTypeComboList);               // 점포유형
    $scope._setComboData("srchMomsStoreManageType", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("srchBranchCd", branchCdComboList);                       // 그룹

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
    // // 그룹
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
        dataItem.branchNm       = messages["prodRankMoms.branchNm"];
        dataItem.storeCd        = messages["todayBillSaleDtl.storeCd"];
        dataItem.storeNm        = messages["todayBillSaleDtl.storeNm"];
        dataItem.saleDate       = messages["todayBillSaleDtl.saleDate"];
        dataItem.posNo          = messages["todayBillSaleDtl.posNo"];
        dataItem.billNo         = messages["todayBillSaleDtl.billNo"];
        dataItem.saleYn         = messages["todayBillSaleDtl.saleYn"];
        dataItem.tblNm          = messages["todayBillSaleDtl.tblNm"];
        dataItem.billDt         = messages["todayBillSaleDtl.billDt"];
        dataItem.lClassCd       = messages["saleDtlChannel.lClassCd"];
        dataItem.lClassNm       = messages["saleDtlChannel.lClassNm"];
        dataItem.mClassCd       = messages["saleDtlChannel.mClassCd"];
        dataItem.mClassNm       = messages["saleDtlChannel.mClassNm"];
        dataItem.sClassCd       = messages["saleDtlChannel.sClassCd"];
        dataItem.sClassNm       = messages["saleDtlChannel.sClassNm"];
        dataItem.prodCd         = messages["todayBillSaleDtl.prodCd"];
        dataItem.sideProdCd     = messages["prodRankMoms.sideProdCd"];
        dataItem.selTypeFg      = messages["prodRankMoms.selTypeFg"];
        dataItem.prodNm         = messages["prodRankMoms.prodNm"];
        
        dataItem.saleQty1       = messages["prodRankMoms.totSaleQty"];
        dataItem.saleQty2       = messages["prodRankMoms.totSaleQty"];
        dataItem.saleQty3       = messages["prodRankMoms.totSaleQty"];
        dataItem.totSaleAmt1    = messages["prodRankMoms.totSaleAmt"];
        dataItem.totSaleAmt2    = messages["prodRankMoms.totSaleAmt"];
        dataItem.totSaleAmt3    = messages["prodRankMoms.totSaleAmt"];
        dataItem.dcAmt1         = messages["saleDtlChannel.dcAmt"];
        dataItem.dcAmt2         = messages["saleDtlChannel.dcAmt"];
        dataItem.dcAmt3         = messages["saleDtlChannel.dcAmt"];
        dataItem.realSaleAmt1   = messages["prodRankMoms.realSaleAmt"];
        dataItem.realSaleAmt2   = messages["prodRankMoms.realSaleAmt"];
        dataItem.realSaleAmt3   = messages["prodRankMoms.realSaleAmt"];
        dataItem.gaAmt1         = messages["saleDtlChannel.gaAmt"];
        dataItem.gaAmt2         = messages["saleDtlChannel.gaAmt"];
        dataItem.gaAmt3         = messages["saleDtlChannel.gaAmt"];
        dataItem.vatAmt1        = messages["saleDtlChannel.vatAmt"];
        dataItem.vatAmt2        = messages["saleDtlChannel.vatAmt"];
        dataItem.vatAmt3        = messages["saleDtlChannel.vatAmt"];

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
        dataItem1.branchNm       = messages["prodRankMoms.branchNm"];
        dataItem1.storeCd        = messages["todayBillSaleDtl.storeCd"];
        dataItem1.storeNm        = messages["todayBillSaleDtl.storeNm"];
        dataItem1.saleDate       = messages["todayBillSaleDtl.saleDate"];
        dataItem1.posNo          = messages["todayBillSaleDtl.posNo"];
        dataItem1.billNo         = messages["todayBillSaleDtl.billNo"];
        dataItem1.saleYn         = messages["todayBillSaleDtl.saleYn"];
        dataItem1.tblNm          = messages["todayBillSaleDtl.tblNm"];
        dataItem1.billDt         = messages["todayBillSaleDtl.billDt"];
        dataItem1.lClassCd       = messages["saleDtlChannel.lClassCd"];
        dataItem1.lClassNm       = messages["saleDtlChannel.lClassNm"];
        dataItem1.mClassCd       = messages["saleDtlChannel.mClassCd"];
        dataItem1.mClassNm       = messages["saleDtlChannel.mClassNm"];
        dataItem1.sClassCd       = messages["saleDtlChannel.sClassCd"];
        dataItem1.sClassNm       = messages["saleDtlChannel.sClassNm"];
        dataItem1.prodCd         = messages["todayBillSaleDtl.prodCd"];
        dataItem1.sideProdCd     = messages["prodRankMoms.sideProdCd"];
        dataItem1.selTypeFg      = messages["prodRankMoms.selTypeFg"];
        dataItem1.prodNm         = messages["prodRankMoms.prodNm"];

        dataItem1.saleQty1       = messages["prodRankMoms.saleQty1"];
        dataItem1.saleQty2       = messages["prodRankMoms.saleQty2"];
        dataItem1.saleQty3       = messages["prodRankMoms.saleQty3"];
        dataItem1.totSaleAmt1    = messages["saleDtlChannel.totSaleAmt1"];
        dataItem1.totSaleAmt2    = messages["saleDtlChannel.totSaleAmt2"];
        dataItem1.totSaleAmt3    = messages["saleDtlChannel.totSaleAmt3"];
        dataItem1.dcAmt1         = messages["saleDtlChannel.dcAmt1"];
        dataItem1.dcAmt2         = messages["saleDtlChannel.dcAmt2"];
        dataItem1.dcAmt3         = messages["saleDtlChannel.dcAmt3"];
        dataItem1.realSaleAmt1   = messages["prodRankMoms.realSaleAmt1"];
        dataItem1.realSaleAmt2   = messages["prodRankMoms.realSaleAmt2"];
        dataItem1.realSaleAmt3   = messages["prodRankMoms.realSaleAmt3"];
        dataItem1.gaAmt1         = messages["saleDtlChannel.gaAmt1"];
        dataItem1.gaAmt2         = messages["saleDtlChannel.gaAmt2"];
        dataItem1.gaAmt3         = messages["saleDtlChannel.gaAmt3"];
        dataItem1.vatAmt1        = messages["saleDtlChannel.vatAmt1"];
        dataItem1.vatAmt2        = messages["saleDtlChannel.vatAmt2"];
        dataItem1.vatAmt3        = messages["saleDtlChannel.vatAmt3"];

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
        $scope._makePickColumns("saleDtlChannelCtrl");

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("saleDtlChannelCtrl", function (event, data) {
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

       // 조회일자 최대 1일 제한
       if (diffDay > 0) {
           s_alert.pop(messages['cmm.dateOver.1day.error']);
           return false;
       }

       // 파라미터
       var params = {};
       params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
       params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
       params.prodClassCd = $scope.prodClassCd;
       params.prodOption = $scope.srchProdOptionCombo.selectedValue;
       params.prodCd = $("#srchProdCd").val();
       params.prodNm = $("#srchProdNm").val();
       params.prodCds = $("#saleDtlChannelSelectCd").val();
       params.dlvrInFgCol = dlvrInFgCol;
       params.listScale = 500; //-페이지 스케일 갯수

       if(orgnFg === "HQ"){
           params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
           params.storeCds = $("#saleDtlChannelStoreCd").val();
           params.prodHqBrandCd = $scope.srchProdHqBrandCombo.selectedValue;
           params.momsTeam = $scope.srchMomsTeamCombo.selectedValue;
           params.momsAcShop = $scope.srchMomsAcShopCombo.selectedValue;
           params.momsAreaFg = $scope.srchMomsAreaFgCombo.selectedValue;
           params.momsCommercial = $scope.srchMomsCommercialCombo.selectedValue;
           params.momsShopType = $scope.srchMomsShopTypeCombo.selectedValue;
           params.momsStoreManageType = $scope.srchMomsStoreManageTypeCombo.selectedValue;
           params.branchCd = $scope.srchBranchCdCombo.selectedValue;

           // '전체' 일때
           if(params.storeHqBrandCd === "" || params.prodHqBrandCd === null || params.prodHqBrandCd === "" || params.storeHqBrandCd === null) {
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
       $scope._inquiryMain("/sale/prod/saleDtlChannel/saleDtlChannel/getSaleDtlChannelList.sb", params, function (){

           // 선택한 테이블에 따른 리스트 항목 visible
           var grid = wijmo.Control.getControl("#wjGridList");
           var columns = grid.columns;

           // 컬럼 총갯수
           var columnsCnt = columns.length;

           for (var i = 0; i < columnsCnt; i++) {
               columns[i].visible = true;
           }

           // 상품표시옵션에 따른 컬럼 제어
           if(params.prodOption === "1"){  // 단품+세트
               // 내점,포장,배달 계
               for(j = 19 ; j < columnsCnt; j++){
                   if(j%3 !== 1){
                       columns[j].visible = false;
                   }
               }
           }else if(params.prodOption === "2"){   // 단품+구성
               // 내점,포장,배달 계
               for(j = 19 ; j < columnsCnt; j++){
                   if(j%3 < 2){
                       columns[j].visible = false;
                   }
               }
           }else if(params.prodOption === "3") {  // 단품+세트+구성
               // 내점,포장,배달 계
               for(j = 19 ; j < columnsCnt; j++){
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

       // 조회일자 최대 1일 제한
       if (diffDay > 0) {
           s_alert.pop(messages['cmm.dateOver.1day.error']);
           return false;
       }

       // 파라미터
       var params = {};
       params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
       params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
       params.prodClassCd = $scope.prodClassCd;
       params.prodOption = $scope.srchProdOptionCombo.selectedValue;
       params.prodCd = $("#srchProdCd").val();
       params.prodNm = $("#srchProdNm").val();
       params.prodCds = $("#saleDtlChannelSelectCd").val();
       params.dlvrInFgCol = dlvrInFgCol;

       if(orgnFg === "HQ"){
           params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
           params.storeCds = $("#saleDtlChannelStoreCd").val();
           params.prodHqBrandCd = $scope.srchProdHqBrandCombo.selectedValue;
           params.momsTeam = $scope.srchMomsTeamCombo.selectedValue;
           params.momsAcShop = $scope.srchMomsAcShopCombo.selectedValue;
           params.momsAreaFg = $scope.srchMomsAreaFgCombo.selectedValue;
           params.momsCommercial = $scope.srchMomsCommercialCombo.selectedValue;
           params.momsShopType = $scope.srchMomsShopTypeCombo.selectedValue;
           params.momsStoreManageType = $scope.srchMomsStoreManageTypeCombo.selectedValue;
           params.branchCd = $scope.srchBranchCdCombo.selectedValue;

           // '전체' 일때
           if(params.storeHqBrandCd === "" || params.prodHqBrandCd === null || params.prodHqBrandCd === "" || params.storeHqBrandCd === null) {
             var momsHqBrandCd = "";
             for(var i=0; i < momsHqBrandCdComboList.length; i++){
               if(momsHqBrandCdComboList[i].value !== null) {
                 momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
               }
             }
             params.userBrands = momsHqBrandCd;
           }
       }

        $scope._broadcast('saleDtlChannelExcelCtrl2', params);
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
    $scope.saleDtlChannelStoreShow = function () {
        $scope._broadcast('saleDtlChannelStoreCtrl');
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.saleDtlChannelSelectShow = function () {
        $scope._broadcast('saleDtlChannelSelectCtrl');
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
app.controller('saleDtlChannelExcelCtrl2', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleDtlChannelExcelCtrl2', $scope, $http, false));

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
        dataItem.branchNm       = messages["prodRankMoms.branchNm"];
        dataItem.storeCd        = messages["todayBillSaleDtl.storeCd"];
        dataItem.storeNm        = messages["todayBillSaleDtl.storeNm"];
        dataItem.saleDate       = messages["todayBillSaleDtl.saleDate"];
        dataItem.posNo          = messages["todayBillSaleDtl.posNo"];
        dataItem.billNo         = messages["todayBillSaleDtl.billNo"];
        dataItem.saleYn         = messages["todayBillSaleDtl.saleYn"];
        dataItem.tblNm          = messages["todayBillSaleDtl.tblNm"];
        dataItem.billDt         = messages["todayBillSaleDtl.billDt"];
        dataItem.lClassCd       = messages["saleDtlChannel.lClassCd"];
        dataItem.lClassNm       = messages["saleDtlChannel.lClassNm"];
        dataItem.mClassCd       = messages["saleDtlChannel.mClassCd"];
        dataItem.mClassNm       = messages["saleDtlChannel.mClassNm"];
        dataItem.sClassCd       = messages["saleDtlChannel.sClassCd"];
        dataItem.sClassNm       = messages["saleDtlChannel.sClassNm"];
        dataItem.prodCd         = messages["todayBillSaleDtl.prodCd"];
        dataItem.sideProdCd     = messages["prodRankMoms.sideProdCd"];
        dataItem.selTypeFg      = messages["prodRankMoms.selTypeFg"];
        dataItem.prodNm         = messages["prodRankMoms.prodNm"];

        dataItem.saleQty1       = messages["prodRankMoms.totSaleQty"];
        dataItem.saleQty2       = messages["prodRankMoms.totSaleQty"];
        dataItem.saleQty3       = messages["prodRankMoms.totSaleQty"];
        dataItem.totSaleAmt1    = messages["prodRankMoms.totSaleAmt"];
        dataItem.totSaleAmt2    = messages["prodRankMoms.totSaleAmt"];
        dataItem.totSaleAmt3    = messages["prodRankMoms.totSaleAmt"];
        dataItem.dcAmt1         = messages["saleDtlChannel.dcAmt"];
        dataItem.dcAmt2         = messages["saleDtlChannel.dcAmt"];
        dataItem.dcAmt3         = messages["saleDtlChannel.dcAmt"];
        dataItem.realSaleAmt1   = messages["prodRankMoms.realSaleAmt"];
        dataItem.realSaleAmt2   = messages["prodRankMoms.realSaleAmt"];
        dataItem.realSaleAmt3   = messages["prodRankMoms.realSaleAmt"];
        dataItem.gaAmt1         = messages["saleDtlChannel.gaAmt"];
        dataItem.gaAmt2         = messages["saleDtlChannel.gaAmt"];
        dataItem.gaAmt3         = messages["saleDtlChannel.gaAmt"];
        dataItem.vatAmt1        = messages["saleDtlChannel.vatAmt"];
        dataItem.vatAmt2        = messages["saleDtlChannel.vatAmt"];
        dataItem.vatAmt3        = messages["saleDtlChannel.vatAmt"];

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
        dataItem1.branchNm       = messages["prodRankMoms.branchNm"];
        dataItem1.storeCd        = messages["todayBillSaleDtl.storeCd"];
        dataItem1.storeNm        = messages["todayBillSaleDtl.storeNm"];
        dataItem1.saleDate       = messages["todayBillSaleDtl.saleDate"];
        dataItem1.posNo          = messages["todayBillSaleDtl.posNo"];
        dataItem1.billNo         = messages["todayBillSaleDtl.billNo"];
        dataItem1.saleYn         = messages["todayBillSaleDtl.saleYn"];
        dataItem1.tblNm          = messages["todayBillSaleDtl.tblNm"];
        dataItem1.billDt         = messages["todayBillSaleDtl.billDt"];
        dataItem1.lClassCd       = messages["saleDtlChannel.lClassCd"];
        dataItem1.lClassNm       = messages["saleDtlChannel.lClassNm"];
        dataItem1.mClassCd       = messages["saleDtlChannel.mClassCd"];
        dataItem1.mClassNm       = messages["saleDtlChannel.mClassNm"];
        dataItem1.sClassCd       = messages["saleDtlChannel.sClassCd"];
        dataItem1.sClassNm       = messages["saleDtlChannel.sClassNm"];
        dataItem1.prodCd         = messages["todayBillSaleDtl.prodCd"];
        dataItem1.sideProdCd     = messages["prodRankMoms.sideProdCd"];
        dataItem1.selTypeFg      = messages["prodRankMoms.selTypeFg"];
        dataItem1.prodNm         = messages["prodRankMoms.prodNm"];

        dataItem1.saleQty1       = messages["prodRankMoms.saleQty1"];
        dataItem1.saleQty2       = messages["prodRankMoms.saleQty2"];
        dataItem1.saleQty3       = messages["prodRankMoms.saleQty3"];
        dataItem1.totSaleAmt1    = messages["saleDtlChannel.totSaleAmt1"];
        dataItem1.totSaleAmt2    = messages["saleDtlChannel.totSaleAmt2"];
        dataItem1.totSaleAmt3    = messages["saleDtlChannel.totSaleAmt3"];
        dataItem1.dcAmt1         = messages["saleDtlChannel.dcAmt1"];
        dataItem1.dcAmt2         = messages["saleDtlChannel.dcAmt2"];
        dataItem1.dcAmt3         = messages["saleDtlChannel.dcAmt3"];
        dataItem1.realSaleAmt1   = messages["prodRankMoms.realSaleAmt1"];
        dataItem1.realSaleAmt2   = messages["prodRankMoms.realSaleAmt2"];
        dataItem1.realSaleAmt3   = messages["prodRankMoms.realSaleAmt3"];
        dataItem1.gaAmt1         = messages["saleDtlChannel.gaAmt1"];
        dataItem1.gaAmt2         = messages["saleDtlChannel.gaAmt2"];
        dataItem1.gaAmt3         = messages["saleDtlChannel.gaAmt3"];
        dataItem1.vatAmt1        = messages["saleDtlChannel.vatAmt1"];
        dataItem1.vatAmt2        = messages["saleDtlChannel.vatAmt2"];
        dataItem1.vatAmt3        = messages["saleDtlChannel.vatAmt3"];

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
        $scope._makePickColumns("saleDtlChannelCtrl");

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("saleDtlChannelExcelCtrl2", function (event, data) {

        $scope.searchExcelList(data);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    var totRowCnt;
    var totExceCnt;

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {
        // 총 row 수 가져오려고 1로 조회
        params.listScale = 1;

        $scope._postJSONQuery.withOutPopUp('/sale/prod/saleDtlChannel/saleDtlChannel/getSaleDtlChannelList.sb', params, function(result) {
            totRowCnt = result.data.data.list[0].totCnt;
            totExceCnt = Math.ceil(totRowCnt / 5000);
            console.log("----- result -----");
            console.log(totRowCnt);
            console.log(totExceCnt);

            if(totRowCnt === 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.excelUploadingPopup(true);
            download(totExceCnt, params);
        });
    };

    async function download(totExceCnt, params) {
        const downloadChk = () =>
            new Promise((resolve) => {
                // 가상로그인 대응한 session id 설정
                if (document.getElementsByName('sessionId')[0]) {
                    params['sid'] = document.getElementsByName('sessionId')[0].value;
                }

                // ajax 통신 설정
                $http({
                    method: 'POST', //방식
                    url: '/sale/prod/saleDtlChannel/saleDtlChannel/getSaleDtlChannelList.sb', /* 통신할 URL */
                    params: params, /* 파라메터로 보낼 데이터 */
                    headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
                }).then(function successCallback(response) {
                    if ($scope._httpStatusCheck(response, true)) {
                        // this callback will be called asynchronously
                        // when the response is available
                        var list = response.data.data.list;
                        if (list.length === undefined || list.length === 0) {
                            $scope.data = new wijmo.collections.CollectionView([]);
                            $scope.excelUploadingPopup(false);
                            return false;
                        }

                        var data = new wijmo.collections.CollectionView(list);
                        data.trackChanges = true;
                        $scope.data = data;
                    }
                }, function errorCallback(response) {
                    // 로딩팝업 hide
                    $scope.excelUploadingPopup(false);
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                    if (response.data.message) {
                        $scope._popMsg(response.data.message);
                    } else {
                        $scope._popMsg(messages['cmm.error']);
                    }
                    return false;
                }).then(function () {
                    // 'complete' code here
                    setTimeout(function () {
                        if ($scope.excelFlex.rows.length <= 0) {
                            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                            $scope.excelUploadingPopup(false);
                            return false;
                        }
                        // 선택한 테이블에 따른 리스트 항목 visible
                        var grid = wijmo.Control.getControl("#wjGridExcelList");
                        var columns = grid.columns;

                        // 컬럼 총갯수
                        var columnsCnt = columns.length - 2; // totCnt, rnum뺌

                        for (var i = 0; i < columnsCnt; i++) {
                            columns[i].visible = true;
                        }

                        // 상품표시옵션에 따른 컬럼 제어
                        if (params.prodOption === "1") {  // 단품+세트
                            // 내점,포장,배달 계
                            for (j = 19; j < columnsCnt; j++) {
                                if (j % 3 !== 1) {
                                    columns[j].visible = false;
                                }
                            }
                        } else if (params.prodOption === "2") {   // 단품+구성
                            // 내점,포장,배달 계
                            for (j = 19; j < columnsCnt; j++) {
                                if (j % 3 < 2) {
                                    columns[j].visible = false;
                                }
                            }
                        } else if (params.prodOption === "3") {  // 단품+세트+구성
                            // 내점,포장,배달 계
                            for (j = 19; j < columnsCnt; j++) {
                                if (0 < j % 3) {
                                    columns[j].visible = false;
                                }
                            }
                        }

                        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                            includeColumnHeaders: true,
                            includeCellStyles   : false,
                            includeColumns      : function (column) {
                                return column.visible;
                            }
                        },  messages["saleDtlChannel.saleDtlChannel"] + '_' + params.startDate + '_' + params.endDate + '_' + getCurDateTime() + '_' +'.xlsx', function () {
                            $timeout(function () {
                                if(i === totExceCnt){ // 마지막 파일의 다운로드가 완료되면 로딩팝업 hide
                                    $scope.excelUploadingPopup(false);
                                }
                                $timeout(function () {
                                    resolve();
                                }, 500);
                            }, 1000);
                        });
                    }, 1000);
                });
            });

        for (let i = 1; i <= totExceCnt; i++) {

            $("#totalRows").html(totExceCnt);
            $("#progressCnt").html(i);

            params.listScale = 5000 * i;
            params.offset = (5000 * i) - 4999;
            console.log(params.offset + ' ~ ' + params.listScale);

            const result = await downloadChk();
            if(totExceCnt === i){
                console.log("다운로드 끝!");
                $scope.excelUploadingPopup(false);
            }
        }
    }

    // 작업내역 로딩 팝업
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            // 팝업내용 동적 생성
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['touchKey.loading.msg'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 진행 중...</div>';
            innerHtml += '<p><img src=\"/resource/solbipos/css/img/loading.gif\" alt=\"\" /></p></div>';
            // html 적용
            $scope._loadingPopup.content.innerHTML = innerHtml;
            // 팝업 show
            $scope._loadingPopup.show(true);
        } else {
            $scope._loadingPopup.hide(true);
        }
    };
}]);