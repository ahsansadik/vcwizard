package me.ballgitte.vcwizard;

import me.ballgitte.vcwizard.events.BotReadyEvent;
import me.ballgitte.vcwizard.events.Ping;
import me.ballgitte.vcwizard.events.VoiceJoinEvent;
import me.ballgitte.vcwizard.events.VoiceLeaveEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;

public class Main {
    private static JDA jda;
    public static void main(String[] args) {
        jda = JDABuilder.createDefault("UPDATE_BOT_TOKEN").build(); // Update your bot token
        
        jda.addEventListener(new Ping());
        jda.upsertCommand("ping", "Ping the bot").setDefaultPermissions(DefaultMemberPermissions.DISABLED).queue();

        jda.addEventListener(new BotReadyEvent());
        jda.addEventListener(new VoiceJoinEvent());
        jda.addEventListener(new VoiceLeaveEvent());
    }

    public static JDA getJda() {
        return jda;
    }

    public static Guild getGuild() {
        return jda.getGuildById("UPDATE_GUILD_ID"); // Update Guild ID Here
    }
}