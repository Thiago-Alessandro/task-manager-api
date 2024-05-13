package net.weg.taskmanager.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.ToString;

import net.weg.taskmanager.model.entity.DashBoard.Chart;
import net.weg.taskmanager.model.entity.DashBoard.Dashboard;
import net.weg.taskmanager.model.property.Property;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Data
@AllArgsConstructor
@ToString()
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
//    @Column(nullable = false)
    private String description;
//    @Column(nullable = false)
    private Boolean favorited = false;

    @OneToOne(cascade = CascadeType.ALL)
    private File image;
    private String imageColor;
    @JoinColumn(nullable = false)
    @ManyToOne()
    private User creator;

    @Column(nullable = false)
    private LocalDate finalDate;
//    @Column(nullable = false)
    private LocalDate creationDate;
//    @Column(nullable = false)
    private LocalDateTime lastTimeEdited;


    //vai continuar msm?
    @ManyToMany
//    @Column(nullable = false)
    private Collection<User> administrators;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private Collection<Comment> comments;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Collection<Property> properties;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = false)
    @JoinColumn(nullable = false)
    private Collection<Status> statusList;
    @ManyToMany
    @JoinColumn(nullable = false)
    private Collection<User> members;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Team team;

    @OneToOne(optional = false)
    private ProjectChat chat;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Collection<Chart> charts;

    @OneToMany(mappedBy = "project")
    private Collection<Task> tasks;

//    public void setDefaultStatus() {
//        Collection<Status> defaultStatus = new HashSet<>();
//        defaultStatus.add(new Status("pendente", "#7CD5F4", "#000000",true));
//        defaultStatus.add(new Status("em progresso", "#FCEC62", "#000000",true));
//        defaultStatus.add(new Status("concluido", "#86C19F", "#000000",true));
//        defaultStatus.add(new Status("não atribuido", "#9CA3AE", "#000000",true));
//        if(this.getStatusList()!=null){
//            this.getStatusList().addAll(defaultStatus);
//        } else{
//            this.setStatusList((defaultStatus));
//        }
//    }

    public void updateLastTimeEdited() {
        this.lastTimeEdited = LocalDateTime.now();
    }

    public Project(){
        this.chat = new ProjectChat();
        this.chat.setProject(this);
        this.chat.setUsers(this.members);
//        this.chat.setUsers(this.members);
        this.creationDate = LocalDate.now();
        updateLastTimeEdited();
//        setDefaultStatus();
    }

    public void setImage(MultipartFile image) {
        if(image!=null){
            File file = new File();
            try {
                file.setData(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            file.setName(image.getOriginalFilename());
            file.setType(image.getContentType());
            this.image = file;
        }
    }

}