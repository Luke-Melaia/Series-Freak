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
package net.lm.seriesfreak.database.implementation;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luke Melaia
 */
public class TypeLoader {

    private String packageName;
    
    private Class<? extends Annotation> annotationType;

    private ClassPath classPath;

    public TypeLoader(String packageName, Class<? extends Annotation> annotationType) {
        this.packageName = packageName;
        this.annotationType = annotationType;
    }
    
    public <T> List<Class<? extends T>> getClasses(Class<T> superType) throws IOException {
        classPath = ClassPath.from(this.getClass().getClassLoader());
        ImmutableSet<ClassPath.ClassInfo> classInfos = classPath.getTopLevelClasses(packageName);

        List<Class<? extends T>> classes = new ArrayList<>();

        for (ClassPath.ClassInfo classInfo : classInfos) {
            if (classInfo.load().isAnnotationPresent(annotationType)) {
                try {
                    classes.add(classInfo.load().asSubclass(superType));
                } catch (ClassCastException ex) {
                    //Checks that the class is castable before casring it.
                }
            }
        }

        return classes;
    }
}
