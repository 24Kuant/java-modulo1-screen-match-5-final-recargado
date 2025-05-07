package com.alura.screenmatch.calculos;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Scanner;

public class ObtenTitulo {
    private String pelicula;
    Scanner teclado = new Scanner(System.in);

    public String obtenNombrePelicula() {
        System.out.print("Teclea el nombre de una pelicula: ");
        this.pelicula = teclado.nextLine();
        return this.pelicula;
    }

    public String obtenUrl() {
        //String uri = "http://www.omdbapi.com/?apikey=f098110&t=" + busqueda.replace(" ", "+") + "&plot=full";
        String url = "http://www.omdbapi.com/?apikey=f098110&t=" + this.pelicula.replace(" ", "+") + "&plot=full";
        System.out.println("url = " + url);
        return url;

        /*
        String encodedUrl = null;
        try {
            encodedUrl = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error al generar la url. Mensaje : " + e.getMessage());
        }
        System.out.println(encodedUrl);

        return encodedUrl;
        */
    }
}
