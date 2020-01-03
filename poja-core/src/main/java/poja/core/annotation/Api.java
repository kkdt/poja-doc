/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-core' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Api {
    /**
     * API name/identifier.
     *
     * @return
     */
    String name();

    /**
     * The API root path value.
     *
     * @return
     */
    String value();

    /**
     * Additional details can be externally linked (i.e. to javadoc, confluence, etc).
     *
     * @return
     */
    String url() default "";

    /**
     * Short API description.
     *
     * @return
     */
    String description() default "";
}
