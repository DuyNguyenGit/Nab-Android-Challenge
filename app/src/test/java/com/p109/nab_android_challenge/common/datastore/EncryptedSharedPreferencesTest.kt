package com.p109.nab_android_challenge.common.datastore

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import androidx.test.core.app.ApplicationProvider
import com.p109.nab_android_challenge.util.FakeAndroidKeyStore
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [22, 28])
class EncryptedSharedPreferencesTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences =
        EncryptedSharedPreferences.create(
            "testPrefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    @Test
    fun `verify string storage`() {
        sharedPreferences.edit().apply {
            putString("key", "value")
        }.apply()

        assertEquals("value", sharedPreferences.getString("key", "default"))
    }

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            FakeAndroidKeyStore.setup
        }
    }

}