package org.robobinding.widget.seekbar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.AbstractEventViewAttributeWithViewListenersAwareTest;
import org.robolectric.annotation.Config;

import android.widget.SeekBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
public class OnSeekBarChangeAttributeTest extends
		AbstractEventViewAttributeWithViewListenersAwareTest<SeekBar, OnSeekBarChangeAttribute, MockSeekBarListeners> {
	@Test
	public void givenBoundAttribute_whenUpdatingProgress_thenEventReceived() {
		bindAttribute();

		int newProgressValue = RandomValues.anyInteger();
		updateProgressOnSeekBar(newProgressValue);

		assertEventReceived(newProgressValue);
	}

	private void updateProgressOnSeekBar(int newProgressValue) {
		view.setProgress(newProgressValue);
	}

	private void assertEventReceived(int newProgressValue) {
		assertEventReceived(SeekBarChangeEvent.class);
		SeekBarChangeEvent seekBarEvent = getEventReceived();
		assertThat(seekBarEvent.getView(), sameInstance(view));
		assertThat(seekBarEvent.getProgress(), is(newProgressValue));
	}

	@Test
	public void whenBinding_thenRegisterWithViewListeners() {
		bindAttribute();

		assertTrue(viewListeners.addOnSeekBarChangeListenerInvoked);
	}
}
