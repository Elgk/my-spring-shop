package ru.geekbrains.myspringshop.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content", length = 1024)
    private String content;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    private Person person;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_moderated", nullable = false)
    private boolean isModerated;

    @PrePersist
    public void init() {
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Person getPerson() {
        return person;
    }

    public Product getProduct() {
        return product;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isModerated() {
        return isModerated;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setModerated(boolean moderated) {
        isModerated = moderated;
    }
}
