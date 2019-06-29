package server.controller;

import common.dto_model.*;
import common.enums.PcColourEnum;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import server.model.AmmoTile;
import server.model.Pc;
import server.model.WeaponCard;
import server.model.squares.AmmoSquare;
import server.model.squares.SpawnPoint;

import java.util.Set;
import java.util.stream.Collectors;


public class CustomizedModelMapper {


    private static ModelMapper modelMapper;


    public CustomizedModelMapper(){
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        initModelMapper();
    }


    private void initModelMapper(){
        configureAmmoTile();
        configureSquare();
        configureWeaponCard();
    }


    public ModelMapper getModelMapper() {
        return modelMapper;
    }


    private void configureAmmoTile(){
        TypeMap<AmmoTile, AmmoTileDTO> typeMap = modelMapper.createTypeMap(AmmoTile.class, AmmoTileDTO.class);
        typeMap.addMappings(mapper -> mapper.map(AmmoTile::hasPowerUp, AmmoTileDTO::setHasPowerUp));
    }


    private void configureSquare() {
        Converter<Set<Pc>, Set<PcColourEnum>> toPcColour = ctx -> ctx.getSource() == null
                ? null
                : ctx.getSource().stream().map(Pc::getColour).collect(Collectors.toSet());

        TypeMap<SpawnPoint, SquareDTO> typeMapSpawnPoint = modelMapper.createTypeMap(SpawnPoint.class, SquareDTO.class);
        TypeMap<AmmoSquare, SquareDTO> typeMapAmmoSquare = modelMapper.createTypeMap(AmmoSquare.class, SquareDTO.class);

        typeMapSpawnPoint.addMappings(mapper -> mapper.using(toPcColour).map(SpawnPoint::getPcs, SquareDTO::setPcs));
        typeMapAmmoSquare.addMappings(mapper -> mapper.using(toPcColour).map(AmmoSquare::getPcs, SquareDTO::setPcs));
    }

    private void configureWeaponCard() {
        TypeMap<WeaponCard, WeaponCardDTO> typeMap = modelMapper.createTypeMap(WeaponCard.class, WeaponCardDTO.class);
        typeMap.addMappings(mapper -> mapper.map(WeaponCard::getFireModeSize, WeaponCardDTO::setBasicEffects));
        typeMap.addMappings(mapper -> mapper.map(WeaponCard::getUpgradesSize, WeaponCardDTO::setUpgrades));
    }

}
