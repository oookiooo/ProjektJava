package net.javaguides.sms.controller;

import net.javaguides.sms.entity.Uzytkownicy;
import net.javaguides.sms.repository.PytaniaRepository;
import net.javaguides.sms.repository.UzytkownicyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import net.javaguides.sms.entity.Pytania;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;


@Controller
@SessionAttributes({"sesja","uzytkownik","haslo","id"})
public class PytaniaController {
	

	private PytaniaRepository pytaniaRepository;
	private UzytkownicyRepository uzytkownicyRepository;
	private HttpSession session;

	public PytaniaController(PytaniaRepository pytaniaRepository,UzytkownicyRepository uzytkownicyRepository) {
		super();
		this.pytaniaRepository = pytaniaRepository;
		this.uzytkownicyRepository = uzytkownicyRepository;

	}
	@GetMapping("/")

	public String Start(HttpSession session, Model model) {
		session.removeAttribute("uzytkownik");
		session.setAttribute("uzytkownik", "");
		model.addAttribute("uzytkownik","");
		session.removeAttribute("sesja");
		session.setAttribute("sesja", 2);
		model.addAttribute("sesja",2);

		return "redirect:/login";

	}
	public String getOdp(Long id) {
		Optional<Pytania> pytanieOptional = pytaniaRepository.findById(id);
		if (pytanieOptional.isPresent()) {
			Pytania pytanie = pytanieOptional.get();
			return pytanie.getOdp();
		} else {
			return null;
		}
	}
	@GetMapping("/wyloguj")
	public String wyloguj(HttpSession session,Model model){
		session.removeAttribute("uzytkownik");
		session.setAttribute("uzytkownik", "");
		model.addAttribute("uzytkownik","");
		session.removeAttribute("sesja");
		session.setAttribute("sesja", 2);
		model.addAttribute("sesja",2);
		return "redirect:/login";
	}

	@PostMapping("/submitQuiz")

	public String submitQuiz(@RequestParam("quizAnswer") String[] quizAnswers, Model model,HttpSession session) {
		int xd = (int) session.getAttribute("sesja");
		String xd2 = (String) session.getAttribute("uzytkownik");
		model.addAttribute("var",xd);
		model.addAttribute("var2",xd2);
		int countA = 0;
		int pom=0;
		String Idpytania;
		Long Idpytanial = null;
		int wynik=0;
		String mozliwaOdpowiedz="";
		for (String quizAnswer : quizAnswers)
		{

			if(pom==0)
			{
				Idpytania=quizAnswer;
				Idpytanial = Long.parseLong(Idpytania);
				pom=1;
			}
			if(quizAnswer.equals("A")||quizAnswer.equals("B")||quizAnswer.equals("C")||quizAnswer.equals("D"))
			{
				mozliwaOdpowiedz=mozliwaOdpowiedz+quizAnswer;
			}
			if(quizAnswer.equals("S"))
			{
				if(mozliwaOdpowiedz.equals(getOdp(Idpytanial)))
				{
				wynik=wynik+1;
				}
				else {
					if(mozliwaOdpowiedz.equals(""))
					{

					}
					else {
						wynik=wynik-1;
						countA--;
					}

				}
				pom=0;
				mozliwaOdpowiedz="";
			}

			System.out.println(quizAnswer+" "+pom+" "+mozliwaOdpowiedz+" "+ getOdp(Idpytanial));
		}
		Double ocena = null;

		if(wynik>=5&& wynik<=6) {
			ocena = 3.0;
		}
		if(wynik==7) {
			ocena = 3.5;
		}
		if(wynik==8) {
			ocena = 4.0;
		}
		if(wynik==9) {
			ocena = 4.5;
		}
		if(wynik==10) {
			ocena = 5.0;
		}
		if (ocena==null)
		{
			ocena=2.0;
		}
		long iduser = (long) session.getAttribute("id");
		Optional<Uzytkownicy> optionalUzytkownik = uzytkownicyRepository.findById(iduser);
		if (optionalUzytkownik.isPresent()) {
			Uzytkownicy uzytkownik = optionalUzytkownik.get();
			uzytkownik.setOcena(Double.toString(ocena));
			uzytkownicyRepository.save(uzytkownik);
		}
		model.addAttribute("ocena", ocena);
		model.addAttribute("countA", wynik);
		model.addAttribute("ujemne", countA);
		return "results";
	}
	@GetMapping("/login")
	public String Login(Model model,HttpSession session) {
		int xd = (int) session.getAttribute("sesja");
		String xd2 = (String) session.getAttribute("uzytkownik");
		model.addAttribute("var",xd);
		model.addAttribute("var2",xd2);
		return "login";
	}
	@PostMapping("/sprawdz_czy_login")
	public String checkLogin(@RequestParam("login") String login,
							 @RequestParam("haslo") String haslo,
							 Model model, HttpSession session) {
		int xd = (int) session.getAttribute("sesja");
		String xd2 = (String) session.getAttribute("uzytkownik");
		model.addAttribute("var",xd);
		model.addAttribute("var2",xd2);

		Uzytkownicy uzytkownik = uzytkownicyRepository.findByLoginAndHaslo(login, haslo);
		System.out.println(login+haslo);
// jeśli użytkownik nie istnieje, dodaj odpowiedni komunikat do modelu
		if (uzytkownik == null) {
			session.removeAttribute("sesja");
			session.setAttribute("sesja", 2);
			model.addAttribute("sesja",2);

			int authenticatedUser = (int) session.getAttribute("sesja");
			System.out.println("Wartość zmiennej sesyjnej 'counter' to: " + authenticatedUser);
			return "redirect:/register";

		}
		else{
			if(login.equals("arek")&&haslo.equals("arek"))
			{
				session.removeAttribute("uzytkownik");
				session.setAttribute("uzytkownik", login);
				model.addAttribute("uzytkownik",login);

				session.removeAttribute("haslo");
				session.setAttribute("haslo", haslo);
				model.addAttribute("haslo",haslo);

				session.removeAttribute("id");
				session.setAttribute("id", uzytkownik.getId());
				model.addAttribute("id",uzytkownik.getId());
				System.out.println("Wartość zmiennej sesyjnasdter' to: " + uzytkownik.getId());

				session.removeAttribute("sesja");
				session.setAttribute("sesja", 20);
				model.addAttribute("sesja",20);

			}
			else{
				session.removeAttribute("uzytkownik");
				session.setAttribute("uzytkownik", login);
				model.addAttribute("uzytkownik",login);

				session.removeAttribute("haslo");
				session.setAttribute("haslo", haslo);
				model.addAttribute("haslo",haslo);

				session.removeAttribute("id");
				session.setAttribute("id", uzytkownik.getId());
				model.addAttribute("id",uzytkownik.getId());
				System.out.println("Wartość zmiennej asd: " + uzytkownik.getId());

				session.removeAttribute("sesja");
				session.setAttribute("sesja", 4);
				model.addAttribute("sesja",4);
			}



			int authenticatedUser = (int) session.getAttribute("sesja");
			System.out.println("Wartość zmiennej sesyjnej 'counter' to: " + authenticatedUser);
			return "redirect:/quiz";
		}
// w przeciwnym razie przekieruj do wybranej strony


	}
	@GetMapping("/register")
	public String Register(Model model,HttpSession session) {
		int xd = (int) session.getAttribute("sesja");
		String xd2 = (String) session.getAttribute("uzytkownik");
		model.addAttribute("var",xd);
		model.addAttribute("var2",xd2);


		return "register";

	}
	@GetMapping("/register/new")
	public String WyswietlFormRejestracji(Model model,HttpSession session) {
		int xd = (int) session.getAttribute("sesja");
		String xd2 = (String) session.getAttribute("uzytkownik");
		model.addAttribute("var",xd);
		model.addAttribute("var2",xd2);

		Uzytkownicy uzytkownik = new Uzytkownicy();
		model.addAttribute("uzytkownicy", uzytkownik);
		return "rejestracja_new";

	}
	@PostMapping("/register")
	public String zapisz(@ModelAttribute("uzytkownicy") Uzytkownicy uzytkownicy,Model model,HttpSession session) {
		int xd = (int) session.getAttribute("sesja");
		String xd2 = (String) session.getAttribute("uzytkownik");
		model.addAttribute("var",xd);
		model.addAttribute("var2",xd2);
		uzytkownicyRepository.save(uzytkownicy);
		return "redirect:/register";
	}
	@GetMapping("/quiz")
	public String listPytania2(Model model,HttpSession session) {
		int xd = (int) session.getAttribute("sesja");
		String xd2 = (String) session.getAttribute("uzytkownik");
		model.addAttribute("var",xd);
		model.addAttribute("var2",xd2);
		int authenticatedUser = (int) session.getAttribute("sesja");
		if (authenticatedUser ==2) {

			return "redirect:/login";
		}
		model.addAttribute("pytania2", pytaniaRepository.findAll());
		return "quiz";
	}


	@GetMapping("/pytania")
	public String listPytania(HttpSession session,Model model) {
		int xd = (int) session.getAttribute("sesja");
		String xd2 = (String) session.getAttribute("uzytkownik");
		model.addAttribute("var",xd);
		model.addAttribute("var2",xd2);

		int authenticatedUser = (int) session.getAttribute("sesja");
		System.out.println("Wartość zmiennej sesyjnej 'counter' to: " + authenticatedUser);
		if (authenticatedUser != 20) {

			return "redirect:/login";
		}
		model.addAttribute("pytania", pytaniaRepository.findAll());
		return "pytania";
	}
	
	@GetMapping("/pytania/new")
	public String createPytaniaFormularz(Model model,HttpSession session) {
		int xd = (int) session.getAttribute("sesja");
		String xd2 = (String) session.getAttribute("uzytkownik");
		model.addAttribute("var",xd);
		model.addAttribute("var2",xd2);

		Pytania pytania = new Pytania();
		model.addAttribute("pytania", pytania);
		return "utwoz_pytanie";
		
	}
	
	@PostMapping("/pytania")
	public String savePytania(@ModelAttribute("pytania") Pytania pytania,Model model,HttpSession session) {
		int xd = (int) session.getAttribute("sesja");
		String xd2 = (String) session.getAttribute("uzytkownik");
		model.addAttribute("var",xd);
		model.addAttribute("var2",xd2);
		pytaniaRepository.save(pytania);
		return "redirect:/pytania";
	}
	
	@GetMapping("/pytania/edit/{id}")
	public String editPytaniaFormularz(@PathVariable Long id, Model model,HttpSession session) {
		int xd = (int) session.getAttribute("sesja");
		String xd2 = (String) session.getAttribute("uzytkownik");
		model.addAttribute("var",xd);
		model.addAttribute("var2",xd2);
		model.addAttribute("pytania", pytaniaRepository.findById(id).get());
		return "edytuj_pytanie";
	}

	@PostMapping("/pytania/{id}")
	public String updatePytania(@PathVariable Long id,
								@ModelAttribute("pytania") Pytania pytania,Model model,HttpSession session) {
		int xd = (int) session.getAttribute("sesja");
		String xd2 = (String) session.getAttribute("uzytkownik");
		model.addAttribute("var",xd);
		model.addAttribute("var2",xd2);
		

		Pytania existingPytania = pytaniaRepository.findById(id).get();
		existingPytania.setId(id);
		existingPytania.setPytanie(pytania.getPytanie());
		existingPytania.setA(pytania.getA());
		existingPytania.setB(pytania.getB());
		existingPytania.setC(pytania.getC());
		existingPytania.setD(pytania.getD());
		existingPytania.setOdp(pytania.getOdp());


		pytaniaRepository.save(existingPytania);
		return "redirect:/pytania";
	}
	

	
	@GetMapping("/pytania/{id}")
	public String usunPytanie(@PathVariable Long id,Model model,HttpSession session) {
		int xd = (int) session.getAttribute("sesja");
		String xd2 = (String) session.getAttribute("uzytkownik");
		model.addAttribute("var",xd);
		model.addAttribute("var2",xd2);
		pytaniaRepository.deleteById(id);
		return "redirect:/pytania";
	}
	@GetMapping("/pokazoceny")
	public String pokazOceny(Model model) {
		List<Uzytkownicy> uzytkownicy = uzytkownicyRepository.findAll();
		model.addAttribute("uzytkownicy", uzytkownicy);
		return "pokazoceny";
	}
}
