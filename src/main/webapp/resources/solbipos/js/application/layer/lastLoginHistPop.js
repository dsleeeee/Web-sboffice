/****************************************************************
 *
 * 파일명 : lastPwdHistPop.js
 * 설  명 : 최근접속이력 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.02.16     김설아      1.0
 *
 * **************************************************************/

// $(".btn_close").click(function() {
//     $("#fullDimmedLastLoginHistPop").hide();
//     $("#layerLastLoginHistPop").hide();
// });

// 비고
var remarkDataMapData = [
    {"name":"로그인 성공","value":"SUCC"},
    {"name":"로그인 실패","value":"FAIL"},
    {"name":"사용여부","value":"USE"},
    {"name":"존재하지 않는 유져","value":"NID"},
    {"name":"패스워드 틀림","value":"ERR"},
    {"name":"패스워드 변경 필요한 상태","value":"CHG"},
    {"name":"패스워드 유효 기간 지남","value":"EXP"},
    {"name":"로그인횟수 초과","value":"CNT"},
    {"name":"자동 로그인 실패","value":"LAF"},
    {"name":"휴면계정","value":"DA"},
    {"name":"계정 사용 중지","value":"NWUI"}
];

/**
 *  최근접속이력 그리드 생성
 */
app.controller('lastLoginHistCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('lastLoginHistCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.remarkDataMap = new wijmo.grid.DataMap(remarkDataMapData, 'value', 'name'); // 비고

        // 조회
        $scope.searchLastLoginHist();
    };

    // <-- 검색 호출 -->
    $scope.$on("lastLoginHistCtrl", function(event, data) {
        $scope.searchLastLoginHist();
        event.preventDefault();
    });

    $scope.searchLastLoginHist = function(){
        var params = {};

        $scope._inquirySub("/user/getLastLoginHistList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 닫기
    $scope.close = function(){
        $("#fullDimmedLastLoginHistPop").hide();
        $("#layerLastLoginHistPop").hide();
    };

    // 1. 쿠키 만들기
    function setCookie(name, value, expiredays) {
        var today = new Date();
        today.setDate(today.getDate() + expiredays);
        document.cookie = name + '=' + escape(value) + '; path=/; expires=' + today.toGMTString() + ';'
    }

    // 2. 쿠키 가져오기
    function getCookie(name){
        var cName = name + "=";
        var x = 0;
        while ( x <= document.cookie.length )
        {
            var y = (x+cName.length);
            if ( document.cookie.substring( x, y ) == cName )
            {
                if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
                    endOfCookie = document.cookie.length;
                return unescape( document.cookie.substring( y, endOfCookie ) );
            }
            x = document.cookie.indexOf( " ", x ) + 1;
            if ( x == 0 )
                break;
        }
        return "";
    }

    // alert(getCookie("notLastLoginHistPop"));

    // 쿠키체크 후 팝업 띄우기
    if(getCookie("notLastLoginHistPop")!="Y") {
        // 메인화면 진입인지 체크
        if(mainYn == "Y") {
            $("#fullDimmedLastLoginHistPop").show();
            $("#layerLastLoginHistPop").show();
        }
    }

    // 7일간 제외
    $scope.dayRemove = function(){
        setCookie('notLastLoginHistPop','Y', 7);
        $("#fullDimmedLastLoginHistPop").hide();
        $("#layerLastLoginHistPop").hide();
    };

}]);