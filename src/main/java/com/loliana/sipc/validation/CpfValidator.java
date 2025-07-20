package com.loliana.sipc.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<CPF, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.isEmpty()) {
            return true; // @NotBlank ou @NotNull cuida se for obrigatório
        }

        cpf = cpf.replaceAll("[^\\d]", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        return isCpfValid(cpf);
    }

    private boolean isCpfValid(String cpf) {
        try {
            int d1 = 0, d2 = 0;
            for (int i = 0; i < 9; i++) {
                int digito = cpf.charAt(i) - '0';
                d1 += digito * (10 - i);
                d2 += digito * (11 - i);
            }

            int resto = d1 % 11;
            int check1 = (resto < 2) ? 0 : 11 - resto;
            d2 += check1 * 2;

            resto = d2 % 11;
            int check2 = (resto < 2) ? 0 : 11 - resto;

            return check1 == (cpf.charAt(9) - '0') && check2 == (cpf.charAt(10) - '0');
        } catch (Exception e) {
            return false;
        }
    }
}