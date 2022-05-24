package cc.sleek.client.event.impl;

import cc.sleek.client.event.Event;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;

public class CollideEvent extends Event {

    private AxisAlignedBB axisAlignedBB;
    private final Block block;
    private int x, y, z;

    public CollideEvent(AxisAlignedBB axisAlignedBB, Block block, int x, int y, int z) {
        this.axisAlignedBB = axisAlignedBB;
        this.block = block;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public AxisAlignedBB getAxisAlignedBB() {
        return axisAlignedBB;
    }

    public void setAxisAlignedBB(AxisAlignedBB axisAlignedBB) {
        this.axisAlignedBB = axisAlignedBB;
    }

    public Block getBlock() {
        return block;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
