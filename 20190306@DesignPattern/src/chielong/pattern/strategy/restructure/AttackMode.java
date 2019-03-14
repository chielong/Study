package chielong.pattern.strategy.restructure;

import java.util.HashMap;
import java.util.Map;

public class AttackMode {
    private static final Map<String , Weaponable> weaponMap = new HashMap<String , Weaponable>();

    static {
        weaponMap.put("sword" , new SwordWeapon());
        weaponMap.put("knife" , new Knife());
    }

    public AttackMode() {
    }

    public void attck(String mode) {
        weaponMap.get(mode).hit();
    }
}
