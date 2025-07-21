package com.tierguardians.finances.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "assets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
