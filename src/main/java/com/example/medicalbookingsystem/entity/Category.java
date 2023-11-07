package com.example.medicalbookingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="category")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
