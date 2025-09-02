package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Person
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    // Relationer 1:1

    @OneToOne(mappedBy="person", cascade = CascadeType.ALL)
    private PersonDetail personDetail;

    // Relationer 1:m

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER) // You would never want to delete fees
    @Builder.Default  // <---- This one is necessary with @Builder
    private Set<Fee> fees = new HashSet<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default  // <---- This one is necessary with @Builder
    private Set<Node> nodes = new HashSet<>();

    // Bi-directional update

    public void addPersonDetail(PersonDetail personDetail)
    {
        this.personDetail = personDetail;
        if (personDetail != null)
        {
            personDetail.setPerson(this);
        }
    }

    public void addFee(Fee fee) {
        if (fee == null) return;
        fee.setPerson(this);
        this.fees.add(fee);
    }


    public void addNote(Node node) {
        this.nodes.add(node);
        if (nodes != null)
        {
            node.setPerson(this);
        }
    }
}
