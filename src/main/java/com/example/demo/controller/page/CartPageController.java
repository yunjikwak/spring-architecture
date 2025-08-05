package com.example.demo.controller.page;

import com.example.demo.controller.page.dto.AddProductToCartRequestDto;
import com.example.demo.service.usecases.IManageCartUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart") // 공통 url - cart 관련 HTTP 요청 처리
@RequiredArgsConstructor // 필요한 필드만 생성자 통해 설정
public class CartPageController { // Primary Adaptor
    // Primary Adaptor는 외부 요청을 받아 UseCase에 전달하고, 결과를 View로 전달하는 역할
    // UseCase : input port + service 구현클래스 ??
    private final IManageCartUseCase cartService; //input port와 연결
    // final : cartService 필드가 한 번만 할당, 의존성 주입을 통한 초기화
        // @RequiredArgsConstructor가 cartService를 생성자 매개변수로 포함X
        // 결과적으로 cartService는 null로 남아있게 됨
    private static final Long MOCK_CUSTOMER_ID = 1L;

    @GetMapping // /cart
    public String displayCart(Model model) {
        cartService.getCart(MOCK_CUSTOMER_ID)
                .ifPresent(cart -> model.addAttribute("cart", cart));
        // ifPresent : Optional 객체가 비어있지 않으면(장바구니가 존재하면) cart 객체를 모델에 추가(모델에 cart 속성 추가) -> 뷰에서 장바구니 정보 사용
        return "cart/list"; // cart/list.html 뷰로 이동
    }

    @PostMapping("/add") // /cart/add
    public String addProductToCart(@ModelAttribute AddProductToCartRequestDto request) { // AddProductToCartRequestDto request <-- HTML 폼에서 전달된 데이터를 DTO로 변환
        // @ModelAttribute : HTTP 요청 파라미터를 DTO로 변환
        cartService.addProductToCart(
                MOCK_CUSTOMER_ID,
                request.getProductId(),
                request.getOptionId(),
                request.getQuantity()
        );
        return "redirect:/cart"; // /cart로 리다이렉트
    }
}
