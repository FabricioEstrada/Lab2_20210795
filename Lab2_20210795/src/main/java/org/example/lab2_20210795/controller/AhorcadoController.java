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

        model.addAttribute("palabra", palabraSeleccionada);
        model.addAttribute("intentos", intentos);

        return "juego";
    }
}