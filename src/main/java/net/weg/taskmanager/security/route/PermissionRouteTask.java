package net.weg.taskmanager.security.route;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import net.weg.taskmanager.model.Project;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.User;
import net.weg.taskmanager.repository.TaskRepository;
import net.weg.taskmanager.repository.UserTaskRepository;
import net.weg.taskmanager.security.model.entity.UserDetailsEntity;
import net.weg.taskmanager.security.model.enums.Auth;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

//import java.util.Map;
import java.util.Map;
import java.util.function.Supplier;

@Component
@AllArgsConstructor
public class PermissionRouteTask implements AuthorizationManager<RequestAuthorizationContext> {
    private final TaskRepository taskRepository;
    private final UserTaskRepository userTaskRepository;

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> supplier, RequestAuthorizationContext object) {
        Authentication authentication = supplier.get();
        HttpServletRequest request = object.getRequest();
        UserDetailsEntity userDetails = (UserDetailsEntity) authentication.getPrincipal();
        User user = userDetails.getUser();
        Map<String, String> mapper = object.getVariables();
        Long statusId = Long.parseLong(mapper.get("statusId"));
        Long taskId = Long.parseLong(mapper.get("taskId"));
        Task task = taskRepository.findById(taskId).get();
        Long userId = Long.parseLong(mapper.get("userId"));
        Project project = task.getProject();
        if (statusId == null) {
            if (userId == null) {
                if (existsByTaskIdAndUser(taskId, user)) {
                    return new AuthorizationDecision(isUserAuthorized(project.getId(), user, Auth.valueOf(request.getMethod())));
                }
            }
            if (existsByTask_IdAndUser_Id(userId, taskId, userDetails)){
                return new AuthorizationDecision(isUserAuthorized(project.getId(), user, Auth.valueOf(request.getMethod())));
            }
        }
        if(existsByStatus_IdAndUser(statusId, user)){
            return new AuthorizationDecision(isUserAuthorized(project.getId(), user, Auth.valueOf(request.getMethod())));
        }
        return new AuthorizationDecision(false);
    }

    private boolean existsByTaskIdAndUser(Long taskId, User user) {
        return taskRepository.existsByIdAndAssociatesContaining(taskId, user);
    }

    private boolean existsByTask_IdAndUser_Id(Long userId, Long taskId, UserDetailsEntity userDetails) {
        return userTaskRepository.existsByUserIdAndTaskIdAndUser_UserDetailsEntity(userId, taskId, userDetails);
    }

    private boolean existsByStatus_IdAndUser(Long statusId, User user) {
        return taskRepository.existsByCurrentStatus_IdAndAssociatesContaining(statusId, user);
    }

    private boolean isUserAuthorized(Long projectId, User user, Auth auth) {
        return user.getProjectsAcess()
                .stream().filter(projectAcess -> projectAcess.getProjectId().equals(projectId))
                .anyMatch(projectAcess -> projectAcess.getAcessProfile().getAuths().contains(auth)
                );
//        return repository.existsByIdAndMembersContaining(projectId,user);
    }
//    private boolean existsByStatus(Long)
}
