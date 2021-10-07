package ru.geekbrains.myspringshop.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="product")
public class Product {
    @Id
    private UUID id;

    private String name;
    private BigDecimal price;
    private int count;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Person createdBy;

    private String vendorCode;
    private LocalDateTime createdAt;

    @PrePersist
    public void init() {
        if(this.id == null) {
            this.id = UUID.randomUUID();
        }
        this.createdAt = LocalDateTime.now();
    }
    public UUID getId() {
        return id;
    }

    public Product setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public int getCount() {
        return count;
    }

    public Product setCount(int count) {
        this.count = count;
        return this;
    }

    public Person getCreatedBy() {
        return createdBy;
    }

    public Product setCreatedBy(Person createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public Product setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Product setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
    public void increaseCount(){
        this.count ++;
    }

    public void decreaseCount(){
        this.count --;
    }
}
