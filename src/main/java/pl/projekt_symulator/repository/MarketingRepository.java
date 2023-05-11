package pl.projekt_symulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.projekt_symulator.entity.MarketingData;


public interface MarketingRepository extends JpaRepository<MarketingData, Long> {

    MarketingData findByUserId(Long Id);


}
