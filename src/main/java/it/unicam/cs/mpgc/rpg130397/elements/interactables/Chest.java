package it.unicam.cs.mpgc.rpg130397.elements.interactables;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.EntityStats;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Weapon;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;

import java.util.Arrays;
import java.util.Random;

/// This class manages the chest GameObject. The chest is interactable, so when it collides with the player,
/// it interacts with it, giving him a random reward, that can be:
/// - update of a random weapon
/// - update of a random characteristic
/// - heal
/// - substitution of a random weapon.
public class Chest extends GameObject implements Interactable {

    RewardType rewardType;

    public Chest(Position position)
    {
        //cannot read rewardType before the constructor is called
        RewardType temp = RewardType.values()[new Random().nextInt((int)Arrays.stream(RewardType.values()).count())];
        rewardType = temp;
        String name;
        switch (temp)
        {
            case HEALTH -> name = "Health Chest";
            case CHARACTERISTIC -> name = "Characteristics Chest";
            case NEW_WEAPON -> name = "New Weapon Chest";
            case WEAPON_LEVEL -> name = "Weapon Level Chest";
            default -> name = "";
        }
        super(name, position);
    }

    public void interact()
    {
        reward();
        GameData.removeGameObject(this);
    }

    private void reward()
    {
        switch (rewardType)
        {
            case HEALTH -> {
                float heal = GameData.getPlayer().getStats().get(EntityStats.StatType.MAX_HEALTH) / 6.0f;
                GameData.getPlayer().changeHealth(heal);}

            case WEAPON_LEVEL -> {
                int randomIndex;
                Weapon chosen;
                do
                {
                    randomIndex = new Random().nextInt((int) Arrays.stream(Characteristics.CharacteristicType.values()).count());
                    chosen = GameData.getPlayer().getInventory().get(Characteristics.CharacteristicType.values()[randomIndex]);
                }while(chosen == null);
                chosen.levelUp();
            }

            case CHARACTERISTIC -> {
                Characteristics.CharacteristicType chosen = Characteristics.CharacteristicType.values()
                        [new Random().nextInt((int) Arrays.stream(Characteristics.CharacteristicType.values()).count())];

                GameData.getPlayer().increaseCharacteristic(chosen, 1);
            }

            case NEW_WEAPON -> {
                GameData.getPlayer().assignWeapon(getNewWeapon());
            }
        }
    }

    private static Weapon getNewWeapon() {
        String randomWeapon;
        boolean chosenIsOk;
        do{
            int randomIndex = new Random().nextInt(GameData.getWeaponStatMap().size());
            chosenIsOk = true;
            randomWeapon = (String) GameData.getWeaponStatMap().keySet().toArray()[randomIndex];
            for(Weapon w : GameData.getPlayer().getInventory().values())
            {
                if (w.getName().equals(randomWeapon)) {
                    chosenIsOk = false;
                    break;
                }
            }
        }while(!chosenIsOk);
        return new Weapon(randomWeapon);
    }

    private enum RewardType{
        HEALTH,
        CHARACTERISTIC,
        WEAPON_LEVEL,
        NEW_WEAPON
    }

}
