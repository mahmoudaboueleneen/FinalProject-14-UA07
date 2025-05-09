package com.ua07.transactions.service;

import com.ua07.transactions.model.Order;
import com.ua07.transactions.model.OrderLineItem;
import com.ua07.transactions.model.OrderStatus;
import com.ua07.transactions.model.PaymentMethod;
import com.ua07.transactions.model.Transaction;
import com.ua07.transactions.model.TransactionStatus;

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
        OrderLineItem item1 = new OrderLineItem();
        item1.setProductId("P001");
        item1.setName("Widget");
        item1.setCount(2);
        item1.setUnitCost(10.0);
        item1.setTotalCost(20.0);

        OrderLineItem item2 = new OrderLineItem();
        item2.setProductId("P002");
        item2.setName("Gadget");
        item2.setCount(1);
        item2.setUnitCost(15.0);
        item2.setTotalCost(15.0);

        List<OrderLineItem> lineItems = Arrays.asList(item1, item2);

        // Create order
        Order order = new Order();
        order.setUserId(UUID.randomUUID());
        order.setTotalAmount(35.0);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setCreatedAt(LocalDateTime.now());
        order.setConfirmedAt(LocalDateTime.now());
        order.setShippingAddress("123 Main Street");
        order.setLineItems(lineItems);

        // Set back reference in line items
        item1.setOrder(order);
        item2.setOrder(order);

        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setOrder(order);
        transaction.setPaymentMethod(PaymentMethod.CARD);
        transaction.setStatus(TransactionStatus.APPROVED);

        order.setTransaction(transaction);

        // Set up Thymeleaf
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
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
