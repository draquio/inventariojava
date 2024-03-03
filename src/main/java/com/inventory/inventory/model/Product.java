package com.inventory.inventory.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
@Data
@Entity
@Table(name="product")
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = -1357017585153571590L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private int price;
    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Category category;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column( name = "picture", columnDefinition = "LONGBLOB")
    private byte[] picture;
}
