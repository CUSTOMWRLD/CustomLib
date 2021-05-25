package com.customwrld.customlib.pigeon.payloads;

import com.customwrld.pigeon.annotations.Payload;
import com.customwrld.pigeon.annotations.Transmit;
import com.customwrld.pigeon.payloads.MessagePayload;
import lombok.Getter;
import org.bukkit.entity.Player;

@Payload
public class ChatPayload extends MessagePayload {

    @Transmit @Getter private Player player;

    public ChatPayload() { payloadId = "minecraft-chat"; }

    public ChatPayload(Player player, String content) {
        super(content);
        this.player = player;
    }

}
