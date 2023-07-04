/****************************************************************
 *
 * 파일명 : dayProdStore.js
 * 설  명 : 맘스터치 > 상품매출분석 > 일별 상품 매출 현황(매장별) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.03.23     권지현      1.0
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

/**
 *  일별 상품 매출 현황 그리드 생성
 */
app.controller('dayProdStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayProdStoreCtrl', $scope, $http, false));

    // 조회일자
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 콤보박스 셋팅
    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("prodHqBrandCdCombo", momsHqBrandCdComboList); // 상품브랜드
    $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹
    $scope._setComboData("prodOptionCombo", prodOptionComboData); // 상품표시옵션
    $scope._setComboData("dayOptionCombo", dayOptionComboData); // 일자표시옵션

    // 그리드 매출구분
    $scope.saleFgMap = new wijmo.grid.DataMap([
        {id: "1", name: messages["todayBillSaleDtl.saleY"]},
        {id: "-1", name: messages["todayBillSaleDtl.saleN"]}
    ], 'id', 'name');

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

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("dayProdStoreCtrl", function (event, data) {
        $scope.searchDayProdList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 리스트 조회
    $scope.searchDayProdList = function () {
        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 3일 제한
        if (diffDay > 2) {
            $scope._popMsg(messages['cmm.dateOver.3day.error']);
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        params.storeCds = $("#dayProdStoreStoreCd").val();
        params.prodCds = $("#dayProdSelectCd").val();
        params.prodHqBrandCd = $scope.prodHqBrandCd;
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null || params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        params.prodOption = $scope.prodOption;
        params.dayOption = $scope.dayOption;
        params.listScale = 500;

        console.log(params);

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/prod/dayProdStore/dayProdStore/getDayProdStoreList.sb", params, function (){
            // <-- 그리드 visible -->
            // 선택한 테이블에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridList");
            var columns = grid.columns;

            // 컬럼 총갯수
            var columnsCnt = columns.length;

            for (var i = 0; i < columnsCnt; i++) {
                columns[i].visible = true;
            }

            // 합계가 0이면 해당 컬럼 숨기기
            for (var j = 0; j < columnsCnt; j++) {
                // 상품표시옵션
                if(params.prodOption === "1"){  // 단품+세트
                    if(columns[j].binding == "saleQty2" || columns[j].binding == "saleQty3" || columns[j].binding == "realSaleAmt2" || columns[j].binding == "realSaleAmt3") {
                        columns[j].visible = false;
                    }
                } else if(params.prodOption === "2"){  // 단품+구성
                    if(columns[j].binding == "saleQty1" || columns[j].binding == "saleQty3" || columns[j].binding == "realSaleAmt1" || columns[j].binding == "realSaleAmt3") {
                        columns[j].visible = false;
                    }
                } else if(params.prodOption === "3"){  // 단품+세트+구성
                    if(columns[j].binding == "saleQty1" || columns[j].binding == "saleQty2" || columns[j].binding == "realSaleAmt1" || columns[j].binding == "realSaleAmt2") {
                        columns[j].visible = false;
                    }
                }

                // 일자표시옵션
                if(params.dayOption === "1"){  // 일자별
                    if(columns[j].binding == "dayFrom" || columns[j].binding == "dayTo") {
                        columns[j].visible = false;
                    }
                } else if(params.dayOption === "2"){  // 기간합
                    if(columns[j].binding == "saleDate" || columns[j].binding == "yoil") {
                        columns[j].visible = false;
                    }
                }
            }
            // <-- //그리드 visible -->
        });
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.dayProdStoreStoreShow = function () {
        $scope._broadcast('dayProdStoreStoreCtrl');
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.dayProdSelectShow = function () {
        $scope._broadcast('dayProdSelectCtrl');
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
  
    // 조회조건/분할 엑셀다운로드
    $scope.excelDownload = function (excelType) {

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
            $scope._popMsg(messages['cmm.dateOver.1day.error']);
            return false;
        }
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        params.storeCds = $("#dayProdStoreStoreCd").val();
        params.prodCds = $("#dayProdSelectCd").val();
        params.prodHqBrandCd = $scope.prodHqBrandCd;
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null || params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        params.prodOption = $scope.prodOption;
        params.dayOption = $scope.dayOption;
        params.excelType = excelType;

        $scope._broadcast('dayProdStoreExcelCtrl', params);
    };

    // 현재화면 엑셀다운로드
    $scope.excelDownload2 = function () {

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
           $scope._popMsg(messages['cmm.dateOver.1day.error']);
           return false;
        }
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
             "일별상품매출현황(매장별)" + '_' +  wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd') + '_' + wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd') + '_' + getCurDateTime()+'.xlsx', function () {
           $timeout(function () {
             $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
           }, 10);
         });
        }, 10);
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };

}]);


/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('dayProdStoreExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayProdStoreExcelCtrl', $scope, $http, false));

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

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("dayProdStoreExcelCtrl", function (event, data) {

        if(data.excelType === '1') {
            $scope.searchExcelList(data);
        }else{
            $scope.searchExcelDivisionList(data);
        }
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/prod/dayProdStore/dayProdStore/getDayProdStoreExcelList.sb", params, function() {
            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            // <-- 그리드 visible -->
            // 선택한 테이블에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridExcelList");
            var columns = grid.columns;

            // 컬럼 총갯수
            var columnsCnt = columns.length;

            for (var i = 0; i < columnsCnt; i++) {
                columns[i].visible = true;
            }

            // 합계가 0이면 해당 컬럼 숨기기
            for (var j = 0; j < columnsCnt; j++) {
                // 상품표시옵션
                if(params.prodOption === "1"){  // 단품+세트
                    if(columns[j].binding == "saleQty2" || columns[j].binding == "saleQty3" || columns[j].binding == "realSaleAmt2" || columns[j].binding == "realSaleAmt3") {
                        columns[j].visible = false;
                    }
                } else if(params.prodOption === "2"){  // 단품+구성
                    if(columns[j].binding == "saleQty1" || columns[j].binding == "saleQty3" || columns[j].binding == "realSaleAmt1" || columns[j].binding == "realSaleAmt3") {
                        columns[j].visible = false;
                    }
                } else if(params.prodOption === "3"){  // 단품+세트+구성
                    if(columns[j].binding == "saleQty1" || columns[j].binding == "saleQty2" || columns[j].binding == "realSaleAmt1" || columns[j].binding == "realSaleAmt2") {
                        columns[j].visible = false;
                    }
                }

                // 일자표시옵션
                if(params.dayOption === "1"){  // 일자별
                    if(columns[j].binding == "dayFrom" || columns[j].binding == "dayTo") {
                        columns[j].visible = false;
                    }
                } else if(params.dayOption === "2"){  // 기간합
                    if(columns[j].binding == "saleDate" || columns[j].binding == "yoil") {
                        columns[j].visible = false;
                    }
                }
            }
            // <-- //그리드 visible -->

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : false,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, "일별상품매출현황(매장별)_" + params.startDate + "_" + params.endDate + "_" + getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };

    // 분할 엑셀 리스트 조회
    $scope.searchExcelDivisionList = function (params) {

        // 다운로드 시작이면 작업내역 로딩 팝업 오픈
        $scope.excelUploadingPopup(true);
        $("#totalRows").html(0);

        // 전체 데이터 수
        var listSize = 0;
        // 다운로드 되는 총 엑셀파일 수
        var totFileCnt = 0;

        // 전체 데이터 수 조회
        params.limit = 1;
        params.offset = 1;
        $scope._postJSONQuery.withOutPopUp( "/sale/prod/dayProdStore/dayProdStore/getDayProdStoreList.sb", params, function(response){

            listSize = response.data.data.list[0].totCnt;
            totFileCnt = Math.ceil(listSize/35000); // 하나의 엑셀파일에 35000개씩 다운로드

            if(listSize === 0 || totFileCnt === 0){
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                $scope.excelUploadingPopup(false);
                return false;
            };

            // 다운로드 될 전체 파일 갯수 셋팅
            $("#totalRows").html(totFileCnt);

            // 엑셀 분할 다운로드
            function delay(x){
                return new Promise(function(resolve, reject){
                    setTimeout(function() {
                        console.log("setTimeout  > i=" + x + " x=" + x);

                        // 다운로드 진행중인 파일 숫자 변경
                        $("#progressCnt").html(x + 1);

                        // 페이징 35000개씩 지정해 분할 다운로드 진행
                        params.limit = 35000 * (x + 1);
                        params.offset = (35000 * (x + 1)) - 34999;

                        // 가상로그인 대응한 session id 설정
                        if (document.getElementsByName('sessionId')[0]) {
                            params['sid'] = document.getElementsByName('sessionId')[0].value;
                        }

                        // ajax 통신 설정
                        $http({
                            method: 'POST', //방식
                            url: '/sale/prod/dayProdStore/dayProdStore/getDayProdStoreList.sb', /* 통신할 URL */
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
                            setTimeout(function() {
                                if ($scope.excelFlex.rows.length <= 0) {
                                    $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                                    $scope.excelUploadingPopup(false);
                                    return false;
                                }

                                // <-- 그리드 visible -->
                                // 선택한 테이블에 따른 리스트 항목 visible
                                var grid = wijmo.Control.getControl("#wjGridExcelList");
                                var columns = grid.columns;

                                // 컬럼 총갯수
                                var columnsCnt = columns.length;

                                for (var i = 0; i < columnsCnt; i++) {
                                    columns[i].visible = true;
                                }

                                // 합계가 0이면 해당 컬럼 숨기기
                                for (var j = 0; j < columnsCnt; j++) {
                                    // 상품표시옵션
                                    if (params.prodOption === "1") {  // 단품+세트
                                        if (columns[j].binding == "saleQty2" || columns[j].binding == "saleQty3" || columns[j].binding == "realSaleAmt2" || columns[j].binding == "realSaleAmt3") {
                                            columns[j].visible = false;
                                        }
                                    } else if (params.prodOption === "2") {  // 단품+구성
                                        if (columns[j].binding == "saleQty1" || columns[j].binding == "saleQty3" || columns[j].binding == "realSaleAmt1" || columns[j].binding == "realSaleAmt3") {
                                            columns[j].visible = false;
                                        }
                                    } else if (params.prodOption === "3") {  // 단품+세트+구성
                                        if (columns[j].binding == "saleQty1" || columns[j].binding == "saleQty2" || columns[j].binding == "realSaleAmt1" || columns[j].binding == "realSaleAmt2") {
                                            columns[j].visible = false;
                                        }
                                    }

                                    // 일자표시옵션
                                    if (params.dayOption === "1") {  // 일자별
                                        if (columns[j].binding == "dayFrom" || columns[j].binding == "dayTo") {
                                            columns[j].visible = false;
                                        }
                                    } else if (params.dayOption === "2") {  // 기간합
                                        if (columns[j].binding == "saleDate" || columns[j].binding == "yoil") {
                                            columns[j].visible = false;
                                        }
                                    }
                                }
                                // <-- //그리드 visible -->

                                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                                    includeColumnHeaders: true,
                                    includeCellStyles: false,
                                    includeColumns: function (column) {
                                        return column.visible;
                                    }
                                }, "일별상품매출현황(매장별)_" + params.startDate + "_" + params.endDate + "_" + getCurDateTime() + '_' + (x + 1) + '.xlsx', function () {
                                    $timeout(function () {
                                        console.log("Export complete start. _" + (x + 1));
                                        getExcelFile(x + 1);
                                    }, 500);
                                }, function (reason) { // onError
                                    // User can catch the failure reason in this callback.
                                    console.log('The reason of save failure is ' + reason + "_" + (x + 1));
                                    $scope.excelUploadingPopup(false);
                                });
                            }, 3000);
                        });
                        resolve(x);
                    }, 3000*x);
                });
            };

            async function getExcelFile(x) {
                if(totFileCnt > x){
                    await delay(x);
                }else{
                    $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
                }
            };
            
            // 엑셀 분할 다운로드 시작
            getExcelFile(0);

        });
    };

    // 작업내역 로딩 팝업
    $scope.excelUploadingPopup = function (showFg) {
      if (showFg) {
          // 팝업내용 동적 생성
          var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['cmm.progress'] + '</p>';
          innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 다운로드 진행 중...</div>';
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