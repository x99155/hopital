package com.process.hopital.web;

import com.process.hopital.entities.Patient;
import com.process.hopital.repository.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {

    private PatientRepository patientRepository;

    @GetMapping("/")
    public String home() {
        return "redirect:/user/index";
    }


    /*
    *  Pour stocker les résultats dans le model on fait appel à l'objet model (de type map (key-value))
     */
    @GetMapping("/user/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "4") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String keyword) {

        // je récupère les données de la bdd que je stocke dans une liste
        Page<Patient> pagePatients = patientRepository.findByNomContainsIgnoreCase(keyword, PageRequest.of(page, size));

        // je stocke maintenant ma liste de patient dans le model(le model est de type key-value)
        // model qui sera ensuite transmis à la vue
        model.addAttribute("listPatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);

        return "patients"; // The name of the view template (patients.html)
    }

    @GetMapping("/admin/deletePatient")
    public String delete(@RequestParam(name = "id") Long id, String keyword, int page) {
        patientRepository.deleteById(id);

        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/formPatients")
    public String formPatients(Model model){

        model.addAttribute("patient", new Patient());
        return "formPatients";
    }

    /*
    @Valid: lors du stockage des données dans patient, on demande à spring de faire la validation
    BindingResult bindingResult : ou seront stocké les données en cas d'erreur lors de la validation

    Pour faire la validation dans spring mvc, on a besoin de 3 choses:
    - ajouter la dépendance spring boot validation
    - ajouter les annotations de validations dans l'entité
    - utiliser l'annotion @Valid, et l'objet BindingResult au niveau du controller
    - utiliser th:errors au niveau de la page html
     */
    @PostMapping("/admin/save")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult) {
        //validation coté serveur
        if(bindingResult.hasErrors()) {
            return "formPatients";
        }
        // save en bdd
        patientRepository.save(patient);

        return "redirect:/admin/formPatients";
    }

    /*
        [Allé]
        Client -> envoie l'id, le keyword, et la page de la donnée à modifier
        model  -> stockes ses données pour etre utilisé par le controlleur
        DAO    -> va chercher l'objet correspondant à l'id

        [Retour]
        DAO    -> envoit les données de l'objet demandé
        model  -> stockes ses données pour etre envoyé à la vue
        Client -> recoit les données de l'objet demandé


        Client(id, keyword, page) -> model -> DAO
        Client <- model <- DAO(un objet patient)

     */

    @GetMapping("/admin/editPatient")
    public String editPatient(Model model, Long id, String keyword, int page) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) throw new RuntimeException("Patient introuvable");

        model.addAttribute("patient", patient);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);

        return "editPatients";
    }

    /*
    grace à @Requestparam on peut définir les valeur par défaut
     */

    @PostMapping("/admin/update")
    public String update(Model model, @Valid Patient patient, BindingResult bindingResult,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "") String keyword) {
        //validation coté serveur
        if(bindingResult.hasErrors()) {
            return "formPatients";
        }
        // save en bdd
        patientRepository.save(patient);

        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

}
