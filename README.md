## Noter

- Typiske Lombok annotationer for hver klasse:
```plaintext
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
```
- Typiske JPA annotationer
```plaintext
@Entity
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@OneToOne(mappedBy="person", cascade = CascadeType.ALL)
@OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
@Builder.Default  // <---- This one is necessary with @Builder
```

- One-To-Many: Husk at bruge Hashsets for at undgå dubletter

```java
private Set<Fee> fees = new HashSet<>();

```
- Hjælpe metoder til at lave bi-directional relationer
```java
  public void addFee(Fee fee)
    {
        this.fees.add(fee);
        if (fee != null)
        {
            fee.setPerson(this);
        }
    }
```

## Husk!
* At indsætte entiteter i `Hibernate.config` filen
* Oprette database
* Oprette `config.properties` i `ressource` mappen

    ```bash
    DB_NAME=dolphin
    DB_USERNAME=postgres
    DB_PASSWORD=postgres
    ```