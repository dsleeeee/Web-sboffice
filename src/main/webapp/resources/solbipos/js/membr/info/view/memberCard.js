// var app = agrid.getApp();
app.controller('memberCardCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberCardCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터
    // $scope._setComboData("rCstCardIssFg", rCstCardIssFgList);
    // $scope._setComboData("rCstCardStatFg", rCstCardStatFgList);

    $scope.selectedMember;
    $scope.setSelectedMember = function (data) {
        $scope.selectedMember = data;
    };
    $scope.getSelectedMember = function () {
        return $scope.selectedMember;
    };

    // 저장/수정인지 파악하기 위해
    $scope.saveMode = "REG";

    /*********************************************************
     * 팝업 오픈
     * 선택된 회원이 없으면 : 신규등록 (폼 리셋)
     * 선택된 회원이 있으면 : 데이터 수정
     * *******************************************************/
    $scope.$on("memberRegistCtrl", function(event, data) {

        $scope.setSelectedMember(data);

        if($.isEmptyObject(data) ) {
            $scope.resetCardForm();
            $scope.saveMode = "REG";
        } else {
            $scope.getMemberInfo();
        }

        event.preventDefault();
    });


    $scope.changeWeddingCombo = function(s, e){
        if(s.selectedValue === 'Y') {
            $scope.weddingDayCombo.isReadOnly     = false;
        } else {
            $scope.weddingDayCombo.isReadOnly     = true;
        }
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {


        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                // 회원번호, 회원명 클릭시 상세정보 팝업
                if (col.binding === "membrNo" || col.binding === "membrNm") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
                // 후불적용매장등록 클릭시 매장선택 팝업
                if (col.binding === "postpaidStore") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });
    };
    /*********************************************************
     * 회원 등록을 위한 폼 리셋
     * *******************************************************/
    $scope.resetCardForm = function () {

        $("#regCardForm")[0].reset();
        $("#memberInfoTitle").text("");

        $scope.$apply(function () {
            $scope.member.cstCardIssFg = '';
            $scope.member.membrCardNo = '';
            $scope.member.oldCstCardNo = '';
            $scope.member.issDate = '';
            $scope.member.issRemark = '';
        });
    };

    $scope.$on("paramsCard", function (event, params) {
        $scope.membrNo = params;
        // $scope.$broadcast("paramsGet", params);
    });

    /*********************************************************
     * 회원정보 조회
     * *******************************************************/
    $scope.$on("getCardList", function () {
        var params = {};
        params.membrNo = $scope.membrNo;
        // params.membrOrgnCd = $scope.card.data.data.membrOrgnCd;
        $scope._inquiryMain("/membr/info/view/view/getCardlist.sb", params, function () {
        });
    });
    /*********************************************************
     * 값 체크
     * *******************************************************/
    $scope.valueCheck = function () {

        // 신규등록일 경우
        //if( $.isEmptyObject($scope.selectedMember) ){

        // 발급구분을 선택해주세요.
        var msg = messages["regist.card.fg"] + messages["cmm.require.select"];
        if (isNull($scope.cstCardIssFgCombo.selectedValue)) {
            $scope._popMsg(msg);
            return false;
        }

        // 신규카드번호를 입력하세요.
        var msg = messages["regist.card.new.no"] + messages["cmm.require.text"];
        if (isNull($scope.member.membrCardNo)) {
            $scope._popMsg(msg);
            return false;
        }

        // 발급일자를 선택해주세요.
        var msg = messages["regist.card.iss.date"] + messages["cmm.require.select"];
        if (isNull($scope.issDateCombo.selectedValue)) {
            $scope._popMsg(msg);
            return false;
        }

        // 전화번호는 숫자만 입력할 수 있습니다.
        // var msg = messages["regist.tel"] + messages["cmm.require.number"];
        // var numChkregexp = /[^0-9]/g;
        // if (numChkregexp.test($scope.member.telNo)) {
        //     $scope._popMsg(msg);
        //     return false;
        // }
        //
        // if($scope.saveMode === "REG"){
        //   // 회원 거래처 매핑코드를 선택해주세요.
        //   var msg = messages["regist.membr.mappingCd"]+messages["cmm.require.select"];
        //   if( isNull( $scope.member.cdCompany)) {
        //     $scope._popMsg(msg);
        //     return false;
        //   }
        // }

        return true;
    };

    /*********************************************************
     * 저장
     * *******************************************************/
    $scope.save = function () {

        if (!$scope.valueCheck()) return false;
        var params = $scope.member;
        params.membrNo = $scope.membrNo;
        params.issDate = getFormatDateString($scope.member.issDate);

        var memberInfoScope = agrid.getScope('memberCtrl');

        $scope._postJSONSave.withPopUp("/membr/info/view/base/registCardInfo.sb", params, function (result ) {
            $scope._popMsg(messages["cmm.saveSucc"]);
            $scope.memberRegistLayer.hide();
            memberInfoScope.getMemberList();
        });
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {
        // 회원조회 팝업 핸들러 추가
        $scope.memberMappingLayer.shown.addHandler(function (s) {
            setTimeout(function () {
                // $scope._broadcast('memberMappingCtrl');
            }, 50)
        });
    });
}]);
