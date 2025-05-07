package com.alura.screenmatch.principal;

import com.alura.screenmatch.calculos.GuardaTitulos;
import com.alura.screenmatch.calculos.ObtenDatosTitulo;
import com.alura.screenmatch.calculos.ObtenTitulo;
import com.alura.screenmatch.modelos.Titulo;
import com.alura.screenmatch.myexceptions.ErrorConsultaDatosPeliculaException;
import com.alura.screenmatch.myexceptions.ErrorDuracionenMinutosException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrincipalApi {
    public static void main(String[] args) throws IOException, InterruptedException {
        ObtenTitulo obtenPelicula = new ObtenTitulo();
        List<Titulo> titulos = new ArrayList<>();

        while (true) {
            String busqueda = obtenPelicula.obtenNombrePelicula();
            if (busqueda.equalsIgnoreCase("salir")) {
                break;
            }

            String uri = obtenPelicula.obtenUrl();
            if (uri == null) {
                break;
            }

            try {
                ObtenDatosTitulo datosPelicula = new ObtenDatosTitulo(uri);
                Titulo elTitulo =  datosPelicula.consultaDatosPelicula();

                titulos.add(elTitulo);  //se agrega el titulo capturado.

            } catch (NumberFormatException e) {
                System.out.println("Ocurrió un error!");
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Ocurrió un error en la URI, verifique por favor!");
                System.out.println(e.getMessage());
            } catch (ErrorDuracionenMinutosException e) {
                System.out.println("ErrorDuracionenMinutosException!");
                System.out.println(e.getMessage());
            } catch (ErrorConsultaDatosPeliculaException e) {
                System.out.println("ErrorConsultaDatosPeliculaException!");
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado!. mensaje: " + e.getMessage());
                System.out.println(e.getMessage());
            }
        }

        System.out.println(titulos);  //capturar Matrix, encanto, top gun, bichos

        GuardaTitulos guardarTitulos = new GuardaTitulos("titulos.json");
        if (guardarTitulos.getFile() != null) {
            guardarTitulos.guardar(titulos);
            guardarTitulos.cerrar();
        }


        System.out.println("Finalizó la ejecución del programa");

        /*
        pelicula matriz:  {"Title":"Matrix","Year":"1993","Rated":"N/A","Released":"01 Mar 1993","Runtime":"60 min","Genre":"Action, Drama, Fantasy","Director":"N/A","Writer":"Grenville Case","Actors":"Nick Mancuso, Phillip Jarrett, Carrie-Anne Moss","Plot":"Steven Matrix is one of the underworld's foremost hitmen until his luck runs out, and someone puts a contract out on him. Shot in the forehead by a .22 pistol, Matrix \"dies\" and finds himself in \"The City In Between\", where he is shown the faces of all the men and women he's murdered and a sea of fire. He's informed that he will be given a second chance. He must earn a reprieve from Hell by helping others. He then wakes up in the hospital, after an apparent \"near death\" experience. In each episode, Matrix meets a new \"guide\" from the world beyond, and is given a new assignment, much in the manner of an unwilling guardian angel. Usually his guides give him little or no useful information about the job to come, and his methods of handling the cases are sometimes as brutal as the rules of his old profession, but he gets the job done.","Language":"English","Country":"Canada","Awards":"1 win total","Poster":"https://m.media-amazon.com/images/M/MV5BM2JiZjU1NmQtNjg1Ni00NjA3LTk2MjMtNjYxMTgxODY0NjRhXkEyXkFqcGc@._V1_SX300.jpg","Ratings":[{"Source":"Internet Movie Database","Value":"7.2/10"}],"Metascore":"N/A","imdbRating":"7.2","imdbVotes":"215","imdbID":"tt0106062","Type":"series","totalSeasons":"N/A","Response":"True"}
        pelicula bichos:  {"Title":"Bichos","Year":"2023","Rated":"N/A","Released":"08 Mar 2023","Runtime":"N/A","Genre":"Short, Family","Director":"Ares Sirvent","Writer":"Ares Sirvent","Actors":"Norberto Arribas, Alejandro Martinez, Antonio Reyes","Plot":"N/A","Language":"Spanish","Country":"Spain","Awards":"N/A","Poster":"https://m.media-amazon.com/images/M/MV5BNmU3NWM4MTItM2U4Mi00YmE5LWE2MmYtNGE4NDRiOWFiNjM2XkEyXkFqcGc@._V1_SX300.jpg","Ratings":[],"Metascore":"N/A","imdbRating":"N/A","imdbVotes":"N/A","imdbID":"tt22191036","Type":"movie","DVD":"N/A","BoxOffice":"N/A","Production":"N/A","Website":"N/A","Response":"True"}
        aqui runtime = N/A
         */

    }
}
