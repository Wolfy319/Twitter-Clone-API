package com.cooksys.socialmedia.controllers.advice;

import com.cooksys.socialmedia.dtos.ErrorDto;
import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@org.springframework.web.bind.annotation.ControllerAdvice(basePackages = {"com.cooksys.socialmedia.controllers"})
public class SocialMediaControllerAdvice {

    /**
     * Handles and responds to BadRequestException by returning an ErrorDto with the exception message.
     *
     * @param request             The HTTP servlet request associated with the exception.
     * @param badRequestException The BadRequestException instance.
     * @return An ErrorDto containing the error message.
     * @throws BadRequestException If there's an issue with handling the bad request.
     */

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorDto handleBadRequestException(HttpServletRequest request, BadRequestException badRequestException) {
        return new ErrorDto(badRequestException.getMessage());
    }

    /**
     * Handles and responds to NotFoundException by returning an ErrorDto with the exception message.
     *
     * @param request           The HTTP servlet request associated with the exception.
     * @param notFoundException The NotFoundException instance.
     * @return An ErrorDto containing the error message.
     * @throws NotFoundException If there's an issue with handling the not found request.
     */

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorDto handleNotFoundException(HttpServletRequest request, NotFoundException notFoundException) {
        return new ErrorDto(notFoundException.getMessage());
    }

    /**
     * Handles and responds to NotAuthorizedException by returning an ErrorDto with the exception message.
     *
     * @param request                The HTTP servlet request associated with the exception.
     * @param notAuthorizedException The NotAuthorizedException instance.
     * @return An ErrorDto containing the error message.
     * @throws NotAuthorizedException If there's an issue with handling the unauthorized request.
     */

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAuthorizedException.class)
    public ErrorDto handleNotAuthorizedException(HttpServletRequest request, NotAuthorizedException notAuthorizedException) {
        return new ErrorDto(notAuthorizedException.getMessage());
    }
}