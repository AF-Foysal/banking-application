package dev.affoysal.banking.Controller;

import dev.affoysal.banking.Entity.Account;
import dev.affoysal.banking.Exception.AccountNotFoundException;
import dev.affoysal.banking.Exception.DepositErrorException;
import dev.affoysal.banking.Exception.WithdrawErrorException;
import dev.affoysal.banking.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    //Add Account REST API
    @PostMapping
    public ResponseEntity<Account> addAccount(@RequestBody Account account) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(account));
    }

    //Retrieve Account REST API
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountByID(@PathVariable Long id) throws AccountNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccountByID(id));
    }

    // Deposit REST API
    @PatchMapping("/{id}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable Long id, @RequestBody Map<String, Double> request) throws AccountNotFoundException, DepositErrorException {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.deposit(id, request.get("amount")));
    }

    // Withdraw REST API
    @PatchMapping("/{id}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request) throws AccountNotFoundException, WithdrawErrorException {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.withdraw(id, request.get("amount")));
    }

    // Retrieve ALL Accounts REST API
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllAccounts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccountByID(@PathVariable Long id) throws AccountNotFoundException {
        accountService.deleteAccountByID(id);
        return ResponseEntity.status(HttpStatus.OK).body("Account Deletion Successful");
    }

    // Exception Handlers
    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String accountNotFoundHandler(AccountNotFoundException e) {
        return "Account with specified does NOT exist.";
    }

    @ExceptionHandler(DepositErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String depositErrorHandler(DepositErrorException e) {
        return "Deposit value is NOT valid.";
    }

    @ExceptionHandler(WithdrawErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String withdrawErrorHandler(WithdrawErrorException e) {
        return "Withdraw value is NOT valid.";
    }
}
