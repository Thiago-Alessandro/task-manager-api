package net.weg.taskmanager.service.processor;

import jdk.swing.interop.SwingInterOpUtils;
import lombok.AllArgsConstructor;
import net.weg.taskmanager.controller.TaskController;
import net.weg.taskmanager.model.*;

import java.util.ArrayList;
public class ChatProcessor {

    private Chat chat;
    private String chatClassName;
    private ArrayList<String> resolvingCascade;

    public static ChatProcessor getInstance(){
        return new ChatProcessor();
    }

    private void setDefaultInfo(Chat resolvingChat, ArrayList<String> _resolvingCascade){
        chat = resolvingChat;
        chatClassName = chat.getClass().getSimpleName();
        resolvingCascade = _resolvingCascade;
        resolvingCascade.add(chatClassName);
    }

    private void genericResolve(Chat resolvingChat, ArrayList<String> _resolvingCascade){
        setDefaultInfo(resolvingChat, _resolvingCascade);
        resolveChatGenericAtributes();
    }

    private void resolveChatGenericAtributes(){
        resolveChatMessages();
        resolveChatUsersGeneric();
    }

    public Chat resolveChatGeneric(Chat resolvingChat, ArrayList<String> _resolvingCascade){
        genericResolve(resolvingChat, _resolvingCascade);

        resolveChatTeam();
        resolveChatProject();

        resolvingCascade.remove(chatClassName);

        return chat;
    }

    public Chat resolveChat(UserChat resolvingChat,  ArrayList<String> _resolvingCascade){

        genericResolve(resolvingChat, _resolvingCascade);
//        resolveUserChatUsers();

        resolvingCascade.remove(chatClassName);

        return chat;
    }

    public Chat resolveChat(ProjectChat resolvingChat,  ArrayList<String> _resolvingCascade){
        genericResolve(resolvingChat, _resolvingCascade);

        resolveChatProject();
//        resolveProjectChatUsers();

        resolvingCascade.remove(chatClassName);

        return chat;
    }

    public Chat resolveChat(TeamChat resolvingChat,  ArrayList<String> _resolvingCascade){
        genericResolve(resolvingChat, _resolvingCascade);


        resolveChatTeam();
//        resolveTeamChatUsers();

        resolvingCascade.remove(chatClassName);

        return chat;
    }

    public Chat resolveChat(UserChat resolvingChat){
        return resolveChat(resolvingChat, new ArrayList<>());
    }

    public Chat resolveChat(TeamChat resolvingChat){
        return resolveChat(resolvingChat, new ArrayList<>());
    }

    public Chat resolveChat(ProjectChat resolvingChat){
        return resolveChat(resolvingChat, new ArrayList<>());
    }


//    private static void resolveTeamChatUsers(){
//        if(chat.getUsers()!=null){
//            if(resolvingCascade.contains(User.class.getSimpleName())){
//                chat.setUsers(null);
//                System.out.println("seteichat TeamChatUsers null " + resolvingCascade);
//                return;
//            }
//
//            for(User user : chat.getUsers()) {
//                UserProcessor.resolveUser(user, resolvingCascade);
//            }
//        }
//    }
//    private static void resolveProjectChatUsers(){
//        if(chat.getUsers()!=null){
//            if(resolvingCascade.contains(User.class.getSimpleName())){
//                chat.setUsers(null);
//                System.out.println("seteichat ProjectChatUsers null " + resolvingCascade);
//                return;
//            }
//
//            for(User user : chat.getUsers()) {
//                UserProcessor.resolveUser(user, resolvingCascade);
//            }
//        }
//    }
//    private static void resolveUserChatUsers(){
//        if(chat.getUsers()!=null){
//            if(resolvingCascade.contains(User.class.getSimpleName())){
//                chat.setUsers(null);
//                System.out.println("seteichat UserChatUsers null " + resolvingCascade);
//                return;
//            }
//
//            for(User user : chat.getUsers()) {
//                UserProcessor.resolveUser(user, resolvingCascade);
//            }
//        }
//    }

    private void resolveChatTeam(){
        if(chat instanceof TeamChat){
            if(((TeamChat) chat).getTeam() != null){
                if(resolvingCascade.contains(Team.class.getSimpleName())){
                    ((TeamChat) chat).setTeam(null);
                    return;
                }
                TeamProcessor.getInstance().resolveTeam(((TeamChat) chat).getTeam(), resolvingCascade);
            }
        }
    }

    private void resolveChatProject(){
        if(chat instanceof ProjectChat){
            if(((ProjectChat) chat).getProject() != null){
                if(resolvingCascade.contains(Project.class.getSimpleName())){
                    ((ProjectChat) chat).setProject(null);
                    return;
                }
                ProjectProcessor.getInstance().resolveProject(((ProjectChat) chat).getProject(), resolvingCascade);
            }
        }
    }

    private void resolveChatMessages(){
        if(chat.getMessages() != null){
            if(resolvingCascade.contains(Message.class.getSimpleName())){
                chat.setMessages(null);
                return;
            }
            for(Message message : chat.getMessages()){
                MessageProcessor.getInstance().resolveMessage(message, resolvingCascade);
            }
        }
    }





    private void resolveChatUsersGeneric(){
        if(chat.getUsers()!=null){
            if(resolvingCascade.contains(User.class.getSimpleName())){
                chat.setUsers(null);
                return;
            }

            for(User user : chat.getUsers()) {
                UserProcessor.getInstance().resolveUser(user, resolvingCascade);
            }
        }
    }

    //    public static Chat resolveChat(Chat resolvingChat){
//        System.out.println(resolvingChat.getClass().getSimpleName());
//        if(resolvingChat instanceof UserChat){
//            System.out.println("sou instance de userchat");
//            return resolveChat(resolvingChat, UserChat.class.getSimpleName(), new ArrayList<>());
//        }else if (resolvingChat instanceof TeamChat){
//            return resolveChat(resolvingChat, TeamChat.class.getSimpleName(), new ArrayList<>());
//        } else if (resolvingChat instanceof ProjectChat) {
//            return resolveChat(resolvingChat, ProjectChat.class.getSimpleName(), new ArrayList<>());
//        }
//        System.out.println("aaaa");
//        return resolveChat(resolvingChat, Chat.class.getSimpleName(), new ArrayList<>());
//    }

}
