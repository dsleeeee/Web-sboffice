/****************************************************************
 *
 * 파일명 : kioskKeyDel.js
 * 설  명 : 키오스크키맵삭제 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.06.05     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  터치키 조회 그리드 생성
 */
app.controller('kioskKeyDelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kioskKeyDelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("kioskKeyDelCtrl", function(event, data) {
        $("#kioskKeyPosNo").text(data);
        $scope.searchKioskKey();
        event.preventDefault();
    });

    $scope.searchKioskKey = function() {
        var params = {};

        params.posNo = $("#kioskKeyPosNo").text();
        params.tuClsType = $scope.kioKeyGrpCd;
        params.tuClsTypeNm = $scope.kioKeyGrpNm;
        if(pageFg === '1'){
            params.pageFg = '1';
            if($("#kioskKeyMapSelectStoreCd").val() !== null && $("#kioskKeyMapSelectStoreCd").val() !== ""){
                params.storeCd = $("#kioskKeyMapSelectStoreCd").val();
            }
        }
        $scope._inquiryMain("/base/prod/kioskKeyMap/kioskKeyMap/getClsTypeList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 삭제
    $scope.saveDel = function() {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        $scope.stepCnt = 10;    // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자

        $scope._popConfirm("선택하신 키맵그룹의 정보가 삭제되며 복구할 수 없습니다. 진행하시겠습니까?", function() {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].posNo = $("#kioskKeyPosNo").text();
                    if(pageFg === '1'){
                        $scope.flex.collectionView.items[i].pageFg = '1';
                        $scope.flex.collectionView.items[i].storeCd = $("#kioskKeyMapSelectStoreCd").val();
                    }
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            if(params.length <= 0) {
                $scope._popMsg(messages["cmm.not.select"]);
                return false;
            }

            $timeout(function () {
                setTimeout(function () {
                    // 저장
                    $scope.save2(params);
                }, 500);
            }, 10);
        });
    };

    // 저장
    $scope.save2 = function(orgParams) {
        $scope.totalRows = orgParams.length;    // 체크수
        var params = [];

        // 저장 시작이면 작업내역 로딩 팝업 오픈
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
            params.push(orgParams[i]);
        }

        console.log("총 갯수 :" + $scope.totalRows);
        console.log("진행 갯수 :" + $scope.progressCnt + "~" + (loopCnt - 1));
        console.log("---------------------------------------------------------------------");

        //가상로그인 session 설정
        var sParam = {};
        if(document.getElementsByName('sessionId')[0]){
            sParam['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/base/prod/kioskKeyMap/kioskKeyMap/getKioskKeyDelete.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    // 작업내역 로딩 팝업 닫기
                    $scope.excelUploadingPopup(false);
                    $scope._gridDataInit();
                    // 저장되었습니다.
                    $scope._popMsg(messages["cmm.saveSucc"]);
                    var selectStoreCd = $("#kioskKeyMapSelectStoreCd").val();
                    // 화면 리플레시
                    setTimeout(function() {
                        if(pageFg === '1'){
                            sessionStorage.setItem('reloaded', 'true');  // 새로 고침 여부 기록
                            sessionStorage.setItem('selectStoreCd', $("#kioskKeyMapSelectStoreCd").val());  // 새로 고침 여부 기록
                            sessionStorage.setItem('selectStoreNm', $("#kioskKeyMapSelectStoreNm").val());  // 새로 고침 여부 기록
                        }
                        location.reload();
                    }, 100);
                }
            }
        }, function errorCallback(response) {
            $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
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
                $scope.save2(orgParams);
            }
        });
    };

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

    // 닫기
    $scope.close = function () {
        $scope.srchKioKeyGrpCd = "";
        $scope.srchKioKeyGrpNm = "";

        $scope.kioskKeyDelLayer.hide();
    };

    // 엑셀다운로드 -->
    $scope.excelDownload = function(){
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	// 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function()	{
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.flex,
                {
                    includeColumnHeaders: 	true,
                    includeCellStyles	: 	false,
                    includeColumns      :	function (column) {
                        // return column.visible;
                        return column.binding != 'gChk';
                    }
                },messages["kioskKeyMap.kioskKeyMapRegist"] + '_' + messages["kioskKeyMap.kioskKeyDel"]+ '_'+ getCurDateTime() +'.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };

}]);