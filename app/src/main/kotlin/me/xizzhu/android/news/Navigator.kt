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
import android.os.Bundle
import androidx.annotation.IntDef
import me.xizzhu.android.news.home.HomeActivity

class Navigator {
    companion object {
        const val SCREEN_HOME = 0

        @IntDef(SCREEN_HOME)
        @Retention(AnnotationRetention.SOURCE)
        annotation class Screen
    }

    fun navigate(activity: Activity, @Screen screen: Int, extras: Bundle? = null) {
        val intent = when (screen) {
            SCREEN_HOME -> HomeActivity.newStartIntent(activity)
            else -> throw IllegalArgumentException("Unknown screen - $screen")
        }
        extras?.let { intent.putExtras(it) }
        activity.startActivity(intent)
    }

    fun goBack(activity: Activity) {
        activity.finish()
    }
}
