package uz.jamshid.java.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.sap.cloud.security.xsuaa.token.Token;
import org.springframework.web.context.annotation.RequestScope;
import uz.jamshid.java.exception.NotAuthorizedException;

@Component
@RequestScope
@RestController
@RequestMapping(path = "")
public class MainController {

    @GetMapping(path = "/")
    public ResponseEntity<String> readAll() {
        return new ResponseEntity<String>("Hello Jamshid!", HttpStatus.OK);
    }
}
