/*
 * Copyright (C) 2015 Luke Melaia
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.lm.seriesfreak.ui.language.node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javafx.scene.Node;
import net.lm.seriesfreak.ui.language.LanguageListener;
import net.lm.seriesfreak.ui.language.LanguageRegistry;
import net.lm.seriesfreak.util.ImageHelper;
import org.apache.logging.log4j.LogManager;

/**
 * @author Luke Melaia
 */
public interface LanguageComponent<T> extends LanguageListener {

    T setTextKey(String key);

    T setTooltipKey(String key);

    T setPromptKey(String key);

    default T setImage(String graphic) {
        try {
            Method setGraphic = this.getClass().getMethod("setGraphic", Node.class);
            setGraphic.invoke(this, ImageHelper.getImage(graphic));
        } catch (NoSuchMethodException ex) {
            throw new UnsupportedOperationException("Not supported yet.");
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            LogManager.getLogger().error("Failed to call setGraphic(Node)", ex);
        }

        return (T) this;
    }

    default T register() {
        LanguageRegistry.registerListener(this);
        return (T) this;
    }
}
