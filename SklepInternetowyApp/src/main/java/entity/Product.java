package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private Long id;
    private String name;
    private String description;
    private int amountInStock;
    private int maximumInStock;

}
