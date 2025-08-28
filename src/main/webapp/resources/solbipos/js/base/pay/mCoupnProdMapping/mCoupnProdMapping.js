/****************************************************************
 *
 * 파일명 : mCoupnProdMapping.js
 * 설  명 : 모바일쿠폰상품매핑 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.08.19     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  모바일쿠폰상품매핑 그리드 생성
 */
app.controller('mCoupnProdMappingCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mCoupnProdMappingCtrl', $scope, $http, false));

    // 조회일자
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("useYnCombo", useYnData); // 사용여부

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 전체기간 체크박스
        $scope.isChecked = true;
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;
    };

    // <-- 검색 호출 -->
    $scope.$on("mCoupnProdMappingCtrl", function(event, data) {
        // 조회구분 (A:가로, B:세로)
        var searchGubun = data;

        $scope.searchMCoupnProdMappingCnt(searchGubun);
        event.preventDefault();
    });

    $scope.searchMCoupnProdMappingCnt = function(searchGubun){
        var params = {};
        // 등록일자 '전체기간' 선택에 따른 params
        if(!$scope.isChecked){
            params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
            params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        }
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.prodClassCd = $scope.prodClassCd;
        params.barCd = $scope.barCd;
        params.useYn = $scope.useYn;

        // 조회구분 (A:가로, B:세로)
        params.searchGubun = searchGubun;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withOutPopUp('/base/pay/mCoupnProdMapping/mCoupnProdMapping/getMCoupnProdMappingCnt.sb', params, function (response) {
            var mCoupnProdMappingCnt = response.data.data.result;
            $scope.mCoupnProdMappingCnt = mCoupnProdMappingCnt;

            // 모바일쿠폰사-상품코드 최대수
            params.mcoupnProdCnt = $scope.mCoupnProdMappingCnt.mcoupnProdCnt;

            // 조회
            $scope.searchMCoupnProdMapping(params);
        });
    };

    $scope.searchMCoupnProdMapping = function(params){
        $scope._inquiryMain("/base/pay/mCoupnProdMapping/mCoupnProdMapping/getMCoupnProdMappingList.sb", params, function(){
            // <-- 그리드 생성 -->
            var grid = wijmo.Control.getControl("#wjGridList");

            while(grid.columns.length > 2){
                grid.columns.removeAt(grid.columns.length-1);
            }

            if(params.searchGubun == "A") {
                grid.columns.push(new wijmo.grid.Column({header: messages["mCoupnProdMapping.mcoupnNm"], binding: 'mcoupnNm', width: 110, align: "center" , isReadOnly: "true"}));
                for(var j=1; j<params.mcoupnProdCnt+1; j++){
                    grid.columns.push(new wijmo.grid.Column({header: messages["mCoupnProdMapping.mappingCd"]+j, binding: 'mcoupnProdCd'+j, width: 100, align: "center" , isReadOnly: "true"}));
                }
            } else {
                grid.columns.push(new wijmo.grid.Column({header: messages["mCoupnProdMapping.saleUprc"], binding: 'saleUprc', width: 80, align: "right" , isReadOnly: "true"}));
                grid.columns.push(new wijmo.grid.Column({header: messages["mCoupnProdMapping.mcoupnNm"], binding: 'mcoupnNm', width: 110, align: "center" , isReadOnly: "true"}));
                grid.columns.push(new wijmo.grid.Column({header: messages["mCoupnProdMapping.mappingCd"], binding: 'mcoupnProdCd', width: 100, align: "center" , isReadOnly: "true"}));
                grid.columns.push(new wijmo.grid.Column({header: messages["mCoupnProdMapping.remark"], binding: 'remark', width: 120, align: "center" , isReadOnly: "true"}));
            }
            // <-- //그리드 생성 -->
        });
    };
    // <-- //검색 호출 -->

    // 전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;
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
                        $scope.prodClassCdNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
    };

    // 현재화면 엑셀다운로드
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
                "모바일쿠폰상품매핑_" + getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
        }, 10);
    };

    // <-- 양식다운로드 -->
    $scope.sampleDownload = function(){
        var params = {};
        $scope._broadcast('mCoupnProdMappingExcelUploadSampleCtrl', params);
    };
    // <-- //양식다운로드 -->

    // <-- 엑셀업로드 -->
    $scope.excelUpload = function(){
        // 엑셀업로드 팝업
        $("#mCoupnProdMappingExcelUpFile").val('');
        $("#mCoupnProdMappingExcelUpFile").trigger('click');
    };
    // <-- //엑셀업로드 -->

    // 이력조회
    $scope.mCoupnProdMappingHist = function(){
        // 팝업 호출
        $scope.wjMCoupnProdMappingHistLayer.show(true);
        event.preventDefault();
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 모바일쿠폰상품매핑 이력조회 팝업 핸들러 추가
        $scope.wjMCoupnProdMappingHistLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('mCoupnProdMappingHistCtrl', 'B');
            }, 50)
        });
    });

}]);


/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('mCoupnProdMappingExcelUploadSampleCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mCoupnProdMappingExcelUploadSampleCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("mCoupnProdMappingExcelUploadSampleCtrl", function (event, data) {
        $scope.searchExcelList(data);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/base/pay/mCoupnProdMapping/mCoupnProdMapping/getMCoupnProdMappingExcelUploadSampleList.sb", params, function() {
            if ($scope.excelUploadSampleFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelUploadSampleFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : false,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, "모바일쿠폰상품매핑 양식" + '_' + getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };

}]);