<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup id="wjContentPop1Layer" control="wjContentPop1Layer" show-trigger="Click" hide-trigger="Click" style="display:none;width:860px;height:610px;" fade-in="false" fade-out="false">
    <div ng-controller="contentPop1Ctrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            개인정보취급방침
            <a href="#" id="btn_close" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- 내용 --%>
        <div class="subCon">
            <%-- 개인정보취급방침 내용1 --%>
            <div style="width: 820px; height: 520px; overflow-x: auto; overflow-y: auto; border: 1px solid #e8e8e8;" class="tl s14 lh15 pd10">
                <p>
                    링크(주)('www.lynk.co.kr'이하'링크(주)')는 개인정보보호법에 따라 이용자의 개인정보 보호 및 권익을 보호하고<br>
                    개인정보와 관련한 이용자의 고충을 원활하게 처리할 수 있도록 다음과 같은 처리방침을 두고 있습니다. 링크(주)는 회사는<br>
                    개인정보처리방침을 개정하는 경우 웹사이트 공지사항(또는 개별공지)을 통하여 공지할 것입니다.<br>
                    본방침은 2016년 3월 17일부터 시행됩니다.<br>
                    <br>
                    <br>
                    <font color="orange">1. 개인정보의 처리 목적</font><br>
                    &nbsp;&nbsp;&nbsp;'링크(주)'는 개인정보를 다음의 목적을 위해 처리합니다. 처리한 개인정보는 다음의 목적이외의 용도로는 사용되지 않으<br>
                    며 이용 목적이 변경될 시에는 사전동의를 구할 예정입니다.<br>
                    &nbsp;&nbsp;&nbsp;(1) 홈페이지 회원가입 및 관리<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;회원 가입의사 확인, 회원제 서비스 제공에 따른 본인 식별, 인증, 회원자격 유지, 관리, 제한적 본인확인제 시행에 따른 본<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;인확인, 서비스 부정이용 방지, 만14세 미만 아동 개인정보 수집 시 법정대리인 동의 여부 확인, 각종 고지, 통지, 고충처<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;리, 분쟁 조정을 위한 기록 보존 등을 목적으로 개인정보를 처리합니다.<br>
                    &nbsp;&nbsp;&nbsp;(2) 민원사무 처리<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;민원인의 신원 확인, 민원사항 확인, 사실조사를 위한 연락·통지, 처리결과 통보 등을 목적으로 개인정보를 처리합니다.<br>
                    &nbsp;&nbsp;&nbsp;(3) 재화 또는 서비스 제공<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;물품배송, 서비스 제공, 청구서 발송, 콘텐츠 제공, 맞춤 서비스 제공, 본인인증, 요금결제·정산, 채권추심 등을 목적으로 개<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;인정보를 처리합니다.<br>
                    &nbsp;&nbsp;&nbsp;(4) 마케팅 및 광고에의 활용<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;신규 서비스(제품) 개발 및 맞춤 서비스 제공, 이벤트 및 광고성 정보 제공 및 참여기회 제공 , 인구통계학적 특성에 따른<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;서비스 제공 및 광고 게재 , 서비스의 유효성 확인, 접속빈도 파악 또는 회원의 서비스 이용에 대한 통계 등을 목적으로 개<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;인정보를 처리합니다.<br>
                    <br>
                    <br>
                    <font color="orange">2. 개인정보 파일 현황</font><br>
                    &nbsp;&nbsp;&nbsp;● 개인정보 파일명 : 링크(주) 개인정보 동의서<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 개인정보 항목 : 이메일, 휴대전화번호, 자택주소, 자택전화번호, 비밀번호 질문과 답, 비밀번호, 로그인ID, 성별, 생년월<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;일, 이름, 회사전화번호, 직책, 부서, 회사명, 직업, 주민등록번호, 신용카드정보, 은행계좌정보, 서비스 이용 기록, 접속<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;로그, 쿠키, 접속 IP 정보, 결제기록, 법정대리인 이름, 법정대리인 자택 전화번호, 법정대리인 자택 주소, 법정대리인 휴대<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;전화번호<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 수집방법 : 홈페이지, 서면양식, 전화/팩스, 경품행사, 배송요청, 제휴사로부터 제공 받음, 생성정보 수집 툴을 통한 수집<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 보유근거 : ASP서비스 이용계약<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 보유기간 : 하기와 같음<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 관련법령 :<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 신용정보의 수집/처리 및 이용 등에 관한 기록 : 3년<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 소비자의 불만 또는 분쟁처리에 관한 기록 : 3년<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 대금결제 및 재화 등의 공급에 관한 기록 : 5년<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 계약 또는 청약철회 등에 관한 기록 : 5년<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 표시/광고에 관한 기록 : 6개월<br>
                    <br>
                    <br>
                    <font color="orange">3. 개인정보의 처리 및 보유 기간</font><br>
                    &nbsp;&nbsp;&nbsp;(1) '링크(주)'는법령에 따른 개인정보 보유·이용기간 또는 정보주체로부터 개인정보를 수집시에 동의 받은 개인정보 보유,<br>
                    &nbsp;&nbsp;&nbsp;이용기간 내에서 개인정보를 처리,보유합니다.<br>
                    &nbsp;&nbsp;&nbsp;(2) 각각의 개인정보 처리 및 보유 기간은 다음과 같습니다.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;● 홈페이지 회원가입 및 관리<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[홈페이지 회원가입 및 관리]와 관련한 개인정보는 수집.이용에 관한 동의일로부터까지 위 이용목적을 위하여 보유.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;이용됩니다.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 보유근거 : 홈페이지 회원가입 및 관리<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 관련법령 :<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 신용정보의 수집/처리 및 이용 등에 관한 기록 : 3년<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 소비자의 불만 또는 분쟁처리에 관한 기록 : 3년<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 대금결제 및 재화 등의 공급에 관한 기록 : 5년<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 계약 또는 청약철회 등에 관한 기록 : 5년<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 표시/광고에 관한 기록 : 6개월<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 예외사유 : 채권·채무관계 잔존시에는 해당 채권,채무관계 정산시까지<br>
                    <br>
                    <br>
                    <font color="orange">4. 개인정보의 제3자 제공에 관한 사항</font><br>
                    &nbsp;&nbsp;&nbsp;(1) '링크(주)'는 정보주체의 동의, 법률의 특별한 규정 등 개인정보 보호법 제17조 및 제18조에 해당하는 경우에만 개인<br>
                    &nbsp;&nbsp;&nbsp;정보를 제3자에게 제공합니다.<br>
                    &nbsp;&nbsp;&nbsp;(2) '링크(주)'는 다음과 같이 개인정보를 제3자에게 제공하고 있습니다.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;● 링크(주) 고객센터, 위탁관리 계약을 체결한 업체<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 개인정보를 제공받는 자 : 링크(주) 고객센터, 위탁관리 계약을 체결한 업체<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 제공받는 자의 개인정보 이용목적 : 사업자정보(대표명, 사업자등록번호, 소재지), 회사명, 회사전화번호, 직책, 이름,<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;생년월일, 성별, 휴대전화번호, 이메일, 자택주소, 자택전화번호, 주민등록번호, 신용카드정보, 은행계좌정보 등 결제정보<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 제공받는 자의 보유.이용기간: 서비스 해지 후 5년<br>
                    <br>
                    <br>
                    <font color="orange">5. 개인정보처리 위탁</font><br>
                    &nbsp;&nbsp;&nbsp;(1) '링크(주)'는원활한 개인정보 업무처리를 위하여 다음과 같이 개인정보 처리업무를 위탁하고 있습니다.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;● 이용계약 및 그 부대업무의 대행, 수수료정산<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 위탁받는 자 (수탁자) : 링크(주) 고객센터, 위탁관리 계약을 체결업체<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 위탁하는 업무의 내용 : 구매 및 요금 결제, 물품배송 또는 청구서 등 발송, 본인인증(금융거래, 금융서비스), 요금추심,<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;회원제 서비스 이용에 따른 본인확인, 불만처리 등 민원처리, 고지사항 전달, 신규 서비스(제품) 개발 및 맞춤 서비스 제공,<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;이벤트 및 광고성 정보 제공 및 참여기회 제공, 영상정보처리기기 운영<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 위탁기간 : 서비스 해지 후 5년<br>
                    &nbsp;&nbsp;&nbsp;(2) '링크(주)'는 위탁계약 체결시 개인정보 보호법 제25조에 따라 위탁업무 수행목적 외 개인정보 처리금지, 기술적․관리<br>
                    &nbsp;&nbsp;&nbsp;적 보호조치, 재위탁 제한, 수탁자에 대한 관리․감독, 손해배상 등 책임에 관한 사항을 계약서 등 문서에 명시하고, 수탁자가 개<br>
                    &nbsp;&nbsp;&nbsp;인정보를 안전하게 처리하는지를 감독하고 있습니다.<br>
                    &nbsp;&nbsp;&nbsp;(3) 위탁업무의 내용이나 수탁자가 변경될 경우에는 지체없이 본 개인정보 처리방침을 통하여 공개하도록 하겠습니다.<br>
                    <br>
                    <br>
                    <font color="orange">6. 정보주체의 권리,의무 및 그 행사방법 이용자는 개인정보주체로서 다음과 같은 권리를 행사할 수 있습니다.</font><br>
                    &nbsp;&nbsp;&nbsp;(1) 정보주체는 '링크(주)'에 대해 언제든지 다음 각 호의 개인정보 보호 관련 권리를 행사할 수 있습니다.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;① 개인정보 열람요구<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;② 오류 등이 있을 경우 정정 요구<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;③ 삭제요구<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;④ 처리정지 요구<br>
                    &nbsp;&nbsp;&nbsp;(2) 제1항에 따른 권리 행사는  '링크(주)'에 대해 개인정보 보호법 시행규칙 별지 제8호 서식에 따라 서면, 전자우편, 모사<br>
                    &nbsp;&nbsp;&nbsp;전송(FAX) 등을 통하여 하실 수 있으며  '링크(주)' 이에 대해 지체 없이 조치하겠습니다.<br>
                    &nbsp;&nbsp;&nbsp;(3) 정보주체가 개인정보의 오류 등에 대한 정정 또는 삭제를 요구한 경우에는 '링크(주)'는 정정 또는 삭제를 완료할 때까<br>
                    &nbsp;&nbsp;&nbsp;지 당해 개인정보를 이용하거나 제공하지 않습니다.<br>
                    &nbsp;&nbsp;&nbsp;(4) 제1항에 따른 권리 행사는 정보주체의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수 있습니다. 이 경우 개인<br>
                    &nbsp;&nbsp;&nbsp;정보 보호법 시행규칙 별지 제11호 서식에 따른 위임장을 제출하셔야 합니다.<br>
                    <br>
                    <br>
                    <font color="orange">7. 처리하는 개인정보의 항목 작성</font><br>
                    &nbsp;&nbsp;&nbsp;링크(주)'는 다음의 개인정보 항목을 처리하고 있습니다.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;● 홈페이지 회원가입 및 관리<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 필수항목 : 사업자정보(대표명, 사업자등록번호, 소재지), 회사명, 회사전화번호, 직책, 이름, 생년월일, 성별, 휴대전화번<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;호, 이메일, 자택주소, 자택전화번호, 주민등록번호, 비밀번호 질문과 답, 비밀번호, 로그인ID<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 선택항목 : 부서, 직업, 법정대리인 이름, 법정대리인 자택 전화번호, 법정대리인 자택 주소, 법정대리인 휴대전화번호,<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;신용카드정보, 은행계좌정보 등 결제정보<br>
                    <br>
                    <br>
                    <font color="orange">8. 개인정보의 파기</font><br>
                    &nbsp;&nbsp;&nbsp;링크(주)'는 원칙적으로 개인정보 처리목적이 달성된 경우에는 지체없이 해당 개인정보를 파기합니다. 파기의 절차, 기한<br>
                    &nbsp;&nbsp;&nbsp;및 방법은 다음과 같습니다.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 파기절차 :<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;이용자가 입력한 정보는 목적 달성 후 별도의 DB에 옮겨져(종이의 경우 별도의 서류) 내부 방침 및 기타 관련 법령에<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;따라 일정기간 저장된 후 혹은 즉시 파기됩니다. 이 때, DB로 옮겨진 개인정보는 법률에 의한 경우가 아니고서는 다른<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;목적으로 이용되지 않습니다.-파기기한이용자의 개인정보는 개인정보의 보유기간이 경과된 경우에는 보유기간의<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;종료일로부터 5일 이내에, 개인정보의 처리 목적 달성, 해당 서비스의 폐지, 사업의 종료 등 그 개인정보가 불필요하<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;게 되었을 때에는 개인정보의 처리가 불필요한 것으로 인정되는 날로부터 5일 이내에 그 개인정보를 파기합니다.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 파기방법 :<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;① 전자적 파일 형태의 정보 : 기록을 재생할 수 없는 기술적 방법을 사용합니다.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;② 종이에 출력된 개인정보 : 분쇄기로 분쇄하거나 소각을 통하여 파기합니다.<br>
                    <br>
                    <br>
                    <font color="orange">9. 개인정보의 안전성 확보 조치</font><br>
                    &nbsp;&nbsp;&nbsp;링크(주)'는개인정보보호법 제29조에 따라 다음과 같이 안전성 확보에 필요한 기술적/관리적 및 물리적 조치를 하고 있습<br>
                    &nbsp;&nbsp;&nbsp;니다.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(1) 정기적인 자체 감사 실시<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;개인정보 취급 관련 안정성 확보를 위해 정기적(분기 1회)으로 자체 감사를 실시하고 있습니다.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(2) 개인정보 취급 직원의 최소화 및 교육<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;개인정보를 취급하는 직원을 지정하고 담당자에 한정시켜 최소화 하여 개인정보를 관리하는 대책을 시행하고 있습니<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;다.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(3) 내부관리계획의 수립 및 시행<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;개인정보의 안전한 처리를 위하여 내부관리계획을 수립하고 시행하고 있습니다.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(4) 해킹 등에 대비한 기술적 대책<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;링크(주)'는 해킹이나 컴퓨터 바이러스 등에 의한 개인정보 유출 및 훼손을 막기 위하여 보안프로그램을 설치하고<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;주기적인 갱신·점검을 하며 외부로부터 접근이 통제된 구역에 시스템을 설치하고 기술적/물리적으로 감시 및 차단하고<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;있습니다.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(5) 개인정보의 암호화<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;이용자의 개인정보는 비밀번호는 암호화 되어 저장 및 관리되고 있어, 본인만이 알 수 있으며 중요한 데이터는 파일 및<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;전송 데이터를 암호화 하거나 파일 잠금 기능을 사용하는 등의 별도 보안기능을 사용하고 있습니다.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(6) 접속기록의 보관 및 위변조 방지<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;개인정보처리시스템에 접속한 기록을 최소 6개월 이상 보관, 관리하고 있으며, 접속 기록이 위변조 및 도난, 분실되지 않<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;도록 보안기능 사용하고 있습니다.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(7) 개인정보에 대한 접근 제한<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;개인정보를 처리하는 데이터베이스시스템에 대한 접근권한의 부여,변경,말소를 통하여 개인정보에 대한 접근통제를 위<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;하여 필요한 조치를 하고 있으며 침입차단시스템을 이용하여 외부로부터의 무단 접근을 통제하고 있습니다.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(8) 문서보안을 위한 잠금장치 사용<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;개인정보가 포함된 서류, 보조저장매체 등을 잠금장치가 있는 안전한 장소에 보관하고 있습니다.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(9) 비인가자에 대한 출입 통제<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;개인정보를 보관하고 있는 물리적 보관 장소를 별도로 두고 이에 대해 출입통제 절차를 수립, 운영하고 있습니다.<br>
                    <br>
                    <br>
                    <font color="orange">10. 개인정보 보호책임자 작성</font><br>
                    &nbsp;&nbsp;&nbsp;(1) '링크(주)'는 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해<br>
                    &nbsp;&nbsp;&nbsp;구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;▶ 개인정보 보호책임자<br>
                    <table class="tblType01 mt10">
                        <colgroup>
                            <col class="w30" />
                            <col class="w70" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <td class="bl br">
                                성명
                            </td>
                            <td class="br">
                                윤한성
                            </td>
                        </tr>
                        <tr>
                            <td class="bl br">
                                직책
                            </td>
                            <td class="br">
                                실장
                            </td>
                        </tr>
                        <tr>
                            <td class="bl br">
                                직급
                            </td>
                            <td class="br">
                                이사
                            </td>
                        </tr>
                        <tr>
                            <td class="bl br">
                                연락처
                            </td>
                            <td class="br">
                                Tel. 1544-5194   E-mail. yhs@solbipos.co.kr   Fax. 02-868-5194
                            </td>
                        </tr>
                    </table>
                    <br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;▶ 개인정보 보호 담당부서<br>
                    <table class="tblType01 mt10">
                        <colgroup>
                            <col class="w30" />
                            <col class="w70" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <td class="bl br">
                                부서명
                            </td>
                            <td class="br">
                                솔루션운영팀
                            </td>
                        </tr>
                        <tr>
                            <td class="bl br">
                                담당자
                            </td>
                            <td class="br">
                                김영덕
                            </td>
                        </tr>
                        <tr>
                            <td class="bl br">
                                연락처
                            </td>
                            <td class="br">
                                Tel. 02-851-9635  E-mail. ydkim @solbipos.co.kr   Fax. 02-868-5194
                            </td>
                        </tr>
                    </table>
                    <br>
                    &nbsp;&nbsp;&nbsp;※ 개인정보 보호 담당부서로 연결됩니다.<br>
                    <br>
                    &nbsp;&nbsp;&nbsp;(2) 정보주체께서는 '링크(주)'의 서비스(또는 사업)을 이용하시면서 발생한 모든 개인정보 보호 관련 문의, 불만처리, 피해<br>
                    &nbsp;&nbsp;&nbsp;구제 등에 관한 사항을 개인정보 보호책임자 및 담당부서로 문의하실 수 있습니다. '링크(주)'는 정보주체의 문의에 대해<br>
                    &nbsp;&nbsp;&nbsp;지체 없이 답변 및 처리해드릴 것입니다.<br>
                    <br>
                    <br>
                    <font color="orange">11. 개인정보 처리방침 변경</font><br>
                    &nbsp;&nbsp;&nbsp;이 개인정보처리방침은 시행일로부터 적용되며, 법령 및 방침에 따른 변경내용의 추가, 삭제 및 정정이 있는 경우에는 변경사<br>
                    &nbsp;&nbsp;&nbsp;항의 시행 7일 전부터 공지사항을 통하여 고지할 것입니다.<br>
                    <br>
                    <br>
                    <font color="orange">12. 자동수집 장치의 설치, 운용 및 그 거부에 관한 사항</font><br>
                    &nbsp;&nbsp;&nbsp;회사는 이용자 개개인에게 개인화되고 맞춤화된 서비스를 제공하기 위해 이용자의 정보를 저장하고 수시로 불러오는 '쿠키(cookie)'<br>
                    &nbsp;&nbsp;&nbsp;를 사용합니다.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(1) 쿠키의 사용 목적<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;회원과 비회원의 접속 빈도나 방문 시간 등의 분석, 이용자의 취향과 관심분야의 파악 및 자취 추적, 각종 이벤트 참여 정도 및<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;방문 회수 파악 등을 통한 타겟 마케팅 및 개인 맞춤 서비스 제공<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(2) 쿠키 설정 거부 방법<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;이용자는 쿠키 설치에 대해 거부할 수 있습니다. 단, 쿠키 설치를 거부하였을 경우 로그인이 필요한 일부 서비스의 이용이<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;어려울 수 있습니다.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 설정방법<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;① IE 기준 : 웹 브라우저 상단의 도구 > 인터넷 옵션 > 개인정보 > 사이트 차단<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;② 크롬 기준 : 웹 브라우저 상단의 더보기 > 설정 > 하단에서 고급 > '개인정보 및 보안'에서 사이트 설정 ><br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;쿠키 및 사이트 데이터 > 타사 쿠키 차단<br>
                    <br>
                    <br>
                </p>
            </div>
            <%-- //개인정보취급방침 내용1 --%>
        </div>
        <%-- //내용 --%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsSend/contentPop1.js?ver=20241106.01" charset="utf-8"></script>