package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.DirectorDto;
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
import service.DirectorService;
import validator.ErrorValidation;

import java.io.*;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ObjectsForTest.*;

@ExtendWith(MockitoExtension.class)
class DirectorServletTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    DirectorService directorService;
    @InjectMocks
    DirectorServlet directorServlet;

    @Test
    void doGetTestFindAll() throws IOException, ServletException {

        DirectorDto directorDto1 = DIRECTOR_DTO_1;
        DirectorDto directorDto2 = DIRECTOR_DTO_2;
        directorDto1.getFilmsDto().add(FILM_DTO_1);
        directorDto2.getFilmsDto().add(FILM_DTO_2);

        List<DirectorDto> listDirectors = List.of(
                directorDto1,
                directorDto2
        );

        StringWriter stringWriter = new StringWriter();

        Mockito.doReturn("/directors/").when(request).getRequestURI();
        Mockito.doReturn(new PrintWriter(stringWriter)).when(response).getWriter();
        Mockito.doReturn(listDirectors).when(directorService).findAll();

        directorServlet.doGet(request, response);

        assertThat(stringWriter.toString()).isEqualTo(objectMapper.writeValueAsString(listDirectors));

    }

    @Test
    void doGetTestFindByIDIsOk() throws IOException, ServletException {

        DirectorDto directorDto1 = DIRECTOR_DTO_1;
        directorDto1.getFilmsDto().add(FILM_DTO_1);


        StringWriter stringWriter = new StringWriter();

        Mockito.doReturn("/directors/1").when(request).getRequestURI();
        Mockito.doReturn(new PrintWriter(stringWriter)).when(response).getWriter();
        Mockito.doReturn(Optional.of(directorDto1)).when(directorService).findById(1L);


        directorServlet.doGet(request, response);


        assertThat(stringWriter.toString()).isEqualTo(objectMapper.writeValueAsString(directorDto1));

    }

    @Test
    void doGetTestFindByIDIsNotOk() throws IOException, ServletException {


        StringWriter stringWriter = new StringWriter();

        Mockito.doReturn("/directors/1").when(request).getRequestURI();
        Mockito.doReturn(new PrintWriter(stringWriter)).when(response).getWriter();
        Mockito.doReturn(Optional.empty()).when(directorService).findById(1L);

        directorServlet.doGet(request, response);

        Mockito.verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);

    }

    @Test
    void doPutWhenOk() throws IOException, ServletException {

        String json = objectMapper.writeValueAsString(DIRECTOR_DTO_1);

        StringWriter stringWriter = new StringWriter();
        StringReader stringReader = new StringReader(json);

        Mockito.doReturn(new BufferedReader(stringReader)).when(request).getReader();
        Mockito.doReturn(new PrintWriter(stringWriter)).when(response).getWriter();
        Mockito.doReturn(DIRECTOR_DTO_1).when(directorService).create(DIRECTOR_DTO_1);

        directorServlet.doPut(request, response);

        assertThat(json).isEqualTo(stringWriter.toString());

    }

    @Test
    void doPutWhenIsNotOk() throws IOException, ServletException {


        StringWriter stringWriter = new StringWriter();
        StringReader stringReader = new StringReader("");

        Mockito.doReturn(new BufferedReader(stringReader)).when(request).getReader();
        Mockito.doReturn(new PrintWriter(stringWriter)).when(response).getWriter();

        directorServlet.doPut(request, response);

        Mockito.verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);

    }

    @Test
    void doPutWhenValidationExeption() throws IOException, ServletException {

        String json = objectMapper.writeValueAsString(DIRECTOR_DTO_1);
        String jsonException = objectMapper.writeValueAsString(
                new ErrorValidation("error.nameDirector", "Name director is invalid"));

        StringWriter stringWriter = new StringWriter();
        StringReader stringReader = new StringReader(json);

        Mockito.doReturn(new BufferedReader(stringReader)).when(request).getReader();
        Mockito.doReturn(new PrintWriter(stringWriter)).when(response).getWriter();
        Mockito.doThrow(new ValidationException(List.of(new ErrorValidation("error.nameDirector", "Name director is invalid"))))
                .when(directorService).create(DIRECTOR_DTO_1);

        directorServlet.doPut(request, response);

        Mockito.verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);
        assertThat(jsonException).isEqualTo(stringWriter.toString());

    }

    @Test
    void doDeleteWhenIsOK() throws ServletException, IOException {
        Mockito.doReturn("/directors/1").when(request).getRequestURI();
        Mockito.doReturn(true).when(directorService).delete(1L);

        directorServlet.doDelete(request, response);

        Mockito.verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void doDeleteWhenIsNotOKByID() throws ServletException, IOException {
        Mockito.doReturn("/directors/").when(request).getRequestURI();


        directorServlet.doDelete(request, response);

        Mockito.verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void doDeleteWhenIsNotOKByDelete() throws ServletException, IOException {
        Mockito.doReturn("/directors/1").when(request).getRequestURI();
        Mockito.doReturn(false).when(directorService).delete(1L);

        directorServlet.doDelete(request, response);

        Mockito.verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void doPostWhenOk() throws IOException, ServletException {


        String json = objectMapper.writeValueAsString(DIRECTOR_DTO_1);

        StringWriter stringWriter = new StringWriter();
        StringReader stringReader = new StringReader(json);

        Mockito.doReturn("/directors/4").when(request).getRequestURI();
        Mockito.doReturn(new BufferedReader(stringReader)).when(request).getReader();
        Mockito.doReturn(new PrintWriter(stringWriter)).when(response).getWriter();
        Mockito.doReturn(DIRECTOR_DTO_1).when(directorService).update(DIRECTOR_DTO_1);

        directorServlet.doPost(request, response);

        assertThat(json).isEqualTo(stringWriter.toString());

    }

    @Test
    void doPostWhenIsNotOkBody() throws IOException, ServletException {


        StringWriter stringWriter = new StringWriter();
        StringReader stringReader = new StringReader("");

        Mockito.doReturn("/directors/4").when(request).getRequestURI();
        Mockito.doReturn(new BufferedReader(stringReader)).when(request).getReader();
        Mockito.doReturn(new PrintWriter(stringWriter)).when(response).getWriter();

        directorServlet.doPost(request, response);

        Mockito.verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);

    }


    @Test
    void doPostWithValidationException() throws IOException, ServletException {

        String json = objectMapper.writeValueAsString(DIRECTOR_DTO_1);
        String jsonException = objectMapper.writeValueAsString(
                new ErrorValidation("error.nameDirector", "Name director is invalid"));

        StringWriter stringWriter = new StringWriter();
        StringReader stringReader = new StringReader(json);

        Mockito.doReturn("/directors/4").when(request).getRequestURI();
        Mockito.doReturn(new BufferedReader(stringReader)).when(request).getReader();
        Mockito.doReturn(new PrintWriter(stringWriter)).when(response).getWriter();
        Mockito.doThrow(new ValidationException(List.of(new ErrorValidation("error.nameDirector", "Name director is invalid"))))
                .when(directorService).update(DIRECTOR_DTO_1);

        directorServlet.doPost(request, response);

        Mockito.verify(response, Mockito.times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);
        assertThat(jsonException).isEqualTo(stringWriter.toString());

    }

}