// Generated by view binder compiler. Do not edit!
package com.example.dermaone.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.dermaone.R;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRegisterBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final EditText etConfirmPassword;

  @NonNull
  public final EditText etEmail;

  @NonNull
  public final EditText etName;

  @NonNull
  public final EditText etPassword;

  @NonNull
  public final MaterialButton registerBtn;

  @NonNull
  public final TextView tvBesideRegister;

  @NonNull
  public final TextView tvConfirmPassword;

  @NonNull
  public final TextView tvEmail;

  @NonNull
  public final TextView tvLogin;

  @NonNull
  public final TextView tvName;

  @NonNull
  public final TextView tvPassword;

  @NonNull
  public final TextView tvRegister;

  private ActivityRegisterBinding(@NonNull ConstraintLayout rootView,
      @NonNull EditText etConfirmPassword, @NonNull EditText etEmail, @NonNull EditText etName,
      @NonNull EditText etPassword, @NonNull MaterialButton registerBtn,
      @NonNull TextView tvBesideRegister, @NonNull TextView tvConfirmPassword,
      @NonNull TextView tvEmail, @NonNull TextView tvLogin, @NonNull TextView tvName,
      @NonNull TextView tvPassword, @NonNull TextView tvRegister) {
    this.rootView = rootView;
    this.etConfirmPassword = etConfirmPassword;
    this.etEmail = etEmail;
    this.etName = etName;
    this.etPassword = etPassword;
    this.registerBtn = registerBtn;
    this.tvBesideRegister = tvBesideRegister;
    this.tvConfirmPassword = tvConfirmPassword;
    this.tvEmail = tvEmail;
    this.tvLogin = tvLogin;
    this.tvName = tvName;
    this.tvPassword = tvPassword;
    this.tvRegister = tvRegister;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_register, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRegisterBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.et_confirm_password;
      EditText etConfirmPassword = ViewBindings.findChildViewById(rootView, id);
      if (etConfirmPassword == null) {
        break missingId;
      }

      id = R.id.et_email;
      EditText etEmail = ViewBindings.findChildViewById(rootView, id);
      if (etEmail == null) {
        break missingId;
      }

      id = R.id.et_name;
      EditText etName = ViewBindings.findChildViewById(rootView, id);
      if (etName == null) {
        break missingId;
      }

      id = R.id.et_password;
      EditText etPassword = ViewBindings.findChildViewById(rootView, id);
      if (etPassword == null) {
        break missingId;
      }

      id = R.id.register_btn;
      MaterialButton registerBtn = ViewBindings.findChildViewById(rootView, id);
      if (registerBtn == null) {
        break missingId;
      }

      id = R.id.tv_beside_register;
      TextView tvBesideRegister = ViewBindings.findChildViewById(rootView, id);
      if (tvBesideRegister == null) {
        break missingId;
      }

      id = R.id.tv_confirm_password;
      TextView tvConfirmPassword = ViewBindings.findChildViewById(rootView, id);
      if (tvConfirmPassword == null) {
        break missingId;
      }

      id = R.id.tv_email;
      TextView tvEmail = ViewBindings.findChildViewById(rootView, id);
      if (tvEmail == null) {
        break missingId;
      }

      id = R.id.tv_login;
      TextView tvLogin = ViewBindings.findChildViewById(rootView, id);
      if (tvLogin == null) {
        break missingId;
      }

      id = R.id.tv_name;
      TextView tvName = ViewBindings.findChildViewById(rootView, id);
      if (tvName == null) {
        break missingId;
      }

      id = R.id.tv_password;
      TextView tvPassword = ViewBindings.findChildViewById(rootView, id);
      if (tvPassword == null) {
        break missingId;
      }

      id = R.id.tv_register;
      TextView tvRegister = ViewBindings.findChildViewById(rootView, id);
      if (tvRegister == null) {
        break missingId;
      }

      return new ActivityRegisterBinding((ConstraintLayout) rootView, etConfirmPassword, etEmail,
          etName, etPassword, registerBtn, tvBesideRegister, tvConfirmPassword, tvEmail, tvLogin,
          tvName, tvPassword, tvRegister);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
