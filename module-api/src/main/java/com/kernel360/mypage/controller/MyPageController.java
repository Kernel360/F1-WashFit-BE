package com.kernel360.mypage.controller;

import com.kernel360.member.dto.MemberDto;
import com.kernel360.member.service.MemberService;
import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final MemberService memberService;
    private final ProductService productService;
    @GetMapping("/main")
    ResponseEntity<Model> main(Model model) {
        List<ProductDto> productDtoList = productService.getProductListOrderByViewCount();
        String bannerImageUrl = "http://localhost:8080/bannersample.png";
        String suggestImageUrl = "http://localhost:8080/suggestsample.png";

        model.addAllAttributes(Map.of("Banner" , bannerImageUrl,
                                    "Suggest", suggestImageUrl,
                                    "Product", productDtoList));

        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @GetMapping("/member")
    ResponseEntity<String> myInfo() {
        return ResponseEntity.status(HttpStatus.OK).body("mypage 내정보 page입니다.");
    }

    @GetMapping("/car")
    ResponseEntity<String> myCar() {
        return ResponseEntity.status(HttpStatus.OK).body("mypage 차량정보 page입니다.");
    }

    @DeleteMapping("/member")
    ResponseEntity<String> memberDelete(@RequestBody MemberDto memberDto) {
        return ResponseEntity.status(HttpStatus.OK).body(memberDto.id() + " 회원이 탈퇴되었습니다.");
    }

    @PostMapping("/member")
    ResponseEntity<String> memberInfoAdd(@RequestBody MemberDto memberDto) {
        return ResponseEntity.status(HttpStatus.OK).body(memberDto.id() + "회원의 회원정보가 추가되었습니다.");
    }

    @PatchMapping("/member")
    ResponseEntity<String> changePassword(@RequestBody MemberDto memberDto) {
        return ResponseEntity.status(HttpStatus.OK).body(memberDto.id() + "회원의 비밀번호가 변경 되었습니다.");
    }


}
