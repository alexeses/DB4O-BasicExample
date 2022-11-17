package com.github.alexeses.db;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.github.alexeses.pojo.Autor;
import com.github.alexeses.pojo.Libro;

import java.util.ArrayList;

public class Access {

    static final String PATH_DB = "src/main/resources/biblioteca.yap";
    ObjectContainer db;

    public Access() {
        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), PATH_DB);
    }

    /**
     * Este método cierra la conexión con la base de datos
     */
    public void close() {
        System.out.println("[!] Closing database");
        db.close();
    }

    /**
     * Este método inserta un autor con su libro en la base de datos y comprueba que este no exista mediante
     * las iniciales del mismo.
     *
     * @param autor
     * @param libro
     */
    public void insertarAutorLibro(String iniciales, String nombre, String nacionalidad, String titulo, int paginas) {

        if (db.queryByExample(new Autor(iniciales, null, null, null)).size() == 0) {
            Libro libro = new Libro(titulo, paginas);
            Autor autor = new Autor(iniciales, nombre, nacionalidad, libro);

            db.store(autor);
            System.out.println("[+] Autor " + nombre + " insertado correctamente");
        } else {
            System.out.println("[!] El autor " + nombre + " ya existe");
        }

    }

    /**
     * Este método consulta todos los autores que hayan escrito libros que tengan más de N páginas
     *
     * @param n // Número de páginas
     */
    public ArrayList<Autor> consultarPorPagina(int n) {

        ArrayList<Autor> autores = new ArrayList<>();
        ObjectSet<Autor> result = db.queryByExample(Autor.class);

        for (Autor autor : result) {
            if (autor.getLibro().getPaginas() > n) {
                autores.add(autor);
            }
        }

        return autores;
    }

    /**
     * Este método realiza una consulta general que devuelve todos los autores con sus correspondientes libros.
     */
    public ArrayList<Autor> consularTodas(){

        ArrayList<Autor> autores = new ArrayList<>();
        ObjectSet<Autor> result = db.queryByExample(Autor.class);

        while (result.hasNext()) {
            autores.add(result.next());
        }

        return autores;
    }

    /**
     * Este método modifica el número de páginas de un libro ya existente en la base de datos
     *
     * @param titulo
     * @param paginas
     */
    public void modificarPaginasLibro(String titulo, int paginas) {

        Libro libro = new Libro(titulo, paginas);
        ObjectSet<Libro> result = db.queryByExample(libro);

        if (result.hasNext()) {
            Libro libroAModificar = result.next();
            libroAModificar.setPaginas(paginas);
            db.store(libroAModificar);
        }
    }
}