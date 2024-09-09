package com.process.hopital.web;

import com.process.hopital.entities.Patient;
import com.process.hopital.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {

    private PatientRepository patientRepository;

    /*
    *  Pour stocker les résultats dans le model on fait appel à l'objet model (de type map (key-value))
     */
    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "4") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String keyword) {

        // je récupère les données de la bdd que je stocke dans ma liste
        Page<Patient> pagePatients = patientRepository.findByNomContainsIgnoreCase(keyword, PageRequest.of(page, size));

        // je stocke maintenant ma liste de patient dans le model de type key-value
        // model qui sera ensuite transmis à la vue
        model.addAttribute("listPatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);

        return "patients"; // The name of the view template (patients.html)
    }

    @GetMapping("/deletePatient")
    public String delete(@RequestParam(name = "id") Long id, String keyword, int page) {
        patientRepository.deleteById(id);

        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

}
