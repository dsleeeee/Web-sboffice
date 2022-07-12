package kr.co.solbipos.stock.product.product.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.product.product.service.ProductService;
import kr.co.solbipos.stock.product.product.service.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : ProductController.java
 * @Description : 재고관리 > 생산관리 > 생산등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.06.21  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.06.21
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/product/product")
public class ProductController {
    private final SessionService sessionService;
    private final ProductService productService;

    @Autowired
    public ProductController(SessionService sessionService, ProductService productService) {
       this.sessionService = sessionService;
       this.productService = productService;
    }

    /**
     * 생산관리 - 생산등록 화면 이동
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2022.06.21
     */
    @RequestMapping(value = "/product/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        // POS에서 해당 WEB 화면 재접속한 경우(이전 접속 session 그대로 존재), 'posLoginReconnect'값울 판단하여 view화면 처리
        if(request.getParameter("posLoginReconnect") != null && request.getParameter("posLoginReconnect").length() > 0){
            model.addAttribute("posLoginReconnect", request.getParameter("posLoginReconnect"));
        }

        return "stock/product/product/product";
    }

    /**
     * 생산관리 - 생산등록 리스트 조회
     * @param request
     * @param response
     * @param model
     * @param productVO
     * @return
     * @author  이다솜
     * @since   2022.06.21
     */
    @RequestMapping(value = "/product/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStProductList(HttpServletRequest request, HttpServletResponse response,
                             Model model, ProductVO productVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = productService.getStProductList(productVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, productVO);
    }

    /**
     * 생산관리 - 생산등록 삭제
     * @param request
     * @param response
     * @param model
     * @param productVOs
     * @return
     * @author  이다솜
     * @since   2022.06.21
     */
    @RequestMapping(value = "/product/delete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteProduct(HttpServletRequest request, HttpServletResponse response,
                            Model model, @RequestBody ProductVO[] productVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = productService.deleteProduct(productVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 생산관리 - 생산등록 상품 리스트 조회
     * @param request
     * @param response
     * @param model
     * @param productVO
     * @return
     * @author  이다솜
     * @since   2022.06.21
     */
    @RequestMapping(value = "/productRegist/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProductRegistList(HttpServletRequest request, HttpServletResponse response,
                                    Model model, ProductVO productVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = productService.getProductRegistList(productVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, productVO);
    }

    /**
     * 생산관리 - 생산등록 제목 변경(header 정보)
     * @param request
     * @param response
     * @param model
     * @param productVO
     * @return
     * @author  이다솜
     * @since   2022.06.21
     */
    @RequestMapping(value = "/productRegist/updateProductHdTitle.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updateProductHdTitle(HttpServletRequest request, HttpServletResponse response,
                                Model model, @RequestBody ProductVO productVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = productService.updateProductHdTitle(productVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 생산관리 - 생산등록 상품 저장
     * @param request
     * @param response
     * @param model
     * @param productVOs
     * @return
     * @author  이다솜
     * @since   2022.06.21
     */
    @RequestMapping(value = "/productRegist/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveProductRegist(HttpServletRequest request, HttpServletResponse response,
                                Model model, @RequestBody ProductVO[] productVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = productService.saveProductRegist(productVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 생산관리 - 생산등록 상세 조회
     * @param request
     * @param response
     * @param model
     * @param productVO
     * @return
     * @author  이다솜
     * @since   2022.06.27
     */
    @RequestMapping(value = "/productDtl/info.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProductDtl(HttpServletRequest request, HttpServletResponse response,
                             Model model, ProductVO productVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = productService.getProductDtl(productVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 생산관리 - 생산등록 상세 리스트 조회
     * @param request
     * @param response
     * @param model
     * @param productVO
     * @return
     * @author  이다솜
     * @since   2022.06.27
     */
    @RequestMapping(value = "/productDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProductDtlList(HttpServletRequest request, HttpServletResponse response,
                             Model model, ProductVO productVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = productService.getProductDtlList(productVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, productVO);
    }

    /**
     * 생산관리 - 생산등록 상세 상품 저장
     * @param request
     * @param response
     * @param model
     * @param productVOs
     * @return
     * @author  이다솜
     * @since   2022.06.27
     */
    @RequestMapping(value = "/productDtl/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveProductDtl(HttpServletRequest request, HttpServletResponse response,
                                Model model, @RequestBody ProductVO[] productVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = productService.saveProductDtl(productVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 생산관리 - 생산등록 리더기자료 텍스트 업로드시, 현재 세션ID 와 동일한 데이터 모두 삭제
     * @param request
     * @param response
     * @param model
     * @param productVO
     * @return
     * @author  이다솜
     * @since   2022.07.01
     */
    @RequestMapping(value = "/productRegist/deleteUploadProduct.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteUploadProduct(HttpServletRequest request, HttpServletResponse response,
                                    Model model, ProductVO productVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = productService.deleteUploadProduct(productVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 생산관리 - 생산등록 리더기자료 텍스트 업로드
     * @param request
     * @param response
     * @param model
     * @param ProductVOs
     * @return
     * @author  이다솜
     * @since   2022.07.01
     */
    @RequestMapping(value = "/productRegist/saveUploadProduct.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveUploadProduct(HttpServletRequest request, HttpServletResponse response,
                                  Model model, @RequestBody ProductVO[] ProductVOs) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = productService.saveUploadProduct(ProductVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 생산관리 - 생산등록 업로드 실패내역 조회
     * @param request
     * @param response
     * @param model
     * @param productVO
     * @return
     * @author  이다솜
     * @since   2022.07.05
     */
    @RequestMapping(value = "/productRegist/getUploadErrInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getUploadErrInfoList(HttpServletRequest request, HttpServletResponse response,
                             Model model, ProductVO productVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = productService.getUploadErrInfoList(productVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, productVO);
    }

    /**
     * 생산관리 - 생산등록 상품찾기
     * @param request
     * @param response
     * @param model
     * @param productVO
     * @return
     * @author  이다솜
     * @since   2022.07.06
     */
    @RequestMapping(value = "/productRegist/getProdInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdInfo(HttpServletRequest request, HttpServletResponse response,
                                Model model, ProductVO productVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = productService.getProdInfo(productVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
