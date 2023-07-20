package ua.sikoraton.forexservice.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "currency_from",
            referencedColumnName = "code",
            nullable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "currency_from_exchange_foreignkey"))
    private Currency from;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "currency_to",
            referencedColumnName = "code",
            nullable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "currency_to_exchange_foreignkey"))
    private Currency to;
    @Column(columnDefinition = "numeric(12,6)", nullable = false)
    private BigDecimal rate;
    @Column(nullable = false)
    private String serviceName;
    @Column(nullable = false)
    private LocalDate date;
    private LocalDateTime dateFromImport;
    @Column(columnDefinition = "boolean default false")
    private boolean lower;
}
