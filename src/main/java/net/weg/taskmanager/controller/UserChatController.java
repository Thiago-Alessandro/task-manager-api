package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.dto.get.GetUserChatDTO;
import net.weg.taskmanager.model.entity.UserChat;
import net.weg.taskmanager.service.UserChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/userChat")
@AllArgsConstructor
<<<<<<< HEAD
public class UserChatController {
    private final UserChatService userChatService;

    @GetMapping("/{userChatId}")
    public ResponseEntity<UserChat> findById(@PathVariable Long userChatId) {
=======
public class UserChatController
//        implements IController<UserChat>
{

    private final UserChatService userChatService;

//    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GetUserChatDTO> findById(@PathVariable Long id) {
>>>>>>> feature/security-updated
        try {
            return new ResponseEntity<>(userChatService.findById(userChatId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

<<<<<<< HEAD
    @GetMapping
    public ResponseEntity<Collection<UserChat>> findAll() {
        return new ResponseEntity<>(userChatService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{userChatId}")
    public ResponseEntity<UserChat> delete(@PathVariable Long userChatId) {
=======
//    @Override
    @GetMapping()
    public ResponseEntity<Collection<GetUserChatDTO>> findAll() {
        return new ResponseEntity<>(userChatService.findAll(), HttpStatus.OK);
    }

//    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<GetUserChatDTO> delete(@PathVariable Long id) {
>>>>>>> feature/security-updated
        try {
            userChatService.delete(userChatId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

<<<<<<< HEAD
    @PostMapping
    public ResponseEntity<UserChat> create(@RequestBody UserChat obj) {
        return new ResponseEntity<>(userChatService.create(obj), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserChat> update(@RequestBody UserChat obj) {
        return new ResponseEntity<>(userChatService.update(obj), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Collection<UserChat>> getUserChatsByUserID(@PathVariable Long userId) {
        return new ResponseEntity<>(userChatService.getChatsByUserId(userId), HttpStatus.OK);
=======
//    @Override
    @PostMapping
    public ResponseEntity<GetUserChatDTO> create(@RequestBody UserChat obj) {
        return new ResponseEntity<>(userChatService.create(obj), HttpStatus.OK);
    }

//    @Override
    @PutMapping()
    public ResponseEntity<GetUserChatDTO> update(@RequestBody UserChat obj) {
        return new ResponseEntity<>(userChatService.update(obj), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Collection<GetUserChatDTO>> getUserChatsByUserID(@PathVariable Long id) {
        return new ResponseEntity<>(userChatService.getChatsByUserId(id), HttpStatus.OK);
>>>>>>> feature/security-updated
    }
}
