// Generated code from Butter Knife. Do not modify!
package com.example.android.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class NewsAdapter$ViewHolder$$ViewInjector<T extends com.example.android.adapter.NewsAdapter.ViewHolder> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131034183, "field 'tvSource'");
    target.tvSource = finder.castView(view, 2131034183, "field 'tvSource'");
    view = finder.findRequiredView(source, 2131034185, "field 'tvSendDate'");
    target.tvSendDate = finder.castView(view, 2131034185, "field 'tvSendDate'");
    view = finder.findRequiredView(source, 2131034181, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131034181, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131034184, "field 'tvTypeName'");
    target.tvTypeName = finder.castView(view, 2131034184, "field 'tvTypeName'");
    view = finder.findRequiredView(source, 2131034182, "field 'tvWriter'");
    target.tvWriter = finder.castView(view, 2131034182, "field 'tvWriter'");
  }

  @Override public void reset(T target) {
    target.tvSource = null;
    target.tvSendDate = null;
    target.tvTitle = null;
    target.tvTypeName = null;
    target.tvWriter = null;
  }
}
