package cuchaz.enigma.config;

import com.google.common.collect.ImmutableMap;
import cuchaz.enigma.gui.Gui;
import cuchaz.enigma.gui.EnigmaSyntaxKit;
import cuchaz.enigma.gui.highlight.BoxHighlightPainter;
import cuchaz.enigma.gui.highlight.TokenHighlightType;
import de.sciss.syntaxpane.DefaultSyntaxKit;

import javax.swing.*;
import java.io.IOException;

public class Themes {

    public static void setLookAndFeel(Gui gui, Config.LookAndFeel lookAndFeel) {
        Config.getInstance().lookAndFeel = lookAndFeel;
	    updateTheme(gui);
    }

    public static void updateTheme(Gui gui) {
        Config config = Config.getInstance();
        config.lookAndFeel.setGlobalLAF();
        config.lookAndFeel.apply(config);
        try {
	        config.saveConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EnigmaSyntaxKit.invalidate();
        DefaultSyntaxKit.initKit();
        DefaultSyntaxKit.registerContentType("text/enigma-sources", EnigmaSyntaxKit.class.getName());
        gui.boxHighlightPainters = ImmutableMap.of(
                TokenHighlightType.OBFUSCATED, BoxHighlightPainter.create(config.obfuscatedColor, config.obfuscatedColorOutline),
                TokenHighlightType.PROPOSED, BoxHighlightPainter.create(config.proposedColor, config.proposedColorOutline),
                TokenHighlightType.DEOBFUSCATED, BoxHighlightPainter.create(config.deobfuscatedColor, config.deobfuscatedColorOutline),
                TokenHighlightType.READONLY, BoxHighlightPainter.create(config.readonlyColor, config.readonlyColorOutline)
        );
        gui.setEditorTheme(config.lookAndFeel);
        SwingUtilities.updateComponentTreeUI(gui.getFrame());
    }
}
