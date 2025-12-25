package testing.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email_address", unique = true)
    // Renaming column name from email to email_address in the database via Flyway migration Tool
    private String email;

    @Column(name = "full_name")
    // Renaming column name from name to full_name in the database via Flyway migration Tool
    private String name;

    @Column(name = "salary_amount")
    // Renaming column name from salary to salary_amount in  the database via Flyway migration Tool
    private Long salary;

}
