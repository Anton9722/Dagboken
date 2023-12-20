package com.dagbok.dagbok;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class EntriesController {

    @Autowired
    private EntrieRepository entrieRepository;
    
    @GetMapping("/")
    public String getIndex(Model model) {

        model.addAttribute("entries", entrieRepository.findByCurrentOrPastDate());

        return "index";
    }

    @PostMapping("/new-item")
    public String addNew(@RequestParam("title") String title, @RequestParam("text") String text, @RequestParam("date") LocalDate date) {
        
        Entries entry = new Entries();

        entry.setTitle(title);
        entry.setText(text);
        entry.setDate(date);

        entrieRepository.save(entry);

        return "redirect:/";

    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        
        System.out.println("delete this " + id);
        entrieRepository.deleteById(id);

        return "redirect:/";
    }

    @GetMapping("/edit")
    public String showEditingForm(@RequestParam int id, Model model) {
        Optional<Entries> optionalEntry = entrieRepository.findById(id);
    

        Entries entry = optionalEntry.get();
        model.addAttribute("entry", entry);
        return "edit";

}
    
    @PostMapping("/update")
    public String updateEntry(@ModelAttribute Entries entry) {

        entrieRepository.save(entry);

        return "redirect:/";
    }

    @GetMapping("/search")
    public String openSearchPage() {

        return "search";
    }

    @GetMapping("/search-results")
    public String searchEntries(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate, Model model) {

        List<Entries> entries = entrieRepository.findByDateBetween(startDate, endDate);
        model.addAttribute("entries", entries);

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "search";
    }
    
}
