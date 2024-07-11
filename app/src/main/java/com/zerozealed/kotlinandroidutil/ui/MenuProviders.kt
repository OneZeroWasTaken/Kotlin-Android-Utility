package com.zerozealed.kotlinandroidutil.ui

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.MenuRes
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import com.zerozealed.kotlinandroidutil.R


infix fun Fragment.withMenu(menu: BaseMenu) {
    this.requireActivity().addMenuProvider(menu, this.viewLifecycleOwner)
}

abstract class BaseMenu(
    @MenuRes
    private val menuId: Int,
    protected var parent: BaseMenu? = null
) : MenuProvider {
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(menuId, menu)
        parent?.onCreateMenu(menu, menuInflater)
    }

    operator fun plus(other: BaseMenu): BaseMenu = this.apply {
        this.parent = other
    }
}

class MainMenu(
    parent: BaseMenu? = null,
    private val onSettings: () -> Unit,
) : BaseMenu(R.menu.menu_main, parent) {
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_settings -> onSettings()
            else -> parent?.onMenuItemSelected(menuItem) ?: return false
        }
        return true
    }
}