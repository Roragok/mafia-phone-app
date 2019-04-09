/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.activities.adapters.recyclerviews.models

interface Searchable {
    /**
     * Determines if this object matches the search criteria
     *
     * @param searchText the search criteria
     * @param ignoreCase flag indicating if case is important
     * @return true if matches
     * @since 1.0.0
     */
    fun matchesSearch(searchText: String, ignoreCase: Boolean): Boolean
}