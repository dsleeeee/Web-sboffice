/****************************************************************
 *
 * 파일명 : menuPasswordChk.js
 * 설  명 : 메뉴 이동 시 비밀번호 확인 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.07.22     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
// var app = agrid.getApp();

/**
 *  비밀번호 확인 그리드
 */
app.controller('menuPasswordChkCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('menuPasswordChkCtrl', $scope, $http, false));


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    $scope.$on("menuPasswordChkCtrl", function(event, data) {
        $scope.menuPasswordChkLayer.show(true);
        $scope.data = data;
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 숫자 클릭 (커서 기준 오른쪽 삽입)
    $scope.pressKey = function(num){
        var pwdTxt = document.getElementById("pwdTxt");
        var start = pwdTxt.selectionStart;
        var end = pwdTxt.selectionEnd;
        if (pwdTxt.value.length >= 4) return; // 최대 4자리 제한

        var before = pwdTxt.value.slice(0, start);
        var after = pwdTxt.value.slice(end);

        pwdTxt.value = before + num + after;

        // 커서 이동: 삽입된 문자 뒤로
        var newPos = start + 1;
        pwdTxt.setSelectionRange(newPos, newPos);
        pwdTxt.focus();
        
    };

    // CLR 클릭(비밀번호 입력 칸 초기화)
    $scope.clearPwd = function (){
        document.getElementById("pwdTxt").value = "";
        $scope.password = '';
    }

    // ← 클릭 (커서 기준 왼쪽 비밀번호 지우기)
    $scope.backSpace = function (){

        var pwdTxt = document.getElementById("pwdTxt");
        var start = pwdTxt.selectionStart;
        var end = pwdTxt.selectionEnd;

        if (start > 0) {
            // 왼쪽 문자 제거
            pwdTxt.value = pwdTxt.value.slice(0, start - 1) + pwdTxt.value.slice(end);
            // 커서 위치 갱신
            pwdTxt.setSelectionRange(start - 1, start - 1);
            pwdTxt.focus();
        }

    }

    // 로그인
    $scope.loginPwdChk = function (){
        var params = {};
        params.confirmPassword = $("#pwdTxt").val();

        // 비밀번호 검증
        $scope._postJSONQuery.withOutPopUp("/menu/loginPwdChk.sb", params, function(response) {
            var result = response.data.data;

            if(result.toString() === '0'){
                $("#chkMsg").text('비밀번호를 다시 확인해주세요');
            }else if(result.toString() === '1'){
                // 비밀번호 맞을 시 팝업 hide 및 text 초기화
                $scope.menuPasswordChkLayer.hide();
                $("#pwdTxt").val('');
                $("#chkMsg").text('');
                
                // url 있을 시(소메뉴 클릭 시) 화면 이동
                if (!isEmpty($scope.data.selectedNode.dataItem.url)) {
                    // 가상로그인시 파라미터인 SessionID 설정
                    if (document.getElementsByName('sessionId').length > 0) {
                        var vSessionId = document.getElementsByName('sessionId')[0].value;
                        location.href = $scope.data.selectedNode.dataItem.url + '?sid=' + vSessionId;
                    } else {
                        location.href = $scope.data.selectedNode.dataItem.url;
                    }
                }else{
                    // url 없을 시(대메뉴, 중메뉴 클릭 시) pageAccessChkPwdYn값 세팅
                    document.getElementsByName('pageAccessChkPwdYn')[0].value = 'Y';
                    
                    // 메뉴 오픈
                    var menuCtrl = agrid.getScope('menuCtrl');
                    menuCtrl.itemClicked($scope.data);
                }
            }
        });

    }

    $scope.close = function (){
        $("#pwdTxt").val('');
        $("#chkMsg").text('');
    }
}]);
