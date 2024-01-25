package com.kernel360.mypage.controller;

import com.kernel360.commoncode.dto.CommonCodeDto;
import com.kernel360.commoncode.service.CommonCodeService;
import com.kernel360.member.code.MemberBusinessCode;
import com.kernel360.member.dto.MemberDto;
import com.kernel360.member.service.MemberService;
import com.kernel360.mypage.enumset.*;
import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.service.ProductService;
import com.kernel360.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
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
    private final CommonCodeService commonCodeService;

    @GetMapping("/main")
    ResponseEntity<Model> main(Model model) {
        List<ProductDto> productDtoList = productService.getProductListOrderByViewCount();
        String bannerImageUrl = "http://localhost:8080/bannersample.png";
        String suggestImageUrl = "http://localhost:8080/suggestsample.png";

        model.addAllAttributes(Map.of("Banner", bannerImageUrl,
                "Suggest", suggestImageUrl,
                "Product", productDtoList));

        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @GetMapping("/member")
    <T> ResponseEntity<ApiResponse<MemberDto>> myInfo(RequestEntity<T> request) {
        MemberDto dto = memberService.findMemberByToken(request);

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_FIND_REQUEST_MEMBER, dto);
    }

    @GetMapping("/car")
    <T> ResponseEntity<ApiResponse<Map<String, Object>>> myCar(RequestEntity<T> request) {
//        CarInfo carInfo = memberService.findCarInfoByToken(request);
//        "car_info", carInfo,
        List<CommonCodeDto> segment = commonCodeService.getCodes("segment");


        Map<String, Object> map = Map.of(
                "segment_options", CarSegment.getAllSegments(),
                "carType_options", CarType.getAllTypes(),
                "color_options", CarColor.getAllColors(),
                "driving_options", CarDriving.getAllDrivings(),
                "parking_options", CarParking.getAllParkings()
        );

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_FIND_CAR_INFO_IN_MEMBER, map);
    }


    @DeleteMapping("/member")
    <T> ResponseEntity<ApiResponse<T>> memberDelete(@RequestBody MemberDto memberDto) {
        memberService.deleteMember(memberDto.id());

        return ApiResponse.toResponseEntity(MemberBusinessCode.SUCCESS_REQUEST_DELETE_MEMBER);
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
