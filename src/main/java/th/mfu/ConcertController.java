package th.mfu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
//import java.util.Locale;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ConcertController {
    
    // Step 1: Create HashMap to store concerts and track next ID
    private HashMap<Integer, Concert> concerts = new HashMap<>();
    private int nextId = 1;
    
    // Step 2: Add InitBinder to handle Date conversion from form
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    
    // Step 3: Handle GET request to list all concerts
    @GetMapping("/concerts")
    public String listConcerts(Model model) {
        // Add the list of concerts to the model so the view can access them
        List<Concert> concertList = new ArrayList<>(concerts.values());
        model.addAttribute("concerts", concertList);
        
        // Return the template name (list-concerts.html)
        return "list-concert";
    }
    
    // Step 4: Handle GET request to show add concert form
    @GetMapping("/add-concert")
    public String addAConcertForm(Model model) {
        // Create a new empty Concert object for form binding
        Concert concert = new Concert();
        model.addAttribute("concert", concert);
        
        // Return the form template (add-concert-form.html)
        return "add-concert-form";
    }
    
    // Step 5: Handle POST request to save a new concert
    @PostMapping("/concerts")
    public String saveConcert(@ModelAttribute Concert concert) {
        // Set the ID for the new concert
        concert.setId(nextId);
        
        // Add concert to the HashMap
        concerts.put(nextId, concert);
        
        // Increment the ID counter for next concert
        nextId++;
        
        // Redirect to the concerts list page
        return "redirect:/concerts";
    }
    
    // Step 6: Handle GET request to delete a specific concert
    @GetMapping("/delete-concert/{id}")
    public String deleteConcert(@PathVariable int id) {
        // Remove the concert with the given ID from HashMap
        concerts.remove(id);
        
        // Redirect to the concerts list page
        return "redirect:/concerts";
    }
    
    // Step 7: Handle GET request to delete all concerts
    @GetMapping("/delete-concert")
    public String removeAllConcerts() {
        // Clear all concerts from the HashMap
        concerts.clear();
        
        // Reset the ID counter
        nextId = 1;
        
        // Redirect to the concerts list page
        return "redirect:/concerts";
    }
}