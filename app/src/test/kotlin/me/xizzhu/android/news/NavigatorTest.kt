/*
 * Copyright (C) 2023 Xizhi Zhu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.xizzhu.android.news

import android.app.Activity
import android.app.Application
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import me.xizzhu.android.news.home.HomeActivity
import me.xizzhu.android.news.tests.BaseActivityTest
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows

@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
class NavigatorTest : BaseActivityTest() {
    private lateinit var navigator: Navigator

    @BeforeTest
    override fun setup() {
        super.setup()
        navigator = Navigator()
    }

    @Test
    fun `test navigate(), internal activities`() {
        testNavigate<HomeActivity>(Navigator.SCREEN_HOME)
    }

    private inline fun <reified A : Activity> testNavigate(@Navigator.Companion.Screen screen: Int) {
        withActivity<A> { activity ->
            navigator.navigate(activity, screen)

            val nextStartedIntent = Shadows.shadowOf(ApplicationProvider.getApplicationContext<Application>()).nextStartedActivity
            assertEquals(A::class.java.name, nextStartedIntent.component?.className)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test navigate(), with unknown screen`() {
        withActivity<HomeActivity> { activity ->
            navigator.navigate(activity, -1)
        }
    }

    @Test
    fun `test goBack()`() {
        withActivity<HomeActivity> { activity ->
            navigator.goBack(activity)
            assertTrue(activity.isFinishing)
        }
    }
}
