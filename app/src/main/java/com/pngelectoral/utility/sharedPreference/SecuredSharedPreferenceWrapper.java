package com.pngelectoral.utility.sharedPreference;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SecuredSharedPreferenceWrapper implements PreferenceProvider {

    private static final String TAG = "SecuredSharedPref";
    private static final String ENCRYPTION_PREFIX = "ENC_v1:";
    private static final String KEY_SEED = "PNGEC_ELECTORAL_SECURE_STORAGE_SALT_2026";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    private PreferenceProvider preferenceProvider;
    private SecretKeySpec secretKeySpec;

    public SecuredSharedPreferenceWrapper(Context context, String sharedPreferenceName) {
        preferenceProvider = new SharedPreferences(context, sharedPreferenceName);
        initKey(context);
    }

    private void initKey(Context context) {
        try {
            String seed = KEY_SEED + (context != null ? context.getPackageName() : "com.pngelectoral");
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] keyBytes = sha.digest(seed.getBytes(StandardCharsets.UTF_8));
            secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        } catch (Exception e) {
            Log.e(TAG, "Failed to initialize encryption key: " + e.getMessage());
        }
    }

    private String encrypt(String plainText) {
        if (plainText == null || secretKeySpec == null) {
            return plainText;
        }
        try {
            byte[] iv = new byte[16];
            new SecureRandom().nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            byte[] combined = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

            return ENCRYPTION_PREFIX + Base64.encodeToString(combined, Base64.NO_WRAP);
        } catch (Exception e) {
            Log.e(TAG, "Encryption error: " + e.getMessage());
            return plainText;
        }
    }

    private String decrypt(String encryptedText) {
        if (encryptedText == null || secretKeySpec == null) {
            return encryptedText;
        }
        if (!encryptedText.startsWith(ENCRYPTION_PREFIX)) {
            // Unencrypted legacy value: return as is
            return encryptedText;
        }
        try {
            String payload = encryptedText.substring(ENCRYPTION_PREFIX.length());
            byte[] combined = Base64.decode(payload, Base64.NO_WRAP);
            if (combined.length < 16) {
                return encryptedText;
            }

            byte[] iv = new byte[16];
            byte[] cipherBytes = new byte[combined.length - 16];
            System.arraycopy(combined, 0, iv, 0, 16);
            System.arraycopy(combined, 16, cipherBytes, 0, cipherBytes.length);

            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);

            byte[] decrypted = cipher.doFinal(cipherBytes);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            Log.e(TAG, "Decryption error: " + e.getMessage());
            return encryptedText;
        }
    }

    @Override
    public void putString(String key, String value) {
        preferenceProvider.putString(key, encrypt(value));
    }

    @Override
    public String getString(String key, String defaultValue) {
        try {
            String stored = preferenceProvider.getString(key, null);
            if (stored == null) {
                return defaultValue;
            }
            return decrypt(stored);
        } catch (ClassCastException e) {
            return defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    @Override
    public void putBoolean(String key, boolean value) {
        preferenceProvider.putBoolean(key, value);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        try {
            return preferenceProvider.getBoolean(key, defaultValue);
        } catch (ClassCastException e) {
            try {
                String val = getString(key, null);
                if (val != null) {
                    return Boolean.parseBoolean(val);
                }
            } catch (Exception ignored) {
            }
            return defaultValue;
        }
    }

    @Override
    public void putInt(String key, int value) {
        preferenceProvider.putInt(key, value);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        try {
            return preferenceProvider.getInt(key, defaultValue);
        } catch (ClassCastException e) {
            try {
                String val = getString(key, null);
                if (val != null) {
                    return Integer.parseInt(val);
                }
            } catch (Exception ignored) {
            }
            return defaultValue;
        }
    }

    @Override
    public void putFloat(String key, float value) {
        preferenceProvider.putFloat(key, value);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        try {
            return preferenceProvider.getFloat(key, defaultValue);
        } catch (ClassCastException e) {
            try {
                String val = getString(key, null);
                if (val != null) {
                    return Float.parseFloat(val);
                }
            } catch (Exception ignored) {
            }
            return defaultValue;
        }
    }

    @Override
    public void putDouble(String key, double value) {
        preferenceProvider.putDouble(key, value);
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        try {
            return preferenceProvider.getDouble(key, defaultValue);
        } catch (ClassCastException e) {
            try {
                String val = getString(key, null);
                if (val != null) {
                    return Double.parseDouble(val);
                }
            } catch (Exception ignored) {
            }
            return defaultValue;
        }
    }

    @Override
    public void remove(String key) {
        preferenceProvider.remove(key);
    }

    @Override
    public void clear() {
        preferenceProvider.clear();
    }

    @Override
    public void putStringSet(String key, Set<String> value) {
        if (value == null) {
            preferenceProvider.putStringSet(key, null);
            return;
        }
        Set<String> encryptedSet = new HashSet<>();
        for (String item : value) {
            encryptedSet.add(encrypt(item));
        }
        preferenceProvider.putStringSet(key, encryptedSet);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        Set<String> rawSet = preferenceProvider.getStringSet(key, null);
        if (rawSet == null) {
            return defaultValue;
        }
        Set<String> decryptedSet = new HashSet<>();
        for (String item : rawSet) {
            decryptedSet.add(decrypt(item));
        }
        return decryptedSet;
    }

    @Override
    public <T> void saveObject(String key, T object) {
        preferenceProvider.saveObject(key, object);
    }

    @Override
    public <T> T getObject(String key, Class<T> object) {
        return preferenceProvider.getObject(key, object);
    }

    @Override
    public <T> void saveObjectsList(String key, List<T> objectList) {
        preferenceProvider.saveObjectsList(key, objectList);
    }

    @Override
    public <T> List<T> getObjectsList(String key, Class<T> object) {
        return preferenceProvider.getObjectsList(key, object);
    }

    @Override
    public boolean containsKey(String key) {
        return preferenceProvider.containsKey(key);
    }
}
