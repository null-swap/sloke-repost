package cc.sleek.client.gui.cgui.lime;

import cc.sleek.client.gui.cgui.lime.components.FrameCategory;
import cc.sleek.client.module.api.Category;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LimeGUI extends GuiScreen {
    private final List<FrameCategory> categories;

    public LimeGUI()
    {
        this.categories = new ArrayList<>();

        int index = -1;
        // Creating category instance foreach listed category
        for(Category category : Category.values())
        {
            categories.add(new FrameCategory(category, 10 + (++index * (Priority.defaultWidth + 10)), 10));
        }
    }

    @Override
    public void initGui()
    {
        categories.forEach(FrameCategory::initGui);
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        categories.forEach(frameCategory -> frameCategory.drawScreen(mouseX, mouseY));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        categories.forEach(frameCategory -> frameCategory.mouseClicked(mouseX, mouseY, mouseButton));
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        categories.forEach(frameCategory -> frameCategory.mouseReleased(mouseX, mouseY, state));
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}