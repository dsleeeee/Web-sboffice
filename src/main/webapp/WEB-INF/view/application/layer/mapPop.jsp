<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<wj-popup id="mapPopLayer" control="mapPopLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:800px;">
    <div class="wj-dialog wj-dialog-columns title">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
          <label id="lblTitle"></label>
          <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body" ng-controller="mapPopCtrl">
            <%-- 지도조회 DIV --%>
            <div id="map" style="width:100%;height:400px;display: none;"></div>
            <%-- 좌표클릭 DIV --%>
            <div class="map_wrap" id="map_wrap">
                <div id="mapCoordinate" style="width:100%;height:100%;position:relative;overflow:hidden;display: none;"></div>
                <div class="hAddr">
                    <span id="centerAddr"></span>
                </div>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/application/layer/mapPop.js?ver=20220928.02" charset="utf-8"></script>

<%-- KAKAO Maps API --%>
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=00a01cbae68d391a0f4b72950f33ada1&libraries=services"></script>

<script>

    // 지도조회
    function searchMap(markerNm, addr, latitude, longitude) {

        // 지도조회 DIV SHOW
        $("#map").css("display", "block");
        $("#map_wrap").css("display", "none");
        $("#mapCoordinate").css("display", "none");

        var mapContainer = document.getElementById('map'), // 지도를 표시할 div
            mapOption = {
                center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
                level: 3 // 지도의 확대 레벨
            };

        // 지도를 생성합니다
        var map = new kakao.maps.Map(mapContainer, mapOption);

        // 좌표가 있는경우, 좌표로 지도조회
        if(latitude !== "" && longitude != "" &&
            latitude !== null && longitude != null &&
            latitude !== undefined && longitude != undefined) {

            var coords = new kakao.maps.LatLng(latitude, longitude);

            // 결과값으로 받은 위치를 마커로 표시합니다
            var marker = new kakao.maps.Marker({
                map: map,
                position: coords
            });

            // 인포윈도우로 장소에 대한 설명을 표시합니다
            var infowindow = new kakao.maps.InfoWindow({
                content: '<div style="width:150px;text-align:center;padding:6px 0;font-size:small;">' + markerNm + '</div>'
            });
            infowindow.open(map, marker);

            // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
            map.setCenter(coords);

        }else{ // 좌표가 없는경우, 주소로 지도조회

            // 주소-좌표 변환 객체를 생성합니다
            var geocoder = new kakao.maps.services.Geocoder();

            // 주소로 좌표를 검색합니다
            geocoder.addressSearch(addr, function (result, status) {

                // 정상적으로 검색이 완료됐으면
                if (status === kakao.maps.services.Status.OK) {

                    var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

                    // 결과값으로 받은 위치를 마커로 표시합니다
                    var marker = new kakao.maps.Marker({
                        map: map,
                        position: coords
                    });

                    // 인포윈도우로 장소에 대한 설명을 표시합니다
                    var infowindow = new kakao.maps.InfoWindow({
                        content: '<div style="width:150px;text-align:center;padding:6px 0;font-size:small;">' + markerNm + '</div>'
                    });
                    infowindow.open(map, marker);

                    // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                    map.setCenter(coords);
                }
            });
        }
    }






    // 좌표클릭으로 주소 및 위도,경도 조회
    function clickMap(markerNm, latitude, longitude){

        // 좌표클릭 DIV SHOW
        $("#map").css("display", "none");
        $("#map_wrap").css("display", "block");
        $("#mapCoordinate").css("display", "block");

        var mapContainer = document.getElementById('mapCoordinate'), // 지도를 표시할 div
            mapOption = {
                center: new kakao.maps.LatLng(latitude, longitude), // 지도의 중심좌표
                level: 1 // 지도의 확대 레벨
            };

        // 지도를 생성합니다
        var map = new kakao.maps.Map(mapContainer, mapOption);

        // 주소-좌표 변환 객체를 생성합니다
        var geocoder = new kakao.maps.services.Geocoder();

        var marker = new kakao.maps.Marker(), // 클릭한 위치를 표시할 마커입니다
            infowindow = new kakao.maps.InfoWindow({zindex:1}); // 클릭한 위치에 대한 주소를 표시할 인포윈도우입니다

        // 현재 지도 중심좌표로 주소를 검색해서 지도 좌측 상단에 표시합니다
        searchAddrFromCoords(map.getCenter(), displayCenterInfo);

        // 지도를 클릭했을 때 클릭 위치 좌표에 대한 주소정보를 표시하도록 이벤트를 등록합니다
        kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
            searchDetailAddrFromCoords(mouseEvent.latLng, function(result, status) {
                if (status === kakao.maps.services.Status.OK) {
                    var detailAddr = !!result[0].road_address ? '<div>도로명주소 : ' + result[0].road_address.address_name + '</div>' : '';
                    detailAddr += '<div>지번 주소 : ' + result[0].address.address_name + '</div>';
                    //var detailAddr = '<div>좌표 : ' + mouseEvent.latLng.getLat() + ', ' +  mouseEvent.latLng.getLng() + '</div>';

                    var content = '<div class="bAddr" style="font-size:small;">' +
                                    //'<span class="title">법정동 주소정보</span>' +
                                    detailAddr +
                                '</div>';

                    // 마커를 클릭한 위치에 표시합니다
                    marker.setPosition(mouseEvent.latLng);
                    marker.setMap(map);

                    // 인포윈도우에 클릭한 위치에 대한 법정동 상세 주소정보를 표시합니다
                    infowindow.setContent(content);
                    infowindow.open(map, marker);
                }
            });
        });
    }

    // 중심 좌표나 확대 수준이 변경됐을 때 지도 중심 좌표에 대한 주소 정보를 표시하도록 이벤트를 등록합니다
    kakao.maps.event.addListener(map, 'idle', function() {
        searchAddrFromCoords(map.getCenter(), displayCenterInfo);
    });

    function searchAddrFromCoords(coords, callback) {
        // 좌표로 행정동 주소 정보를 요청합니다
        geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);
    }

    function searchDetailAddrFromCoords(coords, callback) {
        // 좌표로 법정동 상세 주소 정보를 요청합니다
        geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
    }

    // 지도 좌측상단에 지도 중심좌표에 대한 주소정보를 표출하는 함수입니다
    function displayCenterInfo(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            var infoDiv = document.getElementById('centerAddr');

            for(var i = 0; i < result.length; i++) {
                // 행정동의 region_type 값은 'H' 이므로
                if (result[i].region_type === 'H') {
                    infoDiv.innerHTML = result[i].address_name;
                    break;
                }
            }
        }
    }

</script>

<style>
    /* 좌표클릭 DIV 관련 CSS */
    .map_wrap {position:relative;width:100%;height:350px;}
    .title {font-weight:bold;display:block;}
    .hAddr {position:absolute;left:10px;top:10px;border-radius: 2px;background:#fff;background:rgba(255,255,255,0.8);z-index:1;padding:5px;}
    #centerAddr {display:block;margin-top:2px;font-weight: normal;}
    .bAddr {padding:5px;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
</style>