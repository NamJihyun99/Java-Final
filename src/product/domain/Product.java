package product.domain;

import java.time.LocalDate;

public class Product {

    private String id;
    private String name;
    private String detail;
    private LocalDate startDate;
    private LocalDate endDate;
    private int salePrice;
    private int quantity;
    private final LocalDate createdDate;

    public Product(String name,
                   String detail,
                   LocalDate startDate,
                   LocalDate endDate,
                   int salePrice,
                   int quantity,
                   LocalDate date) {
        this.name = name;
        this.detail = detail;
        this.startDate = startDate;
        this.endDate = endDate;
        this.salePrice = salePrice;
        this.quantity = quantity;
        this.createdDate = date;
    }

    public Product(String id,
                   String name,
                   String detail,
                   LocalDate startDate,
                   LocalDate endDate,
                   int salePrice,
                   int quantity,
                   LocalDate createdDate) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.startDate = startDate;
        this.endDate = endDate;
        this.salePrice = salePrice;
        this.quantity = quantity;
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateDetail(String detail) {
        this.detail = detail;
    }

    public void updateSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }
}
