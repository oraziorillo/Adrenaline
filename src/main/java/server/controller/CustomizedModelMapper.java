package server.controller;

import common.dto_model.AmmoTileDTO;
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
        configureAmmoTile();
    }


    private void configureAmmoTile(){
        TypeMap<AmmoTile, AmmoTileDTO> typeMap = modelMapper.createTypeMap(AmmoTile.class, AmmoTileDTO.class);
        typeMap.addMappings(mapper -> mapper.map(AmmoTile::containsPowerUp, AmmoTileDTO::setHasPowerUp));
    }


    public ModelMapper getModelMapper() {
        return modelMapper;
    }
}
