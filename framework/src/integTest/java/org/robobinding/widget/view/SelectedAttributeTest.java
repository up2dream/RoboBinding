package org.robobinding.widget.view;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.AbstractPropertyViewAttributeTest;
import org.robolectric.annotation.Config;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest=Config.NONE)
public class SelectedAttributeTest extends
		AbstractPropertyViewAttributeTest<View, SelectedAttribute> {
	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		boolean selected = RandomValues.trueOrFalse();

		attribute.updateView(view, selected);

		assertThat(view.isSelected(), equalTo(selected));
	}

}