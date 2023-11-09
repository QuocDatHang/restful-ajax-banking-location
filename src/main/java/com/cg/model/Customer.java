package com.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name")
    @NotBlank(message = "Tên không được để trống")
    private String fullName;
    @NotBlank(message = "Email không được để trống")
    private String email;
    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;
    @OneToOne
    private LocationRegion locationRegion;
    @Column(columnDefinition = "decimal(10,2) default '0.00' ")
    private BigDecimal balance;
    @Column(columnDefinition = "boolean default false")
    private Boolean deleted;

}
