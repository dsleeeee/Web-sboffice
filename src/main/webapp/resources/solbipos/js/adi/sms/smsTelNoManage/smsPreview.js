/****************************************************************
 *
 * 파일명 : smsPreview.js
 * 설  명 : SMS 발신번호 서류인증 미리보기 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.11.06     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  SMS 발신번호 서류인증 미리보기
 */
app.controller('smsPreviewCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('smsPreviewCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // <-- 검색 호출 -->
    $scope.$on("smsPreviewCtrl", function(event, data) {
        if(data !== undefined && !isEmptyObject(data)) {
            var params = {};
            params.orgnCd = data.orgnCd;
            params.userId = data.userId;
            params.certId = data.certId;
            params.gubun = data.gubun;

            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._postJSONQuery.withOutPopUp("/adi/sms/smsTelNoManage/smsPreview/getSmsPreviewList.sb", params, function (response) {
                var fileInfo = response.data.data;

                var fileNm = "";
                if(params.gubun == "1") {
                    fileNm = fileInfo.fileName1;
                } else if(params.gubun == "2") {
                    fileNm = fileInfo.fileName2;
                } else if(params.gubun == "3") {
                    fileNm = fileInfo.fileName3;
                }

                // 이미지 셋팅
                // $("#imgPreview").attr("src", "http://neo.solbipos.com/Board/addSmsNo/" + fileNm);
                $("#imgPreview").attr("src", "http://" + window.location.host + "/Board/addSmsNo/" + fileNm);
            });
        } else {
            $("#imgPreview").attr("src", "");
        }

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 팝업 닫기
    $scope.close = function() {
        // 이미지 초기화
        $("#imgPreview").attr("src", "");
    };

}]);