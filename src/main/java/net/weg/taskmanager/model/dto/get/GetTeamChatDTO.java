package net.weg.taskmanager.model.dto.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.weg.taskmanager.model.dto.shortDTOs.TeamShortDTO;
import net.weg.taskmanager.model.dto.utils.DTOUtils;
import net.weg.taskmanager.model.entity.TeamChat;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTeamChatDTO extends GetChatDTO{
    private TeamShortDTO team;
    public GetTeamChatDTO(TeamChat teamChat){
        super(teamChat);
        this.team = DTOUtils.teamToShortTeamDTO(teamChat.getTeam());
    }

}
