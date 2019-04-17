/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.activities.adapters.viewpagers

import android.content.Context
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.roragok.namafia.R
import com.roragok.namafia.activities.fragments.GamesFragment
import com.roragok.namafia.activities.fragments.GamesFragment.GamesInputData
import com.roragok.namafia.events.IntentExtra

class GamesPagerAdapter(
    private val context: Context,
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        val active = position == 0

        val fragment = GamesFragment()
        fragment.arguments = bundleOf(IntentExtra.INPUT_DATA to GamesInputData(active))

        return fragment
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        val resId = if (position == 0) {
            R.string.active
        } else {
            R.string.inactive
        }

        return context.getString(resId)
    }
}