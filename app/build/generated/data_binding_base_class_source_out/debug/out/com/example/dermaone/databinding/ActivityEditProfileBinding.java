// Generated by view binder compiler. Do not edit!
package com.example.dermaone.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.dermaone.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityEditProfileBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final CardView editProfileCardView;

  @NonNull
  public final EditText etEmail;

  @NonNull
  public final EditText etUsername;

  @NonNull
  public final ImageView ivArrowBack;

  @NonNull
  public final ImageView ivProfilePicture;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final TextView tvEditPicture;

  @NonNull
  public final TextView tvEditProfile;

  @NonNull
  public final TextView tvEmail;

  @NonNull
  public final TextView tvSave;

  @NonNull
  public final TextView tvUsername;

  private ActivityEditProfileBinding(@NonNull ConstraintLayout rootView,
      @NonNull CardView editProfileCardView, @NonNull EditText etEmail,
      @NonNull EditText etUsername, @NonNull ImageView ivArrowBack,
      @NonNull ImageView ivProfilePicture, @NonNull ConstraintLayout main,
      @NonNull TextView tvEditPicture, @NonNull TextView tvEditProfile, @NonNull TextView tvEmail,
      @NonNull TextView tvSave, @NonNull TextView tvUsername) {
    this.rootView = rootView;
    this.editProfileCardView = editProfileCardView;
    this.etEmail = etEmail;
    this.etUsername = etUsername;
    this.ivArrowBack = ivArrowBack;
    this.ivProfilePicture = ivProfilePicture;
    this.main = main;
    this.tvEditPicture = tvEditPicture;
    this.tvEditProfile = tvEditProfile;
    this.tvEmail = tvEmail;
    this.tvSave = tvSave;
    this.tvUsername = tvUsername;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityEditProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityEditProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_edit_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityEditProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.edit_profile_card_view;
      CardView editProfileCardView = ViewBindings.findChildViewById(rootView, id);
      if (editProfileCardView == null) {
        break missingId;
      }

      id = R.id.et_email;
      EditText etEmail = ViewBindings.findChildViewById(rootView, id);
      if (etEmail == null) {
        break missingId;
      }

      id = R.id.et_username;
      EditText etUsername = ViewBindings.findChildViewById(rootView, id);
      if (etUsername == null) {
        break missingId;
      }

      id = R.id.iv_arrow_back;
      ImageView ivArrowBack = ViewBindings.findChildViewById(rootView, id);
      if (ivArrowBack == null) {
        break missingId;
      }

      id = R.id.iv_profile_picture;
      ImageView ivProfilePicture = ViewBindings.findChildViewById(rootView, id);
      if (ivProfilePicture == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.tv_edit_picture;
      TextView tvEditPicture = ViewBindings.findChildViewById(rootView, id);
      if (tvEditPicture == null) {
        break missingId;
      }

      id = R.id.tv_edit_profile;
      TextView tvEditProfile = ViewBindings.findChildViewById(rootView, id);
      if (tvEditProfile == null) {
        break missingId;
      }

      id = R.id.tv_email;
      TextView tvEmail = ViewBindings.findChildViewById(rootView, id);
      if (tvEmail == null) {
        break missingId;
      }

      id = R.id.tv_save;
      TextView tvSave = ViewBindings.findChildViewById(rootView, id);
      if (tvSave == null) {
        break missingId;
      }

      id = R.id.tv_username;
      TextView tvUsername = ViewBindings.findChildViewById(rootView, id);
      if (tvUsername == null) {
        break missingId;
      }

      return new ActivityEditProfileBinding((ConstraintLayout) rootView, editProfileCardView,
          etEmail, etUsername, ivArrowBack, ivProfilePicture, main, tvEditPicture, tvEditProfile,
          tvEmail, tvSave, tvUsername);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
