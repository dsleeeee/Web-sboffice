/****************************************************************
 *
 * 파일명 : memberInfoDtl.js
 * 설  명 : 회원정보 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.11.20     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 공개대상
var lunarYnData = [
    {"name":"양력","value":"Y"},
    {"name":"음력","value":"N"}
];

/**
 *  회원정보 그리드 생성
 */
app.controller('memberInfoDtlCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberInfoDtlCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터
    $scope._getComboDataQuery('299', 'rMembrcardYn', ''); // 회원카드구분
    $scope._getComboDataQuery('076', 'rWeddingYn', '');//결혼여부
    $scope._setComboData("rMemberClassSelect", memberClassSelect); //회원등급
    $scope._setComboData("rLunarYn", lunarYnData); // 음력/양력 구분
    $scope._getComboDataQuery('067', 'rUseYn', ''); //사용여부
    $scope._getComboDataQuery('055', 'rGendrFg', '');//성별
    $scope._getComboDataQuery('072', 'rRecvYn', ''); //sms수신

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // 선택 회원
    $scope.selectedMember;
    $scope.setSelectedMember = function (data) {
        $scope.selectedMember = data;
    };
    $scope.getSelectedMember = function () {
        return $scope.selectedMember;
    };

    // <-- 검색 호출 -->
    $scope.$on("memberInfoDtlCtrl", function(event, data) {
        if( !isEmptyObject(data) ) {
            $scope.setSelectedMember(data);
            $scope.searchMemberInfoDtl();
        }
        event.preventDefault();
    });

    $scope.searchMemberInfoDtl = function(){
        var params = $scope.getSelectedMember();

        $scope._postJSONQuery.withOutPopUp('/membr/info/view/base/getMemberInfo.sb', params, function (response) {
            if ($.isEmptyObject(response.data)) {
                $scope._popMsg(messages["cmm.empty.data"]);
                $scope.wjMemberInfoDtlLayer.hide();
                return false;
            }

            var memberDetailInfo = response.data.data;

            $("#lblMemberInfoDtl").text(" [" + memberDetailInfo.membrNo + "] " + memberDetailInfo.membrNm);

            // 결혼기념일을 입력한 경우
            if(memberDetailInfo.weddingYn === 'Y') {
                $("#trWeddingDay").css("display", "");
                memberDetailInfo.weddingday = dateToDaystring(stringToDate(memberDetailInfo.weddingday));
            }else{
                $("#trWeddingDay").css("display", "none");
            }

            // 생일을 입력한 경우
            if(memberDetailInfo.birthday != '' && memberDetailInfo.birthday != null) {
                $("#divBirthday1").css("display", "");
                $("#thBirthday2").css("display", "");
                $("#thBirthday3").css("display", "");

                // 날짜 형태 변환
                memberDetailInfo.birthday =  dateToDaystring(stringToDate(memberDetailInfo.birthday));
            }else{
                memberDetailInfo.lunarYn = 'N';
            }

            // 데이터 바인딩
            $scope.member = memberDetailInfo;
        });
    };
    // <-- //검색 호출 -->

    // 팝업 닫기
    $scope.close = function(){
        $scope.wjMemberInfoDtlLayer.hide();

        // 저장기능 수행후 재조회
        $scope._broadcast('memberChgBatchCtrl', null);
    };
}]);