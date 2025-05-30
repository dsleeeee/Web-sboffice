/**
 * get application
 */
var app = agrid.getApp();

// 조회일자 일/월구분
var dayGubunComboData = [
    {"name":"전체","value":"A"},
    {"name":"패치일자","value":"P"}
];

app.controller('storeVerPatchCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeVerPatchCtrl', $scope, $http, true));

    $scope.srchPatchDate = wcombo.genDateVal("#srchPatchDate", gvStartDate);

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("selectVerCombo", selectVerComboList); // 버전체크
    $scope._setComboData("dayGubunCombo", dayGubunComboData);      // 조회일자 전체구분
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
        $scope._makePickColumns("storeVerPatchCtrl");

        // 그리드 DataMap 설정
        $scope.areaCdDataMap = new wijmo.grid.DataMap(areaCd, 'value', 'name'); // 지역
        $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFg, 'value', 'name'); // 용도
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name'); // 상태
        $scope.branchCdDataMap = new wijmo.grid.DataMap(branchCdComboList, 'value', 'name'); // 그룹

        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList, 'value', 'name'); // 팀별
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList, 'value', 'name'); // AC점포별
        $scope.momsAreaFgDataMap = new wijmo.grid.DataMap(momsAreaFgComboList, 'value', 'name'); // 지역구분
        $scope.momsCommercialDataMap = new wijmo.grid.DataMap(momsCommercialComboList, 'value', 'name'); // 상권
        $scope.momsShopTypeDataMap = new wijmo.grid.DataMap(momsShopTypeComboList, 'value', 'name'); // 점포유형
        $scope.momsStoreManageTypeDataMap = new wijmo.grid.DataMap(momsStoreManageTypeComboList, 'value', 'name'); // 매장관리타입
        $scope.momsStoreFg01DataMap = new wijmo.grid.DataMap(momsStoreFg01ComboList, 'value', 'name'); // 매장그룹
        $scope.momsStoreFg02DataMap = new wijmo.grid.DataMap(momsStoreFg02ComboList, 'value', 'name'); // 매장그룹2
        $scope.momsStoreFg03DataMap = new wijmo.grid.DataMap(momsStoreFg03ComboList, 'value', 'name'); // 매장그룹3
        $scope.momsStoreFg04DataMap = new wijmo.grid.DataMap(momsStoreFg04ComboList, 'value', 'name'); // 매장그룹4
        $scope.momsStoreFg05DataMap = new wijmo.grid.DataMap(momsStoreFg05ComboList, 'value', 'name'); // 매장그룹5

        // 조회일자 기본값 전체
        $scope.srchPatchDate.isReadOnly = true;

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.version                        = messages["storeVerPatch.version"];
        dataItem.mainRegStoreCntSale            = messages["storeVerPatch.patch"];
        dataItem.mainPatchStoreCntSale2         = messages["storeVerPatch.patch"];
        dataItem.mainRegNotPatchStoreCntSale    = messages["storeVerPatch.patch"];
        dataItem.regStoreCntSale                = messages["storeVerPatch.dtl.Sale"];
        dataItem.regPosCntSale                  = messages["storeVerPatch.dtl.Sale"];
        dataItem.patchStoreCntSale              = messages["storeVerPatch.dtl.Sale"];
        dataItem.patchPosCntSale                = messages["storeVerPatch.dtl.Sale"];
        dataItem.notPatchStoreCntSale           = messages["storeVerPatch.dtl.Sale"];
        dataItem.notPatchPosCntSale             = messages["storeVerPatch.dtl.Sale"];
        dataItem.mainPatchStoreCntSale          = messages["storeVerPatch.dtl.Sale"];
        dataItem.mainNotPatchStoreCntSale       = messages["storeVerPatch.dtl.Sale"];
        dataItem.regStoreCnt                    = messages["storeVerPatch.dtl"];
        dataItem.regPosCnt                      = messages["storeVerPatch.dtl"];
        dataItem.patchStoreCnt                  = messages["storeVerPatch.dtl"];
        dataItem.patchPosCnt                    = messages["storeVerPatch.dtl"];
        dataItem.notPatchStoreCnt               = messages["storeVerPatch.dtl"];
        dataItem.notPatchPosCnt                 = messages["storeVerPatch.dtl"];
        dataItem.mainPatchStoreCnt              = messages["storeVerPatch.dtl"];
        dataItem.mainNotPatchStoreCnt           = messages["storeVerPatch.dtl"];

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
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                // 패치완료, 패치미완료
                if (col.binding === "mainPatchStoreCntSale2" || col.binding === "mainRegNotPatchStoreCntSale") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                //  수량합계 클릭시 상세정보 조회
                if ( col.binding === "mainPatchStoreCntSale2" || col.binding === "mainRegNotPatchStoreCntSale") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params      = {};
                    params.selectVerCd  = selectedRow.version;
                    if(!$scope.srchPatchDate.isReadOnly){
                        params.patchDate = wijmo.Globalize.format($scope.srchPatchDate.value, 'yyyyMMdd');
                    }
                    if(col.binding === "mainPatchStoreCntSale2"){
                        params.patchFg = 'Y';
                    }else{
                        params.patchFg = 'N';
                    }
                    params.prodHqBrandCd        = $scope.prodHqBrandCd;
                    params.momsTeam             = $scope.momsTeam;
                    params.momsAcShop           = $scope.momsAcShop;
                    params.momsAreaFg           = $scope.momsAreaFg;
                    params.momsCommercial       = $scope.momsCommercial;
                    params.momsShopType         = $scope.momsShopType;
                    params.momsStoreManageType  = $scope.momsStoreManageType;
                    params.branchCd             = $scope.branchCd;
                    params.momsStoreFg01 = $scope.momsStoreFg01;
                    params.momsStoreFg02 = $scope.momsStoreFg02;
                    params.momsStoreFg03 = $scope.momsStoreFg03;
                    params.momsStoreFg04 = $scope.momsStoreFg04;
                    params.momsStoreFg05 = $scope.momsStoreFg05;
                    $scope._broadcast('patchStoreDtlCtrl', params);
                }
            }
        });


    };

    // 일/월 구분 콤보박스 선택 이벤트
    $scope.setDayGubunCombo = function (s) {
        if(s.selectedValue === "A") {
            $scope.srchPatchDate.isReadOnly = true;
        } else if(s.selectedValue === "P") {
            $scope.srchPatchDate.isReadOnly = false;
        }
    };



    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("storeVerPatchCtrl", function (event, data) {
        $scope.searchStoreVerPatchList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });


    // 리스트 조회
    $scope.searchStoreVerPatchList = function () {

        // 파라미터
        var params       = {};
        var verCd = $scope.selectVer.indexOf("]");
        params.selectVerCd          =  $scope.selectVer.substring(1,verCd);
        $scope.selectVerCd          = params.selectVerCd;
        if(!$scope.srchPatchDate.isReadOnly){
            params.patchDate            = wijmo.Globalize.format($scope.srchPatchDate.value, 'yyyyMMdd');
        }
        params.prodHqBrandCd        = $scope.prodHqBrandCd;
        params.momsTeam             = $scope.momsTeam;
        params.momsAcShop           = $scope.momsAcShop;
        params.momsAreaFg           = $scope.momsAreaFg;
        params.momsCommercial       = $scope.momsCommercial;
        params.momsShopType         = $scope.momsShopType;
        params.momsStoreManageType  = $scope.momsStoreManageType;
        params.branchCd             = $scope.branchCd;
        // params.storeHqBrandCd = $scope.storeHqBrandCd;
        // // '전체' 일때
        // if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
        //     var momsHqBrandCd = "";
        //     for(var i=0; i < momsHqBrandCdComboList.length; i++){
        //         if(momsHqBrandCdComboList[i].value !== null) {
        //             momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
        //         }
        //     }
        //     params.userBrands = momsHqBrandCd;
        // }
        params.momsStoreFg01 = $scope.momsStoreFg01;
        params.momsStoreFg02 = $scope.momsStoreFg02;
        params.momsStoreFg03 = $scope.momsStoreFg03;
        params.momsStoreFg04 = $scope.momsStoreFg04;
        params.momsStoreFg05 = $scope.momsStoreFg05;
        params.listScale = 100;

        console.log(params);

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/store/storeMoms/storeVerPatch/storeVerPatch/getStoreVerPatchList.sb", params, function (){

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

    // 엑셀 다운로드
    $scope.excelDownloadInfo = function () {

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
                messages["storeVerPatch.storeVerPatch"] + '_' + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

}]);
