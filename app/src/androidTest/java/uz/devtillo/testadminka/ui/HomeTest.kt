package uz.devtillo.testadminka.ui

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Before
import org.junit.Test
import uz.devtillo.testadminka.R

class HomeTest {

    private lateinit var binding: FragmentScenario<Home>

    @Before
    fun setUp() {
        binding = launchFragment()
        binding.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun testAndClickButton() {
        onView(withId(R.id.addQuestion2)).perform(click())
    }
}