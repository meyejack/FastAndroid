// Generated code from Butter Knife. Do not modify!
package com.example.android.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class NewsActivity$$ViewInjector<T extends com.example.android.ui.NewsActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296257, "field 'lvNews'");
    target.lvNews = finder.castView(view, 2131296257, "field 'lvNews'");
  }

  @Override public void reset(T target) {
    target.lvNews = null;
  }
}
