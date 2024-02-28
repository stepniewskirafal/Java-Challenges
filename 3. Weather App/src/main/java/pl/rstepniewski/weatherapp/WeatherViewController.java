package pl.rstepniewski.weatherapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/weatherApp")
public class WeatherViewController {

    @GetMapping()
    public  String indexView(Model model){
        return "weather/index";
    }

    @GetMapping("city")
    public String cityIndexView(Model model) {
        if (model.containsAttribute("weatherRequestFrom")) {
            WeatherRequestFrom weatherRequestFrom = (WeatherRequestFrom) model.asMap().get("weatherRequestFrom");
            model.addAttribute("city", weatherRequestFrom.getCity());
        }
        return "weather/city";
    }

    @GetMapping("pick")
    public  String addView(Model model){
        model.addAttribute("WeatherRequestFrom", new WeatherRequestFrom());

        return "weather/formPickCity";
    }

    @PostMapping("pick")
    public  String add(WeatherRequestFrom weatherRequestFrom, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("weatherRequestFrom", weatherRequestFrom);
        System.out.println(weatherRequestFrom);
        return "redirect:/weatherApp/city";
    }


}
