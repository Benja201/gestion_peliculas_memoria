package cl.usm.gestionPeliculasMemoria.controllers;

import cl.usm.gestionPeliculasMemoria.entities.Comentario;
import cl.usm.gestionPeliculasMemoria.entities.Pelicula;
import cl.usm.gestionPeliculasMemoria.services.PeliculasService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeliculasControllerTest {

    @Mock
    private PeliculasService peliculasService;

    @InjectMocks
    private PeliculasController peliculasController;

    @Test
    void getAll_ok() {

        Pelicula p1 = new Pelicula("1", "Cars", "Nolan", null, null);
        Pelicula p2 = new Pelicula("2", "Shrek", "Escorcese", null, null);

        when(peliculasService.getAll())
                .thenReturn(Arrays.asList(p1, p2));

        ResponseEntity<List<Pelicula>> response =
                peliculasController.getAll(null);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        verify(peliculasService).getAll();
    }

    @Test
    void testGetAll() {

        Pelicula p1 = new Pelicula("1", "Cars", "Nolan", null, null);

        when(peliculasService.filter("Cars"))
                .thenReturn(List.of(p1));

        ResponseEntity<List<Pelicula>> response =
                peliculasController.getAll("Cars");

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        verify(peliculasService).filter("Cars");
    }

    @Test
    void createPelicula_ok() {

        Pelicula pelicula =
                new Pelicula("1", "Cars", "Nolan", "ABC123", null);

        when(peliculasService.createPelicula(any(Pelicula.class)))
                .thenReturn(pelicula);

        ResponseEntity<?> response =
                peliculasController.createPelicula(pelicula);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(pelicula, response.getBody());

        verify(peliculasService).createPelicula(pelicula);
    }

    @Test
    void testCreatePelicula() {

        Pelicula pelicula =
                new Pelicula("1", "Cars", "Nolan", null, null);

        when(peliculasService.createPelicula(any(Pelicula.class)))
                .thenReturn(null);

        ResponseEntity<?> response =
                peliculasController.createPelicula(pelicula);

        assertEquals(500, response.getStatusCode().value());
    }

    @Test
    void findById() {

        Pelicula pelicula =
                new Pelicula("1", "Cars", "Nolan", null, null);

        when(peliculasService.findById("1"))
                .thenReturn(pelicula);

        ResponseEntity<Pelicula> response =
                peliculasController.findById("1");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(pelicula, response.getBody());

        verify(peliculasService).findById("1");
    }

    @Test
    void testFindById() {

        when(peliculasService.findById("999"))
                .thenReturn(null);

        ResponseEntity<Pelicula> response =
                peliculasController.findById("999");

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void getComentarios() {

        Comentario[] comentarios = {
                new Comentario("Juan", "Esta bomba"),
                new Comentario("Pedro", "Penca")
        };

        Pelicula pelicula =
                new Pelicula("1", "Cars", "Nolan", null, comentarios);

        when(peliculasService.findById("1"))
                .thenReturn(pelicula);

        ResponseEntity<?> response =
                peliculasController.getComentarios("1");

        assertEquals(200, response.getStatusCode().value());

        Comentario[] resultado =
                (Comentario[]) response.getBody();

        assertNotNull(resultado);
        assertEquals(2, resultado.length);
    }

    @Test
    void testGetComentarios() {

        when(peliculasService.findById("999"))
                .thenReturn(null);

        ResponseEntity<?> response =
                peliculasController.getComentarios("999");

        assertEquals(404, response.getStatusCode().value());
    }
}