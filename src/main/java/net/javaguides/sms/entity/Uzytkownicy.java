package net.javaguides.sms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "uzytkownicy")
public class Uzytkownicy {




    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    @Column(name = "login")
        private String login;

        @Column(name = "haslo")
        private String haslo;

    public String getOcena() {
        return ocena;
    }

    public void setOcena(String ocena) {
        this.ocena = ocena;
    }

    @Column(name = "ocena")
    private String ocena;


    public Uzytkownicy() {

    }
    public Uzytkownicy(Long id, String login, String haslo) {
        this.id = id;
        this.login = login;
        this.haslo = haslo;
        this.ocena=ocena;
    }


}
