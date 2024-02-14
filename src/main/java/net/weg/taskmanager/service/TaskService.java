package net.weg.taskmanager.service;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.UserTask;
import net.weg.taskmanager.model.UserTaskId;
import net.weg.taskmanager.repository.UserTaskRepository;
import net.weg.taskmanager.service.processor.ResolveStackOverflow;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.property.TaskProjectProperty;
import net.weg.taskmanager.repository.StatusRepository;
import net.weg.taskmanager.repository.TaskProjectPropertyRepository;
import net.weg.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskProjectPropertyRepository taskProjectPropertyRepository;
    private final UserTaskRepository userTaskRepository;

    public UserTask setWorkedTime(UserTask userTask){

        UserTask changingUserTask = userTaskRepository.findByUserIdAndTaskId(userTask.getUserId(), userTask.getTaskId());

        if(changingUserTask!=null){
            changingUserTask.setWorkedHours(userTask.getWorkedHours());
            changingUserTask.setWorkedMinutes(userTask.getWorkedMinutes());
            changingUserTask.setWorkedSeconds(userTask.getWorkedSeconds());
        }

        return changingUserTask;
    }

    public UserTask getUserTask(Integer userId, Integer taskId){
        UserTaskId userTaskId = new UserTaskId();
        userTaskId.setUserId(userId);
        userTaskId.setTaskId(taskId);

//        return userTaskRepository.findByUserIdAndTaskId(userId, taskId);
        return userTaskRepository.findById(userTaskId).get();
    }

        public Task patchProperty(TaskProjectProperty taskProjectProperty, Integer taskId) {
            System.out.println(taskProjectProperty.getType());
        Task task = taskRepository.findById(taskId).get();
        if(taskProjectProperty.getId()==0){
            System.out.println("entrei1");
            taskProjectProperty.setId(null);
        }
        if(taskProjectProperty.getProperty().getId()==0){
            System.out.println("entrei2");
            taskProjectProperty.getProperty().setId(null);
        }
            System.out.println(taskProjectProperty);
        task.getProperties().add(taskProjectProperty);
        return update(task);
    }

    public Task findById(Integer id) {
        Task task = taskRepository.findById(id).get();
        ResolveStackOverflow.getObjectWithoutStackOverflow(task);
        return task;
    }

    public Collection<Task> findAll() {
        Collection<Task> tasks = taskRepository.findAll();

        for(Task task : tasks){
            ResolveStackOverflow.getObjectWithoutStackOverflow(task);
        }
        return tasks;
    }

    public void delete(Integer id) {
//        taskProjectPropertyRepository.deleteALl(taskRepository.findById(id).get().getProperties());
//        taskRepository.findById(id).get().getProperties().removeAll(taskRepository.findById(id).get().getProperties());
//        System.out.println("list properties : "=);
        taskRepository.deleteById(id);
    }

    public Task create(Task task) {
        if(task.getCurrentStatus().getId()==0){
            task.getCurrentStatus().setId(null);

        }
        System.out.println(task);
        taskRepository.save(task);
//        setStatusListIndex(task);
        return update(task);
    }

    public Task update(Task task) {

        setStatusListIndex(task);
        propriedadeSetTarefa(task);

        Task updatedTask = taskRepository.save(task);
        return ResolveStackOverflow.getObjectWithoutStackOverflow(updatedTask);
    }

    private void setStatusListIndex(Task task){
        Integer defaultIndex = -1;
        Integer firstIndex = 0;
        if(task.getCurrentStatus()!=null && task.getStatusListIndex() != null){
            if(task.getStatusListIndex() == defaultIndex){
                Collection<Task> listaTasks = getTasksByStatus(task.getCurrentStatus().getId());
                if(listaTasks != null){
                    task.setStatusListIndex(listaTasks.size());
                }else {
                    task.setStatusListIndex(firstIndex);
                }
            }
        } else {
            task.setStatusListIndex(defaultIndex);
        }
    }

    private void propriedadeSetTarefa(Task task){
        //Verifica se há alguma propriedade na tarefa
        if(task.getProperties() != null && task.getProperties().size()>0){
            //Passa pela lista de propriedades da tarefa
            for(TaskProjectProperty propriedade : task.getProperties()) {
                //Adiciona a referencia da tarefa na propriedade
                propriedade.setTask(task);
                //Salva a propriedade atualizada com a referencia da tarefa
                taskProjectPropertyRepository.save(propriedade);
            }
        }
    }
    private final StatusRepository statusRepository;
    public Collection<Task> getTasksByStatus(Integer id){

        return taskRepository.getTaskByCurrentStatus(statusRepository.findById(id).get());
    }

}