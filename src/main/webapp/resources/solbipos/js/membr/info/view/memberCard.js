// var app = agrid.getApp();
app.controller('memberCardCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberCardCtrl', $scope, $http, false));
    // 조회조건 콤보박스 데이터
    //$scope._setComboData("rMembrcardYn", rMembrcardList);
    // $scope._getComboDataQuery('300', 'cstCardIssFg', 'A');
    //$scope._getComboDataQuery('301', 'cstCardIssFg', '');
    // $scope._getComboDataQuery('067', 'useYn', '');

    var cstCardIssFgNew = [{value:"0",name:"신규발급"}];
    var cstCardIssFgRe = [{value:"1",name:"재발급(분실)"},{value:"2",name:"재발급(훼손)"},{value:"3", name:"재발급(기타)"}];

    $scope._setComboData("cstCardIssFg", cstCardIssFgNew);

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
    $scope.$on("memberRegistCtrl", function (event, data) {

        $scope.setSelectedMember(data);

        $scope.resetCardForm();

        if ($.isEmptyObject(data)) {
            $scope.saveMode = "REG";
        } else {
            $scope.getMemberInfo();
        }

        event.preventDefault();
    });

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

        //$("#regCardForm")[0].reset();
        $("#memberInfoTitle").text("");

        $scope.$apply(function () {
            $("#rMembrCardOrgn").val(sessionInfo.orgnCd + "/" + sessionInfo.orgnNm);
            $scope.member.cstCardIssFg = '';
            $scope.member.membrCardNo = '';
            $scope.issDateCombo.selectedValue = new Date();
            $scope.issDateCombo.refresh();
            $scope.member.issDate = new Date();
            $scope.member.issRemark = '';
            if($scope.saveMode === 'MOD'){
                $scope.rMembrcardIssFgCombo.refresh();
                $scope._setComboData("cstCardIssFg", cstCardIssFgRe);
                $scope.member.cstCardIssFg = '1';
            }else if($scope.saveMode === 'REG'){
                $scope.rMembrcardIssFgCombo.refresh();
                $scope._setComboData("cstCardIssFg", cstCardIssFgNew);
                $scope.member.cstCardIssFg = '0';
                $scope.member.oldCstCardNo = '';
            }
        });
    };

    $scope.$on("paramsCard", function (event, params) {
        $scope.params = params;
        // $scope.$broadcast("paramsGet", params);
    });

    /*********************************************************
     * 회원정보 조회
     * *******************************************************/
    $scope.$on("getCardList", function () {
        var params = {};
        params.membrNo = $scope.params.membrNo;
        // params.membrOrgnCd = $scope.card.data.data.membrOrgnCd;
        $scope._inquiryMain("/membr/info/view/view/getCardlist.sb", params, function () {
            //$("#rMembrCardOrgn").val(sessionInfo.orgnCd + "/" + sessionInfo.orgnNm);
            if ($scope.data.items.length > 0) {
                $scope.saveMode = "MOD";
                $scope.member.oldCstCardNo = $scope.data.items[0].membrCardNo;
                $('#rOldCstCardNo').val($scope.data.items[0].membrCardNo);
            }else{
                $scope.saveMode = "REG";
            }
            $scope.resetCardForm();
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
        if (isNull($scope.rMembrcardIssFgCombo.selectedValue)) {
            $scope._popMsg(msg);
            return false;
        }

        // 본사일때만
        if(orgnFg == "HQ") {
            // 발급소속을 선택해주세요.
            var msg = messages["regist.card.org"] + messages["cmm.require.select"];
            if (isNull($scope.basicRegStoreCdCombo.selectedValue)) {
                $scope._popMsg(msg);
                return false;
            }
        }

        // 신규카드번호를 입력하세요.
        var msg = messages["regist.card.new.no"] + messages["cmm.require.text"];
        if (isNull($scope.member.membrCardNo)) {
            $scope._popMsg(msg);
            return false;
        }

        // 카드번호는 숫자만 입력할 수 있습니다.
        var msg = messages["regist.card.new.no"] + messages["cmm.require.number.en"];
        var numChkregexp = /[^a-zA-Z0-9]/g;
        if (numChkregexp.test($scope.member.membrCardNo)) {
            $scope._popMsg(msg);
            return false;
        }

        // 카드번호
        // var msg = messages["regist.card.new.no"] + messages["regist.card.add.new"];
        // $scope.data.items.forEach( function (e) {
        //     if (e.oldCstCardNo == $scope.member.membrCardNo) {
        //         $scope._popMsg(msg);
        //         return false;
        //     }
        // });

        // 카드번호 최대길이 체크
        if (nvl($scope.member.membrCardNo, '') !== '' && nvl($scope.member.membrCardNo + '', '').getByteLengthForOracle() > 16) {
            msg = messages["regist.card.new.no"] + messages["excelUpload.overLength"] + " 16 " + messages["excelUpload.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        // 발급일자를 선택해주세요.
        var msg = messages["regist.card.iss.date"] + messages["cmm.require.select"];
        if (isNull($scope.member.issDate)) {
            $scope._popMsg(msg);
            return false;
        }

        // 비고 최대길이 체크
        if (nvl($scope.member.issRemark, '') !== '' && nvl($scope.member.issRemark + '', '').getByteLengthForOracle() > 100) {
            msg = messages["regist.card.iss.remark"] + messages["excelUpload.overLength"] + " 100 " + messages["excelUpload.bateLengthInfo"];
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
        params.membrNo = $scope.params.membrNo;
        var leng = $scope.member.issDate.length;
        if(typeof leng !== 'undefined'){
            params.issDate = $scope.member.issDate;
        }else{
            params.issDate = getFormatDateString($scope.member.issDate);
        }
        var memberInfoScope = agrid.getScope('memberCtrl');

        // var msg = messages["regist.card.add.overlap"];
        // $scope._postJSONQuery.withPopUp("/membr/info/view/base/registCardInfoCount.sb", params, function (result) {
        // if(result.data.data > 0){
        $scope._postJSONQuery.withPopUp("/membr/info/view/base/getMemberCardInfoCountDetail.sb", params, function (result) {
            if(result.data.data !== "X"){
                $scope._popMsg("회원번호[" + result.data.data + "]가 카드번호 [" + $scope.member.membrCardNo + "] 사용중입니다.");
                return false;
            }else{
                if ($scope.saveMode === "REG") {
                    $scope._postJSONSave.withPopUp("/membr/info/view/base/registCardInfo.sb", params, function (regResult) {
                        $scope._popMsg(messages["cmm.saveSucc"]);
                        $scope.$emit("responseGet", params, $scope.saveMode);
                        $scope.memberRegistLayer.hide();
                        memberInfoScope.getMemberList();
                        memberInfoScope.memberRegistLayer.show(true);
                    });
                }
                // 수정
                else if ($scope.saveMode === "MOD") {
                    $scope._postJSONSave.withPopUp("/membr/info/view/base/updateMemberCard.sb", params, function (updateResult) {
                        $scope._popMsg(messages["cmm.saveSucc"]);
                        $scope.$emit("responseGet", params, $scope.saveMode);
                        $scope.memberRegistLayer.hide();
                        memberInfoScope.getMemberList();
                        memberInfoScope.memberRegistLayer.show(true);
                    });
                }
            }
        });
    };

    // 화면 ready 된 후 설정
    // angular.element(document).ready(function () {
    //     // 회원조회 팝업 핸들러 추가
    //     $scope.memberMappingLayer.shown.addHandler(function (s) {
    //         setTimeout(function () {
    //             // $scope._broadcast('memberMappingCtrl');
    //         }, 50)
    //     });
    // });
}]);