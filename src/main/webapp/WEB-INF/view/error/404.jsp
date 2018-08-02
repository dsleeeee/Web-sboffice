<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


        <!--right-->
        <div class="contents">
            <div class="elseCon">
                <p class="lgTxt">죄송합니다.<br />요청하신 페이지를 찾을 수 없습니다.</p>
                <p class="smTxt mt20">
                    방문하시려는 페이지의 주소가 잘못 입력되었거나,<br />
                    페이지의 주소가 변경 혹은 삭제되어 요청하신 페이지를 찾을 수 없습니다.<br />
                    입력하신 주소가 정확한지 다시 한번 확인해 주시기 바랍니다.
                    ${sessionScope.sessionInfo.currentMenu.resrceCd}
                </p>
            </div>
        </div>
        <!--//right-->