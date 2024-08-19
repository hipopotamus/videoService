package videoservice.domain.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import videoservice.domain.account.dto.AccountAddRequest;
import videoservice.domain.account.dto.AccountDetailResponse;
import videoservice.domain.account.dto.AccountModifyRequest;
import videoservice.domain.account.service.AccountService;
import videoservice.global.argumentresolver.LoginId;
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

    @GetMapping
    public ResponseEntity<AccountDetailResponse> profileDetails(@LoginId Long loginId) {

        AccountDetailResponse accountDetailResponse = accountService.findProfile(loginId);

        return new ResponseEntity<>(accountDetailResponse, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<IdDto> accountModify(@LoginId Long loginId,
                                               @RequestBody AccountModifyRequest accountModifyRequest) {

        IdDto idDto = accountService.modifyAccount(loginId, accountModifyRequest);

        return new ResponseEntity<>(idDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> accountDelete(@LoginId Long loginId) {

        accountService.deleteAccount(loginId);

        return new ResponseEntity<>("Account successfully deleted", HttpStatus.OK);
    }

}
