package net.weg.taskmanager.service;
import lombok.RequiredArgsConstructor;
import net.weg.taskmanager.model.dto.utils.DTOUtils;
import net.weg.taskmanager.model.entity.User;

import net.weg.taskmanager.model.dto.get.GetProjectDTO;
import net.weg.taskmanager.model.dto.get.GetTaskDTO;
import net.weg.taskmanager.model.dto.post.PostProjectDTO;
import net.weg.taskmanager.model.entity.Project;
import net.weg.taskmanager.model.entity.Status;
import net.weg.taskmanager.model.dto.put.PutProjectDTO;
import net.weg.taskmanager.model.property.Property;
import net.weg.taskmanager.model.record.PriorityRecord;
import net.weg.taskmanager.repository.*;
import net.weg.taskmanager.service.processor.ProjectProcessor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Service
//@AllArgsConstructor
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final StatusRepository statusRepository;
    private final TeamRepository teamRepository;
    private final PropertyRepository propertyRepository;
    private final ModelMapper modelMapper;
    private final ProjectProcessor projectProcessor = new ProjectProcessor();
    
    public GetProjectDTO updateStatusList(Long id, Status status){
        Project project = projectRepository.findById(id).get();
        if(project.getStatusList()!=null){
            for(Status statusFor : project.getStatusList()){
                if(Objects.equals(status.getId(), statusFor.getId())){
                    BeanUtils.copyProperties(status, statusFor);
                    return new GetProjectDTO(treatAndSave(project));
                }
            }
            project.getStatusList().add(status);
        } else {
            project.setStatusList(new ArrayList<>());
            project.getStatusList().add(status);
        }
        return new GetProjectDTO(treatAndSave(project));
    }


    public GetProjectDTO findById(Long id){
        Project project =  projectRepository.findById(id).get();

        projectProcessor.resolveProject(project);

        return new GetProjectDTO(project);
    }

    public Collection<GetProjectDTO> findAll() {
        Collection<Project> projects =  projectRepository.findAll();

        projects.forEach(projectProcessor::resolveProject);

        return DTOUtils.projectToGetProjectDTOS(projects);
    }

    public GetProjectDTO create(PostProjectDTO projectDTO){

        Project project = new Project();
        BeanUtils.copyProperties(projectDTO, project);

        Collection<Status> listaNova = new HashSet<>();

        for (Status st: projectDTO.getStatusList()){
            listaNova.add(new Status(st.getName(), st.getBackgroundColor(), st.getTextColor(), st.getEnabled()));
        }
        project.setStatusList(listaNova);

        updateProjectChat(project);
        //Adiciona o projeto ao BD para que seja criado o seu Id
        projectRepository.save(project);
        //Referencia o projeto nas suas propriedades
        propertiesSetProject(project);

        return new GetProjectDTO(treatAndSave(project));
    }

    public GetProjectDTO update(PutProjectDTO projectDTO){

        Project project = projectRepository.findById(projectDTO.getId()).get();
        modelMapper.map(projectDTO, project);

        updateProjectChat(project);

        return new GetProjectDTO(treatAndSave(project));
    }

    public void delete(Long id){
        Project project = projectRepository.findById(id).get();
        taskRepository.deleteAll(project.getTasks());
        statusRepository.deleteAll(project.getStatusList());

        try {
            if(teamRepository.findTeamByProjectsContaining(project)!=null){
                //seria bom ter o atributo equipe no proprio projeto para não ter que pegar na service
                teamRepository.findTeamByProjectsContaining(project).getProjects().remove(project);
            }
        } catch (Exception e) {
            throw new RuntimeException(e + "Deu erro lá na projectService manin");
        }
        projectRepository.deleteById(id);
    }

    private final UserRepository userRepository;

    public Collection<GetProjectDTO> getProjectsByUserId(Long id){
        User user = userRepository.findById(id).get();
        Collection<Project> projects = projectRepository.findProjectsByMembersContaining(user);

        projects.forEach(projectProcessor::resolveProject);
        return DTOUtils.projectToGetProjectDTOS(projects);
    }


    private Project treatAndSave(Project project){
        project.updateLastTimeEdited();
        Project savedProject = projectRepository.save(project);
        projectProcessor.resolveProject(savedProject);
        return savedProject;
    }

    private void propertiesSetProject(Project project){
        //Verifica se há alguma propriedade no projeto
        if(project.getProperties() != null && project.getProperties().size()>0){
            //Passa pela lista de propriedades do projeto
            for(Property propriedade : project.getProperties()) {
                //Adiciona a referencia do projeto na propriedade
                propriedade.setProject(project);
                //Salva a propriedade atualizada com a referencia do projeto
                propertyRepository.save(propriedade);
            }
        }
    }

    private void updateProjectChat(Project project){
        project.getChat().setUsers(project.getMembers());
    }



}
