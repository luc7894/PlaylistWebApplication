package hr.tvz.spotlist.spotlistapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "authority")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
}