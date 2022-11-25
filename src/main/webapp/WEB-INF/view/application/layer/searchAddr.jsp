<%-- 우편번호 찾기 팝업 : daum 우편번호 서비스 API 사용 --%>
<!-- iOS에서는 position:fixed 버그가 있음, 적용하는 사이트에 맞게 position:absolute 등을 이용하여 top,left값 조정 필요 -->

<%-- 우편번호 찾기 팝업 오픈 시, backgrond dimmed 처리 --%>
<div id="dimmed" class="fullDimmed" style="display: none;z-index:99999;"></div>
<%-- 우편번호 찾기 화면이 보여질 div --%>
<div id="layer" style="display:none;position:fixed;overflow:hidden;z-index:100000;-webkit-overflow-scrolling:touch;">
    <img src="/resource/solbipos/css/img/fixedMenu_close_off.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="닫기 버튼">
</div>

<%-- 우편번호 서비스 API --%>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<%-- KAKAO Maps API --%>
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=1151239a9ff1ce860e3af6ba25465ee9&libraries=services"></script>
<script>

    // 화면 구분자 값을 파악하여, 부모창의 element id명을 파악한다.
    // element id를 jsp:param 방식으로 파라미터에 싣어 사용하려 했으나... kakao에서 불허하여 호출이 거부됨.
    // === 사용중인 화면들 ====
    // hqInfo : 본사등록
    // storeInfo : 매장등록
    // agencyInfo : 총판/대리점등록
    // myInfo : 본사정보관리(구: 내정보관리)
    // vendrRegist : 거래처등록
    // memberBasic : 회원등록
    var pageNm = $("#pageNm").val();
    var elementId = [];

    if(pageNm === "hqInfo" || pageNm === "vendrRegist"){
        elementId[0] = "rPostNo";
        elementId[1] = "rAddr";
        elementId[2] = "rAddrDtl";
        elementId[3] = "";
        elementId[4] = "";
    }else if(pageNm === "memberBasic") {
        elementId[0] = "rPostNo";
        elementId[1] = "rAddr";
        elementId[2] = "rAddrDtl";
        elementId[3] = "rLatitude";
        elementId[4] = "rLongitude";
    }else if(pageNm === "agencyInfo"){
        elementId[0] = "ai_postNo";
        elementId[1] = "ai_addr";
        elementId[2] = "ai_addrDtl";
        elementId[3] = "";
        elementId[4] = "";
    }else if(pageNm === "storeInfo"){
        elementId[0] = "postNo";
        elementId[1] = "addr";
        elementId[2] = "addrDtl";
        elementId[3] = "latitude";
        elementId[4] = "longitude";
    }else{
        elementId[0] = "postNo";
        elementId[1] = "addr";
        elementId[2] = "addrDtl";
        elementId[3] = "";
        elementId[4] = "";
    }

    // 우편번호 찾기 팝업을 띄울 때 background dimmed
    var element_dimmed = document.getElementById('dimmed');

    // 우편번호 찾기 화면을 넣을 element
    var element_layer = document.getElementById('layer');

    // 주소-좌표 변환 객체를 생성합니다
    var geocoder = new kakao.maps.services.Geocoder();

    // 우편번호 찾기 팝업 닫기
    function closeDaumPostcode() {
        // iframe을 넣은 element를 안보이게 한다.
        element_dimmed.style.display = 'none';
        element_layer.style.display = 'none';
    }

    // 우편번호 검색
    function searchAddr() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    //if(data.buildingName !== '' && data.apartment === 'Y'){
                    if(data.buildingName !== ''){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById(elementId[0]).value = data.zonecode;
                document.getElementById(elementId[1]).value = addr + (extraAddr !== '' ? (' ' + extraAddr) : '');
                // 기존 상세주소 초기화 및 커서를 상세주소 필드로 이동한다.
                document.getElementById(elementId[2]).value = "";
                document.getElementById(elementId[2]).focus();

                // 기본주소로 위도와 경도값 추출
                geocoder.addressSearch(data.address, function(results, status) {
                    // 정상적으로 검색이 완료됐으면
                    if (status === kakao.maps.services.Status.OK) {
                        var result = results[0]; //첫번째 결과의 값을 활용
                        console.log("location :" + result.y + "," + result.x);

                        if(elementId[3] !== "" && elementId[4] !== ""){
                            document.getElementById(elementId[3]).value = result.y;
                            document.getElementById(elementId[4]).value = result.x;
                        }
                    }
                });

                // iframe을 넣은 element를 안보이게 한다.
                // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
                element_dimmed.style.display = 'none';
                element_layer.style.display = 'none';
            },
            width : '100%',
            height : '100%',
            maxSuggestItems : 5
        }).embed(element_layer);

        // iframe을 넣은 element를 보이게 한다.
        element_dimmed.style.display = 'block';
        element_layer.style.display = 'block';

        // iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
        initLayerPosition();
    }

    // 브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
    // resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
    // 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
    function initLayerPosition(){
        var width = 500; //우편번호서비스가 들어갈 element의 width
        var height = 470; //우편번호서비스가 들어갈 element의 height
        var borderWidth = 1; //샘플에서 사용하는 border의 두께

        // 위에서 선언한 값들을 실제 element에 넣는다.
        element_layer.style.width = width + 'px';
        element_layer.style.height = height + 'px';
        element_layer.style.border = borderWidth + 'px solid';
        // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
        element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
        element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
    }
</script>