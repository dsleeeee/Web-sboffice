/****************************************************************
 *
 * 파일명 : dlvrProdNmMapping.js
 * 설  명 : 배달시스템 상품 명칭 맵핑 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.10.14     이다솜      1.0
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

// 등록일자 검색기준 콤보박스
var regDtTypeComboData = [
    {"name":"상품","value":"prod"},
    {"name":"상품명칭","value":"dlvrProdNm"}
];

app.controller('dlvrProdNmMappingCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrProdNmMappingCtrl', $scope, $http, true));

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

    $scope.initGrid = function (s, e) {

        // 전체기간 체크박스 선택에 따른 등록일자 검색기준 초기화
        $scope.regDtTypeCombo.isReadOnly = $scope.isChecked;
    };

    $scope.$on("dlvrProdNmMappingCtrl", function (event, data) {
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
        $scope._inquiryMain("/base/prod/dlvrProd/dlvrProd/list.sb", params, function(){});

    };

    // 저장
    $scope.save = function(){

        //
        var arr = dlvrCol.split(",");

        // 배달의민족앱[3] 상품명칭 입력값 전체 체크
        var str = "";
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

            str = "";
            for(var j = 0; j < arr.length; j++) {
                if(eval('$scope.flex.collectionView.items[i].dlvrProdNm' + arr[j]) !== null &&
                   eval('$scope.flex.collectionView.items[i].dlvrProdNm' + arr[j]) !== undefined &&
                   eval('$scope.flex.collectionView.items[i].dlvrProdNm' + arr[j]) !== ""){
                    str +=  "[채널사: (dlvrProdNm"+ arr[j] + ")/" + eval('$scope.flex.collectionView.items[i].dlvrProdNm' + arr[j]) + "]";
                }
            }

            if(str != null && str != undefined && str != ""){
                if(str.indexOf("[채널사: (dlvrProdNm3)/") === -1 ){
                    // 배달의민족앱[3] 입력값이 없는 상품이 있습니다. <br>필수입력값이므로, 임의데이터라도 입력하세요.
                    $scope._popMsg(messages["dlvrProd.baemin.chk.msg"]);
                    return false;
                }
            }
        }

        // 파라미터 설정
        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            for(var j = 0; j < arr.length; j++) {

                var obj = {};
                obj.status = "U";
                obj.prodCd = $scope.flex.collectionView.itemsEdited[i].prodCd;
                obj.dlvrNameCd = arr[j];
                obj.dlvrProdNm = eval('$scope.flex.collectionView.itemsEdited[i].dlvrProdNm' + arr[j]);

                params.push(obj);
            }
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/dlvrProd/dlvrProd/save.sb", params, function () {
            // 재조회
            $scope.searchProdList();
        });
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
    }

    // 상품명칭복사 팝업
    $scope.copyDlvrProdNm = function(){
        $scope.copyDlvrProdNmLayer.show(true);
    };

    // 상품명칭복사 excelUpload
    $scope.excelUpload = function () {
        var vScope = agrid.getScope('excelUploadDlvrProdNmCtrl');
        var msg = messages["dlvrProd.confmMsg"];  // 정상업로드 된 데이터는 자동저장됩니다. 업로드 하시겠습니까?

        s_alert.popConf(msg, function () {

            /* 부모컨트롤러 값을 넣으면 업로드가 완료된 후 uploadCallBack 이라는 함수를 호출해준다. */
            vScope.parentCtrl = 'dlvrProdNmMappingCtrl';

            $("#excelUpFile").val('');
            $("#excelUpFile").trigger('click');

        });
    };

    /** 업로드 완료 후 callback 함수. 업로드 이후 로직 작성. */
    $scope.uploadCallBack = function () {
        $scope._pageView('dlvrProdNmMappingCtrl', 1);
    };

    // 상품명칭복사 양식 다운로드
    $scope.excelDownload = function () {
        var vScope = agrid.getScope('excelUploadDlvrProdNmCtrl');
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
        $scope.wjDlvrProdNmStoreRegistLayer.show(true);
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
        $scope.wjDlvrProdNmStoreRegistLayer.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('dlvrProdNmStoreRegistCtrl', $scope.getSelectedProd());
            }, 50)
        });
    });

}]);