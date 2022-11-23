package main;

import buttons.SWFJoin;
import buttons.SWFLeave;
import buttons.SWFYes;
import buttons.types.ServerButton;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.util.concurrent.ConcurrentHashMap;

public class ButtonManager {
    public ConcurrentHashMap<String, ServerButton> buttons;

    public ButtonManager() {
        this.buttons = new ConcurrentHashMap<>();

        buttons.put("swfjoin", new SWFJoin());
        buttons.put("swfleave", new SWFLeave());
        buttons.put("swfyes", new SWFYes());
    }

    public boolean perform(String command, ButtonInteractionEvent event){

        ServerButton cmd;
        if((cmd = this.buttons.get(command.toLowerCase().substring(0, command.length() - 36))) != null){
            cmd.performCommand(event);
            return true;
        }
        return false;
    }
}