package slash;

import mysql.BotInfos;
import mysql.dashboard.PlayerInfos;
import mysql.dashboard.PunkteSystem;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import slash.types.ServerSlash;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Punkte implements ServerSlash {
    @Override
    public void performCommand(SlashCommandInteractionEvent event) {
        if (BotInfos.getBotInfos("cmd_points_on").equals("1")) {
                if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                    if (event.getSubcommandName().equals("add")) {
                        Member m = event.getOption("member").getAsMember();
                        PunkteSystem.uploadPoints(m.getId(), event.getOption("punkte").getAsInt());

                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle(m.getEffectiveName() + "s Punkte");
                        builder.setDescription("Du hast diesem User " + event.getOption("punkte").getAsInt() + " Punkte hinzugefügt.");
                        builder.setThumbnail(PlayerInfos.getInfo(m.getId(), "discord_id", "discord_pb", "users"));
                        builder.setColor(Color.decode("#2ecc71"));

                        event.replyEmbeds(builder.build()).addActionRow(Button.link("https://sensivity.team/points.php", "PunkteSystem")).setEphemeral(true).queue();

                    } else if (event.getSubcommandName().equals("remove")) {
                        Member m = event.getOption("member").getAsMember();
                        if (PunkteSystem.getPoints(m.getId()) - event.getOption("punkte").getAsInt() <= 0) {
                            PunkteSystem.uploadPoints(m.getId(), -PunkteSystem.getPoints(m.getId()));
                        } else {
                            PunkteSystem.uploadPoints(m.getId(), -event.getOption("punkte").getAsInt());
                        }

                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle(m.getEffectiveName() + "s Punkte");
                        builder.setDescription("Du hast diesem User " + event.getOption("punkte").getAsInt() + " Punkte abgezogen.");
                        builder.setThumbnail(PlayerInfos.getInfo(m.getId(), "discord_id", "discord_pb", "users"));
                        builder.setColor(Color.decode("#2ecc71"));

                        event.replyEmbeds(builder.build()).addActionRow(Button.link("https://sensivity.team/points.php", "PunkteSystem")).setEphemeral(true).queue();


                    } else if (event.getSubcommandName().equals("set")) {
                        Member m = event.getOption("member").getAsMember();
                        if (event.getOption("punkte").getAsInt() <= 0) {
                            PunkteSystem.uploadPoints(m.getId(), -PunkteSystem.getPoints(m.getId()));
                        } else {
                            PunkteSystem.uploadPoints(m.getId(), event.getOption("punkte").getAsInt() - PunkteSystem.getPoints(m.getId()));
                        }

                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle(m.getEffectiveName() + "s Punkte");
                        builder.setDescription("Du hast dem User seinen Punktestand auf " + event.getOption("punkte").getAsInt() + " Punkte gesetzt.");
                        builder.setThumbnail(PlayerInfos.getInfo(m.getId(), "discord_id", "discord_pb", "users"));
                        builder.setColor(Color.decode("#2ecc71"));

                        event.replyEmbeds(builder.build()).addActionRow(Button.link("https://sensivity.team/points.php", "PunkteSystem")).setEphemeral(true).queue();

                    }
                }
                    if (event.getSubcommandName().equals("info")) {
                        if (!event.getOptions().isEmpty()) {
                            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                                Member m = event.getOption("member").getAsMember();

                                if (PlayerInfos.isExist(m.getId(), "discord_id", "users")) {
                                    int punkte = Integer.parseInt(PlayerInfos.getInfo(m.getId(), "discord_id", "points", "users"));

                                    EmbedBuilder builder = new EmbedBuilder();
                                    builder.setTitle(m.getEffectiveName() + "s Punkte");
                                    builder.setDescription("Der User hat **" + punkte + " Punkte**.");
                                    builder.setThumbnail(PlayerInfos.getInfo(m.getId(), "discord_id", "discord_pb", "users"));
                                    builder.setColor(Color.decode("#2ecc71"));

                                    event.replyEmbeds(builder.build()).addActionRow(Button.link("https://sensivity.team/points.php", "PunkteSystem")).setEphemeral(true).queue();

                                } else {
                                    EmbedBuilder builder = new EmbedBuilder();
                                    builder.setTitle("Keinen Account!");
                                    builder.setDescription("Der Member hat kein Team Sensivity Account.");
                                    builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                                    builder.setColor(Color.RED);

                                    event.replyEmbeds(builder.build()).addActionRow(Button.link("https://sensivity.team/points.php", "PunkteSystem")).setEphemeral(true).queue();

                                }
                            } else {
                                EmbedBuilder builder = new EmbedBuilder();
                                builder.setTitle("Keine Berechtigung dafür!");
                                builder.setDescription("Du hast keine Berechtigung die Punkte von anderen Spielern abzufragen.");
                                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                                builder.setColor(Color.RED);

                                event.replyEmbeds(builder.build()).setEphemeral(true).queue();

                            }
                        } else {
                            if (PlayerInfos.isExist(event.getMember().getId(), "discord_id", "users")) {
                                int punkte = Integer.parseInt(PlayerInfos.getInfo(event.getMember().getId(), "discord_id", "points", "users"));

                                EmbedBuilder builder = new EmbedBuilder();
                                builder.setTitle(event.getMember().getEffectiveName() + "s Punkte");
                                builder.setDescription("Du hast **" + punkte + " Punkte**.");
                                builder.setThumbnail(PlayerInfos.getInfo(event.getMember().getId(), "discord_id", "discord_pb", "users"));
                                builder.setColor(Color.decode("#2ecc71"));

                                event.replyEmbeds(builder.build()).addActionRow(Button.link("https://sensivity.team/points.php", "PunkteSystem")).setEphemeral(true).queue();

                            } else {
                                EmbedBuilder builder = new EmbedBuilder();
                                builder.setTitle("Keinen Account!");
                                builder.setDescription("Der Member hat kein Team Sensivity Account.");
                                builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
                                builder.setColor(Color.RED);

                                event.replyEmbeds(builder.build()).setEphemeral(true).queue();
                            }
                        }
                    }
        }else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.red);
            builder.setDescription("Dieser Befehl ist zurzeit deaktiviert. Versuche es später erneut.");
            builder.setThumbnail(BotInfos.getBotInfos("logo_url"));
            builder.setTitle("Befel ist deaktiviert.");

            event.replyEmbeds(builder.build()).setEphemeral(true).queue();
        }
    }
}
