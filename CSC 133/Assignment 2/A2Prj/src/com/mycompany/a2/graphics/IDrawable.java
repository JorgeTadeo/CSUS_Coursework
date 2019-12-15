package com.mycompany.a2.graphics;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

public interface IDrawable {
	/**
	 * 
	 * @param g
	 * @param origin : origin of component relative to parent
	 */
	void draw(Graphics g , Point origin);
}
