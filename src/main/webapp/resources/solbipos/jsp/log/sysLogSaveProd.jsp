<%@ page pageEncoding="UTF-8"%>
<%
    // 'action' 파라미터값 확인
    String action = request.getParameter("action");

    if ("prodSaveButtonClick".equals(action)) {
        // 이 메시지는 웹 서버(예: Tomcat)의 콘솔에 출력됩니다.
        System.out.println(" ================================================ 상품정보 [저장] 버튼 클릭 ================================================");
        // 클라이언트(브라우저)로 응답을 보냅니다.
        out.print("상품정보 [저장] 버튼을 클릭하였습니다.");
    } else if ("prodSaveOkButtonClick".equals(action)) {
        System.out.println(" ============================================= 저장하시겠습니까? [OK] 버튼 클릭 =============================================");
        out.print("저장하시겠습니까? [OK] 버튼을 클릭하였습니다.");
    } else {
        out.print("유효하지 않은 요청입니다.");
    }
%>