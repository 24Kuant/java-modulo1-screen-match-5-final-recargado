package com.alura.screenmatch.calculos;

import com.alura.screenmatch.dto.TituloOmdbDto;
import com.alura.screenmatch.modelos.Titulo;
import com.alura.screenmatch.myexceptions.ErrorConsultaDatosPeliculaException;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ObtenDatosTitulo {
    HttpClient client;
    HttpRequest request;
    HttpResponse<String> response;
    Gson gsonInicial;
    Gson gson;

    public ObtenDatosTitulo(String uri) {
        //nuestro cliente
        this.client = HttpClient.newHttpClient();
        //HttpRequest NO puede ser instanciado de forma directo pq es un etodo Abstracto, por eso e usa el PATRON builder
        //lo que vamos a pedir
        this.request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .build();
        this.gsonInicial = new Gson();
        this.gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)  //los campos inician com letra en Mayusculas
                    .setPrettyPrinting()  //hace que el formato de json se vea ordenado y mas legible
                    .create();

    }

    public Titulo consultaDatosPelicula() {

        //lo que vamos a recibir
        HttpResponse<String> response = null;
        try {
            response = this.client.send(this.request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            System.out.println(json);

            //Convirtiendo de Json a Objetos : Serializacion: se puede usar: Jackson o Gson. en nuestro caso usaremos Gson.
                /*
                    de aqui se obtiene el jar de Gson:
                        https://mvnrepository.com/artifact/com.google.code.gson/gson
                        se baja el jar para agregarlo en el IntelliJ y que reconozca la dependencia de Gson
                        Menu --> Poject Structure --> Dependences  sino muestra el directorio de las dependencias, cerrar y abrir nuevamente IntelliJ.
                 */

            //Gson gsonInicial = new Gson();
            Titulo miTitulo = gsonInicial.fromJson(json, Titulo.class);
            System.out.println(miTitulo);  //System.out.println("Titulo : " + mititulo.getNombre());

            //no regresa nada pq en el json de la API tiene title y year, mientras que en nuestra clase titulo tenemos nombre y fechaDeLanzamiento
            //por lo que necesitamos hacer un tipo de conversion para asociar title con nombre y year con fechaDeLanzamiento
            //esto se logra con anotaciones en la clase Titulo, mediante la anotacion: SerializedName

                /* que asaria si en lugar de usar la API de OMDb Movies, usamos IMDb movies o moviesAPI, es evidente que en cada API
                    los nombres que maneja en sus estructuras de json no seran los mismo, en uno puede ser title, en otro puede ser name, en otro nameMovie, etc.
                    esto quiere decir que para poder usar varias APIs y no depender de una sola, debemos usar algo para poder conectarnos a cualquier API y no depender de los nombres de sus elementos.
                    se hace mediante DTOs o Data Transfer Objects.

                    El DTO sera el responsalbe de entender el Json de una o de varias APIS y hacer la transformacion hacia lo que nosotros necesitamos
                    el DTO es una clase intermedia, que interpreta los resultados de cualquier API y de ahi ya podemos trasformar los datos a nuestra clase final, en este caso la clase Titulo.

                * */

            TituloOmdbDto miTituloOmdb = gsonInicial.fromJson(json, TituloOmdbDto.class);
            System.out.println(miTituloOmdb);

                /* se mueve la declaracion, para convertir la lista de peliculas en json
                Gson gson = new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)  //los campos inician com letra en Mayusculas
                        .create();
                */

            miTituloOmdb = gson.fromJson(json, TituloOmdbDto.class);
            System.out.println(miTituloOmdb);

            //ya que logramos llevar la info del json del request al dto, ahora como le hacemos para mandar la info del DTO especifico a nuestra clase Titulo??

            //probar con las peliculas: Matrix, Bichos, Top gun
            //en top gun , debe ser tratado o convertido (incouding), en este caso el espacio, o caracteres especiales en otros caracteres que una url pueda interpretar de forma correcta.

            Titulo elTitulo = new Titulo(miTituloOmdb);  //creamos el constructor correspondiente
            System.out.println("Titulo ya convertido: " + elTitulo);

                /* al hacr la lista de peliculas que se van a uardar, este codio se mueve al final de capturar todas las peliculsa o series.
                //solo guarda una pelicula
                FileWriter file = new FileWriter("peliculas.txt");
                file.write(elTitulo.toString());
                file.close();
                */

            return elTitulo;

        } catch (IOException e) {
            throw new ErrorConsultaDatosPeliculaException("IOException. " + e.getMessage());
        } catch (InterruptedException e) {
            throw new ErrorConsultaDatosPeliculaException("InterruptedException. " + e.getMessage());
        }

    }
}
