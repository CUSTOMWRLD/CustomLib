package com.customwrld.customlib.pigeon.payloads;

import com.customwrld.pigeon.annotations.Payload;
import com.customwrld.pigeon.annotations.Transmit;
import com.customwrld.pigeon.payloads.BasePayload;
import com.customwrld.pigeon.payloads.MessagePayload;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.UUID;

@Payload
public class ChatPayload extends MessagePayload {

    @Transmit @Getter private Player player;

    public ChatPayload() { payloadId = "minecraft-chat"; }

    public ChatPayload(Player player, String content) {
        super(content);
        this.player = player;
    }

}
