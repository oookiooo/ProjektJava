package net.javaguides.sms.repository;

import net.javaguides.sms.entity.Uzytkownicy;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UzytkownicyRepository extends JpaRepository<Uzytkownicy, Long>{
    Uzytkownicy findByLoginAndHaslo(String login, String haslo);

}