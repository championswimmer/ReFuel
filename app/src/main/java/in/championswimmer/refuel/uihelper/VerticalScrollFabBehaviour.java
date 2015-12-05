package in.championswimmer.refuel.uihelper;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import in.championswimmer.refuel.R;

/**
 * Created by championswimmer on 29/11/15.
 */
public class VerticalScrollFabBehaviour extends FloatingActionButton.Behavior {

    public static final String TAG = "ScrollFabBehaviour";

    public VerticalScrollFabBehaviour(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       FloatingActionButton child,
                                       View directTargetChild, View target, int nestedScrollAxes) {
        Log.i(TAG, "onStartNestedScroll: ");
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target,
                        nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
                dyUnconsumed);

        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            Log.i(TAG, "onNestedScroll: dy>0");
            ((FloatingActionButton) coordinatorLayout.findViewById(R.id.fab)).hide();
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            Log.i(TAG, "onNestedScroll: dy<0");
            ((FloatingActionButton) coordinatorLayout.findViewById(R.id.fab)).show();
        }
    }
}
