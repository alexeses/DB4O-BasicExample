package com.github.alexeses.pojo;

public class Autor {

    private String iniciales;
    private String nombre;
    private String nacionalidad;
    private Libro libro;

    public Autor(String iniciales, String nombre, String nacionalidad, Libro libro) {
        this.iniciales = iniciales;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.libro = libro;
    }

    public Libro getLibro() {
        return libro;
    }

    @Override
    public String toString() {

        return "El autor " + nombre + " (" + iniciales + ") con nacionalidad "
                + nacionalidad + " ha escrito el libro " + libro;
    }
}
