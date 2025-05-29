package me.ballgitte.vcwizard.events;

import me.ballgitte.vcwizard.Areas;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VoiceLeaveEvent extends ListenerAdapter {

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        if (event.getChannelLeft() == null) return;

        VoiceChannel leftChannel = event.getChannelLeft().asVoiceChannel();
        String leftName = leftChannel.getName();

        for (Areas area : Areas.values()) {
            String prefix = area.getName() + ": ";
            if (leftName.startsWith(prefix)) {
                if (leftChannel.getMembers().isEmpty()) {
                    // Get all dynamic channels in the same category and area
                    List<VoiceChannel> matchingChannels = new ArrayList<>();
                    for (VoiceChannel vc : leftChannel.getParentCategory().getVoiceChannels()) {
                        if (vc.getName().startsWith(prefix) && !vc.getId().equals(leftChannel.getId())) {
                            matchingChannels.add(vc);
                        }
                    }

                    // Sort by existing number suffix
                    matchingChannels.sort(Comparator.comparingInt(vc -> {
                        String name = vc.getName();
                        try {
                            return Integer.parseInt(name.substring(prefix.length()));
                        } catch (NumberFormatException e) {
                            return Integer.MAX_VALUE; // just in case
                        }
                    }));

                    // Rename all remaining matching channels
                    for (int i = 0; i < matchingChannels.size(); i++) {
                        matchingChannels.get(i).getManager().setName(prefix + (i + 1)).queue();
                    }

                    // Finally, delete the left channel
                    leftChannel.delete().queue();
                }
                break;
            }
        }
    }
}
