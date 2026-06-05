package cl.usm.gestionPeliculasMemoria.repositories;

import cl.usm.gestionPeliculasMemoria.entities.Pelicula;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PeliculasRepositoryImplTest {

    private PeliculasRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new PeliculasRepositoryImpl();
    }

    @Test
    void insert() {

        Pelicula pelicula = new Pelicula("1", "Cars", "Nolan", null, null);

        Pelicula resultado = repository.insert(pelicula);

        assertNotNull(resultado);
        assertEquals("1", resultado.getId());
        assertEquals("Cars", resultado.getTitulo());
    }

    @Test
    void insert_ShouldThrowException_WhenIdIsNull() {

        Pelicula pelicula = new Pelicula(null, "Cars", "Nolan", null, null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> repository.insert(pelicula));

        assertEquals("El ID de la pelicula no puede ser nulo", exception.getMessage());
    }

    @Test
    void insert_ShouldThrowException_WhenIdAlreadyExists() {
        Pelicula pelicula1 = new Pelicula("1", "Cars", "Nolan", null, null);
        Pelicula pelicula2 = new Pelicula("1", "Avatar", "James Cameron", null, null);
        repository.insert(pelicula1);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> repository.insert(pelicula2));
        assertEquals("La pelicula con ID 1 ya existe", exception.getMessage());
    }

    @Test
    void findAll() {

        repository.insert(
                new Pelicula("1", "Cars", "Nolan", null, null)
        );

        repository.insert(
                new Pelicula("2", "Shrek", "Escorcese", null, null)
        );

        List<Pelicula> peliculas = repository.findAll();

        assertNotNull(peliculas);
        assertEquals(2, peliculas.size());
    }

    @Test
    void findAll_ShouldReturnCopyOfStorage() {

        repository.insert(
                new Pelicula("1", "Cars", "Nolan", null, null)
        );

        List<Pelicula> peliculas = repository.findAll();

        peliculas.clear();

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void findById() {

        Pelicula pelicula = new Pelicula("1", "Cars", "Nolan", null, null);

        repository.insert(pelicula);

        Pelicula resultado = repository.findById("1");

        assertNotNull(resultado);
        assertEquals("1", resultado.getId());
        assertEquals("Cars", resultado.getTitulo());
    }

    @Test
    void findById_ShouldIgnoreCase() {
        Pelicula pelicula = new Pelicula("ABC123", "Cars", "Nolan", null, null);
        repository.insert(pelicula);
        Pelicula resultado = repository.findById("abc123");
        assertNotNull(resultado);
        assertEquals("ABC123", resultado.getId());
    }

    @Test
    void findById_ShouldReturnNull_WhenIdIsNull() {

        Pelicula resultado = repository.findById(null);
        assertNull(resultado);
    }

    @Test
    void findById_ShouldReturnNull_WhenMovieDoesNotExist() {

        Pelicula resultado = repository.findById("999");
        assertNull(resultado);
    }
}