package com.ua07.transactions.service;

import com.ua07.transactions.model.Order;
import com.ua07.transactions.model.OrderLineItem;
import com.ua07.transactions.model.OrderStatus;
import com.ua07.transactions.model.PaymentMethod;
import com.ua07.transactions.model.TransactionStatus;
import com.ua07.transactions.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoiceService {

    private final TemplateEngine templateEngine;

    @Autowired
    public InvoiceService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generateInvoicePdf(Transaction transaction) {
        Order order = transaction.getOrder();

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
    public static void main(String[] args) {
        // Create dummy line items
        OrderLineItem item1 = new OrderLineItem("P001", "Widget", 2, 10.0, 20.0, null);
        OrderLineItem item2 = new OrderLineItem("P002", "Gadget", 1, 15.0, 15.0, null);

        // Create order using Lombok constructor
        Order order = Order.builder()
                .userId("user-123")
                .totalAmount(35.0)
                .status(OrderStatus.CONFIRMED)
                .createdAt(LocalDateTime.now())
                .confirmedAt(LocalDateTime.now())
                .shippingAddress("123 Main Street")
                .lineItems(List.of(item1, item2))
                .build();

        // Set order reference in line items
        item1.setOrder(order);
        item2.setOrder(order);

        // Create transaction using Lombok constructor
        Transaction transaction = new Transaction(order, PaymentMethod.CARD, TransactionStatus.APPROVED);
        order.setTransaction(transaction);

        // Set up Thymeleaf
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/"); // src/main/resources/templates
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        // Generate invoice PDF
        InvoiceService invoiceService = new InvoiceService(templateEngine);
        byte[] pdfBytes = invoiceService.generateInvoicePdf(transaction);

        // Save to file
        try (FileOutputStream fos = new FileOutputStream("invoice.pdf")) {
            fos.write(pdfBytes);
            System.out.println("Invoice PDF generated successfully: invoice.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

