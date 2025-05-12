package com.ua07.transactions.service;

import com.ua07.transactions.model.Order;
import com.ua07.transactions.model.OrderLineItem;
import com.ua07.transactions.model.OrderStatus;
import com.ua07.transactions.model.PaymentMethod;
import com.ua07.transactions.model.Transaction;
import com.ua07.transactions.model.TransactionStatus;

import com.ua07.transactions.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class InvoiceService {

    private final TemplateEngine templateEngine;
    private final OrderService orderService;
    @Autowired
    public InvoiceService(TemplateEngine templateEngine, OrderService orderService) {

        this.templateEngine = templateEngine;
        this.orderService = orderService;
    }

    public byte[] generateInvoicePdf(UUID orderId) {
        Order order = orderService.getOrderById(orderId);

        Context context = new Context();
        context.setVariable("order", order);

        String htmlContent = templateEngine.process("invoice-template", context);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed", e);
        }
    }


}
