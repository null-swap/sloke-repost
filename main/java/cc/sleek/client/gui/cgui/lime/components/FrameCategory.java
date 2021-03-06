package cc.sleek.client.gui.cgui.lime.components;

import cc.sleek.client.Sleek;
import cc.sleek.client.gui.cgui.lime.Priority;
import cc.sleek.client.module.api.Category;
import cc.sleek.client.util.Animate;
import cc.sleek.client.util.Easing;
import cc.sleek.client.util.RenderUtils;
import cc.sleek.client.util.fonts.Fonts;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class FrameCategory implements Priority {

    // Stuff
    private int x, y, xDrag, yDrag;
    private int width, height;

    private int offset; // Used to scroll

    private boolean drag;

    private final Category category;

    private final ArrayList<FrameModule> modules;

    // Smooth animation
    private final Animate animation;

    // Asking x and y so categories are not on themself
    public FrameCategory(Category category, int x, int y)
    {
        this.category = category;
        this.modules = new ArrayList<>();
        this.animation = new Animate().setEase(Easing.CUBIC_OUT).setSpeed(250).setMin(0).setMax(defaultWidth / 2F);

        this.x = x;
        this.y = y;
        this.xDrag = 0;
        this.yDrag = 0;
        this.offset = 0;

        this.drag = false;

        this.width = defaultWidth;
        this.height = defaultHeight;

        Sleek.INSTANCE.getModuleManager().getModulesInCategory(category).forEach(module -> this.modules.add(new FrameModule(module, this, 0, 0)));
    }

    public void initGui()
    {
        this.animation.setSpeed(100).reset();
    }

    public void drawScreen(int mouseX, int mouseY)
    {
        AtomicInteger offCat = new AtomicInteger();
        this.modules.forEach(module -> offCat.addAndGet(module.getOffset()));

        // Calculate height
        height = Math.min(categoryNameHeight + offCat.get(), defaultHeight);

        if(Mouse.hasWheel() && RenderUtils.hover(x, y, mouseX, mouseY, defaultWidth, height))
        {
            int wheel = Mouse.getDWheel();
            if(wheel > 0 && offset - (moduleHeight - 1) > 0) {
                offset -= moduleHeight;
            } else if(wheel < 0 && offset + (moduleHeight - 1) <= offCat.get() - height + categoryNameHeight) {
                offset += moduleHeight;
            }
        }



        // Drawing category base
        Gui.drawRect(getX(), getY(), getX() + width, getY() + getHeight(), mainColor);

        // Drawing category name section
        Gui.drawRect(getX(), getY(), getX() + width, getY() + categoryNameHeight, darkerMainColor);

        // Outline category base
        {
            Gui.drawRect(getX() - outlineWidth, getY(), getX(), getY() + getHeight(), darkerMainColor);
            Gui.drawRect(getX() + width, getY(), getX() + width + outlineWidth, getY() + getHeight(), darkerMainColor);
            Gui.drawRect(getX() - outlineWidth, y + getHeight(), getX() + width + outlineWidth, getY() + getHeight() + outlineWidth, darkerMainColor);
        }

        // Drag ClickGUI
        if(drag) {
            setX(this.xDrag + mouseX);
            setY(this.yDrag + mouseY);
        }

        // Drawing category name
        Fonts.productSans24.drawString(category.name(), x + 3, y + ((categoryNameHeight / 2F) - Fonts.productSans24.getHeight() / 2F), stringColor, true);

        GL11.glPushMatrix();
        GL11.glEnable(3089);
        RenderUtils.prepareScissorBox(getX() + (width / 2F) - animation.update().getValue(), getY() + categoryNameHeight, x + (width / 2F) + animation.getValue(), y + getHeight());


        // Drawing modules
        int i = 0;
        for (FrameModule module : this.modules)
        {
            module.setX(x);
            module.setY(y + categoryNameHeight + i - offset);
            module.drawScreen(mouseX, mouseY);
            i += module.getOffset();
        }

        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        // I really need to explain?
        for (FrameModule module : this.modules)
        {
            if(module.mouseClicked(mouseX, mouseY, mouseButton)) {
                setDrag(false);
                return;
            }
        }

        if(RenderUtils.hover(x, y, mouseX, mouseY, width, height) && mouseButton == 0)
        {
            setDrag(true);
            setXDrag(getX() - mouseX);
            setYDrag(getY() - mouseY);
        } else
            setDrag(false);
    }

    @SuppressWarnings("unused")
    public void mouseReleased(int mouseX, int mouseY, int state)
    {
        this.drag = false;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY()
    {
        return y;
    }

    public void setXDrag(int xDrag)
    {
        this.xDrag = xDrag;
    }

    public void setYDrag(int yDrag)
    {
        this.yDrag = yDrag;
    }

    public void setDrag(boolean drag)
    {
        this.drag = drag;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}