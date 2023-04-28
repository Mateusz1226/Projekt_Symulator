package pl.projekt_symulator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Marketing_datas")
public class MarketingData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = " marketing_data_id")
    private Long id;
   // private String Region;
    private int age;
    private Boolean marketingAgreement;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
