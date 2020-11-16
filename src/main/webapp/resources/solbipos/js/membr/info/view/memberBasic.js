/****************************************************************
 *
 * 파일명 : memberRegist.js
 * 설  명 : 회원정보관리 > 회원정보등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.23     김지은      1.0
 *
 * **************************************************************/

app.controller('memberBasicCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberBasicCtrl', $scope, $http, false));

    // 기본 회원등급
    if (memberClassList.length == 0) {
        memberClassList = [{value: "", name: "전체"}, {value: "001", name: "기본등급"}];
    }

    // 조회조건 콤보박스 데이터
    $scope._getComboDataQuery('055', 'rGendrFg', '');//성별
    $scope._getComboDataQuery('076', 'rWeddingYn', '');//결혼여부
    $scope._getComboDataQuery('067', 'rUseYn', ''); //사용여부
    $scope._getComboDataQuery('072', 'recvYn', ''); //sms수신
    $scope._getComboDataQuery('299', 'rMembrcardYn', ''); //카드발급구분

    /*$scope._setComboData("rGendrFg", genderDataMapEx);*/
    // $scope._setComboData("rWeddingYn", weddingDataMap);
    /*$scope._setComboData("rWeddingYn", weddingList);
    $scope._setComboData("rUseYn", useDataMap);*/
    $scope._setComboData("basicRegStoreCd", regstrStoreList);
    // $scope._setComboData("rMemberClass", memberClassList);
    // $scope._setComboData("rMemberClassSelect", memberClassSelect);

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
    $scope.$on("memberBasicCtrl", function (event, data) {

        $scope.setSelectedMember(data);

        if ($.isEmptyObject(data)) {
            $scope.resetForm();
            $scope.saveMode = "REG";
            $("#trMovePoint").css("display", "")
        } else {
            console.log($scope.member.temp);
            $scope.getMemberInfo();
            $scope.saveMode = "MOD";
            $("#trMovePoint").css("display", "none")
        }

        event.preventDefault();
    });

    /*********************************************************
     * 회원 등록을 위한 폼 리셋
     * *******************************************************/
    $scope.resetForm = function () {

        //$("#regForm")[0].reset();
        // $("#memberInfoTitle").text("");
        //$scope.$apply(function () {
            $scope.member.membrNo = '자동채번';
            $scope.member.beforeBizNo = '';

            // 등록매장 기본셋팅
            if(orgnFg == "STORE"){
                $scope.member.regStoreCd = orgnCd;
                if(hqOfficeCd == "00000"){
                    $scope.member.storeNm = orgnNm;
                }else{
                    $scope.basicRegStoreCdCombo.isReadOnly = true;
                }
            }else{
                $scope.basicRegStoreCdCombo.selectedIndex = 0;
                $scope.basicRegStoreCdCombo.isReadOnly = false;
            }

            $scope.member.membrNm = '';
            $scope.member.membrNicknm = '';
            $scope.member.membrEngNm = '';
            $scope.rMembrcardYn.selectedIndex = 1;
            $scope.member.membrCardNo = '';
            $scope.member.telNo = '01000000000';
            $scope.member.shortNo = '';
            $scope.genderCombo.selectedIndex = 0;

            // 결혼여부 기본셋팅
            $scope.weddingYnCombo.selectedIndex = 0;
            $("#trWeddingDay").css("display", "none");
            $scope.weddingDayCombo.selectedValue = new Date();
            $scope.weddingDayCombo.refresh();
            $scope.member.weddingDayCombo = new Date();

            // 생일구분 기본셋팅
            $("input:checkbox[id='birthChk']").prop("checked", false);
            $("input:radio[name='lunarYn']:radio[value='N']").prop('checked', true);
            $("input:radio[name='lunarYn']:radio[value='Y']").prop('checked', false);
            $("#divBirthday1").css("display", "none");
            $("#thBirthday2").css("display", "none");
            $("#thBirthday3").css("display", "none");
            $scope.birthdayCombo.selectedValue = new Date();
            $scope.birthdayCombo.refresh();
            $scope.member.birthday = new Date();

            $scope.rMemberClassSelectCombo.selectedIndex = 0;
            $scope.useYnCombo.selectedIndex = 1;
            $scope.member.lnPartner = '';
            $scope.member.cdCompany = '';
            $scope.member.cdPartner = '';
            $scope.member.emailAddr = '';
            $scope.member.postNo = '';
            $scope.member.addr = '';
            $scope.member.addrDtl = '';
            $scope.emailRecvYnCombo.selectedIndex = 1;
            $scope.smsRecvYnCombo.selectedIndex = 1;
            $scope.member.remark = '';
            $scope.member.phoneNo = '01000000000';
            // 이전포인트 초기값 세팅
            $scope.member.movePoint = 0;

        //});
    };

    /*********************************************************
     * 회원정보 조회
     * *******************************************************/
    $scope.getMemberInfo = function () {

        // 기존셋팅 한번 초기화
        $scope.resetForm();
        
        var params = $scope.getSelectedMember();

        $scope._postJSONQuery.withOutPopUp('/membr/info/view/base/getMemberInfo.sb', params, function (response) {

            if ($.isEmptyObject(response.data)) {
                $scope._popMsg(messages["cmm.empty.data"]);
                $scope.memberRegistLayer.hide();
                return false;
            }
            var memberDetailInfo = response.data.data;
            $scope.saveMode = "MOD";
            $scope.$emit("modMember", $scope.saveMode, memberDetailInfo);

            $("#memberInfoTitle").text("[" + memberDetailInfo.membrNo + "] " + memberDetailInfo.membrNm);

            // 결혼기념일을 입력한 경우
            if(memberDetailInfo.weddingYn === 'Y') {
                $("#trWeddingDay").css("display", "");
                memberDetailInfo.weddingday = stringToDate(memberDetailInfo.weddingday);
            }else{
                memberDetailInfo.weddingday = new Date();
            }

            // 생일을 입력한 경우
            if(memberDetailInfo.birthday != '' && memberDetailInfo.birthday != null) {
                $("#divBirthday1").css("display", "");
                $("#thBirthday2").css("display", "");
                $("#thBirthday3").css("display", "");

                $("input:checkbox[id='birthChk']").prop("checked", true);
                
                // 날짜 형태 변환
                memberDetailInfo.birthday =  stringToDate(memberDetailInfo.birthday);
            }else{
                memberDetailInfo.lunarYn = 'N';
                memberDetailInfo.birthday =  new Date();
            }

            // 데이터 바인딩
            $scope.member = memberDetailInfo;
            $scope.member.temp = orgnNm;

            console.log($scope.member);
        });
    };
    /*********************************************************
     * 값 체크
     * *******************************************************/
    $scope.valueCheck = function () {

        // 회원명을 입력하세요.
        var msg = messages["regist.membr.nm"] + messages["cmm.require.text"];
        if (isNull($scope.member.membrNm)) {
            $scope._popMsg(msg);
            return false;
        }

        // 등록매장을 선택해주세요.
        if (hqOfficeCd !== '00000') {
            var msg = messages["regist.reg.store.cd"] + messages["cmm.require.select"];
            if (isNull($scope.basicRegStoreCdCombo.selectedValue)) {
                $scope._popMsg(msg);
                return false;
            }
        }

        // 회원명 최대길이 체크
        if (nvl($scope.member.membrNm, '') !== '' && nvl($scope.member.membrNm + '', '').getByteLengthForOracle() > 50) {
            msg = messages["regist.membr.nm"] + messages["excelUpload.overLength"] + " 50 " + messages["excelUpload.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        // 회원명 영문 최대길이 체크
        if (nvl($scope.member.membrEngNm, '') !== '' && nvl($scope.member.membrEngNm + '', '').getByteLengthForOracle() > 50) {
            msg = messages["member.excel.nm.en"] + messages["excelUpload.overLength"] + " 50 " + messages["excelUpload.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        // 연락처(전화번호) 를 입력하세요.
        var msg = messages["regist.tel"] + messages["cmm.require.text"];
        if (isNull($scope.member.telNo)) {
            $scope._popMsg(msg);
            return false;
        }

        // 연락처(전화번호) 정규식
        var msg = messages["regist.tel"] + messages["cmm.require.number"];
        var numChkregexp = /[^0-9]/g;
        if (numChkregexp.test($scope.member.telNo)) {
            $scope._popMsg(msg);
            return false;
        }

        // 전화번호 최대길이 체크
        if (nvl($scope.member.telNo, '') !== '' && nvl($scope.member.telNo + '', '').getByteLengthForOracle() > 14) {
            msg = messages["regist.tel"] + messages["excelUpload.overLength"] + " 14 " + messages["excelUpload.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        // var msg = messages["regist.tel"]+messages["cmm.require.text"];
        // if( isNull( $scope.member.telNo )) {
        //     $scope._popMsg(msg);
        //     return false;
        // }


        // 핸드폰번호를 입력하세요.
        // var msg = messages["regist.phone.no"] + messages["cmm.require.text"];
        // if (isNull($scope.member.phoneNo)) {
        //     $scope._popMsg(msg);
        //     return false;
        // }

        // 핸드폰번호
        // var numChkregexp = /^\d{3}[-]?\d{3,4}[-]?\d{4}$/g;
        // if (!numChkregexp.test($scope.member.phoneNo)) {
        //     console.log($scope.member.phoneNo);
        //     console.log(numChkregexp.test($scope.member.phoneNo));
        //     $scope._popMsg(messages["cmm.not.phoneNo"]);
        //     return false;
        // }

        // 핸드폰번호 최대길이 체크
        // if (nvl($scope.member.phoneNo, '') !== '' && nvl($scope.member.phoneNo + '', '').getByteLengthForOracle() > 14) {
        //     msg = messages["regist.phone.no"] + messages["excelUpload.overLength"] + " 14 " + messages["excelUpload.bateLengthInfo"];
        //     $scope._popMsg(msg);
        //     return false;
        // }

        // 회원카드번호를 입력하세요.
        // var msg = messages["regist.membr.card.no"] + messages["cmm.require.text"];
        // if (isNull($scope.member.membrCardNo)) {
        //     $scope._popMsg(msg);
        //     return false;
        // }
        // 회원카드번호는 숫자만 입력할 수 있습니다.
        var msg = messages["regist.membr.card.no"] + messages["cmm.require.number.en"];
        var numChkregexp = /[^a-zA-Z0-9]/g;
        if(!isNull($scope.member.membrCardNo)) {
            if (numChkregexp.test($scope.member.membrCardNo)) {
                $scope._popMsg(msg);
                return false;
            }
        }

        // 결혼여부를 선택하세요.
        var msg = messages["regist.wedding"] + messages["cmm.require.select"];
        if (isNull($scope.weddingYnCombo.selectedValue)) {
            $scope._popMsg(msg);
            return false;
        }

        // 등급코드을 선택하세요.
        var msg = messages["grade.membr.grade.cd"] + messages["cmm.require.select"];
        if (isNull($scope.rMemberClassSelectCombo.selectedValue)) {
            $scope._popMsg(msg);
            return false;
        }

        //  사용여부를 선택하세요.
        var msg = messages["grade.membr.use.yn"] + messages["cmm.require.select"];
        if (isNull($scope.useYnCombo.selectedValue)) {
            $scope._popMsg(msg);
            return false;
        }

        // 이메일 최대길이 체크
        if (nvl($scope.member.emailAddr, '') !== '' && nvl($scope.member.emailAddr + '', '').getByteLengthForOracle() > 30) {
            msg = messages["regist.email"] + messages["excelUpload.overLength"] + " 30 " + messages["excelUpload.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        //  성별을 선택하세요.
        var msg = messages["regist.gender"] + messages["cmm.require.select"];
        if (isNull($scope.genderCombo.selectedValue)) {
            $scope._popMsg(msg);
            return false;
        }

        // 상세주소 최대길이 체크
        if (nvl($scope.member.addrDtl, '') !== '' && nvl($scope.member.addrDtl + '', '').getByteLengthForOracle() > 100) {
            msg = messages["regist.addr"] + messages["excelUpload.overLength"] + " 100 " + messages["excelUpload.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        //  이메일수신 여부을 선택하세요.
        var msg = messages["regist.email.recv"] + messages["cmm.require.select"];
        if (isNull($scope.emailRecvYnCombo.selectedValue)) {
            $scope._popMsg(msg);
            return false;
        }

        //  SMS수신 여부를 선택하세요.
        var msg = messages["regist.sms.recv"] + messages["cmm.require.select"];
        if (isNull($scope.smsRecvYnCombo.selectedValue)) {
            $scope._popMsg(msg);
            return false;
        }

        // 비고 최대길이 체크
        if (nvl($scope.member.remark, '') !== '' && nvl($scope.member.remark + '', '').getByteLengthForOracle() > 300) {
            msg = messages["regist.remark"] + messages["excelUpload.overLength"] + " 300 " + messages["excelUpload.bateLengthInfo"];
            $scope._popMsg(msg);
            return false;
        }

        // 이전포인트 숫자만
        var msg = messages["regist.membr.move.point"] + messages["cmm.require.number.en"];
        var numChkregexp = /[^0-9]/g;
        if(!isNull($scope.member.movePoint)) {
            if (numChkregexp.test($scope.member.movePoint)) {
                $scope._popMsg(msg);
                return false;
            }
        }
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

        /*if($scope.member.telNo === ''){
            $scope.member.telNo = '01000000000';
        }*/
        
        // 기혼인 경우만 결혼날짜 입력
        if($scope.weddingYnCombo.selectedIndex === 1) {
            params.weddingday = dateToDaystring($scope.member.weddingday);
        }else{
            params.weddingday = '';
        }
        
        // 생일을 입력한 경우만 생일 입력
        if($("input:checkbox[id='birthChk']").is(":checked")) {
            params.birthday = dateToDaystring($scope.member.birthday);
            params.lunarYn = $(":input:radio[name=lunarYn]:checked").val();
        }else{
            params.birthday = '';
        }
        // 단축번호
        if (params.shortNo === "") {
            if(params.telNo.length < 4) {
                params.shortNo = params.telNo;
            } else {
                params.shortNo = params.telNo.substr(params.telNo.length - 4, 4);
            }
        }
        // 단독매장의 경우
        /*if (hqOfficeCd === '00000') {
            params.regStoreCd = $("#basicRegStoreCd").val();
        }*/
        // console.log(params);

        var memberInfoScope = agrid.getScope('memberCtrl');

        // 회원 신규 등록시
        if ($.isEmptyObject($scope.selectedMember)) {
            if ($scope.saveMode === "REG") {
                $scope._postJSONSave.withPopUp("/membr/info/view/base/registMemberInfo.sb", params, function (result) {
                    var scope = agrid.getScope('memberCtrl');
                    $scope._popMsg(messages["cmm.saveSucc"]);
                    // console.log(result.data.data)
                    $scope.$emit("responseGet", result.data.data, $scope.saveMode);
                    $scope.memberRegistLayer.hide();
                    scope.getMemberList();
                });
            }
        } else {
            // 수정
            if ($scope.saveMode === "MOD") {
                $scope._postJSONSave.withPopUp("/membr/info/view/base/updateMemberInfo.sb", params, function (result) {
                    var scope = agrid.getScope('memberCtrl');
                    $scope._popMsg(messages["cmm.saveSucc"]);
                    $scope.$emit("responseGet", result.data.data, $scope.saveMode);
                    $scope.memberRegistLayer.hide();
                    scope.getMemberList();
                    $scope.$broadcast("memberChgBatchCtrl");
                    // $scope.memberInfoDetailLayer.hide();
                });
            }
        }

        // 데이터 초기화 (수정, 저장 후 다시 팝업 호출 시, 예전 정보가 남아있는 경우가 있어서)
        $scope.member = {};
    };

    // 닫기
    $scope.close = function () {
        $scope.member = {};
        $scope.memberRegistLayer.hide();
    };

    // form 변화
    $scope.$watch('member', function () {

    }, true);

    // 생일 구분 입력 CheckBox 클릭에 따른 VIEW 변화
    $scope.changeWeddingCombo = function (s, e) {

        if($scope.saveMode === "REG") {
            // 날짜 다시 오늘날짜로 새로 셋팅
            $scope.weddingDayCombo.selectedValue = new Date();
            $scope.weddingDayCombo.refresh();
            $scope.member.weddingday = new Date();
        }

        if (s.selectedValue === 'N') {
            $("#trWeddingDay").css("display", "none");
            $scope.weddingDayCombo.isReadOnly = true;
        } else {
            $("#trWeddingDay").css("display", "");
            $scope.weddingDayCombo.isReadOnly = false;
        }
    };

    // 생일 구분 입력 CheckBox 클릭에 따른 VIEW 변화
    $scope.showBirthday = function () {

        if($scope.saveMode === "REG"){

            // 날짜 다시 오늘날짜로 새로 셋팅
            $scope.birthdayCombo.selectedValue = new Date();
            $scope.birthdayCombo.refresh();
            $scope.member.birthday = new Date();

            if($("input:checkbox[id='birthChk']").is(":checked")){
                $("#divBirthday1").css("display", "");
                $("#thBirthday2").css("display", "");
                $("#thBirthday3").css("display", "");
                $("input:radio[name='lunarYn']:radio[value='N']").prop('checked', true);

            }else{
                $("#divBirthday1").css("display", "none");
                $("#thBirthday2").css("display", "none");
                $("#thBirthday3").css("display", "none");
                $("input:radio[name='lunarYn']:radio[value='N']").prop('checked', false);
                $("input:radio[name='lunarYn']:radio[value='Y']").prop('checked', false);
            }

        }else{

            if($("input:checkbox[id='birthChk']").is(":checked")){
                $("#divBirthday1").css("display", "");
                $("#thBirthday2").css("display", "");
                $("#thBirthday3").css("display", "");
            }else{
                $("#divBirthday1").css("display", "none");
                $("#thBirthday2").css("display", "none");
                $("#thBirthday3").css("display", "none");
            }
        }

    }


    /*********************************************************
     * 회원 거래처 매핑코드 조회(보나비)
     * *******************************************************/
    $scope.searchMemberMappingCd = function(){
        $scope.memberMappingLayer.show(true, function(s) {

            var memberMappingScope = agrid.getScope('memberMappingCtrl');
            // console.log('getCompany', memberMappingScope.getCompany());
            $scope.$apply(function(){
                if( !$.isEmptyObject(memberMappingScope.getCompany())) {
                    $scope.member.lnPartner = memberMappingScope.getCompany().lnPartner;
                    $scope.member.cdCompany = memberMappingScope.getCompany().cdCompany;
                    $scope.member.cdPartner = memberMappingScope.getCompany().cdPartner;
                }
            });
        });
    };

    /*********************************************************
     * 주소검색 TODO
     * *******************************************************/
    // $scope.searchAddr = function(){
    // };

    // 화면 ready 된 후 설정
    // angular.element(document).ready(function () {
    //     // 회원조회 팝업 핸들러 추가
    //     $scope.memberMappingLayer.shown.addHandler(function (s) {
    //         setTimeout(function() {
    //             // $scope._broadcast('memberMappingCtrl');
    //         }, 50)
    //     });
    // });


}]);
