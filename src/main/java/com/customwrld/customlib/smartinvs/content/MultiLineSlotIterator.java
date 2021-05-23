package com.customwrld.customlib.smartinvs.content;

import com.customwrld.customlib.smartinvs.ClickableItem;
import com.customwrld.customlib.smartinvs.SmartInventory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MultiLineSlotIterator implements SlotIterator {

    private InventoryContents contents;
    private SmartInventory inv;

    private boolean started = false;
    private boolean allowOverride = true;
    private int row, column;
    private int xSize, ySize;

    private Type type;
    private EnumIteratorXDirection xDirection;
    private EnumIteratorYDirection yDirection;

    private Set<SlotPos> blacklisted = new HashSet<>();

    public MultiLineSlotIterator(InventoryContents contents, SmartInventory inv,
                                 int startRow, int startColumn, int xSize, int ySize,
                                 Type type, EnumIteratorXDirection xDirection, EnumIteratorYDirection yDirection) {

        this.contents = contents;
        this.inv = inv;

        this.row = startRow;
        this.column = startColumn;

        this.xSize = xSize;
        this.ySize = ySize;

        this.type = type;
        this.xDirection = xDirection;
        this.yDirection = yDirection;
    }

    @Override
    public Optional<ClickableItem> get() {
        return contents.get(row, column);
    }

    @Override
    public SlotIterator set(ClickableItem item) {
        if(canPlace())
            contents.set(row, column, item);

        return this;
    }

    @Override
    public SlotIterator reset() {
        row = 0;
        column = 0;
        return this;
    }

    @Override
    public SlotIterator previous() {
        if(row == 0 && column == 0) {
            this.started = true;
            return this;
        }

        do {
            if(!this.started) {
                this.started = true;
            }
            else {
                switch(type) {
                    case HORIZONTAL:
                        switch (yDirection) {
                            case DOWN:
                                switch (xDirection) {
                                    case RIGHT -> {
                                        column--;
                                        if (column == 0) {
                                            column = xSize - 1;
                                            row++;
                                        }
                                    }
                                    case LEFT -> {
                                        column = ++column % xSize;
                                        if (column == 0) {
                                            column = 1;
                                            row++;
                                        }
                                    }
                                }
                                break;
                            case UP:
                                switch (xDirection) {
                                    case RIGHT -> {
                                        column--;
                                        if (column == 0) {
                                            column = xSize - 1;
                                            row--;
                                        }
                                    }
                                    case LEFT -> {
                                        column = ++column % xSize;
                                        if (column == 0) {
                                            column = 1;
                                            row--;
                                        }
                                    }
                                }
                        }
                        break;
                    case VERTICAL:
                        switch (yDirection) {
                            case DOWN:
                                switch (xDirection) {
                                    case RIGHT -> {
                                        row--;
                                        if (row == 0) {
                                            row = ySize - 1;
                                            column--;
                                        }
                                    }
                                    case LEFT -> {
                                        row--;
                                        if (row == 0) {
                                            row = ySize - 1;
                                            column++;
                                        }
                                    }
                                }
                                break;
                            case UP:
                                switch (xDirection) {
                                    case RIGHT -> {
                                        row = ++row % ySize;
                                        if (row == 0) {
                                            column--;
                                        }
                                    }
                                    case LEFT -> {
                                        row = ++row % ySize;
                                        if (row == 0) {
                                            column++;
                                        }
                                    }
                                }
                        }
                        break;
                }
            }
        }
        while(!canPlace() && !first());

        return this;
    }

    @Override
    public SlotIterator next() {
        if(ended()) {
            this.started = true;
            return this;
        }

        do {
            if(!this.started) {
                this.started = true;
            }
            else {
                switch(type) {
                    case HORIZONTAL:
                        switch (yDirection) {
                            case DOWN:
                                switch (xDirection) {
                                    case RIGHT -> {
                                        column = ++column % xSize;
                                        if (column == 0) {
                                            row++;
                                        }
                                    }
                                    case LEFT -> {
                                        column--;
                                        if (column == 0) {
                                            column = xSize - 1;
                                            row++;
                                        }
                                    }
                                }
                                break;
                            case UP:
                                switch (xDirection) {
                                    case RIGHT -> {
                                        column = ++column % xSize;
                                        if (column == 0) {
                                            column = 1;
                                            row--;
                                        }
                                    }
                                    case LEFT -> {
                                        column--;
                                        if (column == 0) {
                                            column = xSize - 1;
                                            row--;
                                        }
                                    }
                                }
                        }
                        break;
                    case VERTICAL:
                        switch (yDirection) {
                            case DOWN:
                                switch (xDirection) {
                                    case RIGHT -> {
                                        row = ++row % ySize;
                                        if (row == 0) {
                                            column++;
                                        }
                                    }
                                    case LEFT -> {
                                        row = ++row % ySize;
                                        if (row == 0) {
                                            column--;
                                        }
                                    }
                                }
                                break;
                            case UP:
                                switch (xDirection) {
                                    case RIGHT -> {
                                        row--;
                                        if (row == 0) {
                                            row = ySize - 1;
                                            column++;
                                        }
                                    }
                                    case LEFT -> {
                                        row--;
                                        if (row == 0) {
                                            row = ySize - 1;
                                            column--;
                                        }
                                    }
                                }
                        }
                        break;
                }
            }
        }
        while(!canPlace() && !ended());

        return this;
    }

    @Override
    public SlotIterator blacklist(int row, int column) {
        this.blacklisted.add(SlotPos.of(row, column));
        return this;
    }

    @Override
    public SlotIterator blacklist(SlotPos slotPos) {
        return blacklist(slotPos.getRow(), slotPos.getColumn());
    }

    @Override
    public int row() { return row; }

    @Override
    public SlotIterator row(int row) {
        this.row = row;
        return this;
    }

    @Override
    public int column() { return column; }

    @Override
    public SlotIterator column(int column) {
        this.column = column;
        return this;
    }

    @Override
    public boolean started() {
        return this.started;
    }

    public boolean first() {
        return ((xDirection == EnumIteratorXDirection.RIGHT && column == 0) || (xDirection == EnumIteratorXDirection.LEFT && column == xSize)) &&
                ((yDirection == EnumIteratorYDirection.DOWN && row == 0) || (yDirection == EnumIteratorYDirection.UP && row == ySize));
    }

    @Override
    public boolean ended() {
        return ((xDirection == EnumIteratorXDirection.RIGHT && column == xSize) || (xDirection == EnumIteratorXDirection.LEFT && column == 0)) &&
                ((yDirection == EnumIteratorYDirection.DOWN && row == ySize) || (yDirection == EnumIteratorYDirection.UP && row == 0));
    }

    @Override
    public boolean doesAllowOverride() { return allowOverride; }

    @Override
    public SlotIterator allowOverride(boolean override) {
        this.allowOverride = override;
        return this;
    }

    private boolean canPlace() {
        return !blacklisted.contains(SlotPos.of(row, column)) && (allowOverride || !this.get().isPresent());
    }

}