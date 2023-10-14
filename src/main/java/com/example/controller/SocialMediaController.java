package com.example.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import com.example.entity.*;
import com.example.repository.AccountRepository;

import java.util.List;
import com.example.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    AccountService accountService;

    @Autowired
    MessageService messageService;


    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> registerNewAccount(@RequestBody Account account) {
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            return ResponseEntity.status(400).body(account);
        }
        if (accountService.getAccountByUsername(account.getUsername()) != null) {
            return ResponseEntity.status(409).body(account);
        }
        return ResponseEntity.status(200).body(accountService.persistAccount(account));
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> login(@RequestBody Account account) {
        Account loginAccount = accountService.getAccountByUsername(account.getUsername());
        if (loginAccount == null) {
            return ResponseEntity.status(401).body(account);
        }
        if (!loginAccount.getPassword().equals(account.getPassword())) {
            return ResponseEntity.status(401).body(account);
        }
        return ResponseEntity.status(200).body(loginAccount);
    }

    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<Message> createNewMessage(@RequestBody Message message) {
        if (message.getMessage_text().isBlank() || message.getMessage_text().length() >= 255 || accountService.getAccountById(message.getPosted_by()) == null) {
            return ResponseEntity.status(400).body(message);
        }
        return ResponseEntity.status(200).body(messageService.persistMessage(message));
    }

    @GetMapping("/messages")
    public @ResponseBody ResponseEntity<List<Message>> retrieveAllMessages() {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("/messages/{message_id}")
    public @ResponseBody ResponseEntity<Message> retrieveMessageByID(@PathVariable int message_id) {
        return ResponseEntity.status(200).body(messageService.getMessageById(message_id));
    }

    @DeleteMapping("/messages/{message_id}")
    public @ResponseBody ResponseEntity<Integer> deleteMessage(@PathVariable int message_id) {
        return ResponseEntity.status(200).body(messageService.deleteMessage(message_id));
    }

    @PatchMapping("/messages/{message_id}")
    public @ResponseBody ResponseEntity<Integer> updateMessage(@PathVariable int message_id, @RequestBody Message message) {
        String message_text = message.getMessage_text();
        Integer rows = messageService.updateMessage(message_id, message_text);
        if (rows == null) {
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.status(200).body(rows);
    }

    @GetMapping("/accounts/{account_id}/messages")
    public @ResponseBody ResponseEntity<List<Message>> retrieveMessagesByUser(@PathVariable int account_id) {
        return ResponseEntity.status(200).body(messageService.getMessagesByPostedBy(account_id));
    }
}
