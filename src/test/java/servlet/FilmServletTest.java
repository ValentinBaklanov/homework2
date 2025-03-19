package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.FilmDto;
import exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import service.FilmService;
import validator.ErrorValidation;

import java.io.*;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ObjectsForTest.*;


@ExtendWith(MockitoExtension.class)
class FilmServletTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    FilmService filmService;
    @InjectMocks
    FilmServlet filmServlet;

    @Test
    void doGetIsOkAll() throws IOException, ServletException {

        List<FilmDto> filmDtos = List.of(FILM_DTO_1, FILM_DTO_2);

        StringWriter stringWriter = new StringWriter();

        Mockito.doReturn("/films/").when(request).getRequestURI();
        Mockito.doReturn(new PrintWriter(stringWriter)).when(response).getWriter();
        Mockito.doReturn(filmDtos).when(filmService).findAll();

        filmServlet.doGet(request, response);

        assertThat(stringWriter.toString()).isEqualTo(objectMapper.writeValueAsString(filmDtos));

    }

    @Test
    void doGetIsOkById() throws IOException, ServletException {

        StringWriter stringWriter = new StringWriter();

        Mockito.doReturn("/films/2").when(request).getRequestURI();
        Mockito.doReturn(new PrintWriter(stringWriter)).when(response).getWriter();
        Mockito.doReturn(Optional.of(FILM_DTO_1)).when(filmService).findById(2L);

        filmServlet.doGet(request, response);

        assertThat(stringWriter.toString()).isEqualTo(objectMapper.writeValueAsString(FILM_DTO_1));

    }

    @Test
    void doGetNotIsOkById() throws IOException, ServletException {

        StringWriter stringWriter = new StringWriter();

        Mockito.doReturn("/films/2").when(request).getRequestURI();
        Mockito.doReturn(new PrintWriter(stringWriter)).when(response).getWriter();
        Mockito.doReturn(Optional.empty()).when(filmService).findById(2L);

        filmServlet.doGet(request, response);

        Mockito.verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);

    }

    @Test
    void doPostIsOk() throws IOException, ServletException {

        String json = objectMapper.writeValueAsString(FILM_DTO_1);

        StringWriter stringWriter = new StringWriter();
        StringReader stringReader = new StringReader(json);

        Mockito.doReturn("/films/2").when(request).getRequestURI();
        Mockito.doReturn(new BufferedReader(stringReader)).when(request).getReader();
        Mockito.doReturn(new PrintWriter(stringWriter)).when(response).getWriter();
        Mockito.doReturn(FILM_DTO_1).when(filmService).update(FILM_DTO_1);

        filmServlet.doPost(request, response);

        assertThat(json).isEqualTo(stringWriter.toString());


    }

    @Test
    void doPostIsNotOkBody() throws IOException, ServletException {


        StringWriter stringWriter = new StringWriter();
        StringReader stringReader = new StringReader("");

        Mockito.doReturn("/films/2").when(request).getRequestURI();
        Mockito.doReturn(new BufferedReader(stringReader)).when(request).getReader();
        Mockito.doReturn(new PrintWriter(stringWriter)).when(response).getWriter();


        filmServlet.doPost(request, response);

        Mockito.verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);


    }

    @Test
    void doPostWithValidationException() throws IOException, ServletException {

        String json = objectMapper.writeValueAsString(FILM_DTO_1);
        String jsonException = objectMapper.writeValueAsString(
                new ErrorValidation("error.nameFilm", "Name Film is invalid"));

        StringWriter stringWriter = new StringWriter();
        StringReader stringReader = new StringReader(json);

        Mockito.doReturn("/films/2").when(request).getRequestURI();
        Mockito.doReturn(new BufferedReader(stringReader)).when(request).getReader();
        Mockito.doReturn(new PrintWriter(stringWriter)).when(response).getWriter();
        Mockito.doThrow(new ValidationException(List.of(new ErrorValidation("error.nameFilm", "Name Film is invalid"))))
                .when(filmService).update(FILM_DTO_1);

        filmServlet.doPost(request, response);

        Mockito.verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);
        assertThat(jsonException).isEqualTo(stringWriter.toString());

    }

    @Test
    void doPutIsOk() throws IOException, ServletException {

        String json = objectMapper.writeValueAsString(FILM_DTO_2);

        StringWriter stringWriter = new StringWriter();
        StringReader stringReader = new StringReader(json);

        Mockito.doReturn(new BufferedReader(stringReader)).when(request).getReader();
        Mockito.doReturn(new PrintWriter(stringWriter)).when(response).getWriter();
        Mockito.doReturn(FILM_DTO_2).when(filmService).create(FILM_DTO_2);

        filmServlet.doPut(request, response);

        assertThat(json).isEqualTo(stringWriter.toString());

    }

    @Test
    void doPutWhenIsNotOk() throws IOException, ServletException {


        StringWriter stringWriter = new StringWriter();
        StringReader stringReader = new StringReader("");

        Mockito.doReturn(new BufferedReader(stringReader)).when(request).getReader();
        Mockito.doReturn(new PrintWriter(stringWriter)).when(response).getWriter();

        filmServlet.doPut(request, response);

        Mockito.verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);

    }

    @Test
    void doPutWithValidationException() throws IOException, ServletException {

        String json = objectMapper.writeValueAsString(FILM_DTO_2);
        String jsonException = objectMapper.writeValueAsString(
                new ErrorValidation("error.nameFilm", "Name Film is invalid"));

        StringWriter stringWriter = new StringWriter();
        StringReader stringReader = new StringReader(json);

        Mockito.doReturn(new BufferedReader(stringReader)).when(request).getReader();
        Mockito.doReturn(new PrintWriter(stringWriter)).when(response).getWriter();
        Mockito.doThrow(new ValidationException(List.of(new ErrorValidation("error.nameFilm", "Name Film is invalid"))))
                .when(filmService).create(FILM_DTO_2);

        filmServlet.doPut(request, response);

        Mockito.verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);
        assertThat(jsonException).isEqualTo(stringWriter.toString());

    }

    @Test
    void doDeleteWhenIsOK() throws ServletException, IOException {
        Mockito.doReturn("/directors/1").when(request).getRequestURI();
        Mockito.doReturn(true).when(filmService).delete(1L);

        filmServlet.doDelete(request, response);

        Mockito.verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void doDeleteWhenIsNotOKByID() throws ServletException, IOException {
        Mockito.doReturn("/directors/").when(request).getRequestURI();


        filmServlet.doDelete(request, response);

        Mockito.verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void doDeleteWhenIsNotOKByDelete() throws ServletException, IOException {
        Mockito.doReturn("/directors/1").when(request).getRequestURI();
        Mockito.doReturn(false).when(filmService).delete(1L);

        filmServlet.doDelete(request, response);

        Mockito.verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

}