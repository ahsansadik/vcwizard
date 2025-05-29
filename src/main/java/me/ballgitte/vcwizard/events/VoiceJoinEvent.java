package me.ballgitte.vcwizard.events;

import me.ballgitte.vcwizard.Areas;
import me.ballgitte.vcwizard.Main;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashSet;
import java.util.Set;

public class VoiceJoinEvent extends ListenerAdapter {

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        if (event.getChannelJoined() == null) return;

        for (Areas area : Areas.values()) {
            if (area.getId().equals(event.getChannelJoined().getId())) {
                newVc(event, area);
            }
        }
    }

    private static void newVc(GuildVoiceUpdateEvent event, Areas area) {
        Set<Integer> usedNumbers = new HashSet<>();
        String areaName = area.getName();

        for (VoiceChannel vc : event.getChannelJoined().getParentCategory().getVoiceChannels()) {
            String name = vc.getName();
            if (name.startsWith(areaName + ": ")) {
                try {
                    int num = Integer.parseInt(name.substring(name.lastIndexOf(": ") + 2));
                    usedNumbers.add(num);
                } catch (NumberFormatException ignored) {}
            }
        }

        int newNumber = 1;
        while (usedNumbers.contains(newNumber)) {
            newNumber++;
        }

        String channelName = areaName + ": " + newNumber;
        VoiceChannel newChannel = event.getChannelJoined().getParentCategory().createVoiceChannel(channelName).complete();
        event.getGuild().moveVoiceMember(event.getMember(), newChannel).queue();
    }
}
