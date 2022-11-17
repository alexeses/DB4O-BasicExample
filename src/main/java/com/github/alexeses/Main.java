package com.github.alexeses;

import com.github.alexeses.db.Access;
import com.github.alexeses.pojo.Autor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Main {

    static Access accesoDB = new Access();
    static Scanner sc;


    public static void main(String[] args) {

        sc = new Scanner(System.in);
        mostrarMenu();
        accesoDB.close();
        sc.close();

    }

    private static void consultarLibros() {
        ArrayList<Autor> listaAutores = accesoDB.consularTodas();

        if (listaAutores.isEmpty()) {
            System.out.println("No se han encontrado datos");
        }else {
            System.out.println("Se han encontrado " + listaAutores.size() + " personas");
            for (Autor a : listaAutores) {
                System.out.println(a);
            }
        }
    }

    private static void mostrarMenu() {
        while (true) {
            System.out.println("\nIL - Insertar autores y libros");
            System.out.println("ML - Modificar el número de páginas de un libro");
            System.out.println("CT - Consultar todos los libro");
            System.out.println("CL - Consultar los libros con más de 300 páginas");
            System.out.println("S  - Salir\n");
            System.out.println("Introduzca una opción: ");

            leerOpcion();

            }
        }

    private static void leerOpcion() {

        String opc = getOption(sc);
        switch (opc) {
            case "IL":
                insertarAutoresLibros(sc);
                break;
            case "ML":
                modificarPaginasLibro(sc);
                break;
            case "CT":
                consultarLibros();
                break;
            case "CL":
                consultarLibrosPaginas();
                break;
            case "S":
                System.out.println("Saliendo...");
                System.exit(0);
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    private static void consultarLibrosPaginas() {
        ArrayList<Autor> listaAutores = accesoDB.consultarPorPagina(50);

        if (listaAutores.isEmpty()) {
            System.out.println("No se han encontrado datos");
        }else {
            System.out.println("Se han encontrado " + listaAutores.size() + " personas");
            for (Autor a : listaAutores) {
                System.out.println(a);
            }
        }

    }

    private static String getOption(Scanner sc) {
        String[] opciones = {"IL", "ML", "CT", "CL", "S"};

        while (true) {
            String opcion = sc.nextLine().toUpperCase();
            if (Arrays.asList(opciones).contains(opcion)) {
                return opcion;
            } else {
                System.out.println("Opción no válida, las opciones válidas son: " + Arrays.toString(opciones));
            }
        }
    }

    private static void modificarPaginasLibro(Scanner sc) {

        System.out.println("Introduzca el título del libro: ");
        String titulo = getTitulo(sc);
        System.out.println("Introduzca el número de páginas: ");
        int paginas = getPaginas(sc);

        accesoDB.modificarPaginasLibro(titulo, paginas);

    }

    private static void insertarAutoresLibros(Scanner sc) {

        String iniciales;
        String nombre;
        String nacionalidad;
        String titulo;
        int paginas;

        System.out.println("Introduzca las iniciales del autor: ");
        iniciales = getIniciales(sc);
        System.out.println("Introduzca el nombre del autor: ");
        nombre = getNombre(sc);
        System.out.println("Introduzca la nacionalidad del autor: ");
        nacionalidad = getNacionalidad(sc);
        System.out.println("Introduzca el título del libro: ");
        titulo = getTitulo(sc);
        System.out.println("Introduzca el número de páginas del libro: ");
        paginas = getPaginas(sc);

        accesoDB.insertarAutorLibro(iniciales, nombre, nacionalidad, titulo, paginas);
    }



    /** Método para obtener las páginas de un libro y que sean un número entero entre 0 y 1000.
     *
     * @param sc Scanner
     * @return paginas
     */
    private static int getPaginas(Scanner sc) {

        int paginas = sc.nextInt();

        if (paginas > 0 && paginas < 1000) {
            return paginas;
        } else {
            System.out.println("El número de páginas debe ser un número entero entre 0 y 1000");
            return getPaginas(sc);
        }
    }

    /** Método que devuelve el título del libro
     *
     * @param sc
     * @return
     */

    private static String getTitulo(Scanner sc) {
        String titulo = sc.nextLine();
        while (titulo.isEmpty()) {
            System.out.println("El título no puede estar vacío");
            titulo = sc.nextLine();
        }
        return titulo;
    }

    /** Método que comprueba que la nacionalidad no esté vacía y contenga el listado de nacionalidades
     * @param sc
     * @return nacionalidad
     */

    private static String getNacionalidad(Scanner sc) {
        String[] nacionalidades = {"Española", "Inglesa", "Francesa", "Alemana", "Italiana", "Americana"};
        String nacionalidad = sc.nextLine();
        while (!Arrays.asList(nacionalidades).contains(nacionalidad)) {
            System.out.println("Nacionalidad no válida, solo se admiten: " + Arrays.toString(nacionalidades));
            System.out.println("Introduzca la nacionalidad del autor: ");
            nacionalidad = sc.nextLine();
        }
        return nacionalidad;
    }
    /** Método que comprueba que las iniciales del autor:
     *  - No estén vacías
     *  - Tengan un máximo de 3 caracteres
     *  - No contengan números
     *  - No contengan espacios
     *
     * @param sc
     * @return iniciales
     */

    private static String getIniciales(Scanner sc) {
        String iniciales = sc.nextLine();
        while (iniciales.length() != 3 || !iniciales.matches("[A-Z]+") || iniciales.contains(" ")) {
            System.out.println("Las iníciales deben ser 3 letras mayúsculas, sin espacios ni tabuladores y sin " +
                    "caracteres especiales");
            iniciales = sc.nextLine();
        }
        return iniciales;
    }



    /**
     * Método que comprueba que el nombre introducido:
     *  - No esté vacío
     *  - No contenga números
     *  - No contenga caracteres especiales
     *  - No sea menor de 3 caracteres
     *  - NO sea mayor a 50 caracteres
     *
     * @param sc
     * @return nombre
     */
    private static String getNombre(Scanner sc) {
        String nombre = sc.nextLine();
        while (nombre.matches(".*\\d.*") || nombre.length() < 3 || nombre.length() > 50) {
            System.out.println("El nombre no puede estar vacío, no puede contener números, debe tener entre " +
                    "3 y 50 caracteres");
            nombre = sc.nextLine();
        }
        return nombre;
    }
}
