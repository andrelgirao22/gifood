package br.com.alg.giraofoodapi.core.validation;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private String[] mediaType;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        mediaType = constraintAnnotation.allowed();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return multipartFile == null || Arrays.asList(mediaType).contains(multipartFile.getContentType());
    }


}
