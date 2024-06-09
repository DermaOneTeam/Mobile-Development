// Generated by view binder compiler. Do not edit!
package com.example.dermaone.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.dermaone.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class HistoryListBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout historyList;

  @NonNull
  public final ImageView ivHistoryImage;

  @NonNull
  public final TextView tvHistoryDate;

  private HistoryListBinding(@NonNull LinearLayout rootView, @NonNull LinearLayout historyList,
      @NonNull ImageView ivHistoryImage, @NonNull TextView tvHistoryDate) {
    this.rootView = rootView;
    this.historyList = historyList;
    this.ivHistoryImage = ivHistoryImage;
    this.tvHistoryDate = tvHistoryDate;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static HistoryListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static HistoryListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.history_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static HistoryListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      LinearLayout historyList = (LinearLayout) rootView;

      id = R.id.iv_history_image;
      ImageView ivHistoryImage = ViewBindings.findChildViewById(rootView, id);
      if (ivHistoryImage == null) {
        break missingId;
      }

      id = R.id.tv_history_date;
      TextView tvHistoryDate = ViewBindings.findChildViewById(rootView, id);
      if (tvHistoryDate == null) {
        break missingId;
      }

      return new HistoryListBinding((LinearLayout) rootView, historyList, ivHistoryImage,
          tvHistoryDate);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
