/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.dagger.scopes

import javax.inject.Scope

/**
 * A scoping annotation to permit objects whose lifetime should conform to the life of an activity.
 *
 * @since 1.0.0
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope