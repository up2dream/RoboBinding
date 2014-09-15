package org.robobinding.widget.progressbar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.AbstractPropertyViewAttributeTest;
import org.robolectric.annotation.Config;

import android.widget.ProgressBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest=Config.NONE)
public class SecondaryProgressAttributeTest extends AbstractPropertyViewAttributeTest<ProgressBar, SecondaryProgressAttribute> {
	@Test
	public void whenUpdateView_thenSetSecondaryProgressOnProgressBar() {
		int newProgress = RandomValues.anyInteger();

		attribute.updateView(view, newProgress);

		assertThat(view.getSecondaryProgress(), is(newProgress));
	}

}
