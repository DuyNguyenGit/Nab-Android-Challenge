package com.p109.nab_android_challenge.common.datastore

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object SharedPref {
    private const val sharedPrefsFile: String = "secret_shared_prefs"
    internal lateinit var encryptedSharedPref: SharedPreferences
    fun init(context: Context) {
        Log.d("SharedPref", "init: >>>")
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        encryptedSharedPref = EncryptedSharedPreferences.create(
            sharedPrefsFile,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun write(key: String?, value: String?) {
        return with(encryptedSharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun read(key: String?, defValue: String) = encryptedSharedPref.getString(key, defValue)!!
}