package com.cooksys.socialmedia.exceptions;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -6496940574625196392L;
    
    private String message;
}
