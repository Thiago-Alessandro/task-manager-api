package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.service.processor.ChatProcessor;
import net.weg.taskmanager.model.UserChat;
import net.weg.taskmanager.repository.UserChatRepository;
import net.weg.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
public class UserChatService implements IService<UserChat>{

    //para fins de teste
    private final UserRepository userRepository;

    private final UserChatRepository userChatRepository;

//    private final MessageRepository messageRepository;

//    private static Long count = 1;

    @Override
    public UserChat findById(Long id) {
        UserChat chat = userChatRepository.findById(id).get();
        ChatProcessor.getInstance().resolveChat(chat);
        return chat;
    }

    @Override
    public Collection<UserChat> findAll() {
        Collection<UserChat> chats = userChatRepository.findAll();
        for(UserChat chat :  chats){
            System.out.println("\nRESOLVENDO AQUI HEIN\n\n");
            ChatProcessor.getInstance().resolveChat(chat);
        }
        System.out.println("AOPAAAAAAA");
        System.out.println(chats);
        return chats;
    }

    @Override
    public void delete(Long id) {
        userChatRepository.deleteById(id);
    }

    @Override
    public UserChat create(UserChat obj) {
//        validaChat(obj);
        return userChatRepository.save(obj);
    }

    @Override
    public UserChat update(UserChat obj) {
//        validaChat(obj);

        UserChat updatedUserChat = userChatRepository.save(obj);
        ChatProcessor.getInstance().resolveChat(updatedUserChat);
        return updatedUserChat;
    }

    public Collection<UserChat> getChatsByUserId(Long id){
        Collection<UserChat> chats = userChatRepository.findUserChatsByUsersContaining(userRepository.findById(id).get());
        for(UserChat chat : chats){
            ChatProcessor.getInstance().resolveChat(chat);
        }
        return chats;
    }

//    private void validaChat (Chat chat){
//        if(chat.getType().equals("USER")){
//            if (chat.getMembers().size() != 2){
//                throw new RuntimeException("User chat members number doesn't match excpected (2)");
//            }
//        }
//    }

}
