package gui;

import javax.swing.border.Border;
import java.awt.*;

public class Design {

    private Color backgroundColor, componentColor, headerColor, textColor;
    private Font headerFont, textFont;
    private Border border;

    public Design() {

    }

    public Design(Color backgroundColor, Color componentColor, Color headerColor, Color textColor, Font headerFont, Font textFont, Border border) {
        this.backgroundColor = backgroundColor;
        this.componentColor = componentColor;
        this.headerColor = headerColor;
        this.textColor = textColor;
        this.headerFont = headerFont;
        this.textFont = textFont;
        this.border = border;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getComponentColor() {
        return componentColor;
    }

    public void setComponentColor(Color componentColor) {
        this.componentColor = componentColor;
    }

    public Color getHeaderColor() {
        return headerColor;
    }

    public void setHeaderColor(Color headerColor) {
        this.headerColor = headerColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public Font getHeaderFont() {
        return headerFont;
    }

    public void setHeaderFont(Font headerFont) {
        this.headerFont = headerFont;
    }

    public Font getTextFont() {
        return textFont;
    }

    public void setTextFont(Font textFont) {
        this.textFont = textFont;
    }

    public Border getBorder() {
        return border;
    }

    public void setBorder(Border border) {
        this.border = border;
    }
}
