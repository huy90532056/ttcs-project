package com.shopee.ecommerce_web.service;

import com.shopee.ecommerce_web.configuration.VNPAYConfig;
import com.shopee.ecommerce_web.dto.request.PaymentCreationRequest;
import com.shopee.ecommerce_web.dto.request.PaymentUpdateRequest;
import com.shopee.ecommerce_web.dto.response.PaymentDTO;
import com.shopee.ecommerce_web.dto.response.PaymentResponse;
import com.shopee.ecommerce_web.entity.Order;
import com.shopee.ecommerce_web.entity.Payment;
import com.shopee.ecommerce_web.exception.AppException;
import com.shopee.ecommerce_web.exception.ErrorCode;
import com.shopee.ecommerce_web.repository.OrderRepository;
import com.shopee.ecommerce_web.repository.PaymentRepository;
import com.shopee.ecommerce_web.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final VNPAYConfig vnPayConfig;
    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return PaymentDTO.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }

    // Tạo một payment mới
    public PaymentResponse createPayment(PaymentCreationRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        Payment payment = Payment.builder()
                .order(order)
                .paymentMethod(request.getPaymentMethod())
                .amount(request.getAmount())
                .paymentDate(request.getPaymentDate())
                .build();

        payment = paymentRepository.save(payment);

        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .orderId(payment.getOrder().getOrderId())
                .paymentMethod(payment.getPaymentMethod())
                .amount(payment.getAmount())
                .paymentDate(payment.getPaymentDate())
                .build();
    }

    // Lấy tất cả payments
    public List<PaymentResponse> getPayments() {
        return paymentRepository.findAll().stream()
                .map(payment -> new PaymentResponse(
                        payment.getPaymentId(),
                        payment.getOrder().getOrderId(),
                        payment.getPaymentMethod(),
                        payment.getAmount(),
                        payment.getPaymentDate()))
                .collect(Collectors.toList());
    }

    // Lấy payment theo ID
    public PaymentResponse getPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));

        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getOrder().getOrderId(),
                payment.getPaymentMethod(),
                payment.getAmount(),
                payment.getPaymentDate());
    }

    // Cập nhật payment
    public PaymentResponse updatePayment(Long paymentId, PaymentUpdateRequest request) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));

        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setAmount(request.getAmount());
        payment.setPaymentDate(request.getPaymentDate());

        payment = paymentRepository.save(payment);

        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getOrder().getOrderId(),
                payment.getPaymentMethod(),
                payment.getAmount(),
                payment.getPaymentDate());
    }

    // Xóa payment
    public void deletePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));

        paymentRepository.delete(payment);
    }
}
