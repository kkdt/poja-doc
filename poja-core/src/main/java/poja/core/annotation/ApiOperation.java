/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-core' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiOperation {
    /**
     * Operation full path.
     *
     * @return
     */
    String path();

    /**
     * Short description.
     *
     * @return
     */
    String description() default "";

    /**
     * All formats this operation will produce.
     *
     * @return
     */
    String[] produces() default {};

    /**
     * Allowable formats this operation supports.
     *
     * @return
     */
    String[] consumes() default {};

    /**
     * HTTP methods that this operation supports.
     *
     * @return
     */
    String[] methods() default {};

    /**
     * Additional details can be externally linked (i.e. to javadoc, confluence, etc).
     *
     * @return
     */
    String url() default "";

    /**
     * Parameters into this operation includes url parameters, request body, header attributes.
     *
     * @return
     */
    ApiParam[] parameters() default {};
}
