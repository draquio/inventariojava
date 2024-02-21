package com.inventory.inventory.model;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name="category")
public class Category implements Serializable {
    @Serial
    private static final long serialVersionUID = -4310027227752446841L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

}
