package org.gemini.set_assistant;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;

public class SetAssistantActivity extends Activity {
  private static final String defaultAssistant =
      "com.google.android.googlequicksearchbox/" +
      "com.google.android.voiceinteraction.GsaVoiceInteractionService";

  @Override
  protected void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    final String assistant = getAssistant();
    Settings.Secure.putString(getContentResolver(), "assistant", assistant);
    Settings.Secure.putInt(getContentResolver(), "assist_structure_enabled", 1);
    Settings.Secure.putInt(
        getContentResolver(), "assist_screenshot_enabled", 1);
    Settings.Secure.putString(
        getContentResolver(), "voice_interaction_service", assistant);
    Settings.Secure.putString(
        getContentResolver(), "voice_recognition_service", assistant);
    finish();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Process.killProcess(Process.myPid());
    System.exit(0);
  }

  private String getAssistant() {
    if (getIntent() == null) {
      return defaultAssistant;
    }

    Uri uri = getIntent().getData();
    if (uri == null) {
      return defaultAssistant;
    }

    String path = uri.getPath();
    if (path == null) {
      return defaultAssistant;
    }

    path = path.trim();
    while (path.startsWith("/")) {
      path = path.substring(1);
    }

    if (path.length() == 0) {
      return defaultAssistant;
    }

    return path;
  }
}
