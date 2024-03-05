package pl.rstepniewski.weatherapp.weatherapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.rstepniewski.weatherapp.weatherapp.model.dto.CityDto;
import pl.rstepniewski.weatherapp.weatherapp.model.dto.WeatherRequestFormDto;
import pl.rstepniewski.weatherapp.weatherapp.service.WeatherService;

@Controller
@RequestMapping("/weatherApp")
@RequiredArgsConstructor
public class WeatherViewController {
    private final WeatherService weatherService;

    @GetMapping()
    public  String indexView(Model model){
        return "weather/index";
    }

    @GetMapping("city")
    public String cityIndexView(@ModelAttribute("weatherRequestFrom") WeatherRequestFormDto weatherRequestFormDto, Model model) {
        if (model.containsAttribute("weatherRequestFrom")) {
            final CityDto chartData = weatherService.getWeatherChartData(weatherRequestFormDto);

            model.addAttribute("chartData", chartData);
        }
        return "weather/city";
    }

    @GetMapping("pick")
    public  String addView(Model model){
        model.addAttribute("weatherRequestFrom", new WeatherRequestFormDto());

        return "weather/formPickCity";
    }

    @PostMapping("pick")
    public  String add(@Valid WeatherRequestFormDto weatherRequestFormDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("weatherRequestFrom", weatherRequestFormDto);
            return "weather/formPickCity";
        }
        redirectAttributes.addFlashAttribute("weatherRequestFrom", weatherRequestFormDto);

        return "redirect:/weatherApp/city";
    }
}
