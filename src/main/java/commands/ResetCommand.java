package commands;

import commands.types.ServerCommand;
import main.Main;
import main.Start;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.util.List;

public class ResetCommand implements ServerCommand {
    @Override
    public void performCommand(MessageReceivedEvent event) throws ParseException {
        if (event.getMember().getId().equals("422148236875137059")){
            List<Member> members = Main.INSTANCE.getGuild().getMembers();
            int i = 1;
            for (Member member: members) {
                if(!member.getId().equals("422148236875137059")) {
                    member.modifyNickname(member.getUser().getName()).complete();
                    if (members.size() == i) {
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                        builder.setColor(Color.decode("#2ecc71"));
                        builder.setTitle("Reset erfolgreich!");
                        builder.setDescription("Alle Nicknames wurden erfolgreich zurückgesetzt.");
                    }

                    i++;

                    System.out.println(member.getEffectiveName());
                }
            }
        }else {

        }
    }
}
