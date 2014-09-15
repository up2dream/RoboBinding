package org.robobinding.widget.view;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.util.BitmapDrawableData;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.AbstractPropertyViewAttributeTest;
import org.robobinding.widget.view.BackgroundAttribute.DrawableBackgroundAttribute;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest=Config.NONE)
public class DrawableBackgroundAttributeTest extends
		AbstractPropertyViewAttributeTest<View, DrawableBackgroundAttribute> {
	@Test
	public void givenBoundAttribute_whenUpdateView_thenViewShouldReflectChanges() {
		BitmapDrawableData drawableData = RandomValues.anyBitmapDrawableData(Robolectric.application);

		attribute.updateView(view, drawableData.drawable);

		assertThat(Robolectric.shadowOf(view.getBackground()).getCreatedFromResId(), 
				equalTo(drawableData.resourceId));
	}
}