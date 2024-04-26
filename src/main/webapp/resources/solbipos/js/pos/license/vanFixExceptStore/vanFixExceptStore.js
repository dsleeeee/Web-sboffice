/****************************************************************
 *
 * 파일명 : vanFixExceptStore.js
 * 설  명 : VAN사 변경허용 매장관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.04.09     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 하위대리점 포함여부
var includeFgTypeComboData = [
    {"name":"포함","value":"Y"},
    {"name":"미포함","value":"N"}
];

/**
 * van사 변경허용매장 그리드 생성
 */
app.controller('vanFixExceptCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('vanFixExceptCtrl', $scope, $http, true));

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("includeFgCombo", includeFgTypeComboData); // 하위대리점 포함여부

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.vanCdDataMap = new wijmo.grid.DataMap(vanComboList, 'value', 'name');
    };

    // 대표명칭 그리드 조회
    $scope.$on('vanFixExceptCtrl', function(event, data) {

        var params = {};

        params.vanCd = $("#ssl_srchManageVanCd").val();
        params.includeFg = $scope.srchIncludeFgCombo.selectedValue;
        params.hqOfficeCd = $scope.hqOfficeCd;
        params.hqOfficeNm = $scope.hqOfficeNm;
        params.storeCd = $scope.storeCd;
        params.storeNm = $scope.storeNm;

        if(orgnFg === "MASTER"){
            params.agencyCd = $("#ssl_srchAgencyCd").val();
        }else{
            params.agencyCd = orgnCd;
        }
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/pos/license/vanFixExceptStore/vanFixExceptStore/getVanFixExceptStore.sb", params, function() {
            $scope._broadcast('vanStoreCtrl');
        });
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 삭제
    $scope.delete = function() {
        // 파라미터 설정
        var params = [];
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                params.push($scope.flex.collectionView.items[i]);
            }
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/pos/license/vanFixExceptStore/vanFixExceptStore/deleteFixExceptStore.sb", params, function() {
            $scope._broadcast('vanFixExceptCtrl');
        });
    };

    // 엑셀다운로드
    $scope.excelDownload = function(){

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUploadMPS.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : false,
                includeColumns      : function (column) {
                    // return column.visible;
                    return column.binding != 'gChk';
                }
            }, messages["vanFixExceptStore.fixExceptStore"] + "_" + messages["vanFixExceptStore.fixExceptStore"] + "_" +getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };
    // <-- //엑셀다운로드 -->

    /*********************************************************
     * 관리밴사 조회
     * *******************************************************/
    $scope.searchManageVan = function(){
        var popup = $scope.vanLayer;

        // 팝업 닫을때
        popup.show(true, function (s) {
            var vanScope = agrid.getScope('searchVanCtrl');
            vanScope.$apply(function () {
                vanScope._gridDataInit();
                if (!$.isEmptyObject(vanScope.getVan())) {
                    $("#ssl_srchManageVanCd").val(vanScope.getVan().vanCd);
                    $("#manageVanNm").val(vanScope.getVan().vanNm);
                }
            });
        });
    };

    // 관리밴사 선택취소
    $scope.delManageVanCd = function(){
        $("#ssl_srchManageVanCdExcept").val("");
        $("#manageVanNm").val(messages["cmm.all"]);
    }

    /*********************************************************
     * 대리점(관리업체) 조회
     * *******************************************************/
    $scope.searchAgency = function(){

        var popup = $scope.agencyLayer;

        // 팝업 닫을때
        popup.show(true, function (s) {
            var agencyScope = agrid.getScope('searchAgencyCtrl');
            agencyScope.$apply(function () {
                agencyScope._gridDataInit();
                if (!$.isEmptyObject(agencyScope.getAgency())) {
                    $("#ssl_srchAgencyCd").val(agencyScope.getAgency().agencyCd);
                    $("#agencyNm").val(agencyScope.getAgency().agencyNm);
                }
            });
        });
    };

    // 대리점(관리업체) 선택취소
    $scope.delAgencyCd = function(){
        $("#ssl_srchAgencyCd").val("");
        $("#agencyNm").val(messages["cmm.all"]);
    }


}]);

/**
 * 매장 그리드 생성
 */
app.controller('vanStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('vanStoreCtrl', $scope, $http, false));


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.vanCdDataMap = new wijmo.grid.DataMap(vanComboList, 'value', 'name');

        // 비고 작성시 체크
        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "remark") {
                    item.gChk = true;
                }
            }
            s.collectionView.commitEdit();
        });
    };

    // 대표명칭 그리드 조회
    $scope.$on('vanStoreCtrl', function(event, data) {

        var params = {};
        params.vanCd = $("#ssl_srchManageVanCd").val();
        params.includeFg = $scope.srchIncludeFgCombo.selectedValue;
        params.hqOfficeCd = $scope.hqOfficeCd;
        params.hqOfficeNm = $scope.hqOfficeNm;
        params.storeCd = $scope.storeCd;
        params.storeNm = $scope.storeNm;

        if(orgnFg === "MASTER"){
            params.agencyCd = $("#ssl_srchAgencyCd").val();
        }else{
            params.agencyCd = orgnCd;
        }

        // 조회URL, 파라미터, 콜백함수 형태로 조회함수 호출
        $scope._inquirySub("/pos/license/vanFixExceptStore/vanFixExceptStore/getStoreList.sb", params, function() {
        });
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 저장
    $scope.save = function() {
        // 파라미터 설정
        var params = [];
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                if($scope.maxChk($scope.flex.collectionView.items[i].remark)){
                    params.push($scope.flex.collectionView.items[i]);
                } else {
                    $scope._popMsg(messages["storeCloseExcept.remarkChk"]);
                    return false;
                }
            }
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/pos/license/vanFixExceptStore/vanFixExceptStore/saveFixExceptStore.sb", params, function() {
            $scope._broadcast('vanFixExceptCtrl');
        });
    };

    // MAX값 따지는 함수 50byte
    $scope.maxChk = function (val){
        var str = val;
        var strLength = 0;
        var strTitle = "";
        var strPiece = "";
        if(str === null || str === "" || str === undefined){
            return true;
        }
        for (i = 0; i < str.length; i++){
            var code = str.charCodeAt(i);
            var ch = str.substr(i,1).toUpperCase();
            //체크 하는 문자를 저장
            strPiece = str.substr(i,1)
            code = parseInt(code);
            if ((ch < "0" || ch > "9") && (ch < "A" || ch > "Z") && ((code > 255) || (code < 0))){
                strLength = strLength + 3; //UTF-8 3byte 로 계산
            }else{
                strLength = strLength + 1;
            }
            if(strLength > 500){ //제한 길이 확인
                return false;
            }else{
                strTitle = strTitle+strPiece; //제한길이 보다 작으면 자른 문자를 붙여준다.
            }
        }
        return true;
    };

    // 엑셀다운로드
    $scope.excelDownload = function(){

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUploadMPS.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : false,
                includeColumns      : function (column) {
                    // return column.visible;
                    return column.binding != 'gChk';
                }
            }, messages["vanFixExceptStore.fixExceptStore"] + "_" + messages["vanFixExceptStore.store"] +"_" + getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    };


}]);
