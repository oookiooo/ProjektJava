package net.javaguides.sms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pytania")
public class Pytania {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "pytanie", nullable = false)
	private String pytanie;
	
	@Column(name = "A")
	private String A;
	
	@Column(name = "B")
	private String B;
	@Column(name = "C")
	private String C;
	@Column(name = "D")
	private String D;
	@Column(name = "Odp")
	private String Odp;
	public Pytania() {
		
	}
	
	public Pytania(String pytanie, String A, String B, String C, String D, String Odp) {
		super();
		this.pytanie = pytanie;
		this.A = A;
		this.B = B;
		this.C = C;
		this.D = D;
		this.Odp = Odp;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPytanie() {
		return pytanie;
	}

	public void setPytanie(String pytanie) {
		this.pytanie = pytanie;
	}

	public String getA() {
		return A;
	}

	public void setA(String a) {
		A = a;
	}

	public String getB() {
		return B;
	}

	public void setB(String b) {
		B = b;
	}

	public String getC() {
		return C;
	}

	public void setC(String c) {
		C = c;
	}

	public String getD() {
		return D;
	}

	public void setD(String d) {
		D = d;
	}

	public String getOdp() {
		return Odp;
	}

	public void setOdp(String odp) {
		Odp = odp;
	}

}
