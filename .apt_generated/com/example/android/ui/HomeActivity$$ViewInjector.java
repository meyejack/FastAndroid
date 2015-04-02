// Generated code from Butter Knife. Do not modify!
package com.example.android.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class HomeActivity$$ViewInjector<T extends com.example.android.ui.HomeActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131034173, "field 'tvContent'");
    target.tvContent = finder.castView(view, 2131034173, "field 'tvContent'");
  }

  @Override public void reset(T target) {
    target.tvContent = null;
  }
}
