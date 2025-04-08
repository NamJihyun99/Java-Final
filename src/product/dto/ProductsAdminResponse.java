package product.dto;

import product.domain.Product;

import java.time.LocalDate;

public class ProductsAdminResponse {

    private final String id;
    private final String name;
    private final int salePrice;
    private final int quantity;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private ProductsAdminResponse(String id, String name, int salePrice, int quantity, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.salePrice = salePrice;
        this.quantity = quantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static ProductsAdminResponse of(Product product) {
        return new ProductsAdminResponse(
                product.getId(),
                product.getName(),
                product.getSalePrice(),
                product.getQuantity(),
                product.getStartDate(),
                product.getEndDate()
                );
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
