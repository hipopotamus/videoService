package videoservice.domain.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import videoservice.domain.account.dto.AccountAddRequest;
import videoservice.domain.account.dto.AccountProfileResponse;
import videoservice.domain.account.dto.AccountIdResponse;
import videoservice.domain.account.dto.AccountModifyRequest;
import videoservice.domain.account.service.AccountService;
import videoservice.global.argumentresolver.LoginId;
import videoservice.global.dto.IdDto;
import videoservice.global.dto.PageDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<IdDto> accountAdd(@RequestBody AccountAddRequest accountAddRequest) {

        IdDto idDto = accountService.addAccount(accountAddRequest);

        return new ResponseEntity<>(idDto, HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    public ResponseEntity<AccountProfileResponse> profileDetails(@LoginId Long loginId) {

        AccountProfileResponse accountProfileResponse = accountService.findProfile(loginId);

        return new ResponseEntity<>(accountProfileResponse, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<IdDto> accountUpdate(@LoginId Long loginId,
                                               @RequestBody AccountModifyRequest accountModifyRequest) {

        IdDto idDto = accountService.updateAccount(loginId, accountModifyRequest);

        return new ResponseEntity<>(idDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> accountDelete(@LoginId Long loginId) {

        accountService.deleteAccount(loginId);

        return new ResponseEntity<>("Account successfully deleted", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageDto<AccountIdResponse>> accountIdList
            (@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        PageDto<AccountIdResponse> accountIdResponseList = accountService.findAccountIdList(pageable);

        return new ResponseEntity<>(accountIdResponseList, HttpStatus.OK);
    }

    @GetMapping("/cursor")
    public ResponseEntity<List<AccountIdResponse>> accountIdCursor(@RequestParam Long lastAccountId, @RequestParam int limit) {

        List<AccountIdResponse> accountIdCursor = accountService.findAccountIdCursor(lastAccountId, limit);

        return new ResponseEntity<>(accountIdCursor, HttpStatus.OK);
    }

}

