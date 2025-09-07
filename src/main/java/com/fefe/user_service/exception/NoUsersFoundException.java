package com.fefe.user_service.exception;

public class NoUsersFoundException extends RuntimeException{
    public NoUsersFoundException() {
        super("Nie znaleziono żadnych użytkowaników");
    }
}
