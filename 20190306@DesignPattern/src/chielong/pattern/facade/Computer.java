package chielong.pattern.facade;

import chielong.pattern.facade.subsystem.Disk;
import chielong.pattern.facade.subsystem.Memory;
import chielong.pattern.facade.subsystem.Screen;

/**
 * Created by chielong on 2019-04-19.
 */
public class Computer {
    Disk disk ;
    Memory memory ;
    Screen screen ;

    public Computer() {
        init();
    }

    private void init() {
        disk = new Disk();
        memory = new Memory();
        screen = new Screen();
    }

    public void bootUp() {
        disk.work();
        memory.run();
        screen.powerOn();
    }

    public void bootDown() {
        screen.powerOff();
        memory.stop();
        disk.down();
    }
}
