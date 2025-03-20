package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.FilmDto;
import exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.FilmService;
import validator.ErrorValidation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static servlet.util.ServletsUtil.*;

@WebServlet("/films/*")
public class FilmServlet extends HttpServlet {

    private static final String CONTENT_TYPE = "application/json";

    private final transient FilmService filmService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FilmServlet() {
        this.filmService = new FilmService();
    }

    public FilmServlet(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType(CONTENT_TYPE);
        PrintWriter writer = resp.getWriter();
        try {

            Long id = getId(req);

            Optional<FilmDto> filmDto = filmService.findById(id);

            if (filmDto.isPresent()) {

                writer.write(objectMapper.writeValueAsString(filmDto.get()));

            } else {

                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

            }
        } catch (NumberFormatException e) {

            List<FilmDto> filmDtos = filmService.findAll();

            writer.write(objectMapper.writeValueAsString(filmDtos));

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String body = extractBody(req);
        PrintWriter writer = resp.getWriter();
        resp.setContentType(CONTENT_TYPE);

        try {
            Long id = getId(req);
            if (!body.isEmpty()) {

                FilmDto updateFilm = objectMapper.readValue(body, FilmDto.class);

                updateFilm.setId(id);

                writer.write(objectMapper.writeValueAsString(filmService.update(updateFilm)));

            } else {

                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

            }
        } catch (ValidationException e) {
            for (ErrorValidation error : e.getErrors()) {

                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                writer.write(objectMapper.writeValueAsString(error));

            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String body = extractBody(req);

        PrintWriter writer = resp.getWriter();
        resp.setContentType(CONTENT_TYPE);

        try {

            if (!body.isEmpty()) {

                FilmDto filmDto = objectMapper.readValue(body, FilmDto.class);

                writer.write(objectMapper.writeValueAsString(filmService.create(filmDto)));

            } else {

                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

            }
        } catch (ValidationException e) {
            for (ErrorValidation error : e.getErrors()) {

                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                writer.write(objectMapper.writeValueAsString(error));

            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        try {
            Long id = getId(req);

            boolean statusDelete = filmService.delete(id);

            if (statusDelete) {

                resp.setStatus(HttpServletResponse.SC_OK);

            } else {

                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

            }
        } catch (NumberFormatException e){

            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
