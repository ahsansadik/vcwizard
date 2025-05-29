package me.ballgitte.vcwizard;

public enum Areas {

    LOUNGE("💺Lounge", "UPDATE_CHANNEL_ID"), // Update channel ID
    STAFF_LOUNGE("💺Staff Lounge", "UPDATE_CHANNEL_ID"),  // Update channel ID
    ADMIN_LOUNGE("💺Admin Lounge", "UPDATE_CHANNEL_ID"),;  // Update channel ID

    private final String name2;
    private final String createChannelId2;

    Areas(String name, String createChannelId) {
        name2 = name;
        createChannelId2 = createChannelId;
    }

    public String getName() {
        return name2;
    }

    public String getId() {
        return createChannelId2;
    }
}
