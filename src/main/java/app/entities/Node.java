package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Entity


public class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 256)
    private String note;
    private LocalDate created;
    @Column(nullable = false)
    private String createdBy;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Person person;

    @PrePersist
    private void beforePersist() {
        this.created = LocalDate.now();
    }


}
