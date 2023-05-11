package com.umd.sdlc.example.sdlc_project.controllers;

import java.util.Map;

import com.umd.sdlc.example.sdlc_project.models.User;
import com.umd.sdlc.example.sdlc_project.models.UserDetails;
import com.umd.sdlc.example.sdlc_project.query.UserDetailsRepository;
import com.umd.sdlc.example.sdlc_project.query.UserRepository;

import java.io.IOException;
import java.io.OutputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class NonSpringController extends HttpServlet {
    
    private UserDetailsRepository userDetailsRepository;
    private UserRepository userRepository;
    
    public NonSpringController(UserRepository userRepository, UserDetailsRepository userDetailsRepository) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();

        long id = Long.parseLong(parameterMap.get("id")[0]);
        UserDetails userDetails = userDetailsRepository.getUserDetailsById(id);
        
        response.setStatus(HttpServletResponse.SC_OK);
        sendServerResponse(response, userDetails.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        long userId = Long.parseLong(parameterMap.get("userId")[0]);
        int age = Integer.parseInt(parameterMap.get("age")[0]);
        String birthday = parameterMap.get("birthday")[0];
        String address = parameterMap.get("address")[0];


        User user = userRepository.getReferenceById(userId);
        if(user == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            sendServerResponse(response, "invalid user");
            return;
        }

        userDetailsRepository.insertNewUserDetails(user.getId(), age, birthday, address);
        response.setStatus(HttpServletResponse.SC_OK);
        sendServerResponse(response, "saved");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        long id = Long.parseLong(parameterMap.get("id")[0]);
        long age = Long.parseLong(parameterMap.get("age")[0]);

        userDetailsRepository.updateUserDetailsAge(id, age);
        response.setStatus(HttpServletResponse.SC_OK);
        sendServerResponse(response, "updated");
    }

    /**
     * Writes a response back, purposely trying to expose exceptions
     * @param httpServletResponse
     * @param response
     */
    private void sendServerResponse(HttpServletResponse httpServletResponse, String response) {
        try {
            OutputStream outputStream = httpServletResponse.getOutputStream();
            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (Exception exception) {
            throw new RuntimeException(exception.getLocalizedMessage());
        }
    }
}
