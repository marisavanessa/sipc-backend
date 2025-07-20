package com.loliana.sipc.exception;

public class TypeProcedureNotFoundException extends RuntimeException {
    public TypeProcedureNotFoundException(Integer id) {
        super("Tipo de procedimento não encontrado para o ID: " + id);
    }
}