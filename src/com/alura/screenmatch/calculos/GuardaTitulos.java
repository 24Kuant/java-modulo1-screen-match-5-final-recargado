package com.alura.screenmatch.calculos;

import com.alura.screenmatch.modelos.Titulo;
import com.alura.screenmatch.myexceptions.ErrorGuardarArchivoException;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GuardaTitulos {
    private FileWriter file;
    private String fileName;
    Gson gson;

    public GuardaTitulos(String fileName) {
        if (fileName == null) {
            System.out.println("Nombre del archivo destino incorrecto");
            throw new ErrorGuardarArchivoException("ErrorGuardarArchivoException.Constructor. Error: Nombre del archivo incorrecto!");
        }
        this.fileName = fileName;
        try {
            this.file = new FileWriter(this.fileName);  //"titulos.json"
            this.gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)  //los campos inician com letra en Mayusculas
                    .setPrettyPrinting()  //hace que el formato de json se vea ordenado y mas legible
                    .create();
        } catch (IOException e) {
            this.file = null;
            throw new ErrorGuardarArchivoException("ErrorGuardarArchivoException.Constructor. Error: " + e.getMessage());
        }

    }

    public boolean guardar(List<Titulo> titulos) {
        if (this.file == null) {
            System.out.println("No se pudo inicializar el archivo destino");
            return false;
        }
        if(titulos.isEmpty()) {
            System.out.println("no existen titulos para guardar");
            return false;
        }

        try {
            this.file.write(this.gson.toJson(titulos));
            return true;
        } catch (IOException e) {
            throw new ErrorGuardarArchivoException("ErrorGuardarArchivoException.guardar. Error: " + e.getMessage());
        }
    }

    public void cerrar() {
        if (this.file == null) {
            System.out.println("No se pudo cerrar el archivo destino (no fue inicializado)");
            throw new ErrorGuardarArchivoException("ErrorGuardarArchivoException.cerrar. Error: Nombre del archivo incorrecto");
        }

        try {
            this.file.close();
        } catch (IOException e) {
            throw new ErrorGuardarArchivoException("ErrorGuardarArchivoException.cerrar. Error: " + e.getMessage());
        }
    }

    //getters

    public FileWriter getFile() {
        return this.file;
    }
}
