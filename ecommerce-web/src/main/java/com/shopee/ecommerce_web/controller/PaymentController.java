package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.dto.request.ApiResponse;
import com.shopee.ecommerce_web.dto.request.PaymentCreationRequest;
import com.shopee.ecommerce_web.dto.request.PaymentUpdateRequest;
import com.shopee.ecommerce_web.dto.response.PaymentDTO;
import com.shopee.ecommerce_web.dto.response.PaymentResponse;
import com.shopee.ecommerce_web.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {

    PaymentService paymentService;

    // Tạo mới một Payment
    @PostMapping
    public ApiResponse<PaymentResponse> createPayment(@RequestBody PaymentCreationRequest request) {
        PaymentResponse paymentResponse = paymentService.createPayment(request);
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentResponse)
                .build();
    }

    // Lấy tất cả các Payment
    @GetMapping
    public ApiResponse<List<PaymentResponse>> getPayments() {
        List<PaymentResponse> payments = paymentService.getPayments();
        return ApiResponse.<List<PaymentResponse>>builder()
                .result(payments)
                .build();
    }

    // Lấy Payment theo ID
    @GetMapping("/{paymentId}")
    public ApiResponse<PaymentResponse> getPayment(@PathVariable Long paymentId) {
        PaymentResponse paymentResponse = paymentService.getPayment(paymentId);
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentResponse)
                .build();
    }

    // Cập nhật Payment
    @PutMapping("/{paymentId}")
    public ApiResponse<PaymentResponse> updatePayment(@PathVariable Long paymentId, @RequestBody PaymentUpdateRequest request) {
        PaymentResponse paymentResponse = paymentService.updatePayment(paymentId, request);
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentResponse)
                .build();
    }

    // Xóa Payment
    @DeleteMapping("/{paymentId}")
    public ApiResponse<String> deletePayment(@PathVariable Long paymentId) {
        paymentService.deletePayment(paymentId);
        return ApiResponse.<String>builder()
                .result("Payment has been deleted")
                .build();
    }
    @GetMapping("/vn-pay")
    public ApiResponse<PaymentDTO.VNPayResponse> pay(HttpServletRequest request) {
        PaymentDTO.VNPayResponse response = paymentService.createVnPayPayment(request);
        return new ApiResponse<>(HttpStatus.OK.value(), "Success", response);
    }

    @GetMapping("/vn-pay-callback")
    public ApiResponse<PaymentDTO.VNPayResponse> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");

        // Khởi tạo đối tượng VNPayResponse thông qua Builder
        PaymentDTO.VNPayResponse response = PaymentDTO.VNPayResponse.builder()
                .code("00")
                .message("Success")
                .paymentUrl("http://yourdomain.com/payment/success")
                .build();

        if (status.equals("00")) {
            return new ApiResponse<>(HttpStatus.OK.value(), "Success", response);
        } else {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Failed", null);
        }
    }


}
