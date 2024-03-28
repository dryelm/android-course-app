package com.example.myapplication

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myapplication.callbacks.HabitCallback
import com.example.myapplication.callbacks.InfoCallback
import com.example.myapplication.fragments.InfoFragment
import com.example.myapplication.fragments.MainFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private var habitList: ArrayList<Habit> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigationDrawerLayout = findViewById<DrawerLayout>(R.id.navigationDrawerLayout)
        val drawerToggle = ActionBarDrawerToggle(this, navigationDrawerLayout,
            R.string.open_drawer,
            R.string.close_drawer)
        navigationDrawerLayout.addDrawerListener(drawerToggle)

        drawerToggle.syncState()
        if (savedInstanceState == null) {
            setFragment(navigationDrawerLayout)
        }
    }

    private fun setFragment(navigationDrawerLayout: DrawerLayout) {
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val infoFragment = InfoFragment.newInstance()

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, createMainFragment())
                        .commit()
                }

                R.id.nav_info -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, infoFragment)
                        .commit()
                }
            }
            navigationDrawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, createMainFragment())
            .commit()
    }

    private fun createMainFragment(): MainFragment {
        val bundle = Bundle().apply {
            putParcelableArrayList("habits", habitList)
        }
        val fragment = MainFragment.newInstance(bundle)
        fragment.callback = object : HabitCallback {
            override fun onDestroy(habits: ArrayList<Habit>) {
                habitList = habits
            }

        }
        return fragment
    }
}


