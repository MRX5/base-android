package com.devm7mdibrahim.presentation.base

import android.view.MenuItem
import com.devm7mdibrahim.domain.repository.PreferenceRepository
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import javax.inject.Inject

abstract class BaseActivity : LocalizationActivity() {

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /*when (item.itemId) {
//            R.id.home -> {
//                onBackPressed()
//                return true
//            }
        }*/
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onAfterLocaleChanged() {

    }
}