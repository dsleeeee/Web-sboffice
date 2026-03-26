<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


        <!--right-->
        <div style="position:relative; width:100%;">
            <div style="position: absolute;top:50px;left:50%;transform:translateX(-50%);width:100%;text-align:center;z-index:999">
                <div>
                    <c:choose>
                        <c:when test="${sessionInfo.orgnFg == 'HQ'}">
                            <a href="/mobile/auth/login.sb">
                                <img src="/resource/solbipos/css/img/btn_home_hq.png" style="width:40px;height:35px;">
                            </a>
                        </c:when>
                        <c:when test="${sessionInfo.orgnFg == 'STORE'}">
                            <a href="/mobile/auth/login.sb">
                                <img src="/resource/solbipos/css/img/btn_home_store.png" style="width:40px;height:35px;">
                            </a>
                        </c:when>
                        <c:when test="${sessionInfo.orgnFg == 'MASTER'}">
                            <a href="/mobile/auth/login.sb">
                                <img src="/resource/solbipos/css/img/btn_home_master.png" style="width:40px;height:35px;">
                            </a>
                        </c:when>
                        <c:when test="${sessionInfo.orgnFg == 'AGENCY'}">
                            <a href="/mobile/auth/login.sb">
                                <img src="/resource/solbipos/css/img/btn_home_agency.png" style="width:40px;height:35px;">
                            </a>
                        </c:when>
                    </c:choose>
                    <- 아이콘을 클릭하여 화면을 갱신하여 주십시오.
                </div>
            </div>
        </div>
        <div class="contents">
            <div class="elseCon">
                <p class="lgTxt">
                    로그인 세션이 만료되었거나,<br /> 잘못된 주소를 입력하셨거나,<br /> 시스템 오류가 발생하여 페이지를 불러올 수 없습니다.
                </p>
                <p class="smTxt mt20">
                  방문하시려는 페이지의 주소가 잘못 입력되었거나,<br />
                  페이지의 주소가 변경 혹은 삭제되어 요청하신 페이지를 찾을 수 없습니다.<br />
                  입력하신 주소가 정확한지 다시 한번 확인해 주시기 바랍니다.
                </p>
            </div>
        </div>
        <!--//right-->