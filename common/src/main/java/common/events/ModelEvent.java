package common.events;

import common.dto_model.DTO;

import java.io.Serializable;

/**
 * Interface for model-change event
 */
public interface ModelEvent extends Serializable {
    
    /**
     * @return the dto representing the new model status
     */
    DTO getDTO();
}
