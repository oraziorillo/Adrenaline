package server.controller;

import common.model_dtos.AmmoTileDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import server.model.AmmoTile;

public class CustomizedModelMapper {

    private ModelMapper modelMapper;

    public CustomizedModelMapper(){
        modelMapper = new ModelMapper();
        initModelMapper();
    }


    private void initModelMapper(){
        confiugureAmmoTile();
    }


    private void confiugureAmmoTile(){
        TypeMap<AmmoTile, AmmoTileDTO> typeMap = modelMapper.createTypeMap(AmmoTile.class, AmmoTileDTO.class);
        typeMap.addMappings(mapper -> {
            //mapper.map(src -> src.containsPowerup(), AmmoTileDTO::setHasPowerUp);
        });
    }


    public ModelMapper getModelMapper() {
        return modelMapper;
    }
}
