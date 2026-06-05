package cl.usm.gestionPeliculasMemoria.services;

import cl.usm.gestionPeliculasMemoria.entities.Comentario;
import cl.usm.gestionPeliculasMemoria.entities.Pelicula;
import cl.usm.gestionPeliculasMemoria.repositories.PeliculasRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PeliculasServiceImplTest {


    @Mock
    PeliculasRepository peliculasRepository;

    @InjectMocks
    PeliculasServiceImpl peliculasService;


    @Test
    void createPelicula_ok() {
        Comentario[] comentarios = {
                new Comentario("Juan", "Muy buena película")
        };

        Pelicula pelicula = new Pelicula(
                "1",
                "Cars",
                "Nolan",
                null,
                comentarios
        );

        when(peliculasRepository.insert(any(Pelicula.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Pelicula resultado = peliculasService.createPelicula(pelicula);

        assertNotNull(resultado);
        assertNotNull(resultado.getTokenDescarga());
        assertEquals(10, resultado.getTokenDescarga().length());

    }

    @Test
    void createPelicula_Ex() {

        Pelicula pelicula = new Pelicula(
                "1",
                "Cars",
                "Nolan",
                null,
                null
        );

        when(peliculasRepository.insert(any(Pelicula.class)))
                .thenThrow(new RuntimeException("Error"));

        Pelicula resultado = peliculasService.createPelicula(pelicula);

        assertNull(resultado);

        verify(peliculasRepository, times(1))
                .insert(any(Pelicula.class));
    }

    @Test
    void getAll_ok() {

        Pelicula pelicula = new Pelicula();
        pelicula.setId("1");
        pelicula.setTitulo("Cars");

        when(peliculasRepository.findById("1"))
                .thenReturn(pelicula);

        Pelicula resultado = peliculasService.findById("1");

        assertNotNull(resultado);
        assertEquals("Cars", resultado.getTitulo());

        verify(peliculasRepository).findById("1");
    }

    @Test
    void findById_ok() {
        Pelicula pelicula = new Pelicula();
        pelicula.setId("1");
        pelicula.setTitulo("Cars");

        when(peliculasRepository.findById("1"))
                .thenReturn(pelicula);

        Pelicula resultado = peliculasService.findById("1");

        assertNotNull(resultado);
        assertEquals("Cars", resultado.getTitulo());

        verify(peliculasRepository).findById("1");

    }

    @Test
    void filter_ok() {
        Pelicula p1 = new Pelicula();
        p1.setId("1");
        p1.setTitulo("Cars");

        Pelicula p2 = new Pelicula();
        p2.setId("2");
        p2.setTitulo("Avatar");

        Pelicula p3 = new Pelicula();
        p3.setId("3");
        p3.setTitulo("Shrek");

        when(peliculasRepository.findAll())
                .thenReturn(Arrays.asList(p1, p2, p3));

        List<Pelicula> resultado = peliculasService.filter("Cars");

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream()
                .allMatch(p -> p.getTitulo().toLowerCase().contains("Cars")));

    }
}