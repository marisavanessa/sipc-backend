package com.loliana.sipc.exception;

public class TypeEstablishmentNotFoundException extends RuntimeException {
  public TypeEstablishmentNotFoundException(Integer id) {
    super("Tipo de estabelecimento não encontrado para o ID: " + id);
  }
}
