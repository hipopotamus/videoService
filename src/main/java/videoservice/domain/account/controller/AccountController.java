package videoservice.domain.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import videoservice.domain.account.dto.AccountAddRequest;
import videoservice.domain.account.service.AccountService;
import videoservice.global.dto.IdDto;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<IdDto> accountAdd(@RequestBody AccountAddRequest accountAddRequest) {
        IdDto idDto = accountService.addAccount(accountAddRequest);
        return new ResponseEntity<>(idDto, HttpStatus.CREATED);
    }
}
