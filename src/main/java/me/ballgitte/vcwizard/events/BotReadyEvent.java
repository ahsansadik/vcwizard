package me.ballgitte.vcwizard.events;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.jetbrains.annotations.NotNull;

import me.ballgitte.vcwizard.Main;

public class BotReadyEvent extends ListenerAdapter {
    
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        TextChannel channel = Main.getGuild().getTextChannelById("UPDATE_CHANNEL_ID"); // Update channel ID
        channel.sendMessage("🟢 VC Wizard is now online").queue();
    }
}