package uz.jamshid.java.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "student")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", updatable = false, nullable = false, insertable = false)
    private Integer id;

    @Column
    private String name;
}
