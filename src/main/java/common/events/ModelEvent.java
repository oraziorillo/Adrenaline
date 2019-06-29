package common.events;

import common.dto_model.DTO;

import java.io.Serializable;

public interface ModelEvent extends Serializable {

    DTO getDTO();

    String getDynamicMessage();
}
