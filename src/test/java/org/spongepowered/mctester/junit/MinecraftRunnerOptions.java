package org.spongepowered.mctester.junit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MinecraftRunnerOptions {

    /**
     * Whether or not to close the Minecraft client after all tests have finished running.
     */
    boolean exitMinecraftOnFinish() default true;

    /**
     * Whether or not to delete the temporary world if all tests run successfully
     */
    boolean deleteWorldOnSuccess() default true;

    /**
     * Where or not to delete the temporary world if at least one test fails
     *
     */
    boolean deleteWorldOnFailure() default false;

}