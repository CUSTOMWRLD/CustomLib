package com.customwrld.customlib.util;

import lombok.Getter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AlignUtil {

    @Getter private static final int chatPixels = 320;
    @Getter private static final int centerPixels = chatPixels / 2;

    public static List<BaseComponent> equalizeLengthsReversed(List<List<BaseComponent>> lines) {
        int amountOfGroups = lines.get(0).size();

        List<Integer> biggestElementPixelsInGroup = new ArrayList<>();

        for(int i = 0; i < amountOfGroups; i++) {
            biggestElementPixelsInGroup.add(0);
        }

        for (List<BaseComponent> line : lines) {
            for (int groupIndex = 0; groupIndex < amountOfGroups; groupIndex++) {
                int possibleBiggestPixels = getComponentPixels(line.get(groupIndex));
                if (possibleBiggestPixels > biggestElementPixelsInGroup.get(groupIndex)) {
                    biggestElementPixelsInGroup.set(groupIndex, possibleBiggestPixels);
                }
            }
        }

        List<BaseComponent> alignedLines = new ArrayList<>();

        for (List<BaseComponent> line : lines) {
            TextComponent alignedLine = new TextComponent();
            int prevGroupDifference = 0;
            for (int groupIndex = 0; groupIndex < amountOfGroups; groupIndex++) {
                TextComponent alignedElement = new TextComponent(getSpacePadding(Math.max(biggestElementPixelsInGroup.get(groupIndex) - getComponentPixels(line.get(groupIndex)) - prevGroupDifference, 0)));
                alignedElement.addExtra(line.get(groupIndex));
                alignedLine.addExtra(alignedElement);
                prevGroupDifference = getComponentPixels(alignedElement) - biggestElementPixelsInGroup.get(groupIndex);
            }
            alignedLines.add(alignedLine);
        }

        return alignedLines;
    }

    public static TextComponent rightAlignMessage(BaseComponent message) {
        int messagePxSize = getComponentPixels(message);

        TextComponent spacePadding = getSpacePadding(chatPixels - messagePxSize - 3);

        TextComponent centeredComponent = new TextComponent(spacePadding);
        centeredComponent.addExtra(message);

        return centeredComponent;
    }

    public static TextComponent rightAlignWithPadding(BaseComponent padding, BaseComponent line) {
        int paddingPxSize = getComponentPixels(padding);
        int linePxSize = getComponentPixels(line);

        TextComponent spacePadding = getSpacePadding(chatPixels - paddingPxSize - linePxSize - 3);

        TextComponent centeredComponent = new TextComponent(padding);
        centeredComponent.addExtra(spacePadding);
        centeredComponent.addExtra(line);

        return centeredComponent;
    }


    public static TextComponent centerAlignMessage(BaseComponent message) {
        int messagePxSize = getComponentPixels(message);

        TextComponent spacePadding = getSpacePadding(centerPixels - (messagePxSize / 2));

        TextComponent centeredComponent = new TextComponent(spacePadding);
        centeredComponent.addExtra(message);
        return centeredComponent;
    }

    public static List<BaseComponent> centerAlongFirst(List<BaseComponent> lines) {
        int start = centerPixels - (getComponentPixels(lines.get(0)) / 2);

        List<BaseComponent> alignedLines = new ArrayList<>();
        for(BaseComponent line : lines) {
            TextComponent spacePadding = getSpacePadding(start);

            TextComponent alignedLine = new TextComponent(spacePadding);
            alignedLine.addExtra(line);
            alignedLine.addExtra("\n");
            alignedLines.add(alignedLine);
        }

        return alignedLines;
    }

    public static List<BaseComponent> centerAlongBiggest(List<BaseComponent> lines) {
        int biggestLinePixels = 0;
        for(BaseComponent line : lines) {
            int possibleBiggestPixels = getComponentPixels(line);
            if(possibleBiggestPixels > biggestLinePixels) {
                biggestLinePixels = possibleBiggestPixels;
            }
        }

        int start = centerPixels - (biggestLinePixels / 2);

        List<BaseComponent> alignedLines = new ArrayList<>();
        for(BaseComponent line : lines) {
            TextComponent spacePadding = getSpacePadding(start);

            TextComponent alignedLine = new TextComponent(spacePadding);
            alignedLine.addExtra(line);
            alignedLine.addExtra("\n");
            alignedLines.add(alignedLine);
        }

        return alignedLines;
    }

    public static List<BaseComponent> centerWithPaddingAlongBiggest(List<BaseComponent> paddings, List<BaseComponent> linesToCenter) {
        int biggestPaddingPixels = 0;
        for(BaseComponent padding : paddings) {
            int possibleBiggestPixels = getComponentPixels(padding);
            if(possibleBiggestPixels > biggestPaddingPixels) {
                biggestPaddingPixels = possibleBiggestPixels;
            }
        }

        int biggestToCenterPixels = 0;
        for(BaseComponent line : linesToCenter) {
            int possibleBiggestPixels = getComponentPixels(line);
            if(possibleBiggestPixels > biggestToCenterPixels) {
                biggestToCenterPixels = possibleBiggestPixels;
            }
        }

        int center = biggestPaddingPixels + ((chatPixels - biggestPaddingPixels) / 2);

        int start = center - (biggestToCenterPixels / 2);

        List<BaseComponent> alignedLines = new ArrayList<>();
        int numOfLines = Math.max(paddings.size(), linesToCenter.size());
        for(int i = 0; i < numOfLines; i++) {
            if(i < linesToCenter.size()) {
                BaseComponent lineToCenter = linesToCenter.get(i);
                if (lineToCenter != null) {
                    BaseComponent padding = null;
                    int paddingPxSize = 0;
                    if (i < paddings.size()) {
                        padding = paddings.get(i);
                        if (padding != null) {
                            paddingPxSize = getComponentPixels(padding);
                        }
                    }

                    TextComponent spacePadding = getSpacePadding(start - paddingPxSize);

                    TextComponent alignedLine;
                    if(padding != null) {
                        alignedLine = new TextComponent(padding);
                        alignedLine.addExtra(spacePadding);
                    } else {
                        alignedLine = new TextComponent(spacePadding);
                    }
                    alignedLine.addExtra(lineToCenter);
                    alignedLine.addExtra("\n");
                    alignedLines.add(alignedLine);
                }
            }
        }

        return alignedLines;
    }

    public static int getComponentPixels(BaseComponent message) {
        if(message == null) {
            return 0;
        }

        int messagePxSize = 0;
        if(message instanceof TextComponent) {
            TextComponent textMessage = (TextComponent) message;
            if (textMessage.getText() != null) {
                String plainText = textMessage.getText();

                boolean isBold = textMessage.isBold();

                //System.out.println(plainText.toCharArray());
                for (char c : plainText.toCharArray()) {
                    DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                    messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                    messagePxSize++; //Include one pixel for background
                }
            }
        }

        if(message.getExtra() != null) {
            for(BaseComponent extra : message.getExtra()) {
                messagePxSize += getComponentPixels(extra);
            }
        }

        return messagePxSize;
    }

    public static TextComponent getSpacePadding(int compensate) {
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int boldSpaces = compensate % spaceLength;
        int spaces = (compensate / spaceLength) - boldSpaces;

        return ChatUtil.builderToComponent(
                new ComponentBuilder(StringUtils.repeat(" ", spaces))
                        .append(StringUtils.repeat(" ", boldSpaces)).bold(true));
    }
}
