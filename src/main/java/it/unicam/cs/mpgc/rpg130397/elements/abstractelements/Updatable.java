package it.unicam.cs.mpgc.rpg130397.elements.abstractelements;

/// This interface is implemented by those objects that need an update to work properly,
/// for example the player, the enemies and the bullets, that need to update their logic at each iteration
public interface Updatable {
    void update();
}
