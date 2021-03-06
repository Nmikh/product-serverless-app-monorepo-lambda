package com.products.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.products.core.model.Product;
import com.products.core.repository.ProductRepository;
import org.apache.log4j.Logger;

import java.util.List;

public class ProductsHandler implements RequestHandler<SQSEvent, String> {
    private final Logger logger = Logger.getLogger(this.getClass());
    private final ProductRepository productRepository;

    public ProductsHandler() {
        productRepository = new ProductRepository();
    }

    @Override
    public String handleRequest(SQSEvent sqsEvent, Context context) {
        List<SQSEvent.SQSMessage> records = sqsEvent.getRecords();
        for (SQSEvent.SQSMessage sqsMessage : records) {
            String productJson = sqsMessage.getBody();
            Product product = Product.getProduct(productJson);
            productRepository.save(product);
        }

        return null;
    }
}
