package it.unicam.cs.mpgc.rpg130397.elements.interactables;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.EntityStats;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Weapon;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;

import java.util.Arrays;
import java.util.Random;

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
                int randomIndex = new Random().nextInt((int) Arrays.stream(Characteristics.CharacteristicType.values()).count());
                GameData.getPlayer().getInventory().get(Characteristics.CharacteristicType.values()[randomIndex]).levelUp();
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
