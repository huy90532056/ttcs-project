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
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");

        String html;
        if ("00".equals(status)) {
            html = """
            <html>
                <head><title>Thanh toán thành công</title></head>
                <body style="font-family: Arial; text-align: center; padding-top: 50px;">
                    <h2>🎉 Thanh toán thành công!</h2>
                    <p>Click vào nút bên dưới để đóng cửa sổ này.</p>
                    <button onclick="window.close()"
                       style="padding:10px 20px;background-color:#4CAF50;color:white;
                       border:none;border-radius:5px;cursor:pointer;margin-top:20px;">
                       Đóng trang
                    </button>
                </body>
            </html>
        """;
        } else {
            html = """
            <html>
                <head><title>Thanh toán thất bại</title></head>
                <body style="font-family: Arial; text-align: center; padding-top: 50px;">
                    <h2>❌ Thanh toán thất bại!</h2>
                    <p>Click vào nút bên dưới để đóng cửa sổ này.</p>
                    <button onclick="window.close()"
                       style="padding:10px 20px;background-color:#f44336;color:white;
                       border:none;border-radius:5px;cursor:pointer;margin-top:20px;">
                       Đóng trang
                    </button>
                </body>
            </html>
        """;
        }

        return ResponseEntity.ok()
                .header("Content-Type", "text/html; charset=UTF-8")
                .body(html);
    }
}
