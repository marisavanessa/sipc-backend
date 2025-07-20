package com.loliana.sipc.exception;

public class DuplicateNameException extends RuntimeException {
    public DuplicateNameException(String name) {
        super("Já existe um procedimento com o nome: " + name.toUpperCase());
    }
}

