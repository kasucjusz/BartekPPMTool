package com.springreactproject.ppmtool.validator;


import com.springreactproject.ppmtool.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        User user=(User) o;

        if(user.getPassword().length()<6){//w rejectValue password musi byc taka sama nazwa jak w klasie modelowej
            errors.rejectValue("password", "Length", "Password must be at least 6 characters long");
        }
        if(!user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("confirmPassword", "Match", "Passwords must match");
        }

    }
}
