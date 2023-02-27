package shared;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.RELATIVE;

public class ConstraintBuilder {

    private GridBagConstraints constraint;

    public ConstraintBuilder(int fill, int anchor, Insets insets, int gridx, int gridy, int gridWidth, int gridHeight, int weightx, int weighty) {
        addFill(fill);
        addAnchor(anchor);
        addInsets(insets);
        addGridX(gridx);
        addGridY(gridy);
        addGridWidth(gridWidth);
        addGridHeight(gridHeight);
        addWeightX(weightx);
        addWeightY(weighty);
    }

    public ConstraintBuilder(int fill, int anchor, Insets insets) {
        //Fill Up Missing Values with base Values from GridBagConstraints Class
        this(fill, anchor, insets, RELATIVE, RELATIVE, 1, 1, 0, 0);
    }

    private ConstraintBuilder(GridBagConstraints constraint) {
        this.constraint = constraint;
    }

    public GridBagConstraints build() {
        return constraint;
    }

    @Override
    public ConstraintBuilder clone() {
        return new ConstraintBuilder(constraint);
    }

    public ConstraintBuilder addFill(int fill) {
        constraint.fill = fill;
        return this;
    }
    public ConstraintBuilder addAnchor(int anchor) {
        constraint.anchor = anchor;
        return this;
    }
    public ConstraintBuilder addInsets(Insets insets) {
        constraint.insets = insets;
        return this;
    }
    public ConstraintBuilder addGridX(int gridx) {
        constraint.gridx = gridx;
        return this;
    }
    public ConstraintBuilder addGridY(int gridy) {
        constraint.gridy = gridy;
        return this;
    }
    public ConstraintBuilder addGridWidth(int width) {
        constraint.gridwidth = width;
        return this;
    }
    public ConstraintBuilder addGridHeight(int height) {
        constraint.gridheight = height;
        return this;
    }
    public ConstraintBuilder addWeightX(int weightx) {
        constraint.weightx = weightx;
        return this;
    }
    public ConstraintBuilder addWeightY(int weighty) {
        constraint.weighty = weighty;
        return this;
    }
}
