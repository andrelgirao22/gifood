package br.com.alg.giraofoodapi.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
        this.valorField = constraintAnnotation.valorField();
        this.descricaoField =  constraintAnnotation.descricaoField();
        this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean isValido = true;

        try {
            BigDecimal valor =
                    (BigDecimal) BeanUtils.getPropertyDescriptor(value.getClass(), this.valorField).getReadMethod().invoke(value);
            String descricao = (String) BeanUtils.getPropertyDescriptor(value.getClass(), this.descricaoField).getReadMethod().invoke(value);

            if(valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
                isValido = descricao.toLowerCase().equals(this.descricaoObrigatoria);
            }
            return isValido;
        } catch (Exception e)  {
            throw new ValidationException(e);
        }
    }
}
