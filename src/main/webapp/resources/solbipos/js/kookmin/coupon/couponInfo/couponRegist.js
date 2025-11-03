/****************************************************************
 *
 * 파일명 : couponRegist.js
 * 설  명 : 국민대 > 쿠폰관리 > 쿠폰관리 쿠폰적용관리 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.23     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 쿠폰적용관리 */
app.controller('couponRegistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('couponRegistCtrl', $scope, $http, true));

    // 등록일자 셋팅
    $scope.dtlStartDate = wcombo.genDateVal("#dtlStartDate", gvStartDate);
    $scope.dtlEndDate = wcombo.genDateVal("#dtlEndDate", gvEndDate);

    $scope.coupn = {};

    // 조회 버튼 클릭
    $scope.$on("couponRegistCtrl", function(event, data) {

        $timeout(function() {
            // 신규 등록 시
            if (data.edit !== null && data.edit !== '' && data.edit !== '1') {
                $("#coupnIssue").css('display', "none");
                $("#selectSaleCoupon").css('display', "none");
                $("#couponInfoSave").css('display', "");
                $scope.coupn = {};
                $scope.dtlStartDate.value = new Date();
                $scope.dtlEndDate.value = new Date();
                $("#couponRegistStoreCd").val('');
                $("#couponRegistStoreNm").val('선택');
                $("#selectProdCd").val('');
                $("#selectProd").val('선택');
                $("#selectPartCd").val('');
                $("#selectPart").val('선택');
            } else {
                // 수정 시
                $("#coupnIssue").css('display', "block");
                $("#selectSaleCoupon").css('display', "block");
                $("#couponInfoSave").css('display', "");

                var params = {};
                params.coupnCd      = data.coupnCd;
                params.payClassCd   = data.payClassCd;

                // 쿠폰 상세정보 조회
                $scope._postJSONQuery.withOutPopUp("/kookmin/coupon/couponInfo/couponInfo/getCouponInfoDtlList.sb", params, function (response) {
                    var list = response.data.data.list;

                    if(list.length > 0) {
                        $scope.coupn.coupnCd = list[0].coupnCd;
                        $scope.coupn.coupnStatus = list[0].coupnStatus;
                        $scope.coupn.coupnNm = list[0].coupnNm;
                        $scope.coupn.coupnCount = list[0].coupnCount;
                        $scope.coupn.title = list[0].coupnPrintTitle;
                        $scope.coupn.issuer = list[0].coupnPrintPart
                        $scope.coupn.useStore = list[0].coupnPrintStore;
                        $scope.coupn.printMsg = list[0].coupnPrintRemark;
                        $("#couponRegistStoreCd").val(list[0].storeCd);
                        $("#selectPartCd").val(list[0].vendrCd);
                        $("#selectProdCd").val(list[0].prodCd);
                        $("#couponRegistStoreNm").val(list[0].storeNm);
                        $("#selectPart").val(list[0].vendrNm);
                        $("#selectProd").val(list[0].prodNm);
                        $scope.coupn.saleCnt    = list[0].saleCnt;
                        $scope.coupn.saleAmt    = list[0].saleAmt;
                        $scope.coupn.notSaleCnt = list[0].notSaleCnt;
                        $scope.coupn.notSaleAmt = list[0].notSaleAmt;
                    }

                    if($scope.coupn.coupnStatus === '발행'){
                        $("#coupnIssue").css('display', "none");
                        $("#couponInfoSave").css('display', "none");
                    }

                });

            }
        }, 0);

        event.preventDefault();
    });

    // 상품선택 팝업
    $scope.selectProdShow = function(){
        if($("#couponRegistStoreCd").val() === "" || $("#couponRegistStoreCd").val() === null ){
            $scope._popMsg(messages["couponInfo.require.selectStore"]); // 매장을 선택해주세요
            return false;
        }

        var params = {};
        params.storeCd = $("#couponRegistStoreCd").val();

        $scope._broadcast('couponSelectProdCtrl', params);
        $scope.couponSelectProdLayer.show(true, function(){
            var scope = agrid.getScope('couponSelectProdCtrl');
            scope.srchSelectProdCd = '';
            scope.srchSelectProdNm = '';
        });
    };

    // 상품 선택취소
    $scope.selectProdCancel = function (){
        $("#selectProdCd").val('');
        $("#selectProd").val('선택');
    }

    // 부서 선택 팝업
    $scope.selectPartShow = function(){

        $scope._broadcast('couponSelectPartCtrl');
        // 팝업 닫힐 때
        $scope.couponSelectPartLayer.show(true, function(){
            var scope = agrid.getScope('couponSelectPartCtrl');
            scope.srchSelectPartCd = '';
            scope.srchSelectPartNm = '';
        });
    };

    // 부서 선택취소
    $scope.selectPartCancel = function (){
        $("#selectPartCd").val('');
        $("#selectPart").val('선택');
    }

    // 쿠폰발행
    $scope.couponIssue = function(){
        var msg = messages["captionMsg.excelUpload.confmMsg"];  // 정상업로드 된 데이터는 자동저장됩니다. 업로드 하시겠습니까?

        $scope._popConfirm(msg, function() {

            $("#excelUpFile").val('');
            $("#excelUpFile").trigger('click');

        });
    };

    // 회수쿠폰조회
    $scope.selectSaleCoupon = function (){
        var params = {};
        params.coupnCd = $scope.coupn.coupnCd;
        params.coupnStatusFg = '2';
        $scope._broadcast('couponSaleDtlCtrl',params);
    }
    
    // 입력값 체크
    $scope.couponInfoSave = function(){

        $scope.$applyAsync(function() {
            // 널값 체크
            if(!$scope.coupn || $scope.coupn.coupnNm === undefined || $scope.coupn.coupnNm === null || $scope.coupn.coupnNm === ''){
                $scope._popMsg(messages["couponInfo.coupnNm"] + messages["cmm.inputEnv"]); // 쿠폰명을 입력해주세요.
                return false;
            }

            if($("#couponRegistStoreCd").val() === undefined || $("#couponRegistStoreCd").val() === null || $("#couponRegistStoreCd").val() === ''){
                $scope._popMsg(messages["cmm.require.selectStore"]); // 매장을 선택해 주세요.
                return false;
            }
            if($("#selectPartCd").val() === undefined || $("#selectPartCd").val() === null || $("#selectPartCd").val() === ''){
                $scope._popMsg(messages["couponInfo.require.selectPart"]); // 부서를 선택해주세요.
                return false;
            }

            if($("#selectProdCd").val() === undefined || $("#selectProdCd").val() === null || $("#selectProdCd").val() === ''){
                $scope._popMsg(messages["couponInfo.require.selectProd"]); // 상품을 선택해주세요.
                return false;
            }

            if(Number(wijmo.Globalize.format($scope.dtlStartDate.value, 'yyyyMMdd')) > Number(wijmo.Globalize.format($scope.dtlEndDate.value, 'yyyyMMdd'))){
                $scope._popMsg(messages["couponInfo.date.msg"]); // 사용기간을 확인해주세요
                return;
            }

            if($scope.coupn.coupnCount !== undefined && $scope.coupn.coupnCount !== null && $scope.coupn.coupnCount !== ''){
                // 숫자만 입력
                var numChkexp = /[^0-9]/g;
                if (numChkexp.test($scope.coupn.coupnCount)) {
                    $scope._popMsg(messages["couponInfo.issueQty"] + messages["cmm.require.number"]); // 발행시간은 숫자만 입력해주세요.
                    return;
                }
            }else{
                $scope._popMsg(messages["couponInfo.issueQty"] + messages["cmm.inputEnv"]); // 발행시간을 입력해주세요
                return false;
            }
            if($scope.coupn.title === undefined || $scope.coupn.title === null || $scope.coupn.title === ''){
                $scope._popMsg(messages["couponInfo.title"] + messages["cmm.inputEnv"]); // 제목을 입력해주세요.
                return false;
            }
            if($scope.coupn.issuer === undefined || $scope.coupn.issuer === null || $scope.coupn.issuer === ''){
                $scope._popMsg(messages["couponInfo.issuer"] + messages["cmm.inputEnv"]); // 발행처 입력해주세요.
                return false;
            }
            if($scope.coupn.useStore === undefined || $scope.coupn.useStore === null || $scope.coupn.useStore === ''){
                $scope._popMsg(messages["couponInfo.useStore"] + messages["cmm.inputEnv"]); // 사용매장을 입력해주세요.
                return false;
            }
            if($scope.coupn.printMsg === undefined || $scope.coupn.printMsg === null || $scope.coupn.printMsg === ''){
                $scope._popMsg(messages["couponInfo.printMsg"] + messages["cmm.inputEnv"]); // 인쇄문구를 입력해주세요.
                return false;
            }


            // 길이 체크
            if(nvl($scope.coupn.coupnNm, '').getByteLengthForOracle() > 50){
                // 쿠폰명을 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.  최대 : 현재 : (영문1byte, 한글3byte)
                $scope._popMsg(messages["couponInfo.coupnNm"] + messages["cmm.overLength"] + " 50 " +
                    ", 현재 : " + nvl($scope.coupn.coupnNm, '').getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
                return false;
            }

            if($scope.coupn.coupnCount > 100000){
                $scope._popMsg(messages["couponInfo.issueQty"] + "최대 10만까지 입력 가능합니다"); // 발행시간은 숫최대 10만까지 입력 가능합니다.
                return;
            }

            if(nvl($scope.coupn.title, '').getByteLengthForOracle() > 200){
                // 쿠폰명을 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.  최대 : 현재 : (영문1byte, 한글3byte)
                $scope._popMsg(messages["couponInfo.title"] + messages["cmm.overLength"] + " 200 " +
                    ", 현재 : " + nvl($scope.coupn.title, '').getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
                return false;
            }
            if(nvl($scope.coupn.issuer, '').getByteLengthForOracle() > 200){
                // 쿠폰명을 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.  최대 : 현재 : (영문1byte, 한글3byte)
                $scope._popMsg(messages["couponInfo.issuer"] + messages["cmm.overLength"] + " 200 " +
                    ", 현재 : " + nvl($scope.coupn.issuer, '').getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
                return false;
            }
            if(nvl($scope.coupn.useStore, '').getByteLengthForOracle() > 200){
                // 쿠폰명을 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.  최대 : 현재 : (영문1byte, 한글3byte)
                $scope._popMsg(messages["couponInfo.useStore"] + messages["cmm.overLength"] + " 200 " +
                    ", 현재 : " + nvl($scope.coupn.useStore, '').getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
                return false;
            }
            if(nvl($scope.coupn.printMsg, '').getByteLengthForOracle() > 200){
                // 쿠폰명을 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다.  최대 : 현재 : (영문1byte, 한글3byte)
                $scope._popMsg(messages["couponInfo.printMsg"] + messages["cmm.overLength"] + " 200 " +
                    ", 현재 : " + nvl($scope.coupn.printMsg, '').getByteLengthForOracle() + messages["cmm.bateLengthInfo"]);
                return false;
            }
            // 저장
            $scope.regist();
        });
    };

    // 저장
    $scope.regist = function (){

        var params = $scope.coupn;

        params.storeCd = $("#couponRegistStoreCd").val();
        params.partCd = $("#selectPartCd").val();
        params.prodCd = $("#selectProdCd").val();
        params.startDate = wijmo.Globalize.format($scope.dtlStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.dtlEndDate.value, 'yyyyMMdd');
        params.coupnPrintTitle = $scope.coupn.title;
        params.coupnPrintPart = $scope.coupn.issuer;
        params.coupnPrintStore = $scope.coupn.useStore;
        params.coupnPrintRemark = $scope.coupn.printMsg;

        $scope._postJSONSave.withPopUp("/kookmin/coupon/couponInfo/couponInfo/getCouponRegist.sb", params, function(response){
            $scope.couponRegistLayer.hide();
            // 저장기능 수행후 재조회
            $scope._broadcast('couponInfoCtrl');
        });
    }

    // 팝업 닫기
    $scope.close = function(){
        $scope.couponRegistLayer.hide();

        // 재조회
        $scope._broadcast('couponInfoCtrl');
    };

}]);

/**
 * 엑셀업로드 그리드 생성
 */
app.controller('couponRegistExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('couponRegistExcelCtrl', $scope, $http, false));

    //
    $scope.initGrid = function (s, e) {

    };


    // 엑셀파일이 변경된 경우
    $scope.excelFileChanged = function () {
        if ($('#excelUpFile')[0].files[0]) {
            // 엑셀업로드 호출
            $scope.excelUpload();
        }
    };

    // 엑셀 업로드
    $scope.excelUpload = function () {

        $scope.stepCnt = 100; // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자

        // 선택한 파일이 있으면
        if ($('#excelUpFile')[0].files[0]) {
            var file = $('#excelUpFile')[0].files[0];
            var fileName = file.name;
            var fileExtension = fileName.substring(fileName.lastIndexOf('.'));

            // 확장자가 xlsx, xlsm 인 경우에만 업로드 실행
            if (fileExtension.toLowerCase() === '.xlsx' || fileExtension.toLowerCase() === '.xlsm') {
                $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
                /*$timeout(function () {
                    var flex = $scope.flex;
                    wijmo.grid.xlsx.FlexGridXlsxConverter.loadAsync(flex, $('#excelUpFile')[0].files[0], {includeColumnHeaders: true}
                        , function () {
                            $timeout(function () {
                                $scope.excelUploadToJsonConvert();
                            }, 10);
                        }
                    );
                }, 10);*/

                // excel file read
                var reader = new FileReader();
                var arr = [];
                reader.onload = function(){
                    var fileData = reader.result;
                    var wb = XLSX.read(fileData, {type : 'binary'});
                    wb.SheetNames.forEach(function(sheetName) {
                        arr = XLSX.utils.sheet_to_json(wb.Sheets[sheetName]);

                        // key명 변경
                        arr.forEach(function(item){
                            renameKey(item, '회원번호', 'coupnMembrNo');
                            renameKey(item, '수신자명', 'coupnIssueInfo1');
                            renameKey(item, '수신자연락처', 'coupnIssueInfo2');
                        });

                        // 엔터값 제거
                        arr.forEach(function(item){
                            if (item.coupnMembrNo !== null && item.coupnMembrNo !== undefined && item.coupnMembrNo !== "") {
                                item.coupnMembrNo = item.coupnMembrNo.toString().replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.coupnIssueInfo1 !== null && item.coupnIssueInfo1 !== undefined && item.coupnIssueInfo1 !== "") {
                                item.coupnIssueInfo1 = item.coupnIssueInfo1.toString().replace(/\r\n|\r|\n/g, ' ');
                            }

                            if (item.coupnIssueInfo2 !== null && item.coupnIssueInfo2 !== undefined && item.coupnIssueInfo2 !== "") {
                                item.coupnIssueInfo2 = item.coupnIssueInfo2.toString().replace(/\r\n|\r|\n/g, ' ');
                            }
                        });
                    })
                };
                reader.readAsBinaryString(file);

                $timeout(function () {
                    setTimeout(function() {
                        // 저장 전 입력값 체크
                        $scope.saveRow(arr);
                    }, 500);

                }, 10);

            } else {
                $("#excelUpFile").val('');
                $scope._popMsg(messages['fnkeyCmNmcd.not.excelFile']); // 엑셀 파일만 업로드 됩니다.(*.xlsx, *.xlsm)
                return false;
            }
        }
    };

    // 엑셀업로드 한 데이터를 JSON 형태로 변경한다.
    $scope.excelUploadToJsonConvert = function () {
        var jsonData  = [];
        var item      = {};
        var rowLength = $scope.flex.rows.length;

        if (rowLength === 0) {
            $scope._popMsg(messages['prodLang.not.excelUploadData']); // 엑셀업로드 된 데이터가 없습니다.
            return false;
        }

        // 업로드 된 데이터 JSON 형태로 생성
        for (var r = 0; r < rowLength; r++) {
            item = {};
            for (var c = 0; c < $scope.flex.columns.length; c++) {
                if ($scope.flex.columns[c].header !== null && $scope.flex.getCellData(r, c, false) !== null) {
                    var colBinding = $scope.colHeaderBind[$scope.flex.columns[c].header.replaceAll('\'', '')];
                    var cellValue  = $scope.flex.getCellData(r, c, false) + '';
                    item[colBinding] = cellValue;
                }
            }

            jsonData.push(item);
        }
        $timeout(function () {
            setTimeout(function() {
                // 저장 전 입력값 체크
                $scope.saveRow(jsonData);
            }, 500);

        }, 10);
    };

    // 저장 전 입력값 체크
    $scope.saveRow = function (jsonData) {
        $scope.totalRows = jsonData.length;

        for (var i = 0; i < $scope.totalRows; i++) {
            var item = jsonData[i];
            if(item.coupnMembrNo == "" || item.coupnMembrNo == null){
                $scope._popMsg(messages["couponInfo.membrNo"] + messages["workStudentKookmin.inputEnv"]);
                $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                return false;
            }
            if(item.coupnIssueInfo1 == "" || item.coupnIssueInfo1 == null){
                $scope._popMsg(messages["couponInfo.membrNm"] + messages["workStudentKookmin.inputEnv"]);
                $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                return false;
            }
            if(item.coupnIssueInfo2 == "" || item.coupnIssueInfo2 == null){
                $scope._popMsg(messages["couponInfo.membrTelNo"] + messages["workStudentKookmin.inputEnv"]);
                $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                return false;
            }
        }

        // 데이터 저장
        $timeout(function () {
            // 엑셀 업로드 전 TMP 테이블 데이터 제거
            $scope.delTmpData(jsonData);
        }, 10);
    };

    // 엑셀 업로드 전 TMP 테이블 데이터 제거
    $scope.delTmpData = function (jsonData){
        var params = {};
        var scope = agrid.getScope('couponRegistCtrl')
        params.coupnCd = scope.coupn.coupnCd;

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/kookmin/coupon/couponInfo/couponInfo/getDeleteTmpData.sb", params, function () {
            // 엑셀 업로드
            $scope.saveData(jsonData);
        });
    }

    // 데이터 저장
    $scope.saveData = function (jsonData) {
        $scope.totalRows = jsonData.length;
        var params = [];

        // 저장 시작이면 업로드 중 팝업 오픈
        if ($scope.progressCnt === 0) {
            $timeout(function () {
                $scope.excelUploadingPopup(true);
                $("#progressCnt").html($scope.progressCnt);
                $("#totalRows").html($scope.totalRows);
            }, 10);
        }

        // stepCnt 만큼 데이터 DB에 저장
        var loopCnt = (parseInt($scope.progressCnt) + parseInt($scope.stepCnt) > parseInt($scope.totalRows) ? parseInt($scope.totalRows) : parseInt($scope.progressCnt) + parseInt($scope.stepCnt));
        for (var i = $scope.progressCnt; i < loopCnt; i++) {
            var item = jsonData[i];

            item.progressCnt = $scope.progressCnt;
            var scope = agrid.getScope('couponRegistCtrl')
            item.coupnCd = scope.coupn.coupnCd;
            item.coupnCount = scope.coupn.coupnCount;

            params.push(item);
        }

        //가상로그인 session 설정
        var sParam = {};
        if(document.getElementsByName('sessionId')[0]){
            sParam['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/kookmin/coupon/couponInfo/couponInfo/getCouponIssueExcelUpload.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if (response.data.message !== null) {
                $scope._popMsg(response.data.message);
                return false;
            }
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    $scope.saveCouponIssue();
                }
            }
        }, function errorCallback(response) {
            $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.saveFail']);
            }
            return false;
        }).then(function () {
            // 'complete' code here
            // 처리 된 숫자가 총 업로드할 수보다 작은 경우 다시 save 함수 호출
            if (parseInt($scope.progressCnt) < parseInt($scope.totalRows)) {
                // 처리된 숫자 변경
                $scope.progressCnt = loopCnt;
                // 팝업의 progressCnt 값 변경
                $("#progressCnt").html($scope.progressCnt);
                $scope.saveData(jsonData);
            }
        });
    };

    // 값 저장
    $scope.saveCouponIssue = function (){
        var params = {};
        var scope = agrid.getScope('couponRegistCtrl')
        params.coupnCd = scope.coupn.coupnCd;
        params.coupnCount = scope.coupn.coupnCount;

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp("/kookmin/coupon/couponInfo/couponInfo/saveCouponIssue.sb", params, function () {
            $scope.couponRegistLayer.hide();
            // 저장기능 수행후 재조회
            $scope._broadcast('couponInfoCtrl');
        });
    }

    // 업로딩 팝업 열기
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            // 팝업내용 동적 생성
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['excelUpload.excelUploading'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 적용 중...</div>';
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


