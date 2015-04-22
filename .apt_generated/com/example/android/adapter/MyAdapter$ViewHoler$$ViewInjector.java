// Generated code from Butter Knife. Do not modify!
package com.example.android.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class MyAdapter$ViewHoler$$ViewInjector<T extends com.example.android.adapter.MyAdapter.ViewHoler> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131034181, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131034181, "field 'tvTitle'");
  }

  @Override public void reset(T target) {
    target.tvTitle = null;
  }
}
