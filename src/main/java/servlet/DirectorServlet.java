package servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
import dto.DirectorDto;
import exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.DirectorService;
import servlet.util.ServletsUtil;
import validator.ErrorValidation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import static servlet.util.ServletsUtil.*;


@WebServlet("/directors/*")
public class DirectorServlet extends HttpServlet {

    private static final String CONTENT_TYPE = "application/json";

    private final transient DirectorService directorService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DirectorServlet() {
        this.directorService = new DirectorService();
    }

    public DirectorServlet(DirectorService directorService) {
        this.directorService = directorService;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = getId(req);

        PrintWriter writer = resp.getWriter();
        resp.setContentType(CONTENT_TYPE);

        if (id == -1) {

            List<DirectorDto> directorDtos = directorService.findAll();

            writer.write(objectMapper.writeValueAsString(directorDtos));

        } else {
            Optional<DirectorDto> directorDto = directorService.findById(id);

            if (directorDto.isPresent()) {
                writer.write(objectMapper.writeValueAsString(directorDto.get()));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = ServletsUtil.extractBody(req);

        PrintWriter writer = resp.getWriter();

        resp.setContentType(CONTENT_TYPE);

        try {
            if (!body.isEmpty()) {

                DirectorDto directorDto = objectMapper.readValue(body, DirectorDto.class);

                DirectorDto createdDirector = directorService.create(directorDto);

                writer.write(objectMapper.writeValueAsString(createdDirector));

            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (ValidationException error) {
            for (ErrorValidation err : error.getErrors()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                writer.write(objectMapper.writeValueAsString(err));
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = ServletsUtil.getId(req);

        if (id == -1) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            boolean statusDelete = directorService.delete(id);
            if (statusDelete) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = getId(req);

        String body = extractBody(req);
        PrintWriter writer = resp.getWriter();
        resp.setContentType(CONTENT_TYPE);

        try {
            if (!body.isEmpty() && id != -1) {

                DirectorDto updateDirector = objectMapper.readValue(body, DirectorDto.class);

                updateDirector.setId(id);
                writer.write(objectMapper.writeValueAsString(directorService.update(updateDirector)));
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
}
