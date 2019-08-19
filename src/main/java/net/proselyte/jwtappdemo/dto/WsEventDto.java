package net.proselyte.jwtappdemo.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.proselyte.jwtappdemo.JsonView.Views;

@Data
@AllArgsConstructor
@JsonView(Views.IdTimeDateReversStatus.class)
public class WsEventDto {
    private ObjectType objectType;
    private EventType eventType;


    @JsonRawValue
    private String body;

}
