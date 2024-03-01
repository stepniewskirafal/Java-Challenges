package pl.rstepniewski.weatherapp;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.rstepniewski.weatherapp.model.WeatherRequestFrom;
import pl.rstepniewski.weatherapp.model.openmeteo.ChartDataDto;

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
    public String cityIndexView(Model model) {
        if (model.containsAttribute("weatherRequestFrom")) {
            WeatherRequestFrom weatherRequestFrom = (WeatherRequestFrom) model.asMap().get("weatherRequestFrom");
            final ChartDataDto weatherForecast = weatherService.getWeatherChartData(weatherRequestFrom);

            model.addAttribute("weatherForecast", weatherForecast);
        }
        return "weather/city";
    }

    @GetMapping("pick")
    public  String addView(Model model){
        model.addAttribute("weatherRequestFrom", new WeatherRequestFrom());

        return "weather/formPickCity";
    }

    @PostMapping("pick")
    public  String add(@Valid WeatherRequestFrom weatherRequestFrom, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("weatherRequestFrom", weatherRequestFrom);
            return "weather/formPickCity";
            //return "redirect:/weatherApp/pick";
        }
        redirectAttributes.addFlashAttribute("weatherRequestFrom", weatherRequestFrom);

        return "redirect:/weatherApp/city";
    }


}
