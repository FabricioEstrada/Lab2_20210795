package org.example.lab2_20210795.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Controller
public class AhorcadoController {
    //Creando las listas que no variarán
    private static final List<String> ANIMALES = Arrays.asList("leon", "elefante", "tigre", "cebra", "jirafa", "delfin", "ballena", "gorila", "panda", "aguila", "hipopotamo", "koala", "lobo", "oso", "canguro");
    private static final List<String> FRUTAS = Arrays.asList("manzana", "platano", "kiwi", "mango", "pera", "uva", "fresa", "naranja", "piña", "sandia", "cereza", "melon", "papaya", "limon", "higo");
    private static final List<String> PAISES = Arrays.asList("Mexico", "Canada", "Brasil", "España", "Francia", "Italia", "Alemania", "Japon", "Australia", "Argentina", "Chile", "Peru", "Estados Unidos", "China", "India");

    @GetMapping("/configuracion")
    public String configuracion() {
        return "configuracion";
    }

    @PostMapping("/iniciar-juego")
    public String iniciarJuego(@RequestParam("longitud") int longitud,
                               @RequestParam("intentos") int intentos,
                               @RequestParam("tema") String tema,
                               Model model) {
        List<String> palabras;
        switch (tema) {
            case "Animales":
                palabras = ANIMALES;
                break;
            case "Frutas":
                palabras = FRUTAS;
                break;
            case "Países":
                palabras = PAISES;
                break;
            default:
                palabras = Arrays.asList();
        }

        List<String> palabrasFiltradas = new ArrayList<>();
        for (String palabra : palabras) {
            if (palabra.length() == longitud) {
                palabrasFiltradas.add(palabra);
            }
        }

        if (palabrasFiltradas.isEmpty()) {
            model.addAttribute("mensaje", "No hay palabras disponibles para esa longitud");
            return "configuracion";
        }

        String palabraSeleccionada = palabrasFiltradas.get(new Random().nextInt(palabrasFiltradas.size()));
        String palabraOculta = "-".repeat(palabraSeleccionada.length()); // Inicializa con guiones bajos

        model.addAttribute("palabraOculta", palabraOculta);
        model.addAttribute("palabraSeleccionada", palabraSeleccionada);
        model.addAttribute("intentos", intentos);

        return "juego";
    }
    @PostMapping("/adivinar-letra")
    public String adivinarLetra(@RequestParam("letra") char letra,
                                @RequestParam("palabraOculta") String palabraOculta,
                                @RequestParam("palabraSeleccionada") String palabraSeleccionada,
                                @RequestParam("intentos") int intentos,
                                Model model) {
        StringBuilder nuevaPalabraOculta = new StringBuilder(palabraOculta);
        boolean letraEncontrada = false;

        for (int i = 0; i < palabraSeleccionada.length(); i++) {
            if (palabraSeleccionada.charAt(i) == letra) {
                nuevaPalabraOculta.setCharAt(i, letra);
                letraEncontrada = true;
            }
        }

        if (!letraEncontrada) {
            intentos--;
        }

        model.addAttribute("palabraSeleccionada", palabraSeleccionada);
        model.addAttribute("palabraOculta", nuevaPalabraOculta.toString());
        model.addAttribute("intentos", intentos);

        if (nuevaPalabraOculta.toString().equals(palabraSeleccionada)) {
            model.addAttribute("mensaje", "¡Felicidades! Has adivinado la palabra.");
            return "resultado";
        } else if (intentos <= 0) {
            model.addAttribute("mensaje", "Lo siento, has perdido. La palabra era: " + palabraSeleccionada);
            return "resultado";
        }

        return "juego";
    }
}