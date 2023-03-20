package dtos;

import entities.Hobby;

import java.util.LinkedList;
import java.util.List;

public class HobbyDTO {

    private long id;
    private String name;
    private String description;
    
    public HobbyDTO() {
    }

    public HobbyDTO(Hobby hobby) {
        if (hobby.getId() != null)
            this.id = hobby.getId();
        this.name = hobby.getName();
        this.description = hobby.getDescription();
    }

    public HobbyDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public static List<HobbyDTO> getDtos(List<Hobby> hobbies) {
        List<HobbyDTO> hobbyDTOS = new LinkedList<>();
        for (Hobby hobby : hobbies) {
            HobbyDTO hobbyDTO = new HobbyDTO(hobby);
            hobbyDTOS.add(hobbyDTO);
        }
        return hobbyDTOS;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
