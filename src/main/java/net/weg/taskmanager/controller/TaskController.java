package net.weg.taskmanager.controller;

import lombok.AllArgsConstructor;
import net.weg.taskmanager.model.Priority;
import net.weg.taskmanager.model.Task;
import net.weg.taskmanager.model.UserTask;

//OLHAR PARA JUNTAR AS DTOs MAIS TARDE
import net.weg.taskmanager.model.dto.post.PostTaskDTO;
import net.weg.taskmanager.model.dto.get.GetTaskDTO;


import net.weg.taskmanager.model.dto.put.PutTaskDTO;
import net.weg.taskmanager.model.property.values.PropertyValue;
import net.weg.taskmanager.model.record.PriorityRecord;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{id}")

    public GetTaskDTO findById(@PathVariable Long id){return taskService.findById(id);}

    @GetMapping
    public Collection<GetTaskDTO> findAll(){return taskService.findAll();}

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        taskService.delete(id);}


    @PostMapping
    public GetTaskDTO create(@RequestBody PostTaskDTO postTaskDTO){
        return taskService.create(postTaskDTO);}

    @PutMapping
    public GetTaskDTO update(@RequestBody PutTaskDTO putTaskDTO){return taskService.update(putTaskDTO);}

    @GetMapping("/status/{id}")
    public Collection<GetTaskDTO> getTasksByStatus(@PathVariable Long id){return taskService.getTasksByStatus(id);}

    @PatchMapping("/userTask")
    public UserTask setWorkedTime(@RequestBody UserTask userTask){
        return taskService.setWorkedTime(userTask);
    }

    @PatchMapping("/property/{taskId}")
    public GetTaskDTO patchProperty(@RequestBody Property property, @PathVariable Long taskId) {
        return taskService.patchProperty(property,taskId);
    }

    @GetMapping("/userTask/{userId}/{taskId}")
    public UserTask getUserTask(@PathVariable Long userId, @PathVariable Long taskId){
        return taskService.getUserTask(userId, taskId);
    }

    @PutMapping("/property/put")
    public void putPropertyValue(@RequestBody Property property) {
        taskService.putPropertyValue(property);
    }

    @GetMapping("/priorities")
    public Collection<PriorityRecord> getAllPriorities() {
        List<Priority> listTest =  List.of(Priority.values());
       return listTest.stream().map(priority -> new PriorityRecord(priority.name(), priority.backgroundColor)).collect(Collectors.toList());
    }

}
