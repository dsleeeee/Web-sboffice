/****************************************************************
 *
 * 파일명 : dlvrProdMultiNmMapping.js
 * 설  명 : 배달시스템 상품 명칭 맵핑2 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.01.25     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용여부 콤보는 별도 조회하지 않고 고정적으로 사용
var useYnAllComboData = [
    {"name": "전체", "value": ""},
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];

// 매핑값 구분
var mappFgComboData = [
    {"name": "매핑값 추가", "value": "I"},
    {"name": "매핑값 적용", "value": "D"}
];

// 등록일자 검색기준 콤보박스
var regDtTypeComboData = [
    {"name":"상품","value":"prod"},
    {"name":"상품명칭","value":"dlvrProdNm"}
];

app.controller('dlvrProdMultiNmMappingCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrProdMultiNmMappingCtrl', $scope, $http, $timeout, true));

    // 등록일자 검색기준 콤보박스
    $scope._setComboData("regDtType", regDtTypeComboData);

    // 등록일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchTimeStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchTimeEndDate", gvEndDate);

    // 전체기간 체크박스
    $scope.isChecked = true;

    // 전체기간 체크박스 선택에 따른 날짜선택 초기화
    $scope.srchStartDate.isReadOnly = $scope.isChecked;
    $scope.srchEndDate.isReadOnly = $scope.isChecked;

    // 콤보박스 데이터 Set
    $scope._setComboData('listScaleBox', gvListScaleBoxData);

    // 사용여부를 쓰는 콤보박스의 데이터 (조회용)
    $scope._setComboData('useYnAllComboData', useYnAllComboData);

    // 매핑값 구분 콤보박스의 데이터
    $scope._setComboData('mappFgBox', mappFgComboData);

    $scope.initGrid = function (s, e) {

        // 전체기간 체크박스 선택에 따른 등록일자 검색기준 초기화
        $scope.regDtTypeCombo.isReadOnly = $scope.isChecked;
    };

    $scope.$on("dlvrProdMultiNmMappingCtrl", function (event, data) {
        $scope.searchProdList();
        event.preventDefault();
    });

    // 상품 목록 조회
    $scope.searchProdList = function(){

        // 파라미터
        var params = {};
        params.listScale = $scope.listScale; //-페이지 스케일 갯수
        params.dlvrCol = dlvrCol;
        params.chkDt = $scope.isChecked;

        // 등록일자 '전체기간' 선택에 따른 params
        if(!$scope.isChecked){
            params.regDtType = $scope.regDtTypeCombo.selectedValue;
            params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
            params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
        $scope._inquiryMain("/base/prod/dlvrProdMulti/dlvrProdMulti/list.sb", params, function(){});
    };

    // 저장
    $scope.save = function(){
        var arr = dlvrCol.split(",");

        // 파라미터 설정

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            for(var j = 0; j < arr.length; j++) {

                var prodNmVal = "";
                var obj = {};
                obj.status = "U";
                obj.seq = $scope.flex.collectionView.itemsEdited[i].seq;
                obj.prodCd = $scope.flex.collectionView.itemsEdited[i].prodCd;
                obj.dlvrNameCd = arr[j];
                if(eval('$scope.flex.collectionView.itemsEdited[i].dlvrProdNm' + arr[j]) != null &&
                    eval('$scope.flex.collectionView.itemsEdited[i].dlvrProdNm' + arr[j]) != ""){
                    prodNmVal = eval('$scope.flex.collectionView.itemsEdited[i].dlvrProdNm' + arr[j]).toString().trim();
                    obj.dlvrProdNm = prodNmVal;
                }else{
                    prodNmVal = eval('$scope.flex.collectionView.itemsEdited[i].dlvrProdNm' + arr[j]);
                    obj.dlvrProdNm = prodNmVal;
                }
                params.push(obj);
            }

        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            // 데이터 임시 저장
            $scope.tempInsert(params);
        }, 10);
    };

    // 데이터 임시 저장
    $scope.tempInsert = function (params) {

        if(params.length == 0){
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            $scope._popMsg("변경사항이 없습니다.");
            return false;
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/base/prod/dlvrProdMulti/dlvrProdMulti/getDlvrProdMultiTempInsert.sb", params, function (response) {
            // 중복 체크
            $scope.dupChk();
        });
    };

    // 데이터 중복 체크
    $scope.dupChk = function(){

        var params = {};
        params.mappFg = "S";

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/base/prod/dlvrProdMulti/dlvrProdMulti/getDlvrProdMultiNmMappingChk.sb", params, function (response) {
            var result = response.data.data;

            if(result === null || result === "") {
                // 배민 입력 확인
                $scope.chkNull();
            } else {
                $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                $scope._popMsg(result + " 명칭이 중복됩니다.");
                return false;
            }
        });
    }

    // 배민 입력 확인
    $scope.chkNull = function(){

        var chkParams = {};
        chkParams.mappFg = "S";

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withOutPopUp("/base/prod/dlvrProdMulti/dlvrProdMulti/getProdCdNullChk.sb", chkParams, function(response) {
            var cnt = response.data.data;

            if (cnt != 0) {
                $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                // 배달의민족앱[3] 입력값이 없는 상품이 있습니다. <br>필수입력값이므로, 임의데이터라도 입력하세요.
                $scope._popMsg(messages["dlvrProdMulti.baemin.chk.msg"]);
                return false;
            }else{
                //데이터 저장
                $scope.saveSave();
            }
        });
    }


    // 저장
    $scope.saveSave = function(){

        var chkParams = {};
        chkParams.mappFg = "S";

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/dlvrProdMulti/dlvrProdMulti/save.sb", chkParams, function () {
            // 재조회
            $scope.searchProdList();
        });
        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
    };

    // 전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.regDtTypeCombo.isReadOnly = $scope.isChecked;
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

    // 상품명칭복사 팝업
    $scope.copyDlvrProdNm = function(){
        $scope.copyDlvrProdMultiNmLayer.show(true);
    };

    // 상품명칭매핑 excelUpload
    $scope.excelUpload = function () {
        var vScope = agrid.getScope('excelUploadDlvrProdMultiNmCtrl');
        var msg = messages["dlvrProdMulti.confmMsg"];  // 정상업로드 된 데이터는 자동저장됩니다. 업로드 하시겠습니까?

        s_alert.popConf(msg, function () {

            /* 부모컨트롤러 값을 넣으면 업로드가 완료된 후 uploadCallBack 이라는 함수를 호출해준다. */
            vScope.parentCtrl = 'dlvrProdMultiNmMappingCtrl';

            $("#excelUpFile").val('');
            $("#excelUpFile").trigger('click');
            $("#mappFg").val($scope.mappFgCombo.selectedValue);

        });
    };

    /** 업로드 완료 후 callback 함수. 업로드 이후 로직 작성. */
    $scope.uploadCallBack = function () {
        $scope._pageView('dlvrProdMultiNmMappingCtrl', 1);
    };

    // 상품명칭 양식 다운로드
    $scope.excelDownload = function () {
        var vScope = agrid.getScope('excelUploadDlvrProdMultiNmCtrl');
        vScope.excelFormDownload();
    };

    // 상품명칭 매장적용
    $scope.storeApply = function () {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length <= 0) {
            s_alert.pop(messages["cmm.not.select"]);
            return;
        }

        $scope.setSelectedProd(params);
        $scope.wjDlvrProdMultiNmStoreRegistLayer.show(true);
        event.preventDefault();
    };


    // 선택
    $scope.selectedProd;
    $scope.setSelectedProd = function(store) {
        $scope.selectedProd = store;
    };
    $scope.getSelectedProd = function() {
        return $scope.selectedProd;
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 상품명칭 매장적용 팝업 핸들러 추가
        $scope.wjDlvrProdMultiNmStoreRegistLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('dlvrProdMultiNmStoreRegistCtrl', $scope.getSelectedProd());
            }, 50)
        });
    });

    // 전체 엑셀다운로드
    $scope.excelDownloadTotal = function () {
        var params = {};
        // 데이터양에 따라 2-3초에서 수분이 걸릴 수도 있습니다.
        $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function() {
            $scope._broadcast('dlvrProdMultiNmMappingExcelCtrl', params);
        });
    };
}]);


/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('dlvrProdMultiNmMappingExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrProdMultiNmMappingExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 전체기간 체크박스 선택에 따른 등록일자 검색기준 초기화
        $scope.regDtTypeCombo.isReadOnly = $scope.isChecked;
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("dlvrProdMultiNmMappingExcelCtrl", function (event, data) {
        $scope.searchExcelList(data);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {

        params.dlvrCol = dlvrCol;
        params.chkDt = $scope.isChecked;

        // 등록일자 '전체기간' 선택에 따른 params
        if(!$scope.isChecked){
            params.regDtType = $scope.regDtTypeCombo.selectedValue;
            params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
            params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/base/prod/dlvrProdMulti/dlvrProdMulti/getDlvrProdMultiExcelList.sb", params, function() {
            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : false,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, "상품명칭매핑" + '_' + getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };

}]);